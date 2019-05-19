package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 拜访目的请求参数
 * @author tiancun
 * @date 2019-05-19
 */
@Data
@ApiModel(value = "拜访目的请求参数")
public class PurposeRequest implements Serializable {
    private static final long serialVersionUID = -8768939489125547911L;

    @ApiModelProperty(value = "本次打电话ID")
    private Long callId;

    @ApiModelProperty(value = "目的")
    private String purpose;

    @ApiModelProperty(value = "拜访结果ID列表")
    private List<Long> resultIdList;

}
