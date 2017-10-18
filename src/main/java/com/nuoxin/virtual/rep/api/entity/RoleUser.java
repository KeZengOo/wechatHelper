package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by fenggang on 10/18/17.
 */
@Entity
@Table(name = "role_user")
public class RoleUser extends IdEntity {
    private static final long serialVersionUID = 1286558953253074569L;

    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "create_time")
    private Date createTime;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
