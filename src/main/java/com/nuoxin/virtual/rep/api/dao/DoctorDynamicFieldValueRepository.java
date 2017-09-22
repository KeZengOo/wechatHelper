package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.DoctorDynamicFieldValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Create by tiancun on 2017/9/22
 */
public interface DoctorDynamicFieldValueRepository extends JpaRepository<DoctorDynamicFieldValue,Long>,JpaSpecificationExecutor<DoctorDynamicFieldValue> {




}
