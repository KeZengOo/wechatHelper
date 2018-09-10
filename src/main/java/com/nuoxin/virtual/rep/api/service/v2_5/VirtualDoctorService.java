package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.SaveVirtualDoctorRequest;

public interface VirtualDoctorService {

	/**
	 * 保存单个客户医生
	 * @param request
	 * @return 成功返回true 否则返回 false
	 */
	boolean saveVirtualDoctor(SaveVirtualDoctorRequest request);

}
