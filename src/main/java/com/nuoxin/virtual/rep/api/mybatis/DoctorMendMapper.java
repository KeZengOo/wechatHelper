package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorMendParams;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 保存医生扩展信息
 */
@Component
public interface DoctorMendMapper {

	/**
	 * 保存医生扩展信息
	 * @param virtualDoctorMendParams
	 * @return 返回主键值
	 */
	int saveDoctorMend(VirtualDoctorMendParams virtualDoctorMendParams);

	/**
	 * 修改医生扩展信息
	 * @param virtualDoctorMendParams
	 * @return
	 */
	int updateDoctorMend(VirtualDoctorMendParams virtualDoctorMendParams);

	/**
	 * 查询医生扩展信息
	 * @param virtualDoctorMendParams
	 * @return
	 */
	Long getDoctorMend(VirtualDoctorMendParams virtualDoctorMendParams);


	/**
	 * 更新医生地址
	 * @param doctorId
	 * @param address
	 */
	void updateAddress(@Param(value = "doctorId") Long doctorId,@Param(value = "address") String address);


}
