package com.nuoxin.virtual.rep.api.web.controller.request.question;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fenggang on 9/11/17.
 */
@ApiModel
public class QuestionRequestBean implements Serializable {

    private static final long serialVersionUID = -7014923363775468068L;

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "选项")
    private List<OptionsRequestBean> options;
    @ApiModelProperty(value = "答案")
    private String answer;
    @ApiModelProperty(value = "类型(0-选项，1-问答)")
    private Integer type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<OptionsRequestBean> getOptions() {
        return options;
    }

    public void setOptions(List<OptionsRequestBean> options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
