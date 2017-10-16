package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.dao.TargetRepository;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.Target;
import com.nuoxin.virtual.rep.api.mybatis.DoctorCallInfoMapper;
import com.nuoxin.virtual.rep.api.mybatis.EmailMapper;
import com.nuoxin.virtual.rep.api.mybatis.MeetingDetailMapper;
import com.nuoxin.virtual.rep.api.mybatis.MessageMapper;
import com.nuoxin.virtual.rep.api.web.controller.request.WorkStationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.CustomerStatisticResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.MonthTargetStatisticResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.TodayStatisticsResponseBean;
import com.sun.mail.util.BEncoderStream;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工作台相关
 * Create by tiancun on 2017/10/12
 */
@Service
public class WorkStationService {

    @Autowired
    private MeetingDetailMapper meetingDetailMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private DoctorCallInfoMapper doctorCallInfoMapper;

    @Autowired
    private EmailMapper emailMapper;

    @Autowired
    private DrugUserRepository drugUserRepository;

    @Autowired
    private TargetRepository targetRepository;

    /**
     * 今日通话情况
     * @param bean
     * @return
     */
    public TodayStatisticsResponseBean getTodayStatistic(WorkStationRequestBean bean){
        TodayStatisticsResponseBean todayStatisticsResponseBean = new TodayStatisticsResponseBean();

        DrugUser drugUser = drugUserRepository.findFirstById(bean.getDrugUserId());
        String leaderPath = drugUser.getLeaderPath();
        if (leaderPath == null) {
            leaderPath = "";
        }
        bean.setLeaderPath(leaderPath + "%");

        //会议的统计
        TodayStatisticsResponseBean meetingStatistic = meetingDetailMapper.getMeetingStatistic(bean);
        if (null != meetingStatistic){
            todayStatisticsResponseBean.setMeetingCount(meetingStatistic.getMeetingCount());
            todayStatisticsResponseBean.setMeetingTotalTime(meetingStatistic.getMeetingTotalTime());
            todayStatisticsResponseBean.setMeetingTotalPersonNum(meetingStatistic.getMeetingTotalPersonNum());
            todayStatisticsResponseBean.setMeetingAvgTime(meetingStatistic.getMeetingAvgTime());
        }

        //今日目标数据
        Integer perDayTargetWechatNum = 0;
        Integer perDayTargetImSum = 0;
        Integer perDayTargetEmailNum = 0;
        Integer perDayTargetCallSum = 0;
        Integer perDayTargetCallCount = 0;
        Integer perDayTargetMeetingSum = 0;
        Integer perDayTargetMeetingTime = 0;
        Integer perDayTargetMeetingPersonSum = 0;
        Integer perDayTargetMeetingPersonCount = 0;

        Map<String, Integer> perDayTarget = getPerDayTarget(bean.getProductId());
        perDayTargetWechatNum = perDayTarget.get("perDayTargetWechatNum");
        perDayTargetImSum = perDayTarget.get("perDayTargetImSum");
        perDayTargetEmailNum = perDayTarget.get("perDayTargetEmailNum");
        perDayTargetCallSum = perDayTarget.get("perDayTargetCallSum");
        perDayTargetCallCount = perDayTarget.get("perDayTargetCallCount");
        perDayTargetMeetingSum = perDayTarget.get("perDayTargetMeetingSum");
        perDayTargetMeetingTime = perDayTarget.get("perDayTargetMeetingTime");
        perDayTargetMeetingPersonSum = perDayTarget.get("perDayTargetMeetingPersonSum");
        perDayTargetMeetingPersonCount = perDayTarget.get("perDayTargetMeetingPersonCount");

        //多渠道会话统计
        TodayStatisticsResponseBean messageStatistics = messageMapper.getMessageStatistics(bean);
        if (null != meetingStatistic){
            todayStatisticsResponseBean.setWechatSum(meetingStatistic.getWechatSum());
            todayStatisticsResponseBean.setWechatCount(meetingStatistic.getWechatCount());
            todayStatisticsResponseBean.setImSum(meetingStatistic.getImSum());
            todayStatisticsResponseBean.setImCount(meetingStatistic.getImCount());
        }

        //今日邮件人数
        Integer todayEmailNum = emailMapper.getTodayEmailNum(bean);

        //今日邮件人次
        Integer todayEmailCount = emailMapper.getTodayEmailCount(bean);

        todayStatisticsResponseBean.setEmailSum(todayEmailNum);
        todayStatisticsResponseBean.setTargetWechatSum(perDayTargetWechatNum);
        todayStatisticsResponseBean.setTargetImSum(perDayTargetImSum);
        todayStatisticsResponseBean.setTargetEmailSum(perDayTargetEmailNum);


        //呼出统计
        TodayStatisticsResponseBean callInfoStatistics = doctorCallInfoMapper.getCallInfoStatistics(bean);
        if (null != callInfoStatistics){
            todayStatisticsResponseBean.setCallCount(callInfoStatistics.getCallCount());
            todayStatisticsResponseBean.setCallSum(callInfoStatistics.getCallSum());

        }

        todayStatisticsResponseBean.setTargetCallSum(perDayTargetCallSum);
        todayStatisticsResponseBean.setTargetCallCount(perDayTargetCallCount);

        TodayStatisticsResponseBean callInfoTimeStatistics = doctorCallInfoMapper.getCallInfoTimeStatistics(bean);
        if (null != callInfoStatistics){
            todayStatisticsResponseBean.setCallTotalTime(callInfoTimeStatistics.getCallTotalTime());
        }



        //拜访客户数
        Integer meetingTotalPerson = todayStatisticsResponseBean.getMeetingTotalPersonNum();
        if (meetingTotalPerson == null){
            meetingTotalPerson = 0;
        }
        Integer wechatSum = todayStatisticsResponseBean.getWechatSum();
        if (wechatSum == null){
            wechatSum = 0;
        }

        Integer imSum = todayStatisticsResponseBean.getImSum();
        if (imSum == null){
            imSum = 0;
        }


        Integer callSum = todayStatisticsResponseBean.getCallSum();
        if (null == callSum){
            callSum = 0;
        }
        //拜访客户总人数
        Integer visitTotalSum = meetingTotalPerson + wechatSum + imSum + todayEmailCount + callSum;
        todayStatisticsResponseBean.setVisitSum(visitTotalSum);

        //拜访客户总人次
        Integer meetingCount = todayStatisticsResponseBean.getMeetingCount();
        if (null == meetingCount){
            meetingCount = 0;
        }

        Integer wechatCount = todayStatisticsResponseBean.getWechatCount();
        if (null == wechatCount){
            wechatCount = 0;
        }

        Integer imCount = todayStatisticsResponseBean.getImCount();
        if (imCount == null){
            imCount = 0;
        }


        Integer callCount = todayStatisticsResponseBean.getCallCount();
        if (callCount == null){
            callCount = 0;
        }

        Integer visitCount = meetingCount + wechatCount + imCount + todayEmailCount + callCount;
        todayStatisticsResponseBean.setCallCount(visitCount);

        //目标人次没有短信，邮件，微信的
        Integer targetVisitCount = perDayTargetMeetingPersonCount + perDayTargetCallCount ;
        Integer targetVisitSum = perDayTargetMeetingPersonCount + perDayTargetCallSum + perDayTargetWechatNum + perDayTargetImSum + perDayTargetEmailNum;
        todayStatisticsResponseBean.setTargetVisitCount(targetVisitCount);
        todayStatisticsResponseBean.setTargetVisitSum(targetVisitSum);
        return todayStatisticsResponseBean;
    }


