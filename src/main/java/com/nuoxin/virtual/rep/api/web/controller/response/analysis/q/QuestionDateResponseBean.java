package com.nuoxin.virtual.rep.api.web.controller.response.analysis.q;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 10/16/17.
 */
@ApiModel
public class QuestionDateResponseBean implements Serializable {

    private static final long serialVersionUID = -6713979785849900263L;

    @ApiModelProperty(value = "日期")
    private String date;
    @ApiModelProperty(value = "月")
    private Integer monthNum;
    @ApiModelProperty(value = "天")
    private Integer dayNum;
    @ApiModelProperty(value = "次数")
    private Integer countNum = 0;
    @ApiModelProperty(value = "人数")
    private Integer peopleNum = 0;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getMonthNum() {
        return monthNum;
    }

    public void setMonthNum(Integer monthNum) {
        this.monthNum = monthNum;
    }

    public Integer getDayNum() {
        return dayNum;
    }

    public void setDayNum(Integer dayNum) {
        this.dayNum = dayNum;
    }

    public Integer getCountNum() {
        return countNum;
    }

    public void setCountNum(Integer countNum) {
        this.countNum = countNum;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }
}
