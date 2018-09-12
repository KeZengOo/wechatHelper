package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import com.nuoxin.virtual.rep.api.entity.v2_5.HospitalProvinceBean;

/**
 * 客户医生 Mapper
 * @author xiekaiyu
 */
public interface HospitalMapper {
	
	/**
	 * 根据医院名获取医院信息(精确匹配)
	 * @param name
	 * @return
	 */
	HospitalProvinceBean getHospital(String name);
	
	/**
	 * 根据医院名获取医院信息(模糊匹配)
	 * @param name
	 * @return
	 */
	List<HospitalProvinceBean> getHospitals(String name);
	
	/**
	 * 保存医院信息
	 * @param hospitals
	 * @return 返回主键值
	 */
	int saveHospital(HospitalProvinceBean hospitalProvince);
	
}
