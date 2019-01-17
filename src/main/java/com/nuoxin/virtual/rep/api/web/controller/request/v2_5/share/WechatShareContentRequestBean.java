package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 手动记录微信分享
 * @author tiancun
 * @date 2019-01-03
 */
@Data
@ApiModel(value = "手动记录微信分享请求参数")
public class WechatShareContentRequestBean implements Serializable {
    private static final long serialVersionUID = 7211765986842612583L;

    @ApiModelProperty(value = "医生ID列表")
    private List<Long> doctorIdList;

    @ApiModelProperty(value = "内容ID")
    private Long contentId;

    @ApiModelProperty(value = "代表ID")
    private Long drugUserId;

    @ApiModelProperty(value = "分享时间")
    private String shareTime;

}
