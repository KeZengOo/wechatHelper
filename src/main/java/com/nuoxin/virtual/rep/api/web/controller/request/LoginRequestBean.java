package com.nuoxin.virtual.rep.api.web.controller.request;

import java.io.Serializable;

/**
 * Created by fenggang on 9/11/17.
 */
public class LoginRequestBean implements Serializable {

    private static final long serialVersionUID = -1250277657540472099L;

    private String password;
    private String userName;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
