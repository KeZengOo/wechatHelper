package com.nuoxin.virtual.rep.api.entity.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 问卷网-答卷详情列表
 * @author wujiang
 * @date 20190427
 */
@ApiModel("问卷网-答卷详情列表")
@Data
public class WenJuanAnswerSheet {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "答题状态")
    private String rspdStatus;
    @ApiModelProperty(value = "微信用户地址(nan为空)")
    private String weixinAddr;
    @ApiModelProperty(value = "微信用户性别(nan为空)")
    private String weixinSex;
    @ApiModelProperty(value = "微信用户昵称(nan为空)")
    private String weixinNickname;
    @ApiModelProperty(value = "分数 (项目需是测评)")
    private String score;
    @ApiModelProperty(value = "答题内容")
    private String answer;
    @ApiModelProperty(value = "题目类型")
    private String typeDesc;
    @ApiModelProperty(value = "题目标题")
    private String title;
    @ApiModelProperty(value = "答卷序号")
    private Integer seq;
    @ApiModelProperty(value = "来源")
    private String source;
    @ApiModelProperty(value = "答题时长")
    private String timeUsed;
    @ApiModelProperty(value = "答题ip")
    private String ip;
    @ApiModelProperty(value = "结束时间")
    private String finish;
    @ApiModelProperty(value = "开始时间")
    private String start;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "项目短id")
    private String shortId;
    @ApiModelProperty(value = "用户编号")
    private String user;
}
