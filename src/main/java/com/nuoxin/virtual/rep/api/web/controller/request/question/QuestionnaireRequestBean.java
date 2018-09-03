package com.nuoxin.virtual.rep.api.web.controller.request.question;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fenggang on 9/11/17.
 */
@ApiModel
public class QuestionnaireRequestBean implements Serializable {

    private static final long serialVersionUID = -955947577004247095L;

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "产品id")
    private Long productId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "问题")
    private List<QuestionRequestBean> questions;
    private Long drugUserId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<QuestionRequestBean> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionRequestBean> questions) {
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
