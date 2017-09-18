package com.nuoxin.virtual.rep.api.web.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 9/18/17.
 */
@ApiModel
public class SmsSendRequestBean implements Serializable {

    private static final long serialVersionUID = 2326614631256049253L;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "模板")
    private Long templateId;
    @ApiModelProperty(value = "不用传")
    private Long drugUserId;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }
}
