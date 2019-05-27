package com.nuoxin.virtual.rep.api.entity.v3_0.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 通话页面拜访目的请求参数
 * @author tiancun
 * @date 2019-05-27
 */
@Data

public class PurposeParams {

    @ApiModelProperty(value = "本次打电话ID")
    private Long callId;

    @ApiModelProperty(value = "目的")
    private String purpose;

    @ApiModelProperty(value = "拜访结果ID")
    private Long resultId;


}
