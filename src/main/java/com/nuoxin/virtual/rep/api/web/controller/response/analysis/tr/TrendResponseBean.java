package com.nuoxin.virtual.rep.api.web.controller.response.analysis.tr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 10/12/17.
 */
@ApiModel
public class TrendResponseBean implements Serializable {

    private static final long serialVersionUID = 6433705231350969340L;

    @ApiModelProperty(value = "横坐标")
    private String title;
    @ApiModelProperty(value = "数量")
    private Integer num;
    @ApiModelProperty(value = "百分比")
    private Float percentage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }
}
