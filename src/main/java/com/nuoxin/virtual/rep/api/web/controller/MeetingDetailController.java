package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.service.MeetingDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Create by tiancun on 2017/10/11
 */
@Api(value = "会议详情接口")
@RequestMapping(value = "/meeting/detail")
@RestController
public class MeetingDetailController extends BaseController{

    @Autowired
    private MeetingDetailService meetingDetailService;

    @ApiOperation(value = "会议详情导入接口", notes = "会议详情导入接口")
    @PostMapping("/import")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<Boolean>> importExcel(MultipartFile file, @RequestParam(value = "meetingId", required = true) Long meetingId){
        boolean flag = meetingDetailService.importExcel(meetingId, file);

        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(flag);
        return ResponseEntity.ok(responseBean);
    }

}
