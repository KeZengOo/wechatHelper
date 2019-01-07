package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductVisitFrequencyRequestBean;

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
}
