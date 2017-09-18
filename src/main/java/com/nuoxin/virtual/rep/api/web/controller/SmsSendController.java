package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.web.controller.request.SmsSendRequestBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/template/list")
    public DefaultResponseBean<Object> smsTemplate(HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean<Object> responseBean = new DefaultResponseBean<>();
        return responseBean;
    }

    @ApiOperation(value = "短信发送", notes = "短信发送")
    @PostMapping("/send")
    public DefaultResponseBean<Boolean> send(@RequestBody SmsSendRequestBean bean,
                                             HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        return responseBean;
    }
}
