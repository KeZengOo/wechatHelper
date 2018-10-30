package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.mybatis.*;
import com.nuoxin.virtual.rep.api.utils.RegularUtils;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.SaveDoctorTelephoneRequestBean;
import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v2_5.DoctorVirtualParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.DrugUserDoctorOneToOneParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.DrugUserDoctorParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.DrugUserDoctorQuateParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.HospitalProvinceBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorBasicResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorDO;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorMendParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorMiniResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorParams;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
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
	private DoctorMapper doctorMapper;
	@Resource
	private DrugUserDoctorQuateMapper drugUserDoctorQuateMapper;
	@Resource
	private CommonService commonService;

	@Override
	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
	public boolean saveVirtualDoctor(SaveVirtualDoctorRequest request, DrugUser user) {

		checkSaveVirtualDoctorParam(request);

		int hospitalId = this.getHospiTalId(request);
		if (hospitalId > 0) {
			// 保存医生信息
			long virtualDoctorId = this.saveSingleDoctor(request, hospitalId);
			if (virtualDoctorId > 0) {
				this.saveDrugUserDoctorProductRelationShip(request, virtualDoctorId, user);
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
	public VirtualDoctorMiniResponse getVirtualDoctorMini(Long virtualDoctorId) {
		VirtualDoctorMiniResponse miniResponse = virtualDoctorMapper.getVirtualDoctorMini(virtualDoctorId);
		if(miniResponse != null) {
			Date nextVisitTime = miniResponse.getNextVisit();
			if(nextVisitTime != null) {
				String nextVisitTimeContent = commonService.alterNextVisitTimeContent(nextVisitTime);
				miniResponse.setNextVisitTimeContent(nextVisitTimeContent);
				
				SimpleDateFormat fmt=new SimpleDateFormat("yyyy/MM/dd");
				miniResponse.setNextVisitTime(fmt.format(nextVisitTime));
			}
		}
		
		return miniResponse;
	}

	@Override
	public List<HospitalProvinceBean> getHospitals(String hospitalName) {
		return hospitalMapper.getHospitals(hospitalName);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 校验新增客户的参数
	 * @param request
	 */
	private void checkSaveVirtualDoctorParam(SaveVirtualDoctorRequest request) {

//		String mobile = request.getMobile();
//		Integer doctorCount = doctorMapper.doctorCountByMobile(mobile);
//		if (doctorCount != null && doctorCount > 0){
//			throw new BusinessException(ErrorEnum.ERROR, "手机号已经存在!");
//		}


		List<String> telephones = request.getTelephones();
		if (CollectionsUtil.isEmptyList(telephones)){
			throw new BusinessException(ErrorEnum.ERROR, "联系方式不能为空！");
		}

		List<String> collectTelphone = telephones.stream().map(String::trim).distinct().collect(Collectors.toList());
		if (telephones.size() != collectTelphone.size()){
			throw new BusinessException(ErrorEnum.ERROR, "联系方式不能重复");
		}

		for (String telephone:telephones){
			boolean mobileMatcher = RegularUtils.isMatcher(RegularUtils.MATCH_TELEPHONE, telephone);
			if (!mobileMatcher){
				boolean fixPhoneMatch = RegularUtils.isMatcher(RegularUtils.MATCH_FIX_PHONE, telephone);
				if (!fixPhoneMatch){
					throw new BusinessException(ErrorEnum.ERROR, "联系方式:" + telephone + " 输入不合法！");
				}
			}else {
				Integer count = doctorMapper.doctorCountByMobile(telephone);
				if (count !=null && count > 0){
					throw new BusinessException(ErrorEnum.ERROR, "手机号：" + telephone + " 已经存在！");
				}
			}
		}

	}


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

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 保存单个医生信息
	 * @param request    SaveVirtualDoctorRequest 页面请求对象
	 * @param hospitalId 医院ID
	 * @return 返回医生ID
	 */
	private long saveSingleDoctor(SaveVirtualDoctorRequest request, int hospitalId) {
		long doctorId = this.saveDoctor(request, hospitalId);
		this.saveDoctorTelephones(request, doctorId);


		// 保存医生扩展信息
		this.saveVirtualDoctorMend(request, doctorId);



		// 保存虚拟代表指定医生参数信息 方法废弃
		// this.saveVirtualDoctor(request, doctorId);

		return doctorId;
	}

	/**
	 * 保存医生的多个联系方式
	 * @param request
	 * @param doctorId
	 */
	private void saveDoctorTelephones(SaveVirtualDoctorRequest request, long doctorId) {

		List<String> telephones = request.getTelephones();
		List<SaveDoctorTelephoneRequestBean> list = new ArrayList<>();
		for (String telephone:telephones){
			boolean matcher = RegularUtils.isMatcher(RegularUtils.MATCH_TELEPHONE, telephone);
			SaveDoctorTelephoneRequestBean saveDoctorTelephoneRequestBean = new SaveDoctorTelephoneRequestBean();
			if (matcher){
				saveDoctorTelephoneRequestBean.setType(1);
			}else {
				saveDoctorTelephoneRequestBean.setType(2);
			}

			saveDoctorTelephoneRequestBean.setDoctorId(doctorId);
			saveDoctorTelephoneRequestBean.setTelephone(telephone);
			list.add(saveDoctorTelephoneRequestBean);
		}


		if (CollectionsUtil.isNotEmptyList(list)){
			doctorMapper.insertDoctorTelephone(list);
		}
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

		List<String> telephones = request.getTelephones();
		String mobile = getMobile(telephones);

		param.setMobile(mobile);

		param.setEmail(request.getEmail());
		param.setDepart(request.getDepart());
		param.setTitle(request.getTitle());

		param.setProvince(request.getProvince());
		param.setCity(request.getCity());
		param.setHospital(request.getHospital());
		param.setHospitalId(hospitalId);

		virtualDoctorMapper.saveVirtualDoctor(param);

		return param.getId();
	}

	/**
	 * 得到医生要插入的手机号，如果没有就创建一个以4开头的虚拟手机号
	 * @param telephones
	 * @return
	 */
	private String getMobile(List<String> telephones) {

		String mobile = null;
		for (String telephone:telephones){
			if (RegularUtils.isMatcher(RegularUtils.MATCH_TELEPHONE, telephone)){
				mobile = telephone;
				break;
			}
		}

		if (StringUtil.isEmpty(mobile)){
			String telephone = virtualDoctorMapper.maxTelephone();
			if (StringUtil.isEmpty(telephone)){
				mobile = "40000000000";
			}else{
				mobile = Integer.valueOf(telephone) + 1 + "";
			}
		}

		return mobile;
	}

	/**
	 * 保存客户医生扩展信息
	 * @param request
	 * @param virtualDoctorId
	 */
	private void saveVirtualDoctorMend(SaveVirtualDoctorRequest request, long virtualDoctorId) {
		VirtualDoctorMendParams param = new VirtualDoctorMendParams();
		param.setVirtualDoctorId(virtualDoctorId); // 保存关联关系
		param.setAddress(request.getAddress());

		param.setWechat(request.getWechat());

		List<VirtualDoctorMendParams> list = new ArrayList<>(1);
		list.add(param);
		virtualDoctorMapper.saveVirtualDoctorMends(list);
	}

	/**
	 * 方法废弃，去掉医生等级字段
	 * 保存虚拟代表指定医生参数信息
	 * @param request
	 * @param virtualDoctorId
	 */
	@Deprecated
	private void saveVirtualDoctor(SaveVirtualDoctorRequest request, long virtualDoctorId) {
		List<SaveVirtualDoctorMendRequest> mends = request.getMends();
		if (CollectionsUtil.isEmptyList(mends)) {
			return;
		}

		int size = mends.size();
		List<DoctorVirtualParams> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			SaveVirtualDoctorMendRequest doctorVirtual = mends.get(i);
			DoctorVirtualParams param = new DoctorVirtualParams();
			param.setVirtualDoctorId(virtualDoctorId); // 设置关联关系
			param.setHcpLevel(doctorVirtual.getHcpLevel()); // 虚拟代表指定的医生等级
			param.setProductId(doctorVirtual.getProductLineId());
			list.add(param);
		}

		virtualDoctorMapper.saveDoctorVirtuals(list);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 保存代表与医生关联关系信息
	 * @param request
	 * @param virtualDoctorId
	 * @param user
	 */
	private void saveDrugUserDoctorProductRelationShip(SaveVirtualDoctorRequest request, long virtualDoctorId, DrugUser user) {
		// 保存代表医生一对一关联关系
		this.saveDrugUserDoctor(request, virtualDoctorId, user);
		// 保存代表医生产品关联关系
		this.saveDrugUserDoctorProduct(request, virtualDoctorId, user);
	}

	/**
	 * 保存虚拟代表医生一对一关系
	 * @param request
	 * @param virtualDoctorId
	 * @param user
	 */
	private void saveDrugUserDoctor(SaveVirtualDoctorRequest request, Long virtualDoctorId, DrugUser user) {
		DrugUserDoctorOneToOneParams param = new DrugUserDoctorOneToOneParams();
		param.setDoctorId(virtualDoctorId);
		param.setDrugUserId(user.getId());
		param.setIsAddWechat(request.getIsAddWechat());

		List<DrugUserDoctorOneToOneParams> list = new ArrayList<>(1);
		list.add(param);
		drugUserDoctorMapper.saveDrugUserDoctorsOneToOne(list);
	}

	/**
	 * 写入 drug_user_doctor 关联表
	 * @param request
	 * @param virtualDoctorId
	 * @param user
	 */
	private void saveDrugUserDoctorProduct(SaveVirtualDoctorRequest request, Long virtualDoctorId, DrugUser user) {
		List<SaveVirtualDoctorMendRequest> mends = request.getMends();
		if (CollectionsUtil.isEmptyList(mends)) {
			return;
		}

		int size = mends.size();

		List<DrugUserDoctorParams> drugUserDoctorParams = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			DrugUserDoctorParams drugUserDoctorParam = this.buildDrugUserDoctorProduct(request, virtualDoctorId, user, mends.get(i));
			drugUserDoctorParams.add(drugUserDoctorParam);
		}
		drugUserDoctorMapper.saveDrugUserDoctors(drugUserDoctorParams);
		
		List<DrugUserDoctorQuateParams> drugUserDoctorQuateParams = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			DrugUserDoctorQuateParams drugUserDoctorQuateParam = this.buildDrugUserDoctorProductQuate(virtualDoctorId,
					user, mends.get(i), request.getIsAddWechat());
			drugUserDoctorQuateParams.add(drugUserDoctorQuateParam);
		}
		drugUserDoctorQuateMapper.saveDrugUserDoctorQuates(drugUserDoctorQuateParams);
	}

	private DrugUserDoctorParams buildDrugUserDoctorProduct(SaveVirtualDoctorRequest request, Long virtualDoctorId, DrugUser user,
			SaveVirtualDoctorMendRequest mend) {
		DrugUserDoctorParams param = new DrugUserDoctorParams();
		param.setDoctorId(virtualDoctorId); // 保存关联关系
		param.setDrugUserId(user.getId()); // 保存关联关系
		param.setProdId(mend.getProductLineId()); // 保存关联关系

		param.setDoctorName(request.getName());
		param.setDoctorEmail(request.getEmail());
		param.setDrugUserName(user.getName());
		param.setDrugUserEmail(user.getEmail());

		return param;
	}

	private DrugUserDoctorQuateParams buildDrugUserDoctorProductQuate(Long virtualDoctorId,
			DrugUser user, SaveVirtualDoctorMendRequest mend, Integer isAddWechat) {
		DrugUserDoctorQuateParams param = new DrugUserDoctorQuateParams();
		param.setDoctorId(virtualDoctorId); // 关联医生ID
		param.setVirtualDrugUserId(user.getId()); // 关联代表ID
		param.setProductLineId(mend.getProductLineId()); // 关联产品ID
		
		param.setIsHasDrug(mend.getIsHasDrug());
		param.setIsRecruit(mend.getIsRecruit());
		param.setHcpPotential(mend.getHcpPotential());
		return param;
	}

}
