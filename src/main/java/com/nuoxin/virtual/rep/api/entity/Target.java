package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Create by tiancun on 2017/10/16
 */
@Entity
@Table(name = "virtual_target")
public class Target extends IdEntity{

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "level", length = 10)
    private String level;

    @Column(name = "month_covered")
    private Integer monthCovered;

    @Column(name = "month_tel_num")
    private Integer monthTelNum;

    @Column(name = "month_tel_time")
    private Integer monthTelTime;

    @Column(name = "month_tel_person")
    private Integer monthTelPerson;

    @Column(name = "month_tel_avg_time")
    private Integer monthTelAvgTime;

    @Column(name = "month_im_num")
    private Integer monthImNum;

    @Column(name = "month_im_count")
    private Integer monthImCount;

    @Column(name = "month_wechat_num")
    private Integer monthWechatNum;

    @Column(name = "month_wechat_count")
    private Integer monthWechatCount;


    @Column(name = "month_email_num")
    private Integer monthEmailNum;

    @Column(name = "month_email_count")
    private Integer monthEmailCount;

    @Column(name = "month_meeting_num")
    private Integer monthMeetingNum;

    @Column(name = "month_meeting_time")
    private Integer monthMeetingTime;

    @Column(name = "month_meeting_person_sum")
    private Integer monthMeetingPersonSum;

    @Column(name = "month_meeting_person_count")
    private Integer monthMeetingPersonCount;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getMonthCovered() {
        return monthCovered;
    }

    public void setMonthCovered(Integer monthCovered) {
        this.monthCovered = monthCovered;
    }

    public Integer getMonthTelNum() {
        return monthTelNum;
    }

    public void setMonthTelNum(Integer monthTelNum) {
        this.monthTelNum = monthTelNum;
    }

    public Integer getMonthTelTime() {
        return monthTelTime;
    }

    public void setMonthTelTime(Integer monthTelTime) {
        this.monthTelTime = monthTelTime;
    }

    public Integer getMonthTelPerson() {
        return monthTelPerson;
    }

    public void setMonthTelPerson(Integer monthTelPerson) {
        this.monthTelPerson = monthTelPerson;
    }

    public Integer getMonthTelAvgTime() {
        return monthTelAvgTime;
    }

    public void setMonthTelAvgTime(Integer monthTelAvgTime) {
        this.monthTelAvgTime = monthTelAvgTime;
    }

    public Integer getMonthImNum() {
        return monthImNum;
    }

    public void setMonthImNum(Integer monthImNum) {
        this.monthImNum = monthImNum;
    }

    public Integer getMonthWechatNum() {
        return monthWechatNum;
    }

    public void setMonthWechatNum(Integer monthWechatNum) {
        this.monthWechatNum = monthWechatNum;
    }

    public Integer getMonthEmailNum() {
        return monthEmailNum;
    }

    public void setMonthEmailNum(Integer monthEmailNum) {
        this.monthEmailNum = monthEmailNum;
    }

    public Integer getMonthMeetingNum() {
        return monthMeetingNum;
    }

    public void setMonthMeetingNum(Integer monthMeetingNum) {
        this.monthMeetingNum = monthMeetingNum;
    }

    public Integer getMonthMeetingTime() {
        return monthMeetingTime;
    }

    public void setMonthMeetingTime(Integer monthMeetingTime) {
        this.monthMeetingTime = monthMeetingTime;
    }


    public Integer getMonthMeetingPersonSum() {
        return monthMeetingPersonSum;
    }

    public void setMonthMeetingPersonSum(Integer monthMeetingPersonSum) {
        this.monthMeetingPersonSum = monthMeetingPersonSum;
    }

    public Integer getMonthMeetingPersonCount() {
        return monthMeetingPersonCount;
    }

    public void setMonthMeetingPersonCount(Integer monthMeetingPersonCount) {
        this.monthMeetingPersonCount = monthMeetingPersonCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    public Integer getMonthImCount() {
        return monthImCount;
    }

    public void setMonthImCount(Integer monthImCount) {
        this.monthImCount = monthImCount;
    }

    public Integer getMonthWechatCount() {
        return monthWechatCount;
    }

    public void setMonthWechatCount(Integer monthWechatCount) {
        this.monthWechatCount = monthWechatCount;
    }

    public Integer getMonthEmailCount() {
        return monthEmailCount;
    }

    public void setMonthEmailCount(Integer monthEmailCount) {
        this.monthEmailCount = monthEmailCount;
    }
}
