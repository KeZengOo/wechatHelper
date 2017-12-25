package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.hcp.HcpBasicFieldRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.customer.DoctorDymamicFieldValueResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorBasicInfoResponseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Create by tiancun on 2017/10/19
 */
public interface DynamicFieldMapper {

    //查询出动态添加的医生的基本信息
    List<DoctorBasicInfoResponseBean> getDoctorBasicInfo(@Param(value = "doctorId") Long doctorId);


    /**
     * 删除指定医生id 和分类的字段
     * @param doctorId
     * @param classification
     */
    void deleteAllByDoctorIdAndClassification(@Param(value = "doctorId") Long doctorId,@Param(value = "classification") Integer classification);


    /**
     * 插入医生的动态字段
     * @param bean
     * @return
     */
    Long insertHcpBasicField(HcpBasicFieldRequestBean bean);



    /**
     * 插入医生的动态字段历史
     * @param bean
     * @return
     */
    Long insertHcpBasicFieldHistory(HcpBasicFieldRequestBean bean);



}
