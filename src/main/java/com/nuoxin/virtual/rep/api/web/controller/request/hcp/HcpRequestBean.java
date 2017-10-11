package com.nuoxin.virtual.rep.api.web.controller.request.hcp;


import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by guanliyuan on 17/8/2.
 */
@ApiModel(value = "主数据医生请求相关参数")
public class HcpRequestBean extends PageRequestBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主数据医生id")
    private Long hcpId;

    @ApiModelProperty(value = "主数据医生姓名")
    private String hcpName;

    @ApiModelProperty(value = "主数据医院id")
    private Long hciId;

    @ApiModelProperty(value = "主数据医生科室")
    private String dept;

    @ApiModelProperty(value = "主数据医生级别")
    private String levelName;

    @ApiModelProperty(value = "销售id")
    private Long drugUserId;

    public Long getHcpId() {
        return hcpId;
    }

    public void setHcpId(Long hcpId) {
        this.hcpId = hcpId;
    }

    public String getHcpName() {
        return hcpName;
    }

    public void setHcpName(String hcpName) {
        this.hcpName = hcpName;
    }

    public Long getHciId() {
        return hciId;
    }

    public void setHciId(Long hciId) {
        this.hciId = hciId;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    @Override
    public String toString() {
        return "" + hcpId + hcpName + dept + levelName + hciId + drugUserId +
                 super.getPage() + super.getPageSize();

    }
}

