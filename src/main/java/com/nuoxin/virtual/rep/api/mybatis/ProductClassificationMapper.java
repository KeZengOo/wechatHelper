package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductClassificationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductClassificationResponseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产品分型设置
 * @author tiancun
 * @date 2019-01-03
 */
public interface ProductClassificationMapper {

    /**
     * 医生分型设置
     * @param bean
     */
    void add(@Param(value = "bean") ProductClassificationRequestBean bean);

    /**
     * 根据产品删除
     * @param productId
     */
    void deleteByProductId(@Param(value = "productId") Long productId);


    /**
     * 根据产品查询医生分型
     * @param productId
     * @return
     */
    List<ProductClassificationResponseBean> getProductClassificationList(@Param(value = "productId") Long productId);
}
