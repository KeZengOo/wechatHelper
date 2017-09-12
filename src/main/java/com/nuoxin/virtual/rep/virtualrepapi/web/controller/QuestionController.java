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
@Api(value = "",description = "问卷接口")
@RestController
@RequestMapping(value = "/question")
public class QuestionController extends BaseController {

    @ApiOperation(value = "添加问卷", notes = "添加问卷")
    @PostMapping("/save")
    public void save(HttpServletRequest request, HttpServletResponse response){

    }

    @ApiOperation(value = "修改问卷", notes = "修改问卷")
    @PostMapping("/update")
    public void update(HttpServletRequest request, HttpServletResponse response){

    }

    @ApiOperation(value = "获取单个问卷", notes = "获取单个问卷")
    @GetMapping("/details/{id}")
    public void get(@PathVariable Long id,
                    HttpServletRequest request, HttpServletResponse response){

    }

    @PostMapping("/page")
    @ApiOperation(value = "问卷列表", notes = "问卷列表")
    public void page(HttpServletRequest request, HttpServletResponse response){

    }

}
