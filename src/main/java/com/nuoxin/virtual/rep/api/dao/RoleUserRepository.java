package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 角色
 *
 * Created by fenggang on 9/18/17.
 */
public interface RoleUserRepository extends JpaRepository<RoleUser, Long>, JpaSpecificationExecutor<RoleUser> {

    List<RoleUser> findByUserId(Long userId);
}
