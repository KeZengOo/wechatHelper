package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.mybatis.ProductClassificationMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.ProductClassificationService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.questionnaire.ProductQuestionnaireRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductClassificationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductClassificationFieldResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductClassificationResponseBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author tiancun
 * @date 2019-01-03
 */
@Service
public class ProductClassificationServiceImpl implements ProductClassificationService {

    @Resource
    private ProductClassificationMapper productClassificationMapper;

    @Override
    public void add(ProductClassificationRequestBean bean) {
        String fieldValue = bean.getFieldValue();
        if (StringUtil.isEmpty(fieldValue)){
            throw new BusinessException(ErrorEnum.ERROR, "字段的值不能为空");
        }

        if (fieldValue.contains("，")){
            fieldValue = fieldValue.replace("，", ",");
        }

        String[] fieldValueArray = fieldValue.split(",");
        List<String> fieldValueList = new ArrayList<>(Arrays.asList(fieldValueArray));
        bean.setFieldValueList(fieldValueList);

        productClassificationMapper.add(bean);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(ProductClassificationRequestBean bean) {
        Long productId = bean.getProductId();
        this.deleteByProductId(productId);
        this.add(bean);
    }

    @Override
    public void deleteByProductId(Long productId) {

        productClassificationMapper.deleteByProductId(productId);

    }

    @Override
    public List<ProductClassificationResponseBean> getProductClassificationList(Long productId) {

        List<ProductClassificationResponseBean> productClassificationList = productClassificationMapper.getProductClassificationList(productId);
        if (CollectionsUtil.isNotEmptyList(productClassificationList)){
            return productClassificationList;
        }

        return new ArrayList<>();
    }

    @Override
    public ProductClassificationFieldResponseBean getProductClassificationField(Long productId) {
        ProductClassificationFieldResponseBean productClassificationField = productClassificationMapper.getProductClassificationField(productId);
        return productClassificationField;
    }
}
