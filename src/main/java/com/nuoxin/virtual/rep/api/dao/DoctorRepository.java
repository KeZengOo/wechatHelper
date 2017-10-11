package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by fenggang on 9/11/17.
 */
public interface DoctorRepository extends JpaRepository<Doctor,Long>,JpaSpecificationExecutor<Doctor> {

    Doctor findTopByMobile(String mobile);

    Doctor findFirstById(Long id);

    List<Doctor> findByIdIn(Collection<Long> ids);

    List<Doctor> findByMobileIn(Collection<String> mobiles);



    @Query("select count(distinct d.id) as doctorNum,count(distinct d.hospitalName) as hospitalNum from Doctor d where d.drugUserIds like :drugUserId ")
    Map<String,Long> statDrugUserDoctorNum(@Param("drugUserId") String drugUserId);

}
