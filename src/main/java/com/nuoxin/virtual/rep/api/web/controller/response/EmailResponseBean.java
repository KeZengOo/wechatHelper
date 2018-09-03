package com.nuoxin.virtual.rep.api.web.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 10/18/17.
 */
@ApiModel
public class EmailResponseBean implements Serializable {
    private static final long serialVersionUID = 5168048583815511110L;

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "产品id")
    private Long productId;
    @ApiModelProperty(value = "邮件标题")
    private String title;
    @ApiModelProperty(value = "邮件正文")
    private String content;
    @ApiModelProperty(value = "时间")
    private String date;
    @ApiModelProperty(value = "发件人")
    private String drugUserName;
    private Long doctorId;
    private Long drugUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getDrugUserName() {
        return drugUserName;
    }

    public void setDrugUserName(String drugUserName) {
        this.drugUserName = drugUserName;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }
}
