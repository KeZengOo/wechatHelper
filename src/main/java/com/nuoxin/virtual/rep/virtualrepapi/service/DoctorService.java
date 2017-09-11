package com.nuoxin.virtual.rep.virtualrepapi.service;

import com.nuoxin.virtual.rep.virtualrepapi.dao.DoctorRepository;
import com.nuoxin.virtual.rep.virtualrepapi.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fenggang on 9/11/17.
 */
@Service
@Transactional(readOnly = true)
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor findById(Long id){
        return doctorRepository.findOne(id);
    }
}
