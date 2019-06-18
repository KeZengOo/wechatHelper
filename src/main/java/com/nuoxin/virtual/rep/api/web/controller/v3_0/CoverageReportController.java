package com.nuoxin.virtual.rep.api.web.controller.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.CoverageReportRequest;
import com.nuoxin.virtual.rep.api.service.v3_0.CoverageReportService;
import com.nuoxin.virtual.rep.api.web.controller.v2_5.NewBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

/**
 * @ClassName CoverageReportController
 * @Description 拜访数据汇总-覆盖月报相关接口
 * @Author dangjunhui
 * @Date 2019/6/14 15:59
 * @Version 1.0
 */
@Slf4j
@Api(value = "V3.1.0拜访数据汇总-覆盖月报相关接口")
@RestController
@RequestMapping("/coverage")
public class CoverageReportController extends NewBaseController implements Serializable {

    private static final long serialVersionUID = 2065078175471895137L;

    @Resource
    private CoverageReportService coverageReportService;

    /**
     * 覆盖情况总览图表
     * @param request 请求参数
     * @return DefaultResponseBean
     * @throws Exception
     */
    @ApiOperation(value = "覆盖情况总览", notes = "覆盖情况总览")
    @PostMapping("/overview")
    public DefaultResponseBean<Map<String, Object>> overview(@RequestBody CoverageReportRequest request) throws Exception {
        String startTime = request.getStartTime();
        String endTime = request.getEndTime();
        Long productId = request.getProductId();
        if(productId == null) {
            throw new Exception("参数不合法!");
        }
        if(StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            throw new Exception("参数不合法!");
        }
        Map<String, Object> map = coverageReportService.findOverviewListByProductIdAndTime(productId, startTime, endTime);
        DefaultResponseBean<Map<String, Object>> result = new DefaultResponseBean<>();
        result.setData(map);
        return result;
    }

    /**
     * 覆盖月报概览——报表数据导出
     * @param request 请求参数
     * @throws Exception
     */
    @ApiOperation(value = "拜访数据汇总-覆盖月报总览导出")
    @GetMapping(value = "/overview/export")
    public void getOverview(HttpServletRequest request) throws Exception {
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        if(StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            throw new Exception("参数不合法!");
        }
        String productId = request.getParameter("productId");
        if(StringUtils.isBlank(productId)) {
            throw new Exception("参数不合法!");
        }
        coverageReportService.exportOverview(getResponse(), Long.parseLong(productId), startTime, endTime);
    }

    /**
     * 电话覆盖分析
     * @param request 请求对象
     * @return DefaultResponseBean
     * @throws Exception
     */
    @ApiOperation(value = "覆盖渠道-电话覆盖", notes = "覆盖渠道-电话覆盖")
    @PostMapping("/call")
    public DefaultResponseBean<Map<String, Object>> callData(@RequestBody CoverageReportRequest request) throws Exception {
        String startTime = request.getStartTime();
        String endTime = request.getEndTime();
        Long productId = request.getProductId();
        if(productId == null) {
            throw new Exception("参数不合法!");
        }
        if(StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            throw new Exception("参数不合法!");
        }
        Map<String, Object> map = coverageReportService.findCallListByProductIdAndTime(productId, startTime, endTime);
        DefaultResponseBean<Map<String, Object>> result = new DefaultResponseBean<>();
        result.setData(map);
        return result;
    }

    /**
     * 电话覆盖——报表数据导出
     * @param request 请求参数
     * @throws Exception
     */
    @ApiOperation(value = "拜访数据汇总-电话覆盖导出")
    @GetMapping(value = "/call/export")
    public void getCall(HttpServletRequest request) throws Exception {
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        if(StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            throw new Exception("参数不合法!");
        }
        String productId = request.getParameter("productId");
        if(StringUtils.isBlank(productId)) {
            throw new Exception("参数不合法!");
        }
        coverageReportService.exportCall(getResponse(), Long.parseLong(productId), startTime, endTime);
    }



}
