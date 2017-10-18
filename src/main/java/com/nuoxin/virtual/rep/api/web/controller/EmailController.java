package com.nuoxin.virtual.rep.api.web.controller;

import com.alibaba.fastjson.JSON;
import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.service.EmailService;
import com.nuoxin.virtual.rep.api.web.controller.request.EmailQueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.EmailRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.EmailResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fenggang on 10/11/17.
 */
@Api(value = "", description = "邮件接口")
@RestController
@RequestMapping(value = "/email")
public class EmailController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EmailService emailService;

    @ApiOperation(value = "邮件发送", notes = "邮件发送")
    @PostMapping("/send/ids")
    public DefaultResponseBean<Boolean> sendIds(@RequestBody EmailRequestBean bean,
                                             HttpServletRequest request, HttpServletResponse response) throws MessagingException {
        logger.info("{}接口请求数据【】{}",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        if(!StringUtils.isNotEmtity(bean.getDoctorIds())){
            responseBean.setCode(500);
            responseBean.setMessage("发送医生不能为空");
            return responseBean;
        }
        responseBean.setData(emailService.commonEmailSendIds(bean));
        return responseBean;
    }

    @ApiOperation(value = "邮件发送", notes = "邮件发送")
    @PostMapping("/send/to")
    public DefaultResponseBean<Boolean> sendEmail(@RequestBody EmailRequestBean bean,
                                             HttpServletRequest request, HttpServletResponse response) throws MessagingException {
        logger.info("{}接口请求数据【】{}",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        if(!StringUtils.isNotEmtity(bean.getDoctorIds())){
            responseBean.setCode(500);
            responseBean.setMessage("发送医生不能为空");
            return responseBean;
        }
        responseBean.setData(emailService.commonEmailSendTo(bean));
        return responseBean;
    }

    @ApiOperation(value = "邮件历史", notes = "邮件历史")
    @PostMapping("/page")
    public DefaultResponseBean<PageResponseBean<EmailResponseBean>> page(@RequestBody EmailQueryRequestBean bean,
                                                                         HttpServletRequest request, HttpServletResponse response){
        logger.info("{}接口请求数据【】{}",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<PageResponseBean<EmailResponseBean>> responseBean = new DefaultResponseBean<>();
        if(bean.getDoctorId()==null || bean.getDoctorId()==0l){
            responseBean.setCode(500);
            responseBean.setMessage("医生id不能为空");
            return responseBean;
        }
        responseBean.setData(emailService.page(bean));
        return responseBean;
    }
}
