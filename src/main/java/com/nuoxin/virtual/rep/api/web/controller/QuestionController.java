package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionQueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionnaireRequestBean;
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
    public DefaultResponseBean save(@RequestBody QuestionnaireRequestBean bean,
                     HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean();
        return responseBean;
    }

    @ApiOperation(value = "修改问卷", notes = "修改问卷")
    @PostMapping("/update")
    public DefaultResponseBean<Boolean> update(@RequestBody QuestionnaireRequestBean bean,
                       HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        return responseBean;
    }

    @ApiOperation(value = "获取单个问卷", notes = "获取单个问卷")
    @GetMapping("/details/{id}")
    public DefaultResponseBean<QuestionnaireRequestBean> get(@PathVariable Long id,
                    HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        return responseBean;
    }

    @PostMapping("/page")
    @ApiOperation(value = "问卷列表", notes = "问卷列表")
    public DefaultResponseBean<PageResponseBean<QuestionnaireRequestBean>> page(@RequestBody QuestionQueryRequestBean bean,
                                                      HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        return responseBean;
    }

}
