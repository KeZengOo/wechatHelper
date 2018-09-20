package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsParams;
import com.nuoxin.virtual.rep.api.web.controller.request.WorkStationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallInfoResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.TodayStatisticsResponseBean;
import org.apache.ibatis.annotations.Param;


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
     * 根据sigToken更新录音url
     * @param callUrl
     * @param signToken
     */
    void updateCallUrlBySigToken(@Param(value = "callUrl") String callUrl, @Param(value = "sinToken") String signToken);

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
}
