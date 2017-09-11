package com.nuoxin.virtual.rep.virtualrepapi.web.controller;

import com.nuoxin.virtual.rep.virtualrepapi.common.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fenggang on 9/11/17.
 */
@Api(value = "",description = "获取客户信息接口")
@RestController
@RequestMapping(value = "/doctor")
public class DoctorController extends BaseController {

    @ApiOperation(value = "获取医生信息", notes = "获取医生信息")
    @GetMapping("/details/{id}")
    public void doctorDetails(@PathVariable Long id,
                              HttpServletRequest request, HttpServletResponse response){

    }
}
