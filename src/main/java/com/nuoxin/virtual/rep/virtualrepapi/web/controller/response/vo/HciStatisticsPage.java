package com.nuoxin.virtual.rep.virtualrepapi.web.controller.response.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tiancun on 17/8/4.
 */
public class HciStatisticsPage implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private Integer total;

    private Integer offset;

    private Integer size;

    private List<HciStatistics> datas;

    private Integer totalPage;


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<HciStatistics> getDatas() {
        return datas;
    }

    public void setDatas(List<HciStatistics> datas) {
        this.datas = datas;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
