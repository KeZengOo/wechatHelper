package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.ContactPlan;
import com.nuoxin.virtual.rep.api.entity.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Create by tiancun on 2017/10/16
 */
public interface ContactPlanRepository extends JpaRepository<ContactPlan, Long>,JpaSpecificationExecutor<ContactPlan> {

    @Query("select c from ContactPlan  c where c.drugUserId=:drugUserId and c.dateTime>=:start and c.dateTime<=:end")
    List<ContactPlan> getDrugUserDate(@Param("drugUserId") Long drugUserId, @Param("start") Date start,@Param("end") Date end);
}
