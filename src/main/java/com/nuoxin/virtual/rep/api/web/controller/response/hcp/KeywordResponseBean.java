package com.nuoxin.virtual.rep.api.web.controller.response.hcp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "关键词")
public class KeywordResponseBean implements Serializable,Comparable<KeywordResponseBean>{
    private static final long serialVersionUID = -342697146157822893L;

    @ApiModelProperty(value = "关键词")
    private String keyword;

    @ApiModelProperty(value = "关键词数量")
    private Integer count;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public int compareTo(KeywordResponseBean o) {
        if(o.count > this.count){
            return 1;
        }else if(o.count == this.count){
            return 0;
        }else{
            return -1;
        }
    }
}
