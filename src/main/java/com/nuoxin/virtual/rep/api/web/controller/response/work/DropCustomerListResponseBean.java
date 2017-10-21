package com.nuoxin.virtual.rep.api.web.controller.response.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Create by tiancun on 2017/10/21
 */
@ApiModel(value = "脱落客户")
public class DropCustomerListResponseBean implements Serializable{
    private static final long serialVersionUID = 8774673281839025217L;

    @ApiModelProperty(value = "客户级别")
    private String level;

    @ApiModelProperty(value = "每个级别的脱落客户总数")
    private Integer levelDropCount;

    @ApiModelProperty(value = "脱落客户最多的坐席列表")
    private List<DrugUserAnalysisResponseBean> maxDropDrugUserList;


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getLevelDropCount() {
        return levelDropCount;
    }

    public void setLevelDropCount(Integer levelDropCount) {
        this.levelDropCount = levelDropCount;
    }

    public List<DrugUserAnalysisResponseBean> getMaxDropDrugUserList() {
        return maxDropDrugUserList;
    }

    public void setMaxDropDrugUserList(List<DrugUserAnalysisResponseBean> maxDropDrugUserList) {
        this.maxDropDrugUserList = maxDropDrugUserList;
    }
}
