package com.nuoxin.virtual.rep.api.web.controller;

import com.alibaba.fastjson.JSON;
import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.analysis.TrendAnalysisService;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.analysis.QuestionnaireAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.analysis.TrendAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.tr.TrendResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.tr.TrendSessionStatResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.tr.TrendStatResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by fenggang on 10/11/17.
 */
@Api(value = "趋势分析", description = "趋势分析接口")
@RestController
@RequestMapping(value = "/trend/analysis")
public class TrendAnalysisController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TrendAnalysisService trendAnalysisService;

    @ApiOperation(value = "汇总统计接口-呼出", notes = "汇总统计接口")
    @PostMapping("/summation/callout")
    public DefaultResponseBean<List<TrendResponseBean>> summationCallout(@RequestBody TrendAnalysisRequestBean bean,
                                                  HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<List<TrendResponseBean>> responseBean = new DefaultResponseBean();
        String error = super.checkoutDate(bean);
        if(StringUtils.isNotEmtity(error)){
            responseBean.setCode(500);
            responseBean.setMessage(error);
            return responseBean;
        }
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
//        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(trendAnalysisService.summationCallout(bean));
        return responseBean;
    }

    @ApiOperation(value = "汇总统计接口-呼出平均", notes = "汇总统计接口")
    @PostMapping("/summation/callout/avg")
    public DefaultResponseBean<List<TrendResponseBean>> summationCalloutAvg(@RequestBody TrendAnalysisRequestBean bean,
                                                                            HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<List<TrendResponseBean>> responseBean = new DefaultResponseBean();
        String error = super.checkoutDate(bean);
        if(StringUtils.isNotEmtity(error)){
            responseBean.setCode(500);
            responseBean.setMessage(error);
            return responseBean;
        }
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
//        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(trendAnalysisService.summationCalloutAvg(bean));
        return responseBean;
    }

    @ApiOperation(value = "汇总统计接口-人数", notes = "汇总统计接口")
    @PostMapping("/summation/count")
    public DefaultResponseBean<List<TrendResponseBean>> summationCover(@RequestBody TrendAnalysisRequestBean bean,
                                                                       HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<List<TrendResponseBean>> responseBean = new DefaultResponseBean();
        String error = super.checkoutDate(bean);
        if(StringUtils.isNotEmtity(error)){
            responseBean.setCode(500);
            responseBean.setMessage(error);
            return responseBean;
        }
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
//        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(trendAnalysisService.summationCalloutCount(bean));
        return responseBean;
    }

    @ApiOperation(value = "汇总统计接口-会话", notes = "汇总统计接口")
    @PostMapping("/summation/session")
    public DefaultResponseBean<List<TrendSessionStatResponseBean>> summationSession(@RequestBody TrendAnalysisRequestBean bean,
                                                                                    HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<List<TrendSessionStatResponseBean>> responseBean = new DefaultResponseBean();
        String error = super.checkoutDate(bean);
        if(StringUtils.isNotEmtity(error)){
            responseBean.setCode(500);
            responseBean.setMessage(error);
            return responseBean;
        }
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
//        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(trendAnalysisService.summationSession(bean));
        return responseBean;
    }

    @ApiOperation(value = "呼出统计接口", notes = "呼出统计接口")
    @PostMapping("/callout")
    public DefaultResponseBean<List<TrendStatResponseBean>> callOut(@RequestBody TrendAnalysisRequestBean bean,
                                                                    HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<List<TrendStatResponseBean>> responseBean = new DefaultResponseBean();
        if(!StringUtils.isNotEmtity(bean.getDate())){
            responseBean.setCode(500);
            responseBean.setMessage("时间不能为空");
            return responseBean;
        }
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
//        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(trendAnalysisService.callOut(bean));
        return responseBean;
    }

    @ApiOperation(value = "会话统计接口", notes = "会话统计接口")
    @PostMapping("/session")
    public DefaultResponseBean<List<TrendStatResponseBean>> session(@RequestBody TrendAnalysisRequestBean bean,
                                                                HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<List<TrendStatResponseBean>> responseBean = new DefaultResponseBean();
        if(!StringUtils.isNotEmtity(bean.getDate())){
            responseBean.setCode(500);
            responseBean.setMessage("时间不能为空");
            return responseBean;
        }
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
//        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(trendAnalysisService.session(bean));
        return responseBean;
    }

}
