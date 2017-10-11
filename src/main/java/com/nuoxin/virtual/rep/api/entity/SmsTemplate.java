package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by fenggang on 9/18/17.
 */
@Entity
@Table(name = "virtual_sms_template")
public class SmsTemplate extends IdEntity {

    private static final long serialVersionUID = 1694566452240188510L;
    private String template;
    private String message;
    private String topic;
    private String sigName;
    private Long drugId;
    private Date createTime;
    private String name;

    private String maps;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSigName() {
        return sigName;
    }

    public void setSigName(String sinName) {
        this.sigName = sinName;
    }

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaps() {
        return maps;
    }

    public void setMaps(String maps) {
        this.maps = maps;
    }
}
