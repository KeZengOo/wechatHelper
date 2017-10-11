package com.nuoxin.virtual.rep.api.web.controller.request;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * Created by fenggang on 9/25/17.
 */
@ApiModel
public class UpdatePwdRequestBean implements Serializable {

    private static final long serialVersionUID = 4486648004694245210L;
    private String email;
    private String code;
    private String password;
    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
