package com.nuoxin.virtual.rep.api.entity.v3_0.request;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 内容分享列表查询Request
 * @author wujiang
 * @date 20190505
 */
@Data
@ApiModel("内容分享列表查询Request")
public class ContentSharingRequest extends PageRequestBean {

    @ApiModelProperty(value = "产品ID")
    private Integer productId;
    @ApiModelProperty("分享渠道1微信，2短信，3邮件")
    private Integer shareType;
    @ApiModelProperty("代表ID")
    private Long[] drugUserId;
    @ApiModelProperty(value = "第一个开始时间")
    private String startTimeBefore;
    @ApiModelProperty(value = "第二个开始时间")
    private String startTimeAfter;
    @ApiModelProperty(value = "文章标题")
    private String title;
}
