package com.nuoxin.virtual.rep.api.common.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 7/28/17.
 */
@ApiModel
public class DefaultResponseBean<T> implements Serializable {

    private static final long serialVersionUID = -4210048244033765940L;

    @ApiModelProperty(value="状态")
    private Integer code = 200;
    @ApiModelProperty(value="返回数据")
    private T data;
    @ApiModelProperty(value="描述(开发测试人员看的)")
    private String description;
    @ApiModelProperty(value="返回消息（用于弹框提示）")
    private String message;

    public static DefaultResponseBean<?> clone(String message,Integer status,String description){
        return new DefaultResponseBean<>(message, status, description);
    }

    public DefaultResponseBean(String message, Integer status, String description) {
        super();
        this.code = status;
        this.message=message;
        this.description = description;
    }

    public DefaultResponseBean(){
        super();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
