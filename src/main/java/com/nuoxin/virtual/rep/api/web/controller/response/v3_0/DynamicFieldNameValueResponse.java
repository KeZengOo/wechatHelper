package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 动态字段返回数据
 * @author tiancun
 * @date 2019-05-07
 */
@Data
@ApiModel(value = "动态字段返回数据")
public class DynamicFieldNameValueResponse implements Serializable {
    private static final long serialVersionUID = -6937031634235466834L;

    @ApiModelProperty(value = "字段ID")
    private Long fieldId;

    @ApiModelProperty(value = "字段名称")
    private String fieldName;

    @ApiModelProperty(value = "字段填充的值")
    private String fieldValue;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;


}
