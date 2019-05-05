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
import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.dao.ProductLineRepository;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.ProductLine;
import com.nuoxin.virtual.rep.api.utils.*;
import com.nuoxin.virtual.rep.api.web.controller.request.vo.DoctorVo;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DoctorImportErrorDetailResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DoctorImportErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.mybatis.DrugUserMapper;
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
	private ProductLineRepository productLineRepository;

	@Resource
	private DrugUserRepository drugUserRepository;

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
	public void doctorImport(MultipartFile file) {
		InputStream inputStream = null;
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

		//新增或者更新，并且返回错误
		Map<String,List<DoctorImportErrorResponse>> errorMap = this.saveOrUpdateDoctorMap(doctorListMap);

	}

	/**
	 * 获取Excel导入的数据医生新增或者更新，并且返回错误
	 * @param doctorListMap
	 * @return
	 */
	private Map<String,List<DoctorImportErrorResponse>> saveOrUpdateDoctorMap(Map<String, List<DoctorVo>> doctorListMap) {
		if (CollectionsUtil.isEmptyMap(doctorListMap)){
			throw new BusinessException(ErrorEnum.ERROR, "导入医生Excel为空！");
		}

		Map<String,List<DoctorImportErrorResponse>> errorMap = new HashMap<>();
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

			// 统计重复的手机号
			List<Map.Entry<String, Long>> repeatTelephone = doctorVos.stream().map(DoctorVo::getTelephone)
					.collect(Collectors.groupingBy(t -> t, Collectors.counting())).entrySet().stream().filter(map -> (map.getValue() > 1L)).collect(Collectors.toList());
			if (CollectionsUtil.isNotEmptyList(repeatTelephone)){
				Long repeatCount = 0L;
				for (Map.Entry<String, Long> rt : repeatTelephone) {
					Long value = rt.getValue();
					repeatCount += value;
				}
				doctorImportError.setRepeatNum(repeatCount.intValue());
			}




			for (int i = 0; i < doctorVos.size(); i++) {
				DoctorVo doctorVo = doctorVos.get(0);
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
				String drugUserEmail = doctorVo.getDrugUserEmail();
				if (StringUtil.isEmpty(province)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("医院所在省为空！");
					doctorImportErrorDetail.setRowNum(i++);
					detailList.add(doctorImportErrorDetail);
					continue;
				}

				if (StringUtil.isEmpty(city)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("医院所在市为空！");
					doctorImportErrorDetail.setRowNum(i++);
					detailList.add(doctorImportErrorDetail);
					continue;
				}

				if (StringUtil.isEmpty(hospitalName)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("医院名称为空！");
					doctorImportErrorDetail.setRowNum(i++);
					detailList.add(doctorImportErrorDetail);
					continue;
				}

				if (StringUtil.isNotEmpty(hospitalLevel) && (!"0".equals(hospitalLevel))
						&& (!HospitalLevelUtil.getLevelNameByLevelCode(hospitalLevel).equals("未知"))){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("医院级别输入不合法！");
					doctorImportErrorDetail.setRowNum(i++);
					detailList.add(doctorImportErrorDetail);
					continue;
				}


				if (StringUtil.isEmpty(doctorName)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("医生姓名为空！");
					doctorImportErrorDetail.setRowNum(i++);
					detailList.add(doctorImportErrorDetail);
					continue;
				}


				if (StringUtil.isEmpty(telephone)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("医生手机号为空！");
					doctorImportErrorDetail.setRowNum(i++);
					detailList.add(doctorImportErrorDetail);
					continue;
				}

				if (!RegularUtils.isMatcher(RegularUtils.MATCH_ELEVEN_NUM, telephone)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("医生手机号输入不合法，手机号：" + telephone);
					doctorImportErrorDetail.setRowNum(i++);
					detailList.add(doctorImportErrorDetail);
					continue;
				}


				if (StringUtil.isNotEmpty(sex)){
					if (!(sex.equals("男") || sex.equals("女"))){
						DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
						doctorImportErrorDetail.setError("医生性别只能输入男或者女！");
						doctorImportErrorDetail.setRowNum(i++);
						detailList.add(doctorImportErrorDetail);
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
					doctorImportErrorDetail.setRowNum(i++);
					detailList.add(doctorImportErrorDetail);
					continue;
				}


				List<Long> productIdList = drugUserMapper.getProductIdListByEmail(drugUserEmail);
				if (CollectionsUtil.isNotEmptyList(productIdList) || (!productIdList.contains(product.getId()))){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("邮箱：" + drugUserEmail + " 对应代表不在指定产品：" + productName + " 下！");
					doctorImportErrorDetail.setRowNum(i++);
					detailList.add(doctorImportErrorDetail);
					continue;
				}

				// 判断同一个医生是否有不同的代表且同一个角色
				List<DoctorVo> doctorDrugUserList = doctorVos.stream().filter(d -> (d.getTelephone().equals(telephone))).collect(Collectors.toList());
				if (CollectionsUtil.isNotEmptyList(doctorDrugUserList) && doctorDrugUserList.size() > 1){
					List<String> drugUserEmailList = doctorDrugUserList.stream().map(DoctorVo::getDrugUserEmail).distinct().collect(Collectors.toList());
					// 查询角色数量
					if (CollectionsUtil.isNotEmptyList(drugUserEmailList)){
						List<Long> roleIdList = drugUserMapper.getRoleIdList(drugUserEmailList);
						if (CollectionsUtil.isNotEmptyList(roleIdList) && roleIdList.size() > 1){
							DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
							doctorImportErrorDetail.setError("手机号为：" + telephone + " 数据重复，对应的代表"+ drugUserEmailList.toString() +"角色相同！");
							doctorImportErrorDetail.setRowNum(i++);
							detailList.add(doctorImportErrorDetail);
							continue;
						}
					}

				}

				DrugUser drugUser = drugUserRepository.findFirstByEmail(drugUserEmail);
				if (drugUser == null){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					doctorImportErrorDetail.setError("代表邮箱为：" + drugUserEmail + " 对应代表不存在！");
					doctorImportErrorDetail.setRowNum(i++);
					detailList.add(doctorImportErrorDetail);
					continue;
				}


				List<DrugUser> drugUserList = drugUserMapper.getDrugUserList(telephone, product.getId(), drugUser.getRoleId());
				if (CollectionsUtil.isNotEmptyList(drugUserList)){
					DoctorImportErrorDetailResponse doctorImportErrorDetail = new DoctorImportErrorDetailResponse();
					List<String> drugUserNameList = drugUserList.stream().map(DrugUser::getName).distinct().collect(Collectors.toList());
					doctorImportErrorDetail.setError("手机号为：" + telephone + " 医生已经关联同一角色代表:" + drugUserNameList);
					doctorImportErrorDetail.setRowNum(i++);
					detailList.add(doctorImportErrorDetail);
					continue;
				}

			}

			//doctorVos.stream().
			return null;
		}

		return null;
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
