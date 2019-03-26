package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 内容问卷请求参数
 * @author tiancun
 * @date 2019-03-25
 */
@ApiModel(value = "内容问卷请求参数")
@Data
public class QuestionnaireRequestBean implements Serializable {
    private static final long serialVersionUID = -6676088169139900264L;

    @ApiModelProperty(value = "内容ID")
    private Long contentId;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "问卷ID")
    @NotNull(message = "问卷ID不能为空")
    private Long questionnaireId;

}
