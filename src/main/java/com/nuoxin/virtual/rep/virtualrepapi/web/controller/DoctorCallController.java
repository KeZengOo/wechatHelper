package com.nuoxin.virtual.rep.virtualrepapi.web.controller;

import com.nuoxin.virtual.rep.virtualrepapi.common.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fenggang on 9/11/17.
 */
@Api(value = "",description = "电话页面接口")
@RestController
@RequestMapping(value = "/call")
public class DoctorCallController extends BaseController {

    @ApiOperation(value = "客户电话搜索列表", notes = "客户电话搜索列表")
    @PostMapping("/doctor/page")
    public void doctorPage(HttpServletRequest request, HttpServletResponse response){

    }

    @ApiOperation(value = "客户电话历史记录", notes = "客户电话历史记录")
    @PostMapping("/doctor/history/page")
    public void doctorHistoryPage(HttpServletRequest request, HttpServletResponse response){

    }

    @ApiOperation(value = "电话顶部统计", notes = "电话顶部统计")
    @PostMapping("/stat")
    public void stat(HttpServletRequest request, HttpServletResponse response){

    }


}
