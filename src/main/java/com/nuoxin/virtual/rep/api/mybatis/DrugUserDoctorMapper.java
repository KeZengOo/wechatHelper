package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.statistics.DailyStatisticsRequestBean;
import org.apache.ibatis.annotations.Param;

import com.nuoxin.virtual.rep.api.entity.v2_5.DrugUserDoctorOneToOneParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.DrugUserDoctorParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.ProductBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsResponse;

/**
 * drug_user_doctor Mapper 类
 * @author xiekaiyu
 */
public interface DrugUserDoctorMapper {

	/**
	 * 指保保存至 drug_user_doctor 表
	 * @param list
	 * @return
	 */
	int saveDrugUserDoctors(List<DrugUserDoctorParams> list);

	int saveDrugUserDoctor(DrugUserDoctorParams params);

	/**
	 * 更新代表医生产品的关系可用状态
	 * @param drugUserId
	 * @param doctorId
	 * @param prodId
	 */
	void updateDrugUserDoctorAvailable(@Param(value = "drugUserId") Long drugUserId,@Param(value = "doctorId") Long doctorId,@Param(value = "prodId") Long prodId);
	
	/**
	 * 保存代表医生一对一关联关系
	 */
	int saveDrugUserDoctorsOneToOne (List<DrugUserDoctorOneToOneParams> list);

	/**
	 * 删除代表医生一对一关联关系
	 */
	void deleteDrugUserDoctorsOneToOne (@Param(value = "drugUserId") Long drugUserId,@Param(value = "doctorId") Long doctorId);

	/**
	 * 根据参数查询销售代表列表
	 * @param statisticsParams
	 * @return
	 */
	List<StatisticsResponse> selectDrugUserDoctors(StatisticsParams statisticsParams);
	/**
	 * 根据参数查询销售代表列表总数
	 * @param statisticsParams
	 * @return
	 */
	Integer selectDrugUserDoctorsCount(StatisticsParams statisticsParams);
	
	List<ProductBean> getProducts(@Param(value = "virtualDrugUserId")Long virtualDrugUserId,
			                                          @Param(value = "virtualDoctorId")Long virtualDoctorId);

	List<StatisticsResponse> getDrugUserIdByProductId(@Param(value = "productId")Integer productId,
			                                          @Param(value = "leaderPath")String leaderPath);

	/**
	 * 添加微信的医生数量
	 * @param bean
	 * @return
	 */
	Integer addWechatDoctor(DailyStatisticsRequestBean bean);


	/**
	 * 得到重复数据ID，用来删除
	 * @param available
	 * @return
	 */
	List<Long> getAvailableDeleteIdList(@Param(value = "available") Integer available);

	/**
	 * 得到要重复删除的
	 * @return
	 */
	List<Long> getRepeatDeleteIdList();

	/**
	 * 根据ID删除
	 * @param list
	 */
	void deleteByIdList(List<Long> list);

	/**
	 * 查询总数，判断是否存在
	 * @param drugUserId
	 * @param doctorId
	 * @param productId
	 * @return
	 */
	Integer getCountByDrugUserDoctorProduct(@Param(value = "drugUserId") Long drugUserId,
											@Param(value = "doctorId") Long doctorId,@Param(value = "productId") Long productId);
}

