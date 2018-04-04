package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.dao.DoctorVirtualRepository;
import com.nuoxin.virtual.rep.api.entity.DoctorVirtual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fenggang on 10/18/17.
 */
@Service
@Transactional(readOnly = true)
public class DoctorVirtualService extends BaseService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DoctorVirtualRepository doctorVirtualRepository;

    /**
     * 获取doctor关联virtual
     * @param doctorId
     * @return
     */
    @Cacheable(value = "virtual_rep_api_doctor_virtual", key = "'_findByDoctorId_'+#doctorId")
    public DoctorVirtual findByDoctorId(Long doctorId){
        return doctorVirtualRepository.findByDoctorId(doctorId);
    }

    /**
     * 保存doctor关联virtual
     * @param doctorVirtual
     * @return
     */
    @Transactional(readOnly = false)
    public DoctorVirtual save(DoctorVirtual doctorVirtual){
        return doctorVirtualRepository.saveAndFlush(doctorVirtual);
    }
}
