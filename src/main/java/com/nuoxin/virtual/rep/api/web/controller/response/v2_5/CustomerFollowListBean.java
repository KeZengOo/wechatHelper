package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private Long doctorId;
    @ApiModelProperty(value = "医生姓名")
    private String doctorName;
    @ApiModelProperty(value = "医生性别")
    private Byte gender;
    @ApiModelProperty(value = "邮箱")
    private String email;
    
    @JsonIgnore
    private String doctorMobile;
    @JsonIgnore
    private String secondaryDoctorMobile;
    @JsonIgnore
    private String thirdaryDoctorMobile;
    
    @ApiModelProperty(value = "手机号 1主,2次,3三")
	private List<String> mobiles = new ArrayList<>(3);
    
    @ApiModelProperty(value = "医生所在医院名称")
    private String hospitalName;
    @ApiModelProperty(value = "医生所在科室")
    private String department;
    
    @ApiModelProperty(value = "上次电话拜访时间")
    private String visitTimeStr;
    @JsonIgnore
    private Date visitTime;
    
    @JsonIgnore
    private Date nextVisitTime;
    @ApiModelProperty(value = "下次拜访时间")
    private String nextVisitTimeStr;
    
    @JsonIgnore
    private String visitResult; 
    @ApiModelProperty(value = "拜访结果")
    private Object visitResultObj;
    
    @JsonIgnore
    private String wechat;
    @ApiModelProperty(value = "是否添加微信:0.未添加,1.已添加")
    private Integer isHasWeChat;
    
    @ApiModelProperty(value = "产品列表,最多只有两条")
	private List<ProductInfoResponse> productInfos = new ArrayList<>(2);
   
    @ApiModelProperty(value = "是否是目标客户 1是,0否,-1/null 未知")
    private Integer isTarget;
    @ApiModelProperty(value = "是否是脱落客户 1是,0否,-1/null 未知")
    private Integer isBreakOff;
}
