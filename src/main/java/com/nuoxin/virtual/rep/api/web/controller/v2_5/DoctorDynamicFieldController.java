package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.v2_5.DoctorDynamicFieldService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.DoctorBasicDynamicFieldValueListRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.DoctorDynamicFieldValueListRequestBean;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.DoctorQuestionnaireDetailRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.questionnaire.ProductQuestionnaireRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.*;
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
	@ApiOperation(value = "产品信息录入字段的对应的值", notes = "产品信息录入字段的对应的值")
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
    @ApiOperation(value = "产品信息录入字段的对应的值,关联拜访的callId", notes = "产品信息录入字段的对应的值,关联拜访的callId")
    @PostMapping(value = "/call/value/add")
    public DefaultResponseBean<Boolean> addDoctorCallDynamicFieldValue(HttpServletRequest request, @RequestBody DoctorDynamicFieldValueListRequestBean bean) {
        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }

        doctorDynamicFieldService.addDoctorCallDynamicFieldValue(bean);

        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);

        return responseBean;
    }


    @SuppressWarnings("unchecked")
    @ApiOperation(value = "基本信息录入字段的对应的值", notes = "基本信息录入字段的对应的值")
    @PostMapping(value = "/basic/value/add")
    public DefaultResponseBean<Boolean> addBasicDoctorDynamicFieldValue(HttpServletRequest request, @RequestBody DoctorBasicDynamicFieldValueListRequestBean bean) {
        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }

        doctorDynamicFieldService.addDoctorBasicDynamicFieldValue(bean);

        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);

        return responseBean;
    }


    @SuppressWarnings("unchecked")
    @ApiOperation(value = "基本信息录入字段的对应的值,关联拜访的callId", notes = "基本信息录入字段的对应的值,关联拜访的callId")
    @PostMapping(value = "/call/basic/value/add")
    public DefaultResponseBean<Boolean> addBasicDoctorCallDynamicFieldValue(HttpServletRequest request, @RequestBody DoctorBasicDynamicFieldValueListRequestBean bean) {
        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }

        doctorDynamicFieldService.addDoctorBasicCallDynamicFieldValue(bean);

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
    public DefaultResponseBean<List<ProductDynamicFieldQuestionnaireResponseBean>> getDoctorProductDynamicFieldValue(HttpServletRequest request, @PathVariable(value = "doctorId") Long doctorId) {
        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }

        List<ProductDynamicFieldQuestionnaireResponseBean> doctorProductDynamicFieldValue = doctorDynamicFieldService.getDoctorProductDynamicFieldValue(doctorId, user.getId());
        DefaultResponseBean<List<ProductDynamicFieldQuestionnaireResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(doctorProductDynamicFieldValue);
        return responseBean;
    }


    @ApiOperation(value = "医生产品信息问卷列表", notes = "医生产品信息问卷列表")
    @PostMapping(value = "/product/questionnaire/list")
    public DefaultResponseBean<PageResponseBean<ProductQuestionnaireResponseBean>> getProductQuestionnairePage(HttpServletRequest request, @RequestBody ProductQuestionnaireRequestBean bean) {
        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }

        PageResponseBean<ProductQuestionnaireResponseBean> productQuestionnairePage = doctorDynamicFieldService.getProductQuestionnairePage(bean);
        DefaultResponseBean<PageResponseBean<ProductQuestionnaireResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(productQuestionnairePage);
        return responseBean;
    }



    @SuppressWarnings("unchecked")
    @ApiOperation(value = "医生产品信息问卷详情", notes = "医生产品信息问卷详情")
    @PostMapping(value = "/product/questionnaire/detail")
    public DefaultResponseBean<List<DynamicFieldQuestionDetailResponseBean>> getDynamicFieldQuestionList(@RequestBody DoctorQuestionnaireDetailRequestBean bean) {

        List<DynamicFieldQuestionDetailResponseBean> dynamicFieldQuestionList = doctorDynamicFieldService.getDynamicFieldQuestionList(bean);
        DefaultResponseBean<List<DynamicFieldQuestionDetailResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(dynamicFieldQuestionList);
        return responseBean;
    }



}
