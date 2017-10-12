package com.nuoxin.virtual.rep.api.web.controller.response.analysis.ta;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 10/12/17.
 */
@ApiModel
public class MettingTargetResponseBean implements Serializable {
    private static final long serialVersionUID = -4188264882364262626L;

    @ApiModelProperty(value = "目标会议数")
    private Integer mTargetCount;
    @ApiModelProperty(value = "会议数")
    private Integer mCount;
    @ApiModelProperty(value = "会议百分比")
    private Float mPercentage;

    @ApiModelProperty(value = "目标医生数")
    private Integer dTargetCount;
    @ApiModelProperty(value = "邀请医生数")
    private Integer dCount;
    @ApiModelProperty(value = "医生百分比")
    private Float dPercentage;

    public Integer getmTargetCount() {
        return mTargetCount;
    }

    public void setmTargetCount(Integer mTargetCount) {
        this.mTargetCount = mTargetCount;
    }

    public Integer getmCount() {
        return mCount;
    }

    public void setmCount(Integer mCount) {
        this.mCount = mCount;
    }

    public Float getmPercentage() {
        return mPercentage;
    }

    public void setmPercentage(Float mPercentage) {
        this.mPercentage = mPercentage;
    }

    public Integer getdTargetCount() {
        return dTargetCount;
    }

    public void setdTargetCount(Integer dTargetCount) {
        this.dTargetCount = dTargetCount;
    }

    public Integer getdCount() {
        return dCount;
    }

    public void setdCount(Integer dCount) {
        this.dCount = dCount;
    }

    public Float getdPercentage() {
        return dPercentage;
    }

    public void setdPercentage(Float dPercentage) {
        this.dPercentage = dPercentage;
    }
}
