package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.entity.v2_5.HospitalProvinceBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorParams;
import com.nuoxin.virtual.rep.api.mybatis.VirtualDoctorMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualDoctorService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.SaveVirtualDoctorRequest;

@Service
public class VirtualDoctorServiceImpl implements VirtualDoctorService{

	@Resource
	private VirtualDoctorMapper virtualDoctorMapper;

	@Override
	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
	public boolean saveVirtualDoctor(SaveVirtualDoctorRequest request) {
		int hospitalId = 0;
		
		HospitalProvinceBean hospitalProvince = virtualDoctorMapper.getHospital(request.getName());
		if(hospitalProvince != null) {
			request.setProvince(hospitalProvince.getProvince());
			request.setCity(hospitalProvince.getCity());
			hospitalId = hospitalProvince.getId();
		} else {
			hospitalProvince = new HospitalProvinceBean();
			hospitalProvince.setCity(request.getCity());
			hospitalProvince.setProvince(request.getProvince());
			hospitalProvince.setLevel(request.getHciLevel());
			hospitalProvince.setName(request.getName());
			virtualDoctorMapper.saveHospital(hospitalProvince);
			hospitalId = hospitalProvince.getId();
		}
		
		if(this.doSaveVirtualDoctor(request, hospitalId) >0) {
			return true;
		}
		
		return false;
	}
	
	private int doSaveVirtualDoctor(SaveVirtualDoctorRequest request, int hospitalId) {
		VirtualDoctorParams param = new VirtualDoctorParams();
		param.setName(request.getName());
		param.setGender(request.getGender());
		param.setMobile(request.getMobile());
		param.setEmail(request.getEmail());
		param.setDepart(request.getDepart());
		param.setTitle(request.getTitle());
		
		param.setProvince(request.getProvince());
		param.setCity(request.getCity());
		param.setHospital(request.getHospital());
		param.setHospitalId(hospitalId);

		List<VirtualDoctorParams> params = new ArrayList<>(1);
		params.add(param);

		return virtualDoctorMapper.saveVirtualDoctors(params);
	}
	
}
