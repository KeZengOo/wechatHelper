package com.nuoxin.virtual.rep.api.web.controller.request;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/12
 */
@ApiModel(value = "工作台请求类")
public class WorkStationRequestBean extends PageRequestBean implements Serializable{
    private static final long serialVersionUID = 3083177023027501328L;

    @ApiModelProperty(value = "销售id，前端不用传")
    private Long drugUserId;

    @ApiModelProperty(value = "模糊查询销售，前端不用传")
    private String leaderPath;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "日期类型，1是今天，2是本周，3是本月，4是本季度")
    private Integer dateType;

    @ApiModelProperty(value = "通话达标设置的值，前端不用传")
    private Integer callReach;

    @ApiModelProperty(value = "微信达标设置的值，前端不用传")
    private Integer wechatReach;

    @ApiModelProperty(value = "短信达标设置的值，前端不用传")
    private Integer imReach;

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

    public String getLeaderPath() {
        return leaderPath;
    }

    public void setLeaderPath(String leaderPath) {
        this.leaderPath = leaderPath;
    }


    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }


    public Integer getCallReach() {
        return callReach;
    }

    public void setCallReach(Integer callReach) {
        this.callReach = callReach;
    }

    public Integer getWechatReach() {
        return wechatReach;
    }

    public void setWechatReach(Integer wechatReach) {
        this.wechatReach = wechatReach;
    }

    public Integer getImReach() {
        return imReach;
    }

    public void setImReach(Integer imReach) {
        this.imReach = imReach;
    }
}
