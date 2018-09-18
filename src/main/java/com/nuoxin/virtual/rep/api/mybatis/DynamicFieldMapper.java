package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.hcp.HcpBasicFieldRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.DoctorDynamicFieldValueRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.DoctorDynamicFieldRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.DynamicFieldProductRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.customer.DoctorDynamicFieldResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorBasicInfoResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.hcp.HcpBasicInfoHistoryResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.hcp.HcpDynamicRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DoctorBasicDynamicFieldValueResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DoctorProductDynamicFieldValueResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DynamicFieldProductResponseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;


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

    /**
     * 如果是客户设置返回的是医生的基本信息和医院信息
     * 如果是产品设置返回的是处方和拜访记录
     * @param bean 产品id,只有产品设置的时候才会有这个字段
     * @return
     */
    List<DoctorDynamicFieldResponseBean> getDoctorDynamicFieldList(DoctorDynamicFieldRequestBean bean);

    /**
     * 动态字段修改
     * @param bean
     */
    void updateDoctorDynamicField(DoctorDynamicFieldRequestBean bean);

    /**
     * 动态字段删除
     * @param id
     */
    void deleteDoctorDynamicField(@Param(value="id") Long id);

    /**
     * 动态字段新增
     * @param bean
     * @return
     */
    void insertDoctorDynamicField(DoctorDynamicFieldRequestBean bean);


    /**
     * 得到医生基本信息动态字段填充的值
     * @param doctorId
     * @return
     */
    List<DoctorBasicDynamicFieldValueResponseBean> getDoctorBasicDynamicFieldValue(@Param(value = "doctorId") Long doctorId, @Param(value="classification") Integer classification);


    /**
     * 得到医生产品信息动态字段填充的值
     * @param doctorId
     * @param productId
     * @return
     */
    List<DoctorProductDynamicFieldValueResponseBean> getDoctorProductDynamicFieldValue(@Param(value = "doctorId") Long doctorId,@Param(value = "productId") Long productId);

    /**
     * 新增医生动态字段填充的值
     * @param doctorId
     * @param list
     */
    void addDoctorBasicDynamicFieldValue(@Param(value="doctorId") Long doctorId,@Param(value = "list") List<DoctorDynamicFieldValueRequestBean> list);

    /**
     * 删除医生动态字段填充的值
     * @param doctorId 医生id
     * @param classification 分类：目前1基本信息，2医生的处方信息，3之前拜访记录，4分析，5是医院信息
     */
    void deleteDoctorDynamicFieldValue(@Param(value="doctorId") Long doctorId,@Param(value="classification") Integer classification);


    /**
     * 找出填的字段中的必填字段
     * @param list
     * @return
     */
    List<Long> getRequiredFieldId(@Param(value = "list") List<Long> list);

    /**
     * 客户设置产品信息列表
     * @param bean
     * @return
     */
    List<DynamicFieldProductResponseBean> getDynamicFieldProductList(DynamicFieldProductRequestBean bean);

    /**
     * 客户设置产品信息列表总数
     * @param bean
     * @return
     */
    Integer getDynamicFieldProductListCount(DynamicFieldProductRequestBean bean);
}
