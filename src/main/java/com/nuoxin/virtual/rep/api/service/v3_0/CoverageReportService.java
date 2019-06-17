package com.nuoxin.virtual.rep.api.service.v3_0;

import com.nuoxin.virtual.rep.api.entity.v3_0.VisitDataPart;

import java.util.List;

/**
 * @ClassName CoverageReportService
 * @Description 拜访数据汇总 覆盖月报Service
 * @Author dangjunhui
 * @Date 2019/6/14 16:08
 * @Version 1.0
 */
public interface CoverageReportService {

    List<VisitDataPart> findOverviewListByProductIdAndTime(Long proId, String startTime, String endTime);




}
