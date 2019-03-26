package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 电话拜访扩展表
 * @author wujiang
 * @date 2019-03-26
 */
@ApiModel("电话拜访扩展表")
@Data
public class VirtualDoctorCallInfoMendBean {

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "关联销售代表id")
    private Long virtualDrugUserId;
    @ApiModelProperty(value = "关联医生id")
    private Long virtualDoctorId;
    @ApiModelProperty(value = "关联产品线id")
    private Integer productId;
    @ApiModelProperty(value = "下次拜访时间")
    private Date nextVisitTime;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "拜访结果以数组形式存储如:[结果1,结果2]")
    private String visitResult;
    @ApiModelProperty(value = "关联 virtual_doctor_call_info.id")
    private Long callId;
    @ApiModelProperty(value = "医生态度0-5分")
    private Integer attitude;
    @ApiModelProperty(value = "问卷ID")
    private Integer doctorQuestionnaireId;
    @ApiModelProperty(value = "医生潜力 3高,2中,1低,-1未知")
    private Integer hcpPotential;
    @ApiModelProperty(value = "是否脱落 1是,0否,-1未知")
    private Integer isBreakOff;
    @ApiModelProperty(value = "是否有AE 1是,0否.-1未知")
    private Integer isHasAe;
    @ApiModelProperty(value = "是否目标客户1是,0否 -1未知")
    private Integer isTarget;
    @ApiModelProperty(value = "是否有药1是,0否,-1未知")
    private Integer isHasDrug;
    @ApiModelProperty(value = "是否招募 1是,0否,-1未知，2退出项目")
    private Integer isRecruit;
    @ApiModelProperty(value = "招募时间")
    private Date recruitTime;
    @ApiModelProperty(value = "移出时间")
    private Date dropOutTime;

}
