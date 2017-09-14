package com.nuoxin.virtual.rep.api.web.controller.response.hcp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tiancun on 17/8/3.
 */
@ApiModel(value = "医生论文")
public class HcpDocResponseBean implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "论文名称")
    private String thesisName;

    @ApiModelProperty(value = "论文期刊")
    private String thesisNo;

    @ApiModelProperty(value = "论文引用次数")
    private Integer thesisReferenceNum;

    @ApiModelProperty(value = "关键词")
    private List<String> keys;

    @ApiModelProperty(value = "合作作者")
    private String authors;


    public String getThesisName() {
        return thesisName;
    }

    public void setThesisName(String thesisName) {
        this.thesisName = thesisName;
    }

    public String getThesisNo() {
        return thesisNo;
    }

    public void setThesisNo(String thesisNo) {
        this.thesisNo = thesisNo;
    }

    public Integer getThesisReferenceNum() {
        return thesisReferenceNum;
    }

    public void setThesisReferenceNum(Integer thesisReferenceNum) {
        this.thesisReferenceNum = thesisReferenceNum;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }
}
