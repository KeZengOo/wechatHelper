package com.nuoxin.virtual.rep.api.web.controller.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by fenggang on 9/19/17.
 */
@Data
@ApiModel("返回登录信息")
public class LoginResponseBean implements Serializable {

    private static final long serialVersionUID = -8729573139433484148L;

    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "角色（101-普通虚拟代表，102-虚拟代表管理员）")
    private Long roleId;
    @ApiModelProperty(value = "虚拟代表ID")
    private Long virtualDrugUserId;
    @ApiModelProperty(value = "销售类型，0是没有类型为经理的，1是线上销售，2是线下销售")
    private Integer saleType;

    private DrugUserCallDetaiBean callBean;

}
