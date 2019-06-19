package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.service.DoctorDynamicFieldService;
import com.nuoxin.virtual.rep.api.service.DoctorDynamicFieldValueService;
import com.nuoxin.virtual.rep.api.web.controller.request.customer.DoctorDynamicFieldRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.customer.DoctorDymamicFieldValueResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.customer.DoctorDynamicFieldResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户设置
 * Create by tiancun on 2017/9/22
 */
@RestController
@Api(value = "客户设置")
@RequestMapping(value = "/customer/setup")
public class CustomerSetupController {

    @Autowired
    private DoctorDynamicFieldValueService doctorDynamicFieldValueService;
    @Autowired
    private DoctorDynamicFieldService doctorDynamicFieldService;

    @ApiOperation(value = "添加动态新增的字段", notes = "添加动态新增的字段")
    @PostMapping("/addField")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<Boolean>> addField(@RequestBody List<DoctorDynamicFieldRequestBean> list) {

        Boolean flag = doctorDynamicFieldService.add(list);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(flag);
        return ResponseEntity.ok(responseBean);
    }

    @ApiOperation(value = "删除动态字段接口", notes = "删除动态字段接口")
    @GetMapping("/deleteField/{id}")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<Boolean>> deleteField(@PathVariable(value = "id") Long id) {
        Boolean flag = doctorDynamicFieldService.delete(id);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(flag);
        return ResponseEntity.ok(responseBean);
    }

    @ApiOperation(value = "修改动态新增的字段", notes = "修改动态新增的字段")
    @PostMapping("/updateField")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<Boolean>> addField(@RequestBody DoctorDynamicFieldRequestBean bean) {
        Boolean flag = doctorDynamicFieldService.update(bean);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(flag);
        return ResponseEntity.ok(responseBean);
    }

    @ApiOperation(value = "获取所有动态新增的字段")
    @GetMapping("/getFields")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<List<DoctorDynamicFieldResponseBean>>> getFields() {
        List<DoctorDynamicFieldResponseBean> list = doctorDynamicFieldService.getList();
        DefaultResponseBean<List<DoctorDynamicFieldResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(list);
        return ResponseEntity.ok(responseBean);
    }

    @ApiOperation(value = "获取某个医生动态添加的字段的值")
    @GetMapping("/getFieldValues/{id}")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<List<DoctorDymamicFieldValueResponseBean>>> getDoctorDymamicFieldValueList(@PathVariable(value = "id") Long id) {
        List<DoctorDymamicFieldValueResponseBean> list = doctorDynamicFieldValueService.getDoctorDymamicFieldValueList(id);
        DefaultResponseBean<List<DoctorDymamicFieldValueResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(list);
        return ResponseEntity.ok(responseBean);
    }

}