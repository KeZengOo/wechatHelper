package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by fenggang on 9/11/17.
 */
@Entity
@Table(name = "virtual_questionnaire")
public class Questionnaire extends IdEntity {

    private static final long serialVersionUID = 7132023957911272808L;

    @Column(name = "title")
    private String title;
    @Column(name = "create_id")
    private Long createId;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "del_flag")
    private Integer delFlag = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
