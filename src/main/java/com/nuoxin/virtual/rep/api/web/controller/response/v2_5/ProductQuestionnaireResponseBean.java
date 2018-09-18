package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医生详情产品信息问卷调查
 * @author tiancun
 * @date 2018-09-18
 */
@ApiModel(value = "医生详情产品信息问卷调查")
@Data
public class ProductQuestionnaireResponseBean implements Serializable {
    private static final long serialVersionUID = -7216872607499169380L;

    @ApiModelProperty(value = "问卷ID")
    private Long questionnaireId;

    @ApiModelProperty(value = "问卷名称")
    private String questionnaireName;

    @ApiModelProperty(value = "回答时间")
    private String answerTime;

    @ApiModelProperty(value = "上次调研时间")
    private String beforeTime;

    @ApiModelProperty(value = "销售代表姓名")
    private String drugUserName;

    @ApiModelProperty(value = "题目总数")
    private Integer questionCount;

    @ApiModelProperty(value = "已经回答的题目总数")
    private Integer answerQuestionCount;

}
