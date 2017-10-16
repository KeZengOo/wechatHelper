package com.nuoxin.virtual.rep.api.service.analysis;

import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.mybatis.TrendAnalysisMapper;
import com.nuoxin.virtual.rep.api.web.controller.request.analysis.TargetAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.analysis.TrendAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.ta.MettingTargetResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.ta.TargetResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.tr.TrendStatResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fenggang on 10/12/17.
 */
@Service
public class TrendAnalysisService extends BaseService{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TrendAnalysisMapper trendAnalysisMapper;

    /**
     * 呼出数
     * @param bean
     * @return
     */
    public List<TrendStatResponseBean> callOut(TrendAnalysisRequestBean bean){
        //TODO 获取时间
        bean.setEndDate(bean.getDate()+" 23:00:00");
        bean.setStartDate(bean.getDate()+" 00:00:00");
        List<TrendStatResponseBean> responseBeans = trendAnalysisMapper.callOut(bean);
        return responseBeans;
    }

    /**
     * 会话数
     * @param bean
     * @return
     */
    public List<TrendStatResponseBean> session(TrendAnalysisRequestBean bean){
        //TODO 获取时间
        bean.setEndDate(bean.getDate()+" 23:00:00");
        bean.setStartDate(bean.getDate()+" 00:00:00");
        List<TrendStatResponseBean> responseBeans = new ArrayList<>();
        List<TrendStatResponseBean> wechat = trendAnalysisMapper.sessionType1(bean);
        List<TrendStatResponseBean> sms = trendAnalysisMapper.sessionType2(bean);
        for (int i = 0; i < 24; i++) {
            TrendStatResponseBean responseBean = new TrendStatResponseBean();
            responseBean.setHour(i);
            if(wechat!=null && !wechat.isEmpty()){
                for (TrendStatResponseBean stat:wechat) {
                    if(stat.getHour()!=null && stat.getHour().intValue()==i){
                        responseBean.setWechat(stat.getWechat());
                    }
                }
            }
            if(sms!=null && !sms.isEmpty()){
                for (TrendStatResponseBean stat:sms) {
                    if(stat.getHour()!=null && stat.getHour().intValue()==i){
                        responseBean.setWechat(stat.getSms());
                    }
                }
            }

            responseBeans.add(responseBean);
        }
        return responseBeans;
    }
}
