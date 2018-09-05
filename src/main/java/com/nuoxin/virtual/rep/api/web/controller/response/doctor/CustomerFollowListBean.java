package com.nuoxin.virtual.rep.api.web.controller.response.doctor;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客户跟进列表类
 * @author xiekaiyu
 */
@ApiModel
@Data
public class CustomerFollowListBean implements Serializable {

    private static final long serialVersionUID = 9122147743030730987L;
    
    @ApiModelProperty(value = "医生ID")
    private String doctorId;
    @ApiModelProperty(value = "医生姓名")
    private String doctorName;
    @ApiModelProperty(value = "医生性别")
    private Byte gender;
    @ApiModelProperty(value = "医生电话")
    private String doctorMobile;
    
    @ApiModelProperty(value = "医生所在医院名称")
    private String hospitalName;
    @ApiModelProperty(value = "医生所在科室")
    private String department;
    
    @ApiModelProperty(value = "打电话状态")
    private String callStatusName;
    @ApiModelProperty(value = "上次拜访时间")
    private Date visitTime;
    
    @ApiModelProperty(value = "是否添加微信:0.未添加,1.已添加")
    private byte isHasWeChat;
    @ApiModelProperty(value = "距离上次拜访时间间隔,单位:分钟. -1表示未拜访")
    private long lastVisitTimeInterval;
    @ApiModelProperty(value = "下次拜访时间")
    private Date nextVisitTime;
   
}
