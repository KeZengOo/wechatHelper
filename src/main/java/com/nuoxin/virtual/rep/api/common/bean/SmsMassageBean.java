package com.nuoxin.virtual.rep.api.common.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by fenggang on 9/18/17.
 */
public class SmsMassageBean implements Serializable {

    private static final long serialVersionUID = -8966810737758849605L;
    private String templateCode;
    private String topic;
    private String message;
    private Map<String,Object> map;
    private String signName;
    private List<String> mobiles;

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public List<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }
}
