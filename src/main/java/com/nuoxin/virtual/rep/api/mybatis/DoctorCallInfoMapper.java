package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsParams;
import com.nuoxin.virtual.rep.api.web.controller.request.WorkStationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.IdentifyCallUrlRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo.RetryCallInfoRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallInfoResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.CallTimeResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.TodayStatisticsResponseBean;
import org.apache.ibatis.annotations.Param;


import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Create by tiancun on 2017/10/12
 */
public interface DoctorCallInfoMapper {

    TodayStatisticsResponseBean getCallInfoStatistics(WorkStationRequestBean bean);

    TodayStatisticsResponseBean getCallInfoTimeStatistics(WorkStationRequestBean bean);

    /**
     * 用来测试的，参数写死
     * @return
     */
    List<CallInfoResponseBean> getCallInfoList();

    /**
     * 用来测试
     * 更新录音地址
     * @param callUrl
     * @param id
     */
    void updateCallUrl(@Param(value = "callUrl") String callUrl, @Param(value = "id") Long id);

    /**
     * 根据sinToken查询录音url
     * @param sinToken
     * @return
     */
    String getCallUrlBySigToken(@Param(value = "sigToken") String sinToken);

    /**
     * 根据sinToken查询电话记录信息
     * @param sinToken
     * @return
     */
    CallInfoResponseBean getCallInfoBySinToken(@Param(value = "sinToken") String sinToken);

    /**
     * 新增重试的电话记录
     * @param bean
     */
    void addRetryCallInfo(RetryCallInfoRequestBean bean);

    /**
     * 根据sigToken更新录音url
     * @param callUrl
     * @param signToken
     */
    void updateCallUrlBySigToken(@Param(value = "callUrl") String callUrl, @Param(value = "sinToken") String signToken, @Param(value = "callTime") Integer callTime);

    /**
     * 医生拜访明细表
     * @param statisticsParams
     * @return
     */
    List<LinkedHashMap<String,Object>> getDoctorVisitDetailList(StatisticsParams statisticsParams);

    /**
     * 医生拜访明细表统计
     * @param statisticsParams
     * @return
     */
    int getDoctorVisitDetailListCount(StatisticsParams statisticsParams);


    /**
     * 得到电话统计
     * @param statisticsParams
     * @return
     */
    CallTimeResponseBean getCallTime(StatisticsParams statisticsParams);


    /**
     * 代表跟医生的上一次拜访时间
     * @param doctorId
     * @param drugUserId
     * @return
     */
    Date lastVisitTime(@Param(value = "drugUserId")Long drugUserId,@Param(value = "doctorId")Long doctorId);


    /**
     * 查询需要识别的录音url
     * @param bean
     * @return
     */
    List<CallInfoResponseBean> getIdentifyCallUrl(IdentifyCallUrlRequestBean bean);

}
