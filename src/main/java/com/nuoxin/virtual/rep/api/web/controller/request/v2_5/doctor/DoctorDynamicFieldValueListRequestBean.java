package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 医生动态字段基本信息填充的值列表
 * @author tiancun
 * @date 2018-09-17
 */
@ApiModel(description = "医生动态字段基本信息填充的值列表")
@Data
public class DoctorDynamicFieldValueListRequestBean implements Serializable {
    private static final long serialVersionUID = -1098418984612347315L;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "如果是拜访的时候修改的，则需要传")
    private Long callId;

    @ApiModelProperty(value = "医生动态字段基本信息填充的值")
    private List<DoctorDynamicFieldValueRequestBean> list;

}
