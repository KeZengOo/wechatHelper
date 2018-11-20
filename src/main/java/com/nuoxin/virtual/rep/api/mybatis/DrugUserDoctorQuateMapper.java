package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import com.nuoxin.virtual.rep.api.entity.v2_5.DrugUserDoctorQuateParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsDrugNumResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsParams;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.ProductInfoResponse;
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
	@Deprecated
	int backupRelationShipInfo(DrugUserDoctorQuateParams params);

	/**
	 *
	 * @param statisticsParams
	 * @return 返回影响条数
	 */
	List<StatisticsDrugNumResponse>  getPotentialDoctorCount(@Param(value = "statisticsParams")StatisticsParams statisticsParams, @Param(value = "hcpPotential") Integer hcpPotential);

	/**
	 * 医生的产品信息，每个医生只取两条
	 * @param doctorId
	 * @param leaderPath
	 * @return
	 */
	List<ProductInfoResponse> getProductInfoList(@Param(value = "doctorId") Long doctorId,@Param(value = "leaderPath") String leaderPath);

	/**
	 * 医生的产品信息，每个医生只取两条
	 * @param doctorId
	 * @param productIdList
	 * @return
	 */
	List<ProductInfoResponse> getProductInfoListByProductIdList(@Param(value = "doctorId") Long doctorId,@Param(value = "productIdList") List<Long> productIdList);


	/**
	 * 查询总数，校验记录是否存在
	 * @param drugUserId
	 * @param doctorId
	 * @param productId
	 * @return
	 */
	Integer getQuateCount(@Param(value = "drugUserId") Long drugUserId,@Param(value = "doctorId") Long doctorId,@Param(value = "productId") Long productId);


	/**
	 * 根据产品ID得到医生招募结果
	 * @param productId
	 * @param doctorId
	 * @return
	 */
	Integer getProductRecruit(@Param(value = "productId") Long productId,@Param(value = "doctorId") Long doctorId);

}

