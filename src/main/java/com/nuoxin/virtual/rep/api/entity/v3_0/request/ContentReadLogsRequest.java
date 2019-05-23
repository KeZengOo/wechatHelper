package com.nuoxin.virtual.rep.api.entity.v3_0.request;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 内容阅读记录Request
 * @author wujiang
 * @date 20190505
 */
@Data
@ApiModel("内容阅读记录Request")
public class ContentReadLogsRequest extends PageRequestBean {
    @ApiModelProperty("内容Id")
    private Long dataId;
    @ApiModelProperty("代表ID")
    private Long drugUserId;
    @ApiModelProperty("分享渠道")
    private Integer shareType;
}
