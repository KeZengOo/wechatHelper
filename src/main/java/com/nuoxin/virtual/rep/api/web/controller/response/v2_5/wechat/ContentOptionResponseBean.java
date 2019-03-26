package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tiancun
 * @date 2019-03-25
 */
@Data
public class ContentOptionResponseBean implements Serializable {
    private static final long serialVersionUID = -1778221289332841674L;


    @ApiModelProperty(value = "选项ID")
    private Long optionId;

    @ApiModelProperty(value = "选项")
    private String option;




}
