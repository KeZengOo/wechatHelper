package com.nuoxin.virtual.rep.api.web.controller;

import com.alibaba.fastjson.JSON;
import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.analysis.TargetAnalysisService;
import com.nuoxin.virtual.rep.api.web.controller.request.analysis.TargetAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.ta.MettingTargetResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.ta.TargetResponseBean;
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
import java.util.List;

/**
 * Created by fenggang on 10/11/17.
 */
@Api(value = "目标分析", description = "目标分析接口")
@RestController
@RequestMapping(value = "/target/analysis")
public class TargetAnalysisController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TargetAnalysisService targetAnalysisService;

    @ApiOperation(value = "汇总统计接口", notes = "汇总统计接口")
    @PostMapping("/summation")
    public DefaultResponseBean<TargetResponseBean> summation(@RequestBody TargetAnalysisRequestBean bean,
                                                  HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<TargetResponseBean> responseBean = new DefaultResponseBean();
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
        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(targetAnalysisService.summation(bean));
        return responseBean;
    }

    @ApiOperation(value = "客户统计接口-电话人数", notes = "客户统计接口")
    @PostMapping("/custom/tel/person")
    public DefaultResponseBean<TargetResponseBean> custom_tel_person(@RequestBody TargetAnalysisRequestBean bean,
                                                                    HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<TargetResponseBean> responseBean = new DefaultResponseBean();
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
        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(targetAnalysisService.custom_tel_person(bean));
        return responseBean;
    }

    @ApiOperation(value = "客户统计接口-电话次数", notes = "客户统计接口")
    @PostMapping("/custom/tel/count")
    public DefaultResponseBean<TargetResponseBean> custom_tel_count(@RequestBody TargetAnalysisRequestBean bean,
                                                                HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<TargetResponseBean> responseBean = new DefaultResponseBean();
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
        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(targetAnalysisService.custom_tel_count(bean));
        return responseBean;
    }

    @ApiOperation(value = "客户统计接口-短信人数", notes = "客户统计接口")
    @PostMapping("/custom/sms/person")
    public DefaultResponseBean<TargetResponseBean> custom_sms(@RequestBody TargetAnalysisRequestBean bean,
                                                                HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<TargetResponseBean> responseBean = new DefaultResponseBean();
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
        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(targetAnalysisService.custom_sms(bean));
        return responseBean;
    }

    @ApiOperation(value = "客户统计接口-微信人数", notes = "客户统计接口")
    @PostMapping("/custom/wechat/person")
    public DefaultResponseBean<TargetResponseBean> custom_wechat(@RequestBody TargetAnalysisRequestBean bean,
                                                                HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<TargetResponseBean> responseBean = new DefaultResponseBean();
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
        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(targetAnalysisService.custom_wechat(bean));
        return responseBean;
    }

    @ApiOperation(value = "客户统计接口-电话总时长", notes = "客户统计接口")
    @PostMapping("/custom/tel/sum")
    public DefaultResponseBean<TargetResponseBean> custom_tel_sum(@RequestBody TargetAnalysisRequestBean bean,
                                                                HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<TargetResponseBean> responseBean = new DefaultResponseBean();
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
        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(targetAnalysisService.custom_tel_sum(bean));
        return responseBean;
    }

    @ApiOperation(value = "客户统计接口-电话平均时长", notes = "客户统计接口")
    @PostMapping("/custom/tel/avg")
    public DefaultResponseBean<TargetResponseBean> custom_tel_avg(@RequestBody TargetAnalysisRequestBean bean,
                                                                HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<TargetResponseBean> responseBean = new DefaultResponseBean();
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
        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(targetAnalysisService.custom_tel_avg(bean));
        return responseBean;
    }

    @ApiOperation(value = "会议统计接口", notes = "会议统计接口")
    @PostMapping("/meeting")
    public DefaultResponseBean<MettingTargetResponseBean> meeting(@RequestBody TargetAnalysisRequestBean bean,
                                               HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<MettingTargetResponseBean> responseBean = new DefaultResponseBean();
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
        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(targetAnalysisService.meeting(bean));
        return responseBean;
    }

}
