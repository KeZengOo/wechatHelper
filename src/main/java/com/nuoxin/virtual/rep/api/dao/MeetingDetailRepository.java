package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.Meeting;
import com.nuoxin.virtual.rep.api.entity.MeetingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Create by tiancun on 2017/10/11
 */
public interface MeetingDetailRepository extends JpaRepository<MeetingDetail, Long>,JpaSpecificationExecutor<MeetingDetail> {


   void deleteAllByMeetingId(Long meetingId);

   List<MeetingDetail> findByMeetingIdAndDoctorId(Long meetingId, Long doctorId);

   void deleteAllByMeetingIdAndDoctorId(Long meetingId, Long doctorId);

}
