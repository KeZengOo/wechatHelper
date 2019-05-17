package com.nuoxin.virtual.rep.api.service.v3_0;

import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DailyReportRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DailyReportResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * 日报相关业务接口
 * @author tiancun
 * @date 2019-05-09
 */
public interface DailyReportService {

    /**
     * 日报导出
     * @param response
     * @param request
     * @param drugUser
     * @return
     */
    void exportDailyReport(HttpServletResponse response, DailyReportRequest request, DrugUser drugUser);



    /**
     * 日报
     * @param request
     * @return
     */
    DailyReportResponse getDailyReport(DailyReportRequest request);


}
