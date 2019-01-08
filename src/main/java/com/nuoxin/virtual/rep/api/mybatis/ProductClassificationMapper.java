package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductClassificationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductClassificationFieldResponseBean;
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
     * 根据产品查询医生分型，列表形式
     * @param productId
     * @return
     */
    List<ProductClassificationResponseBean> getProductClassificationList(@Param(value = "productId") Long productId);

    /**
     * 根据产品查询医生分型，字符串形式多个以逗号分开
     * @param productId
     * @return
     */
    ProductClassificationFieldResponseBean getProductClassificationField(@Param(value = "productId") Long productId);

    /**
     * 医生选中的分型
     * @param productId
     * @param doctorId
     * @return
     */
    List<ProductClassificationResponseBean> getDoctorClassificationList(@Param(value = "productId") Long productId,@Param(value = "doctorId") Long doctorId);

    /**
     * 删除医生的分型
     * @param productId
     * @param doctorId
     */
    void deleteDoctorClassificationByProductId(@Param(value = "productId") Long productId,@Param(value = "doctorId") Long doctorId);

    /**
     * 新增医生分型
     * @param productId
     * @param doctorId
     * @param classificationIdList
     */
    void addDoctorClassification(@Param(value = "productId") Long productId,@Param(value = "doctorId") Long doctorId,
                                 @Param(value = "classificationIdList") List<Long> classificationIdList);



}
