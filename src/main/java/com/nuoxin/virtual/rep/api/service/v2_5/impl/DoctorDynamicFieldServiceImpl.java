package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.DynamicFieldResponse;
import com.nuoxin.virtual.rep.api.mybatis.ProductClassificationMapper;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.*;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.questionnaire.ProductQuestionnaireRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.*;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductClassificationResponseBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.nuoxin.virtual.rep.api.common.enums.ClassificationEnum;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.entity.v2_5.ProductDO;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserMapper;
import com.nuoxin.virtual.rep.api.mybatis.DynamicFieldMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v2_5.DoctorDynamicFieldService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;

/**
 * 医生动态字段相关业务接口实现
 * @author tiancun
 * @date 2018-09-17
 */
@Service(value = "dynamic")
public class DoctorDynamicFieldServiceImpl implements DoctorDynamicFieldService {

    @Resource
    private DynamicFieldMapper dynamicFieldMapper;
    @Resource
    private DrugUserMapper drugUserMapper;
    @Resource
    private CommonService commonService;

    @Resource
    private ProductClassificationMapper productClassificationMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addDoctorDynamicFieldValue(DoctorDynamicFieldValueListRequestBean bean) {

        Long doctorId = bean.getDoctorId();
        Long productId = bean.getProductId();
        // 先暂时去掉医生分型
//        List<Long> classificationIdList = bean.getClassificationIdList();
        List<DoctorDynamicFieldValueRequestBean> list = bean.getList();
        if(checkRequiredDoctorDynamicFieldValueList(list)){
            List<Integer> collectClassification = list.stream().map(DoctorDynamicFieldValueRequestBean::getClassification).distinct().collect(Collectors.toList());
            if (CollectionsUtil.isNotEmptyList(collectClassification)){
                collectClassification.forEach(classification->{
                    this.deleteDoctorDynamicFieldValue(doctorId, classification);
                });
            }

            dynamicFieldMapper.addDoctorBasicDynamicFieldValue(doctorId, list);
        }


//        if (CollectionsUtil.isNotEmptyList(classificationIdList)){
//            productClassificationMapper.deleteDoctorClassificationByProductId(productId, doctorId);
//            productClassificationMapper.addDoctorClassification(productId, doctorId, classificationIdList);
//        }

    }

    @Override
    public void addDoctorCallDynamicFieldValue(DoctorDynamicFieldValueListRequestBean bean) {
        Long doctorId = bean.getDoctorId();
        Long callId = bean.getCallId();
        List<DoctorDynamicFieldValueRequestBean> list = bean.getList();
        if(checkRequiredDoctorDynamicFieldValueList(list)){
            List<Integer> collectClassification = list.stream().map(DoctorDynamicFieldValueRequestBean::getClassification).distinct().collect(Collectors.toList());
            if (CollectionsUtil.isNotEmptyList(collectClassification)){
                collectClassification.forEach(classification->{
                    this.deleteDoctorCallDynamicFieldValue(doctorId, classification, callId);
                });
            }

            dynamicFieldMapper.addDoctorCallBasicDynamicFieldValue(doctorId, callId, list);
        }
    }

    @Override
    public void addDoctorBasicDynamicFieldValue(DoctorBasicDynamicFieldValueListRequestBean bean) {
        Long callId = bean.getCallId();
        // 如果是根据拜访来的，不添加
        if (callId !=null && callId > 0){
            return;
        }
        DoctorDynamicFieldValueListRequestBean doctorDynamicFieldValueListRequestBean = getDoctorDynamicFieldValueList(bean);
        this.addDoctorDynamicFieldValue(doctorDynamicFieldValueListRequestBean);


    }

    @Override
    public void addDoctorBasicCallDynamicFieldValue(DoctorBasicDynamicFieldValueListRequestBean bean) {
        DoctorDynamicFieldValueListRequestBean doctorDynamicFieldValueListRequestBean = getDoctorDynamicFieldValueList(bean);
        this.addDoctorCallDynamicFieldValue(doctorDynamicFieldValueListRequestBean);
    }

