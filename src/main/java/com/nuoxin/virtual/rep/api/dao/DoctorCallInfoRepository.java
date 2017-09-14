package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.DoctorCallInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Map;

/**
 * Created by fenggang on 9/11/17.
 */
public interface DoctorCallInfoRepository extends JpaRepository<DoctorCallInfo,Long>,JpaSpecificationExecutor<DoctorCallInfo> {

    @Query("select count(distinct d.id) from DoctorCallInfo d where createTime>=:date")
    Integer findByCreateTimeCount(@Param("date") Date date);

    @Query("select count(distinct d.id) as allNum,sum(d.callTime) as callTimes,sum(d.status) as num from DoctorCallInfo d where d.doctor.drugUserIds like :drugUserIds and d.type=:type")
    Map<String,Long> statDrugUserIds(@Param("drugUserIds") String drugUserIds,@Param("type") Integer type);

}
