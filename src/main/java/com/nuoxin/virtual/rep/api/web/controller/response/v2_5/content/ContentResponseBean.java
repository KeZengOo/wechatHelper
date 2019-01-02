package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.content;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 内容返回数据
 * @author tiancun
 * @date 2019-01-02
 */
@Data
@ApiModel(value = "内容返回数据")
public class ContentResponseBean implements Serializable {
    private static final long serialVersionUID = -7997331610829703520L;

    @ApiModelProperty(value = "内容ID")
    private Long contentId;

    @ApiModelProperty(value = "文章标题")
    private String title;



}
