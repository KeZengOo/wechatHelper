package com.nuoxin.virtual.rep.api.service.analysis;

import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.entity.Target;
import com.nuoxin.virtual.rep.api.mybatis.TrendAnalysisMapper;
import com.nuoxin.virtual.rep.api.service.TargetService;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.analysis.TargetAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.analysis.TrendAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.ta.MettingTargetResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.ta.TargetResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.tr.TrendResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.tr.TrendSessionStatResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.tr.TrendStatResponseBean;
import org.hibernate.mapping.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

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
        List<TrendResponseBean> responseBeans = this._getTrendResponseBean(bean);
        List<TrendResponseBean> list = trendAnalysisMapper.summationCallout(bean);
        if (responseBeans != null && !list.isEmpty()) {
            TrendResponseBean t = null;
            for (TrendResponseBean trend : responseBeans) {
                t = this._getTrendResponseBean(list, bean.getDateType(), trend);
                if (t != null) {
                    trend.setNum(t.getNum());
                    trend.setCount(t.getCount());
                }
            }
        }
        Collections.sort(responseBeans, new Comparator<TrendResponseBean>() {

            @Override
            public int compare(TrendResponseBean o1, TrendResponseBean o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
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
        List<TrendResponseBean> responseBeans = this._getTrendResponseBean(bean);
        List<TrendResponseBean> list = trendAnalysisMapper.summationCalloutAvg(bean);
        if (responseBeans != null && !list.isEmpty()) {
            TrendResponseBean t = null;
            for (TrendResponseBean trend : responseBeans) {
                t = this._getTrendResponseBean(list, bean.getDateType(), trend);
                if (t != null) {
                    trend.setNum(t.getNum());
                    trend.setCount(t.getCount());
                }
            }
        }
        Collections.sort(responseBeans, new Comparator<TrendResponseBean>() {

            @Override
            public int compare(TrendResponseBean o1, TrendResponseBean o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return responseBeans;
    }


    /**
     * 呼出次数
     *
     * @param bean
     * @return
     */
    public List<TrendResponseBean> summationCalloutCount(TrendAnalysisRequestBean bean) {
        bean.checkDate();
        List<TrendResponseBean> responseBeans = this._getTrendResponseBean(bean);
        List<TrendResponseBean> list = trendAnalysisMapper.summationCalloutCount(bean);
        if (responseBeans != null && !list.isEmpty()) {
            TrendResponseBean t = null;
            for (TrendResponseBean trend : responseBeans) {
                t = this._getTrendResponseBean(list, bean.getDateType(), trend);
                if (t != null) {
                    trend.setNum(t.getNum());
                    trend.setCount(t.getCount());
                }
            }
        }
        Collections.sort(responseBeans, new Comparator<TrendResponseBean>() {

            @Override
            public int compare(TrendResponseBean o1, TrendResponseBean o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
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
        List<TrendResponseBean> responseBeans = this._getTrendResponseBean(bean);
//        Target target = targetService.findFirstByProductIdAndLevel(bean.getProductId(), bean.getCustomLevel());
//        List<TrendResponseBean> list = trendAnalysisMapper.summationCover(bean);
//        if(responseBeans!=null && !list.isEmpty()){
//            for (TrendResponseBean trend:responseBeans) {
//                trend.setNum(this._getNum(list,bean.getDateType(),trend));
//            }
//        }

        Collections.sort(responseBeans, new Comparator<TrendResponseBean>() {

            @Override
            public int compare(TrendResponseBean o1, TrendResponseBean o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return responseBeans;
    }

    /**
     * 会话
     *
     * @param bean
     * @return
     */
    public List<TrendSessionStatResponseBean> summationSession(TrendAnalysisRequestBean bean) {
        bean.checkDate();
        List<TrendSessionStatResponseBean> responseBeans = new ArrayList<>();
        List<TrendResponseBean> list = this._getTrendResponseBean(bean);
        List<TrendResponseBean> wechat = trendAnalysisMapper.summationSessionType1(bean);
        List<TrendResponseBean> sms = trendAnalysisMapper.summationSessionType2(bean);
        List<TrendResponseBean> email = trendAnalysisMapper.summationSessionType3(bean);

        if (list != null && !list.isEmpty()) {
            for (TrendResponseBean trend : list) {
                TrendSessionStatResponseBean responseBean = new TrendSessionStatResponseBean();
                responseBean.setDate(trend.getDate());
                responseBean.setDay(trend.getDay());
                responseBean.setMonth(trend.getMonth());
                responseBean.setYear(trend.getYear());
                responseBean.setQuarter(trend.getQuarter());
                responseBean.setWeek(trend.getWeek());

                TrendResponseBean trendwechat = this._getTrendResponseBean(wechat, bean.getDateType(), trend);
                if (trendwechat != null) {
                    responseBean.setWechat(trendwechat.getNum());
                    responseBean.setWechatCount(trendwechat.getCount());
                }
                TrendResponseBean trendemail = this._getTrendResponseBean(email, bean.getDateType(), trend);
                if (trendemail != null) {
                    responseBean.setEmailCount(trendemail.getCount());
                    responseBean.setEmail(trendemail.getNum());
                }
                TrendResponseBean trendsms = this._getTrendResponseBean(sms, bean.getDateType(), trend);
                if (trendsms != null) {
                    responseBean.setSms(trendsms.getNum());
                    responseBean.setSmsCount(trendsms.getCount());
                }

                responseBeans.add(responseBean);
            }
        }

        Collections.sort(responseBeans, new Comparator<TrendSessionStatResponseBean>() {

            @Override
            public int compare(TrendSessionStatResponseBean o1, TrendSessionStatResponseBean o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return responseBeans;
    }

    private TrendResponseBean _getTrendResponseBean(List<TrendResponseBean> list, Integer type, TrendResponseBean trend) {
        TrendResponseBean responseBean = new TrendResponseBean();
        if (list != null && !list.isEmpty()) {
            for (TrendResponseBean bean : list) {
                responseBean.setNum(bean.getNum());
                responseBean.setCount(bean.getCount());
                if (type == 1) {
                    if (trend.getDate().equals(bean.getDate())) {
                        return responseBean;
                    }
                } else if (type == 2) {
                    if (bean.getYear().equals(trend.getYear()) && bean.getWeek().equals(trend.getWeek())) {
                        return responseBean;
                    }
                } else if (type == 3) {
                    if (bean.getYear().equals(trend.getYear()) && bean.getMonth().equals(trend.getMonth())) {
                        return responseBean;
                    }
                } else if (type == 4) {
                    if (bean.getYear().equals(trend.getYear()) && bean.getQuarter().equals(trend.getQuarter())) {
                        return responseBean;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 呼出数
     *
     * @param bean
     * @return
     */
    public List<TrendStatResponseBean> callOut(TrendAnalysisRequestBean bean) {
        //TODO 获取时间
        bean.checkDate();
        int count = this._computationsDateNum(DateUtil.getDateFromStr(bean.getStartDate()),DateUtil.getDateFromStr(bean.getEndDate()));
//        bean.setEndDate(bean.getDate() + " 23:59:59");
//        bean.setStartDate(bean.getDate() + " 00:00:00");
        List<TrendStatResponseBean> responseBeans = new ArrayList<>();
        List<TrendStatResponseBean> connect = trendAnalysisMapper.callOut(bean);
        List<TrendStatResponseBean> callout = trendAnalysisMapper.callOutCount(bean);
        for (int i = 0; i < 24; i++) {
            TrendStatResponseBean responseBean = new TrendStatResponseBean();
            responseBean.setHour(i);
            if (connect != null && !connect.isEmpty()) {
                for (TrendStatResponseBean stat : connect) {
                    if (stat.getHour() != null && stat.getHour().intValue() == i) {
                        responseBean.setConnect(stat.getConnect()/count);
                    }
                }
            }
            if (callout != null && !callout.isEmpty()) {
                for (TrendStatResponseBean stat : callout) {
                    if (stat.getHour() != null && stat.getHour().intValue() == i) {
                        responseBean.setCallout(stat.getCallout()/count);
                    }
                }
            }
            responseBeans.add(responseBean);
        }
        Collections.sort(responseBeans, new Comparator<TrendStatResponseBean>() {

            @Override
            public int compare(TrendStatResponseBean o1, TrendStatResponseBean o2) {
                return o1.getHour().compareTo(o2.getHour());
            }
        });
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
        bean.checkDate();
        int count = this._computationsDateNum(DateUtil.getDateFromStr(bean.getStartDate()),DateUtil.getDateFromStr(bean.getEndDate()));
//        bean.setEndDate(bean.getDate() + " 23:59:59");
//        bean.setStartDate(bean.getDate() + " 00:00:00");
        List<TrendStatResponseBean> responseBeans = new ArrayList<>();
        List<TrendStatResponseBean> wechat = trendAnalysisMapper.sessionType1(bean);
        List<TrendStatResponseBean> sms = trendAnalysisMapper.sessionType2(bean);
        List<TrendStatResponseBean> email = trendAnalysisMapper.sessionType3(bean);
        for (int i = 0; i < 24; i++) {
            TrendStatResponseBean responseBean = new TrendStatResponseBean();
            responseBean.setHour(i);
            if (wechat != null && !wechat.isEmpty()) {
                for (TrendStatResponseBean stat : wechat) {
                    if (stat.getHour() != null && stat.getHour().intValue() == i) {
                        responseBean.setWechat(stat.getWechat()/count);
                        responseBean.setWechatCount(stat.getWechatCount()/count);
                    }
                }
            }
            if (sms != null && !sms.isEmpty()) {
                for (TrendStatResponseBean stat : sms) {
                    if (stat.getHour() != null && stat.getHour().intValue() == i) {
                        responseBean.setSms(stat.getSms()/count);
                        responseBean.setSmsCount(stat.getSmsCount()/count);
                    }
                }
            }
            if (email != null && !email.isEmpty()) {
                for (TrendStatResponseBean stat : email) {
                    if (stat.getHour() != null && stat.getHour().intValue() == i) {
                        responseBean.setEmail(stat.getEmail()/count);
                        responseBean.setEmailCount(stat.getEmailCount()/count);
                    }
                }
            }

            responseBeans.add(responseBean);
        }
        Collections.sort(responseBeans, new Comparator<TrendStatResponseBean>() {

            @Override
            public int compare(TrendStatResponseBean o1, TrendStatResponseBean o2) {
                return o1.getHour().compareTo(o2.getHour());
            }
        });
        return responseBeans;
    }

    private int _computationsDateNum(Date start, Date end) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(start);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(end);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
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
                responseBean.setQuarter(((int) calendar.get(Calendar.MONTH) / 3) + 1);
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
                responseBean.setQuarter(((int) calendar.get(Calendar.MONTH) / 3) + 1);
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
                responseBean.setQuarter(((int) calendar.get(Calendar.MONTH) / 3) + 1);
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
                responseBean.setQuarter(((int) calendar.get(Calendar.MONTH) / 3) + 1);
                responseBeans.add(responseBean);
                calendar.add(Calendar.MONTH, +3);
            }
        }

        return responseBeans;
    }
}
