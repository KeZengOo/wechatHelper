package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 医生详情产品信息问题详情
 * @author tiancun
 * @date 2018-09-18
 */
@ApiModel(value = "医生详情产品信息问题详情")
public class DynamicFieldQuestionDetailResponseBean implements Serializable {
    private static final long serialVersionUID = 3150701929998936382L;

    @ApiModelProperty(value = "问题标题")
    private String title;

    @ApiModelProperty(value = "问题选项")
    private String options;

    @ApiModelProperty(value = "问题答案")
    private String answer;



}
