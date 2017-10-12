package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.web.controller.request.TargetAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionnaireAnalysisRequestBean;
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

/**
 * Created by fenggang on 10/11/17.
 */
@Api(value = "", description = "目标分析接口")
@RestController
@RequestMapping(value = "/target/analysis")
public class TargetAnalysisController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ApiOperation(value = "汇总统计接口", notes = "汇总统计接口")
    @PostMapping("/summation")
    public DefaultResponseBean<Boolean> summation(@RequestBody TargetAnalysisRequestBean bean,
                                                  HttpServletRequest request, HttpServletResponse response) {
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean();

        return responseBean;
    }

    @ApiOperation(value = "客户统计接口", notes = "客户统计接口")
    @PostMapping("/client")
    public DefaultResponseBean<Boolean> client(@RequestBody TargetAnalysisRequestBean bean,
                                                  HttpServletRequest request, HttpServletResponse response) {
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean();

        return responseBean;
    }

    @ApiOperation(value = "会议统计接口", notes = "会议统计接口")
    @PostMapping("/meeting")
    public DefaultResponseBean<Boolean> meeting(@RequestBody TargetAnalysisRequestBean bean,
                                               HttpServletRequest request, HttpServletResponse response) {
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean();

        return responseBean;
    }

}
