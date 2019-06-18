package com.nuoxin.virtual.rep.api.web.controller.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.service.v3_0.MonthlyRecruitService;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DrugUserDoctorCallRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.MonthlyRecruitRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DrugUserDoctorCallResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly.MonthlyDoctorRecruitResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly.MonthlyHistogramResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly.MonthlyHospitalRecruitResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly.MonthlyRecruitContactResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 月报招募相关
 * @author tiancun
 * @date 2019-06-12
 */
@RestController
@Api(value = "V3.1.0 月报招募相关")
@RequestMapping(value = "/monthly")
public class MonthlyRecruitController {

    @Resource
    private MonthlyRecruitService monthlyRecruitService;

    @ApiOperation(value = "医院招募")
    @PostMapping(value = "/hospital/recruit")
    public DefaultResponseBean<MonthlyHospitalRecruitResponse> getMonthlyHospitalRecruit(@RequestBody MonthlyRecruitRequest request){

        MonthlyHospitalRecruitResponse monthlyHospitalRecruit = monthlyRecruitService.getMonthlyHospitalRecruit(request);
        DefaultResponseBean<MonthlyHospitalRecruitResponse> responseBean = new DefaultResponseBean<>();
        responseBean.setData(monthlyHospitalRecruit);
        return responseBean;
    }



    @ApiOperation(value = "医生招募")
    @PostMapping(value = "/doctor/recruit")
    public DefaultResponseBean<MonthlyDoctorRecruitResponse> getMonthlyDoctorRecruit(@RequestBody MonthlyRecruitRequest request){

        MonthlyDoctorRecruitResponse monthlyDoctorRecruit = monthlyRecruitService.getMonthlyDoctorRecruit(request);
        DefaultResponseBean<MonthlyDoctorRecruitResponse> responseBean = new DefaultResponseBean<>();
        responseBean.setData(monthlyDoctorRecruit);
        return responseBean;
    }



    @ApiOperation(value = "招募导出")
    @GetMapping(value = "/recruit/export")
    public void exportMonthlyRecruit(HttpServletRequest request, HttpServletResponse response){

        MonthlyRecruitRequest bean = this.getMonthlyRecruitExportParam(request);
        monthlyRecruitService.exportMonthlyRecruit(bean, response);
    }


    @ApiOperation(value = "医生招募中联系方式统计")
    @PostMapping(value = "/recruit/contact")
    public DefaultResponseBean<MonthlyRecruitContactResponse> getMonthlyRecruitContact(@RequestBody MonthlyRecruitRequest request){

        MonthlyRecruitContactResponse monthlyRecruitContact = monthlyRecruitService.getMonthlyRecruitContact(request);
        DefaultResponseBean<MonthlyRecruitContactResponse> responseBean = new DefaultResponseBean<>();
        responseBean.setData(monthlyRecruitContact);
        return responseBean;
    }




    @ApiOperation(value = "医生招募中联系方式统计导出")
    @GetMapping(value = "/recruit/contact/export")
    public void getMonthlyRecruitExportParam(HttpServletRequest request, HttpServletResponse response){

        MonthlyRecruitRequest bean = this.getMonthlyRecruitExportParam(request);
        monthlyRecruitService.exportMonthlyRecruitContact(bean, response);
    }

    @ApiOperation(value = "医生招募中省份统计")
    @PostMapping(value = "/recruit/province")
    public DefaultResponseBean<MonthlyHistogramResponse> getMonthlyProvinceRecruitList(@RequestBody MonthlyRecruitRequest request){

        MonthlyHistogramResponse monthlyProvinceRecruitList = monthlyRecruitService.getMonthlyProvinceRecruitList(request);
        DefaultResponseBean<MonthlyHistogramResponse> responseBean = new DefaultResponseBean<>();
        responseBean.setData(monthlyProvinceRecruitList);
        return responseBean;
    }

