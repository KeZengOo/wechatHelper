package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.mybatis.DoctorCallInfoMapper;
import com.nuoxin.virtual.rep.api.mybatis.MeetingDetailMapper;
import com.nuoxin.virtual.rep.api.mybatis.MessageMapper;
import com.nuoxin.virtual.rep.api.web.controller.request.WorkStationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.TodayStatisticsResponseBean;
import com.sun.mail.util.BEncoderStream;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public TodayStatisticsResponseBean getTodayStatistic(WorkStationRequestBean bean){
        TodayStatisticsResponseBean todayStatisticsResponseBean = new TodayStatisticsResponseBean();

        bean.setDrugUserIdStr("%,"+ bean.getDrugUserId() + ",%");
        //会议的统计
        TodayStatisticsResponseBean meetingStatistic = meetingDetailMapper.getMeetingStatistic(bean);
        if (null != meetingStatistic){
            todayStatisticsResponseBean.setMeetingCount(meetingStatistic.getMeetingCount());
            todayStatisticsResponseBean.setMeetingTotalTime(meetingStatistic.getMeetingTotalTime());
            todayStatisticsResponseBean.setMeetingTotalPerson(meetingStatistic.getMeetingTotalPerson());
            todayStatisticsResponseBean.setMeetingAvgTime(meetingStatistic.getMeetingAvgTime());
        }

        //多渠道会话统计
        TodayStatisticsResponseBean messageStatistics = messageMapper.getMessageStatistics(bean);
        if (null != meetingStatistic){
            todayStatisticsResponseBean.setWechatSum(meetingStatistic.getWechatSum());
            todayStatisticsResponseBean.setWechatCount(meetingStatistic.getWechatCount());
            todayStatisticsResponseBean.setImSum(meetingStatistic.getImSum());
            todayStatisticsResponseBean.setImCount(meetingStatistic.getImCount());
            //目标和邮件的暂时没有

        }

        //呼出统计
        TodayStatisticsResponseBean callInfoStatistics = doctorCallInfoMapper.getCallInfoStatistics(bean);
        if (null != callInfoStatistics){
            todayStatisticsResponseBean.setCallCount(callInfoStatistics.getCallCount());
            todayStatisticsResponseBean.setCallSum(callInfoStatistics.getCallSum());
            //目标人数和人次暂时没有

        }

        TodayStatisticsResponseBean callInfoTimeStatistics = doctorCallInfoMapper.getCallInfoTimeStatistics(bean);
        if (null != callInfoStatistics){
            todayStatisticsResponseBean.setCallTotalTime(callInfoTimeStatistics.getCallTotalTime());
        }



        //拜访客户数，暂时没有邮件的和目标客户数
        Integer meetingTotalPerson = todayStatisticsResponseBean.getMeetingTotalPerson();
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

        //暂时没有邮件的人数

        Integer callSum = todayStatisticsResponseBean.getCallSum();
        if (null == callSum){
            callSum = 0;
        }
        //拜访客户总数
        Integer visitTotalSum = meetingTotalPerson + wechatSum + imSum + callSum;
        todayStatisticsResponseBean.setVisitSum(visitTotalSum);

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

        //暂时没有邮件的人次

        Integer callCount = todayStatisticsResponseBean.getCallCount();
        if (callCount == null){
            callCount = 0;
        }

        Integer visitCount = meetingCount + wechatCount + imCount + callCount;
        todayStatisticsResponseBean.setCallCount(visitCount);

        //暂时没有拜访客户的目标次数

        return todayStatisticsResponseBean;
    }

}
