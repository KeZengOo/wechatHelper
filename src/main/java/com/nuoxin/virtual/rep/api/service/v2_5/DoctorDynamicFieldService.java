package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.DoctorBasicDynamicFieldValueListRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.DoctorDynamicFieldValueListRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.DoctorQuestionnaireDetailRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.questionnaire.ProductQuestionnaireRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DoctorBasicDynamicFieldValueListResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DynamicFieldQuestionDetailResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.ProductDynamicFieldQuestionnaireResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.ProductQuestionnaireResponseBean;

import java.util.List;

/**
 * 医生动态字段相关业务接口
 * @author tiancun
 * @date 2018-09-17
 */
public interface DoctorDynamicFieldService {

    /**
     * 新增医生的动态字段填充的值
     * @param bean
     */
    void addDoctorDynamicFieldValue(DoctorDynamicFieldValueListRequestBean bean);


    /**
     * 新增医生的动态字段填充的值，关联上每次拜访的callId
     * @param bean
     */
    void addDoctorCallDynamicFieldValue(DoctorDynamicFieldValueListRequestBean bean);

    /**
     * 新增医生的基本信息医院信息动态字段填充的值
     * @param bean
     */
    void addDoctorBasicDynamicFieldValue(DoctorBasicDynamicFieldValueListRequestBean bean);


    /**
     * 新增医生的基本信息医院信息动态字段填充的值,关联上每次拜访的callId
     * @param bean
     */
    void addDoctorBasicCallDynamicFieldValue(DoctorBasicDynamicFieldValueListRequestBean bean);


    /**
     * 删除医生动态字段填充的值
     * @param doctorId
     * @param classification
     */
    void deleteDoctorDynamicFieldValue(Long doctorId, Integer classification);


    /**
     * 删除医生动态字段填充的值，关联上
     * @param doctorId
     * @param classification
     * @param callId
     */
    void deleteDoctorCallDynamicFieldValue(Long doctorId, Integer classification, Long callId);


    /**
     * 得到医生的基本信息动态字段填充的值
     * @param doctorId
     * @return
     */
    DoctorBasicDynamicFieldValueListResponseBean getDoctorBasicDynamicFieldValue(Long doctorId);


    /**
     * 得到医生的产品动态字段填充的值
     * @param doctorId
     * @param drugUserId
     * @return
     */
    List<ProductDynamicFieldQuestionnaireResponseBean> getDoctorProductDynamicFieldValue(Long doctorId, Long drugUserId);


    /**
     * 医生详情产品信息问卷列表
     * @param bean
     * @return
     */
    PageResponseBean<ProductQuestionnaireResponseBean> getProductQuestionnairePage(ProductQuestionnaireRequestBean bean);


    /**
     * 得到医生详情问卷问题列表
     * @param bean
     * @return
     */
    List<DynamicFieldQuestionDetailResponseBean> getDynamicFieldQuestionList(DoctorQuestionnaireDetailRequestBean bean);


}
