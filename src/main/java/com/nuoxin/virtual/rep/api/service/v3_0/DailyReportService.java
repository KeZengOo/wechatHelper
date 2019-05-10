package com.nuoxin.virtual.rep.api.service.v3_0;

import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DailyReportRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DailyReportResponse;

/**
 * 日报相关业务接口
 * @author tiancun
 * @date 2019-05-09
 */
public interface DailyReportService {

    /**
     * 日报返回数据
     * @param request
     * @return
     */
    DailyReportResponse getDailyReport(DailyReportRequest request);

}
