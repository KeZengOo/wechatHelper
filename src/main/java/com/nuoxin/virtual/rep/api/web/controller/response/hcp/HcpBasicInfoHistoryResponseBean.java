package com.nuoxin.virtual.rep.api.web.controller.response.hcp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author tiancun
 */
@ApiModel(value = "基本信息修改历史")
public class HcpBasicInfoHistoryResponseBean implements Serializable{
    private static final long serialVersionUID = 4297902544221975021L;


    @ApiModelProperty(value = "字段名称")
    private String fieldName;

    @ApiModelProperty(value = "字段的值")
    private String fieldValue;

    @ApiModelProperty(value = "")
    private String drugUserName;



}
