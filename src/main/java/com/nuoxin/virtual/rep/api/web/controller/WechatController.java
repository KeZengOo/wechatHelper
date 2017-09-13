package com.nuoxin.virtual.rep.api.web.controller;


import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.service.WechatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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


}

