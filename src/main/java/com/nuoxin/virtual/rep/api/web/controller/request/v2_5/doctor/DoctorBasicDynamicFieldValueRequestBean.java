package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 医生基本信息动态字段及录入的值
 * @author tiancun
 * @date 2018-10-30
 */
@Data
@ApiModel(value = "医生基本信息动态字段及录入的值")
public class DoctorBasicDynamicFieldValueRequestBean implements Serializable {

    private static final long serialVersionUID = -3503512941943125690L;


    @ApiModelProperty(value = "动态字段ID")
    private Long dynamicFieldId;

    @ApiModelProperty(value = "动态字段的名称")
    private String dynamicFieldName;

    @ApiModelProperty(value = "字段输入的值")
    private String dynamicFieldValue;

    @ApiModelProperty(value = "字段输入的扩展值")
    private String dynamicExtendValue;

}
