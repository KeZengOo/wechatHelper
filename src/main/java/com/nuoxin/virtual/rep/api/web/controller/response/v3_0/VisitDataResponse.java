package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName VisitDataResponse
 * @Description 拜访数据列表返回实体
 * @Author dangjunhui
 * @Date 2019/5/14 14:39
 * @Version 1.0
 */
@ApiModel(value = "拜访数据汇总返回数据")
@Data
public class VisitDataResponse implements Serializable {

    private static final long serialVersionUID = 4042804124371545133L;

    /**
     * 销售代表id
     */
    @ApiModelProperty(value = "销售代表id")
    private Long repId;

    /**
     * 所属产品
     */
    @ApiModelProperty(value = "所属产品")
    private String product;

    /**
     * 代表-姓名
     */
    @ApiModelProperty(value = "代表-姓名")
    private String repName;

    /**
     * 代表-类型
     */
    @ApiModelProperty(value = "类型")
    private String repType;

    /**
     * 代表-拜访方式
     */
    @ApiModelProperty(value = "代表-拜访方式")
    private String repVisitWay;

    /**
     * 招募统计-拜访医生数
     */
    @ApiModelProperty(value = "招募统计-拜访医生数")
    private Integer visitHcpNum;

    /**
     * 招募统计-接触医生数
     */
    @ApiModelProperty(value = "招募统计-接触医生数")
    private Integer contactedHcpNum;

    /**
     * 招募统计-成功医生数
     */
    @ApiModelProperty(value = "招募统计-成功医生数")
    private Integer successHcpNum;

    /**
     * 招募统计-招募医生数
     */
    @ApiModelProperty(value = "招募统计-招募医生数")
    private Integer recruitedHcpNum;

    /**
     * 覆盖统计-覆盖医生数
     */
    @ApiModelProperty(value = "覆盖统计-覆盖医生数")
    private Integer coveringHcpNum;

    /**
     * 覆盖统计-电话>75秒
     */
    @ApiModelProperty(value = "覆盖统计-电话>75秒")
    private Integer more75s;

    /**
     * 覆盖统计-微信回复数
     */
    @ApiModelProperty(value = "覆盖统计-微信回复数")
    private Integer responseNum;

    /**
     * 覆盖统计-会议覆盖
     */
    @ApiModelProperty(value = "覆盖统计-会议覆盖")
    private Integer meetingCoverage;

    /**
     * 覆盖统计-问卷覆盖
     */
    @ApiModelProperty(value = "覆盖统计-问卷覆盖")
    private Integer questionCoverage;

    /**
     * 外呼统计-外呼次数
     */
    @ApiModelProperty(value = "外呼统计-外呼次数")
    private Integer outGoingTimes;

    /**
     * 外呼统计-接通数
     */
    @ApiModelProperty(value = "外呼统计-接通数")
    private Integer connectionNum;

    /**
     * 外呼统计-总时长
     */
    @ApiModelProperty(value = "外呼统计-总时长")
    private String totalDuration;

    /**
     * 微信聊天统计-发送人数
     */
    @ApiModelProperty(value = "微信聊天统计-发送人数")
    private Integer sendingNum;

    /**
     * 微信聊天统计-医生回复数
     */
    @ApiModelProperty(value = "微信聊天统计-医生回复数")
    private Integer hcpReplyNum;

    /**
     * 微信聊天统计-微信添加数
     */
    @ApiModelProperty(value = "微信聊天统计-微信添加数")
    private Integer additionNum;

    /**
     * 内容分享统计-发送次数
     */
    @ApiModelProperty(value = "内容分享统计-发送次数")
    private Integer sendingTimes;

    /**
     * 内容分享统计-阅读人数
     */
    @ApiModelProperty(value = "内容分享统计-阅读人数")
    private Integer readerNum;

    /**
     * 内容分享统计-阅读总时长
     */
    @ApiModelProperty(value = "内容分享统计-阅读总时长")
    private String readTotalLength;

    /**
     * 内容分享统计->50s医生数
     */
    @ApiModelProperty(value = "内容分享统计->50s医生数")
    private Integer more50sHcpNum;

}
