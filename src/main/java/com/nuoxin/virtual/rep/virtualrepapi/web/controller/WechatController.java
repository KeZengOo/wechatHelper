package com.nuoxin.virtual.rep.virtualrepapi.web.controller;

import com.nuoxin.virtual.rep.virtualrepapi.common.controller.BaseController;
import com.nuoxin.virtual.rep.virtualrepapi.service.WechatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "微信相关接口")
@RequestMapping(value = "/wechat")
@RestController
public class WechatController extends BaseController{

    @Autowired
    private WechatService wechatService;


    @ApiOperation(value = "微信消息导入接口", notes = "微信消息导入接口")
    @PostMapping("/importExcel")
    public void importExcel(HttpServletRequest request, HttpServletResponse response){


    }


}

