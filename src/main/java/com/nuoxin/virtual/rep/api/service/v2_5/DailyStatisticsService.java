package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.statistics.DailyStatisticsRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.statistics.DailyStatisticsResponseBean;

import java.util.List;

/**
 * 日报统计相关接口
 * @author tiancun
 * @date 2019-02-25
 */
public interface DailyStatisticsService {


    /**
     * 得到日报的统计
     * @param bean
     * @return
     */
    DailyStatisticsResponseBean getDailyStatistics(DailyStatisticsRequestBean bean);


    /**
     * 得到下载日报的数据
     * @param bean
     */
    List<List<String>> getDownloadDailyStatisticsData(DailyStatisticsRequestBean bean);

    /**
     * 得到 Excel 的表头描述信息
     * @param bean
     * @return
     */
    String getDownloadDesc(DailyStatisticsRequestBean bean);


    /**
     * 更新产品目标医院
     * @param productId
     * @param targetHospital
     */
    void updateTargetHospital(Long productId, Integer targetHospital);



    /**
     * 更新产品目标医生
     * @param productId
     * @param targetDoctor
     */
    void updateTargetDoctor(Long productId, Integer targetDoctor);

}