    /**
     * 本月目标
     * @param bean
     * @return
     */
    public MonthTargetStatisticResponseBean getMonthTargetStatistic(WorkStationRequestBean bean){


        return null;
    }


    /**
     * 客户总数统计
     * @param bean
     * @return
     */
    public List<CustomerStatisticResponseBean> getTotalCustomerStatistic(WorkStationRequestBean bean){


        return null;
    }


    /**
     * 当月新增客户统计
     * @param bean
     * @return
     */
    public List<CustomerStatisticResponseBean> getAddCustomerStatistic(WorkStationRequestBean bean){

        return null;
    }

    /**
     * 当月覆盖客户总数统计
     * @param bean
     * @return
     */
    public List<CustomerStatisticResponseBean> getCoverCustomerStatistic(WorkStationRequestBean bean){

        return null;
    }


    /**
     * 本月目标
     * @return
     */
    private Map<String, Integer> getMonthTarget(Long productId){
        Map<String, Integer> map = new HashMap<>();
        Integer monthTargetWechatNum = 0;
        Integer monthTargetImSum = 0;
        Integer monthTargetEmailNum = 0;
        Integer monthTargetCallSum = 0;
        Integer monthTargetCallCount = 0;
        Integer monthTargetMeetingSum = 0;
        Integer monthTargetMeetingTime = 0;
        Integer monthTargetMeetingPersonSum = 0;
        Integer monthTargetMeetingPersonCount = 0;
        List<Target> targets = targetRepository.findByProductId(productId);
        if (targets != null && !targets.isEmpty()){
            for (Target target:targets){
                if (target != null){
                    monthTargetWechatNum += target.getMonthWechatNum();
                    monthTargetImSum += target.getMonthImNum();
                    monthTargetEmailNum += target.getMonthEmailNum();
                    monthTargetCallSum += target.getMonthTelPerson();
                    monthTargetCallCount += target.getMonthTelNum();
                    monthTargetMeetingSum += target.getMonthMeetingNum();
                    monthTargetMeetingTime += target.getMonthMeetingTime();
                    monthTargetMeetingPersonSum += target.getMonthMeetingPersonSum();
                    monthTargetMeetingPersonCount += target.getMonthMeetingPersonCount();
                }
            }
        }

        map.put("monthTargetWechatNum", monthTargetWechatNum);
        map.put("monthTargetImSum", monthTargetImSum);
        map.put("monthTargetEmailNum", monthTargetEmailNum);
        map.put("monthTargetCallSum", monthTargetCallSum);
        map.put("monthTargetCallCount", monthTargetCallCount);
        map.put("monthTargetMeetingSum", monthTargetMeetingSum);
        map.put("monthTargetMeetingTime", monthTargetMeetingTime);
        map.put("monthTargetMeetingPersonSum", monthTargetMeetingPersonSum);
        map.put("monthTargetMeetingPersonCount", monthTargetMeetingPersonCount);


        return map;
    }

