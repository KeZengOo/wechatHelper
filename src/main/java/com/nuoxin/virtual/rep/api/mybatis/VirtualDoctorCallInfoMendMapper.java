package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoxin.virtual.rep.api.entity.v2_5.CallVisitBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorCallInfoParams;

/**
 * 电话拜访 Mapper
 * @author xiekaiyu
 */
public interface VirtualDoctorCallInfoMendMapper {
	
	int saveVirtualDoctorCallInfoMend(VirtualDoctorCallInfoParams params);
	
	int getCallVisitCount(@Param(value = "virtualDrugUserId") Long virtualDrugUserId,
			@Param(value = "virtualDoctorId") Long virtualDoctorId);
	
	List<CallVisitBean> getCallVisitList(@Param(value = "virtualDrugUserId") Long virtualDrugUserId,
			@Param(value = "virtualDoctorId") Long virtualDoctorId, @Param(value = "currentSize") Integer currentSize,
			@Param(value = "pageSize") Integer pageSize);
}

