package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import com.nuoxin.virtual.rep.api.entity.v2_5.DoctorVirtualParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorDO;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorMendParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorMiniResponse;
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
	 * 根据 virtualDoctorId 获取医生简要信息
	 * @param virtualDoctorId
	 * @return
	 */
	VirtualDoctorMiniResponse getVirtualDoctorMini(Long virtualDoctorId);

	/**
	 * 批量添加客户医生
	 * @param list
	 * @return 返回影响条数
	 */
	int saveVirtualDoctors(List<VirtualDoctorParams> list);


	/**
	 * 添加单个客户医生
	 * @param virtualDoctorParams
	 * @return 返回影响条数
	 */
	void saveVirtualDoctor(VirtualDoctorParams virtualDoctorParams);

	
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


	/**
	 * 得到以4开头的最大的手机号
	 * @return
	 */
	String maxTelephone();

}
