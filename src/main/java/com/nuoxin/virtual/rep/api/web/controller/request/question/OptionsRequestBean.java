package com.nuoxin.virtual.rep.api.web.controller.request.question;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 9/11/17.
 */
@ApiModel
public class OptionsRequestBean implements Serializable {

    private static final long serialVersionUID = 7017936123676061740L;

    @ApiModelProperty(value = "选项")
    private String key;
    @ApiModelProperty(value = "答案")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
