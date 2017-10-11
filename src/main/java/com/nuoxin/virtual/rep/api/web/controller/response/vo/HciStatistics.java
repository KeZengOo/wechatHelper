package com.nuoxin.virtual.rep.api.web.controller.response.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tiancun on 17/8/4.
 */
@ApiModel(value = "医院统计")
public class HciStatistics implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主数据医院id")
    private Long id;

    @ApiModelProperty(value = "主数据医院名称")
    private String name;

    @ApiModelProperty(value = "医院级别")
    private Integer medicalGrade;

    @ApiModelProperty(value = "医院下的医生数量")
    private Integer hcpCount;

    @ApiModelProperty(value = "医院别名")
    private List<HciAlias> hciAliasList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getMedicalGrade() {
        return medicalGrade;
    }

    public void setMedicalGrade(Integer medicalGrade) {
        this.medicalGrade = medicalGrade;
    }

    public List<HciAlias> getHciAliasList() {
        return hciAliasList;
    }

    public void setHciAliasList(List<HciAlias> hciAliasList) {
        this.hciAliasList = hciAliasList;
    }

    public Integer getHcpCount() {
        return hcpCount;
    }

    public void setHcpCount(Integer hcpCount) {
        this.hcpCount = hcpCount;
    }
}
