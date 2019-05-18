package com.nuoxin.virtual.rep.api.entity.v3_0.request;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 问卷详情Request
 * @author wujiang
 * @date 20190517
 */
@Data
@ApiModel("问卷详情Request")
public class WenJuanInfoRequest extends PageRequestBean {
    @ApiModelProperty(value = "项目短ID")
    private String shortId;
}
