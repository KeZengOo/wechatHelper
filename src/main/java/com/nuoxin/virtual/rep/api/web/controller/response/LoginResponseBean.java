package com.nuoxin.virtual.rep.api.web.controller.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    @ApiModelProperty(value = "角色 101-普通虚拟代表，102-虚拟代表管理员, 103 项目管理员，104 招募，105 电话覆盖, 106 微信覆盖")
    private List<Long> roleIdList = new ArrayList<>();

    @ApiModelProperty(value = "角色名称给前端展示用")
    private String roleName;

    @ApiModelProperty(value = "虚拟代表ID")
    private Long virtualDrugUserId;
    @ApiModelProperty(value = "销售类型，0是没有类型为经理的，1是线上销售，2是线下销售")
    private Integer saleType;

    @ApiModelProperty(value = "线上线下")
    private String saleTypeName;

    private DrugUserCallDetaiBean callBean;

}
