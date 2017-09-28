package com.nuoxin.virtual.rep.api.web.controller.request;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fenggang on 9/28/17.
 */
public class CallbackRequestBean implements Serializable {

    private static final long serialVersionUID = -4444994767742014644L;
    private Integer count;
    private Integer etime;
    private Integer stime;
    private Integer type;
    private List<CallbackListRequestBean> recordList;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getEtime() {
        return etime;
    }

    public void setEtime(Integer etime) {
        this.etime = etime;
    }

    public Integer getStime() {
        return stime;
    }

    public void setStime(Integer stime) {
        this.stime = stime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<CallbackListRequestBean> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<CallbackListRequestBean> recordList) {
        this.recordList = recordList;
    }
}
