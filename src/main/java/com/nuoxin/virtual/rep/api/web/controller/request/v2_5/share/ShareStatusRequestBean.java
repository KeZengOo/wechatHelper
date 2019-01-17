package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分享类型请求参数
 * @author tiancun
 * @date 2018-09-20
 */
@ApiModel(value = "分享类型请求参数")
@Data
public class ShareStatusRequestBean implements Serializable {
    private static final long serialVersionUID = -6474836526340407081L;

    @ApiModelProperty(value = "分享ID", required = true)
    private Long shareId;

    @ApiModelProperty(value = "分享类型：1是成功拜访，2是服务，3是无人回应", required = true)
    private Integer shareStatus;

}
