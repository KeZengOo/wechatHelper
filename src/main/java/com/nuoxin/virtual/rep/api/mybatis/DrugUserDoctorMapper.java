package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface DrugUserDoctorMapper {
	
	/**
	 * 根据 virtualDrugUserIds 获取医生 ids
	 * @param virtualDrugUserIds
	 * @return 成功返回 List<Long>,否则返回空集合
	 */
	List<Long> getDoctorIdsByVirtualDrugUserIds(@Param(value = "virtualDrugUserIds") List<Long> virtualDrugUserIds);
	
	List<Long> search(@Param(value = "search")String search, 
			                     @Param(value = "virtualDrugUserIds")List<Long> virtualDrugUserIds);
	
	List<Long> screen(@Param(value = "virtualDrugUserIds")List<Long> virtualDrugUserIds,
			                     @Param(value = "productLineIds") List<Integer> productLineIds);
}
