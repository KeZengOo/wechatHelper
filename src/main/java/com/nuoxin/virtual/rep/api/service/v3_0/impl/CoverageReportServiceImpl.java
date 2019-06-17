package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.entity.v3_0.VisitDataPart;
import com.nuoxin.virtual.rep.api.mybatis.CoverageReportMapper;
import com.nuoxin.virtual.rep.api.service.v3_0.CoverageReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName CoverageReportServiceImpl
 * @Description 拜访数据汇总 覆盖月报Service实现
 * @Author dangjunhui
 * @Date 2019/6/14 17:04
 * @Version 1.0
 */
@Service
public class CoverageReportServiceImpl implements CoverageReportService {

    @Resource
    private CoverageReportMapper coverageReportMapper;

    @Override
    public List<VisitDataPart> findOverviewListByProductIdAndTime(Long proId, String startTime, String endTime) {

        return null;
    }



}
