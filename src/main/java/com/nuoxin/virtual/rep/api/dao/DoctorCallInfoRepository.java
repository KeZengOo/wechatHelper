package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.DoctorCallInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * Created by fenggang on 9/11/17.
 */
public interface DoctorCallInfoRepository extends JpaRepository<DoctorCallInfo,Long>,JpaSpecificationExecutor<DoctorCallInfo> {

    @Query("select count(distinct d.id) from DoctorCallInfo d where createTime>=:date")
    Integer findByCreateTimeCount(@Param("date") Date date);

}
