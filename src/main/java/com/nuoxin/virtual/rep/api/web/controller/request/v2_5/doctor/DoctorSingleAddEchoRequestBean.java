package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 新增单个医生医生信息回显请求参数
 * @author tiancun
 * @date 2019-02-20
 */
@Data
@ApiModel(value = "新增单个医生医生信息回显请求参数")
public class DoctorSingleAddEchoRequestBean implements Serializable {
    private static final long serialVersionUID = 1903499179389124099L;

    @ApiModelProperty(value = "医生多个手机号")
    private List<String> telephones;


    @ApiModelProperty(value = "代表ID")
    @JsonIgnore
    private Long drugUserId;

    @ApiModelProperty(value = "医生姓名")
    private String doctorName;

    @ApiModelProperty(value = "医院姓名")
    private String hospitalName;

}
