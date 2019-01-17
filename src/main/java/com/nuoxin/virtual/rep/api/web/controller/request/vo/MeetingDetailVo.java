package com.nuoxin.virtual.rep.api.web.controller.request.vo;

import com.nuoxin.virtual.rep.api.common.annotations.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 会议详情导入
 * Create by tiancun on 2017/10/11
 */
@Data
public class MeetingDetailVo {

    @Excel(name = "医生手机号")
    private String telephone;

    @Excel(name = "开始时间")
    private Date attendStartTime;

    @Excel(name = "结束时间")
    private Date attendEndTime;


    @Excel(name = "参会方式（1-网站，2-电话，3微信）")
    private String attendWay;

    @Excel(name = "类型（参会-1，or回看-2）")
    private String attendType;

    @Excel(name = "是否下载（1-下载，2未下载）")
    private String download;


}
