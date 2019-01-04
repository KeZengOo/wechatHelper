package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.ProductClassificationFrequencyParams;

import java.util.List;

/**
 * 产品下医生分型拜访频次设置
 * @author tiancun
 * @date 2019-01-04
 */
public interface ProductClassificationFrequencyMapper {

    /**
     * 批量新增
     * @param list
     */
    void batchInsert(List<ProductClassificationFrequencyParams> list);

}
