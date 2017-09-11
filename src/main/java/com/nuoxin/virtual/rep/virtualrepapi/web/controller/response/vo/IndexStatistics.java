package com.nuoxin.virtual.rep.virtualrepapi.web.controller.response.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tiancun on 17/8/4.
 */
@ApiModel(value = "首页统计")
public class IndexStatistics implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "医院总数")
    private Integer hciCount;

    @ApiModelProperty(value = "医院级别统计")
    private List<HciLevelStatistics> hciGroupInfo;

    @ApiModelProperty(value = "医生总数")
    private Integer hcpCount;

    @ApiModelProperty(value = "医生级别统计")
    private List<HcpLevelStatistics> hcpGroupInfo;


    public Integer getHciCount() {
        return hciCount;
    }

    public void setHciCount(Integer hciCount) {
        this.hciCount = hciCount;
    }

    public List<HciLevelStatistics> getHciGroupInfo() {
        return hciGroupInfo;
    }

    public void setHciGroupInfo(List<HciLevelStatistics> hciGroupInfo) {
        this.hciGroupInfo = hciGroupInfo;
    }

    public Integer getHcpCount() {
        return hcpCount;
    }

    public void setHcpCount(Integer hcpCount) {
        this.hcpCount = hcpCount;
    }

    public List<HcpLevelStatistics> getHcpGroupInfo() {
        return hcpGroupInfo;
    }

    public void setHcpGroupInfo(List<HcpLevelStatistics> hcpGroupInfo) {
        this.hcpGroupInfo = hcpGroupInfo;
    }
}
