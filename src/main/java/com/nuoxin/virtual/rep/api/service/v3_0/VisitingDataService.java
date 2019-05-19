package com.nuoxin.virtual.rep.api.service.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.VisitDataRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.VisitDataResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName VisitingDataService
 * @Description 拜访数据Service
 * @Author dangjunhui
 * @Date 2019/5/14 11:26
 * @Version 1.0
 */
public interface VisitingDataService {

    /**
     * 分页查询拜访数据统计列表数据
     * @Author dangjunhui
     * @param request 请求对象 含分页参数和查询参数
     * @return page
     */
    PageResponseBean<VisitDataResponse> getVisitDataByPage(String leaderPath, VisitDataRequest request);

    /**
     * 导出拜访数据汇总记录
     * @param response 相应内容
     * @param leaderPath 当前用户的关联人员
     * @param request 请求对象
     */
    void exportVisitDataSummary(HttpServletResponse response, String leaderPath, VisitDataRequest request);

}
