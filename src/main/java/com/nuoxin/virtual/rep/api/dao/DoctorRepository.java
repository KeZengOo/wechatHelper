package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorStatResponseBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

/**
 * Created by fenggang on 9/11/17.
 */
public interface DoctorRepository extends JpaRepository<Doctor,Long>,JpaSpecificationExecutor<Doctor> {

    Doctor findTopByMobile(String mobile);

    @Query("select count(distinct d.id) as doctorNum,count(distinct d.hospitalName) as hospitalNum from Doctor d where d.drugUserIds like :drugUserId ")
    Map<String,Integer> statDrugUserDoctorNum(@Param("drugUserId") String drugUserId);

}
