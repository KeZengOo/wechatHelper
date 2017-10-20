package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.service.ContactPlanService;
import com.nuoxin.virtual.rep.api.web.controller.request.ContactPlanQueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.ContactPlanRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.ContactPlanResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Create by tiancun on 2017/10/19
 */
@Api(value = "联系计划相关接口")
@RequestMapping(value = "/contact/plan")
@RestController
public class ContactPlanController extends BaseController{

    @Autowired
    private ContactPlanService contactPlanService;


    @ApiOperation(value = "联系计划列表接口", notes = "联系计划列表接口")
    @PostMapping("/page")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<PageResponseBean<ContactPlanResponseBean>>> page(@RequestBody ContactPlanQueryRequestBean bean, HttpServletRequest request) {
        Long drugUserId = bean.getDrugUserId();
        if (drugUserId == null || drugUserId == 0){
            drugUserId = getLoginId(request);
        }
        bean.setDrugUserId(drugUserId);
        PageResponseBean<ContactPlanResponseBean> page = contactPlanService.page(bean);
        DefaultResponseBean<PageResponseBean<ContactPlanResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(page);
        return ResponseEntity.ok(responseBean);
    }


    @ApiOperation(value = "新增或者修改联系计划列表接口", notes = "新增或者修改联系计划列表接口")
    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<Boolean>> save(@RequestBody ContactPlanRequestBean bean, HttpServletRequest request) {
        Long drugUserId = bean.getDrugUserId();
        if (drugUserId == null || drugUserId == 0){
            drugUserId = getLoginId(request);
        }

        bean.setDrugUserId(drugUserId);
        contactPlanService.save(bean);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);
        return ResponseEntity.ok(responseBean);
    }

    @ApiOperation(value = "更新联系计划状态接口", notes = "更新联系计划状态接口")
    @PostMapping("/updateStatus/{id}")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<Boolean>> save(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        contactPlanService.updateStatus(id);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);
        return ResponseEntity.ok(responseBean);
    }

}
