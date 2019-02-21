package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.single;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 新增单个医生，如果医生存在返回的动态字段回显
 * @author tiancun
 * @date 2019-02-20
 */
@Data
@ApiModel(value = "新增单个医生，动态字段回显")
public class DoctorAddDynamicFieldResponseBean implements Serializable {
    private static final long serialVersionUID = -5701721418462827406L;

    @ApiModelProperty(value = "字段ID")
    private Long fieldId;

    @ApiModelProperty(value = "字段名称")
    private String fieldName;

    @ApiModelProperty(value = "选中的值")
    private String selectValue;

}
