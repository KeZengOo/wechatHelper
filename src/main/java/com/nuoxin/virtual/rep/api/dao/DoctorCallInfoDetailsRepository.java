package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.DoctorCallInfo;
import com.nuoxin.virtual.rep.api.entity.DoctorCallInfoDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Map;

/**
 * Created by fenggang on 9/11/17.
 */
public interface DoctorCallInfoDetailsRepository extends JpaRepository<DoctorCallInfoDetails,Long>,JpaSpecificationExecutor<DoctorCallInfoDetails> {


}
