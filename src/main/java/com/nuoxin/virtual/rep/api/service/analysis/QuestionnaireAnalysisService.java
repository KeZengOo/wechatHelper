package com.nuoxin.virtual.rep.api.service.analysis;

import com.alibaba.fastjson.JSON;
import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.entity.Question;
import com.nuoxin.virtual.rep.api.mybatis.QuestionnaireAnalysisMapper;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.analysis.QuestionnaireAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.question.OptionsRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.q.QuestionDateResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.q.QuestionOptionsStatResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.q.QuestionStatResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.q.QuestionnaireStatResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.question.QuestionnaireResponseBean;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


/**
 * Created by fenggang on 10/11/17.
 */
@Service
public class QuestionnaireAnalysisService extends BaseService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuestionnaireAnalysisMapper questionnaireAnalysisMapper;

    /**
     * 获取当前时间段问卷分析的汇总统计及图表数据
     *
     * @param bean
     * @return
     */
    public QuestionnaireStatResponseBean summation(QuestionnaireAnalysisRequestBean bean) {
        //TODO 判断时间格式是否正常
        QuestionnaireStatResponseBean sumBean = questionnaireAnalysisMapper.summation(bean);
        List<QuestionDateResponseBean> sumList = questionnaireAnalysisMapper.summationList(bean);

        //获取从开始时间到结束时间中间所有的横坐标，纵坐标默认为0
        List<QuestionDateResponseBean> responseBeans = this._lineChartResult(bean);
        if(sumList!=null && !sumList.isEmpty()){
            for (int i = 0,leng=responseBeans.size(); i < leng; i++) {
                QuestionDateResponseBean responseBean = responseBeans.get(i);
                for (int j = 0,jleng=sumList.size(); j < jleng ; j++) {
                    QuestionDateResponseBean qbean = sumList.get(j);
                    if(responseBean.getDate().equals(qbean.getDate())){
                        responseBean.setCountNum(qbean.getCountNum());
                        responseBean.setPeopleNum(qbean.getPeopleNum());
                    }
                }
            }
            sumBean.setList(responseBeans);
        }
        return sumBean;
    }

    /**
     * 获取当前时间段所涉及到的所有问卷
     *
     * @param bean
     * @return
     */
    public List<QuestionnaireResponseBean> list(QuestionnaireAnalysisRequestBean bean) {
        return questionnaireAnalysisMapper.list(bean);
    }

    /**
     * 获取当时时间段当前问卷所有的答题信息汇总统计
     *
     * @param bean
     * @return
     */
    public List<QuestionStatResponseBean> details(QuestionnaireAnalysisRequestBean bean) {

        List<QuestionStatResponseBean> questions = questionnaireAnalysisMapper.questionList(bean);

        List<QuestionOptionsStatResponseBean> radios = questionnaireAnalysisMapper.questionUsreAnswerType0(bean);
        List<QuestionOptionsStatResponseBean> checkbox = questionnaireAnalysisMapper.questionUsreAnswerType1(bean);

        Map<Long,List<QuestionOptionsStatResponseBean>> map = new HashMap<Long,List<QuestionOptionsStatResponseBean>>();
        if(radios!=null && !radios.isEmpty()){
            for (int i = 0,leng=radios.size(); i < leng ; i++) {
                QuestionOptionsStatResponseBean optionsStat = radios.get(i);
                List<QuestionOptionsStatResponseBean> mapOptions = map.get(optionsStat.getQuestionId());
                if(mapOptions==null || mapOptions.isEmpty()){
                    mapOptions = new ArrayList<QuestionOptionsStatResponseBean>();
                }
                mapOptions.add(optionsStat);
                map.put(optionsStat.getQuestionId(),mapOptions);
            }
        }
        if(checkbox!=null && !checkbox.isEmpty()){
            for (int i = 0,leng=checkbox.size(); i < leng ; i++) {
                QuestionOptionsStatResponseBean optionsStat = checkbox.get(i);
                List<QuestionOptionsStatResponseBean> mapOptions = map.get(optionsStat.getQuestionId());
                if(mapOptions==null || mapOptions.isEmpty()){
                    mapOptions = new ArrayList<QuestionOptionsStatResponseBean>();
                }
                mapOptions.add(optionsStat);
                map.put(optionsStat.getQuestionId(),mapOptions);
            }
        }
        if(questions!=null && !questions.isEmpty()){
            for (int i = 0,leng=questions.size(); i < leng ; i++) {
                QuestionStatResponseBean responseBean = questions.get(i);
                responseBean.setOptions(JSON.parseArray(responseBean.getOption(), OptionsRequestBean.class));
                List<QuestionOptionsStatResponseBean> statList = map.get(responseBean.getId());
                if(statList!=null && !statList.isEmpty()){
                    for (QuestionOptionsStatResponseBean stat:statList) {
                        if(responseBean.getAnswerNum()==0 || responseBean.getAnswerNum()==null){
                            stat.setPercentage(0f);
                        }else{
                            stat.setPercentage(new BigDecimal(stat.getNum()/responseBean.getAnswerNum().doubleValue()).floatValue());
                        }
                    }
                    responseBean.setOptionsStat(statList);
                }
            }
        }
        return questions;
    }

    /**
     * 获取从开始时间到结束时间中间所有的横坐标，纵坐标默认为0
     *
     * @param bean
     * @return
     */
    private List<QuestionDateResponseBean> _lineChartResult(QuestionnaireAnalysisRequestBean bean) {
        List<QuestionDateResponseBean> responseBeans = new ArrayList<QuestionDateResponseBean>();
        String startDate = bean.getStartDate()+ " 00:00:00";
        String endDate = bean.getEndDate()+ " 23:59:59";

        Date start = DateUtil.getDateFromStr(startDate);
        Date end = DateUtil.getDateFromStr(endDate);

        //初始化开始循环输出的时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);

        while (true){
            if(calendar.getTime().getTime()>end.getTime()){
                break;
            }

            QuestionDateResponseBean dateBean = new QuestionDateResponseBean();
            String date = DateUtil.getDateString(calendar.getTime());
            dateBean.setDate(date);
            String[] dates = date.split("-");
            dateBean.setDayNum(Integer.valueOf(dates[1]));
            dateBean.setMonthNum(Integer.valueOf(dates[2]));
            calendar.add(Calendar.DAY_OF_YEAR,+1);
            responseBeans.add(dateBean);
        }

        return responseBeans;
    }
}
