package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fenggang on 9/18/17.
 */
@Api(value = "",description = "短信接口")
@RestController
@RequestMapping(value = "/sms")
public class SmsSendController extends BaseController {

    @ApiOperation(value = "短信模板", notes = "短信模板")
    @GetMapping("/update")
    public DefaultResponseBean<Object> smsTemplate(HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean<Object> responseBean = new DefaultResponseBean<>();
        return responseBean;
    }

    @ApiOperation(value = "短信发送", notes = "短信发送")
    @PostMapping("/update")
    public DefaultResponseBean<Boolean> send(HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        return responseBean;
    }
}