    @ApiOperation(value = "医生招募中省份统计导出")
    @GetMapping(value = "/recruit/province/export")
    public void exportMonthlyProvinceRecruit(HttpServletRequest request, HttpServletResponse response){
        MonthlyRecruitRequest bean = this.getMonthlyRecruitExportParam(request);
        monthlyRecruitService.exportMonthlyProvinceRecruit(bean, response);
    }



    @ApiOperation(value = "医院级别招募情况汇总")
    @PostMapping(value = "/recruit/hospital/level")
    public DefaultResponseBean<MonthlyHistogramResponse> getMonthlyHospitalLevelRecruitList(@RequestBody MonthlyRecruitRequest request){

        MonthlyHistogramResponse monthlyHospitalLevelRecruitList = monthlyRecruitService.getMonthlyHospitalLevelRecruitList(request);
        DefaultResponseBean<MonthlyHistogramResponse> responseBean = new DefaultResponseBean<>();
        responseBean.setData(monthlyHospitalLevelRecruitList);
        return responseBean;
    }



    @ApiOperation(value = "医院级别招募情况汇总导出")
    @GetMapping(value = "/recruit/hospital/level/export")
    public void exportMonthlyHospitalLevelRecruit(HttpServletRequest request, HttpServletResponse response){
        MonthlyRecruitRequest bean = this.getMonthlyRecruitExportParam(request);
        monthlyRecruitService.exportMonthlyHospitalLevelRecruit(bean, response);
    }


    @ApiOperation(value = "医生科室招募情况汇总")
    @PostMapping(value = "/recruit/depart")
    public DefaultResponseBean<MonthlyHistogramResponse> getMonthlyDepartRecruitList(@RequestBody MonthlyRecruitRequest request){
        MonthlyHistogramResponse monthlyDepartRecruitList = monthlyRecruitService.getMonthlyDepartRecruitList(request);
        DefaultResponseBean<MonthlyHistogramResponse> responseBean = new DefaultResponseBean<>();
        responseBean.setData(monthlyDepartRecruitList);
        return responseBean;
    }


    @ApiOperation(value = "医生科室招募情况汇总导出")
    @GetMapping(value = "/recruit/depart/export")
    public void exportMonthlyDepartRecruit(HttpServletRequest request, HttpServletResponse response){
        MonthlyRecruitRequest bean = this.getMonthlyRecruitExportParam(request);
        monthlyRecruitService.exportMonthlyDepartRecruit(bean, response);
    }


    @ApiOperation(value = "医生级别招募情况汇总")
    @PostMapping(value = "/recruit/doctor/level")
    public DefaultResponseBean<MonthlyHistogramResponse> getMonthlyDoctorLevelRecruitList(@RequestBody MonthlyRecruitRequest request){
        MonthlyHistogramResponse monthlyDoctorLevelRecruitList = monthlyRecruitService.getMonthlyDoctorLevelRecruitList(request);
        DefaultResponseBean<MonthlyHistogramResponse> responseBean = new DefaultResponseBean<>();
        responseBean.setData(monthlyDoctorLevelRecruitList);
        return responseBean;
    }


    @ApiOperation(value = "医生级别招募情况汇总导出")
    @GetMapping(value = "/recruit/doctor/level/export")
    public void exportMonthlyDoctorLevelRecruit(HttpServletRequest request, HttpServletResponse response){
        MonthlyRecruitRequest bean = this.getMonthlyRecruitExportParam(request);
        monthlyRecruitService.exportMonthlyDoctorLevelRecruit(bean, response);
    }

    /**
     * 得到月报导出数据
     * @param request
     * @return
     */
    private MonthlyRecruitRequest getMonthlyRecruitExportParam(HttpServletRequest request) {

        String productId = request.getParameter("productId");
        if (StringUtil.isEmpty(productId)){
            throw new BusinessException(ErrorEnum.ERROR, "产品ID不能为空！");
        }
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        MonthlyRecruitRequest monthlyRecruit = new MonthlyRecruitRequest();
        monthlyRecruit.setProductId(Long.valueOf(productId));
        monthlyRecruit.setStartDate(startDate);
        monthlyRecruit.setEndDate(endDate);
        return monthlyRecruit;
    }

}
