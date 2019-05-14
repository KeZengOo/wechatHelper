package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信消息概况
 * @author tiancun
 * @date 2019-05-14
 */
@Data
@ApiModel(value = "微信消息概况")
public class WechatMessageSummaryResponse {

    @ApiModelProperty(value = "代表ID")
    private Long drugUserId;

    @ApiModelProperty(value = "代表姓名")
    private String drugUserName;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "拜访方式")
    private String saleType;

    @ApiModelProperty(value = "1是和医生聊天，2是群聊")
    private Integer messageType;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "如果是群聊，群聊ID")
    private String chatRoomId;

    @ApiModelProperty(value = "医生姓名,如果是群聊的为群名称")
    private String doctorName;

    @ApiModelProperty(value = "医院ID")
    private Long hospitalId;

    @ApiModelProperty(value = "医院名称")
    private String hospitalName;

    @ApiModelProperty(value = "医生科室")
    private String depart;

    @ApiModelProperty(value = "招募状态")
    private String recruitStatus;

    @ApiModelProperty(value = "覆盖状态")
    private String coverStatus;

    @ApiModelProperty(value = "总对话数")
    private Integer totalDialogCount;

    @ApiModelProperty(value = "代表对话数量")
    private Integer drugUserDialogCount;

    @ApiModelProperty(value = "医生对话数量")
    private Integer doctorDialogCount;

    @ApiModelProperty(value = "上一次微信导入时间")
    private String lastSyncTime;


}