    /**
     * 将 DoctorBasicDynamicFieldValueListRequestBean 转为 DoctorDynamicFieldValueListRequestBean
     * @param bean
     * @return
     */
    private DoctorDynamicFieldValueListRequestBean getDoctorDynamicFieldValueList(DoctorBasicDynamicFieldValueListRequestBean bean) {
        DoctorDynamicFieldValueListRequestBean doctorDynamicFieldValueListRequestBean = new DoctorDynamicFieldValueListRequestBean();
        Long doctorId = bean.getDoctorId();
        Long callId = bean.getCallId();
        List<DoctorDynamicFieldValueRequestBean> list = new ArrayList<>();
        List<DoctorBasicDynamicFieldValueRequestBean> basic = bean.getBasic();
        if (CollectionsUtil.isNotEmptyList(basic)){
            basic.forEach(b->{
                DoctorDynamicFieldValueRequestBean doctorDynamicFieldValueRequestBean = new DoctorDynamicFieldValueRequestBean();
                doctorDynamicFieldValueRequestBean.setClassification(ClassificationEnum.BASIC.getType());
                doctorDynamicFieldValueRequestBean.setDynamicFieldId(b.getDynamicFieldId());
                doctorDynamicFieldValueRequestBean.setDynamicFieldName(b.getDynamicFieldName());
                doctorDynamicFieldValueRequestBean.setDynamicFieldValue(b.getDynamicFieldValue());
                doctorDynamicFieldValueRequestBean.setDynamicExtendValue(b.getDynamicExtendValue());
                list.add(doctorDynamicFieldValueRequestBean);
            });
        }


        List<DoctorBasicDynamicFieldValueRequestBean> hospital = bean.getHospitalDynamic();
        if (CollectionsUtil.isNotEmptyList(hospital)){
            hospital.forEach(h->{
                DoctorDynamicFieldValueRequestBean doctorDynamicFieldValueRequestBean = new DoctorDynamicFieldValueRequestBean();
                doctorDynamicFieldValueRequestBean.setClassification(ClassificationEnum.HOSPITAL.getType());
                doctorDynamicFieldValueRequestBean.setDynamicFieldId(h.getDynamicFieldId());
                doctorDynamicFieldValueRequestBean.setDynamicFieldName(h.getDynamicFieldName());
                doctorDynamicFieldValueRequestBean.setDynamicFieldValue(h.getDynamicFieldValue());
                doctorDynamicFieldValueRequestBean.setDynamicExtendValue(h.getDynamicExtendValue());
                list.add(doctorDynamicFieldValueRequestBean);
            });
        }

        doctorDynamicFieldValueListRequestBean.setDoctorId(doctorId);
        doctorDynamicFieldValueListRequestBean.setCallId(callId);
        doctorDynamicFieldValueListRequestBean.setList(list);

        return doctorDynamicFieldValueListRequestBean;
    }

    /**
     * 检查必填字段是否都已经填值
     * @param list
     * @return
     */
    private boolean checkRequiredDoctorDynamicFieldValueList(List<DoctorDynamicFieldValueRequestBean> list) {
        if (list == null || list.isEmpty()){
            return false;
        }

        List<Long> collectIdList = list.stream().map(DoctorDynamicFieldValueRequestBean::getDynamicFieldId).distinct().collect(Collectors.toList());
        if (collectIdList == null || collectIdList.isEmpty()){
            return false;
        }


        list.forEach(d->{
            Long dynamicFieldId = d.getDynamicFieldId();
            if (dynamicFieldId == null || dynamicFieldId <=0){
                throw new BusinessException(ErrorEnum.ERROR.getStatus(), "dynamicFieldId 必须大于0");
            }

            String dynamicFieldName = d.getDynamicFieldName();
            if (StringUtil.isEmpty(dynamicFieldName)){
                throw new BusinessException(ErrorEnum.ERROR.getStatus(), "dynamicFieldName 不能为空！");
            }

            DoctorBasicDynamicFieldValueResponseBean dynamicFieldNameById = dynamicFieldMapper.getDynamicFieldNameById(dynamicFieldId);
            if (dynamicFieldNameById == null){
                throw new BusinessException(ErrorEnum.ERROR.getStatus(), "无效的dynamicFieldId！");
            }
            if (!dynamicFieldName.equals(dynamicFieldNameById.getDynamicFieldName())){
                throw new BusinessException(ErrorEnum.ERROR.getStatus(), "dynamicFieldId:"+ dynamicFieldId +" 和 dynamicFieldName:"+ dynamicFieldName +" 不匹配");
            }

            Integer classification = d.getClassification();
            if (!classification.equals(dynamicFieldNameById.getClassification())){
                throw new BusinessException(ErrorEnum.ERROR.getStatus(), "字段：" + dynamicFieldName +" 和类型不匹配");
            }


            Integer required = dynamicFieldNameById.getRequired();
            if (required != null && required.equals(1)){
                String dynamicFieldValue = d.getDynamicFieldValue();
                if (StringUtil.isEmpty(dynamicFieldValue)){
                    throw new BusinessException(ErrorEnum.ERROR.getStatus(), "必填字段:" + dynamicFieldName + " 输入的值不能为空！");
                }
            }

        });

        return true;
    }

