package com.nuoxin.virtual.rep.api.web.controller.response.analysis.ta;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by fenggang on 10/12/17.
 */
@ApiModel
public class MettingTargetResponseBean implements Serializable {
    private static final long serialVersionUID = -4188264882364262626L;

    @ApiModelProperty(value = "目标会议数")
    private Integer mTargetCount = 0;
    @ApiModelProperty(value = "会议数")
    private Integer mCount = 0;
    @ApiModelProperty(value = "会议百分比")
    private Float mPercentage = 0f;

    @ApiModelProperty(value = "目标医生数")
    private Integer dTargetCount = 0;
    @ApiModelProperty(value = "邀请医生数")
    private Integer dCount = 0;
    @ApiModelProperty(value = "医生百分比")
    private Float dPercentage = 0f;

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
        mPercentage = new BigDecimal(mPercentage*100).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
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
        dPercentage = new BigDecimal(dPercentage*100).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
        this.dPercentage = dPercentage;
    }
}
