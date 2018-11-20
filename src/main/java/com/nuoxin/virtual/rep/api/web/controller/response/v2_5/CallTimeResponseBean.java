package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通话时长返回数据
 * @author tiancun
 * @date 2018-11-20
 */
@Data
@ApiModel(value = "通话时长返回数据")
public class CallTimeResponseBean implements Serializable {
    private static final long serialVersionUID = -2311627192579683554L;

    @ApiModelProperty(value = "通话时长，单位秒")
    private Integer callTime;

    @ApiModelProperty(value = "接通次数")
    private Integer callCount;


}
