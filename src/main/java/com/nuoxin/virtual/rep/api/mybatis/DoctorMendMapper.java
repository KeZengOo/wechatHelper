package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorMendParams;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.statistics.DailyStatisticsRequestBean;
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
	 * 新增医生微信
	 * @param doctorId
	 * @param wechat
	 */
	void addWechat(@Param(value = "doctorId") Long doctorId,@Param(value = "wechat") String wechat);

	/**
	 * 更新医生微信
	 * @param doctorId
	 * @param wechat
	 */
	void updateWechat(@Param(value = "doctorId") Long doctorId,@Param(value = "wechat") String wechat);

	/**
	 * 更新医生微信
	 * @param doctorId
	 * @param wechat
	 */
	void updateWechatAndTime(@Param(value = "doctorId") Long doctorId,@Param(value = "wechat") String wechat);


	/**
	 * 新增医生地址
	 * @param doctorId
	 * @param address
	 */
	void addAddress(@Param(value = "doctorId") Long doctorId,@Param(value = "address") String address);

	/**
	 * 更新医生地址
	 * @param doctorId
	 * @param address
	 */
	void updateAddress(@Param(value = "doctorId") Long doctorId,@Param(value = "address") String address);

	/**
	 * 根据医生ID查询总数，校验数据是否存在
	 * @param doctorId
	 * @return
	 */
	Integer getCountByDoctorId(@Param(value = "doctorId") Long doctorId);


	/**
	 * 有微信医生人数
	 * @param bean
	 * @return
	 */
	Integer hasWechatDoctor(DailyStatisticsRequestBean bean);


	/**
	 * 根据医生ID查询医生扩展信息
	 * @param doctorId
	 * @return
	 */
	VirtualDoctorMendParams getVirtualDoctorMendParams(@Param(value = "doctorId") Long doctorId);

}
