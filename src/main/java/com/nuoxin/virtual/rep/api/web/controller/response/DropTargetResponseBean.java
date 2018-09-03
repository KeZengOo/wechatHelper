package com.nuoxin.virtual.rep.api.web.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/20
 */
@ApiModel(value = "脱落返回的数据")
public class DropTargetResponseBean implements Serializable{
    private static final long serialVersionUID = 7696203321234417522L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "客户等级")
    private String level;

    @ApiModelProperty(value = "多长时间未联系算是脱落，单位是周")
    private Integer dropPeriod;

    @ApiModelProperty(value = "产品id")
    private Long productId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getDropPeriod() {
        return dropPeriod;
    }

    public void setDropPeriod(Integer dropPeriod) {
        this.dropPeriod = dropPeriod;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
