package com.nuoxin.virtual.rep.api.web.controller.response.analysis.ta;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 12/25/17.
 *
 * @author fenggang
 * @date 12/25/17
 */
@ApiModel
public class FollowUpTypeStatBean implements Serializable {

    private static final long serialVersionUID = -4373590621179593987L;
    private String title;
    private Integer num;
    private Integer numAll;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getNumAll() {
        return numAll;
    }

    public void setNumAll(Integer numAll) {
        this.numAll = numAll;
    }
}
