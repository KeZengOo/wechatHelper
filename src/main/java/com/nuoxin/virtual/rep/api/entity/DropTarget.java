package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Create by tiancun on 2017/10/20
 */
@Entity
@Table(name = "virtual_drop_target")
public class DropTarget extends IdEntity{


    @Column(name = "level")
    private String level;

    @Column(name = "drop_period")
    private Integer dropPriod;

    @Column(name = "productId")
    private Long productId;

    @Column(name = "create_time")
    private Date createTime;


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getDropPriod() {
        return dropPriod;
    }

    public void setDropPriod(Integer dropPriod) {
        this.dropPriod = dropPriod;
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
