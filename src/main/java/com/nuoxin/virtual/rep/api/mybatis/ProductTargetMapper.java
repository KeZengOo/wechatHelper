package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.statistics.ProductTargetResponseBean;
import org.apache.ibatis.annotations.Param;

/**
 * 产品目标相关
 * @author tiancun
 * @date 2019-02-25
 */
public interface ProductTargetMapper {


    /**
     * 根据产品ID 得到产品目标
     * @param productId
     * @return
     */
    ProductTargetResponseBean getProductTarget(@Param(value = "productId") Long productId);

}
