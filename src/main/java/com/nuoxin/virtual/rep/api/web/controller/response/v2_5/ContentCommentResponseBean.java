package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 内容评论
 * @author tiancun
 * @date 2018-09-20
 */
@ApiModel(value = "内容评论")
@Data
public class ContentCommentResponseBean implements Serializable{
    private static final long serialVersionUID = -2808708249512558062L;

    @ApiModelProperty(value = "内容ID")
    private Long contentId;

    @ApiModelProperty(value = "评论内容")
    private String comment;

    @ApiModelProperty(value = "评论时间")
    private String commentTime;
}
