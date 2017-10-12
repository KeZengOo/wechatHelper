package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
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

    @ApiOperation(value = "问卷次数，参与人数，图表接口", notes = "问卷次数，参与人数，图表接口")
    @PostMapping("/summation")
    public DefaultResponseBean<Boolean> summation(@RequestBody QuestionnaireAnalysisRequestBean bean,
                                                  HttpServletRequest request, HttpServletResponse response) {
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean();

        return responseBean;
    }

}
