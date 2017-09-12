package com.nuoxin.virtual.rep.api.web.controller.response.vo;

import java.io.Serializable;

/**
 * Created by tiancun on 17/8/4.
 */
public class HcpLevelStatistics implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer count;

    private String title;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
