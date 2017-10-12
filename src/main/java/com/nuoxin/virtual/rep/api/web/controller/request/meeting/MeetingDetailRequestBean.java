package com.nuoxin.virtual.rep.api.web.controller.request.meeting;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/12
 */
@ApiModel(value = "会议详情请求类")
public class MeetingDetailRequestBean implements Serializable{
    private static final long serialVersionUID = 6698650969012185562L;


    private Long drugUserId;

    private Long productId;




}
