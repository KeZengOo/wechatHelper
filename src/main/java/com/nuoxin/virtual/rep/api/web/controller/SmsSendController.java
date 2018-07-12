package com.nuoxin.virtual.rep.api.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.service.SmsSendService;
import com.nuoxin.virtual.rep.api.service.SmsTemplateService;
import com.nuoxin.virtual.rep.api.web.controller.request.SmsSendRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.SmsTemplateResponseBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by fenggang on 9/18/17.
 */
@Api(value = "短信发送",description = "短信接口")
@RestController
@RequestMapping(value = "/sms")
public class SmsSendController extends BaseController {

    @Autowired
    private SmsSendService smsSendService;
    @Autowired
    private SmsTemplateService smsTemplateService;

    @ApiOperation(value = "短信模板", notes = "短信模板")
    @GetMapping("/template/list")
    public DefaultResponseBean<List<SmsTemplateResponseBean>> smsTemplate(HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean<List<SmsTemplateResponseBean>> responseBean = new DefaultResponseBean<>();
        List<SmsTemplateResponseBean> list = smsTemplateService.findByAll();
        responseBean.setData(list);
        return responseBean;
    }

    @ApiOperation(value = "短信发送", notes = "短信发送")
    @PostMapping("/send")
    public DefaultResponseBean<List<String>> send(@RequestBody SmsSendRequestBean bean,
                                          HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean<List<String>> responseBean = new DefaultResponseBean<>();
        bean.setDrugUserId(this.getLoginId(request));
        List<String> list = smsSendService.send(bean);
        responseBean.setData(list);
        return responseBean;
    }
}
