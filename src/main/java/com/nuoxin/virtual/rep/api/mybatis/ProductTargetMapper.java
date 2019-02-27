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


    /**
     * 新增产品目标医院
     * @param productId
     * @param targetHospital
     */
    void addTargetHospital(@Param(value = "productId") Long productId,@Param(value = "targetHospital") Integer targetHospital);


    /**
     * 更新产品目标医院
     * @param productId
     * @param targetHospital
     */
    void updateTargetHospital(@Param(value = "productId") Long productId,@Param(value = "targetHospital") Integer targetHospital);



    /**
     * 新增产品目标医生
     * @param productId
     * @param targetDoctor
     */
    void addTargetDoctor(@Param(value = "productId") Long productId,@Param(value = "targetDoctor") Integer targetDoctor);


    /**
     * 更新产品目标医生
     * @param productId
     * @param targetDoctor
     */
    void updateTargetDoctor(@Param(value = "productId") Long productId,@Param(value = "targetDoctor") Integer targetDoctor);

}