package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by fenggang on 9/11/17.
 */
public interface DoctorRepository extends JpaRepository<Doctor,Long>,JpaSpecificationExecutor<Doctor> {

    Doctor findTopByMobile(String mobile);

}
