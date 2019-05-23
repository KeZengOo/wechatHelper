package com.nuoxin.virtual.rep.api.entity.v3_0.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 内容分享列表params
 * @author wujiang
 * @date 20190505
 */
@Data
@ApiModel("内容分享列表params")
public class ContentSharingParams {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("内容标题")
    private String title;
    @ApiModelProperty("内容创建时间")
    private String time;
    @ApiModelProperty("分享渠道1微信，2短信，3邮件，4小程序")
    private Integer shareType;
    @ApiModelProperty("代表ID")
    private Long drugUserId;
    @ApiModelProperty("代表名称")
    private String drugUserName;
    @ApiModelProperty("阅读人数")
    private Integer peopleNumber;
    @ApiModelProperty("总阅读时长-秒")
    private String totalDuration;
    @ApiModelProperty("产品ID")
    private Integer productId;
    @ApiModelProperty("产品名称")
    private String prodName;
    @ApiModelProperty("销售类型，0是没有类型为经理的，1是线上销售，2是线下销售")
    private Integer saleType;
    @ApiModelProperty("代表类型")
    private String roleName;
}
