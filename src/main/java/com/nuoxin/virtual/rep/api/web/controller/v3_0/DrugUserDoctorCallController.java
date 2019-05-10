package com.nuoxin.virtual.rep.api.web.controller.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.service.v3_0.DrugUserDoctorCallService;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DailyReportRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DrugUserDoctorCallRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DailyReportResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DrugUserDoctorCallResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 电话拜访记录查询相关
 * @author tiancun
 * @date 2019-05-10
 */
@RestController
@Api(value = "V3.0.1电话拜访记录查询相关")
@RequestMapping(value = "/drug/user/doctor")
public class DrugUserDoctorCallController {

    @Resource
    private DrugUserDoctorCallService drugUserDoctorCallService;

    @ApiOperation(value = "电话拜访记录查询列表")
    @RequestMapping(value = "/call/list", method = { RequestMethod.POST})
    public DefaultResponseBean<PageResponseBean<DrugUserDoctorCallResponse>> getDrugUserDoctorCallPage(@RequestBody DrugUserDoctorCallRequest request){

        PageResponseBean<DrugUserDoctorCallResponse> drugUserDoctorCallPage = drugUserDoctorCallService.getDrugUserDoctorCallPage(request);
        DefaultResponseBean<PageResponseBean<DrugUserDoctorCallResponse>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(drugUserDoctorCallPage);
        return responseBean;
    }


}
