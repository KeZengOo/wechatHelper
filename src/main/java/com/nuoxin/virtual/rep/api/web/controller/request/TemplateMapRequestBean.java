package com.nuoxin.virtual.rep.api.web.controller.request;

import com.nuoxin.virtual.rep.api.common.bean.TemplateMap;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by fenggang on 9/25/17.
 */
@ApiModel
public class TemplateMapRequestBean extends TemplateMap {
    private static final long serialVersionUID = 851206832718976921L;

    @ApiModelProperty(value = "值")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
