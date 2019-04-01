package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 销售与医生关系指标表
 * @author wujiang
 * @date 2019-03-26
 */
@ApiModel("销售与医生关系指标")
@Data
public class DrugUserDoctorQuateBean {

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "医生ID")
    private Long doctorId;
    @ApiModelProperty(value = "销售ID")
    private Long drugUserId;
    @ApiModelProperty(value = "产品ID")
    private Long productId;
    @ApiModelProperty(value = "是否有药1是,0否,-1未知")
    private Integer isHasDrug;
    @ApiModelProperty(value = "是否目标客户1是,0否,-1未知")
    private Integer isTarget;
    @ApiModelProperty(value = "是否有AE1是,0否,-1未知")
    private Integer isHasAe;
    @ApiModelProperty(value = "是否招募 1是,0否,-1未知，2退出项目")
    private Integer isRecruit;
    @ApiModelProperty(value = "是否脱落 1是,0否,-1未知")
    private Integer isBreakOff;
    @ApiModelProperty(value = "医生潜力 3高,2中,1低,-1未知")
    private Integer hcpPotential;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "记录是否可用 1可用,0不可用")
    private Integer isAvailable;

}
