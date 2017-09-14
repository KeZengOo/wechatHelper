package com.nuoxin.virtual.rep.api.web.controller.response.hcp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by tiancun on 17/8/3.
 */
@ApiModel(value = "论文关键词")
public class DocKeywordResponseBean implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关键词数量")
    private Integer count;

    @ApiModelProperty(value = "关键词")
    private String keyword;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}

