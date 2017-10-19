package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.DoctorDynamicFieldValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Create by tiancun on 2017/9/22
 */
public interface DoctorDynamicFieldValueRepository extends JpaRepository<DoctorDynamicFieldValue,Long>,JpaSpecificationExecutor<DoctorDynamicFieldValue> {


    List<DoctorDynamicFieldValue> findByDoctorId(Long doctorId);

    void deleteByDynamicFieldId(Long id);

}
