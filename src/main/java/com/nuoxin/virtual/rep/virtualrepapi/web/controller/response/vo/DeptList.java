package com.nuoxin.virtual.rep.virtualrepapi.web.controller.response.vo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "科室列表")
public class DeptList implements Serializable{

    private static final long serialVersionUID = 1L;

    private List<Dept> depts;

    public List<Dept> getDepts() {
        return depts;
    }

    public void setDepts(List<Dept> depts) {
        this.depts = depts;
    }
}
