package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 从库产品表
 * @author wujiang
 */
@ApiModel("从库产品表")
@Data
public class EnterpriseProductParams {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "产品code")
    private String code;
    @ApiModelProperty(value = "产品名称")
    private String name;
    @ApiModelProperty(value = "产品描述")
    private String descriptions;
}
