package com.nuoxin.virtual.rep.api.web.controller.response.hcp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "后台配置的所有关键词")
public class KeywordListResponseBean implements Serializable{
    private static final long serialVersionUID = 6709785132430607318L;

    @ApiModelProperty(value = "竞品关键词")
    private List<KeywordResponseBean> ckeywordList;

    @ApiModelProperty(value = "产品关键词")
    private List<KeywordResponseBean> pkeywordList;


    public List<KeywordResponseBean> getCkeywordList() {
        return ckeywordList;
    }

    public void setCkeywordList(List<KeywordResponseBean> ckeywordList) {
        this.ckeywordList = ckeywordList;
    }

    public List<KeywordResponseBean> getPkeywordList() {
        return pkeywordList;
    }

    public void setPkeywordList(List<KeywordResponseBean> pkeywordList) {
        this.pkeywordList = pkeywordList;
    }
}
