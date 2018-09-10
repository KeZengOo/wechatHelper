package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoxin.virtual.rep.api.entity.v2_5.HospitalProvinceBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorParams;

/**
 * 客户医生 Mapper
 * @author xiekaiyu
 */
public interface VirtualDoctorMapper {
	
	/**
	 * 根据医院名获取医院信息
	 * @param name
	 * @return
	 */
	HospitalProvinceBean getHospital(String name);
	
	/**
	 * 保存医院信息
	 * @param hospitals
	 * @return 返回主键值
	 */
	int saveHospital(HospitalProvinceBean hospitalProvince);

	/**
	 * 添加客户医生(们)
	 * @param saveRequest
	 * @return 返回影响条数
	 */
	int saveVirtualDoctors(List<VirtualDoctorParams> list);

}
