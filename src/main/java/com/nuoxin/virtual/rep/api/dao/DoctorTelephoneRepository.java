package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.DoctorTelephone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 医生多个手机号
 * @author tiancun
 * @date 2018-04-11
 */
public interface DoctorTelephoneRepository extends JpaRepository<DoctorTelephone, Long>, JpaSpecificationExecutor<DoctorTelephone> {


    void deleteAllByDoctorId(Long doctorId);

}
