package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.entity.v2_5.ProductClassificationFrequencyParams;
import com.nuoxin.virtual.rep.api.enums.ExtendDynamicFieldTypeEnum;
import com.nuoxin.virtual.rep.api.mybatis.DynamicFieldMapper;
import com.nuoxin.virtual.rep.api.mybatis.ProductClassificationFrequencyMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.ProductClassificationFrequencyService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductClassificationFrequencyRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductClassificationFrequencyUpdateRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.TableHeader;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.DoctorDynamicExtendResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.PotentialClassificationResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductClassificationFrequencyResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductClassificationResponseBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author tiancun
 * @date 2019-01-04
 */
@Service
public class ProductClassificationFrequencyServiceImpl implements ProductClassificationFrequencyService {

    @Resource
    private ProductClassificationFrequencyMapper productClassificationFrequencyMapper;

    @Resource
    private DynamicFieldMapper dynamicFieldMapper;


    @Override
    public PotentialClassificationResponseBean getPotentialClassification(Long productId) {

        PotentialClassificationResponseBean potentialClassificationResponseBean = new PotentialClassificationResponseBean();
        List<DoctorDynamicExtendResponseBean> extendDoctorDynamicField = dynamicFieldMapper.getExtendDoctorDynamicField(productId);
        if (CollectionsUtil.isEmptyList(extendDoctorDynamicField)){
            return potentialClassificationResponseBean;
        }

        extendDoctorDynamicField.forEach(f->{
            String fieldName = f.getFieldName();
            String fieldValue = f.getFieldValue();
            Integer extendType = f.getExtendType();
            if (StringUtil.isNotEmpty(fieldValue)){
                String[] fieldValueArray = fieldValue.split(",");
                if (ExtendDynamicFieldTypeEnum.POTENTIAL.getType() == extendType){
                    potentialClassificationResponseBean.setPotential(fieldName);
                    potentialClassificationResponseBean.setPotentialList(Arrays.asList(fieldValueArray));
                    potentialClassificationResponseBean.setPotentialType(f.getFieldType());
                    potentialClassificationResponseBean.setPotentialRequired(f.getRequired());
                }

                if (ExtendDynamicFieldTypeEnum.CLASSIFICATION.getType() == extendType){
                    potentialClassificationResponseBean.setClassification(fieldName);
                    potentialClassificationResponseBean.setClassificationList(Arrays.asList(fieldValueArray));
                    potentialClassificationResponseBean.setClassificationType(f.getFieldType());
                    potentialClassificationResponseBean.setClassificationRequired(f.getRequired());
                }
            }

        });

        if (StringUtil.isNotEmpty(potentialClassificationResponseBean.getPotential()) && StringUtil.isNotEmpty(potentialClassificationResponseBean.getClassification())){
            // 拜访频次目前是固定的
            potentialClassificationResponseBean.setVisitFrequency("拜访频次");
        }

        return potentialClassificationResponseBean;

    }

    @Override
    public void add(ProductClassificationFrequencyRequestBean bean) {

        List<ProductClassificationFrequencyParams> paramsList = this.getProductClassificationFrequencyParams(bean);
        productClassificationFrequencyMapper.batchInsert(paramsList);

    }

    @Override
    public void deleteByBatchNo(String batchNo) {
        productClassificationFrequencyMapper.deleteByBatchNo(batchNo);
    }

