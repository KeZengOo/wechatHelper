package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.ContactPlan;
import com.nuoxin.virtual.rep.api.entity.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Create by tiancun on 2017/10/16
 */
public interface ContactPlanRepository extends JpaRepository<ContactPlan, Long>,JpaSpecificationExecutor<ContactPlan> {
}
