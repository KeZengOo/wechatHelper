package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.dao.DropTargetRepository;
import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.dao.TargetRepository;
import com.nuoxin.virtual.rep.api.entity.DropTarget;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.Target;
import com.nuoxin.virtual.rep.api.enums.DateTypeEnum;
import com.nuoxin.virtual.rep.api.mybatis.*;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.WorkStationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.ta.TargetResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.*;
import com.sun.mail.util.BEncoderStream;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.ArrayList;
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

    @Autowired
    private DropTargetRepository dropTargetRepository;


    /**
     * 得到管理员下所有的坐席
     * @param drugUserId
     * @return
     */
    public List<DrugUserResponseBean> getDrugUserList(Long drugUserId){
        List<DrugUserResponseBean> list = new ArrayList<>();

        DrugUser drugUser = drugUserRepository.findFirstById(drugUserId);
        String leaderPath = drugUser.getLeaderPath();
        if (leaderPath == null) {
            leaderPath = "";
        }
        leaderPath = leaderPath + "%";

        List<DrugUser> drugUsers = drugUserRepository.findByLeaderPathLike(leaderPath);
        if (drugUsers != null && !drugUsers.isEmpty()){
            for (DrugUser du:drugUsers){
                DrugUserResponseBean drugUserResponseBean = new DrugUserResponseBean();
                drugUserResponseBean.setId(du.getId());
                drugUserResponseBean.setName(du.getName());
                list.add(drugUserResponseBean);
            }
        }

        return list;
    }


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
            Integer meetingCount = meetingStatistic.getMeetingCount();
            if (meetingCount == null){
                meetingCount = 0;
            }
            todayStatisticsResponseBean.setMeetingCount(meetingCount);


            Integer meetingTotalTime = meetingStatistic.getMeetingTotalTime();
            if (meetingTotalTime == null){
                meetingTotalTime = 0;
            }
            todayStatisticsResponseBean.setMeetingTotalTime(meetingTotalTime);

            Integer meetingTotalPersonNum = meetingStatistic.getMeetingTotalPersonNum();
            if (meetingTotalPersonNum == null){
                meetingTotalPersonNum = 0;
            }
            todayStatisticsResponseBean.setMeetingTotalPersonNum(meetingTotalPersonNum);

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

        DayTargetResponseBean perDayTarget = getPerDayTarget(bean.getProductId());
        perDayTargetWechatNum = perDayTarget.getDayTargetWechatNum();
        perDayTargetWechatCount = perDayTarget.getDayTargetWechatCount();
        perDayTargetImSum = perDayTarget.getDayTargetImSum();
        perDayTargetImCount = perDayTarget.getDayTargetImCount();
        perDayTargetEmailNum = perDayTarget.getDayTargetEmailNum();
        perDayTargetEmailCount = perDayTarget.getDayTargetEmailCount();
        perDayTargetCallSum = perDayTarget.getDayTargetCallSum();
        perDayTargetCallCount = perDayTarget.getDayTargetCallCount();
        perDayTargetMeetingSum = perDayTarget.getDayTargetMeetingSum();
        perDayTargetMeetingTime = perDayTarget.getDayTargetMeetingTime();
        perDayTargetMeetingPersonSum = perDayTarget.getDayTargetMeetingPersonSum();
        perDayTargetMeetingPersonCount = perDayTarget.getDayTargetMeetingPersonCount();

        //多渠道会话统计
        TodayStatisticsResponseBean messageStatistics = messageMapper.getMessageStatistics(bean);
        if (null != meetingStatistic){

            Integer wechatSum = meetingStatistic.getWechatSum();
            if (wechatSum == null){
                wechatSum = 0;
            }
            todayStatisticsResponseBean.setWechatSum(wechatSum);


            Integer wechatCount = meetingStatistic.getWechatCount();
            if (wechatCount == null){
                wechatCount = 0;
            }
            todayStatisticsResponseBean.setWechatCount(wechatCount);


            Integer imSum = meetingStatistic.getImSum();
            if (imSum == null){
                imSum = 0;
            }
            todayStatisticsResponseBean.setImSum(imSum);


            Integer imCount = meetingStatistic.getImCount();
            if (imCount == null){
                imCount = 0;
            }
            todayStatisticsResponseBean.setImCount(imCount);
        }

        //今日邮件人数
        Integer todayEmailNum = emailMapper.getTodayEmailNum(bean);
        if (todayEmailNum == null){
            todayEmailNum = 0;
        }

        //今日邮件人次
        Integer todayEmailCount = emailMapper.getTodayEmailCount(bean);
        if (todayEmailCount == null){
            todayEmailCount = 0;
        }

        todayStatisticsResponseBean.setEmailSum(todayEmailNum);
        todayStatisticsResponseBean.setTargetWechatSum(perDayTargetWechatNum);
        todayStatisticsResponseBean.setTargetImSum(perDayTargetImSum);
        todayStatisticsResponseBean.setTargetEmailSum(perDayTargetEmailNum);


        //呼出统计
        TodayStatisticsResponseBean callInfoStatistics = doctorCallInfoMapper.getCallInfoStatistics(bean);
        if (null != callInfoStatistics){
            Integer callCount = callInfoStatistics.getCallCount();
            if (callCount == null){
                callCount = 0;
            }
            todayStatisticsResponseBean.setCallCount(callCount);


            Integer callSum = callInfoStatistics.getCallSum();
            if (callSum == null){
                callSum = 0;
            }
            todayStatisticsResponseBean.setCallSum(callSum);

        }

        todayStatisticsResponseBean.setTargetCallSum(perDayTargetCallSum);
        todayStatisticsResponseBean.setTargetCallCount(perDayTargetCallCount);

        TodayStatisticsResponseBean callInfoTimeStatistics = doctorCallInfoMapper.getCallInfoTimeStatistics(bean);
        if (null != callInfoStatistics){
            Integer callTotalTime = callInfoTimeStatistics.getCallTotalTime();
            if (callTotalTime == null){
                callTotalTime = 0;
            }
            todayStatisticsResponseBean.setCallTotalTime(callTotalTime);
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
        Integer visitTotalSum = meetingTotalPerson + wechatSum + imSum + todayEmailNum + callSum;
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
        todayStatisticsResponseBean.setVisitCount(visitCount);

        //目标人次没有微信的
        Integer targetVisitCount = perDayTargetMeetingPersonCount + perDayTargetCallCount + perDayTargetWechatCount + perDayTargetImCount + perDayTargetEmailCount;
        Integer targetVisitSum = perDayTargetMeetingPersonSum + perDayTargetCallSum + perDayTargetWechatNum + perDayTargetImSum + perDayTargetEmailNum;
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


        MonthTargetResponseBean monthTarget = getMonthTarget(bean.getProductId());
        Integer monthTargetWechatNum = monthTarget.getMonthTargetWechatNum();
        Integer monthTargetWechatCount = monthTarget.getMonthTargetWechatCount();
        Integer monthTargetImSum = monthTarget.getMonthTargetImSum();
        Integer monthTargetImCount = monthTarget.getMonthTargetImCount();
        Integer monthTargetEmailNum = monthTarget.getMonthTargetEmailNum();
        Integer monthTargetEmailCount = monthTarget.getMonthTargetEmailCount();
        Integer monthTargetCallSum = monthTarget.getMonthTargetCallSum();
        Integer monthTargetCallCount = monthTarget.getMonthTargetCallCount();
        Integer monthTargetCallTime = monthTarget.getMonthTargetCallTime();
        Integer monthTargetMeetingSum = monthTarget.getMonthTargetMeetingSum();
        Integer monthTargetMeetingTime = monthTarget.getMonthTargetMeetingTime();
        Integer monthTargetMeetingPersonSum = monthTarget.getMonthTargetMeetingPersonSum();
        Integer monthTargetMeetingPersonCount = monthTarget.getMonthTargetMeetingPersonCount();

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
        //List<OneMonthNoFollowCustomerResponseBean> oneMonthNoFollowCustomerList = null;
        List<OneMonthNoFollowCustomerResponseBean> oneMonthNoFollowCustomerList = workStationMapper.getOneMonthNoFollowCustomerList(bean);
        Integer count = workStationMapper.getOneMonthNoFollowCustomerListCount(bean);
        if (count == null){
            count = 0;
        }

        PageResponseBean<OneMonthNoFollowCustomerResponseBean> pageResponseBean = new PageResponseBean<>(bean, count, oneMonthNoFollowCustomerList);

        return pageResponseBean;
    }



    public List<DrugUserInteractResponseBean> drugUserInteract(WorkStationRequestBean bean){
        DrugUser drugUser = drugUserRepository.findFirstById(bean.getDrugUserId());
        String leaderPath = drugUser.getLeaderPath();
        if (leaderPath == null) {
            leaderPath = "";
        }
        bean.setLeaderPath(leaderPath + "%");

        List<DrugUserInteractResponseBean> drugUserInteractResponseBeans = workStationMapper.drugUserInteract(bean);


        return drugUserInteractResponseBeans;
    }

    /**
     * 坐席分析
     * @param bean
     * @return
     */
    public DrugUserAnalysisListResponseBean getDrugUserAnalysisList(WorkStationRequestBean bean){
        DrugUserAnalysisListResponseBean drugUserAnalysisList = new DrugUserAnalysisListResponseBean();
        DrugUser drugUser = drugUserRepository.findFirstById(bean.getDrugUserId());
        String leaderPath = drugUser.getLeaderPath();
        if (leaderPath == null) {
            leaderPath = "";
        }
        bean.setLeaderPath(leaderPath + "%");

        Integer minCallTotalTime = workStationMapper.minCallTotalTime(bean);
        bean.setMinCallTotalTime(minCallTotalTime);
        List<DrugUserAnalysisResponseBean> minCallTotalTimeList = workStationMapper.minCallTotalTimeList(bean);

        Integer minAvgCallTotalTime = workStationMapper.minAvgCallTotalTime(bean);
        bean.setMinAvgCallTotalTime(minAvgCallTotalTime);
        List<DrugUserAnalysisResponseBean> minAvgCallTotalTimeList = workStationMapper.minAvgCallTotalTimeList(bean);

        Integer minCallCount = workStationMapper.minCallCount(bean);
        bean.setMinCallCount(minCallCount);
        List<DrugUserAnalysisResponseBean> minCallCountList = workStationMapper.minCallCountList(bean);

        Integer minCallCoveredCount = workStationMapper.minCallCoveredCount(bean);
        bean.setMinCallCoveredCount(minCallCoveredCount);
        List<DrugUserAnalysisResponseBean> minCallCoveredCountList = workStationMapper.minCallCoveredCountList(bean);

        Integer minImCount = workStationMapper.minImCount(bean);
        bean.setMinImCount(minImCount);
        List<DrugUserAnalysisResponseBean> minImCountList = workStationMapper.minImCountList(bean);

        Integer minImCoveredCount = workStationMapper.minImCoveredCount(bean);
        bean.setMinImCoveredCount(minImCoveredCount);
        List<DrugUserAnalysisResponseBean> minImCoveredCountList = workStationMapper.minImCoveredCountList(bean);

        Integer minWechatCount = workStationMapper.minWechatCount(bean);
        bean.setMinWechatCount(minWechatCount);
        List<DrugUserAnalysisResponseBean> minWechatCountList = workStationMapper.minWechatCountList(bean);

        Integer minWechatCoveredCount = workStationMapper.minWechatCoveredCount(bean);
        bean.setMinWechatCoveredCount(minWechatCoveredCount);
        List<DrugUserAnalysisResponseBean> minWechatCoveredCountList = workStationMapper.minWechatCoveredCountList(bean);

        Integer minEmailCount = workStationMapper.minEmailCount(bean);
        bean.setMinEmailCount(minEmailCount);
        List<DrugUserAnalysisResponseBean> minEmailCountList = workStationMapper.minEmailCountList(bean);


        Integer minEmailCoveredCount = workStationMapper.minEmailCoveredCount(bean);
        bean.setMinEmailCoveredCount(minEmailCoveredCount);
        List<DrugUserAnalysisResponseBean> minEmailCoveredCountList = workStationMapper.minEmailCoveredCountList(bean);


        //脱落客户
        List<DropCustomerListResponseBean> dropCustomerListResponseBeanList = new ArrayList<>();
        //A级别
        bean.setLevel("A");
        DropCustomerListResponseBean dropACustomerListResponseBean = getDropCustomerListResponseBean(bean);
        dropCustomerListResponseBeanList.add(dropACustomerListResponseBean);
        //B级客户
        bean.setLevel("B");
        DropCustomerListResponseBean dropBCustomerListResponseBean = getDropCustomerListResponseBean(bean);
        dropCustomerListResponseBeanList.add(dropBCustomerListResponseBean);

        //C级客户
        bean.setLevel("C");
        DropCustomerListResponseBean dropCCustomerListResponseBean = getDropCustomerListResponseBean(bean);
        dropCustomerListResponseBeanList.add(dropCCustomerListResponseBean);

        //D级客户
        bean.setLevel("D");
        DropCustomerListResponseBean dropDCustomerListResponseBean = getDropCustomerListResponseBean(bean);
        dropCustomerListResponseBeanList.add(dropDCustomerListResponseBean);

        //其他级别
        bean.setLevel("other");
        DropCustomerListResponseBean dropOtherCustomerListResponseBean = getDropCustomerListResponseBean(bean);
        dropCustomerListResponseBeanList.add(dropOtherCustomerListResponseBean);

        Integer dateType = bean.getDateType();
        if (dateType != null){
            Integer targetCallSum = 0;
            Integer targetWechatNum = 0;
            Integer targetImSum = 0;
            Integer targetEmailNum = 0;
            if (dateType == DateTypeEnum.DAY.getValue()){
                DayTargetResponseBean perDayTarget = getPerDayTarget(bean.getProductId());
                targetCallSum = perDayTarget.getDayTargetCallSum();
                targetWechatNum = perDayTarget.getDayTargetWechatNum();
                targetImSum = perDayTarget.getDayTargetImSum();
                targetEmailNum = perDayTarget.getDayTargetEmailNum();
            }

            if (dateType == DateTypeEnum.WEEK.getValue()){
                WeekTargetResponseBean perWeekTarget = getPerWeekTarget(bean.getProductId());
                targetCallSum = perWeekTarget.getWeekTargetCallSum();
                targetWechatNum = perWeekTarget.getWeekTargetWechatNum();
                targetImSum = perWeekTarget.getWeekTargetImSum();
                targetEmailNum = perWeekTarget.getWeekTargetEmailNum();
            }


            if (dateType == DateTypeEnum.MONTH.getValue()){
                MonthTargetResponseBean perMonthTarget = getMonthTarget(bean.getProductId());
                targetCallSum = perMonthTarget.getMonthTargetCallSum();
                targetWechatNum = perMonthTarget.getMonthTargetWechatNum();
                targetImSum = perMonthTarget.getMonthTargetImSum();
                targetEmailNum = perMonthTarget.getMonthTargetEmailNum();
            }

            if (dateType == DateTypeEnum.QUARTER.getValue()){
                QuarterTargetResponseBean perQuarterTarget = getPerQuarterTarget(bean.getProductId());
                targetCallSum = perQuarterTarget.getQuarterTargetCallSum();
                targetWechatNum = perQuarterTarget.getQuarterTargetWechatNum();
                targetImSum = perQuarterTarget.getQuarterTargetImSum();
                targetEmailNum = perQuarterTarget.getQuarterTargetEmailNum();
            }

            bean.setCallReach(targetCallSum);
            bean.setWechatReach(targetWechatNum);
            bean.setImReach(targetImSum);
            bean.setEmailReach(targetEmailNum);

            List<String> callNoReachList = workStationMapper.callNoReach(bean);
            List<String> wechatNoReachList = workStationMapper.wechatNoReach(bean);
            List<String> imNoReachList = workStationMapper.imNoReach(bean);
            List<String> emailNoReachList = workStationMapper.emailNoReach(bean);
            drugUserAnalysisList.setCallNoReachList(callNoReachList);
            drugUserAnalysisList.setWechatNoReachList(wechatNoReachList);
            drugUserAnalysisList.setImNoReachList(imNoReachList);
            drugUserAnalysisList.setEmailNoReachList(emailNoReachList);

        }


        drugUserAnalysisList.setMinTotalCallTimeList(minCallTotalTimeList);
        drugUserAnalysisList.setMinAvgCallTimeList(minAvgCallTotalTimeList);
        drugUserAnalysisList.setMinTotalCallCountList(minCallCountList);
        drugUserAnalysisList.setMinCallCoveredCountList(minCallCoveredCountList);
        drugUserAnalysisList.setMinTotalImCountList(minImCountList);
        drugUserAnalysisList.setMinImCoveredCountList(minImCoveredCountList);
        drugUserAnalysisList.setMinTotalWechatCountList(minWechatCountList);
        drugUserAnalysisList.setMinWechatCoveredCountList(minWechatCoveredCountList);
        drugUserAnalysisList.setMinEmailCountList(minEmailCountList);
        drugUserAnalysisList.setMinWechatCoveredCountList(minEmailCoveredCountList);

        return drugUserAnalysisList;

    }


    /**
     * 客户分析
     * @param bean
     * @return
     */
    public DoctorAnalysisListResponseBean getDoctorAnalysisList(WorkStationRequestBean bean){
        DoctorAnalysisListResponseBean doctorAnalysitList = new DoctorAnalysisListResponseBean();

        DrugUserAnalysisListResponseBean drugUserAnalysisList = new DrugUserAnalysisListResponseBean();
        DrugUser drugUser = drugUserRepository.findFirstById(bean.getDrugUserId());
        String leaderPath = drugUser.getLeaderPath();
        if (leaderPath == null) {
            leaderPath = "";
        }
        bean.setLeaderPath(leaderPath + "%");

        Integer minCallTotalTime = workStationMapper.minDoctorCallTotalTime(bean);
        bean.setMinCallTotalTime(minCallTotalTime);
        List<DoctorAnalysisResponseBean> minCallTotalTimeList = workStationMapper.minDoctorCallTotalTimeList(bean);

        Integer minAvgCallTotalTime = workStationMapper.minDoctorAvgCallTotalTime(bean);
        bean.setMinAvgCallTotalTime(minAvgCallTotalTime);
        List<DoctorAnalysisResponseBean> minAvgCallTotalTimeList = workStationMapper.minDoctorAvgCallTotalTimeList(bean);

        Integer minCallCount = workStationMapper.minDoctorCallCount(bean);
        bean.setMinCallCount(minCallCount);
        List<DoctorAnalysisResponseBean> minCallCountList = workStationMapper.minDoctorCallCountList(bean);

        Integer minImCount = workStationMapper.minDoctorImCount(bean);
        bean.setMinImCount(minImCount);
        List<DoctorAnalysisResponseBean> minImCountList = workStationMapper.minDoctorImCountList(bean);


        Integer minWechatCount = workStationMapper.minDoctorWechatCount(bean);
        bean.setMinWechatCount(minWechatCount);
        List<DoctorAnalysisResponseBean> minWechatCountList = workStationMapper.minDoctorWechatCountList(bean);


        Integer minEmailCount = workStationMapper.minDoctorEmailCount(bean);
        bean.setMinEmailCount(minEmailCount);
        List<DoctorAnalysisResponseBean> minEmailCountList = workStationMapper.minDoctorEmailCountList(bean);

        Integer minMeetingTime = workStationMapper.minDoctorMeetingTime(bean);
        bean.setMinMeetingTime(minMeetingTime);
        List<DoctorAnalysisResponseBean> minMeetingTimeList = workStationMapper.minDoctorMeetingTimeList(bean);


        doctorAnalysitList.setMinTotalCallTimeList(minCallTotalTimeList);
        doctorAnalysitList.setMinAvgCallTimeList(minAvgCallTotalTimeList);
        doctorAnalysitList.setMinTotalCallCountList(minCallCountList);
        doctorAnalysitList.setMinTotalImCountList(minImCountList);
        doctorAnalysitList.setMinTotalWechatCountList(minWechatCountList);
        doctorAnalysitList.setMinEmailCountList(minEmailCountList);
        doctorAnalysitList.setMinMeetingTimeList(minMeetingTimeList);

        return doctorAnalysitList;
    }



    private DropCustomerListResponseBean getDropCustomerListResponseBean(WorkStationRequestBean bean){
        DropCustomerListResponseBean dropCustomerListResponseBean = new DropCustomerListResponseBean();
        Long productId = bean.getProductId();
        String level = bean.getLevel();
        DropTarget dropTarget = dropTargetRepository.findFirstByProductIdAndLevel(productId,level);
        if (null != dropTarget){
            bean.setLevel(level);
            Integer dropPriod = dropTarget.getDropPriod();
            if (dropPriod == null){
                dropPriod=0;
            }
            bean.setLevelDropCount(dropPriod * 7);
            Integer dropCount = workStationMapper.getDropCount(bean);
            Integer maxDropCount = workStationMapper.maxDropCount(bean);
            bean.setMaxLevelDropCount(maxDropCount);
            List<DrugUserAnalysisResponseBean> maxDropCountList = workStationMapper.maxDropCountList(bean);
            dropCustomerListResponseBean.setLevel(level);
            dropCustomerListResponseBean.setLevelDropCount(dropCount);
            dropCustomerListResponseBean.setMaxDropDrugUserList(maxDropCountList);
        }


        return dropCustomerListResponseBean;
    }



    /**
     * 得到本月目标
     * @return
     */
    private MonthTargetResponseBean getMonthTarget(Long productId){
        MonthTargetResponseBean monthTarget = new MonthTargetResponseBean();
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

                    Integer monthWechatNum = target.getMonthWechatNum();
                    if (monthWechatNum == null){
                        monthWechatNum = 0;
                    }
                    monthTargetWechatNum += monthWechatNum;


                    Integer monthWechatCount = target.getMonthWechatCount();
                    if (monthWechatCount == null){
                        monthWechatCount = 0;
                    }
                    monthTargetWechatCount += monthWechatCount;


                    Integer monthImNum = target.getMonthImNum();
                    if (monthImNum == null){
                        monthImNum = 0;
                    }
                    monthTargetImSum += monthImNum;


                    Integer monthImCount = target.getMonthImCount();
                    if (monthImCount == null){
                        monthImCount = 0;
                    }
                    monthTargetImCount += monthImCount;


                    Integer monthEmailNum = target.getMonthEmailNum();
                    if (monthEmailNum == null){
                        monthEmailNum = 0;
                    }
                    monthTargetEmailNum += monthEmailNum;


                    Integer monthEmailCount = target.getMonthEmailCount();
                    if (monthEmailCount == null){
                        monthEmailCount = 0;
                    }
                    monthTargetEmailCount += monthEmailCount;


                    Integer monthTelPerson = target.getMonthTelPerson();
                    if (monthTelPerson == null){
                        monthTelPerson = 0;
                    }
                    monthTargetCallSum += monthTelPerson;


                    Integer monthTelNum = target.getMonthTelNum();
                    if (monthTelNum == null){
                        monthTelNum = 0;
                    }
                    monthTargetCallCount += monthTelNum;


                    Integer monthTelTime = target.getMonthTelTime();
                    if (monthTelTime == null){
                        monthTelTime = 0;
                    }
                    monthTargetCallTime += monthTelTime;


                    Integer monthMeetingNum = target.getMonthMeetingNum();
                    if (monthMeetingNum == null){
                        monthMeetingNum = 0;
                    }
                    monthTargetMeetingSum += monthMeetingNum;


                    Integer monthMeetingTime = target.getMonthMeetingTime();
                    if (monthMeetingTime == null){
                        monthMeetingTime = 0;
                    }
                    monthTargetMeetingTime += monthMeetingTime;


                    Integer monthMeetingPersonSum = target.getMonthMeetingPersonSum();
                    if (monthMeetingPersonSum == null){
                        monthMeetingPersonSum = 0;
                    }
                    monthTargetMeetingPersonSum += monthMeetingPersonSum;


                    Integer monthMeetingPersonCount = target.getMonthMeetingPersonCount();
                    if (monthMeetingPersonCount == null){
                        monthMeetingPersonCount = 0;
                    }
                    monthTargetMeetingPersonCount += monthMeetingPersonCount;
                }
            }
        }

        monthTarget.setMonthTargetWechatNum(monthTargetWechatNum);
        monthTarget.setMonthTargetWechatCount(monthTargetWechatCount);
        monthTarget.setMonthTargetImSum(monthTargetImSum);
        monthTarget.setMonthTargetImCount(monthTargetImCount);
        monthTarget.setMonthTargetEmailNum(monthTargetEmailNum);
        monthTarget.setMonthTargetEmailCount(monthTargetEmailCount);
        monthTarget.setMonthTargetCallSum(monthTargetCallSum);
        monthTarget.setMonthTargetCallCount(monthTargetCallCount);
        monthTarget.setMonthTargetCallTime(monthTargetCallTime);
        monthTarget.setMonthTargetMeetingSum(monthTargetMeetingSum);
        monthTarget.setMonthTargetMeetingTime(monthTargetMeetingTime);
        monthTarget.setMonthTargetMeetingPersonSum(monthTargetMeetingPersonSum);
        monthTarget.setMonthTargetMeetingPersonCount(monthTargetMeetingPersonCount);

        return monthTarget;
    }

    /**
     * 每天目标
     * @return
     */
    private DayTargetResponseBean getPerDayTarget(Long productId){
        DayTargetResponseBean dayTarget = new DayTargetResponseBean();


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

        MonthTargetResponseBean monthTarget = getMonthTarget(productId);


        Integer monthTargetWechatNum = monthTarget.getMonthTargetWechatNum();
        Integer monthTargetWechatCount = monthTarget.getMonthTargetWechatCount();
        Integer monthTargetImSum = monthTarget.getMonthTargetImSum();
        Integer monthTargetImCount = monthTarget.getMonthTargetImCount();
        Integer monthTargetEmailNum = monthTarget.getMonthTargetEmailNum();
        Integer monthTargetEmailCount = monthTarget.getMonthTargetEmailCount();
        Integer monthTargetCallSum = monthTarget.getMonthTargetCallSum();
        Integer monthTargetCallCount = monthTarget.getMonthTargetCallCount();
        Integer monthTargetCallTime = monthTarget.getMonthTargetCallTime();
        Integer monthTargetMeetingSum = monthTarget.getMonthTargetMeetingSum();
        Integer monthTargetMeetingTime = monthTarget.getMonthTargetMeetingTime();
        Integer monthTargetMeetingPersonSum = monthTarget.getMonthTargetMeetingPersonSum();
        Integer monthTargetMeetingPersonCount = monthTarget.getMonthTargetMeetingPersonCount();

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




        dayTarget.setDayTargetWechatNum(perDayTargetWechatNum);
        dayTarget.setDayTargetWechatCount(perDayTargetWechatCount);
        dayTarget.setDayTargetImSum(perDayTargetImSum);
        dayTarget.setDayTargetImCount(perDayTargetImCount);
        dayTarget.setDayTargetEmailNum(perDayTargetEmailNum);
        dayTarget.setDayTargetEmailCount(perDayTargetEmailCount);
        dayTarget.setDayTargetCallSum(perDayTargetCallSum);
        dayTarget.setDayTargetCallCount(perDayTargetCallCount);
        dayTarget.setDayTargetCallTime(perDayTargetCallTime);
        dayTarget.setDayTargetMeetingPersonSum(perDayTargetMeetingPersonSum);
        dayTarget.setDayTargetMeetingTime(perDayTargetMeetingTime);
        dayTarget.setDayTargetMeetingPersonSum(perDayTargetMeetingPersonSum);
        dayTarget.setDayTargetMeetingPersonCount(perDayTargetMeetingPersonCount);

        return dayTarget;

    }


    /**
     * 每周目标
      * @param productId
     * @return
     */
    private WeekTargetResponseBean getPerWeekTarget(Long productId){
        WeekTargetResponseBean weekTarget = new WeekTargetResponseBean();


        Integer perWeekTargetWechatNum = 0;
        Integer perWeekTargetWechatCount = 0;
        Integer perWeekTargetImSum = 0;
        Integer perWeekTargetImCount = 0;
        Integer perWeekTargetEmailNum = 0;
        Integer perWeekTargetEmailCount = 0;
        Integer perWeekTargetCallSum = 0;
        Integer perWeekTargetCallCount = 0;
        Integer perWeekTargetCallTime = 0;
        Integer perWeekTargetMeetingSum = 0;
        Integer perWeekTargetMeetingTime = 0;
        Integer perWeekTargetMeetingPersonSum = 0;
        Integer perWeekTargetMeetingPersonCount = 0;

        //现在按一个月四周处理
        Integer week = 4;

        MonthTargetResponseBean monthTarget = getMonthTarget(productId);
        Integer monthTargetWechatNum = monthTarget.getMonthTargetWechatNum();
        Integer monthTargetWechatCount = monthTarget.getMonthTargetWechatCount();
        Integer monthTargetImSum = monthTarget.getMonthTargetImSum();
        Integer monthTargetImCount = monthTarget.getMonthTargetImCount();
        Integer monthTargetEmailNum = monthTarget.getMonthTargetEmailNum();
        Integer monthTargetEmailCount = monthTarget.getMonthTargetEmailCount();
        Integer monthTargetCallSum = monthTarget.getMonthTargetCallSum();
        Integer monthTargetCallCount = monthTarget.getMonthTargetCallCount();
        Integer monthTargetCallTime = monthTarget.getMonthTargetCallTime();
        Integer monthTargetMeetingSum = monthTarget.getMonthTargetMeetingSum();
        Integer monthTargetMeetingTime = monthTarget.getMonthTargetMeetingTime();
        Integer monthTargetMeetingPersonSum = monthTarget.getMonthTargetMeetingPersonSum();
        Integer monthTargetMeetingPersonCount = monthTarget.getMonthTargetMeetingPersonCount();


        perWeekTargetWechatNum = Math.round(monthTargetWechatNum/week);
        perWeekTargetWechatCount = Math.round(monthTargetWechatCount/week);
        perWeekTargetImSum = Math.round(monthTargetImSum/week);
        perWeekTargetImCount = Math.round(monthTargetImCount/week);
        perWeekTargetEmailNum = Math.round(monthTargetEmailNum/week);
        perWeekTargetEmailCount = Math.round(monthTargetEmailCount/week);
        perWeekTargetCallSum = Math.round(monthTargetCallSum/week);
        perWeekTargetCallCount = Math.round(monthTargetCallCount/week);
        perWeekTargetCallTime = Math.round(monthTargetCallTime/week);
        perWeekTargetMeetingSum = Math.round(monthTargetMeetingSum/week);
        perWeekTargetMeetingTime = Math.round(monthTargetMeetingTime/week);
        perWeekTargetMeetingPersonSum = Math.round(monthTargetMeetingPersonSum/week);
        perWeekTargetMeetingPersonCount = Math.round(monthTargetMeetingPersonCount/week);



        weekTarget.setWeekTargetWechatNum(perWeekTargetWechatNum);
        weekTarget.setWeekTargetWechatCount(perWeekTargetWechatCount);
        weekTarget.setWeekTargetImSum(perWeekTargetImSum);
        weekTarget.setWeekTargetImCount(perWeekTargetImCount);
        weekTarget.setWeekTargetEmailNum(perWeekTargetEmailNum);
        weekTarget.setWeekTargetEmailCount(perWeekTargetEmailCount);
        weekTarget.setWeekTargetCallSum(perWeekTargetCallSum);
        weekTarget.setWeekTargetCallCount(perWeekTargetCallCount);
        weekTarget.setWeekTargetCallTime(perWeekTargetCallTime);
        weekTarget.setWeekTargetMeetingSum(perWeekTargetMeetingSum);
        weekTarget.setWeekTargetMeetingTime(perWeekTargetMeetingTime);
        weekTarget.setWeekTargetMeetingPersonSum(perWeekTargetMeetingPersonSum);
        weekTarget.setWeekTargetMeetingPersonCount(perWeekTargetMeetingPersonCount);

        return weekTarget;
    }

    /**
     * 每个季度的目标
     * @param productId
     * @return
     */
    private QuarterTargetResponseBean getPerQuarterTarget(Long productId){
        QuarterTargetResponseBean quarterTarget = new QuarterTargetResponseBean();


        Integer perQuarterTargetWechatNum = 0;
        Integer perQuarterTargetWechatCount = 0;
        Integer perQuarterTargetImSum = 0;
        Integer perQuarterTargetImCount = 0;
        Integer perQuarterTargetEmailNum = 0;
        Integer perQuarterTargetEmailCount = 0;
        Integer perQuarterTargetCallSum = 0;
        Integer perQuarterTargetCallCount = 0;
        Integer perQuarterTargetCallTime = 0;
        Integer perQuarterTargetMeetingSum = 0;
        Integer perQuarterTargetMeetingTime = 0;
        Integer perQuarterTargetMeetingPersonSum = 0;
        Integer perQuarterTargetMeetingPersonCount = 0;

        //一个季度按三个月处理
        Integer month = 3;



        MonthTargetResponseBean monthTarget = getMonthTarget(productId);
        Integer monthTargetWechatNum = monthTarget.getMonthTargetWechatNum();
        Integer monthTargetWechatCount = monthTarget.getMonthTargetWechatCount();
        Integer monthTargetImSum = monthTarget.getMonthTargetImSum();
        Integer monthTargetImCount = monthTarget.getMonthTargetImCount();
        Integer monthTargetEmailNum = monthTarget.getMonthTargetEmailNum();
        Integer monthTargetEmailCount = monthTarget.getMonthTargetEmailCount();
        Integer monthTargetCallSum = monthTarget.getMonthTargetCallSum();
        Integer monthTargetCallCount = monthTarget.getMonthTargetCallCount();
        Integer monthTargetCallTime = monthTarget.getMonthTargetCallTime();
        Integer monthTargetMeetingSum = monthTarget.getMonthTargetMeetingSum();
        Integer monthTargetMeetingTime = monthTarget.getMonthTargetMeetingTime();
        Integer monthTargetMeetingPersonSum = monthTarget.getMonthTargetMeetingPersonSum();
        Integer monthTargetMeetingPersonCount = monthTarget.getMonthTargetMeetingPersonCount();


        perQuarterTargetWechatNum = Math.round(monthTargetWechatNum*month);
        perQuarterTargetWechatCount = Math.round(monthTargetWechatCount*month);
        perQuarterTargetImSum = Math.round(monthTargetImSum*month);
        perQuarterTargetImCount = Math.round(monthTargetImCount*month);
        perQuarterTargetEmailNum = Math.round(monthTargetEmailNum*month);
        perQuarterTargetEmailCount = Math.round(monthTargetEmailCount*month);
        perQuarterTargetCallSum = Math.round(monthTargetCallSum*month);
        perQuarterTargetCallCount = Math.round(monthTargetCallCount*month);
        perQuarterTargetCallTime = Math.round(monthTargetCallTime*month);
        perQuarterTargetMeetingSum = Math.round(monthTargetMeetingSum*month);
        perQuarterTargetMeetingTime = Math.round(monthTargetMeetingTime*month);
        perQuarterTargetMeetingPersonSum = Math.round(monthTargetMeetingPersonSum*month);
        perQuarterTargetMeetingPersonCount = Math.round(monthTargetMeetingPersonCount*month);

        quarterTarget.setQuarterTargetWechatNum(perQuarterTargetWechatNum);
        quarterTarget.setQuarterTargetWechatCount(perQuarterTargetWechatCount);
        quarterTarget.setQuarterTargetImSum(perQuarterTargetImSum);
        quarterTarget.setQuarterTargetImCount(perQuarterTargetImCount);
        quarterTarget.setQuarterTargetEmailNum(perQuarterTargetEmailNum);
        quarterTarget.setQuarterTargetEmailCount(perQuarterTargetEmailCount);
        quarterTarget.setQuarterTargetCallSum(perQuarterTargetCallSum);
        quarterTarget.setQuarterTargetCallCount(perQuarterTargetCallCount);
        quarterTarget.setQuarterTargetCallTime(perQuarterTargetCallTime);
        quarterTarget.setQuarterTargetMeetingSum(perQuarterTargetMeetingSum);
        quarterTarget.setQuarterTargetMeetingTime(perQuarterTargetMeetingTime);
        quarterTarget.setQuarterTargetMeetingPersonSum(perQuarterTargetMeetingPersonSum);
        quarterTarget.setQuarterTargetMeetingPersonCount(perQuarterTargetMeetingPersonCount);


        return quarterTarget;
    }


}
