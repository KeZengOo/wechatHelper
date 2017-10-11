package com.nuoxin.virtual.rep.api.web.controller.response;

import com.nuoxin.virtual.rep.api.common.bean.TemplateMap;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fenggang on 9/25/17.
 */
@ApiModel
public class SmsTemplateResponseBean implements Serializable {

    private static final long serialVersionUID = -4801776363685686629L;
    private Long id;
    private String template;
    private String message;
    private String name;

    private List<TemplateMap> maps;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TemplateMap> getMaps() {
        return maps;
    }

    public void setMaps(List<TemplateMap> maps) {
        this.maps = maps;
    }
}
