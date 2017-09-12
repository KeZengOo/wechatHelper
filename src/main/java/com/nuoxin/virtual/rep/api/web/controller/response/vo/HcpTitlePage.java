package com.nuoxin.virtual.rep.api.web.controller.response.vo;

import java.util.List;

/**
 * Created by guanliyuan on 17/8/4.
 */
public class HcpTitlePage {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private Integer total;

    private Integer offset;

    private Integer size;

    private List<HcpTitle> datas;

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

    public List<HcpTitle> getDatas() {
        return datas;
    }

    public void setDatas(List<HcpTitle> datas) {
        this.datas = datas;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
