package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.mybatis.DailyReportMapper;
import com.nuoxin.virtual.rep.api.service.v3_0.DailyReportService;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DailyReportRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DailyReportResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tiancun
 * @date 2019-05-09
 */
@Service
public class DailyReportServiceImpl implements DailyReportService {

    @Resource
    private DailyReportMapper dailyReportMapper;

    @Override
    public DailyReportResponse getDailyReport(DailyReportRequest request) {
        DailyReportResponse dailyReport = new DailyReportResponse();

        Integer activeCoverDoctorNum = dailyReportMapper.activeCoverDoctorNum(request);

        dailyReport.setActiveCoverDoctorNum(activeCoverDoctorNum);


        return dailyReport;
    }
}
