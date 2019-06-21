package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.questionnaire;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tiancun
 * @date 2019-06-21
 */
@Data
@ApiModel(value = "问卷网答案")
public class QuestionnaireAnswerResponse implements Serializable {
    private static final long serialVersionUID = -2269821385108491673L;

    @ApiModelProperty(value = "本条数据ID")
    private Long id;

    @ApiModelProperty(value = "答案")
    private String answer;

    @ApiModelProperty(value = "匹配上的手机号")
    private String telephone;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;


}
