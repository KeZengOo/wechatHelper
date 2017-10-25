package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.DoctorCallInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Map;

/**
 * Created by fenggang on 9/11/17.
 */
public interface DoctorCallInfoRepository extends JpaRepository<DoctorCallInfo,Long>,JpaSpecificationExecutor<DoctorCallInfo> {

    @Query("select count(distinct d.id) from DoctorCallInfo d where d.createTime>=:date and d.delFlag=0")
    Integer findByCreateTimeCount(@Param("date") Date date);

    @Query("select count(distinct d.id) from DoctorCallInfo d where d.createTime>=:date and d.doctor.id=:doctorId and d.delFlag=0")
    Integer findByCreateTimeCount(@Param("date") Date date,@Param("doctorId") Long doctorId);

    @Query("select count(distinct d.id) as allNum,sum(d.callTime) as callTimes,0 as num from DoctorCallInfo d where d.drugUser.leaderPath like :drugUserIds and d.type=:type and d.delFlag=0 and d.doctor.id is not null")
    Map<String,Long> statDrugUserIds(@Param("drugUserIds") String drugUserIds,@Param("type") Integer type);

    @Query(value = "select count(DISTINCT v1.id) num from virtual_doctor_call_info v1 join drug_user_doctor dud on dud.doctor_id=v1.virtual_doctor_id join virtual_doctor_call_info_details v2 on v1.id=v2.call_id join doctor_virtual d on d.doctor_id=v1.virtual_doctor_id join drug_user du on du.id=v1.virtual_drug_user_id " +
            " where v1.type=:type and v1.del_flag=0 and du.leader_path like :drugUserIds and v2.status_name='answer'",nativeQuery = true)
    Long statDrugUserIdsCount(@Param("drugUserIds") String drugUserIds,@Param("type") Integer type);

    DoctorCallInfo findBySinToken(String sinToken);

    @Modifying
    @Query("update DoctorCallInfo d set d.delFlag=:flag where d.doctor.id=:doctorId and d.drugUser.id=:drugUserId and d.productId=:productId")
    void updateDoctorIdAndDrugUserIdAndProductId(@Param("doctorId") Long doctorId,@Param("drugUserId") Long drugUserId,@Param("productId") Long productId,@Param("flag") Integer flag);

}
