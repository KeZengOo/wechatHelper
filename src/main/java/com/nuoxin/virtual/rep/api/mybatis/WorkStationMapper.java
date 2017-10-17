package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.WorkStationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.CustomerSumResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.OneMonthNoFollowCustomerResponseBean;

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

    //总通话时间最短
    Map<String, String> minCallTotalTime(WorkStationRequestBean bean);

    //平均通话时间最短,按照次数
    Map<String, String> minAvgCallTotalTime(WorkStationRequestBean bean);

    //电话数量最少
    Map<String, String> minCallCount(WorkStationRequestBean bean);

    //电话覆盖数量最少
    Map<String, String> minCallCoveredCount(WorkStationRequestBean bean);

    //总短信数量最少
    Map<String, String> minImCount(WorkStationRequestBean bean);

    //短信覆盖客户数量最少
    Map<String, String> minImCoveredCount(WorkStationRequestBean bean);

    //微信数量最少
    Map<String, String> minWechatCount(WorkStationRequestBean bean);


    //微信覆盖客户数最少
    Map<String, String> minWechatCoveredCount(WorkStationRequestBean bean);

    //通话未达标，按照人数
    Map<String, String> callNoReach(WorkStationRequestBean bean);

    //微信未达标，按照人数
    Map<String, String> wechatNoReach(WorkStationRequestBean bean);

    //短信未达标，按照人数
    Map<String, String> imNoReach(WorkStationRequestBean bean);

    //脱落客户最严重
    Map<String, String> dropCustomers(WorkStationRequestBean bean);
}
