package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色表
 * @author wujiang
 * @date 2019-03-25
 */
@ApiModel("角色表")
@Data
public class RoleParams {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "创建时间")
    private String createDate;
    @ApiModelProperty(value = "更新时间")
    private String updateDate;
}
