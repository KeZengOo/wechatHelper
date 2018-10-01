package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 电话记录需要重试的请求参数，有的电话记录没有走回调需要重试来获取
 * @author tiancun
 * @date 2018-09-30
 */
@Data
public class RetryCallInfoRequestBean implements Serializable {
    private static final long serialVersionUID = -5582055936123772728L;

    /**
     * 电话记录的唯一标识
     */
    private String sinToken;

    /**
     * 是否重试(1是重试，0是未重试)，这里面都是重试的，为1
     */
    private Integer retry = 1;

    /**
     * 电话号码
     */
    private String mobile;


    /**
     * 电话接通状态
     */
    private String statusName;

    /**
     * 打电话的时长
     */
    private Integer callTime;

    /**
     * 录音地址
     */
    private String callUrl;

    /**
     * 打电话时间
     */
    private String createTime;

    /**
     * 1是呼入，2是呼出
     */
    private Integer type;

}
