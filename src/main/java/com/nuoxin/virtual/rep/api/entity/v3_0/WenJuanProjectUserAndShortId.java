package com.nuoxin.virtual.rep.api.entity.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 问卷网-项目短ID和用户
 * @author wujiang
 * @date 20190426
 */
@ApiModel("问卷网-项目表")
@Data
public class WenJuanProjectUserAndShortId {

    @ApiModelProperty(value = "用户编号")
    private String user;
    @ApiModelProperty(value = "项目短ID")
    private String shortId;
}
