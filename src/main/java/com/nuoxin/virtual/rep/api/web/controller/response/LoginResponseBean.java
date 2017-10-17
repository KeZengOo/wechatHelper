package com.nuoxin.virtual.rep.api.web.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 9/19/17.
 */
@ApiModel
public class LoginResponseBean implements Serializable {

    private static final long serialVersionUID = -8729573139433484148L;

    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "角色")
    private Long roleId;

    private DrugUserCallDetaiBean callBean;

    public DrugUserCallDetaiBean getCallBean() {
        return callBean;
    }

    public void setCallBean(DrugUserCallDetaiBean callBean) {
        this.callBean = callBean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
