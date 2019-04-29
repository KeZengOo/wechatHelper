package com.nuoxin.virtual.rep.api.web.controller.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.v3_0.MyDoctorService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share.ShareRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.MyDoctorRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.ContentShareResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.MyDoctorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 我的客户相关接口
 * @author tiancun
 * @date 2019-04-28
 */
@RestController
@Api(value = "V3.0.1我的客户相关接口")
@RequestMapping(value = "/my/doctor")
public class MyDoctorController {

    @Resource
    private MyDoctorService myDoctorService;


    @ApiOperation(value = "列表", notes = "列表")
    @PostMapping(value = "/list")
    public DefaultResponseBean<PageResponseBean<MyDoctorResponse>> getDoctorPage(HttpServletRequest request, @RequestBody MyDoctorRequest bean) {


        PageResponseBean<MyDoctorResponse> doctorPage = myDoctorService.getDoctorPage(bean);
        DefaultResponseBean<PageResponseBean<MyDoctorResponse>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(doctorPage);
        return responseBean;
    }



}
