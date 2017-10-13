package com.nuoxin.virtual.rep.api.web.controller;

import com.alibaba.fastjson.JSON;
import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.entity.Questionnaire;
import com.nuoxin.virtual.rep.api.service.analysis.QuestionnaireAnalysisService;
import com.nuoxin.virtual.rep.api.web.controller.request.analysis.QuestionnaireAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.q.QuestionStatResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.q.QuestionnaireStatResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.question.QuestionnaireResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by fenggang on 10/11/17.
 */
@Api(value = "问卷分析", description = "问卷分析接口")
@RestController
@RequestMapping(value = "/questionnaire/analysis")
public class QuestionnaireAnalysisController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuestionnaireAnalysisService questionnaireAnalysisService;

    @ApiOperation(value = "问卷次数，参与人数，图表接口", notes = "问卷次数，参与人数，图表接口")
    @PostMapping("/summation")
    public DefaultResponseBean<QuestionnaireStatResponseBean> summation(@RequestBody QuestionnaireAnalysisRequestBean bean,
                                                                        HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<QuestionnaireStatResponseBean> responseBean = new DefaultResponseBean();
        responseBean.setData(questionnaireAnalysisService.summation(bean));
        return responseBean;
    }

    @ApiOperation(value = "问卷列表接口", notes = "问卷列接口")
    @PostMapping("/list")
    public DefaultResponseBean<List<QuestionnaireResponseBean>> list(@RequestBody QuestionnaireAnalysisRequestBean bean,
                                                         HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<List<QuestionnaireResponseBean>> responseBean = new DefaultResponseBean();
        responseBean.setData(questionnaireAnalysisService.list(bean));
        return responseBean;
    }

    @ApiOperation(value = "问卷统计详情接口", notes = "图表问卷统计详情接口")
    @PostMapping("/details/options")
    public DefaultResponseBean<List<QuestionStatResponseBean>> details(@RequestBody QuestionnaireAnalysisRequestBean bean,
                                                                       HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<List<QuestionStatResponseBean>> responseBean = new DefaultResponseBean();
        responseBean.setData(questionnaireAnalysisService.details(bean));
        return responseBean;
    }
}
