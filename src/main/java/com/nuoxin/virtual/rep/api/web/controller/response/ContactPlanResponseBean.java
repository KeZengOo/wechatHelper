package com.nuoxin.virtual.rep.api.web.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 10/18/17.
 */
@ApiModel
public class ContactPlanResponseBean implements Serializable {

    private static final long serialVersionUID = 1634916412127245233L;

    @ApiModelProperty(value = "新增不用传，编辑时候传")
    private Long id;
    @ApiModelProperty(value = "医生id")
    private Long doctorId;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "时间（格式：yyyy-MM-dd HH:mm:ss）")
    private String date;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "处理人")
    private String drugUserName;

    @ApiModelProperty(value = "不用传")
    private Long drugUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDrugUserName() {
        return drugUserName;
    }

    public void setDrugUserName(String drugUserName) {
        this.drugUserName = drugUserName;
    }
}
