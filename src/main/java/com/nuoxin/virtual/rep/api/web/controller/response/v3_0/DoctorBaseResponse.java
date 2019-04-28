package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 医生返回的基础信息
 * @author tiancun
 * @date 2019-04-28
 */
@ApiModel(value = "医生返回的基础信息")
@Data
public class DoctorBaseResponse implements Serializable {
    private static final long serialVersionUID = 3675796506873746209L;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "医生姓名")
    private String doctorName;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "职称")
    private String position;

    @ApiModelProperty(value = "医生联系方式")
    private List<String> telephoneList;

    @ApiModelProperty(value = "医院ID")
    private Long hospitalId;

    @ApiModelProperty(value = "医院名称")
    private String hospitalName;

    @ApiModelProperty(value = "科室")
    private String depart;

    @ApiModelProperty(value = "代表ID")
    private Long drugUserId;

    @ApiModelProperty(value = "代表姓名")
    private String drugUserName;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;


    @ApiModelProperty(value = "是否是目标客户")
    private String target;

    @ApiModelProperty(value = "招募状态")
    private String recruit;

    @ApiModelProperty(value = "是否有药")
    private String hasDrug;

    @ApiModelProperty(value = "覆盖状态")
    private String cover;


}
