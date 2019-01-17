package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 客户设置动态字段请求参数
 * @author tiancun
 * @date 2018-09-15
 */
@ApiModel(description = "客户设置动态字段请求参数")
@Data
public class DoctorDynamicFieldRequestBean implements Serializable {
    private static final long serialVersionUID = -2953248116587340016L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "序号", hidden = true)
    private Integer serialNumber;

    @ApiModelProperty(value = "字段名称")
    private String name;

    @ApiModelProperty(value = "字段类型，1是文本,2是下拉框")
    private Integer type;

    @ApiModelProperty(value = "下拉框的值")
    private String value;

    @ApiModelProperty(value = "是否必填，1是必填0是非必填")
    private Integer required;

    @ApiModelProperty(value = "分类，目前1基本信息，2医生的处方信息，3之前拜访记录，4分析，5是医院信息")
    private Integer classification;

    @ApiModelProperty(value = "产品id,只有产品设置的时候才会有这个的字段")
    private Long productId = 0L;

    @ApiModelProperty(value = "创建者id,前端不用传", hidden = true)
    private Long creatorId;

    @ApiModelProperty(value = "创建者，前端不用传", hidden = true)
    private String creatorName;

    @ApiModelProperty(value = "修改者id,前端不用传", hidden = true)
    private Long menderId;

    @ApiModelProperty(value = "修改者，前端不用传", hidden = true)
    private String menderName;


}
