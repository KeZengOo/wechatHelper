package com.nuoxin.virtual.rep.virtualrepapi.dao;

import com.nuoxin.virtual.rep.virtualrepapi.entity.Doctor;
import com.nuoxin.virtual.rep.virtualrepapi.entity.DrugUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by fenggang on 9/11/17.
 */
public interface DrugUserRepository extends JpaRepository<DrugUser,Long>,JpaSpecificationExecutor<DrugUser> {

    DrugUser findFirstByEmail(String email);

}
