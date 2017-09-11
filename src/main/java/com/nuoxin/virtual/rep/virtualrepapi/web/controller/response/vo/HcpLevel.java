package com.nuoxin.virtual.rep.virtualrepapi.web.controller.response.vo;

import java.io.Serializable;

public class HcpLevel implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //主数据医生级别(头衔)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
