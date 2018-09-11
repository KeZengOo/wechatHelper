package com.nuoxin.virtual.rep.api.web.controller.request;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by fenggang on 9/11/17.
 */
@ApiModel
@Data
public class LoginRequestBean implements Serializable {

    private static final long serialVersionUID = -1250277657540472099L;

    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "密码")
    private String password;

}
