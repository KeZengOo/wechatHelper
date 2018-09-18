package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品动态字段包含问卷调查的信息
 * @author tiancun
 * @date 2018-09-18
 */
@ApiModel(value = "产品动态字段包含问卷调查的信息")
@Data
public class ProductDynamicFieldQuestionnaireResponseBean implements Serializable {
    private static final long serialVersionUID = -650497624101900561L;

    @ApiModelProperty(value = "产品动态字段")
    private List<DoctorProductDynamicFieldValueResponseBean> productDynamicFieldList = new ArrayList<>();

    @ApiModelProperty(value = "产品问卷调查")
    private List<ProductQuestionnaireResponseBean> productQuestionnaireList = new ArrayList<>();
}
