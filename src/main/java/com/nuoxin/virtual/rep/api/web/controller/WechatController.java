package com.nuoxin.virtual.rep.api.web.controller;


import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.service.WechatService;
import com.nuoxin.virtual.rep.api.web.controller.request.wechat.WechatMessageRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.wechat.WechatMessageResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "微信相关接口")
@RequestMapping(value = "/wechat")
@RestController
public class WechatController extends BaseController {

    @Autowired
    private WechatService wechatService;


    @ApiOperation(value = "微信消息导入接口", notes = "微信消息导入接口")
    @PostMapping("/importExcel")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<Boolean>> importExcel(MultipartFile file){

        boolean b = wechatService.importExcel(file);

        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(b);

        return ResponseEntity.ok(responseBean);

    }


    @ApiOperation(value = "查询微信消息接口", notes = "查询微信消息接口")
    @PostMapping("/getList")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<PageResponseBean<WechatMessageResponseBean>>> getList(@RequestBody WechatMessageRequestBean bean){

        PageResponseBean<WechatMessageResponseBean> wechatMessageList = wechatService.getWechatMessageList(bean);

        DefaultResponseBean<PageResponseBean<WechatMessageResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(wechatMessageList);

        return ResponseEntity.ok(responseBean);

    }


}

