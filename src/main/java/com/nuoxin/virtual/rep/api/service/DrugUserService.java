package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorStatResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fenggang on 9/11/17.
 */
@Service
@Transactional(readOnly = true)
public class DrugUserService {

    @Autowired
    private DrugUserRepository drugUserRepository;

    public DrugUser findByEmail(String email){
        return drugUserRepository.findFirstByEmail(email);
    }

}