    /**
     * 每天目标
     * @return
     */
    private Map<String, Integer> getPerDayTarget(Long productId){
        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> monthTarget = getMonthTarget(productId);
        if (null == monthTarget || monthTarget.isEmpty()){
            return map;
        }

        Integer perDayTargetWechatNum = 0;
        Integer perDayTargetImSum = 0;
        Integer perDayTargetEmailNum = 0;
        Integer perDayTargetCallSum = 0;
        Integer perDayTargetCallCount = 0;
        Integer perDayTargetMeetingSum = 0;
        Integer perDayTargetMeetingTime = 0;
        Integer perDayTargetMeetingPersonSum = 0;
        Integer perDayTargetMeetingPersonCount = 0;

        //目前一月按照30天来算,四舍五入
        Integer month = 30;
        Integer monthTargetWechatNum = monthTarget.get("monthTargetWechatNum");
        Integer monthTargetImSum = monthTarget.get("monthTargetImSum");
        Integer monthTargetEmailNum = monthTarget.get("monthTargetEmailNum");
        Integer monthTargetCallSum = monthTarget.get("monthTargetCallSum");
        Integer monthTargetCallCount = monthTarget.get("monthTargetCallCount");
        Integer monthTargetMeetingSum = monthTarget.get("monthTargetMeetingSum");
        Integer monthTargetMeetingTime = monthTarget.get("monthTargetMeetingTime");
        Integer monthTargetMeetingPersonSum = monthTarget.get("monthTargetMeetingPersonSum");
        Integer monthTargetMeetingPersonCount = monthTarget.get("monthTargetMeetingPersonCount");

        perDayTargetWechatNum = Math.round(monthTargetWechatNum/month);
        perDayTargetImSum = Math.round(monthTargetImSum/month);
        perDayTargetEmailNum = Math.round(monthTargetEmailNum/month);
        perDayTargetCallSum = Math.round(monthTargetCallSum/month);
        perDayTargetCallCount = Math.round(monthTargetCallCount/month);
        perDayTargetMeetingSum = Math.round(monthTargetMeetingSum/month);
        perDayTargetMeetingTime = Math.round(monthTargetMeetingTime/month);
        perDayTargetMeetingPersonSum = Math.round(monthTargetMeetingPersonSum/month);
        perDayTargetMeetingPersonCount = Math.round(monthTargetMeetingPersonCount/month);


        map.put("perDayTargetWechatNum", perDayTargetWechatNum);
        map.put("perDayTargetImSum", perDayTargetImSum);
        map.put("perDayTargetEmailNum", perDayTargetEmailNum);
        map.put("perDayTargetCallSum", perDayTargetCallSum);
        map.put("perDayTargetCallCount", perDayTargetCallCount);
        map.put("perDayTargetMeetingSum", perDayTargetMeetingSum);
        map.put("perDayTargetMeetingTime", perDayTargetMeetingTime);
        map.put("perDayTargetMeetingPersonSum", perDayTargetMeetingPersonSum);
        map.put("perDayTargetMeetingPersonCount", perDayTargetMeetingPersonCount);

        return map;

    }


}
