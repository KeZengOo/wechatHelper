package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.DynamicFieldResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.DynamicFieldValueResponse;
import com.nuoxin.virtual.rep.api.web.controller.request.hcp.HcpBasicFieldRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.DoctorDynamicFieldValueRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.questionnaire.ProductQuestionnaireRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.DoctorDynamicFieldRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.DynamicFieldProductRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.statistics.DailyStatisticsRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.customer.DoctorDynamicFieldResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorBasicInfoResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.hcp.HcpBasicInfoHistoryResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.hcp.HcpDynamicRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.*;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.DoctorDynamicExtendResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.SearchDynamicFieldResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.single.DoctorAddDynamicFieldResponseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;


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
     * 得到产品扩展类型(分型,潜力); 用来判断字段是否存在
     * @param bean
     * @return
     */
    Long getProductExtendTypeField(DoctorDynamicFieldRequestBean bean);
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
    @Deprecated
    List<DoctorBasicDynamicFieldValueResponseBean> getDoctorBasicDynamicFieldValue(@Param(value = "doctorId") Long doctorId, @Param(value="classification") Integer classification);


    /**
     * 得到医生基本信息动态字段
     * @param doctorId
     * @return
     */
    List<DoctorBasicDynamicFieldValueResponseBean> getDoctorBasicDynamicField(@Param(value = "doctorId") Long doctorId, @Param(value="classification") Integer classification);


    /**
     * 得到医生产品信息动态字段填充的值
     * @param doctorId
     * @param productId
     * @return
     */
    @Deprecated
    List<DoctorProductDynamicFieldValueResponseBean> getDoctorProductDynamicFieldValue(@Param(value = "doctorId") Long doctorId,@Param(value = "productId") Long productId);


    /**
     * 根据产品ID得到产品设置的动态字段
     * @param productId
     * @return
     */
    List<DoctorProductDynamicFieldValueResponseBean> getDoctorProductDynamicField(@Param(value = "productId") Long productId);


    /**
     * 得到医生字段填充的值
     * @param doctorId
     * @param dynamicFieldId
     * @return
     */
    DoctorBasicDynamicFieldValueResponseBean getDoctorDynamicFieldValue(@Param(value = "doctorId") Long doctorId,@Param(value = "dynamicFieldId") Long dynamicFieldId);



    /**
     * 得到产品信息动态字段填充的值
     * @param productId
     * @return
     */
    List<DoctorProductDynamicFieldValueResponseBean> getDoctorProductDynamicFieldValueByProductId(@Param(value = "productId") Long productId);



    /**
     * 新增医生动态字段填充的值
     * @param doctorId
     * @param list
     */
    void addDoctorBasicDynamicFieldValue(@Param(value="doctorId") Long doctorId,@Param(value = "list") List<DoctorDynamicFieldValueRequestBean> list);


    /**
     * 新增医生动态字段填充的值
     * @param doctorId
     * @param callId
     * @param list
     */
    void addDoctorCallBasicDynamicFieldValue(@Param(value="doctorId") Long doctorId,@Param(value = "callId") Long callId,@Param(value = "list") List<DoctorDynamicFieldValueRequestBean> list);


    /**
     * 删除医生动态字段填充的值
     * @param doctorId 医生id
     * @param classification 分类：目前1基本信息，2医生的处方信息，3之前拜访记录，4分析，5是医院信息
     */
    void deleteDoctorDynamicFieldValue(@Param(value="doctorId") Long doctorId,@Param(value="classification") Integer classification);

    /**
     * 删除医生动态字段填充的值，关联每次拜访的的callId
     * @param doctorId 医生id
     * @param classification 分类：目前1基本信息，2医生的处方信息，3之前拜访记录，4分析，5是医院信息
     */
    void deleteDoctorCallDynamicFieldValue(@Param(value="doctorId") Long doctorId,@Param(value="classification") Integer classification,@Param(value = "callId") Long callId);


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


    /**
     * 根据产品ID删除
     * @param productId
     */
    void deleteByProductId(@Param(value = "productId") Long productId);

    /**
     * 根据产品复制动态字段
     * @param drugUserId 销售代表ID
     * @param drugUserName 销售代表姓名
     * @param oldProductId 被复制的产品ID
     * @param newProductId 复制的产品ID
     */
    void copyByProductId(@Param(value = "drugUserId") Long drugUserId,@Param(value = "drugUserName") String drugUserName,@Param(value = "oldProductId") Long oldProductId,@Param(value = "newProductId") Long newProductId);


    /**
     * 得到医生指定产品下的问卷列表
     * @param bean
     * @return
     */
    List<ProductQuestionnaireResponseBean> getProductQuestionnaireList(ProductQuestionnaireRequestBean bean);

    /**
     * 得到医生指定产品下的问卷列表总数
     * @param bean
     * @return
     */
    Integer getProductQuestionnaireListCount(ProductQuestionnaireRequestBean bean);


    /**
     * 得到产品下动态字段的总数
     * @param productId
     * @return
     */
    Integer getProductDynamicFieldCount(@Param(value = "productId") Long productId);

    /**
     * 得到产品下动态字段
     * @param productId
     * @return
     */
    List<DynamicFieldResponse> getProductDynamicField(@Param(value = "productId") Long productId);

    /**
     * 得到产品下以及基本信息动态字段
     * @param productId
     * @param doctorIds
     * @return
     */
    List<DynamicFieldValueResponse> getProductDynamicFieldValue(@Param(value = "productId") Long productId, @Param(value = "doctorIds") Set<Long> doctorIds);

    /**
     * 得到产品下以及基本信息动态字段，关联到callId
     * @param productId
     * @param callIds
     * @return
     */
    List<DynamicFieldValueResponse> getProductCallDynamicFieldValue(@Param(value = "productId") Long productId, @Param(value = "callIds") Set<Long> callIds);


    /**
     * 得到医生详情问卷问题列表
     * @param questionnaireId
     * @param doctorId
     * @return
     */
    List<DynamicFieldQuestionDetailResponseBean> getDynamicFieldQuestionList(@Param(value = "questionnaireId") Long questionnaireId,@Param(value = "doctorId") Long doctorId,@Param(value = "answerTime") String answerTime);

    /**
     * 根据ID得到动态字段的名称
     * @param id
     * @return
     */
    DoctorBasicDynamicFieldValueResponseBean getDynamicFieldNameById(@Param(value = "id") Long id);

    /**
     * 根据产品ID 得到拜访记录信息
     * @param productId
     * @param doctorId
     * @return
     */
    List<String> getVisit(@Param(value = "doctorId") Long doctorId, @Param(value = "productId") Long productId);

    /**
     * 根据产品ID得到处方信息
     * @param productId
     * @param doctorId
     * @return
     */
    PrescriptionResponseBean getPrescription(@Param(value = "doctorId") Long doctorId, @Param(value = "productId") Long productId);


    /**
     * 得到产品下的扩展动态字段
     * 目前：潜力，分型
     * @param productId
     * @return
     */
    List<DoctorDynamicExtendResponseBean> getExtendDoctorDynamicField(@Param(value = "productId") Long productId);


    /**
     * 得到高级搜索需要用的动态字段
     * @param productId
     * @return
     */
    List<SearchDynamicFieldResponseBean> getSearchDynamicField(Long productId);

    /**
     * 查询动态字段，用于医生新增回显
     * @param doctorId
     * @param classification
     * @return
     */
    List<DoctorBasicDynamicFieldValueResponseBean> getDoctorAddDynamicField(@Param(value = "doctorId") Long doctorId, @Param(value = "classification") Integer classification);


    /**
     * 得到有需求的医生人数
     * @param bean
     * @return
     */
    Integer getDemandDoctor(DailyStatisticsRequestBean bean);

    /**
     * 根据产品和医生列表获得医生处方列表
     * @param productId
     * @param doctorList
     * @return
     */
    List<PrescriptionResponseBean> getPrescriptionListByDoctorList(@Param(value = "productId") Long productId, @Param(value = "doctorList") List<Long> doctorList);


    /**
     * 判断是否有医生需求字段
     * @param productId
     * @return 有的话大于0
     */
    Integer hasDemandField(@Param(value = "productId") Long productId);

}
