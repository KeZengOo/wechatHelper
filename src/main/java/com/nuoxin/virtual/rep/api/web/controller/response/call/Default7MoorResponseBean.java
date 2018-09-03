package com.nuoxin.virtual.rep.api.web.controller.response.call;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by fenggang on 7/28/17.
 */
public class Default7MoorResponseBean<T> implements Serializable {

    private static final long serialVersionUID = -4210048244033765940L;

    /**
     * 状态,成功为true,失败返回false
     */
    private boolean success;

    /**
     * 返回数据
     */
    private T data;

    /**
     *
     */
    private String message;

    public Default7MoorResponseBean() {

    }


    public Default7MoorResponseBean(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
