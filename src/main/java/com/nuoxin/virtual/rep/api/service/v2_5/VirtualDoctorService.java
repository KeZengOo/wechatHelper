package com.nuoxin.virtual.rep.api.service.v2_5;

import java.util.List;

import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v2_5.HospitalProvinceBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorBasicResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorMiniResponse;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.DoctorSingleAddEchoRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.PrescriptionRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.SaveVirtualDoctorRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.UpdateVirtualDoctorRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DoctorDetailsResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.single.DoctorAddResponseBean;
import org.apache.ibatis.annotations.Param;

public interface VirtualDoctorService {


	/**
	 * 新增单个医生时候，数据回显
	 * @param bean
	 * @return
	 */
	DoctorAddResponseBean getDoctorAddEcho(DoctorSingleAddEchoRequestBean bean);


	/**
	 * 保存单个客户医生信息,有 REQUIRED 事务<br>
	 * 涉及 hospital,doctor,drug_user_doctor,drug_user_doctor_quate 表
	 * @param request
	 * @param user
	 * @return
	 */
	Long saveVirtualDoctor(SaveVirtualDoctorRequest request, DrugUser user);

	/**
	 * 得到指定产品下的线上代表
	 * @param productId
	 * @return
	 */
	List<DrugUserResponseBean> getOnlineDrugUserList(Long productId);

	/**
	 * 得到指定产品下的所有代表，不包括经理、管理员
	 * @param productId
	 * @return
	 */
	List<DrugUserResponseBean> getAllDrugUserList(Long productId);


	void updateVirtualDoctor(UpdateVirtualDoctorRequest request, DrugUser user);

	/**
	 * 修改医生的产品的固定字段(比如：处方信息)
	 * @param bean
	 */
	void updateDoctorProductFixField(PrescriptionRequestBean bean);


	/**
	 * 获取客户医生基本信息
	 * @param virtualDoctorId  医生ID
	 * @return 返回 VirtualDoctorBasicResponse 对象
	 */
	VirtualDoctorBasicResponse getVirtualDoctorBasic(Long virtualDoctorId, String leaderPath);
	
	/**
	 * 获取客户医生简要信息信息
	 * @param virtualDoctorId  医生ID
	 * @return 返回 VirtualDoctorMiniResponse 对象
	 */
	VirtualDoctorMiniResponse getVirtualDoctorMini(Long virtualDoctorId);
	
	/**
	 * 根据医院名模糊匹配
	 * @param drugUserId
	 * @param hospitalName
	 * @return
	 */
	List<HospitalProvinceBean> getHospitals(Long drugUserId, String hospitalName);


	/**
	 * 根据联系方式获取详情
	 * @param telephone
	 * @return
	 */
	List<DoctorDetailsResponseBean> getDoctorListByTelephone(String telephone);
}
