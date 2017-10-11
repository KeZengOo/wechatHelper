package com.nuoxin.virtual.rep.api.web.controller.request;

import java.io.Serializable;

/**
 * Created by fenggang on 9/28/17.
 */
public class CallbackListRequestBean implements Serializable{

    private static final long serialVersionUID = 5366289091636217527L;
    private String callid;
    private String device;
    private String called;
    private String caller;
    private String duration;
    private String recordUrl;
    private Integer stime;
    private Integer antime;
    private Integer etime;
    private Integer cause;
    private String dir;
    private Integer agentid;
    private Integer type;
    private String callflag;
    private Integer mediano;
    private Integer count;

    public String getCallid() {
        return callid;
    }

    public void setCallid(String callid) {
        this.callid = callid;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getCalled() {
        return called;
    }

    public void setCalled(String called) {
        this.called = called;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }

    public Integer getStime() {
        return stime;
    }

    public void setStime(Integer stime) {
        this.stime = stime;
    }

    public Integer getAntime() {
        return antime;
    }

    public void setAntime(Integer antime) {
        this.antime = antime;
    }

    public Integer getEtime() {
        return etime;
    }

    public void setEtime(Integer etime) {
        this.etime = etime;
    }

    public Integer getCause() {
        return cause;
    }

    public void setCause(Integer cause) {
        this.cause = cause;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public Integer getAgentid() {
        return agentid;
    }

    public void setAgentid(Integer agentid) {
        this.agentid = agentid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCallflag() {
        return callflag;
    }

    public void setCallflag(String callflag) {
        this.callflag = callflag;
    }

    public Integer getMediano() {
        return mediano;
    }

    public void setMediano(Integer mediano) {
        this.mediano = mediano;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
