package com.nuoxin.virtual.rep.api.web.controller.response;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * Created by fenggang on 9/19/17.
 */
@ApiModel
public class LoginResponseBean implements Serializable {

    private static final long serialVersionUID = -8729573139433484148L;
    private String name;
    private String email;

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
}