    @Override
    public void deleteDoctorDynamicFieldValue(Long doctorId, Integer classification) {
        dynamicFieldMapper.deleteDoctorDynamicFieldValue(doctorId, classification);
    }

    @Override
    public void deleteDoctorCallDynamicFieldValue(Long doctorId, Integer classification, Long callId) {
        dynamicFieldMapper.deleteDoctorCallDynamicFieldValue(doctorId, classification, callId);
    }

    @Override
    public DoctorBasicDynamicFieldValueListResponseBean getDoctorBasicDynamicFieldValue(Long doctorId) {
        DoctorBasicDynamicFieldValueListResponseBean bean = new DoctorBasicDynamicFieldValueListResponseBean();

        //List<DoctorBasicDynamicFieldValueResponseBean> doctorBasicDynamicFieldValue = dynamicFieldMapper.getDoctorBasicDynamicFieldValue(doctorId, ClassificationEnum.BASIC.getType());
        List<DoctorBasicDynamicFieldValueResponseBean> doctorBasicDynamicFieldValue = dynamicFieldMapper.getDoctorBasicDynamicField(doctorId, ClassificationEnum.BASIC.getType());

        if (CollectionsUtil.isNotEmptyList(doctorBasicDynamicFieldValue)){
            doctorBasicDynamicFieldValue.forEach(doctorBasicDynamicField->{
                DoctorBasicDynamicFieldValueResponseBean doctorDynamicFieldValue = dynamicFieldMapper.getDoctorDynamicFieldValue(doctorId, doctorBasicDynamicField.getDynamicFieldId());
                if (doctorDynamicFieldValue !=null){
                    doctorBasicDynamicField.setDynamicFieldValue(doctorDynamicFieldValue.getDynamicFieldValue());
                    doctorBasicDynamicField.setDynamicExtendValue(doctorDynamicFieldValue.getDynamicExtendValue());
                }
            });


            bean.setBasic(doctorBasicDynamicFieldValue);
        }

        //List<DoctorBasicDynamicFieldValueResponseBean> doctorHospitalDynamicFieldValue = dynamicFieldMapper.getDoctorBasicDynamicFieldValue(doctorId, ClassificationEnum.HOSPITAL.getType());
        List<DoctorBasicDynamicFieldValueResponseBean> doctorHospitalDynamicFieldValue = dynamicFieldMapper.getDoctorBasicDynamicField(doctorId, ClassificationEnum.HOSPITAL.getType());
        if (CollectionsUtil.isNotEmptyList(doctorHospitalDynamicFieldValue)){
            doctorHospitalDynamicFieldValue.forEach(doctorBasicDynamicField->{
                DoctorBasicDynamicFieldValueResponseBean doctorDynamicFieldValue = dynamicFieldMapper.getDoctorDynamicFieldValue(doctorId, doctorBasicDynamicField.getDynamicFieldId());
                if (doctorDynamicFieldValue !=null){
                    doctorBasicDynamicField.setDynamicFieldValue(doctorDynamicFieldValue.getDynamicFieldValue());
                    doctorBasicDynamicField.setDynamicExtendValue(doctorDynamicFieldValue.getDynamicExtendValue());
                }
            });

            bean.setHospital(doctorHospitalDynamicFieldValue);
        }

        return bean;
    }

