package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.DoctorCallInfo;
import com.nuoxin.virtual.rep.api.entity.DoctorCallInfoDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fenggang on 9/11/17.
 */
public interface DoctorCallInfoDetailsRepository extends JpaRepository<DoctorCallInfoDetails,Long>,JpaSpecificationExecutor<DoctorCallInfoDetails> {

    /**
     * 根据通话标识查询通话状态记录
     * @param callId
     * @return
     */
    @Query("select d from DoctorCallInfoDetails d where d.callId=:callId and d.statusName<>'after' order by createTime desc")
    List<DoctorCallInfoDetails> findByCallIdOrderOrderByCreateTime(@Param("callId") Long callId);
}
