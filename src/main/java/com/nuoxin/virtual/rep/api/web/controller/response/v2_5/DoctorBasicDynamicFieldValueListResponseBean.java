package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 医生基本信息医院信息动态字段包括字段填充的值集合
 * @author tiancun
 * @date 2018-09-17
 */
@ApiModel(description = "医生基本信息医院信息动态字段包括字段填充的值集合")
@Data
public class DoctorBasicDynamicFieldValueListResponseBean implements Serializable {
    private static final long serialVersionUID = -5999190901968228085L;

    @ApiModelProperty(value = "医生基本信息")
    private List<DoctorBasicDynamicFieldValueResponseBean> basic = new ArrayList<>();

    @ApiModelProperty(value = "医院信息")
    private List<DoctorBasicDynamicFieldValueResponseBean> hospital = new ArrayList<>();

}
