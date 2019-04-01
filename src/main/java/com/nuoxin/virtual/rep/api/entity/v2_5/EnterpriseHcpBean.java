package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 医生表
 * @author wujiang
 * @date 2019-03-28
 */
@ApiModel("医生表")
@Data
public class EnterpriseHcpBean {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "医生名称")
    private String name;
    @ApiModelProperty(value = "医院ID")
    private Long hospitalId;
    @ApiModelProperty(value = "医院名称")
    private String hospitalName;
    @ApiModelProperty(value = "科室")
    private String depart;
    @ApiModelProperty(value = "性别")
    private String sex;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "更新时间")
    private String modifyTime;

}
