package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author tiancun
 * @date 2019-04-28
 */
@Data
@ApiModel(value = "共性的请求参数")
public class CommonRequest implements Serializable {
    private static final long serialVersionUID = 2563597192660555795L;

    @ApiModelProperty(value = "产品ID列表")
    protected List<Long> productIdList;

    @ApiModelProperty(value = "代表ID列表")
    protected List<Long> drugUserIdList;

}
