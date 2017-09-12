package com.nuoxin.virtual.rep.api.web.controller.response.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by guanliyuan on 17/8/4.
 */
public class HcpTitle {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主数据医生id")
    private Long id;

    @ApiModelProperty(value = "主数据医生姓名")
    private String name;

    @ApiModelProperty(value = "主数据医生科室")
    private String dept;

    @ApiModelProperty(value = "主数据医生职称")
    private String title;

    @ApiModelProperty(value = "主数据医生所在医院")
    private String hciName;

    @ApiModelProperty(value = "主数据医院id")
    private Long hciId;

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

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getHciName() {
        return hciName;
    }

    public void setHciName(String hciName) {
        this.hciName = hciName;
    }

    public Long getHciId() {
        return hciId;
    }

    public void setHciId(Long hciId) {
        this.hciId = hciId;
    }
}
