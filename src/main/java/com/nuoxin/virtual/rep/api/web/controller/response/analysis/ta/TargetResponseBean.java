package com.nuoxin.virtual.rep.api.web.controller.response.analysis.ta;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 10/12/17.
 */
@ApiModel
public class TargetResponseBean implements Serializable {

    private static final long serialVersionUID = 2477200536129913041L;

    @ApiModelProperty(value = "目标数")
    private Integer targerNum;
    @ApiModelProperty(value = "覆盖数")
    private Integer coverNum;
    @ApiModelProperty(value = "比例")
    private Float percentage;

    public Integer getTargerNum() {
        return targerNum;
    }

    public void setTargerNum(Integer targerNum) {
        this.targerNum = targerNum;
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
        this.percentage = percentage;
    }
}
