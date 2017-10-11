package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.DoctorDynamicField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Create by tiancun on 2017/9/22
 */
public interface DoctorDynamicFieldRepository extends JpaRepository<DoctorDynamicField,Long>,JpaSpecificationExecutor<DoctorDynamicField> {

    DoctorDynamicField findById(Long id);
}
