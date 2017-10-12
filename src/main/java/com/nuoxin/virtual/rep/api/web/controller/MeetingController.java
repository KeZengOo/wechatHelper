package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.service.MeetingService;
import com.nuoxin.virtual.rep.api.web.controller.request.meeting.MeetingRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.meeting.MeetingResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * Create by tiancun on 2017/10/11
 */
@Api(value = "会议相关接口")
@RequestMapping(value = "/meeting")
@RestController
public class MeetingController extends BaseController{

    @Autowired
    private MeetingService meetingService;


    @ApiOperation(value = "会议导入接口", notes = "会议导入接口")
    @PostMapping("/import")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<Boolean>> importExcel(MultipartFile file,@RequestParam(value = "productId", required = true) Long productId,@RequestParam(value = "productName", required = true) String productName){
        boolean flag = meetingService.importExcel(file, productId, productName);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(flag);
        return ResponseEntity.ok(responseBean);

    }


    @ApiOperation(value = "会议列表接口", notes = "会议列表接口")
    @PostMapping("/getList")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<PageResponseBean<MeetingResponseBean>>> getList(@RequestBody MeetingRequestBean bean){

        PageResponseBean<MeetingResponseBean> pageResponseBean = meetingService.getList(bean);

        DefaultResponseBean<PageResponseBean<MeetingResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(pageResponseBean);
        return ResponseEntity.ok(responseBean);
    }



    @ApiOperation(value = "会议删除接口", notes = "会议删除接口")
    @GetMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<Boolean>> delete(@PathVariable(value = "id") Long id){

        boolean flag = meetingService.delete(id);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(flag);
        return ResponseEntity.ok(responseBean);
    }


    @ApiOperation(value = "会议修改接口", notes = "会议修改接口")
    @PostMapping("/update")
    @ResponseBody
    public void update(MeetingRequestBean bean){

    }

}
