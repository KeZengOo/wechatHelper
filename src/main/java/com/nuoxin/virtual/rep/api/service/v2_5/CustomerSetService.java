package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.DoctorDynamicFieldRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.DynamicFieldProductRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.customer.DoctorDynamicFieldResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DynamicFieldProductResponseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 新版客户设置或者产品设置相关
 * @author tiancun
 * @date 2018-09-12
 */
public interface CustomerSetService {


    /**
     * 返回医生基本信息和医院新增的动态字段的
     * @return
     */
    List<DoctorDynamicFieldResponseBean> getBasicAndHospitalFieldList();

    /**
     * 返回产品新增的动态字段
     * @param productId
     * @return
     */
    List<DoctorDynamicFieldResponseBean> getProductFieldList(Long productId);

    /**
     * 客户设置医生动态字段修改
     * @param bean
     */
    void updateDoctorDynamicField(DoctorDynamicFieldRequestBean bean);



    /**
     * 客户设置医生动态字段删除
     * @param id
     */
    void deleteDoctorDynamicField(Long id);


    /**
     * 客户设置动态字段新增
     * @param bean
     * @return
     */
    Long insertDoctorDynamicField(DoctorDynamicFieldRequestBean bean);


    /**
     * 产品设置动态字段新增
     * @param bean
     * @return
     */
    Long insertProductDoctorDynamicField(DoctorDynamicFieldRequestBean bean);

    /**
     * 客户设置产品信息列表
     * @param bean
     * @return
     */
    PageResponseBean<DynamicFieldProductResponseBean> getDynamicFieldProductPage(DrugUser user,DynamicFieldProductRequestBean bean);

    /**
     * 根据产品复制动态字段
     * @param user
     * @param oldProductId
     * @param newProductId
     */
    void copyDynamicFieldByProductId(DrugUser user, Long oldProductId, Long newProductId);

    /**
     * 得到产品下动态字段的总数
     * @param productId
     * @return
     */
    Integer getProductDynamicFieldCount(Long productId);

}
