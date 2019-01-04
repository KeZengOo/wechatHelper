package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.entity.v2_5.ProductClassificationFrequencyParams;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductClassificationFrequencyRequestBean;

import java.util.List;

/**
 * 产品的不同分型的医生拜访频次设置
 * @author tiancun
 * @date 2019-01-04
 */
public interface ProductClassificationFrequencyService {

    /**
     * 新增
     * @param bean
     */
    void add(ProductClassificationFrequencyRequestBean bean);


}
