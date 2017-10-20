package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.WorkStationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.*;

import java.util.List;
import java.util.Map;

/**
 * Create by tiancun on 2017/10/16
 */
public interface WorkStationMapper {

    List<CustomerSumResponseBean> getTotalCustomerStatistic(WorkStationRequestBean bean);

    List<CustomerSumResponseBean> getAddCustomerStatistic(WorkStationRequestBean bean);

    List<CustomerSumResponseBean> getCoverCustomerStatistic(WorkStationRequestBean bean);

    List<OneMonthNoFollowCustomerResponseBean> getOneMonthNoFollowCustomerList(WorkStationRequestBean bean);

    Integer getOneMonthNoFollowCustomerListCount(WorkStationRequestBean bean);

    List<DrugUserInteractResponseBean> drugUserInteract(WorkStationRequestBean bean);


    /**
     * 坐席分析
     */
    //总通话时间最短
    Integer minCallTotalTime(WorkStationRequestBean bean);
    List<DrugUserAnalysisResponseBean> minCallTotalTimeList(WorkStationRequestBean bean);

    //平均通话时间最短,按照次数
    Integer minAvgCallTotalTime(WorkStationRequestBean bean);
    List<DrugUserAnalysisResponseBean> minAvgCallTotalTimeList(WorkStationRequestBean bean);

    //电话数量最少
    Integer minCallCount(WorkStationRequestBean bean);
    List<DrugUserAnalysisResponseBean> minCallCountList(WorkStationRequestBean bean);

    //电话覆盖数量最少
    Integer minCallCoveredCount(WorkStationRequestBean bean);
    List<DrugUserAnalysisResponseBean> minCallCoveredCountList(WorkStationRequestBean bean);

    //总短信数量最少
    Integer minImCount(WorkStationRequestBean bean);
    List<DrugUserAnalysisResponseBean> minImCountList(WorkStationRequestBean bean);

    //短信覆盖客户数量最少
    Integer minImCoveredCount(WorkStationRequestBean bean);
    List<DrugUserAnalysisResponseBean> minImCoveredCountList(WorkStationRequestBean bean);

    //微信数量最少
    Integer minWechatCount(WorkStationRequestBean bean);
    List<DrugUserAnalysisResponseBean> minWechatCountList(WorkStationRequestBean bean);


    //微信覆盖客户数最少
    Integer minWechatCoveredCount(WorkStationRequestBean bean);
    List<DrugUserAnalysisResponseBean> minWechatCoveredCountList(WorkStationRequestBean bean);


    //邮件数量最少
    Integer minEmailCount(WorkStationRequestBean bean);
    List<DrugUserAnalysisResponseBean> minEmailCountList(WorkStationRequestBean bean);

    //邮件覆盖客户最少
    Integer minEmailCoveredCount(WorkStationRequestBean bean);
    List<DrugUserAnalysisResponseBean> minEmailCoveredCountList(WorkStationRequestBean bean);

    //通话未达标，按照人数
    List<String> callNoReach(WorkStationRequestBean bean);

    //微信未达标，按照人数
    List<String> wechatNoReach(WorkStationRequestBean bean);

    //短信未达标，按照人数
    List<String> imNoReach(WorkStationRequestBean bean);

    //邮件未达标，按照人数
    List<String> emailNoReach(WorkStationRequestBean bean);

    /**
     * 脱落客户最严重
     */

    //A级脱落总数
    Integer getDropAcount(WorkStationRequestBean bean);

    //B级脱落总数
    Integer getDropBcount(WorkStationRequestBean bean);


    //B级脱落总数
    //Integer getDropBcount(WorkStationRequestBean bean);


    /**
     * 客户分析
     */
    //总通话时间最短
    Integer minDoctorCallTotalTime(WorkStationRequestBean bean);
    List<DoctorAnalysisResponseBean> minDoctorCallTotalTimeList(WorkStationRequestBean bean);

    //平均通话时间最短,按照次数
    Integer minDoctorAvgCallTotalTime(WorkStationRequestBean bean);
    List<DoctorAnalysisResponseBean> minDoctorAvgCallTotalTimeList(WorkStationRequestBean bean);

    //电话数量最少
    Integer minDoctorCallCount(WorkStationRequestBean bean);
    List<DoctorAnalysisResponseBean> minDoctorCallCountList(WorkStationRequestBean bean);


    //总短信数量最少
    Integer minDoctorImCount(WorkStationRequestBean bean);
    List<DoctorAnalysisResponseBean> minDoctorImCountList(WorkStationRequestBean bean);


    //微信数量最少
    Integer minDoctorWechatCount(WorkStationRequestBean bean);
    List<DoctorAnalysisResponseBean> minDoctorWechatCountList(WorkStationRequestBean bean);

    //微信数量最少
    Integer minDoctorEmailCount(WorkStationRequestBean bean);
    List<DoctorAnalysisResponseBean> minDoctorEmailCountList(WorkStationRequestBean bean);

    //会议时间最少
    Integer minDoctorMeetingTime(WorkStationRequestBean bean);
    List<DoctorAnalysisResponseBean> minDoctorMeetingTimeList(WorkStationRequestBean bean);


}
