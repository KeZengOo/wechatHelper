package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.DoctorDynamicFieldValueListRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DoctorBasicDynamicFieldValueListResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DoctorBasicDynamicFieldValueResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DoctorProductDynamicFieldValueResponseBean;

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
     * 删除医生动态字段填充的值
     * @param doctorId
     * @param classification
     */
    void deleteDoctorDynamicFieldValue(Long doctorId, Integer classification);


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
    List<List<DoctorProductDynamicFieldValueResponseBean>> getDoctorProductDynamicFieldValue(Long doctorId, Long drugUserId);

}
