package com.nuoxin.virtual.rep.api.web.controller.response.analysis.q;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by fenggang on 10/12/17.
 */
@ApiModel
public class QuestionOptionsStatResponseBean implements Serializable {
    private static final long serialVersionUID = 2371495444436598429L;

    private Long questionId;
    @ApiModelProperty(value = "选项")
    private String key;
    @ApiModelProperty(value = "数量")
    private Integer num;
    @ApiModelProperty(value = "百分比")
    private Float percentage;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getNum() {
        return num==null?0:num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        percentage = new BigDecimal(percentage*100).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
        this.percentage = percentage;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
