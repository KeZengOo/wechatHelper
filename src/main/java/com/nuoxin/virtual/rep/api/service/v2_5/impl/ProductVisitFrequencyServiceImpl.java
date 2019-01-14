package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.enums.VisitTypeEnum;
import com.nuoxin.virtual.rep.api.mybatis.ProductVisitFrequencyMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.ProductVisitFrequencyService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductVisitFrequencyRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductVisitFrequencyResponseBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author tiancun
 * @date 2019-01-07
 */
@Service
public class ProductVisitFrequencyServiceImpl implements ProductVisitFrequencyService {

    @Resource
    private ProductVisitFrequencyMapper productVisitFrequencyMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addProductVisitFrequency(ProductVisitFrequencyRequestBean bean) {

        Long productId = bean.getProductId();
        Integer visitType = bean.getVisitType();
        Integer time = bean.getTime();
        Integer unit = bean.getUnit();
        ProductVisitFrequencyResponseBean productVisitFrequency = productVisitFrequencyMapper.getProductVisitFrequency(productId);
        if (productVisitFrequency != null){
            productVisitFrequencyMapper.deleteByProductId(productId);
        }else {
            productVisitFrequency = new ProductVisitFrequencyResponseBean();
        }

        if (VisitTypeEnum.VISIT.getType().equals(visitType)){
            productVisitFrequency.setVisitFrequency(time);
            productVisitFrequency.setVisitFrequencyType(unit);

        }else if (VisitTypeEnum.RET_VISIT.getType().equals(visitType)){

            productVisitFrequency.setRetVisitFrequency(time);
            productVisitFrequency.setRetVisitFrequencyType(unit);
        }else if (VisitTypeEnum.BEFORE_MEETING.getType().equals(visitType)){

            productVisitFrequency.setBeforeMeetingFrequency(time);
            productVisitFrequency.setBeforeMeetingFrequencyType(unit);
        }else if (VisitTypeEnum.AFTER_MEETING.getType().equals(visitType)){

            productVisitFrequency.setAfterMeetingFrequency(time);
            productVisitFrequency.setAfterMeetingFrequencyType(unit);
        }else {
            throw new BusinessException(ErrorEnum.ERROR, "不合法的拜访类型");
        }

        productVisitFrequency.setProductId(productId);

        productVisitFrequencyMapper.addProductVisitFrequency(productVisitFrequency);
    }

    @Override
    public ProductVisitFrequencyResponseBean getProductVisitFrequency(Long productId) {
        ProductVisitFrequencyResponseBean productVisitFrequency = productVisitFrequencyMapper.getProductVisitFrequency(productId);
        return productVisitFrequency;
    }
}
