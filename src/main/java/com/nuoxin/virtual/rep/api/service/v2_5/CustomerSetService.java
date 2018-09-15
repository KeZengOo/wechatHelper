package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.DoctorDynamicFieldRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.customer.DoctorDynamicFieldResponseBean;

import java.util.List;

/**
 * 新版客户设置相关业务
 * @author tiancun
 * @date 2018-09-12
 */
public interface CustomerSetService {


    /**
     * 返回医生基本信息和医院新增的动态字段的
     * @return
     */
    List<DoctorDynamicFieldResponseBean> getBasicAndHospitalFieldList();


    /**
     * 客户设置医生动态字段修改
     * @param bean
     */
    void updateDoctorDynamicField(DoctorDynamicFieldRequestBean bean);



    /**
     * 客户设置医生动态字段删除
     * @param id
     */
    void deleteDoctorDynamicField(Long id);


}
