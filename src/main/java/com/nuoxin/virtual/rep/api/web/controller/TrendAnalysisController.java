package com.nuoxin.virtual.rep.api.web.controller;

import com.alibaba.fastjson.JSON;
import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.web.controller.request.analysis.TrendAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.tr.TrendResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by fenggang on 10/11/17.
 */
@Api(value = "趋势分析", description = "趋势分析接口")
@RestController
@RequestMapping(value = "/trend/analysis")
public class TrendAnalysisController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ApiOperation(value = "汇总统计接口-呼出", notes = "汇总统计接口")
    @PostMapping("/summation/callout")
    public DefaultResponseBean<List<TrendResponseBean>> summationCallout(@RequestBody TrendAnalysisRequestBean bean,
                                                  HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<List<TrendResponseBean>> responseBean = new DefaultResponseBean();

        return responseBean;
    }

    @ApiOperation(value = "汇总统计接口-呼出平均", notes = "汇总统计接口")
    @PostMapping("/summation/callout/avg")
    public DefaultResponseBean<List<TrendResponseBean>> summationCalloutAvg(@RequestBody TrendAnalysisRequestBean bean,
                                                                            HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<List<TrendResponseBean>> responseBean = new DefaultResponseBean();

        return responseBean;
    }

    @ApiOperation(value = "汇总统计接口-覆盖", notes = "汇总统计接口")
    @PostMapping("/summation/cover")
    public DefaultResponseBean<List<TrendResponseBean>> summationCover(@RequestBody TrendAnalysisRequestBean bean,
                                                                       HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<List<TrendResponseBean>> responseBean = new DefaultResponseBean();

        return responseBean;
    }

    @ApiOperation(value = "汇总统计接口-会话", notes = "汇总统计接口")
    @PostMapping("/summation/session")
    public DefaultResponseBean<List<TrendResponseBean>> summationSession(@RequestBody TrendAnalysisRequestBean bean,
                                                                            HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<List<TrendResponseBean>> responseBean = new DefaultResponseBean();

        return responseBean;
    }

    @ApiOperation(value = "呼出统计接口", notes = "呼出统计接口")
    @PostMapping("/callout")
    public DefaultResponseBean<List<TrendResponseBean>> callOut(@RequestBody TrendAnalysisRequestBean bean,
                                                HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<List<TrendResponseBean>> responseBean = new DefaultResponseBean();

        return responseBean;
    }

    @ApiOperation(value = "会话统计接口", notes = "会话统计接口")
    @PostMapping("/session")
    public DefaultResponseBean<List<TrendResponseBean>> session(@RequestBody TrendAnalysisRequestBean bean,
                                                                HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<List<TrendResponseBean>> responseBean = new DefaultResponseBean();

        return responseBean;
    }

}
