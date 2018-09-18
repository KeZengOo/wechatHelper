package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;

import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.v2_5.DoctorDynamicFieldService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.DoctorDynamicFieldValueListRequestBean;

import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DoctorBasicDynamicFieldValueListResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DoctorProductDynamicFieldValueResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author tiancun
 * @date 2018-09-17
 */
@RestController
@Api(value = "V2.5医生详情动态添加的字段")
@RequestMapping(value = "/doctor/dynamic/field")
public class DoctorDynamicFieldController extends NewBaseController{

    @Resource(name="dynamic")
    private DoctorDynamicFieldService doctorDynamicFieldService;

    @SuppressWarnings("unchecked")
	@ApiOperation(value = "录入字段的对应的值", notes = "录入字段的对应的值")
    @PostMapping(value = "/value/add")
    public DefaultResponseBean<Boolean> addDoctorDynamicFieldValue(HttpServletRequest request, @RequestBody DoctorDynamicFieldValueListRequestBean bean) {
        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }

        doctorDynamicFieldService.addDoctorDynamicFieldValue(bean);

        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);

        return responseBean;
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "医生基本信息医院信息及录入的值", notes = "医生基本信息医院信息及录入的值")
    @GetMapping(value = "/basic/{doctorId}")
    public DefaultResponseBean<DoctorBasicDynamicFieldValueListResponseBean> getDoctorBasicDynamicFieldValue(HttpServletRequest request, @PathVariable(value = "doctorId") Long doctorId) {
        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }

        DoctorBasicDynamicFieldValueListResponseBean doctorBasicDynamicFieldValue = doctorDynamicFieldService.getDoctorBasicDynamicFieldValue(doctorId);

        DefaultResponseBean<DoctorBasicDynamicFieldValueListResponseBean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(doctorBasicDynamicFieldValue);
        return responseBean;
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "医生产品信息及录入的值", notes = "医生产品信息及录入的值")
    @GetMapping(value = "/product/{doctorId}")
    public DefaultResponseBean<List<List<DoctorProductDynamicFieldValueResponseBean>>> getDoctorProductDynamicFieldValue(HttpServletRequest request, @PathVariable(value = "doctorId") Long doctorId) {
        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }

        List<List<DoctorProductDynamicFieldValueResponseBean>> doctorProductDynamicFieldValue = doctorDynamicFieldService.getDoctorProductDynamicFieldValue(doctorId, user.getId());
        DefaultResponseBean<List<List<DoctorProductDynamicFieldValueResponseBean>>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(doctorProductDynamicFieldValue);
        return responseBean;
    }

}
