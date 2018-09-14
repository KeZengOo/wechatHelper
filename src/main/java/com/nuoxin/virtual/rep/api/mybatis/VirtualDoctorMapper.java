package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import com.nuoxin.virtual.rep.api.entity.v2_5.DoctorVirtualParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorDO;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorMendParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorParams;

/**
 * 客户医生 Mapper
 * @author xiekaiyu
 */
public interface VirtualDoctorMapper {
	
	/**
	 * 根据 virtualDoctorId 获取医生信息
	 * @param virtualDoctorId
	 * @return
	 */
	VirtualDoctorDO getVirtualDoctor(Long virtualDoctorId);

	/**
	 * 批量添加客户医生
	 * @param saveRequest
	 * @return 返回影响条数
	 */
	int saveVirtualDoctors(List<VirtualDoctorParams> list);
	
	/**
	 * 批量添加客户医生扩展信息
	 * @param list
	 * @return
	 */
	int saveVirtualDoctorMends(List<VirtualDoctorMendParams> list);

	/**
	 * 批量添加至 doctor_virtual 表
	 * @param list
	 * @return
	 */
	int saveDoctorVirtuals(List<DoctorVirtualParams> list);

}
