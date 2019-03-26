package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tiancun
 * @date 2019-03-25
 */
@ApiModel(value = "问卷")
@Data
public class ContentQuestionnaireResponseBean implements Serializable {
    private static final long serialVersionUID = 4389577406605263163L;


    @ApiModelProperty(value = "问卷ID")
    private Long questionnaireId;

    @ApiModelProperty(value = "问卷标题")
    private String questionnaireTitle;

    @ApiModelProperty(value = "答题时间")
    private String answerTime;

    @ApiModelProperty(value = "答题数量")
    private Integer answerNum;

}
