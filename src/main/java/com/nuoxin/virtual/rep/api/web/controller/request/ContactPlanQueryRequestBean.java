package com.nuoxin.virtual.rep.api.web.controller.request;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by fenggang on 10/18/17.
 */
@ApiModel
public class ContactPlanQueryRequestBean extends PageRequestBean {

    private static final long serialVersionUID = -994512497197291600L;

    @ApiModelProperty(value = "状态（0-未完成，1-完成）")
    private Integer status;
    @ApiModelProperty(value = "医生id")
    private Long doctorId;
    @ApiModelProperty(value = "销售id（从左边的列表上获取）")
    private Long drugUserId;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
