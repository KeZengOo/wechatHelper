package com.nuoxin.virtual.rep.virtualrepapi.dao;

import com.nuoxin.virtual.rep.virtualrepapi.entity.Doctor;
import com.nuoxin.virtual.rep.virtualrepapi.entity.DoctorCallInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by fenggang on 9/11/17.
 */
public interface DoctorCallInfoRepository extends JpaRepository<DoctorCallInfo,Long>,JpaSpecificationExecutor<DoctorCallInfo> {

}
