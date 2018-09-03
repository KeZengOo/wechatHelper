package com.nuoxin.virtual.rep.api.web.controller.response.analysis.q;

import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by fenggang on 10/12/17.
 */
@ApiModel
public class QuestionStatResponseBean extends QuestionRequestBean {
    private static final long serialVersionUID = -1032311111048047535L;

    @ApiModelProperty(value = "答题人数")
    private Integer answerNum;
    @ApiModelProperty(value = "选项统计")
    private List<QuestionOptionsStatResponseBean> optionsStat;
    private Long questionnaireId;

    private String option;

    public Integer getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(Integer answerNum) {
        this.answerNum = answerNum;
    }

    public List<QuestionOptionsStatResponseBean> getOptionsStat() {
        return optionsStat;
    }

    public void setOptionsStat(List<QuestionOptionsStatResponseBean> optionsStat) {
        this.optionsStat = optionsStat;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }
}
