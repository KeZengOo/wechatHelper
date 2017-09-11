package com.nuoxin.virtual.rep.virtualrepapi.service;

import com.nuoxin.virtual.rep.virtualrepapi.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.virtualrepapi.entity.DrugUser;
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

        return null;
    }
}
