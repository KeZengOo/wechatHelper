package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.DrugUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by fenggang on 9/11/17.
 */
public interface DrugUserRepository extends JpaRepository<DrugUser,Long>,JpaSpecificationExecutor<DrugUser> {

    DrugUser findFirstByEmail(String email);

    DrugUser findFirstByMobile(String mobile);

    DrugUser findFirstById(Long id);


    List<DrugUser> findByLeaderPathLike(String leaderPath);
}
