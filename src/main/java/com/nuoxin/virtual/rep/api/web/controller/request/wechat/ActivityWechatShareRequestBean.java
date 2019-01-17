package com.nuoxin.virtual.rep.api.web.controller.request.wechat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信分享手动记录请求参数
 * @author tiancun
 * @date 2019-01-02
 */
@Data
@ApiModel(value = "微信分享手动记录请求参数")
public class ActivityWechatShareRequestBean implements Serializable {
    private static final long serialVersionUID = 8927113095140785080L;

    @ApiModelProperty(value = "内容ID")
    private Long contentId;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "销售代表ID")
    private Long drugUserId;



}
