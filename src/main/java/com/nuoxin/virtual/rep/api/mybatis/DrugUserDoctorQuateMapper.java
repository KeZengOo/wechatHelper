package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.UpdateVirtualDrugUserDoctorRelationship;

/**
 * drug_user_doctor Mapper 类
 * @author xiekaiyu
 */
public interface DrugUserDoctorQuateMapper {

	/**
	 * 变更虚拟代表关联的医生关系信息:是否有药,是否是目标客户,是否有AE
	 * @param params
	 * @return 返回影响条数
	 */
	int replaceRelationShipInfo(UpdateVirtualDrugUserDoctorRelationship params);
	
	/**
	 * 备虚拟代表关联的医生关系信息:是否有药,是否是目标客户,是否有AE
	 * @param params
	 * @return 返回影响条数
	 */
	int backRelationShipInfo(UpdateVirtualDrugUserDoctorRelationship params);
}

