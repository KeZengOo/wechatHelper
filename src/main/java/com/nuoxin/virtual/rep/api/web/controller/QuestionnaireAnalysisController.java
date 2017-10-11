package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionnaireAnalysisRequestBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fenggang on 10/11/17.
 */
@Api(value = "", description = "问卷统计接口")
@RestController
@RequestMapping(value = "/questionnaire/analysis")
public class QuestionnaireAnalysisController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ApiOperation(value = "问卷次数，参与人数，图表接口", notes = "问卷次数，参与人数，图表接口")
    @PostMapping("/summation")
    public DefaultResponseBean<Boolean> summation(@RequestBody QuestionnaireAnalysisRequestBean bean,
                                                  HttpServletRequest request, HttpServletResponse response) {
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean();

        return responseBean;
    }

    @ApiOperation(value = "问卷列表接口", notes = "问卷列接口")
    @GetMapping("/list")
    public DefaultResponseBean<Boolean> list(HttpServletRequest request, HttpServletResponse response) {
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean();

        return responseBean;
    }

    @ApiOperation(value = "问卷统计详情接口", notes = "图表问卷统计详情接口")
    @GetMapping("/details/{id}")
    public DefaultResponseBean<Boolean> details(@PathVariable Long id,
                                                HttpServletRequest request, HttpServletResponse response) {
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean();

        return responseBean;
    }
}
