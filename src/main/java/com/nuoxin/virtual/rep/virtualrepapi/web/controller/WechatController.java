package com.nuoxin.virtual.rep.virtualrepapi.web.controller;

import com.nuoxin.virtual.rep.virtualrepapi.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.virtualrepapi.common.controller.BaseController;
import com.nuoxin.virtual.rep.virtualrepapi.service.WechatService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "微信相关接口")
@RequestMapping(value = "/wechat")
@RestController
public class WechatController extends BaseController{

    @Autowired
    private WechatService wechatService;





}

