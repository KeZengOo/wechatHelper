package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoxin.virtual.rep.api.entity.v2_5.CallVisitMendBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorCallInfoParams;

/**
 * 电话拜访 Mapper
 * @author xiekaiyu
 */
public interface VirtualDoctorCallInfoMendMapper {
	
	/**
	 * 保存扩展信息
	 * @param params
	 * @return
	 */
	int saveVirtualDoctorCallInfoMend(VirtualDoctorCallInfoParams params);
	
	/**
	 * 根据 callIds 获取扩展信息
	 * @param callIds
	 * @param virtualDoctorId
	 * @return
	 */
	List<CallVisitMendBean> getCallVisitMendList(@Param(value = "callIds") List<Long> callIds);
}

