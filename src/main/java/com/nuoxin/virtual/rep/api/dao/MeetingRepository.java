package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.Meeting;
import com.nuoxin.virtual.rep.api.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Create by tiancun on 2017/10/11
 */
public interface MeetingRepository extends JpaRepository<Meeting, Long>,JpaSpecificationExecutor<Meeting> {

    public void deleteById(Long id);

}