    @Override
    public List<ProductClassificationFrequencyResponseBean> getProductClassificationFrequencyList(Long productId) {
        List<ProductClassificationFrequencyResponseBean> productClassificationFrequencyList =
                productClassificationFrequencyMapper.getProductClassificationFrequencyList(productId);


        if (CollectionsUtil.isEmptyList(productClassificationFrequencyList)){
            return new ArrayList<>();
        }


        for (ProductClassificationFrequencyResponseBean responseBean : productClassificationFrequencyList) {
            String classification = responseBean.getClassification();
            if (StringUtil.isEmpty(classification)){
                continue;
            }

            String[] classificationArray = classification.split(",");
            if (CollectionsUtil.isEmptyArray(classificationArray)){
                continue;
            }
            List<String> classificationList = new ArrayList<>(Arrays.asList(classificationArray));
            if (CollectionsUtil.isNotEmptyList(classificationList)){
                responseBean.setClassificationList(classificationList);
            }
        }


        return productClassificationFrequencyList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(ProductClassificationFrequencyUpdateRequestBean bean) {
        String batchNo = bean.getBatchNo();
        this.deleteByBatchNo(batchNo);
        this.add(bean);
    }

    @Override
    public List<TableHeader> getTableHeaderList(Long productId) {
        List<DoctorDynamicExtendResponseBean> extendDoctorDynamicField = dynamicFieldMapper.getExtendDoctorDynamicField(productId);
        if (CollectionsUtil.isEmptyList(extendDoctorDynamicField)){
            return new ArrayList<>();
        }

        List<TableHeader> labelList = new ArrayList<>();
        extendDoctorDynamicField.forEach(d->{
            String fieldName = d.getFieldName();
            Integer extendType = d.getExtendType();
            if (ExtendDynamicFieldTypeEnum.POTENTIAL.getType() == extendType){
                TableHeader tableHeader = new TableHeader();
                tableHeader.setName("potential");
                tableHeader.setLabel(fieldName);
                labelList.add(tableHeader);
            }

            if (ExtendDynamicFieldTypeEnum.CLASSIFICATION.getType() == extendType){
                TableHeader tableHeader = new TableHeader();
                tableHeader.setName("classification");
                tableHeader.setLabel(fieldName);
                labelList.add(tableHeader);
            }

        });

        TableHeader tableHeader = new TableHeader();
        tableHeader.setName("frequency");
        tableHeader.setLabel("拜访频次");
        labelList.add(tableHeader);

        return labelList;
    }

    /**
     * 参数处理，用作新增
     * @param bean
     * @return
     */
    private List<ProductClassificationFrequencyParams> getProductClassificationFrequencyParams(ProductClassificationFrequencyRequestBean bean) {

        this.checkProductClassificationFrequencyRequestBean(bean);
        List<String> classificationList = bean.getClassificationList();
        String potential = bean.getPotential();
        Long productId = bean.getProductId();
        Integer visitFrequency = bean.getVisitFrequency();
        String batchNo = UUID.randomUUID().toString();
        List<ProductClassificationFrequencyParams> paramsList = new ArrayList<>(classificationList.size());
        classificationList.forEach(c ->{

            ProductClassificationFrequencyParams params = new ProductClassificationFrequencyParams();
            params.setProductId(productId);
            params.setVisitFrequency(visitFrequency);
            params.setClassification(c);
            params.setPotential(potential);
            params.setBatchNo(batchNo);
            paramsList.add(params);
        });

        return paramsList;
    }

    /**
     * 校验参数
     * @param bean
     */
    private void checkProductClassificationFrequencyRequestBean(ProductClassificationFrequencyRequestBean bean) {

        Long productId = bean.getProductId();
        if (productId == null || productId <=0){
            throw new BusinessException(ErrorEnum.ERROR, "产品ID不能为空！");
        }

        String potential = bean.getPotential();
        if (StringUtil.isEmpty(potential)){
            throw new BusinessException(ErrorEnum.ERROR, "医生潜力不能为空！");
        }

        List<String> classificationList = bean.getClassificationList();
        if (CollectionsUtil.isEmptyList(classificationList)){
            throw new BusinessException(ErrorEnum.ERROR, "医生分型不能为空！");
        }

        Integer count = productClassificationFrequencyMapper.getClassificationCountByPotential(classificationList, potential);
        if (count !=null && count > 0){
            throw new BusinessException(ErrorEnum.ERROR, "该潜力医生分型已经存在！");
        }
    }
}
