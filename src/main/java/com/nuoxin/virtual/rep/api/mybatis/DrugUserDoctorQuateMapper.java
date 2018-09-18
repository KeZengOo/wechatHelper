package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import com.nuoxin.virtual.rep.api.entity.v2_5.DrugUserDoctorQuateParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsDrugNumResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsParams;
import org.apache.ibatis.annotations.Param;

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
	int replaceRelationShipInfo(DrugUserDoctorQuateParams params);
	
	/**
	 * 指保存至 drug_user_doctor_quate 表
	 * @param list
	 * @return
	 */
	int saveDrugUserDoctorQuates (List<DrugUserDoctorQuateParams> list);
	
	/**
	 * 备虚拟代表关联的医生关系信息:是否有药,是否是目标客户,是否有AE
	 * @param params
	 * @return 返回影响条数
	 */
	int backupRelationShipInfo(DrugUserDoctorQuateParams params);

	/**
	 *
	 * @param statisticsParams
	 * @return 返回影响条数
	 */
	List<StatisticsDrugNumResponse>  getPotentialDoctorCount(@Param(value = "statisticsParams")StatisticsParams statisticsParams, @Param(value = "hcpPotential") Integer hcpPotential);
}

