package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductVisitFrequencyRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductVisitFrequencyResponseBean;
import org.apache.ibatis.annotations.Param;

/**
 * 拜访频次设置
 * @author tiancun
 * @date 2019-01-07
 */
public interface ProductVisitFrequencyMapper {

    /**
     * 获取产品下拜访频次
     * @param productId
     * @return
     */
    ProductVisitFrequencyResponseBean getProductVisitFrequency(@Param(value = "productId") Long productId);

    /**
     * 新增
     * @param bean
     */
    void addProductVisitFrequency(ProductVisitFrequencyResponseBean bean);

    /**
     * 删除
     * @param productId
     */
    void deleteByProductId(@Param(value = "productId") Long productId);
}
