package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.hcp.HcpBasicFieldRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.customer.DoctorDymamicFieldValueResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.customer.DoctorDynamicFieldResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorBasicInfoResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.hcp.HcpBasicInfoHistoryResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.hcp.HcpDynamicRequestBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Create by tiancun on 2017/10/19
 */
public interface DynamicFieldMapper {

    //查询出动态添加的医生的基本信息
    List<DoctorBasicInfoResponseBean> getDoctorBasicInfo(@Param(value = "doctorId") Long doctorId);


    /**
     * 每个字段修改的历史
     * @param bean
     * @return
     */
    List<HcpBasicInfoHistoryResponseBean> getHcpBasicInfoHistoryList(HcpDynamicRequestBean bean);
    /**
     * 查询出每个动态字段填入的值
     * @param doctorId
     * @param fieldId
     * @return
     */
    DoctorBasicInfoResponseBean getDoctorBasicInfoValue(@Param(value = "doctorId") Long doctorId,@Param(value = "fieldId") Long fieldId);


    //查询出医生固定的字段
    List<DoctorBasicInfoResponseBean> getFixedField();


    /**
     * 删除之前先得到字段
     * @param doctorId
     * @param classification
     * @return
     */
    List<DoctorBasicInfoResponseBean> getFieldByDoctorIdAndClassification(@Param(value = "doctorId") Long doctorId,@Param(value = "classification") Integer classification);


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


    /**
     * 获取所有的动态字段
     * @return
     */
    List<DoctorDynamicFieldResponseBean> getList();


}
