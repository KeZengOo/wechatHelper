package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 销售代表给医生打电话的表，拜访表，包含电话拜访，非电话拜访
 * @author wujiang
 * @date 2019-03-27
 */
@ApiModel("销售代表给医生打电话的表")
@Data
public class VirtualDoctorCallInfoBean {

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "电话callId")
    private String sinToken;
    @ApiModelProperty(value = "是否用了重试，0是未用，1是用了")
    private Long isRetry;
    @ApiModelProperty(value = "这条记录的拜访渠道，默认是电话1，2是微信，3是短信，4邮件，5面谈")
    private Integer visitChannel;
    @ApiModelProperty(value = "医生id")
    private Long virtualDoctorId;
    @ApiModelProperty(value = "手机")
    private String mobile;
    @ApiModelProperty(value = "销售id")
    private Long virtualDrugUserId;
    @ApiModelProperty(value = "状态，1成功，0失败，废弃")
    private Integer status;
    @ApiModelProperty(value = "状态名称 answer 接通,cancelmakecall 无响应")
    private String statusName;
    @ApiModelProperty(value = "通话时长，单位秒")
    private Long callTime;
    @ApiModelProperty(value = "录音url")
    private String callUrl;
    @ApiModelProperty(value = "问卷id")
    private Long doctorQuestionnaireId;
    @ApiModelProperty(value = "录音url")
    private String remark;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "类型，1是呼出，2是呼入")
    private Integer type;
    @ApiModelProperty(value = "回掉之后的信息，废弃")
    private String infoJson;
    @ApiModelProperty(value = "产品id")
    private Long productId;
    @ApiModelProperty(value = "跟进类型，关联virtual_follow_up_type")
    private String followUpType;
    @ApiModelProperty(value = "删除状态（0-正常，1-删除）")
    private String delFlag;

}
