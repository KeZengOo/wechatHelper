package com.nuoxin.virtual.rep.virtualrepapi.dao;

import com.nuoxin.virtual.rep.virtualrepapi.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by fenggang on 9/11/17.
 */
public interface DoctorRepository extends JpaRepository<Doctor,Long>,JpaSpecificationExecutor<Doctor> {

}
