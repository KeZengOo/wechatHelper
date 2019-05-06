package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.HospitalResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产品对应的医院
 * @author tiancun
 * @date 2019-05-06
 */
public interface ProductHospitalMapper {

    /**
     * 根据产品获得医院列表
     * @param productId
     * @return
     */
    List<HospitalResponse> getHospitalListByPoductId(@Param(value = "productId") Long productId);
}
