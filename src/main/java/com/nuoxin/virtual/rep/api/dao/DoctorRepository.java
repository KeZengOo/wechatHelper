package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.web.controller.response.vo.Doc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by fenggang on 9/11/17.
 */
public interface DoctorRepository extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {

    Doctor findTopByMobile(String mobile);

    Doctor findFirstById(Long id);

    List<Doctor> findByIdIn(Collection<Long> ids);

    List<Doctor> findByMobileIn(Collection<String> mobiles);

    List<Doctor> findByEmailIn(Collection<String> emails);

    //@Query("select count(distinct d.id) as doctorNum,count(distinct d.hospitalName) as hospitalNum from DrugUserDoctor d where d.doctorVirtual.drugUserIds like :drugUserId ")
    @Query(value = "select count(distinct d.id) as doctorNum,count(distinct d.hospitalName) as hospitalNum from drug_user_doctor dud join drug_user du on du.id=dud.drug_user_id join doctor d on d.id=dud.doctor_id where du.leader_path like :drugUserId",nativeQuery = true)
    Map<String, Long> statDrugUserDoctorNum(@Param("drugUserId") String drugUserId);

    @Modifying
    void deleteByIdIn(Collection<Long> ids);

    @Modifying
    @Query(value = "update doctor d join doctor_virtual v on v.doctor_id=d.id set d.virtual_doctor_id=v.id where d.virtual_doctor_id is null", nativeQuery = true)
    void updateVirtualDoctorId();

}
