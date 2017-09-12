package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fenggang on 9/11/17.
 */
@Api(value = "",description = "客户信息接口")
@RestController
@RequestMapping(value = "/doctor")
public class DoctorController extends BaseController {

    @ApiOperation(value = "获取医生信息", notes = "获取医生信息")
    @GetMapping("/details/{id}")
    public void doctorDetails(@PathVariable Long id,
                              HttpServletRequest request, HttpServletResponse response){

    }

    @ApiOperation(value = "获取医生列表", notes = "获取医生列表")
    @PostMapping("/page")
    public void page(HttpServletRequest request, HttpServletResponse response){

    }

    @ApiOperation(value = "客户头部统计", notes = "客户头部统计")
    @PostMapping("/stat")
    public void stat(HttpServletRequest request, HttpServletResponse response){

    }

    @ApiOperation(value = "医生保存", notes = "医生保存")
    @PostMapping("/save")
    public void save(HttpServletRequest request, HttpServletResponse response){

    }

    @ApiOperation(value = "医生excle导入", notes = "医生excle导入")
    @PostMapping("/excel")
    public void excel(HttpServletRequest request, HttpServletResponse response){

    }
}
