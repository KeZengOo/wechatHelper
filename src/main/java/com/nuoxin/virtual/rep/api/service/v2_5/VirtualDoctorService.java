package com.nuoxin.virtual.rep.api.service.v2_5;

import java.util.List;

import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v2_5.HospitalProvinceBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorBasicResponse;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.SaveVirtualDoctorRequest;

public interface VirtualDoctorService {

	/**
	 * 保存单个客户医生信息,有 REQUIRED 事务<br>
	 * 涉及 hospital,doctor,drug_user_doctor,drug_user_doctor_quate 表
	 * @param request
	 * @return 成功返回true 否则返回 false
	 */
	boolean saveVirtualDoctor(SaveVirtualDoctorRequest request, DrugUser user);
	
	/**
	 * 获取客户医生基本信息
	 * @param virtualDoctorId  医生ID
	 * @return 返回 VirtualDoctorBasicResponse 对象
	 */
	VirtualDoctorBasicResponse getVirtualDoctorBasic(Long virtualDoctorId);
	
	/**
	 * 根据医院名模糊匹配
	 * @param hospitalName
	 * @return
	 */
	List<HospitalProvinceBean> getHospitals(String hospitalName);

}
