package com.nuoxin.backend.entity;

import com.nuoxin.backend.common.entity.IdEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by fenggang on 8/4/17.
 */
@Entity
@Table(name = "product_line")
public class ProductLine implements Serializable{

    private static final long serialVersionUID = 6866487226449249336L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private Long id;
    @Column(name = "e_id")
    private Long drugId;
    @Column(name = "prod_name")
    private String name;
    @Column(name = "prod_desc")
    private String desc;
    @Column(name = "remarks")
    private String remark;
    @Column(name = "p_key_word")
    private String pkeyWord;
    @Column(name = "c_key_word")
    private String ckeyWord;
    @Column(name = "wechat_share_icon")
    private String shareIocn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPkeyWord() {
        return pkeyWord;
    }

    public void setPkeyWord(String pkeyWord) {
        this.pkeyWord = pkeyWord;
    }

    public String getCkeyWord() {
        return ckeyWord;
    }

    public void setCkeyWord(String ckeyWord) {
        this.ckeyWord = ckeyWord;
    }

    public String getShareIocn() {
        return shareIocn;
    }

    public void setShareIocn(String shareIocn) {
        this.shareIocn = shareIocn;
    }
}
