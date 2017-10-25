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

    //月目标电话个数
    @Column(name = "month_tel_num")
    private Integer monthTelNum;

    //月目标电话总时长(单位秒)
    @Column(name = "month_tel_time")
    private Integer monthTelTime;

    //月目标电话人数
    @Column(name = "month_tel_person")
    private Integer monthTelPerson;

    //月目标电话每次平均时长(单位秒)
    @Column(name = "month_tel_avg_time")
    private Integer monthTelAvgTime;

    //月目标短信数
    @Column(name = "month_im_num")
    private Integer monthImNum;

    //月目标短信人次
    @Column(name = "month_im_count")
    private Integer monthImCount;

    //月目标微信数
    @Column(name = "month_wechat_num")
    private Integer monthWechatNum;

    //月目标微信人次
    @Column(name = "month_wechat_count")
    private Integer monthWechatCount;


    //月目标邮件数
    @Column(name = "month_email_num")
    private Integer monthEmailNum;

    //月目标邮件人次
    @Column(name = "month_email_count")
    private Integer monthEmailCount;

    //月目标会议场次
    @Column(name = "month_meeting_num")
    private Integer monthMeetingNum;

    //月目标会议总时长(单位分钟)
    @Column(name = "month_meeting_time")
    private Integer monthMeetingTime;

    //月目标会议参数人数
    @Column(name = "month_meeting_person_sum")
    private Integer monthMeetingPersonSum;

    //月目标会议人次
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


    public Integer getMonthTelNum() {
        return monthTelNum==null?0:monthTelNum;
    }

    public void setMonthTelNum(Integer monthTelNum) {
        this.monthTelNum = monthTelNum;
    }

    public Integer getMonthTelTime() {
        return monthTelTime==null?0:monthTelTime;
    }

    public void setMonthTelTime(Integer monthTelTime) {
        this.monthTelTime = monthTelTime;
    }

    public Integer getMonthTelPerson() {
        return monthTelPerson==null?0:monthTelPerson;
    }

    public void setMonthTelPerson(Integer monthTelPerson) {
        this.monthTelPerson = monthTelPerson;
    }

    public Integer getMonthTelAvgTime() {
        return monthTelAvgTime==null?0:monthTelAvgTime;
    }

    public void setMonthTelAvgTime(Integer monthTelAvgTime) {
        this.monthTelAvgTime = monthTelAvgTime;
    }

    public Integer getMonthImNum() {
        return monthImNum==null?0:monthImNum;
    }

    public void setMonthImNum(Integer monthImNum) {
        this.monthImNum = monthImNum;
    }

    public Integer getMonthWechatNum() {
        return monthWechatNum==null?0:monthWechatNum;
    }

    public void setMonthWechatNum(Integer monthWechatNum) {
        this.monthWechatNum = monthWechatNum;
    }

    public Integer getMonthEmailNum() {
        return monthEmailNum==null?0:monthEmailNum;
    }

    public void setMonthEmailNum(Integer monthEmailNum) {
        this.monthEmailNum = monthEmailNum;
    }

    public Integer getMonthMeetingNum() {
        return monthMeetingNum==null?0:monthMeetingNum;
    }

    public void setMonthMeetingNum(Integer monthMeetingNum) {
        this.monthMeetingNum = monthMeetingNum;
    }

    public Integer getMonthMeetingTime() {
        return monthMeetingTime==null?0:monthMeetingTime;
    }

    public void setMonthMeetingTime(Integer monthMeetingTime) {
        this.monthMeetingTime = monthMeetingTime;
    }


    public Integer getMonthMeetingPersonSum() {
        return monthMeetingPersonSum==null?0:monthMeetingPersonSum;
    }

    public void setMonthMeetingPersonSum(Integer monthMeetingPersonSum) {
        this.monthMeetingPersonSum = monthMeetingPersonSum;
    }

    public Integer getMonthMeetingPersonCount() {
        return monthMeetingPersonCount==null?0:monthMeetingPersonCount;
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
        return monthImCount==null?0:monthImCount;
    }

    public void setMonthImCount(Integer monthImCount) {
        this.monthImCount = monthImCount;
    }

    public Integer getMonthWechatCount() {
        return monthWechatCount==null?0:monthWechatCount;
    }

    public void setMonthWechatCount(Integer monthWechatCount) {
        this.monthWechatCount = monthWechatCount;
    }

    public Integer getMonthEmailCount() {
        return monthEmailCount==null?0:monthEmailCount;
    }

    public void setMonthEmailCount(Integer monthEmailCount) {
        this.monthEmailCount = monthEmailCount;
    }
}
