package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductVisitFrequencyRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductVisitFrequencyResponseBean;

/**
 * @author tiancun
 * @date 2019-01-07
 */
public interface ProductVisitFrequencyService {


    /**
     * 新增
     * @param bean
     */
    void addProductVisitFrequency(ProductVisitFrequencyRequestBean bean);

    /**
     * 获取产品下拜访频次
     * @param productId
     * @return
     */
    ProductVisitFrequencyResponseBean getProductVisitFrequency(Long productId);
}
