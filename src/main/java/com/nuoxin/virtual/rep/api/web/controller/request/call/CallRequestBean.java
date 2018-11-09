package com.nuoxin.virtual.rep.api.web.controller.request.call;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by fenggang on 9/12/17.
 */
@ApiModel
public class CallRequestBean implements Serializable {

    private static final long serialVersionUID = 1742302643174127854L;
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "电话")
    private String mobile;
    @ApiModelProperty(value = "唯一标识")
    private String sinToken;
    @ApiModelProperty(value = "状态名称")
    private String statusName;
    @ApiModelProperty(value = "状态(1-成功接通，0-未成功)")
    private Integer status;
    @ApiModelProperty(value = "类型(1,呼出,2-呼入)")
    private Integer type;
    @ApiModelProperty(value = "通话时长")
    private Long times;
    @ApiModelProperty(value = "录音文件url")
    private String url;

    @ApiModelProperty(value = "产品id")
    private Long productId;
    private Long drugUserId;

    @NotNull(message = "医生ID不能为空")
    private Long doctorId;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSinToken() {
        return sinToken;
    }

    public void setSinToken(String sinToken) {
        this.sinToken = sinToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }


    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
