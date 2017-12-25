package com.nuoxin.virtual.rep.api.web.controller.request.question;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 9/12/17.
 */
@ApiModel
public class QuestionQueryRequestBean extends PageRequestBean{
    private static final long serialVersionUID = 7316202160035386189L;

    @ApiModelProperty(value = "查询内容")
    private String query;
    @ApiModelProperty(value = "产品id")
    private Long productId;
    @ApiModelProperty(value = "手机号")
    private String mobile;

    private Long drugUserId;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return  query + drugUserId + super.toString();
    }
}
