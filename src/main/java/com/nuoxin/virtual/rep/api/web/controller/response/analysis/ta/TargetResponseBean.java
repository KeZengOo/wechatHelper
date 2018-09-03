package com.nuoxin.virtual.rep.api.web.controller.response.analysis.ta;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by fenggang on 10/12/17.
 */
@ApiModel
public class TargetResponseBean implements Serializable {

    private static final long serialVersionUID = 2477200536129913041L;

    @ApiModelProperty(value = "目标数")
    private Integer targetNum = 0;
    @ApiModelProperty(value = "覆盖数")
    private Integer coverNum = 0;
    @ApiModelProperty(value = "比例")
    private Float percentage = 0f;

    public Integer getTargetNum() {
        return targetNum;
    }

    public void setTargetNum(Integer targetNum) {
        this.targetNum = targetNum;
    }

    public Integer getCoverNum() {
        return coverNum;
    }

    public void setCoverNum(Integer coverNum) {
        this.coverNum = coverNum;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = new BigDecimal(percentage*100).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
