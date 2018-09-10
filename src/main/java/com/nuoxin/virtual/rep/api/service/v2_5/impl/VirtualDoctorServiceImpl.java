package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v2_5.DrugUserDoctorParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.HospitalProvinceBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.UpdateVirtualDrugUserDoctorRelationship;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorBasicResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorDO;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorMendParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorParams;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserDoctorMapper;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserDoctorQuateMapper;
import com.nuoxin.virtual.rep.api.mybatis.HospitalMapper;
import com.nuoxin.virtual.rep.api.mybatis.VirtualDoctorMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualDoctorService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.SaveVirtualDoctorRequest;

@Service
public class VirtualDoctorServiceImpl implements VirtualDoctorService{
	
	@Resource
	private HospitalMapper hospitalMapper;
	@Resource
	private VirtualDoctorMapper virtualDoctorMapper;
	@Resource
	private DrugUserDoctorMapper drugUserDoctorMapper;
	@Resource
	private DrugUserDoctorQuateMapper drugUserDoctorQuateMapper;
	
	@Override
	public VirtualDoctorBasicResponse getVirtualDoctorBasic(Long virtualDoctorId) {
		VirtualDoctorBasicResponse virtualDoctorBasic = new VirtualDoctorBasicResponse();
		VirtualDoctorDO virtualDoctorDO = virtualDoctorMapper.getVirtualDoctor(virtualDoctorId);
		
		HospitalProvinceBean hospitalBean = null;
		if (virtualDoctorDO != null) {
			String hospitalName = virtualDoctorDO.getHospitalName();
			hospitalBean = hospitalMapper.getHospital(hospitalName);
		}
		
		virtualDoctorBasic.setVirtualDoctor(virtualDoctorDO);
		virtualDoctorBasic.setHospital(hospitalBean);
		
		return virtualDoctorBasic;
	}

	@Override
	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
	public boolean saveVirtualDoctor(SaveVirtualDoctorRequest request, DrugUser user) {
		int hospitalId = this.getHospiTalId(request);
		if (hospitalId > 0) {
			long virtualDoctorId = this.saveVirtualDoctor(request, hospitalId);
			if (virtualDoctorId > 0) {
				this.saveVirtualDoctorMend(request, virtualDoctorId);
				this.saveDrugUserDoctor(request, virtualDoctorId, user);
				this.saveDrugUserDoctorQuate(request, virtualDoctorId, user);
				
				return true;
			}
		}
		
		return false;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 获取医院ID,走 getOrInsert路线
	 * @param request
	 */
	private int getHospiTalId (SaveVirtualDoctorRequest request) {
		int hospitalId;
		
		HospitalProvinceBean hospitalProvince = hospitalMapper.getHospital(request.getName());
		if(hospitalProvince != null) {
			request.setProvince(hospitalProvince.getProvince());
			request.setCity(hospitalProvince.getCity());
			hospitalId = hospitalProvince.getId();
		} else {
			hospitalProvince = new HospitalProvinceBean();
			hospitalProvince.setCity(request.getCity());
			hospitalProvince.setProvince(request.getProvince());
			hospitalProvince.setLevel(request.getHciLevel());
			hospitalProvince.setName(request.getHospital());
			hospitalMapper.saveHospital(hospitalProvince);
			hospitalId = hospitalProvince.getId();
		}
		
		return hospitalId;
	}
	
	/**
	 * 保存单个客户医生信息,返回主键盘值
	 * @param request
	 * @param hospitalId
	 * @return 成功返回主键值
	 */
	private long saveVirtualDoctor(SaveVirtualDoctorRequest request, int hospitalId) {
		List<VirtualDoctorParams> params = new ArrayList<>(1);

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

		params.add(param);
		virtualDoctorMapper.saveVirtualDoctors(params);
		
		return params.get(0).getId();
	}
	
	private void saveVirtualDoctorMend(SaveVirtualDoctorRequest request, long virtualDoctorId) {
		List<VirtualDoctorMendParams> list = new ArrayList<>(1);
		
		VirtualDoctorMendParams param = new VirtualDoctorMendParams();
		param.setVirtualDoctorId(virtualDoctorId);
		param.setAddress(request.getAddress());
		param.setFixedPhone(request.getFixedPhone());
		param.setWechat(request.getWechat());
		
		list.add(param);
		virtualDoctorMapper.saveVirtualDoctorMends(list);
	}
	
	/**
	 * 写入 drug_user_doctor 表
	 * @param request
	 * @param virtualDoctorId
	 * @param user
	 */
	private void saveDrugUserDoctor(SaveVirtualDoctorRequest request, Long virtualDoctorId, DrugUser user) {
		List<DrugUserDoctorParams> list = new ArrayList<>(1);
		
		DrugUserDoctorParams param = new DrugUserDoctorParams();
		param.setDoctorId(virtualDoctorId);
		param.setDoctorName(request.getName());
		param.setDoctorEmail(request.getEmail());
		
		param.setDrugUserId(user.getId());
		param.setDrugUserName(user.getName());
		param.setDrugUserEmail(user.getEmail());
		param.setProdId(request.getProductLineId());
		
		list.add(param);
		drugUserDoctorMapper.saveDrugUserDoctors(list);
	}
	
	/**
	 * 写入 drug_user_doctor_quate 表
	 * @param request
	 * @param virtualDoctorId
	 * @param user
	 */
	private void saveDrugUserDoctorQuate(SaveVirtualDoctorRequest request, Long virtualDoctorId, DrugUser user) {
		List<UpdateVirtualDrugUserDoctorRelationship> list = new ArrayList<>(1);
		
		UpdateVirtualDrugUserDoctorRelationship param = new UpdateVirtualDrugUserDoctorRelationship();
		param.setDoctorId(virtualDoctorId);
		param.setVirtualDrugUserId(user.getId());
		param.setProductLineId(request.getProductLineId());
		param.setIsHasDrug(request.getIsHasDrug());
		param.setIsRecruit(request.getIsRecruit());
		
		list.add(param);
		drugUserDoctorQuateMapper.saveDrugUserDoctorQuates(list);
	}
	
}
