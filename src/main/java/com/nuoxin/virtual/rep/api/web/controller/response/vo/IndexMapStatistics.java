package com.nuoxin.virtual.rep.api.web.controller.response.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tiancun on 17/8/4.
 */
public class IndexMapStatistics implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private List<MapProvinceStatistics> mapProvinceStatisticsList;


    public List<MapProvinceStatistics> getMapProvinceStatisticsList() {
        return mapProvinceStatisticsList;
    }

    public void setMapProvinceStatisticsList(List<MapProvinceStatistics> mapProvinceStatisticsList) {
        this.mapProvinceStatisticsList = mapProvinceStatisticsList;
    }
}
