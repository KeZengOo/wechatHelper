package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.DoctorVirtual;
import org.springframework.stereotype.Component;

/**
 * 医生等级关系表
 */
@Component
public interface DoctorVirtualMapper {

	/**
	 * 更新医生级别信息
	 * @param doctorVirtual
	 * @return
	 */
	int updateDoctorVirtual(DoctorVirtual doctorVirtual);

	/**
	 * 保存医生级别信息
	 * @param doctorVirtual
	 * @return 返回主键值
	 */
	int saveDoctorVirtual(DoctorVirtual doctorVirtual);

	/**
	 * 查询医生级别信息
	 * @param doctorVirtual
	 * @return
	 */
	Long getDoctorVirtualByDoctorId(DoctorVirtual doctorVirtual);
}
