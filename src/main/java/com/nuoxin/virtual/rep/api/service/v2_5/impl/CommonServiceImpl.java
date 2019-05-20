package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.dao.DoctorRepository;
import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.dao.ProductLineRepository;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.DrugUserDoctor;
import com.nuoxin.virtual.rep.api.entity.ProductLine;
import com.nuoxin.virtual.rep.api.entity.v2_5.DrugUserDoctorParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.HospitalProvinceBean;
import com.nuoxin.virtual.rep.api.mybatis.*;
import com.nuoxin.virtual.rep.api.service.RoleUserService;
import com.nuoxin.virtual.rep.api.utils.*;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat.WechatMessageRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.vo.DoctorVo;
import com.nuoxin.virtual.rep.api.web.controller.request.vo.DrugUserDoctorTransferVo;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DoctorImportErrorDetailResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DoctorImportErrorResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DoctorTelephoneResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.HospitalResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserResponseBean;
import org.springframework.web.multipart.MultipartFile;

/**
 * 通用接口实现类
 * @author xiekaiyu
 */
@Service
public class CommonServiceImpl implements CommonService {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);
	private static final SimpleDateFormat FMT=new SimpleDateFormat("yyyy-MM-dd");
	
	@Resource
	private DrugUserMapper drugUserMapper;

	@Resource
	private DoctorMapper doctorMapper;

	@Resource
	private HospitalMapper hospitalMapper;

	@Resource
	private ProductLineMapper productLineMapper;

	@Resource
	private DrugUserDoctorMapper drugUserDoctorMapper;

	@Resource
	private ProductHospitalMapper productHospitalMapper;

	@Resource
	private ProductLineRepository productLineRepository;

	@Resource
	private DrugUserRepository drugUserRepository;

	@Resource
	private DoctorRepository doctorRepository;


	@Autowired
	private RoleUserService roleUserService;

	/**
	 * 批量插入的条数限制
	 */
	private final int BATCH_INSERT_SIZE = 1000;

	@Override
	public List<Long> getSubordinateIds(String leaderPath) {
		return drugUserMapper.getSubordinateIdsByLeaderPath(leaderPath);
	}

	@Override
	public List<DrugUserResponseBean> getSubordinates(String leaderPath) {
		return drugUserMapper.getSubordinatesByLeaderPath(leaderPath);
	}
	
	@Override
	public String getLeaderPathById(Long drugUserId) {
		return drugUserMapper.getLeaderPathById(drugUserId);
	}
	
	@Override
	public String alterLastVisitTimeContent(long interval) {
		String str = "";
		long minuteInterval = interval / 60000;
		if (minuteInterval < 60) { // 小于 60 显示分钟
			str = str.concat(String.valueOf(minuteInterval)).concat("分钟");
		} else if (minuteInterval < 1440) { // 小于 1440 显示 小时
			str = str.concat(String.valueOf(minuteInterval / 60)).concat("小时");
		} else { // 超过 1440 显示 天
			str = str.concat(String.valueOf(minuteInterval / 1440)).concat("天");
		}

		return str.concat("前");
	}


	@Override
	public String alterCallTimeContent(Long seconds) {
		if (seconds == null || seconds == 0){
			return "";
		}

		String timeStr = seconds + "秒";
		if (seconds > 60) {
			long second = seconds % 60;
			long min = seconds / 60;
			timeStr = min + "分" + second + "秒";
			if (min > 60) {
				min = (seconds / 60) % 60;
				long hour = (seconds / 60) / 60;
				timeStr = hour + "小时" + min + "分" + second + "秒";
				if (hour > 24) {
					hour = ((seconds / 60) / 60) % 24;
					long day = (((seconds / 60) / 60) / 24);
					timeStr = day + "天" + hour + "小时" + min + "分" + second + "秒";
				}
			}
		}
		return timeStr;
	}

	
	@Override
	public String alterNextVisitTimeContent(Date nextVisitTime) {
		String str = "";
		long interval = this.getDelta(nextVisitTime) / 86400000; // 转换成天
		if (interval > 0) {
			str = str.concat(String.valueOf(interval)).concat("天后");
		} else if (interval < 0) {
			str = str.concat(String.valueOf(interval * -1)).concat("天前");
		} else {
			str = "今天";
		}
		
		return str;
	}



	@Override
	public Map<String,DoctorImportErrorResponse> doctorImportMap(MultipartFile file) {

		Map<String, List<DoctorVo>> doctorListMap = this.getDoctorVoMap(file);
		//新增或者更新，并且返回错误
		Map<String,DoctorImportErrorResponse> errorMap = this.saveOrUpdateDoctorMap(doctorListMap);
		return errorMap;

	}





	@Override
	public DoctorImportErrorResponse doctorImport(MultipartFile file) {
		Map<String, List<DoctorVo>> doctorListMap = this.getDoctorVoMap(file);

		DoctorImportErrorResponse error = this.saveOrUpdateDoctor(doctorListMap);

		return error;
	}




	@Override
	public Map<String, DoctorImportErrorResponse> drugUserDoctorTransferMap(MultipartFile file) {

		Map<String, List<DrugUserDoctorTransferVo>> doctorListMap = this.getDrugUserDoctorTransferVoMap(file);
		//新增或者更新，并且返回错误
		Map<String,DoctorImportErrorResponse> errorMap = this.saveOrUpdateDrugUserDoctorTransferMap(doctorListMap);
		return errorMap;
	}


	@Override
	public DoctorImportErrorResponse drugUserDoctorTransfer(MultipartFile file) {
		Map<String, List<DrugUserDoctorTransferVo>> doctorListMap = this.getDrugUserDoctorTransferVoMap(file);
		//新增或者更新，并且返回错误
		DoctorImportErrorResponse error = this.saveOrUpdateDrugUserDoctorTransfer(doctorListMap);


		return error;
	}




	@Override
	public Long getHospitalId(HospitalProvinceBean hospitalProvinceBean) {
		HospitalProvinceBean hospitalProvince = hospitalMapper.getHospital(hospitalProvinceBean.getName());
		if (hospitalProvince == null) {
			hospitalProvince = new HospitalProvinceBean();
			hospitalProvince.setCity(hospitalProvinceBean.getCity());
			hospitalProvince.setProvince(hospitalProvinceBean.getProvince());
			hospitalProvince.setLevel(hospitalProvinceBean.getLevel());
			hospitalProvince.setName(hospitalProvinceBean.getName());
			hospitalMapper.saveHospital(hospitalProvince);
		}

		return Long.valueOf(hospitalProvince.getId());

	}




	/**
	 * 从文件中得到医生数据
	 * @param file
	 * @return
	 */
	private Map<String, List<DoctorVo>> getDoctorVoMap(MultipartFile file){

		InputStream inputStream;
		ExcelUtils<DoctorVo> excelUtils = new ExcelUtils<>(new DoctorVo());
		Map<String, List<DoctorVo>> doctorListMap;
		try {
			inputStream = file.getInputStream();
			doctorListMap = excelUtils.readFromFileMulSheet(null, inputStream);
		} catch (Exception e) {
			logger.error("导入医生Excel 读取失败！", e.getMessage(),e);
			throw new BusinessException(ErrorEnum.ERROR, "导入医生Excel读取失败！");
		}
		if (CollectionsUtil.isEmptyMap(doctorListMap)){
			throw new BusinessException(ErrorEnum.ERROR, "Excel 内容为空！");
		}

		return doctorListMap;
	}


	/**
	 * 从文件中得到转移代数据
	 * @param file
	 * @return
	 */
	private Map<String, List<DrugUserDoctorTransferVo>> getDrugUserDoctorTransferVoMap(MultipartFile file){
		InputStream inputStream;
		ExcelUtils<DrugUserDoctorTransferVo> excelUtils = new ExcelUtils<>(new DrugUserDoctorTransferVo());
		Map<String, List<DrugUserDoctorTransferVo>> doctorListMap;
		try {
			inputStream = file.getInputStream();
			doctorListMap = excelUtils.readFromFileMulSheet(null, inputStream);
		} catch (Exception e) {
			logger.error("代表转移医生Excel 读取失败！", e.getMessage(),e);
			throw new BusinessException(ErrorEnum.ERROR, "代表转移医生Excel 读取失败！");
		}
		if (CollectionsUtil.isEmptyMap(doctorListMap)){
			throw new BusinessException(ErrorEnum.ERROR, "Excel 内容为空！");
		}

		return doctorListMap;
	}


	/**
	 *
	 * @param doctorListMap
	 * @return
	 */
	private DoctorImportErrorResponse saveOrUpdateDrugUserDoctorTransfer(Map<String, List<DrugUserDoctorTransferVo>> doctorListMap) {

		Map<String, DoctorImportErrorResponse> errorMap = new HashMap<>();
		Map<String, Long> productNameIdMap = getProductNameIdMap();
		DoctorImportErrorResponse doctorImportError = new DoctorImportErrorResponse();
		List<DoctorImportErrorDetailResponse> detailList = new ArrayList<>();

		List<DrugUserDoctorParams> addDrugUserDoctorParamsList = new ArrayList<>();
		List<DrugUserDoctorParams> tempDrugUserDoctorParamsList = new ArrayList<>();
		List<DrugUserDoctorParams> deleteDrugUserDoctorParamsList = new ArrayList<>();

		// 统计重复的数据，不同的行包含相同的手机号并且对应的代表一样就算重复
		Integer repeatNum = 0;
		Integer failNum = 0;
		Integer totalNum = 0;

		for (Map.Entry<String, List<DrugUserDoctorTransferVo>> entry : doctorListMap.entrySet()) {
			String sheetName = entry.getKey();
			List<DrugUserDoctorTransferVo> drugUserDoctorTransferVos = entry.getValue();
			if (CollectionsUtil.isEmptyList(drugUserDoctorTransferVos)) {
				throw new BusinessException(ErrorEnum.ERROR, "sheet名称为:" + sheetName + "的表格内容为空");
			}

			totalNum += drugUserDoctorTransferVos.size();

			for (int i = 0; i < drugUserDoctorTransferVos.size(); i++) {
				DrugUserDoctorTransferVo drugUserDoctorTransferVo = drugUserDoctorTransferVos.get(i);
				String drugUserEmail = drugUserDoctorTransferVo.getDrugUserEmail();
				String productName = drugUserDoctorTransferVo.getProductName();
				String telephone = drugUserDoctorTransferVo.getTelephone();
				String toDrugUserEmail = drugUserDoctorTransferVo.getToDrugUserEmail();
				int row = i + 2;
				if (StringUtil.isEmpty(drugUserEmail)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(sheetName);
					doctorImportErrorDetail.setError("当前代表邮箱为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				if (StringUtil.isEmpty(productName)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(sheetName);
					doctorImportErrorDetail.setError("产品名称为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				if (StringUtil.isEmpty(telephone)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(sheetName);
					doctorImportErrorDetail.setError("医生手机号为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				if (StringUtil.isEmpty(toDrugUserEmail)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(sheetName);
					doctorImportErrorDetail.setError("转给代表的邮箱为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				Long productId = productNameIdMap.get(productName);
				if (productId == null || productId == 0L){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(sheetName);
					doctorImportErrorDetail.setError("产品名称："+ productName +" 对应产品不存在！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				DrugUser drugUser = drugUserRepository.findFirstByEmail(drugUserEmail);
				if (drugUser == null){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(sheetName);
					doctorImportErrorDetail.setError("当前代表邮箱：" + drugUserEmail + " 对应代表不存在！" );
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}


				DrugUser toDrugUser = drugUserRepository.findFirstByEmail(toDrugUserEmail);
				if (toDrugUser == null){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(sheetName);
					doctorImportErrorDetail.setError("转给代表邮箱：" + drugUserEmail + " 对应代表不存在！" );
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}


				Integer productUserCount = productLineMapper.getProductUserCount(drugUserEmail, productId);
				if (productUserCount == 0 || productUserCount == 0){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(sheetName);
					doctorImportErrorDetail.setError("当前代表邮箱：" + drugUserEmail + " 不在产品：" + productName + " 下！" );
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				Integer productToUserCount = productLineMapper.getProductUserCount(toDrugUserEmail, productId);
				if (productToUserCount == 0 || productToUserCount == 0){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(sheetName);
					doctorImportErrorDetail.setError("转移代表邮箱：" + drugUserEmail + " 不在产品：" + productName + " 下！" );
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				if (!RegularUtils.isMatcher(RegularUtils.MATCH_ELEVEN_NUM, telephone)) {
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(sheetName);
					doctorImportErrorDetail.setError("医生手机号：" + telephone + " 输入不合法！" );
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				Doctor doctor = doctorMapper.findTopByMobile(telephone);
				if (doctor == null){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(sheetName);
					doctorImportErrorDetail.setError("医生手机号：" + telephone + " 对应医生不存在！" );
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}


				List<DrugUserDoctorTransferVo> collectList = drugUserDoctorTransferVos.stream().filter(dv -> (drugUserEmail.equals(dv.getDrugUserEmail()) && productName.equals(dv.getProductName()) && telephone.equals(dv.getTelephone()) && toDrugUserEmail.equals(dv.getToDrugUserEmail()))).collect(Collectors.toList());
				if (CollectionsUtil.isNotEmptyList(collectList) && collectList.size() > 1){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(sheetName);
					doctorImportErrorDetail.setError("数据重复！" );
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					repeatNum ++;
					continue;
				}

				DrugUserDoctorParams addDrugUserDoctorParams = new DrugUserDoctorParams();
				addDrugUserDoctorParams.setDoctorId(doctor.getId());
				addDrugUserDoctorParams.setDoctorName(doctor.getName());
				addDrugUserDoctorParams.setDoctorEmail(doctor.getEmail());
				addDrugUserDoctorParams.setDrugUserId(toDrugUser.getId());
				addDrugUserDoctorParams.setDrugUserName(toDrugUser.getName());
				addDrugUserDoctorParams.setDrugUserEmail(toDrugUser.getEmail());
				addDrugUserDoctorParams.setProdId(productId.intValue());
				addDrugUserDoctorParamsList.add(addDrugUserDoctorParams);


				DrugUserDoctorParams tempDrugUserDoctorParams = new DrugUserDoctorParams();
				tempDrugUserDoctorParams.setDoctorId(doctor.getId());
				tempDrugUserDoctorParams.setDoctorName(doctor.getName());
				tempDrugUserDoctorParams.setDoctorEmail(doctor.getEmail());
				tempDrugUserDoctorParams.setDrugUserId(drugUser.getId());
				tempDrugUserDoctorParams.setDrugUserName(drugUser.getName());
				tempDrugUserDoctorParams.setDrugUserEmail(drugUser.getEmail());
				tempDrugUserDoctorParams.setProdId(productId.intValue());
				tempDrugUserDoctorParamsList.add(tempDrugUserDoctorParams);

			}



			// 去重待删除的记录
			Map<Long, Map<Integer, List<DrugUserDoctorParams>>> distinctMap = tempDrugUserDoctorParamsList.stream().collect(Collectors.groupingBy(DrugUserDoctorParams::getDrugUserId, Collectors.groupingBy(DrugUserDoctorParams::getProdId)));
			distinctMap.forEach((k, v)->{
				v.forEach((k1, v1)->{
					v1.forEach(d->{
						Long drugUserId = d.getDrugUserId();
						Integer prodId = d.getProdId();
						DrugUserDoctorParams drugUserDoctorParams = new DrugUserDoctorParams();
						drugUserDoctorParams.setDrugUserId(drugUserId);
						drugUserDoctorParams.setProdId(prodId);
						deleteDrugUserDoctorParamsList.add(drugUserDoctorParams);
					});
				});
			});

		}

		this.saveDrugUserDoctorTransferRecord(deleteDrugUserDoctorParamsList, addDrugUserDoctorParamsList);

		doctorImportError.setDetailList(detailList);
		doctorImportError.setRepeatNum(repeatNum);
		doctorImportError.setFailNum(failNum);
		doctorImportError.setSuccessNum(addDrugUserDoctorParamsList.size());
		doctorImportError.setTotalNum(totalNum);

		return doctorImportError;

	}



	/**
	 * 代表转移医生，并且返回错误，不同的sheet
	 * @param doctorListMap
	 * @return
	 */
	private Map<String, DoctorImportErrorResponse> saveOrUpdateDrugUserDoctorTransferMap(Map<String, List<DrugUserDoctorTransferVo>> doctorListMap) {
		Map<String, DoctorImportErrorResponse> errorMap = new HashMap<>();
		Map<String, Long> productNameIdMap = getProductNameIdMap();
		for (Map.Entry<String, List<DrugUserDoctorTransferVo>> entry : doctorListMap.entrySet()) {
			String sheetName = entry.getKey();
			List<DrugUserDoctorTransferVo> drugUserDoctorTransferVos = entry.getValue();
			if (CollectionsUtil.isEmptyList(drugUserDoctorTransferVos)) {
				throw new BusinessException(ErrorEnum.ERROR, "sheet名称为:" + sheetName + "的表格内容为空");
			}
			DoctorImportErrorResponse doctorImportError = new DoctorImportErrorResponse();
			List<DoctorImportErrorDetailResponse> detailList = new ArrayList<>();
			doctorImportError.setSheetName(sheetName);
			doctorImportError.setTotalNum(drugUserDoctorTransferVos.size());
			List<DrugUserDoctorParams> addDrugUserDoctorParamsList = new ArrayList<>();
			List<DrugUserDoctorParams> tempDrugUserDoctorParamsList = new ArrayList<>();
			List<DrugUserDoctorParams> deleteDrugUserDoctorParamsList = new ArrayList<>();
			// 统计重复的数据，不同的行包含相同的手机号并且对应的代表一样就算重复
			Integer repeatNum = 0;
			Integer successNum = 0;
			Integer failNum = 0;


			for (int i = 0; i < drugUserDoctorTransferVos.size(); i++) {
				DrugUserDoctorTransferVo drugUserDoctorTransferVo = drugUserDoctorTransferVos.get(i);
				String drugUserEmail = drugUserDoctorTransferVo.getDrugUserEmail();
				String productName = drugUserDoctorTransferVo.getProductName();
				String telephone = drugUserDoctorTransferVo.getTelephone();
				String toDrugUserEmail = drugUserDoctorTransferVo.getToDrugUserEmail();
				int row = i + 2;
				if (StringUtil.isEmpty(drugUserEmail)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("当前代表邮箱为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				if (StringUtil.isEmpty(productName)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("产品名称为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				if (StringUtil.isEmpty(telephone)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("医生手机号为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				if (StringUtil.isEmpty(toDrugUserEmail)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("转给代表的邮箱为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				Long productId = productNameIdMap.get(productName);
				if (productId == null || productId == 0L){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("产品名称："+ productName +" 对应产品不存在！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				DrugUser drugUser = drugUserRepository.findFirstByEmail(drugUserEmail);
				if (drugUser == null){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("当前代表邮箱：" + drugUserEmail + " 对应代表不存在！" );
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}


				DrugUser toDrugUser = drugUserRepository.findFirstByEmail(toDrugUserEmail);
				if (toDrugUser == null){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("转给代表邮箱：" + drugUserEmail + " 对应代表不存在！" );
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}


				Integer productUserCount = productLineMapper.getProductUserCount(drugUserEmail, productId);
				if (productUserCount == 0 || productUserCount == 0){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("当前代表邮箱：" + drugUserEmail + " 不在产品：" + productName + " 下！" );
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				Integer productToUserCount = productLineMapper.getProductUserCount(toDrugUserEmail, productId);
				if (productToUserCount == 0 || productToUserCount == 0){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("转移代表邮箱：" + drugUserEmail + " 不在产品：" + productName + " 下！" );
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				if (!RegularUtils.isMatcher(RegularUtils.MATCH_ELEVEN_NUM, telephone)) {
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("医生手机号：" + telephone + " 输入不合法！" );
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				Doctor doctor = doctorMapper.findTopByMobile(telephone);
				if (doctor == null){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("医生手机号：" + telephone + " 对应医生不存在！" );
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}


				List<DrugUserDoctorTransferVo> collectList = drugUserDoctorTransferVos.stream().filter(dv -> (drugUserEmail.equals(dv.getDrugUserEmail()) && productName.equals(dv.getProductName()) && telephone.equals(dv.getTelephone()) && toDrugUserEmail.equals(dv.getToDrugUserEmail()))).collect(Collectors.toList());
				if (CollectionsUtil.isNotEmptyList(collectList) && collectList.size() > 1){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("数据重复！" );
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					repeatNum ++;
					continue;
				}

				DrugUserDoctorParams addDrugUserDoctorParams = new DrugUserDoctorParams();
				addDrugUserDoctorParams.setDoctorId(doctor.getId());
				addDrugUserDoctorParams.setDoctorName(doctor.getName());
				addDrugUserDoctorParams.setDoctorEmail(doctor.getEmail());
				addDrugUserDoctorParams.setDrugUserId(toDrugUser.getId());
				addDrugUserDoctorParams.setDrugUserName(toDrugUser.getName());
				addDrugUserDoctorParams.setDrugUserEmail(toDrugUser.getEmail());
				addDrugUserDoctorParams.setProdId(productId.intValue());
				addDrugUserDoctorParamsList.add(addDrugUserDoctorParams);


				DrugUserDoctorParams tempDrugUserDoctorParams = new DrugUserDoctorParams();
				tempDrugUserDoctorParams.setDoctorId(doctor.getId());
				tempDrugUserDoctorParams.setDoctorName(doctor.getName());
				tempDrugUserDoctorParams.setDoctorEmail(doctor.getEmail());
				tempDrugUserDoctorParams.setDrugUserId(drugUser.getId());
				tempDrugUserDoctorParams.setDrugUserName(drugUser.getName());
				tempDrugUserDoctorParams.setDrugUserEmail(drugUser.getEmail());
				tempDrugUserDoctorParams.setProdId(productId.intValue());
				tempDrugUserDoctorParamsList.add(tempDrugUserDoctorParams);

			}

			doctorImportError.setDetailList(detailList);
			doctorImportError.setRepeatNum(repeatNum);
			doctorImportError.setFailNum(failNum);
			doctorImportError.setSuccessNum(addDrugUserDoctorParamsList.size());
			errorMap.put(sheetName, doctorImportError);

			// 去重待删除的记录
			Map<Long, Map<Integer, List<DrugUserDoctorParams>>> distinctMap = tempDrugUserDoctorParamsList.stream().collect(Collectors.groupingBy(DrugUserDoctorParams::getDrugUserId, Collectors.groupingBy(DrugUserDoctorParams::getProdId)));
			distinctMap.forEach((k, v)->{
				v.forEach((k1, v1)->{
					v1.forEach(d->{
						Long drugUserId = d.getDrugUserId();
						Integer prodId = d.getProdId();
						DrugUserDoctorParams drugUserDoctorParams = new DrugUserDoctorParams();
						drugUserDoctorParams.setDrugUserId(drugUserId);
						drugUserDoctorParams.setProdId(prodId);
						deleteDrugUserDoctorParamsList.add(drugUserDoctorParams);
					});
				});
			});

			this.saveDrugUserDoctorTransferRecord(deleteDrugUserDoctorParamsList, addDrugUserDoctorParamsList);
		}


		return errorMap;

	}

	/**
	 * 删除掉被转移的记录，新增待转移人记录
	 * @param deleteDrugUserDoctorParamsList
	 * @param addDrugUserDoctorParamsList
	 */
	private void saveDrugUserDoctorTransferRecord(List<DrugUserDoctorParams> deleteDrugUserDoctorParamsList, List<DrugUserDoctorParams> addDrugUserDoctorParamsList) {

		if (CollectionsUtil.isNotEmptyList(addDrugUserDoctorParamsList)){
			drugUserDoctorMapper.saveDrugUserDoctors(addDrugUserDoctorParamsList);
		}

		if (CollectionsUtil.isNotEmptyList(deleteDrugUserDoctorParamsList)){
			for (DrugUserDoctorParams drugUserDoctorParams : deleteDrugUserDoctorParamsList) {
				Integer prodId = drugUserDoctorParams.getProdId();
				Long drugUserId = drugUserDoctorParams.getDrugUserId();
				drugUserDoctorMapper.updateDrugUserDoctorAvailable(drugUserId, null, Long.valueOf(prodId));
			}
		}

		// 删除掉重复的
		this.deleteRepeatDrugUserDoctorRecord();

	}



	/**
	 *
	 * @param doctorListMap
	 * @return
	 */
	private DoctorImportErrorResponse saveOrUpdateDoctor(Map<String, List<DoctorVo>> doctorListMap) {

		DoctorImportErrorResponse error = new DoctorImportErrorResponse();
		List<DrugUserDoctorParams> drugUserDoctorParamsList = new ArrayList<>();
		List<DoctorImportErrorDetailResponse> detailList = new ArrayList<>();
		DoctorImportErrorResponse doctorImportError = new DoctorImportErrorResponse();
		// 统计重复的数据，不同的行包含相同的手机号并且对应的代表一样就算重复
		Integer repeatNum = 0;
		Integer successNum = 0;
		Integer failNum = 0;
		Integer totalNum = 0;

		for (Map.Entry<String, List<DoctorVo>> entry: doctorListMap.entrySet()){
			String productName = entry.getKey();
			ProductLine product = productLineRepository.findFirstByName(productName);
			if (product == null){
				throw new BusinessException(ErrorEnum.ERROR, "sheet名称:" + productName + " 对应产品不存在");
			}

			List<DoctorVo> doctorVos = entry.getValue();
			if (CollectionsUtil.isEmptyList(doctorVos)){
				throw new BusinessException(ErrorEnum.ERROR, "sheet名称为:" + productName + "的表格内容为空");
			}

			totalNum += doctorVos.size();

			// 先查询是否有目标的医院
			List<HospitalResponse> productHospitalList = productHospitalMapper.getHospitalListByPoductId(product.getId());
			if (CollectionsUtil.isEmptyList(productHospitalList)){
				throw new BusinessException(ErrorEnum.ERROR, product.getName() + " 还没有添加目标医院！");
			}


			for (int i = 0; i < doctorVos.size(); i++) {
				DoctorVo doctorVo = doctorVos.get(i);
				String province = doctorVo.getProvince();
				String city = doctorVo.getCity();
				String hospitalName = doctorVo.getHospitalName();
				String hospitalLevel = doctorVo.getHospitalLevel();
				String drugHospitalId = doctorVo.getDrugHospitalId();
				String depart = doctorVo.getDepart();
				String address = doctorVo.getAddress();
				String doctorName = doctorVo.getDoctorName();
				String sex = doctorVo.getSex();
				String positions = doctorVo.getPositions();
				String telephone = doctorVo.getTelephone();
				List<String> telephoneList = new ArrayList<>();
				int row = i + 2;
				if (telephone.contains("，")){
					telephone = telephone.replace("，", ",");
				}

				String drugUserEmail = doctorVo.getDrugUserEmail();
				if (StringUtil.isEmpty(province)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(productName);
					doctorImportErrorDetail.setError("医院所在省为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				if (StringUtil.isEmpty(city)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(productName);
					doctorImportErrorDetail.setError("医院所在市为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				if (StringUtil.isEmpty(hospitalName)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(productName);
					doctorImportErrorDetail.setError("医院名称为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}


				// 判断医院是否是目标医院
				if (CollectionsUtil.isNotEmptyList(productHospitalList)){
					List<String> hospitalNameList = productHospitalList.stream().map(HospitalResponse::getHospitalName).distinct().collect(Collectors.toList());
					if (CollectionsUtil.isNotEmptyList(hospitalNameList) && (!hospitalNameList.contains(hospitalName))){
						DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
						doctorImportErrorDetail.setSheetName(productName);
						doctorImportErrorDetail.setError("医院：" + hospitalName + " 不在目标医院中！");
						doctorImportErrorDetail.setRowNum(row);
						detailList.add(doctorImportErrorDetail);
						failNum ++;
						continue;
					}
				}


				if (StringUtil.isNotEmpty(hospitalLevel) && (!"0".equals(hospitalLevel))
						&& (HospitalLevelUtil.getLevelNameByLevelCode(hospitalLevel).equals("未知"))){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(productName);
					doctorImportErrorDetail.setError("医院级别:" + hospitalLevel +"输入不合法！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}


				if (StringUtil.isEmpty(doctorName)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(productName);
					doctorImportErrorDetail.setError("医生姓名为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}


				if (StringUtil.isEmpty(telephone)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(productName);
					doctorImportErrorDetail.setError("医生手机号为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				if (telephone.contains(",")){
					String[] telephoneArray = telephone.split(",");
					if (CollectionsUtil.isEmptyArray(telephoneArray)){
						DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
						doctorImportErrorDetail.setSheetName(productName);
						doctorImportErrorDetail.setError("医生手机号为空！");
						doctorImportErrorDetail.setRowNum(row);
						detailList.add(doctorImportErrorDetail);
						continue;
					}

					for (String t : telephoneArray) {
						if (!RegularUtils.isMatcher(RegularUtils.MATCH_ELEVEN_NUM, t)){
							DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
							doctorImportErrorDetail.setSheetName(productName);
							doctorImportErrorDetail.setError("医生手机号 "+ telephone +" 输入不合法!");
							doctorImportErrorDetail.setRowNum(row);
							detailList.add(doctorImportErrorDetail);
							continue;
						}

						telephoneList.add(t);
					}
				}else {
					if (!RegularUtils.isMatcher(RegularUtils.MATCH_ELEVEN_NUM, telephone)){
						DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
						doctorImportErrorDetail.setSheetName(productName);
						doctorImportErrorDetail.setError("医生手机号 "+ telephone +" 输入不合法!");
						doctorImportErrorDetail.setRowNum(row);
						detailList.add(doctorImportErrorDetail);
						continue;
					}

					telephoneList.add(telephone);
				}

				if (CollectionsUtil.isEmptyList(telephoneList)){
					failNum ++;
					continue;
				}


				// 统计重复的数据
				boolean repeatFlag = false;
				for (String s : telephoneList) {
					List<DoctorVo> filterDoctorVoList = doctorVos.stream().filter(dv -> (StringUtil.isNotEmpty(dv.getTelephone()) && dv.getTelephone().contains(s) && drugUserEmail.equals(dv.getDrugUserEmail()))).collect(Collectors.toList());
					if (CollectionsUtil.isNotEmptyList(filterDoctorVoList) && filterDoctorVoList.size() > 1){
						DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
						doctorImportErrorDetail.setSheetName(productName);
						doctorImportErrorDetail.setError("数据重复，医生手机号包含:" + s + "，代表邮箱为：" + drugUserEmail);
						doctorImportErrorDetail.setRowNum(row);
						detailList.add(doctorImportErrorDetail);
						repeatNum ++;
						repeatFlag = true;
						continue;
					}
				}

				// 有重复的不能添加
				if (repeatFlag){
					failNum ++;
					continue;
				}



				if (StringUtil.isNotEmpty(sex)){
					if (!(sex.equals("男") || sex.equals("女"))){
						DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
						doctorImportErrorDetail.setSheetName(productName);
						doctorImportErrorDetail.setError("医生性别只能输入男或者女！");
						doctorImportErrorDetail.setRowNum(row);
						detailList.add(doctorImportErrorDetail);
						failNum ++;
						continue;
					}

					if (sex.equals("男")){
						sex = "0";
					}

					if (sex.equals("女")){
						sex = "1";
					}
				}else {
					sex = "2";
				}


				if (StringUtil.isEmpty(drugUserEmail)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(productName);
					doctorImportErrorDetail.setError("代表工作邮箱为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				DrugUser drugUser = drugUserRepository.findFirstByEmail(drugUserEmail);
				if (drugUser == null){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(productName);
					doctorImportErrorDetail.setError("代表邮箱为：" + drugUserEmail + " 对应代表不存在！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}
				drugUser.setRoleId(roleUserService.checkVirtualRole(drugUser.getId()));

				List<Long> productIdList = drugUserMapper.getProductIdListByEmail(drugUserEmail);
				if (CollectionsUtil.isNotEmptyList(productIdList) && (!productIdList.contains(product.getId()))){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(productName);
					doctorImportErrorDetail.setError("邮箱：" + drugUserEmail + " 对应代表不在指定产品：" + productName + " 下！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}


				// 判断Excel中同一个医生是否有不同的代表且同一个角色
				boolean roleFlag = false;
				for (String te: telephoneList){
					List<DoctorVo> doctorDrugUserList = doctorVos.stream().filter(d -> (StringUtil.isNotEmpty(d.getTelephone()) && d.getTelephone().contains(te) && (!drugUserEmail.equals(d.getDrugUserEmail())))).collect(Collectors.toList());
					if (CollectionsUtil.isNotEmptyList(doctorDrugUserList)){
						List<String> drugUserEmailList = doctorDrugUserList.stream().map(DoctorVo::getDrugUserEmail).distinct().collect(Collectors.toList());
						// 查询角色数量
						if (CollectionsUtil.isNotEmptyList(drugUserEmailList)){
							List<Long> roleIdList = drugUserMapper.getRoleIdList(drugUserEmailList);
							if (CollectionsUtil.isNotEmptyList(roleIdList) && roleIdList.contains(drugUser.getRoleId())){
								DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
								doctorImportErrorDetail.setSheetName(productName);
								doctorImportErrorDetail.setError("和代表"+ drugUserEmailList.toString() +"关联的角色相同！");
								doctorImportErrorDetail.setRowNum(row);
								detailList.add(doctorImportErrorDetail);
								roleFlag = true;
								continue;
							}
						}

					}
				}

				// 判断同一个医生是否有不同的代表且同一个角色
				if (roleFlag){
					failNum ++;
					continue;
				}



				List<DrugUser> drugUserList = drugUserMapper.getDrugUserList(telephone, product.getId(), drugUser.getRoleId());
				if (CollectionsUtil.isNotEmptyList(drugUserList)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					List<String> drugUserNameList = drugUserList.stream().map(DrugUser::getName).distinct().collect(Collectors.toList());
					doctorImportErrorDetail.setSheetName(productName);
					doctorImportErrorDetail.setError("手机号为：" + telephone + " 医生已经关联同一角色代表:" + drugUserNameList);
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				Doctor doctor = new Doctor();
				List<Doctor> doctors = doctorMapper.selectDoctorByMobiles(telephoneList);
				if (CollectionsUtil.isNotEmptyList(doctors)){
					doctor = doctors.get(0);
					// 判断库中同一个医生是否有不同的代表且同一个角色
					List<DrugUser> roleDrugUserList = drugUserMapper.getRoleIdListByDoctor(drugUser.getId(), doctor.getId(), product.getId());
					if (CollectionsUtil.isNotEmptyList(roleDrugUserList)) {
						List<String> drugUserEmailList = roleDrugUserList.stream().filter(du -> (drugUser.getRoleId().equals(du.getRoleId()))).map(DrugUser::getEmail).distinct().collect(Collectors.toList());
						if (CollectionsUtil.isNotEmptyList(drugUserEmailList)){
							DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
							doctorImportErrorDetail.setSheetName(productName);
							doctorImportErrorDetail.setError("和代表"+ drugUserEmailList.toString() +"关联的角色相同！");
							doctorImportErrorDetail.setRowNum(row);
							detailList.add(doctorImportErrorDetail);
							failNum++;
							continue;
						}

					}
				}

				HospitalProvinceBean hospitalProvinceBean = new HospitalProvinceBean();
				hospitalProvinceBean.setName(hospitalName);
				hospitalProvinceBean.setProvince(province);
				hospitalProvinceBean.setCity(city);
				hospitalProvinceBean.setLevel(Integer.valueOf(hospitalLevel));
				Long hospitalId = this.getHospitalId(hospitalProvinceBean);
				doctor.setMobile(telephoneList.get(0));
				doctor.setTelephoneList(telephoneList);
				doctor.setSex(Integer.valueOf(sex));
				doctor.setHospitalId(hospitalId);
				doctor.setHospitalName(hospitalName);
				doctor.setDepartment(depart);
				doctor.setDoctorLevel(positions);
				doctor.setName(doctorName);
				doctor.setStatus(1);

				Long doctorId;
				try {
					Doctor saveDoctor = doctorRepository.save(doctor);
					doctorId = saveDoctor.getId();
					successNum ++;
				}catch (Exception e){
					logger.error("导入医生出错，doctorTelephones={}, drugUserEmail={}", telephone, drugUserEmail, e);
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setSheetName(productName);
					doctorImportErrorDetail.setError("代表邮箱：" + drugUserEmail + " 手机号为：" + telephone + " 导入失败");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				// 保存医生的手机号
				this.saveDoctorTelephone(doctorId, telephoneList);

				// 保存医生和代表的关联
				DrugUserDoctorParams drugUserDoctorParams = new DrugUserDoctorParams();
				drugUserDoctorParams.setDoctorEmail(doctor.getEmail());
				drugUserDoctorParams.setDoctorId(doctorId);
				drugUserDoctorParams.setDoctorName(doctorName);
				drugUserDoctorParams.setDrugUserId(drugUser.getId());
				drugUserDoctorParams.setDrugUserName(drugUser.getName());
				drugUserDoctorParams.setDrugUserEmail(drugUserEmail);
				drugUserDoctorParams.setProdId(product.getId().intValue());
				drugUserDoctorParamsList.add(drugUserDoctorParams);
			}

		}

		// 批量保存代表医生的关联关系
		this.batchAddDrugUserDoctor(drugUserDoctorParamsList);

		doctorImportError.setTotalNum(totalNum);
		doctorImportError.setSuccessNum(successNum);
		doctorImportError.setFailNum(failNum);
		doctorImportError.setDetailList(detailList);
		doctorImportError.setRepeatNum(repeatNum);

		return doctorImportError;


	}


	/**
	 * TODO 优化代码结构
	 * 获取Excel导入的数据医生新增或者更新，并且返回错误,不同的sheet的错误分成了不同的Map
	 * @param doctorListMap
	 * @return
	 */
	private Map<String,DoctorImportErrorResponse> saveOrUpdateDoctorMap(Map<String, List<DoctorVo>> doctorListMap) {

		Map<String,DoctorImportErrorResponse> errorMap = new HashMap<>();
		List<DrugUserDoctorParams> drugUserDoctorParamsList = new ArrayList<>();
		for (Map.Entry<String, List<DoctorVo>> entry: doctorListMap.entrySet()){
			String productName = entry.getKey();
			ProductLine product = productLineRepository.findFirstByName(productName);
			if (product == null){
				throw new BusinessException(ErrorEnum.ERROR, "sheet名称:" + productName + " 对应产品不存在");
			}


			List<DoctorVo> doctorVos = entry.getValue();
			if (CollectionsUtil.isEmptyList(doctorVos)){
				throw new BusinessException(ErrorEnum.ERROR, "sheet名称为:" + productName + "的表格内容为空");
			}

			DoctorImportErrorResponse doctorImportError = new DoctorImportErrorResponse();
			List<DoctorImportErrorDetailResponse> detailList = new ArrayList<>();
			doctorImportError.setSheetName(productName);
			doctorImportError.setTotalNum(doctorVos.size());

			// 统计重复的数据，不同的行包含相同的手机号并且对应的代表一样就算重复
			Integer repeatNum = 0;
			Integer successNum = 0;
			Integer failNum = 0;

			// 先查询是否有目标的医院
			List<HospitalResponse> productHospitalList = productHospitalMapper.getHospitalListByPoductId(product.getId());

			for (int i = 0; i < doctorVos.size(); i++) {
				DoctorVo doctorVo = doctorVos.get(i);
				String province = doctorVo.getProvince();
				String city = doctorVo.getCity();
				String hospitalName = doctorVo.getHospitalName();
				String hospitalLevel = doctorVo.getHospitalLevel();
				String drugHospitalId = doctorVo.getDrugHospitalId();
				String depart = doctorVo.getDepart();
				String address = doctorVo.getAddress();
				String doctorName = doctorVo.getDoctorName();
				String sex = doctorVo.getSex();
				String positions = doctorVo.getPositions();
				String telephone = doctorVo.getTelephone();
				List<String> telephoneList = new ArrayList<>();
				int row = i + 2;
				if (telephone.contains("，")){
					telephone = telephone.replace("，", ",");
				}

				String drugUserEmail = doctorVo.getDrugUserEmail();
				if (StringUtil.isEmpty(province)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("医院所在省为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				if (StringUtil.isEmpty(city)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("医院所在市为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				if (StringUtil.isEmpty(hospitalName)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("医院名称为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}


				// 判断医院是否是目标医院
				if (CollectionsUtil.isNotEmptyList(productHospitalList)){
					List<String> hospitalNameList = productHospitalList.stream().map(HospitalResponse::getHospitalName).distinct().collect(Collectors.toList());
					if (CollectionsUtil.isNotEmptyList(hospitalNameList) && (!hospitalNameList.contains(hospitalName))){
						DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
						doctorImportErrorDetail.setError("医院：" + hospitalName + " 不在目标医院中！");
						doctorImportErrorDetail.setRowNum(row);
						detailList.add(doctorImportErrorDetail);
						failNum ++;
						continue;
					}
				}


				if (StringUtil.isNotEmpty(hospitalLevel) && (!"0".equals(hospitalLevel))
						&& (HospitalLevelUtil.getLevelNameByLevelCode(hospitalLevel).equals("未知"))){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("医院级别:" + hospitalLevel +"输入不合法！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}


				if (StringUtil.isEmpty(doctorName)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("医生姓名为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}


				if (StringUtil.isEmpty(telephone)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("医生手机号为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				if (telephone.contains(",")){
					String[] telephoneArray = telephone.split(",");
					if (CollectionsUtil.isEmptyArray(telephoneArray)){
						DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
						doctorImportErrorDetail.setError("医生手机号为空！");
						doctorImportErrorDetail.setRowNum(row);
						detailList.add(doctorImportErrorDetail);
						continue;
					}

					for (String t : telephoneArray) {
						if (!RegularUtils.isMatcher(RegularUtils.MATCH_ELEVEN_NUM, t)){
							DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
							doctorImportErrorDetail.setError("医生手机号 "+ telephone +" 输入不合法!");
							doctorImportErrorDetail.setRowNum(row);
							detailList.add(doctorImportErrorDetail);
							continue;
						}

						telephoneList.add(t);
					}
				}else {
					if (!RegularUtils.isMatcher(RegularUtils.MATCH_ELEVEN_NUM, telephone)){
						DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
						doctorImportErrorDetail.setError("医生手机号 "+ telephone +" 输入不合法!");
						doctorImportErrorDetail.setRowNum(row);
						detailList.add(doctorImportErrorDetail);
						continue;
					}

					telephoneList.add(telephone);
				}

				if (CollectionsUtil.isEmptyList(telephoneList)){
					failNum ++;
					continue;
				}


				// 统计重复的数据
				boolean repeatFlag = false;
				for (String s : telephoneList) {
					List<DoctorVo> filterDoctorVoList = doctorVos.stream().filter(dv -> (StringUtil.isNotEmpty(dv.getTelephone()) && dv.getTelephone().contains(s) && drugUserEmail.equals(dv.getDrugUserEmail()))).collect(Collectors.toList());
					if (CollectionsUtil.isNotEmptyList(filterDoctorVoList) && filterDoctorVoList.size() > 1){
						DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
						doctorImportErrorDetail.setError("数据重复，医生手机号包含:" + s + "，代表邮箱为：" + drugUserEmail);
						doctorImportErrorDetail.setRowNum(row);
						detailList.add(doctorImportErrorDetail);
						repeatNum ++;
						repeatFlag = true;
						continue;
					}
				}

				// 有重复的不能添加
				if (repeatFlag){
					failNum ++;
					continue;
				}



				if (StringUtil.isNotEmpty(sex)){
					if (!(sex.equals("男") || sex.equals("女"))){
						DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
						doctorImportErrorDetail.setError("医生性别只能输入男或者女！");
						doctorImportErrorDetail.setRowNum(row);
						detailList.add(doctorImportErrorDetail);
						failNum ++;
						continue;
					}

					if (sex.equals("男")){
						sex = "0";
					}

					if (sex.equals("女")){
						sex = "1";
					}
				}else {
					sex = "2";
				}


				if (StringUtil.isEmpty(drugUserEmail)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("代表工作邮箱为空！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				DrugUser drugUser = drugUserRepository.findFirstByEmail(drugUserEmail);
				if (drugUser == null){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("代表邮箱为：" + drugUserEmail + " 对应代表不存在！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}
				drugUser.setRoleId(roleUserService.checkVirtualRole(drugUser.getId()));

				List<Long> productIdList = drugUserMapper.getProductIdListByEmail(drugUserEmail);
				if (CollectionsUtil.isNotEmptyList(productIdList) && (!productIdList.contains(product.getId()))){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("邮箱：" + drugUserEmail + " 对应代表不在指定产品：" + productName + " 下！");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}


				// 判断Excel中同一个医生是否有不同的代表且同一个角色
				boolean roleFlag = false;
				for (String te: telephoneList){
					List<DoctorVo> doctorDrugUserList = doctorVos.stream().filter(d -> (StringUtil.isNotEmpty(d.getTelephone()) && d.getTelephone().contains(te) && (!drugUserEmail.equals(d.getDrugUserEmail())))).collect(Collectors.toList());
					if (CollectionsUtil.isNotEmptyList(doctorDrugUserList)){
						List<String> drugUserEmailList = doctorDrugUserList.stream().map(DoctorVo::getDrugUserEmail).distinct().collect(Collectors.toList());
						// 查询角色数量
						if (CollectionsUtil.isNotEmptyList(drugUserEmailList)){
							List<Long> roleIdList = drugUserMapper.getRoleIdList(drugUserEmailList);
							if (CollectionsUtil.isNotEmptyList(roleIdList) && roleIdList.contains(drugUser.getRoleId())){
								DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
								doctorImportErrorDetail.setError("和代表"+ drugUserEmailList.toString() +"关联的角色相同！");
								doctorImportErrorDetail.setRowNum(row);
								detailList.add(doctorImportErrorDetail);
								roleFlag = true;
								continue;
							}
						}

					}
				}

				// 判断同一个医生是否有不同的代表且同一个角色
				if (roleFlag){
					failNum ++;
					continue;
				}



				List<DrugUser> drugUserList = drugUserMapper.getDrugUserList(telephone, product.getId(), drugUser.getRoleId());
				if (CollectionsUtil.isNotEmptyList(drugUserList)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					List<String> drugUserNameList = drugUserList.stream().map(DrugUser::getName).distinct().collect(Collectors.toList());
					doctorImportErrorDetail.setError("手机号为：" + telephone + " 医生已经关联同一角色代表:" + drugUserNameList);
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				Doctor doctor = new Doctor();
				List<Doctor> doctors = doctorMapper.selectDoctorByMobiles(telephoneList);
				if (CollectionsUtil.isNotEmptyList(doctors)){
					doctor = doctors.get(0);
					// 判断库中同一个医生是否有不同的代表且同一个角色
					List<DrugUser> roleDrugUserList = drugUserMapper.getRoleIdListByDoctor(drugUser.getId(), doctor.getId(), product.getId());
					if (CollectionsUtil.isNotEmptyList(roleDrugUserList)) {
						List<String> drugUserEmailList = roleDrugUserList.stream().filter(du -> (drugUser.getRoleId().equals(du.getRoleId()))).map(DrugUser::getEmail).distinct().collect(Collectors.toList());
						if (CollectionsUtil.isNotEmptyList(drugUserEmailList)){
							DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
							doctorImportErrorDetail.setError("和代表"+ drugUserEmailList.toString() +"关联的角色相同！");
							doctorImportErrorDetail.setRowNum(row);
							detailList.add(doctorImportErrorDetail);
							failNum++;
							continue;
						}

					}
				}

				HospitalProvinceBean hospitalProvinceBean = new HospitalProvinceBean();
				hospitalProvinceBean.setName(hospitalName);
				hospitalProvinceBean.setProvince(province);
				hospitalProvinceBean.setCity(city);
				hospitalProvinceBean.setLevel(Integer.valueOf(hospitalLevel));
				Long hospitalId = this.getHospitalId(hospitalProvinceBean);
				doctor.setMobile(telephoneList.get(0));
				doctor.setTelephoneList(telephoneList);
				doctor.setSex(Integer.valueOf(sex));
				doctor.setHospitalId(hospitalId);
				doctor.setHospitalName(hospitalName);
				doctor.setDepartment(depart);
				doctor.setDoctorLevel(positions);
				doctor.setName(doctorName);
				doctor.setStatus(1);

				Long doctorId;
				try {
					Doctor saveDoctor = doctorRepository.save(doctor);
					doctorId = saveDoctor.getId();
					successNum ++;
				}catch (Exception e){
					logger.error("导入医生出错，doctorTelephones={}, drugUserEmail={}", telephone, drugUserEmail, e);
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("代表邮箱：" + drugUserEmail + " 手机号为：" + telephone + " 导入失败");
					doctorImportErrorDetail.setRowNum(row);
					detailList.add(doctorImportErrorDetail);
					failNum ++;
					continue;
				}

				// 保存医生的手机号
				this.saveDoctorTelephone(doctorId, telephoneList);

				// 保存医生和代表的关联
				DrugUserDoctorParams drugUserDoctorParams = new DrugUserDoctorParams();
				drugUserDoctorParams.setDoctorEmail(doctor.getEmail());
				drugUserDoctorParams.setDoctorId(doctorId);
				drugUserDoctorParams.setDoctorName(doctorName);
				drugUserDoctorParams.setDrugUserId(drugUser.getId());
				drugUserDoctorParams.setDrugUserName(drugUser.getName());
				drugUserDoctorParams.setDrugUserEmail(drugUserEmail);
				drugUserDoctorParams.setProdId(product.getId().intValue());
				drugUserDoctorParamsList.add(drugUserDoctorParams);
			}

			doctorImportError.setSuccessNum(successNum);
			doctorImportError.setFailNum(failNum);
			doctorImportError.setDetailList(detailList);
			doctorImportError.setRepeatNum(repeatNum);
			errorMap.put(productName, doctorImportError);
		}

		// 批量保存代表医生的关联关系
		this.batchAddDrugUserDoctor(drugUserDoctorParamsList);

		return errorMap;

	}


	/**
	 * 过滤掉无效的联系方式
	 * @param doctorTelephones
	 * @return
	 */
	@Override
	public List<String> filterInvalidTelephones(List<DoctorTelephoneResponse> doctorTelephones) {
		if (CollectionsUtil.isEmptyList(doctorTelephones)){
			return null;
		}

		List<String> telephoneList = new ArrayList<>();
		List<String> telephones = doctorTelephones.stream().map(DoctorTelephoneResponse::getTelephone).distinct().collect(Collectors.toList());
		if (CollectionsUtil.isNotEmptyList(telephones)){
			for (String telephone : telephones) {
				if (RegularUtils.isMatcher(RegularUtils.MATCH_ELEVEN_NUM, telephone) || RegularUtils.isMatcher(RegularUtils.MATCH_FIX_PHONE, telephone)){
					telephoneList.add(telephone);
				}
			}
		}

		return telephoneList;
	}

	@Override
	public Map<String, Long> getProductNameIdMap() {
		Map<String, Long> map = new HashMap<>();
		List<ProductLine> allProductLineList = productLineMapper.getAllProductLineList();
		if (CollectionsUtil.isEmptyList(allProductLineList)){
			return map;
		}

		allProductLineList.forEach(p->{
			Long id = p.getId();
			String name = p.getName();
			map.put(name, id);
		});


		return map;
	}

	@Override
	public Map<Long, String> getProductIdNameMap() {
		Map<Long, String> map = new HashMap<>();
		List<ProductLine> allProductLineList = productLineMapper.getAllProductLineList();
		if (CollectionsUtil.isEmptyList(allProductLineList)){
			return map;
		}

		allProductLineList.forEach(p->{
			Long id = p.getId();
			String name = p.getName();
			map.put(id, name);
		});

		return map;
	}


	/**
	 * 批量保存代表医生关联关系
	 * @param drugUserDoctorParamsList
	 */
	private void batchAddDrugUserDoctor(List<DrugUserDoctorParams> drugUserDoctorParamsList) {
		if (CollectionsUtil.isEmptyList(drugUserDoctorParamsList)){
			return;
		}

		// 每次最多插入1000条
		int size = drugUserDoctorParamsList.size();
		int totalPage = PageUtil.getTotalPage(size, BATCH_INSERT_SIZE);
		List<DrugUserDoctorParams> subDrugUserDoctorParamsList = null;
		for (int i = 0; i < totalPage; i++) {
			if (i == (totalPage - 1)) {

				subDrugUserDoctorParamsList = drugUserDoctorParamsList.subList(i * BATCH_INSERT_SIZE, i * BATCH_INSERT_SIZE + (size - i * BATCH_INSERT_SIZE));
			} else {
				subDrugUserDoctorParamsList = drugUserDoctorParamsList.subList(i * BATCH_INSERT_SIZE, i * BATCH_INSERT_SIZE + BATCH_INSERT_SIZE);
			}

			List<DrugUserDoctorParams> addDrugUserDoctorParamsList = new ArrayList<>(subDrugUserDoctorParamsList);
			if (CollectionsUtil.isNotEmptyList(addDrugUserDoctorParamsList)){
				drugUserDoctorMapper.saveDrugUserDoctors(addDrugUserDoctorParamsList);
			}
		}

		// 删除掉重复的
		this.deleteRepeatDrugUserDoctorRecord();
	}


	/**
	 * 删除掉重复的记录
	 */
	@Async
	public void deleteRepeatDrugUserDoctorRecord(){

		List<Long> availableDeleteIdList = drugUserDoctorMapper.getAvailableDeleteIdList(1);
		while(CollectionsUtil.isNotEmptyList(availableDeleteIdList)){
			drugUserDoctorMapper.deleteByIdList(availableDeleteIdList);
			availableDeleteIdList = drugUserDoctorMapper.getAvailableDeleteIdList(1);
		}

		List<Long> noAvailableDeleteIdList = drugUserDoctorMapper.getAvailableDeleteIdList(0);
		while(CollectionsUtil.isNotEmptyList(noAvailableDeleteIdList)){
			drugUserDoctorMapper.deleteByIdList(noAvailableDeleteIdList);
			noAvailableDeleteIdList = drugUserDoctorMapper.getAvailableDeleteIdList(0);
		}

		List<Long> repeatDeleteIdList = drugUserDoctorMapper.getRepeatDeleteIdList();
		if(CollectionsUtil.isNotEmptyList(repeatDeleteIdList)){
			drugUserDoctorMapper.deleteByIdList(repeatDeleteIdList);
		}
	}



	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long getDelta(Date nextVisitTime) {
		String next = FMT.format(nextVisitTime);
		String current = FMT.format(new Date(System.currentTimeMillis()));

		long delta = 0;
		try {
			Date currentDate = FMT.parse(current);
			Date nextVisitDate = FMT.parse(next);
			delta = nextVisitDate.getTime() - currentDate.getTime();
		} catch (ParseException e) {
			logger.error("时间格式转换异常", e);
		}

		return delta;
	}


	/**
	 * 新增医生ID
	 * @param doctorId
	 * @param doctorTelephoneList
	 */
	private void saveDoctorTelephone(Long doctorId, List<String> doctorTelephoneList){
		List<String> doctorTelephones = doctorMapper.getDoctorTelephone(doctorId);
		if (CollectionsUtil.isNotEmptyList(doctorTelephones)){
			doctorTelephoneList.addAll(doctorTelephones);
		}
		List<String> distinctTelephoneList = doctorTelephoneList.stream().distinct().collect(Collectors.toList());
		if (CollectionsUtil.isNotEmptyList(distinctTelephoneList)){
			doctorMapper.deleteDoctorTelephone(doctorId);
			doctorMapper.saveTelephoneList(doctorId, distinctTelephoneList);
		}
	}

	
	public static void main(String[] args) throws ParseException {
		CommonService s = new CommonServiceImpl();
		
		String result = s.alterNextVisitTimeContent(new Date());
		System.out.println(result);
		
		result = s.alterNextVisitTimeContent(FMT.parse("2018-10-12 23:59:59"));
		System.out.println(result);
		
		result = s.alterNextVisitTimeContent(FMT.parse("2018-10-09 23:59:59"));
		System.out.println(result);
	}
}
