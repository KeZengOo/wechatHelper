package com.nuoxin.virtual.rep.api.web.controller.response.call;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 9/12/17.
 */
@ApiModel
public class CallResponseBean implements Serializable {

    private static final long serialVersionUID = 199411772918914469L;

    @ApiModelProperty(value = "id")
    private Long doctorId;
    @ApiModelProperty(value = "姓名")
    private String doctorName;
    @ApiModelProperty(value = "电话")
    private String doctorMobile;
    @ApiModelProperty(value = "客户等级")
    private String clientLevel;
    @ApiModelProperty(value = "通话时间（时间戳）")
    private Long timeLong;
    @ApiModelProperty(value = "通话时间（yyyy-MM-dd HH:mm）")
    private String timeStr;
    @ApiModelProperty(value = "druguserId")
    private Long drugUserId;
    @ApiModelProperty(value = "产品id")
    private Long productId;
    @ApiModelProperty(value = "druguserName")
    private String drugUserName;

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorMobile() {
        return doctorMobile;
    }

    public void setDoctorMobile(String doctorMobile) {
        this.doctorMobile = doctorMobile;
    }

    public String getClientLevel() {
        return clientLevel;
    }

    public void setClientLevel(String clientLevel) {
        this.clientLevel = clientLevel;
    }

    public Long getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(Long timeLong) {
        this.timeLong = timeLong;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getDrugUserName() {
        return drugUserName;
    }

    public void setDrugUserName(String drugUserName) {
        this.drugUserName = drugUserName;
    }

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
