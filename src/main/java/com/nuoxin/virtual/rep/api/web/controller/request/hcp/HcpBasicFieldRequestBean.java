package com.nuoxin.virtual.rep.api.web.controller.request.hcp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author tiancun
 */
@ApiModel(value = "医生基本信息修改每个字段的请求参数")
public class HcpBasicFieldRequestBean implements Serializable{
    private static final long serialVersionUID = 4655248582939056676L;

    @ApiModelProperty(value = "每个字段的id")
    private Long id;

    @ApiModelProperty(value = "字段的名称")
    private String key;

    @ApiModelProperty(value = "每个字段的值")
    private String value;

    @ApiModelProperty(value = "正确错误的使用的字段")
    private String correct;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }
}
