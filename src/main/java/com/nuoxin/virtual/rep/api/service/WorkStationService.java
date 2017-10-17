package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.dao.TargetRepository;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.Target;
import com.nuoxin.virtual.rep.api.mybatis.*;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.WorkStationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.*;
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
    private WorkStationMapper workStationMapper;

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
        Integer perDayTargetWechatCount = 0;
        Integer perDayTargetImSum = 0;
        Integer perDayTargetImCount = 0;
        Integer perDayTargetEmailNum = 0;
        Integer perDayTargetEmailCount = 0;
        Integer perDayTargetCallSum = 0;
        Integer perDayTargetCallCount = 0;
        Integer perDayTargetMeetingSum = 0;
        Integer perDayTargetMeetingTime = 0;
        Integer perDayTargetMeetingPersonSum = 0;
        Integer perDayTargetMeetingPersonCount = 0;

        Map<String, Integer> perDayTarget = getPerDayTarget(bean.getProductId());
        perDayTargetWechatNum = perDayTarget.get("perDayTargetWechatNum");
        perDayTargetWechatCount = perDayTarget.get("perDayTargetWechatCount");
        perDayTargetImSum = perDayTarget.get("perDayTargetImSum");
        perDayTargetImCount = perDayTarget.get("perDayTargetImCount");
        perDayTargetEmailNum = perDayTarget.get("perDayTargetEmailNum");
        perDayTargetEmailCount = perDayTarget.get("perDayTargetEmailCount");
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

        //目标人次没有微信的
        Integer targetVisitCount = perDayTargetMeetingPersonCount + perDayTargetCallCount + perDayTargetWechatCount + perDayTargetImCount + perDayTargetEmailCount;
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

        DrugUser drugUser = drugUserRepository.findFirstById(bean.getDrugUserId());
        String leaderPath = drugUser.getLeaderPath();
        if (leaderPath == null) {
            leaderPath = "";
        }
        bean.setLeaderPath(leaderPath + "%");

        MonthTargetStatisticResponseBean monthTargetStatistic = new MonthTargetStatisticResponseBean();

        Map<String, Integer> monthTarget = getMonthTarget(bean.getProductId());
        Integer monthTargetMeetingPersonSum = monthTarget.get("monthTargetMeetingPersonSum");
        Integer monthTargetMeetingPersonCount = monthTarget.get("monthTargetMeetingPersonCount");
        Integer monthTargetMeetingTime = monthTarget.get("monthTargetMeetingTime");
        Integer monthTargetWechatNum = monthTarget.get("monthTargetWechatNum");
        Integer monthTargetWechatCount = monthTarget.get("monthTargetWechatCount");
        Integer monthTargetImSum = monthTarget.get("monthTargetImSum");
        Integer monthTargetImCount = monthTarget.get("monthTargetImCount");
        Integer monthTargetEmailNum = monthTarget.get("monthTargetEmailNum");
        Integer monthTargetEmailCount = monthTarget.get("monthTargetEmailCount");
        Integer monthTargetCallSum = monthTarget.get("monthTargetCallSum");
        Integer monthTargetCallCount = monthTarget.get("monthTargetCallCount");
        Integer monthTargetCallTime = monthTarget.get("monthTargetCallTime");

        monthTargetStatistic.setTargetMeetingSum(monthTargetMeetingPersonSum);
        monthTargetStatistic.setTargetMeetingTotalTime(monthTargetMeetingTime);
        monthTargetStatistic.setTargetMessageSum(monthTargetWechatNum + monthTargetImSum + monthTargetEmailNum);
        monthTargetStatistic.setTargetMessageCount(monthTargetWechatCount + monthTargetImCount + monthTargetEmailCount);
        monthTargetStatistic.setTargetCallSum(monthTargetCallSum);
        monthTargetStatistic.setTargetCallCount(monthTargetCallCount);
        monthTargetStatistic.setTargetCallTime(monthTargetCallTime);
        monthTargetStatistic.setTargetVisitSum(monthTargetMeetingPersonSum + monthTargetWechatNum + monthTargetImSum + monthTargetEmailNum);

        monthTargetStatistic.setTargetVisitCount(monthTargetMeetingPersonCount + monthTargetWechatCount + monthTargetImCount + monthTargetEmailCount);

        return monthTargetStatistic;
    }


    /**
     * 客户总数统计
     * @param bean
     * @return
     */
    public List<CustomerSumResponseBean> getTotalCustomerStatistic(WorkStationRequestBean bean){

        DrugUser drugUser = drugUserRepository.findFirstById(bean.getDrugUserId());
        String leaderPath = drugUser.getLeaderPath();
        if (leaderPath == null) {
            leaderPath = "";
        }
        bean.setLeaderPath(leaderPath + "%");
        List<CustomerSumResponseBean> totalCustomerStatistic = workStationMapper.getTotalCustomerStatistic(bean);

        return totalCustomerStatistic;
    }


    /**
     * 当月新增客户统计
     * @param bean
     * @return
     */
    public List<CustomerSumResponseBean> getAddCustomerStatistic(WorkStationRequestBean bean){

        DrugUser drugUser = drugUserRepository.findFirstById(bean.getDrugUserId());
        String leaderPath = drugUser.getLeaderPath();
        if (leaderPath == null) {
            leaderPath = "";
        }
        bean.setLeaderPath(leaderPath + "%");
        List<CustomerSumResponseBean> addCustomerStatistic = workStationMapper.getAddCustomerStatistic(bean);
        return addCustomerStatistic;
    }

    /**
     * 当月覆盖客户总数统计
     * @param bean
     * @return
     */
    public List<CustomerSumResponseBean> getCoverCustomerStatistic(WorkStationRequestBean bean){
        DrugUser drugUser = drugUserRepository.findFirstById(bean.getDrugUserId());
        String leaderPath = drugUser.getLeaderPath();
        if (leaderPath == null) {
            leaderPath = "";
        }
        bean.setLeaderPath(leaderPath + "%");
        List<CustomerSumResponseBean> coverCustomerStatistic = workStationMapper.getCoverCustomerStatistic(bean);
        return coverCustomerStatistic;
    }


    /**
     * 连续一个月未跟进客户
     * @param bean
     * @return
     */
    public PageResponseBean<OneMonthNoFollowCustomerResponseBean> getOneMonthNoFollowCustomers(WorkStationRequestBean bean){
        DrugUser drugUser = drugUserRepository.findFirstById(bean.getDrugUserId());
        String leaderPath = drugUser.getLeaderPath();
        if (leaderPath == null) {
            leaderPath = "";
        }
        bean.setLeaderPath(leaderPath + "%");
        Integer page = bean.getPage();
        Integer pageSize = bean.getPageSize();
        bean.setPage(page  * pageSize);

        List<OneMonthNoFollowCustomerResponseBean> oneMonthNoFollowCustomerList = workStationMapper.getOneMonthNoFollowCustomerList(bean);
        Integer count = workStationMapper.getOneMonthNoFollowCustomerListCount(bean);


        PageResponseBean<OneMonthNoFollowCustomerResponseBean> pageResponseBean = new PageResponseBean<>(bean, count, oneMonthNoFollowCustomerList);

        return pageResponseBean;
    }

    /**
     * 本月目标
     * @return
     */
    private Map<String, Integer> getMonthTarget(Long productId){
        Map<String, Integer> map = new HashMap<>();
        Integer monthTargetWechatNum = 0;
        Integer monthTargetWechatCount = 0;
        Integer monthTargetImSum = 0;
        Integer monthTargetImCount = 0;
        Integer monthTargetEmailNum = 0;
        Integer monthTargetEmailCount = 0;
        Integer monthTargetCallSum = 0;
        Integer monthTargetCallCount = 0;
        Integer monthTargetCallTime = 0;
        Integer monthTargetMeetingSum = 0;
        Integer monthTargetMeetingTime = 0;
        Integer monthTargetMeetingPersonSum = 0;
        Integer monthTargetMeetingPersonCount = 0;
        List<Target> targets = targetRepository.findByProductId(productId);
        if (targets != null && !targets.isEmpty()){
            for (Target target:targets){
                if (target != null){
                    monthTargetWechatNum += target.getMonthWechatNum();
                    monthTargetWechatCount += target.getMonthWechatCount();
                    monthTargetImSum += target.getMonthImNum();
                    monthTargetImCount += target.getMonthImCount();
                    monthTargetEmailNum += target.getMonthEmailNum();
                    monthTargetEmailCount += target.getMonthEmailCount();
                    monthTargetCallSum += target.getMonthTelPerson();
                    monthTargetCallCount += target.getMonthTelNum();
                    monthTargetCallTime += target.getMonthTelTime();
                    monthTargetMeetingSum += target.getMonthMeetingNum();
                    monthTargetMeetingTime += target.getMonthMeetingTime();
                    monthTargetMeetingPersonSum += target.getMonthMeetingPersonSum();
                    monthTargetMeetingPersonCount += target.getMonthMeetingPersonCount();
                }
            }
        }

        map.put("monthTargetWechatNum", monthTargetWechatNum);
        map.put("monthTargetWechatCount", monthTargetWechatCount);
        map.put("monthTargetImSum", monthTargetImSum);
        map.put("monthTargetImCount", monthTargetImCount);
        map.put("monthTargetEmailNum", monthTargetEmailNum);
        map.put("monthTargetEmailCount", monthTargetEmailCount);
        map.put("monthTargetCallSum", monthTargetCallSum);
        map.put("monthTargetCallCount", monthTargetCallCount);
        map.put("monthTargetCallTime", monthTargetCallTime);
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
        Integer perDayTargetWechatCount = 0;
        Integer perDayTargetImSum = 0;
        Integer perDayTargetImCount = 0;
        Integer perDayTargetEmailNum = 0;
        Integer perDayTargetEmailCount = 0;
        Integer perDayTargetCallSum = 0;
        Integer perDayTargetCallCount = 0;
        Integer perDayTargetCallTime = 0;
        Integer perDayTargetMeetingSum = 0;
        Integer perDayTargetMeetingTime = 0;
        Integer perDayTargetMeetingPersonSum = 0;
        Integer perDayTargetMeetingPersonCount = 0;

        //当前月的天数
        Integer month = DateUtil.getCurrentMonthLastDay();
        if (month == null || month == 0){
            month = 1;
        }

        Integer monthTargetWechatNum = monthTarget.get("monthTargetWechatNum");
        Integer monthTargetWechatCount = monthTarget.get("monthTargetWechatCount");
        Integer monthTargetImSum = monthTarget.get("monthTargetImSum");
        Integer monthTargetImCount = monthTarget.get("monthTargetImCount");
        Integer monthTargetEmailNum = monthTarget.get("monthTargetEmailNum");
        Integer monthTargetEmailCount = monthTarget.get("monthTargetEmailCount");
        Integer monthTargetCallSum = monthTarget.get("monthTargetCallSum");
        Integer monthTargetCallCount = monthTarget.get("monthTargetCallCount");
        Integer monthTargetCallTime = monthTarget.get("monthTargetCallTime");
        Integer monthTargetMeetingSum = monthTarget.get("monthTargetMeetingSum");
        Integer monthTargetMeetingTime = monthTarget.get("monthTargetMeetingTime");
        Integer monthTargetMeetingPersonSum = monthTarget.get("monthTargetMeetingPersonSum");
        Integer monthTargetMeetingPersonCount = monthTarget.get("monthTargetMeetingPersonCount");

        perDayTargetWechatNum = Math.round(monthTargetWechatNum/month);
        perDayTargetWechatCount = Math.round(monthTargetWechatCount/month);
        perDayTargetImSum = Math.round(monthTargetImSum/month);
        perDayTargetImCount = Math.round(monthTargetImCount/month);
        perDayTargetEmailNum = Math.round(monthTargetEmailNum/month);
        perDayTargetEmailCount = Math.round(monthTargetEmailCount/month);
        perDayTargetCallSum = Math.round(monthTargetCallSum/month);
        perDayTargetCallCount = Math.round(monthTargetCallCount/month);
        perDayTargetCallTime = Math.round(monthTargetCallTime/month);
        perDayTargetMeetingSum = Math.round(monthTargetMeetingSum/month);
        perDayTargetMeetingTime = Math.round(monthTargetMeetingTime/month);
        perDayTargetMeetingPersonSum = Math.round(monthTargetMeetingPersonSum/month);
        perDayTargetMeetingPersonCount = Math.round(monthTargetMeetingPersonCount/month);


        map.put("perDayTargetWechatNum", perDayTargetWechatNum);
        map.put("perDayTargetWechatCount", perDayTargetWechatCount);
        map.put("perDayTargetImSum", perDayTargetImSum);
        map.put("perDayTargetImCount", perDayTargetImCount);
        map.put("perDayTargetEmailNum", perDayTargetEmailNum);
        map.put("perDayTargetEmailCount", perDayTargetEmailCount);
        map.put("perDayTargetCallSum", perDayTargetCallSum);
        map.put("perDayTargetCallCount", perDayTargetCallCount);
        map.put("perDayTargetCallTime", perDayTargetCallTime);
        map.put("perDayTargetMeetingSum", perDayTargetMeetingSum);
        map.put("perDayTargetMeetingTime", perDayTargetMeetingTime);
        map.put("perDayTargetMeetingPersonSum", perDayTargetMeetingPersonSum);
        map.put("perDayTargetMeetingPersonCount", perDayTargetMeetingPersonCount);

        return map;

    }


}
