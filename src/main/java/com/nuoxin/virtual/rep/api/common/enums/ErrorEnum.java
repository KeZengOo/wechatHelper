package com.nuoxin.virtual.rep.api.common.enums;

/**
 * Created by fenggang on 7/28/17.
 */
public enum ErrorEnum {

    LOGIN_NO(300,"登录失效"),
    LOGIN_ERROR(301,"登录错误"),

    PAGE_ERROR(400,"分页参数错误"),

    ERROR(500,"系统错误"),

    PERMISSION_ERROR(550,"权限不足"),

    SYSTEM_REQUEST_PARAM_ERROR(600,"请求参数错误");

    private int status;
    private String message;

    ErrorEnum(int status, String massage){
        this.status=status;
        this.message=massage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
