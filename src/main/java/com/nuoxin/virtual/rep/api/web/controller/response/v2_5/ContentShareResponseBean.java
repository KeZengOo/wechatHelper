package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**内容推送记录返回数据
 * @author tiancun
 * @date 2018-09-20
 */
@ApiModel(value = "内容推送记录返回数据")
@Data
public class ContentShareResponseBean implements Serializable {
    private static final long serialVersionUID = -4829395878909000657L;

    @ApiModelProperty(value = "分享ID")
    private Long id;

    @ApiModelProperty(value = "销售代表姓名")
    private String drugUserName;

    @ApiModelProperty(value = "分享时间")
    private String shareTime;

    @ApiModelProperty(value = "分享类型：1微信，2短信，3邮件")
    private Integer shareType;

    @ApiModelProperty(value = "内容ID")
    private Long contentId;

    @ApiModelProperty(value = "分享标题")
    private String title;

    @ApiModelProperty(value = "分享状态：无人应答，服务，成功拜访")
    private String shareStatus;

    @ApiModelProperty(value = "阅读次数")
    private Integer readCount;

    @ApiModelProperty(value = "阅读时长")
    private String readTime;

    @ApiModelProperty(value = "是否转发，1是转发，0是未转发")
    private Integer forward;

    @ApiModelProperty(value = "是否评论有用：1有用，0没用，null为没有评论")
    private Integer useful;

    @ApiModelProperty(value = "评论内容")
    List<ContentCommentResponseBean> commentList = new ArrayList<>();

    @ApiModelProperty(value = "内容问卷")
    private ContentQuestionnaireResponseBean questionnaire;

}
