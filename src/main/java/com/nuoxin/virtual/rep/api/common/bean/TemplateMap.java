package com.nuoxin.virtual.rep.api.common.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 9/25/17.
 */
@ApiModel
public class TemplateMap implements Serializable {

    private static final long serialVersionUID = 8092866730638779996L;
    @ApiModelProperty(value = "key")
    private String key;
    @ApiModelProperty(value = "是否需要输入（0-不需要输入，0或者空都需要输入）")
    private Integer input;
    @ApiModelProperty(value = "名称")
    private String name;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getInput() {
        return input;
    }

    public void setInput(Integer input) {
        this.input = input;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
