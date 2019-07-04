package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import	java.util.Date;

import java.io.Serializable;

/**
 * @author tiancun
 *Datate 2019-07-04
 */
@Data
@ApiModel(value = "代表转医生操作")
public class DrugUserDoctorLogResponse implements Serializable {
    private static final long serialVersionUID = -57265023430049838L;

    @ApiModelProperty(value = "原代表ID")
    private Long oldDrugUserId;

    @ApiModelProperty(value = "原来代表姓名")
    private String oldDrugUserName;

    @ApiModelProperty(value = "新的代表ID")
    private Long newDrugUserId;

    @ApiModelProperty(value = "新的代表姓名")
    private String newDrugUserName;


    @ApiModelProperty(value = "操作时间")
    private String operateTime;

    @ApiModelProperty(value = "操作方式")
    private Integer operateWay;

    @ApiModelProperty(value = "操作人ID")
    private Long operateDrugUserId;

    @ApiModelProperty(value = "操作人")
    private String operateDrugUserName;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "医生姓名")
    private String doctorName;

    @ApiModelProperty(value = "医院ID")
    private Long hospitalId;

    @ApiModelProperty(value = "医院名称")
    private String hospitalName;

    @ApiModelProperty(value = "科室")
    private String depart;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;



}