    @Override
    public List<ProductDynamicFieldQuestionnaireResponseBean> getDoctorProductDynamicFieldValue(Long doctorId, Long drugUserId) {
        List<ProductDynamicFieldQuestionnaireResponseBean> list = new ArrayList<>();
        String leaderPath = commonService.getLeaderPathById(drugUserId);
        List<ProductDO> productList = drugUserMapper.getSetDynamicFieldProductListByDoctorId(leaderPath, doctorId);
        if (CollectionsUtil.isEmptyList(productList)){
            return list;
        }

        List<Long> productIdList = productList.stream().map(ProductDO::getProductId).distinct().collect(Collectors.toList());
        if (CollectionsUtil.isEmptyList(productIdList)){
            return list;
        }


        productList.forEach(product->{
            ProductDynamicFieldQuestionnaireResponseBean productDynamicFieldQuestionnaireResponseBean = new ProductDynamicFieldQuestionnaireResponseBean();
            ProductLineResponseBean productLineResponseBean = new ProductLineResponseBean();
            productLineResponseBean.setProductId(product.getProductId());
            productLineResponseBean.setProductName(product.getProductName());
            productDynamicFieldQuestionnaireResponseBean.setProductLineResponseBean(productLineResponseBean);

            //List<DoctorProductDynamicFieldValueResponseBean> doctorProductDynamicFieldValue = dynamicFieldMapper.getDoctorProductDynamicFieldValue(doctorId, product.getProductId());

            // 动态字段
            List<DoctorProductDynamicFieldValueResponseBean> doctorProductDynamicFieldValue = dynamicFieldMapper.getDoctorProductDynamicField(product.getProductId());
            if (CollectionsUtil.isNotEmptyList(doctorProductDynamicFieldValue)){
                doctorProductDynamicFieldValue.forEach(doctorProductDynamicField->{
                    DoctorBasicDynamicFieldValueResponseBean doctorDynamicFieldValue = dynamicFieldMapper.getDoctorDynamicFieldValue(doctorId, doctorProductDynamicField.getDynamicFieldId());
                    if (doctorDynamicFieldValue !=null){
                        doctorProductDynamicField.setDynamicFieldValue(doctorDynamicFieldValue.getDynamicFieldValue());
                        doctorProductDynamicField.setDynamicExtendValue(doctorDynamicFieldValue.getDynamicExtendValue());
                    }
                });

                productDynamicFieldQuestionnaireResponseBean.setProductDynamicFieldList(doctorProductDynamicFieldValue);
            }



            // 调查问卷
//            if (CollectionsUtil.isNotEmptyList(doctorProductDynamicFieldValue)){
//                productDynamicFieldQuestionnaireResponseBean.setProductDynamicFieldList(doctorProductDynamicFieldValue);
//                List<ProductQuestionnaireResponseBean> productQuestionnaireList = dynamicFieldMapper.getProductQuestionnaireList(doctorId, product.getProductId());
//                if (CollectionsUtil.isNotEmptyList(productQuestionnaireList)){
//                    productDynamicFieldQuestionnaireResponseBean.setProductQuestionnaireList(productQuestionnaireList);
//                }
//
//                list.add(productDynamicFieldQuestionnaireResponseBean);
//            }


            // 添加上医生的处方信息和拜访记录信息
            PrescriptionResponseBean prescription = dynamicFieldMapper.getPrescription(doctorId, product.getProductId());

            if (prescription != null){
                productDynamicFieldQuestionnaireResponseBean.setPrescription(prescription);
            }

            List<String> result = dynamicFieldMapper.getVisit(doctorId, product.getProductId());
            if (CollectionsUtil.isNotEmptyList(result)){
               VisitResponseBean visitResponseBean = new VisitResponseBean();
               visitResponseBean.setVisitResult(result);
               productDynamicFieldQuestionnaireResponseBean.setVisit(visitResponseBean);

           }

            // 医生的选中的分型
            List<ProductClassificationResponseBean> doctorClassificationList = productClassificationMapper.getDoctorClassificationList(product.getProductId(), doctorId);
            if (CollectionsUtil.isNotEmptyList(doctorClassificationList)){
                List<Long> idList = doctorClassificationList.stream().map(ProductClassificationResponseBean::getId).distinct().collect(Collectors.toList());
                List<String> valueList = doctorClassificationList.stream().map(ProductClassificationResponseBean::getFieldValue).distinct().collect(Collectors.toList());
                if (CollectionsUtil.isNotEmptyList(idList)){
                    productDynamicFieldQuestionnaireResponseBean.setClassificationIdList(idList);
                }

                if (CollectionsUtil.isNotEmptyList(valueList)){
                    productDynamicFieldQuestionnaireResponseBean.setClassificationList(valueList);
                }
            }


            list.add(productDynamicFieldQuestionnaireResponseBean);
        });

        return list;
    }

    @Override
    public PageResponseBean<ProductQuestionnaireResponseBean> getProductQuestionnairePage(ProductQuestionnaireRequestBean bean) {
        Integer page = bean.getPage();
        Integer pageSize = bean.getPageSize();
        bean.setCurrentSize(page  * pageSize);

        Integer count = dynamicFieldMapper.getProductQuestionnaireListCount(bean);
        PageResponseBean<ProductQuestionnaireResponseBean> empty = new PageResponseBean<>();
        if (count !=null && count > 0){
            List<ProductQuestionnaireResponseBean> productQuestionnaireList = dynamicFieldMapper.getProductQuestionnaireList(bean);
            PageResponseBean<ProductQuestionnaireResponseBean> pageResponseBean = new PageResponseBean<>(bean, count, productQuestionnaireList);
            return pageResponseBean;
        }
        return empty;
    }

    @Override
    public List<DynamicFieldQuestionDetailResponseBean> getDynamicFieldQuestionList(DoctorQuestionnaireDetailRequestBean bean) {
        List<DynamicFieldQuestionDetailResponseBean> dynamicFieldQuestionList = dynamicFieldMapper.getDynamicFieldQuestionList(bean.getQuestionnaireId(), bean.getDoctorId(), bean.getAnswerTime());
        return dynamicFieldQuestionList;
    }
}
