package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 医院表
 * @author wujiang
 * @date 2019-03-27
 */
@ApiModel("医院表")
@Data
public class HospitalBean {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "医院名称")
    private String name;
    @ApiModelProperty(value = "医院级别")
    private String level;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
