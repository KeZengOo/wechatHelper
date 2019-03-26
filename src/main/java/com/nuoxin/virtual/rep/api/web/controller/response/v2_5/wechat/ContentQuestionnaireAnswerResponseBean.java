package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author tiancun
 * @date 2019-03-25
 */
@ApiModel(value = "问卷答案")
@Data
public class ContentQuestionnaireAnswerResponseBean implements Serializable {
    private static final long serialVersionUID = 3565213225142338287L;


    @ApiModelProperty(value = "问卷标题")
    private String questionnaireTitle;

    @ApiModelProperty(value = "问题ID")
    private Long questionId;

    @ApiModelProperty(value = "问题标题")
    private String questionTitle;

    @ApiModelProperty(value = "题目类型，1是选择题，2多选题，3是问答")
    private Integer type;

    @ApiModelProperty(value = "选项")
    private List<ContentOptionResponseBean> optionList;

    @ApiModelProperty(value = "题目的答案")
    private List<String> answerList;

}
