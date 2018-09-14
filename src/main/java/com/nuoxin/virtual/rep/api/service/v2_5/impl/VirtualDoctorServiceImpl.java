package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v2_5.DoctorVirtualParams;
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
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.SaveVirtualDoctorMendRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.SaveVirtualDoctorRequest;

/**
 * 医生业务实现类
 * @author xiekaiyu
 */
@Service
public class VirtualDoctorServiceImpl implements VirtualDoctorService {

	@Resource
	private HospitalMapper hospitalMapper;
	@Resource
	private VirtualDoctorMapper virtualDoctorMapper;
	@Resource
	private DrugUserDoctorMapper drugUserDoctorMapper;
	@Resource
	private DrugUserDoctorQuateMapper drugUserDoctorQuateMapper;

	@Override
	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
	public boolean saveVirtualDoctor(SaveVirtualDoctorRequest request, DrugUser user) {
		int hospitalId = this.getHospiTalId(request);
		if (hospitalId > 0) {
			// 保存医生信息
			long virtualDoctorId = this.saveDoctor(request, hospitalId);
			if (virtualDoctorId > 0) {
				// 保存医生扩展信息
				this.saveVirtualDoctorMend(request, virtualDoctorId);
				this.saveVirtualDoctor(request, virtualDoctorId);
				// 保存医生关联关系
				this.saveDrugUserDoctor(request, virtualDoctorId, user);
				// 保存医生关联关系扩展信息
				this.saveDrugUserDoctorQuate(request, virtualDoctorId, user);

				return true;
			}
		}

		return false;
	}

	@Override
	public VirtualDoctorBasicResponse getVirtualDoctorBasic(Long virtualDoctorId) {
		HospitalProvinceBean hospitalBean = null;
		
		VirtualDoctorDO virtualDoctorDO = virtualDoctorMapper.getVirtualDoctor(virtualDoctorId);
		if (virtualDoctorDO != null) {
			String hospitalName = virtualDoctorDO.getHospitalName();
			hospitalBean = hospitalMapper.getHospital(hospitalName);
		}

		VirtualDoctorBasicResponse virtualDoctorBasic = new VirtualDoctorBasicResponse();
		virtualDoctorBasic.setVirtualDoctor(virtualDoctorDO);
		virtualDoctorBasic.setHospital(hospitalBean);

		return virtualDoctorBasic;
	}

	@Override
	public List<HospitalProvinceBean> getHospitals(String hospitalName) {
		return hospitalMapper.getHospitals(hospitalName);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 获取医院ID,走 getOrInsert路线
	 * @param request
	 */
	private int getHospiTalId(SaveVirtualDoctorRequest request) {
		int hospitalId;

		HospitalProvinceBean hospitalProvince = hospitalMapper.getHospital(request.getHospital());
		if (hospitalProvince != null) {
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
	private long saveDoctor(SaveVirtualDoctorRequest request, int hospitalId) {
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

		List<VirtualDoctorParams> list = new ArrayList<>(1);
		list.add(param);
		virtualDoctorMapper.saveVirtualDoctors(list);

		return param.getId();
	}

	/**
	 * 保存客户医生扩展信息
	 * @param request
	 * @param virtualDoctorId
	 */
	private void saveVirtualDoctorMend(SaveVirtualDoctorRequest request, long virtualDoctorId) {
		VirtualDoctorMendParams param = new VirtualDoctorMendParams();
		param.setVirtualDoctorId(virtualDoctorId);
		param.setAddress(request.getAddress());
		param.setFixedPhone(request.getFixedPhone());
		param.setWechat(request.getWechat());
		param.setSecondaryMobile(request.getSecondaryMobile());
		param.setThirdaryMobile(request.getThirdaryMobile());
		
		List<VirtualDoctorMendParams> list = new ArrayList<>(1);
		list.add(param);
		virtualDoctorMapper.saveVirtualDoctorMends(list);
		
		// TODO 医生潜力 @田存
	}
	
	private void saveVirtualDoctor(SaveVirtualDoctorRequest request, long virtualDoctorId) {
		List<SaveVirtualDoctorMendRequest> mends = request.getMends();
		if(CollectionsUtil.isEmptyList(mends)) {
			return;
		}
		
		int size = mends.size();
		List<DoctorVirtualParams> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			SaveVirtualDoctorMendRequest doctorVirtual = mends.get(i);
			DoctorVirtualParams param = new DoctorVirtualParams();
			param.setVirtualDoctorId(virtualDoctorId);
			param.setHcpLevel(doctorVirtual.getHcpLevel());
			list.add(param);
		}
		
		virtualDoctorMapper.saveDoctorVirtuals(list);
	}

	/**
	 * 写入 drug_user_doctor 关联表
	 * @param request
	 * @param virtualDoctorId
	 * @param user
	 */
	private void saveDrugUserDoctor(SaveVirtualDoctorRequest request, Long virtualDoctorId, DrugUser user) {
		List<SaveVirtualDoctorMendRequest> mends = request.getMends();
		if (CollectionsUtil.isEmptyList(mends)) {
			return;
		}

		int size = mends.size();
		List<DrugUserDoctorParams> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			DrugUserDoctorParams param = this.buildDrugUserDoctorParams(request, virtualDoctorId, user, mends.get(i));
			list.add(param);
		}
		drugUserDoctorMapper.saveDrugUserDoctors(list);
	}

	private DrugUserDoctorParams buildDrugUserDoctorParams(SaveVirtualDoctorRequest request, Long virtualDoctorId, DrugUser user,
			SaveVirtualDoctorMendRequest mend) {
		DrugUserDoctorParams param = new DrugUserDoctorParams();
		param.setDoctorId(virtualDoctorId);
		param.setDoctorName(request.getName());
		param.setDoctorEmail(request.getEmail());

		param.setDrugUserId(user.getId());
		param.setDrugUserName(user.getName());
		param.setDrugUserEmail(user.getEmail());

		param.setProdId(mend.getProductLineId());

		return param;
	}

	/**
	 * 写入 drug_user_doctor_quate 表
	 * @param request
	 * @param virtualDoctorId
	 * @param user
	 */
	private void saveDrugUserDoctorQuate(SaveVirtualDoctorRequest request, Long virtualDoctorId, DrugUser user) {
		List<SaveVirtualDoctorMendRequest> mends = request.getMends();
		if (CollectionsUtil.isEmptyList(mends)) {
			return;
		}
		
		int size = mends.size();
		List<UpdateVirtualDrugUserDoctorRelationship> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			UpdateVirtualDrugUserDoctorRelationship param = this.buildUpdateVirtualDrugUserDoctorRelationship(virtualDoctorId,
					user, mends.get(i));
			list.add(param);
		}
		drugUserDoctorQuateMapper.saveDrugUserDoctorQuates(list);
	}

	private UpdateVirtualDrugUserDoctorRelationship buildUpdateVirtualDrugUserDoctorRelationship(Long virtualDoctorId,
			DrugUser user, SaveVirtualDoctorMendRequest mend) {
		UpdateVirtualDrugUserDoctorRelationship param = new UpdateVirtualDrugUserDoctorRelationship();
		param.setDoctorId(virtualDoctorId);
		param.setVirtualDrugUserId(user.getId());
		param.setProductLineId(mend.getProductLineId());
		param.setIsHasDrug(mend.getIsHasDrug());
		param.setIsRecruit(mend.getIsRecruit());

		return param;
	}

}
