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

    @Query("select count(distinct d.id) from DoctorCallInfo d where d.createTime>=:date")
    Integer findByCreateTimeCount(@Param("date") Date date);

    @Query("select count(distinct d.id) from DoctorCallInfo d where d.createTime>=:date and d.doctor.id=:doctorId")
    Integer findByCreateTimeCount(@Param("date") Date date,@Param("doctorId") Long doctorId);

    @Query("select count(distinct d.id) as allNum,sum(d.callTime) as callTimes,0 as num from DoctorCallInfo d where d.doctor.drugUserIds like :drugUserIds and d.type=:type")
    Map<String,Long> statDrugUserIds(@Param("drugUserIds") String drugUserIds,@Param("type") Integer type);

    @Query(value = "select count(DISTINCT v1.id) num from virtual_doctor_call_info v1 join virtual_doctor_call_info_details v2 on v1.id=v2.call_id join virtual_doctor d on d.id=v1.virtual_doctor_id" +
            " where v1.type=:type and d.drug_user_ids like :drugUserIds",nativeQuery = true)
    Long statDrugUserIdsCount(@Param("drugUserIds") String drugUserIds,@Param("type") Integer type);

}
