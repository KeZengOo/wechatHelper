package com.nuoxin.virtual.rep.api.web.controller.request.call;

import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionnaireRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fenggang on 9/12/17.
 */
@ApiModel
public class CallInfoRequestBean implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "问卷信息")
    private List<QuestionnaireRequestBean> questions;
    @ApiModelProperty(value = "备注")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<QuestionnaireRequestBean> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionnaireRequestBean> questions) {
        this.questions = questions;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
