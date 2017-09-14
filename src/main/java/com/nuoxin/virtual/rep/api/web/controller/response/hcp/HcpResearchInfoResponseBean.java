package com.nuoxin.virtual.rep.api.web.controller.response.hcp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tiancun on 17/8/3.
 */
@ApiModel(value = "医生学术信息")
public class HcpResearchInfoResponseBean implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所有的论文")
    private List<DocKeywordResponseBean> docKeywordList;

    @ApiModelProperty(value = "所有合作作者")
    private List<HcpCoAuthorResponseBean> hcpCoAuthorList;

    @ApiModelProperty(value = "所有合作期刊")
    private List<HcpCoMagazineResponseBean> hcpCoMagazineList;


    public List<DocKeywordResponseBean> getDocKeywordList() {
        return docKeywordList;
    }

    public void setDocKeywordList(List<DocKeywordResponseBean> docKeywordList) {
        this.docKeywordList = docKeywordList;
    }

    public List<HcpCoAuthorResponseBean> getHcpCoAuthorList() {
        return hcpCoAuthorList;
    }

    public void setHcpCoAuthorList(List<HcpCoAuthorResponseBean> hcpCoAuthorList) {
        this.hcpCoAuthorList = hcpCoAuthorList;
    }

    public List<HcpCoMagazineResponseBean> getHcpCoMagazineList() {
        return hcpCoMagazineList;
    }

    public void setHcpCoMagazineList(List<HcpCoMagazineResponseBean> hcpCoMagazineList) {
        this.hcpCoMagazineList = hcpCoMagazineList;
    }
}
