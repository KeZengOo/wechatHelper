package com.nuoxin.virtual.rep.api.web.controller.request.hcp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author tiancun
 */
@ApiModel(value = "医生基本信息修改")
public class HcpBasicUpdateRequestBean implements Serializable{
    private static final long serialVersionUID = -3406253254634803400L;

    @ApiModelProperty(value = "医生id")
    private Long doctorId;

    @ApiModelProperty(value = "销售id,前端不用传，后端可以从登录获取")
    private Long drugUserId;

    @ApiModelProperty(value = "分类")
    private Integer classification;



    @ApiModelProperty(value = "医生更新的字段")
    private List<HcpBasicUpdateRequestBean> list;



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

    public Integer getClassification() {
        return classification;
    }

    public void setClassification(Integer classification) {
        this.classification = classification;
    }

    public List<HcpBasicUpdateRequestBean> getList() {
        return list;
    }

    public void setList(List<HcpBasicUpdateRequestBean> list) {
        this.list = list;
    }
}
