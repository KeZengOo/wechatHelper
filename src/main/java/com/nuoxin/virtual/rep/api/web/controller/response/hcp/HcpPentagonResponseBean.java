package com.nuoxin.virtual.rep.api.web.controller.response.hcp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by tiancun on 17/8/3.
 */
@ApiModel(value = "医生的雷达学术信息")
public class HcpPentagonResponseBean implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发表论文数")
    private String paper;

    @ApiModelProperty(value = "论文引用数")
    private String citation;

    @ApiModelProperty(value = "共同的作者数")
    private String sociability;

    private String h_index;

    private String g_index;


    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }

    public String getCitation() {
        return citation;
    }

    public void setCitation(String citation) {
        this.citation = citation;
    }

    public String getSociability() {
        return sociability;
    }

    public void setSociability(String sociability) {
        this.sociability = sociability;
    }

    public String getH_index() {
        return h_index;
    }

    public void setH_index(String h_index) {
        this.h_index = h_index;
    }

    public String getG_index() {
        return g_index;
    }

    public void setG_index(String g_index) {
        this.g_index = g_index;
    }
}
