package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.entity.v2_5.ProductClassificationFrequencyParams;
import com.nuoxin.virtual.rep.api.mybatis.ProductClassificationFrequencyMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.ProductClassificationFrequencyService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductClassificationFrequencyRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductClassificationFrequencyUpdateRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductClassificationFrequencyResponseBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
        List<ProductClassificationFrequencyResponseBean> productClassificationFrequencyList = productClassificationFrequencyMapper.getProductClassificationFrequencyList(productId);
        if (CollectionsUtil.isNotEmptyList(productClassificationFrequencyList)){
            return productClassificationFrequencyList;
        }

        return new ArrayList<>();
    }

    @Override
    @Transactional
    public void update(ProductClassificationFrequencyUpdateRequestBean bean) {
        String batchNo = bean.getBatchNo();
        this.deleteByBatchNo(batchNo);
        this.add(bean);
    }

    /**
     * 参数处理，用作新增
     * @param bean
     * @return
     */
    private List<ProductClassificationFrequencyParams> getProductClassificationFrequencyParams(ProductClassificationFrequencyRequestBean bean) {

        this.checkProductClassificationFrequencyRequestBean(bean);
        List<Long> classificationIdList = bean.getClassificationIdList();
        Integer potential = bean.getPotential();
        Long productId = bean.getProductId();
        Integer visitFrequency = bean.getVisitFrequency();
        String batchNo = UUID.randomUUID().toString();
        List<ProductClassificationFrequencyParams> paramsList = new ArrayList<>(classificationIdList.size());
        classificationIdList.forEach(c ->{

            ProductClassificationFrequencyParams params = new ProductClassificationFrequencyParams();
            params.setProductId(productId);
            params.setVisitFrequency(visitFrequency);
            params.setClassificationId(c);
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

        Integer potential = bean.getPotential();
        if (potential == null){
            throw new BusinessException(ErrorEnum.ERROR, "医生潜力不能为空！");
        }

        List<Long> classificationIdList = bean.getClassificationIdList();
        if (CollectionsUtil.isEmptyList(classificationIdList)){
            throw new BusinessException(ErrorEnum.ERROR, "医生分型不能为空！");
        }

        Integer count = productClassificationFrequencyMapper.getClassificationCountByPotential(classificationIdList, potential);
        if (count !=null && count > 0){
            throw new BusinessException(ErrorEnum.ERROR, "该潜力医生分型已经存在！");
        }
    }
}
