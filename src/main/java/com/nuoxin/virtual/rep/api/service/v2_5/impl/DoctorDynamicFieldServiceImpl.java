package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.DoctorQuestionnaireDetailRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.*;
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
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.DoctorDynamicFieldValueListRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.DoctorDynamicFieldValueRequestBean;

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

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addDoctorDynamicFieldValue(DoctorDynamicFieldValueListRequestBean bean) {
        Long doctorId = bean.getDoctorId();
        Integer classification = bean.getClassification();
        List<DoctorDynamicFieldValueRequestBean> list = bean.getList();
        if(checkRequiredDoctorDynamicFieldValueList(list)){
            deleteDoctorDynamicFieldValue(doctorId, classification);
            dynamicFieldMapper.addDoctorBasicDynamicFieldValue(doctorId, list);
        }
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

        List<Long> requiredFieldId = dynamicFieldMapper.getRequiredFieldId(collectIdList);
        if (requiredFieldId == null || requiredFieldId.isEmpty()){
            return true;
        }

        requiredFieldId.forEach(id->{
            List<DoctorDynamicFieldValueRequestBean> collect = list.stream().filter(k -> k.getDynamicFieldId().equals(id)).filter(k -> StringUtils.isEmpty(k.getDynamicFieldValue())).collect(Collectors.toList());
            if (collect !=null && collect.size() > 0){
                List<String> stringList = collect.stream().map(DoctorDynamicFieldValueRequestBean::getDynamicFieldName).distinct().collect(Collectors.toList());
                throw new BusinessException(ErrorEnum.ERROR.getStatus(), "必填字段：" + stringList.toString() + " 不能为空！");
            }
        });

        return true;
    }

    @Override
    public void deleteDoctorDynamicFieldValue(Long doctorId, Integer classification) {
        dynamicFieldMapper.deleteDoctorDynamicFieldValue(doctorId, classification);
    }

    @Override
    public DoctorBasicDynamicFieldValueListResponseBean getDoctorBasicDynamicFieldValue(Long doctorId) {
        DoctorBasicDynamicFieldValueListResponseBean bean = new DoctorBasicDynamicFieldValueListResponseBean();

        List<DoctorBasicDynamicFieldValueResponseBean> doctorBasicDynamicFieldValue = dynamicFieldMapper.getDoctorBasicDynamicFieldValue(doctorId, ClassificationEnum.BASIC.getType());
        if (CollectionsUtil.isNotEmptyList(doctorBasicDynamicFieldValue)){
            bean.setBasic(doctorBasicDynamicFieldValue);
        }

        List<DoctorBasicDynamicFieldValueResponseBean> doctorHospitalDynamicFieldValue = dynamicFieldMapper.getDoctorBasicDynamicFieldValue(doctorId, ClassificationEnum.HOSPITAL.getType());
        if (CollectionsUtil.isNotEmptyList(doctorHospitalDynamicFieldValue)){
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

            List<DoctorProductDynamicFieldValueResponseBean> doctorProductDynamicFieldValue = dynamicFieldMapper.getDoctorProductDynamicFieldValue(doctorId, product.getProductId());
            // 没设置过字段或者是还没有录入值
            if (CollectionsUtil.isEmptyList(doctorProductDynamicFieldValue)){
                doctorProductDynamicFieldValue = dynamicFieldMapper.getDoctorProductDynamicFieldValueByProductId(product.getProductId());
            }

            if (CollectionsUtil.isNotEmptyList(doctorProductDynamicFieldValue)){

                productDynamicFieldQuestionnaireResponseBean.setProductDynamicFieldList(doctorProductDynamicFieldValue);
                List<ProductQuestionnaireResponseBean> productQuestionnaireList = dynamicFieldMapper.getProductQuestionnaireList(doctorId, product.getProductId());
                if (CollectionsUtil.isNotEmptyList(productQuestionnaireList)){
                    productDynamicFieldQuestionnaireResponseBean.setProductQuestionnaireList(productQuestionnaireList);
                }

                list.add(productDynamicFieldQuestionnaireResponseBean);
            }
        });

        return list;
    }

    @Override
    public List<DynamicFieldQuestionDetailResponseBean> getDynamicFieldQuestionList(DoctorQuestionnaireDetailRequestBean bean) {
        List<DynamicFieldQuestionDetailResponseBean> dynamicFieldQuestionList = dynamicFieldMapper.getDynamicFieldQuestionList(bean.getQuestionnaireId(), bean.getDoctorId(), bean.getAnswerTime());
        return dynamicFieldQuestionList;
    }
}
