package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医生动态字段扩展类型字段
 * @author tiancun
 * @date 2019-01-23
 */
@ApiModel(value = "医生动态字段扩展类型字段")
@Data
public class DoctorDynamicExtendResponseBean implements Serializable {
    private static final long serialVersionUID = 1224608598615286967L;

    @ApiModelProperty(value = "字段名称")
    private String fieldName;

    @ApiModelProperty(value = "字段类型")
    private Integer fieldType;

    @ApiModelProperty(value = "是否必填")
    private Integer required;

    @ApiModelProperty(value = "字段的值")
    private String fieldValue;

    @ApiModelProperty(value = "扩展的类型，目前1是潜力，2是分型")
    private Integer extendType;



}
