package com.nuoxin.virtual.rep.api.service.analysis;

import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.mybatis.TrendAnalysisMapper;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.analysis.TargetAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.analysis.TrendAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.ta.MettingTargetResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.ta.TargetResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.tr.TrendResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.tr.TrendStatResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by fenggang on 10/12/17.
 */
@Service
public class TrendAnalysisService extends BaseService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TrendAnalysisMapper trendAnalysisMapper;

    /**
     * 呼出总时长
     *
     * @param bean
     * @return
     */
    public List<TrendResponseBean> summationCallout(TrendAnalysisRequestBean bean) {
        bean.checkDate();
        List<TrendResponseBean> responseBeans = this. _getTrendResponseBean(bean);
        return responseBeans;
    }

    /**
     * 呼出平均时长
     *
     * @param bean
     * @return
     */
    public List<TrendResponseBean> summationCalloutAvg(TrendAnalysisRequestBean bean) {
        bean.checkDate();
        List<TrendResponseBean> responseBeans = this. _getTrendResponseBean(bean);
        return responseBeans;
    }

    /**
     * 覆盖
     *
     * @param bean
     * @return
     */
    public List<TrendResponseBean> summationCover(TrendAnalysisRequestBean bean) {
        bean.checkDate();
        List<TrendResponseBean> responseBeans = this. _getTrendResponseBean(bean);
        return responseBeans;
    }

    /**
     * 会话
     *
     * @param bean
     * @return
     */
    public List<TrendResponseBean> summationSession(TrendAnalysisRequestBean bean) {
        bean.checkDate();
        List<TrendResponseBean> responseBeans = this. _getTrendResponseBean(bean);
        return responseBeans;
    }

    /**
     * 呼出数
     *
     * @param bean
     * @return
     */
    public List<TrendStatResponseBean> callOut(TrendAnalysisRequestBean bean) {
        //TODO 获取时间
        bean.setEndDate(bean.getDate() + " 23:00:00");
        bean.setStartDate(bean.getDate() + " 00:00:00");
        List<TrendStatResponseBean> responseBeans = trendAnalysisMapper.callOut(bean);
        return responseBeans;
    }

    /**
     * 会话数
     *
     * @param bean
     * @return
     */
    public List<TrendStatResponseBean> session(TrendAnalysisRequestBean bean) {
        //TODO 获取时间
        bean.setEndDate(bean.getDate() + " 23:00:00");
        bean.setStartDate(bean.getDate() + " 00:00:00");
        List<TrendStatResponseBean> responseBeans = new ArrayList<>();
        List<TrendStatResponseBean> wechat = trendAnalysisMapper.sessionType1(bean);
        List<TrendStatResponseBean> sms = trendAnalysisMapper.sessionType2(bean);
        for (int i = 0; i < 24; i++) {
            TrendStatResponseBean responseBean = new TrendStatResponseBean();
            responseBean.setHour(i);
            if (wechat != null && !wechat.isEmpty()) {
                for (TrendStatResponseBean stat : wechat) {
                    if (stat.getHour() != null && stat.getHour().intValue() == i) {
                        responseBean.setWechat(stat.getWechat());
                    }
                }
            }
            if (sms != null && !sms.isEmpty()) {
                for (TrendStatResponseBean stat : sms) {
                    if (stat.getHour() != null && stat.getHour().intValue() == i) {
                        responseBean.setWechat(stat.getSms());
                    }
                }
            }

            responseBeans.add(responseBean);
        }
        return responseBeans;
    }

    /**
     * 根据传人的值获取返回数据
     *
     * @param bean
     * @return
     */
    private List<TrendResponseBean> _getTrendResponseBean(TrendAnalysisRequestBean bean) {
        List<TrendResponseBean> responseBeans = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getDateFromStr(bean.getStartDate()));
        if (bean.getDateType() == 1) {
            for (int i = 0; i < 7; i++) {
                TrendResponseBean responseBean = new TrendResponseBean();
                responseBean.setDate(DateUtil.getDateString(calendar.getTime()));
                responseBean.setDay(calendar.get(Calendar.DAY_OF_MONTH));
                responseBean.setMonth(calendar.get(Calendar.MONTH) + 1);
                responseBean.setWeek(calendar.get(Calendar.WEEK_OF_YEAR));
                responseBean.setYear(calendar.get(Calendar.YEAR));
                responseBeans.add(responseBean);
                calendar.add(Calendar.DAY_OF_YEAR, +1);
            }
        } else if (bean.getDateType() == 2) {
            for (int i = 0; i < 4; i++) {
                TrendResponseBean responseBean = new TrendResponseBean();
                responseBean.setDate(DateUtil.getDateString(calendar.getTime()));
                responseBean.setDay(calendar.get(Calendar.DAY_OF_MONTH));
                responseBean.setMonth(calendar.get(Calendar.MONTH) + 1);
                responseBean.setWeek(calendar.get(Calendar.WEEK_OF_YEAR));
                responseBean.setYear(calendar.get(Calendar.YEAR));
                responseBeans.add(responseBean);
                calendar.add(Calendar.WEEK_OF_YEAR, +1);
            }

        } else if (bean.getDateType() == 3) {
            for (int i = 0; i < 4; i++) {
                TrendResponseBean responseBean = new TrendResponseBean();
                responseBean.setDate(DateUtil.getDateString(calendar.getTime()));
                responseBean.setDay(calendar.get(Calendar.DAY_OF_MONTH));
                responseBean.setMonth(calendar.get(Calendar.MONTH) + 1);
                responseBean.setWeek(calendar.get(Calendar.WEEK_OF_YEAR));
                responseBean.setYear(calendar.get(Calendar.YEAR));
                responseBeans.add(responseBean);
                calendar.add(Calendar.MONTH, +1);
            }
        } else if (bean.getDateType() == 4) {
            for (int i = 0; i < 4; i++) {
                TrendResponseBean responseBean = new TrendResponseBean();
                responseBean.setDate(DateUtil.getDateString(calendar.getTime()));
                responseBean.setDay(calendar.get(Calendar.DAY_OF_MONTH));
                responseBean.setMonth(calendar.get(Calendar.MONTH) + 1);
                responseBean.setWeek(calendar.get(Calendar.WEEK_OF_YEAR));
                responseBean.setYear(calendar.get(Calendar.YEAR));
                responseBean.setQuarter(((int) calendar.get(Calendar.MONTH) / 3)+1);
                responseBeans.add(responseBean);
                calendar.add(Calendar.MONTH, +3);
            }
        }

        return responseBeans;
    }
}
