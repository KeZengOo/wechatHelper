package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.plan;

import lombok.Data;

import java.io.Serializable;

/**
 * 今日分型要拜访的医生
 * @author tiancun
 * @date 2019-01-10
 */
@Data
public class VisitDoctorResponseBean implements Serializable {
    private static final long serialVersionUID = -7868037500443510711L;

    /**
     * 最大的拜访时间
     */
    private String maxVisitTime;

    /**
     * 医生ID
     */
    private Long doctorId;

    /**
     * 日期间隔，用来判断是否已经过期
     */
    private Integer visitIntervalDay;

    /**
     * 查询的
     */
    private Integer days;

    /**
     * 设置的频次
     */
    private Integer frequency;
}
