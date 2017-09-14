package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.service.DoctorService;
import com.nuoxin.virtual.rep.api.web.controller.request.QueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.doctor.DoctorRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorDetailsResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorStatResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fenggang on 9/11/17.
 */
@Api(value = "",description = "客户信息接口")
@RestController
@RequestMapping(value = "/doctor")
public class DoctorController extends BaseController {

    @Autowired
    private DoctorService doctorService;

    @ApiOperation(value = "获取医生信息", notes = "获取医生信息")
    @GetMapping("/details/{doctorId}")
    public DefaultResponseBean<DoctorDetailsResponseBean> doctorDetails(@PathVariable Long doctorId,
                                                                        HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        responseBean.setData(doctorService.findById(doctorId));
        return responseBean;
    }

    @ApiOperation(value = "获取医生列表", notes = "获取医生列表")
    @PostMapping("/page")
    public DefaultResponseBean<PageResponseBean<DoctorResponseBean>> page(@RequestBody QueryRequestBean bean,
                                                                          HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        bean.setDrugUserId(super.getLoginId(request));
        responseBean.setData(doctorService.page(bean));
        return responseBean;
    }

    @ApiOperation(value = "客户头部统计", notes = "客户头部统计")
    @PostMapping("/top/stat")
    public DefaultResponseBean<DoctorStatResponseBean> stat(HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        responseBean.setData(doctorService.stat(super.getLoginId(request)));
        return responseBean;
    }

    @ApiOperation(value = "医生保存", notes = "医生保存")
    @PostMapping("/save")
    public DefaultResponseBean<Boolean> save(@RequestBody DoctorRequestBean bean,
                                             HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        bean.setDrugUserId(super.getLoginId(request));
        responseBean.setData(doctorService.save(bean));
        return responseBean;
    }

    @ApiOperation(value = "医生excle导入", notes = "医生excle导入")
    @PostMapping("/excel")
    public DefaultResponseBean<Boolean> excel(MultipartFile file,
                                              HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        return responseBean;
    }
}
