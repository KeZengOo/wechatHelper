package com.nuoxin.virtual.rep.api.web.controller.response.analysis.q;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fenggang on 10/12/17.
 */
@ApiModel
public class QuestionnaireStatResponseBean implements Serializable{
    private static final long serialVersionUID = 6168647379339853033L;

    @ApiModelProperty(value = "人数")
    private Integer peopleNum;
    @ApiModelProperty(value = "次数")
    private Integer countNum;

    @ApiModelProperty(value = "图表集合")
    private List<QuestionDateResponseBean> list;

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Integer getCountNum() {
        return countNum;
    }

    public void setCountNum(Integer countNum) {
        this.countNum = countNum;
    }

    public List<QuestionDateResponseBean> getList() {
        return list;
    }

    public void setList(List<QuestionDateResponseBean> list) {
        this.list = list;
    }
}
