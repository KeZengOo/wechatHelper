package com.nuoxin.virtual.rep.api.web.controller.response.hcp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by tiancun on 17/8/3.
 */
@ApiModel(value = "主数据医生对话")
public class HcpDialogResponseBean implements Serializable{

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主数据医生id")
    private Long id;

    @ApiModelProperty(value = "对话内容")
    private String content;

    @ApiModelProperty(value = "对话时间")
    private String startTime;

//    @ApiModelProperty(value = "竞品关键词")
//    private String ckeyWord;
//
//    @ApiModelProperty(value = "产品关键词")
//    private String pkeyWord;
//
    @ApiModelProperty(value = "所有的关键词")
    private String allKeyWord;
//
//    @ApiModelProperty(value = "竞品关键词")
//    private List<KeywordResponseBean> ckeyWordList;
//
//    @ApiModelProperty(value = "产品关键词")
//    private List<KeywordResponseBean> pkeyWordList;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    public String getAllKeyWord() {
        return allKeyWord;
    }

    public void setAllKeyWord(String allKeyWord) {
        this.allKeyWord = allKeyWord;
    }
}
