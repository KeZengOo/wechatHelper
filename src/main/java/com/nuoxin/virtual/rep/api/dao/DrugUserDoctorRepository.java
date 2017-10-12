package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.DrugUserDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

/**
 * Created by fenggang on 9/11/17.
 */
public interface DrugUserDoctorRepository extends JpaRepository<DrugUserDoctor,Long>,JpaSpecificationExecutor<DrugUserDoctor> {

    @Modifying
    void deleteByDoctorIdAndDrugUserId(Long doctorId,Long drugUserId);
    @Modifying
    void deleteByDoctorIdAndDrugUserIdAndProductId(Long doctorId,Long drugUserId,Long productId);

    DrugUserDoctor findByDoctorIdAndDrugUserIdAndProductId(Long doctorId,Long drugUserId,Long productId);
}
