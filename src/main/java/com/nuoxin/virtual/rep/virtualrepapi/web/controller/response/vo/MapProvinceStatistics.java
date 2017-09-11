package com.nuoxin.virtual.rep.virtualrepapi.web.controller.response.vo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * Created by tiancun on 17/8/4.
 */
@ApiModel(value = "首页地图有数据的省份")
public class MapProvinceStatistics implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private String province;

    private Integer count;


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
