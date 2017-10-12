package com.nuoxin.virtual.rep.api.web.controller.request.analysis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 10/12/17.
 */
@ApiModel
public class TargetAnalysisRequestBean implements Serializable{
    private static final long serialVersionUID = 8831698029573694558L;

    @ApiModelProperty(value = "产品id")
    private Long productId;
    @ApiModelProperty(value = "日期（格式为:yyyy-MM-dd）")
    private String date;
    @ApiModelProperty(value = "坐席id")
    private Long drugUserId;
    @ApiModelProperty(value = "客户等级")
    private String clientLevle;
    @ApiModelProperty(value = "区域")
    private String area;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    public String getClientLevle() {
        return clientLevle;
    }

    public void setClientLevle(String clientLevle) {
        this.clientLevle = clientLevle;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
