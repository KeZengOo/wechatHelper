package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tiancun
 * @date 2019-04-18
 */
@Data
@ApiModel(value = "医生互动的数据")
public class DoctorInteractResponseBean implements Serializable {
    private static final long serialVersionUID = 671188184047536240L;

    @ApiModelProperty(value = "微信拜访次数")
    private Integer wechatVisitCount;

    @ApiModelProperty(value = "阅读次数")
    private Integer readCount;

    @ApiModelProperty(value = "参会的次数")
    private Integer meetingCount;



}
