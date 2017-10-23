package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.DrugUserDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by fenggang on 9/11/17.
 */
public interface DrugUserDoctorRepository extends JpaRepository<DrugUserDoctor,Long>,JpaSpecificationExecutor<DrugUserDoctor> {

    @Modifying
    void deleteByDoctorIdAndDrugUserId(Long doctorId,Long drugUserId);
    @Modifying
    @Query("delete from DrugUserDoctor d where d.drugUserId=:drugUserId and d.doctorId=:doctorId and d.productId=:productId")
    void deleteByDoctorIdAndDrugUserIdAndProductId(@Param("doctorId") Long doctorId,@Param("drugUserId") Long drugUserId,@Param("productId") Long productId);

    List<DrugUserDoctor> findByDoctorIdAndDrugUserIdAndProductId(Long doctorId,Long drugUserId,Long productId);

    List<DrugUserDoctor> findByDoctorId(Long doctorId);

    List<DrugUserDoctor> findByDoctorIdAndProductId(Long doctorId,Long productId);

    @Modifying
    void deleteByDoctorId(Long doctorId);
}
