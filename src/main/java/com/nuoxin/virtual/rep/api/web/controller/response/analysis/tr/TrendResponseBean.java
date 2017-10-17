package com.nuoxin.virtual.rep.api.web.controller.response.analysis.tr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 10/12/17.
 */
@ApiModel
public class TrendResponseBean implements Serializable {

    private static final long serialVersionUID = 6433705231350969340L;

    @ApiModelProperty(value = "数量")
    private Integer num;
    @ApiModelProperty(value = "百分比")
    private Float percentage;
    @ApiModelProperty(value = "日期")
    private String date;
    @ApiModelProperty(value = "月")
    private Integer month;
    @ApiModelProperty(value = "日")
    private Integer day;
    @ApiModelProperty(value = "周")
    private Integer week;
    @ApiModelProperty(value = "年")
    private Integer year;
    @ApiModelProperty(value = "季度")
    private Integer quarter;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }
}
