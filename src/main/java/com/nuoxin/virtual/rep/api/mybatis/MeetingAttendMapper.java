package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoxin.virtual.rep.api.entity.v2_5.MeetingBean;

/**
 * 参会记录 Mapper
 * @author xiekaiyu
 */
public interface MeetingAttendMapper {
	
	/**
	 * 根据客户医生ID 获取记录条数
	 * @param virtualDoctorId
	 * @return
	 */
	int getMeetingAttendCount(@Param(value = "virtualDoctorId") Long virtualDoctorId,@Param(value = "leaderPath") String leaderPath);
	
	/**
	 * 根据客户医生ID 获取列表
	 * @param virtualDoctorId
	 * @param currentSize
	 * @param pageSize
	 * @return
	 */
	List<MeetingBean> getMeetingAttendList(@Param(value = "virtualDoctorId") Long virtualDoctorId, @Param(value = "leaderPath") String leaderPath,
			@Param(value = "currentSize") Integer currentSize, @Param(value = "pageSize") Integer pageSize);
}

