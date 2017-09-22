package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.service.DoctorDynamicFieldService;
import com.nuoxin.virtual.rep.api.service.DoctorDynamicFieldValueService;
import com.nuoxin.virtual.rep.api.web.controller.request.customer.DoctorDynamicFieldRequestBean;
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


//
//    @ApiOperation(value = "添加动态新增的字段的值")
//    @PostMapping("/addValues")
//    //public ResponseEntity<DefaultResponseBean<Boolean>> add(@RequestBody DoctorDynamicFieldValueRequestBean bean) {
//    public ResponseEntity<DefaultResponseBean<Boolean>> add(@RequestBody List<DoctorDynamicFieldValueRequestBean> list) {
//        DoctorDynamicFieldValueRequestBean bean = new DoctorDynamicFieldValueRequestBean();
//        //bean.setDoctorId(doctorId);
//        //bean.setMap(map);
//        boolean flag = doctorDynamicFieldValueService.add(bean);
//        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
//        responseBean.setData(flag);
//        return ResponseEntity.ok(responseBean);
//    }


    @ApiOperation(value = "添加动态新增的字段")
    @PostMapping("/addField")
    public ResponseEntity<DefaultResponseBean<Long>> addField(@RequestBody DoctorDynamicFieldRequestBean bean) {

        Long id = doctorDynamicFieldService.add(bean);
        DefaultResponseBean<Long> responseBean = new DefaultResponseBean<>();
        responseBean.setData(id);
        return ResponseEntity.ok(responseBean);
    }



    @ApiOperation(value = "获取所有动态新增的字段")
    @GetMapping("/getFields")
    public ResponseEntity<DefaultResponseBean<List<DoctorDynamicFieldResponseBean>>> getFields() {

        List<DoctorDynamicFieldResponseBean> list = doctorDynamicFieldService.getList();
        DefaultResponseBean<List<DoctorDynamicFieldResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(list);
        return ResponseEntity.ok(responseBean);
    }


}
