package com.nuoxin.virtual.rep.api.entity.v3_0.request;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 内容分享列表查询Request
 * @author wujiang
 * @date 20190505
 */
@Data
@ApiModel("内容分享列表查询Request")
public class WenJuanProjectRequest extends PageRequestBean {

    @ApiModelProperty(value = "产品ID")
    private Long[] productId;
    @ApiModelProperty(value = "第一个开始时间")
    private String startTimeBefore;
    @ApiModelProperty(value = "第二个开始时间")
    private String startTimeAfter;
    @ApiModelProperty(value = "文章标题")
    private String title;
}
