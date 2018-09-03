package com.nuoxin.virtual.rep.api.web.controller.response.question;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by fenggang on 10/12/17.
 */
@ApiModel
public class QuestionnaireResponseBean implements Serializable {
    private static final long serialVersionUID = -7437886054126173918L;

    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "产品id")
    private Long productId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
