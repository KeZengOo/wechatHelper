package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 代表和医生电话拜访统计数
 * @author tiancun
 * @date 2019-05-09
 */
@Data
@ApiModel(value = "代表和医生电话拜访统计数")
public class DrugUserDoctorCallResponse  implements Serializable{


    private static final long serialVersionUID = 2654595322238499752L;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "代表ID")
    private Long drugUserId;

    @ApiModelProperty(value = "代表姓名")
    private String drugUserName;

    @ApiModelProperty(value = "代表角色")
    private String drugUserRole;

    @ApiModelProperty(value = "线上或者线下")
    private String drugUserVisitType;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "医生姓名")
    private String doctorName;

    @ApiModelProperty(value = "医院ID")
    private Long hospitalId;

    @ApiModelProperty(value = "医院名称")
    private String hospitalName;


    @ApiModelProperty(value = "医生科室")
    private String depart;

    @ApiModelProperty(value = "覆盖状态")
    private String coverStatus;

    @ApiModelProperty(value = "招募状态")
    private String recruitStatus;

    @ApiModelProperty(value = "外呼数")
    private Integer callCount;

    @ApiModelProperty(value = "接通数")
    private Integer connectCallCount;

    @ApiModelProperty(value = "接通率")
    private String connectRate;

    @ApiModelProperty(value = "接通总时长")
    private String totalCallTime;

    @ApiModelProperty(value = "总通话，单位秒")
    @JsonIgnore
    private Long totalCallSecond;

    @ApiModelProperty(value = "最后一次拜访时间")
    private String lastVisitTime;

    @ApiModelProperty(value = "最后一次拜访时间")
    @JsonIgnore
    private Date lastVisitDate;

    @ApiModelProperty(value = "最后一次通话时长")
    private String lastCallTime;

    @ApiModelProperty(value = "最后一次通话，单位秒")
    @JsonIgnore
    private Long lastCallSecond;


}
