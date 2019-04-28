package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 会议请求参数
 * @author tiancun
 * @date 2019-04-28
 */
@Data
@ApiModel(value = "会议请求参数")
public class MeetingRequest implements Serializable {
    private static final long serialVersionUID = -7874589756335926792L;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "代表ID列表")
    private List<Long> drugUserIdList;


}
