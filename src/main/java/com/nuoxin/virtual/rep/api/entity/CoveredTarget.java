package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Create by tiancun on 2017/10/19
 */
@Entity
@Table(name = "virtual_covered_target")
public class CoveredTarget extends IdEntity{

    //客户等级
    @Column(name = "level", length = 10)
    private String level;

    //月目标覆盖量
    @Column(name = "month_covered")
    private Integer monthCovered;

    //产品id
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "create_time")
    private Date createTime;


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getMonthCovered() {
        return monthCovered==null?0:monthCovered;
    }

    public void setMonthCovered(Integer monthCovered) {
        this.monthCovered = monthCovered;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
