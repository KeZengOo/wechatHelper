package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.io.FileOutputStream;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.entity.v2_5.DynamicFieldResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.DynamicFieldValueResponse;
import com.nuoxin.virtual.rep.api.enums.ClassificationEnum;
import com.nuoxin.virtual.rep.api.enums.MeetingTimeTypeEnum;
import com.nuoxin.virtual.rep.api.enums.SearchTypeEnum;
import com.nuoxin.virtual.rep.api.mybatis.*;
import com.nuoxin.virtual.rep.api.utils.*;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.excel.SheetRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.SearchDynamicFieldRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.DoctorPotentialClassificationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.*;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.plan.VisitDoctorResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.CustomerFollowUpPageResponseBean;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v2_5.CustomerFollowUpService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.ScreenRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.SearchRequestBean;

import shaded.org.apache.commons.lang3.StringUtils;

/**
 * 客户跟进实现类
 * @author xiekaiyu
 */
@Service
public class CustomerFollowUpServiceImpl implements CustomerFollowUpService{
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerFollowUpServiceImpl.class);
	
	private static final List<Object> tableHeaders = new ArrayList<>(20);

	@Value("${download.file.path}")
	private String downloadPath;

	@Resource
	private DrugUserDoctorQuateMapper drugUserDoctorQuateMapper;

	@Resource
	private VirtualDoctorCallInfoMapper virtualDoctorCallInfoMapper;

	@Resource
	private DrugUserDoctorQuateResultMapper drugUserDoctorQuateResultMapper;

	@Resource
	private ProductVisitFrequencyMapper productVisitFrequencyMapper;

	@Resource
	private ProductClassificationFrequencyMapper productClassificationFrequencyMapper;

	@Resource
	private HolidayMapper holidayMapper;

	@Resource
	private DynamicFieldMapper dynamicFieldMapper;


	/**
	 * 初始化表头信息
	 */
	static {
		CustomerFollowUpServiceImpl.getTableHeaders();
	}
	
	@Resource
	private DoctorMapper doctorMapper;
	@Resource
	private CommonService commonService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public CustomerFollowUpPageResponseBean<List<CustomerFollowListBean>> list(SearchRequestBean pageRequest, String leaderPath) {
		int count = 0;
		CustomerFollowUpPageResponseBean pageResponse = null;
		// 获取所有下属(直接&间接) virtualDrugUserIds
		List<Long> virtualDrugUserIds = commonService.getSubordinateIds(leaderPath);
		if (CollectionsUtil.isNotEmptyList(virtualDrugUserIds)) {
			pageRequest.setVirtualDrugUserIds(virtualDrugUserIds);

			this.fileSearchType(pageRequest);
			this.handleSearchValueList(pageRequest);


			count = doctorMapper.getListCount(pageRequest);
			if(count > 0) {
				int currentSize = pageRequest.getCurrentSize();
				int pageSize = pageRequest.getPageSize();
				long startTime = System.currentTimeMillis();

				pageRequest.setCurrentSize(currentSize);

				List<CustomerFollowListBean> list = doctorMapper.getList(pageRequest);
				if (CollectionsUtil.isNotEmptyList(list)){
					// 填充产品信息
					this.fillProductInfos(list, leaderPath);
				}

				long endTime = System.currentTimeMillis();
				logger.info("list 耗时{}", (endTime-startTime) ,"ms");
				
				pageResponse = this.getDoctorsList(count, list, pageRequest);
			}
		} 
		
		this.compensate(pageRequest, pageResponse);
		return pageResponse;
	}

	/**
	 * 补充上搜索类型
	 * @param pageRequest
	 */
	private void fileSearchType(SearchRequestBean pageRequest) {
		Integer searchType = pageRequest.getSearchType();
		// 现在改成了只能一个产品
		Long productId = pageRequest.getProductLineIds().get(0);
		ProductVisitFrequencyResponseBean productVisitFrequency = productVisitFrequencyMapper.getProductVisitFrequency(productId);
		if (productVisitFrequency == null){
			throw new BusinessException(ErrorEnum.ERROR, "该产品还未设置拜访频次");
		}

		pageRequest.setMeetingTimeType(productVisitFrequency.getBeforeMeetingFrequencyType());
		if (searchType != null && searchType != 0){
			List<VisitDoctorResponseBean> classificationVisitDoctorList = null;
			if (searchType == SearchTypeEnum.SEARCH_TWO.getUserType()){
				classificationVisitDoctorList = doctorMapper.getClassificationVisitDoctorList(pageRequest);

				if (CollectionsUtil.isNotEmptyList(classificationVisitDoctorList)){
					List<Long> doctorIdList = classificationVisitDoctorList.stream().map(VisitDoctorResponseBean::getDoctorId).distinct().collect(Collectors.toList());
					List<DoctorPotentialResponseBean> doctorPotentialList = doctorMapper.getDoctorPotentialList(doctorIdList, pageRequest.getProductLineIds());
					List<DoctorClassificationResponseBean> doctorClassificationList = doctorMapper.getDoctorClassificationList(doctorIdList, pageRequest.getProductLineIds());
					classificationVisitDoctorList = this.getSearchTwoClassificationVisitDoctorList(classificationVisitDoctorList,doctorPotentialList, doctorClassificationList, pageRequest.getProductLineIds());
				}

				classificationVisitDoctorList = this.getVisitDoctorList(classificationVisitDoctorList, productVisitFrequency.getVisitFrequency(), SearchTypeEnum.SEARCH_TWO.getUserType());
			}

			if (searchType == SearchTypeEnum.SEARCH_THREE.getUserType()){
				classificationVisitDoctorList = doctorMapper.getVisitFrequencyDayDoctorList(pageRequest);
				classificationVisitDoctorList = this.getVisitDoctorList(classificationVisitDoctorList, productVisitFrequency.getVisitFrequency(), SearchTypeEnum.SEARCH_THREE.getUserType());
			}

			if (searchType == SearchTypeEnum.SEARCH_FOUR.getUserType()){
				classificationVisitDoctorList = doctorMapper.getRetVisitFrequencyDayDoctorList(pageRequest);
			}


			if (searchType == SearchTypeEnum.SEARCH_FIVE.getUserType()){
				// 1是工作日，2是小时
				Integer beforeMeetingFrequencyType = productVisitFrequency.getBeforeMeetingFrequencyType();
				if (beforeMeetingFrequencyType == MeetingTimeTypeEnum.DAY.getType()){
					pageRequest.setMeetingTimeType(beforeMeetingFrequencyType);
					classificationVisitDoctorList = doctorMapper.getBeforeMeetingDoctorList(pageRequest);
					classificationVisitDoctorList = this.getVisitDoctorList(classificationVisitDoctorList, beforeMeetingFrequencyType, SearchTypeEnum.SEARCH_FIVE.getUserType());
				}

			}

			if (searchType == SearchTypeEnum.SEARCH_SIX.getUserType()){
				// 1是工作日，2是小时
				Integer afterMeetingFrequencyType = productVisitFrequency.getAfterMeetingFrequencyType();
				pageRequest.setMeetingTimeType(afterMeetingFrequencyType);
				if (MeetingTimeTypeEnum.DAY.getType()==afterMeetingFrequencyType){
					classificationVisitDoctorList = doctorMapper.getAfterMeetingDoctorList(pageRequest);
					classificationVisitDoctorList = this.getVisitDoctorList(classificationVisitDoctorList, afterMeetingFrequencyType, SearchTypeEnum.SEARCH_SIX.getUserType());
				}
				if (MeetingTimeTypeEnum.HOUR.getType()==afterMeetingFrequencyType){
					classificationVisitDoctorList = doctorMapper.getAfterMeetingDoctorList(pageRequest);
				}


			}


			if (CollectionsUtil.isEmptyList(classificationVisitDoctorList)){
				classificationVisitDoctorList = new ArrayList<>();
				VisitDoctorResponseBean visitDoctorResponseBean = new VisitDoctorResponseBean();
				visitDoctorResponseBean.setDoctorId(0L);
				visitDoctorResponseBean.setVisitIntervalDay(0);
				classificationVisitDoctorList.add(visitDoctorResponseBean);
			}

			pageRequest.setVisitDoctorList(classificationVisitDoctorList);

		}

	}

	/**
	 * 得到医生分型潜力设置的拜访频次
	 * @param classificationVisitDoctorList
	 * @param doctorPotentialList
	 * @param doctorClassificationList
	 * @param productLineIds
	 * @return
	 */
	private List<VisitDoctorResponseBean> getSearchTwoClassificationVisitDoctorList(List<VisitDoctorResponseBean> classificationVisitDoctorList,List<DoctorPotentialResponseBean> doctorPotentialList,
																					List<DoctorClassificationResponseBean> doctorClassificationList,
																					List<Long> productLineIds) {

		if (CollectionsUtil.isEmptyList(classificationVisitDoctorList)
				|| CollectionsUtil.isEmptyList(doctorPotentialList)
				|| CollectionsUtil.isEmptyList(doctorClassificationList)
				|| CollectionsUtil.isEmptyList(productLineIds)){
			return new ArrayList<>();
		}

		List<DoctorPotentialClassificationResponseBean> frequencyList = productClassificationFrequencyMapper.getFrequencyList(productLineIds);
		if (CollectionsUtil.isEmptyList(frequencyList)){
			return new ArrayList<>();
		}


		List<DoctorPotentialClassificationRequestBean> list = new ArrayList<>();
		doctorClassificationList.forEach(c ->{
			String classification = c.getClassification();
			if (StringUtil.isNotEmpty(classification)){
				Optional<DoctorPotentialResponseBean> doctorPotentialFirst = doctorPotentialList.stream().filter(p -> p.getDoctorId().equals(c.getDoctorId())).findFirst();
				if (doctorPotentialFirst.isPresent()){
					DoctorPotentialResponseBean doctorPotentialResponseBean = doctorPotentialFirst.get();
					String potential = doctorPotentialResponseBean.getPotential();
					String[] classificationArray = classification.split(",");
					if (StringUtil.isNotEmpty(potential) && CollectionsUtil.isNotEmptyArray(classificationArray)){

						for (String f:classificationArray){
							DoctorPotentialClassificationRequestBean doctorPotentialClassificationRequestBean = new DoctorPotentialClassificationRequestBean();
							doctorPotentialClassificationRequestBean.setDoctorId(c.getDoctorId());
							doctorPotentialClassificationRequestBean.setClassification(f);
							doctorPotentialClassificationRequestBean.setPotential(potential);
							list.add(doctorPotentialClassificationRequestBean);
						}

					}
				}
			}

		});



		list.forEach(pc->{
			Optional<DoctorPotentialClassificationResponseBean> doctorPotentialClassification = frequencyList.stream().filter(f -> (f.getPotential().equals(pc.getPotential()) && f.getClassification().equals(pc.getClassification()))).findFirst();
			if (doctorPotentialClassification.isPresent()){
				DoctorPotentialClassificationResponseBean dcp = doctorPotentialClassification.get();
				pc.setFrequency(dcp.getFrequency());
			}
		});

		// 得到每个医生的最小拜访频次
		Map<Long, Integer> map = new HashMap<>();
		for (DoctorPotentialClassificationRequestBean dcf : list) {
			Long doctorId = dcf.getDoctorId();
			Integer value = map.get(doctorId);
			Integer frequency = dcf.getFrequency();
			if (frequency == null){
				frequency = 0;
			}

			if (value == null){
				map.put(doctorId, frequency);
			}else {
				if (frequency > value){
					map.put(doctorId, frequency);
				}
			}
		}

		List<VisitDoctorResponseBean> vList = new ArrayList<>();
		for (VisitDoctorResponseBean visitDoctorResponseBean : classificationVisitDoctorList) {
			Integer frequency = map.get(visitDoctorResponseBean.getDoctorId());
			if (frequency !=null && frequency > 0){
				visitDoctorResponseBean.setFrequency(map.get(visitDoctorResponseBean.getDoctorId()));
				vList.add(visitDoctorResponseBean);
			}

		}

		return vList;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public CustomerFollowUpPageResponseBean<List<CustomerFollowListBean>> search(SearchRequestBean request, String leaderPath) {
		CustomerFollowUpPageResponseBean pageResponseBean = null;
		int count = 0;
		 // 获取所有下属(直接&间接) virtualDrugUserIds
		List<Long> productLineIds = request.getProductLineIds();
		List<Long> virtualDrugUserIds = commonService.getSubordinateIds(leaderPath);
		if (CollectionsUtil.isNotEmptyList(virtualDrugUserIds)) {
			request.setVirtualDrugUserIds(virtualDrugUserIds);
			this.fileSearchType(request);
			this.handleSearchValueList(request);
			count = doctorMapper.getListCount(request);
			if(count > 0) {
				int currentSize = request.getCurrentSize();
				request.setCurrentSize(currentSize);
				List<CustomerFollowListBean> list = doctorMapper.getList(request);
				// TODO 填充上产品
				pageResponseBean = this.getDoctorsList(count, list, request);
//				this.fillProductInfos(list, productLineIds);
			}
		} 
		
		this.compensate(request, pageResponseBean);
		return pageResponseBean;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public CustomerFollowUpPageResponseBean<List<CustomerFollowListBean>> screen(SearchRequestBean request) {
		CustomerFollowUpPageResponseBean pageResponseBean = null;
		this.fileSearchType(request);
		this.handleSearchValueList(request);
		int count = doctorMapper.getListCount(request);
		if(count > 0 ) {
			int currentSize = request.getCurrentSize();
			request.setCurrentSize(currentSize);
			List<CustomerFollowListBean> list = doctorMapper.getList(request);
			pageResponseBean = this.getDoctorsList(count, list, request);
		}
		
		this.compensate(request, pageResponseBean);
		return pageResponseBean;
	}

	/**
	 * 处理动态字段，将空值的去掉
	 * @param request
	 */
	private void handleSearchValueList(SearchRequestBean request) {
		List<SearchDynamicFieldRequestBean> valueList = request.getValueList();
		if (CollectionsUtil.isEmptyList(valueList)){
			return;
		}

		List<SearchDynamicFieldRequestBean> handleValueList = new ArrayList<>();
		for (SearchDynamicFieldRequestBean searchDynamicFieldRequestBean : valueList) {
			Long dynamicFieldId = searchDynamicFieldRequestBean.getDynamicFieldId();
			List<String> dynamicFieldValueList = searchDynamicFieldRequestBean.getDynamicFieldValueList();
			if (dynamicFieldId == null || dynamicFieldId == 0 || CollectionsUtil.isEmptyList(dynamicFieldValueList)){
				continue;
			}

			handleValueList.add(searchDynamicFieldRequestBean);
		}

		request.setValueList(handleValueList);
	}

	@Override
	public SearchDynamicFieldListResponseBean getSearchDynamicField(Long productId) {
		SearchDynamicFieldListResponseBean searchDynamicFieldList = new SearchDynamicFieldListResponseBean();
		List<SearchDynamicFieldResponseBean> searchDynamicField = dynamicFieldMapper.getSearchDynamicField(productId);
		if (CollectionsUtil.isNotEmptyList(searchDynamicField)){
			Map<Integer, List<SearchDynamicFieldResponseBean>> map = searchDynamicField.stream().collect(Collectors.groupingBy(SearchDynamicFieldResponseBean::getClassification));
			for (Map.Entry<Integer, List<SearchDynamicFieldResponseBean>> entry:map.entrySet()){
				Integer type = entry.getKey();
				List<SearchDynamicFieldResponseBean> value = entry.getValue();
				if (CollectionsUtil.isEmptyList(value)){
					continue;
				}

				if (ClassificationEnum.BASIC.getType() == type){
					searchDynamicFieldList.setBasic(value);
				}

				if (ClassificationEnum.HOSPITAL.getType() == type){
					searchDynamicFieldList.setHospital(value);
				}

				if (ClassificationEnum.PRESCRIPTION.getType() == type){
					searchDynamicFieldList.setPrescription(value);
				}

				if (ClassificationEnum.VISIT.getType() == type){
					searchDynamicFieldList.setVisit(value);
				}
			}
		}

		return searchDynamicFieldList;
	}

	@Override
	public String getExportExcelName(SearchRequestBean searchReq) throws Exception{
		this.fileSearchType(searchReq);
		this.handleSearchValueList(searchReq);
		//设置不分页标志
		searchReq.setNonPagingFlag(1);
		List<CustomerFollowListBean> list = doctorMapper.getList(searchReq);
		if(CollectionsUtil.isEmptyList(list)){
			throw new BusinessException(ErrorEnum.SYSTEM_REQUEST_PARAM_ERROR, "无数据返回");
		}

		//医生id列表
		List<Long> doctorIds = new ArrayList<Long>();
		Set<Long> ids = new HashSet<>();
		//医生-基础信息map
		Map<Long, CustomerFollowListBean> doctorInfoMap = new HashMap<Long, CustomerFollowListBean>();

		list.forEach(d->{
			doctorIds.add(d.getDoctorId());
			ids.add(d.getDoctorId());
			doctorInfoMap.put(d.getDoctorId(), d);
				}
		);

		//产品id,目前产品数量为1
		Long productId = searchReq.getProductLineIds().get(0);

		//动态字段列表
		List<DynamicFieldResponse> dynamicTitleList = dynamicFieldMapper.getProductDynamicField(productId);

		//动态字段value列表
		List<DynamicFieldValueResponse> dynamicFieldValueList = dynamicFieldMapper.getProductDynamicFieldValue(productId, ids);
		Map<Long, List<DynamicFieldValueResponse>> doctorDynamicFieldMap = dynamicFieldValueList.stream().collect(Collectors.groupingBy(DynamicFieldValueResponse::getDoctorId));

		//医生拜访结果
		Map<Long, List<VisitResultResponseBean>> visitResultListMap = new HashMap<>();
		List<VisitResultResponseBean> visitResultList = drugUserDoctorQuateResultMapper.getVisitResultList(doctorIds, searchReq.getProductLineIds());
		if (CollectionsUtil.isNotEmptyList(visitResultList)){
			Map<Long, List<VisitResultResponseBean>> groupByVisitResultList = visitResultList.stream().collect(Collectors.groupingBy(VisitResultResponseBean::getDoctorId));
			if (CollectionsUtil.isNotEmptyMap(groupByVisitResultList)){
				visitResultListMap = groupByVisitResultList;
			}
		}

		//获得处方信息
		List<PrescriptionResponseBean> prescriptionList = dynamicFieldMapper.getPrescriptionListByDoctorList(productId, doctorIds);
		Map<Long, List<PrescriptionResponseBean>> prescriptionListMap = new HashMap<>();
		if (CollectionsUtil.isNotEmptyList(prescriptionList)){
			Map<Long, List<PrescriptionResponseBean>> groupByPrescriptionList = prescriptionList.stream().collect(Collectors.groupingBy(PrescriptionResponseBean::getDoctorId));
			if (CollectionsUtil.isNotEmptyMap(groupByPrescriptionList)){
				prescriptionListMap = groupByPrescriptionList;
			}
		}

		List<LinkedHashMap<String, Object>> mapList = new ArrayList<LinkedHashMap<String, Object>>();
		for(CustomerFollowListBean d : list){
			LinkedHashMap<String, Object> detailMap = new LinkedHashMap<String, Object>();
			Long doctorId = d.getDoctorId();
			//固定字段值
			detailMap.put("doctor_id", doctorId);
			detailMap.put("doctor_name", d.getDoctorName());
			detailMap.put("doctor_gender", d.getGender() == 0 ? "男" : d.getGender() == 1 ? "女" : "未知");
			detailMap.put("doctor_dept", d.getDepartment());
			detailMap.put("doctor_title", d.getTitle());
			detailMap.put("hospital_id", d.getHospitalId());
			detailMap.put("hospital_name", d.getHospitalName());
			//detailMap.put("hospital_level", d.getHospitalLevel());
			detailMap.put("hospital_levelName", HospitalLevelUtil.getLevelNameByLevelCode(d.getHospitalLevel().toString()));
			detailMap.put("hospital_province", d.getProvince());
			detailMap.put("hospital_city", d.getCity());

			//处方信息固定值
			List<PrescriptionResponseBean> tempPresList = prescriptionListMap.containsKey(doctorId) ? prescriptionListMap.get(doctorId): null;
			PrescriptionResponseBean prescription = null;
			if(CollectionsUtil.isNotEmptyList(tempPresList)){
				prescription = tempPresList.get(0);
			}
			detailMap.put("prescription_hasDrug", prescription == null ? "" : processPrescriptionShowFlag(prescription.getHasDrug()));
			detailMap.put("prescription_target", prescription == null ? "" : processPrescriptionShowFlag(prescription.getTarget()));
			detailMap.put("prescription_recruit", prescription == null ? "" : processPrescriptionShowFlag(prescription.getRecruit()));
			detailMap.put("prescription_breakOff", prescription == null ? "" : processPrescriptionShowFlag(prescription.getBreakOff()));

			detailMap.put("visit_result", visitResultListMap.containsKey(doctorId) ? processVisitResult(visitResultListMap.get(doctorId)) : "");

			//填充动态字段值
			List<DynamicFieldValueResponse> valueList = doctorDynamicFieldMap.containsKey(doctorId) ? doctorDynamicFieldMap.get(doctorId) : null;
			if(CollectionsUtil.isNotEmptyList(valueList)){
				valueList.forEach(v -> {
					if (v.getProp() !=null && (!"".equals(v.getProp().trim()))) {
						detailMap.put(v.getProp(), v.getValue() == null ? "" : v.getValue());
					}
				});
			}

			mapList.add(detailMap);
		}

		return getExportExcelName(mapList, dynamicTitleList);
	}

	//拜访记录结果展示
	private String processVisitResult(List<VisitResultResponseBean> resultList){
		if(CollectionsUtil.isEmptyList(resultList)){
			return "";
		}
		Set<String> resultSet = new HashSet<String>();
		resultList.forEach(r->{
			resultSet.add(r.getVisitResult() == null ? "" : r.getVisitResult());
		 }
		);
		return String.join(",", resultSet);
	}

	//处方信息结果展示
	private String processPrescriptionShowFlag(Integer flag){
		if(flag == null) return "未知";
		return flag == 1 ? "是" : flag == 0 ? "否" : "未知";
	}

	private Map<String, String> getTitleMap(List<DynamicFieldResponse> list){
		Map<String, String> titleMap = new LinkedHashMap<>();
		titleMap.put("doctor_id", "医生_id");
		titleMap.put("doctor_name", "医生_名称");
		titleMap.put("doctor_gender", "医生_性别");
		titleMap.put("doctor_dept", "医生_科室");
		titleMap.put("doctor_title", "医生_职称");
		titleMap.put("hospital_id", "医院_id");
		titleMap.put("hospital_name", "医院_名称");
		titleMap.put("hospital_levelName", "医院_等级");
		titleMap.put("hospital_province", "医院_省份");
		titleMap.put("hospital_city", "医院_城市");
		titleMap.put("prescription_hasDrug", "处方信息_是否有药");
		titleMap.put("prescription_target", "处方信息_是否目标医生");
		titleMap.put("prescription_recruit", "处方信息_是否招募");
		titleMap.put("prescription_breakOff", "处方信息_是否脱落");
		titleMap.put("visit_result", "拜访记录_拜访结果");

		list.sort((DynamicFieldResponse d1, DynamicFieldResponse d2) -> d1.getClassification().compareTo(d2.getClassification()));

		list.forEach(x->{
			String prefix = "";
			switch (x.getClassification()){
				case 1 : prefix = "医生_";
				break;
				case 2: prefix = "处方信息_";
				break;
				case 3: prefix = "拜访记录_";
				break;
				case 5: prefix = "医院_";
				break;
			}
			titleMap.put(x.getProp(), prefix + x.getLable());
		});
		return titleMap;
	}

	private String getExportExcelName(List<LinkedHashMap<String, Object>> mapList, List<DynamicFieldResponse> dynamicTitleList) throws Exception{
		List<SheetRequestBean> sheetList = new ArrayList<>();
		SheetRequestBean sheetRequestBean = new SheetRequestBean();
		sheetRequestBean.setDataList(mapList);
		sheetRequestBean.setTitleMap(getTitleMap(dynamicTitleList));
		sheetRequestBean.setSheetName("医生详情表");
		sheetList.add(sheetRequestBean);
		HSSFWorkbook wb = ExportExcel.excelLinkedHashMapExport(sheetList);

		String fileName = "virtual_app_doctorList_"  + DateUtil.getDateSecondString(new Date()) + ".xls";
		String fullFileName = downloadPath + fileName;
		FileOutputStream exportXls = null;
		try{
			exportXls = new FileOutputStream(fullFileName);
			wb.write(exportXls);
			exportXls.flush();
		}catch (Exception e){

		}finally {
			exportXls.close();

		}
		return fileName;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 医生列表信息处理(重要) TODO 补充对应的产品信息 @田存
	 * @param count int
	 * @param list List<CustomerFollowListBean>
	 * @param pageRequestBean
	 * @return PageResponseBean<List<CustomerFollowListBean>>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private CustomerFollowUpPageResponseBean<List<CustomerFollowListBean>> getDoctorsList(int count, List<CustomerFollowListBean> list,
																						  ScreenRequestBean pageRequestBean) {
		List<Long> productLineIds = pageRequestBean.getProductLineIds();
		final List<CustomerFollowListBean> customerFollowListBeanList = list;
		if (CollectionsUtil.isNotEmptyList(list)) {

			this.fillOtherDoctorInfo(list, productLineIds);


			list.forEach(doctor -> {
				this.alterCustomerFollowListBean(doctor, productLineIds);
				// TODO 补充对应的产品信息 @田存
//				if (CollectionsUtil.isNotEmptyList(productLineIds)){
//					this.fillProductInfos(customerFollowListBeanList, productLineIds);
//				}

			});
		}

		if (CollectionsUtil.isEmptyList(list)) {
			list = Collections.emptyList();
		}

		return new CustomerFollowUpPageResponseBean(pageRequestBean, count, list);
	}

	private void fillOtherDoctorInfo(List<CustomerFollowListBean> list, List<Long> productLineIds) {
		if (CollectionsUtil.isEmptyList(list)){
			return;
		}


		List<Long> doctorIdList = list.stream().map(CustomerFollowListBean::getDoctorId).distinct().collect(Collectors.toList());
		if (CollectionsUtil.isEmptyList(doctorIdList)){
			return;
		}


		// 医生的手机号
		Map<Long, List<CallTelephoneReponseBean>> telephoneMap = new HashMap<>();
		List<CallTelephoneReponseBean> allTelephoneCallCount = virtualDoctorCallInfoMapper.getAllTelephoneCallCount(doctorIdList);
		if (CollectionsUtil.isNotEmptyList(allTelephoneCallCount)){
			Map<Long, List<CallTelephoneReponseBean>> groupByList = allTelephoneCallCount.stream().collect(Collectors.groupingBy(CallTelephoneReponseBean::getDoctorId));
			this.orderByCallCountDesc(groupByList);

			if (CollectionsUtil.isNotEmptyMap(groupByList)){
				telephoneMap = groupByList;
			}

		}


		// 医生拜访结果
		Map<Long, List<VisitResultResponseBean>> visitResultListMap = new HashMap<>();
		List<VisitResultResponseBean> visitResultList = drugUserDoctorQuateResultMapper.getVisitResultList(doctorIdList, productLineIds);
		if (CollectionsUtil.isNotEmptyList(visitResultList)){
			Map<Long, List<VisitResultResponseBean>> groupByVisitResultList = visitResultList.stream().collect(Collectors.groupingBy(VisitResultResponseBean::getDoctorId));
			if (CollectionsUtil.isNotEmptyMap(groupByVisitResultList)){
				visitResultListMap = groupByVisitResultList;
			}
		}

		// 医生产品信息
		Map<Long, List<ProductInfoResponse>> productMap = new HashMap<>();
		List<ProductInfoResponse> productInfoList = drugUserDoctorQuateMapper.getAllProductInfoListByProductIdList(doctorIdList,productLineIds);
		if (CollectionsUtil.isNotEmptyList(productInfoList)){
			Map<Long, List<ProductInfoResponse>> collect = productInfoList.stream().collect(Collectors.groupingBy(ProductInfoResponse::getDoctorId));
			if (CollectionsUtil.isNotEmptyMap(collect)){
				productMap = collect;
			}
		}



		for (CustomerFollowListBean doctor:list){
			Long doctorId = doctor.getDoctorId();
			List<CallTelephoneReponseBean> callTelephoneList = telephoneMap.get(doctorId);
			if (CollectionsUtil.isNotEmptyList(callTelephoneList)){
				doctor.setMobiles(callTelephoneList);
			}

			List<VisitResultResponseBean> visitResultResponseBeanList = visitResultListMap.get(doctorId);
			if (CollectionsUtil.isNotEmptyList(visitResultResponseBeanList)){
				List<String> getVisitResult = visitResultResponseBeanList.stream().map(VisitResultResponseBean::getVisitResult).collect(Collectors.toList());
				doctor.setVisitResultList(getVisitResult);
			}


			List<ProductInfoResponse> productInfoResponses = productMap.get(doctorId);
			if (CollectionsUtil.isNotEmptyList(productInfoResponses)){
				// 超过两个则只去两个
				int size = productInfoResponses.size();
				if (size > 2){
					List<ProductInfoResponse> productInfoResponseList = new ArrayList<>(2);
					productInfoResponseList.add(productInfoResponses.get(0));
					productInfoResponseList.add(productInfoResponses.get(1));
					productInfoResponses = productInfoResponseList;
				}

				doctor.setProductInfos(productInfoResponses);
			}
		}

	}

	/**
	 * 根据接通次数降序排列
	 * @param groupByList
	 */
	private void orderByCallCountDesc(Map<Long, List<CallTelephoneReponseBean>> groupByList) {
		if (CollectionsUtil.isEmptyMap(groupByList)){
			return;
		}

		for (Map.Entry<Long, List<CallTelephoneReponseBean>> entry:groupByList.entrySet()){
			List<CallTelephoneReponseBean> value = entry.getValue();
			if (CollectionsUtil.isEmptyList(value)){
				continue;
			}
			Collections.sort(value, Comparator.comparing(CallTelephoneReponseBean::getCallCount).reversed());
		}

	}

	/**
	 * 填充上产品信息
	 * @param list
	 * @return
	 */
	private void fillProductInfos(List<CustomerFollowListBean> list, String leaderPath) {
		if (CollectionsUtil.isEmptyList(list)){
			return;
		}

		String leaderPatnTemp = leaderPath + "%";
		list.forEach(listBean ->{
			List<ProductInfoResponse> productInfoList = drugUserDoctorQuateMapper.getProductInfoList(listBean.getDoctorId(),
					leaderPatnTemp);
			if (CollectionsUtil.isNotEmptyList(productInfoList)){
				listBean.setProductInfos(productInfoList);
			}
		});
	}


	/**
	 * 填充上产品信息
	 * @param list
	 * @param productIdList
	 * @return
	 */
	private void fillProductInfos(List<CustomerFollowListBean> list, List<Long> productIdList) {
		if (CollectionsUtil.isEmptyList(list)){
			return;
		}
		list.forEach(listBean ->{
			List<ProductInfoResponse> productInfoList = drugUserDoctorQuateMapper.getProductInfoListByProductIdList(listBean.getDoctorId(),
					productIdList);
			if (CollectionsUtil.isNotEmptyList(productInfoList)){
				listBean.setProductInfos(productInfoList);
			}
		});
	}
	
	/**
	 * 修正列表信息
	 * @param doctor
	 * @param productLineIds
	 */
	private void alterCustomerFollowListBean(CustomerFollowListBean doctor, List<Long> productLineIds) {
		// 手机号s
//		List<String> mobiles = doctor.getMobiles();
//		mobiles.add(doctor.getDoctorMobile()); // 主要
//		String secondary = doctor.getSecondaryDoctorMobile(); // 次要
//		if(StringUtils.isNotBlank(secondary)) {
//			mobiles.add(doctor.getSecondaryDoctorMobile());
//		}
//		String thirdary = doctor.getThirdaryDoctorMobile();// 三要
//		if(StringUtils.isNotBlank(thirdary)) {
//			mobiles.add(doctor.getThirdaryDoctorMobile());
//		}


//		List<String> doctorTelephoneList = doctorMapper.getDoctorTelephone(doctor.getDoctorId());
//		if (CollectionsUtil.isNotEmptyList(doctorTelephoneList)){
//			doctor.setMobiles(doctorTelephoneList);
//		}


//		List<CallTelephoneReponseBean> telephoneCallCount = virtualDoctorCallInfoMapper.getTelephoneCallCount(doctor.getDoctorId());
//		if (CollectionsUtil.isNotEmptyList(telephoneCallCount)){
//			doctor.setMobiles(telephoneCallCount);
//		}


		// (上次)拜访时间
		Date visitTime = doctor.getVisitTime();
		if (visitTime != null) {
			long visitTimeDelta = System.currentTimeMillis() - visitTime.getTime();
			String lastVisitTime = commonService.alterLastVisitTimeContent(visitTimeDelta);
			doctor.setVisitTimeStr(lastVisitTime);
			doctor.setBeforeVisitDateTime(DateUtil.getDateTimeString(visitTime));
		} else {
			doctor.setVisitTimeStr("无");
		}

		// 下次拜访时间
		Date nextVisitTime = doctor.getNextVisitTime();
		if (nextVisitTime != null) {
			String nextVisitTimeStr = commonService.alterNextVisitTimeContent(nextVisitTime);
			doctor.setNextVisitTimeStr(nextVisitTimeStr);
			doctor.setNextVisitDateTime(DateUtil.getDateTimeString(nextVisitTime));
		} else {
			doctor.setNextVisitTimeStr("无");
		}
		
		// 拜访结果
//		String visitResult = doctor.getVisitResult();
//		if (StringUtils.isNotBlank(visitResult)) {
//			JSONArray jsonArr = JSONObject.parseArray(visitResult);
//			doctor.setVisitResultObj(jsonArr);
//		}

//		List<String> visitResult = drugUserDoctorQuateResultMapper.getVisitResult(doctor.getDoctorId(), productLineIds);
//		if (CollectionsUtil.isNotEmptyList(visitResult)){
//			doctor.setVisitResultList(visitResult);
//		}


		// 是否有微信
		String wechat = doctor.getWechat();
		if (StringUtils.isNotBlank(wechat)) {
			doctor.setIsHasWeChat(1); // 有
		} else {
			doctor.setIsHasWeChat(0); // 无
		}
	}
	
	/**
	 * PageResponseBean 为null 时的补偿
	 * @param pageResponseBean
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void compensate(PageRequestBean request, CustomerFollowUpPageResponseBean pageResponseBean) {
		if (pageResponseBean == null) {
			int count = 0;
			pageResponseBean = new CustomerFollowUpPageResponseBean(request, count, Collections.emptyList());
		}
		pageResponseBean.setTableHeaders(tableHeaders);
	}
	
	private static  void getTableHeaders () {
		TableHeader id = new TableHeader();
		id.setLabel("ID");
		id.setName("doctorId");
		tableHeaders.add(id);
		
		TableHeader doctorName = new TableHeader();
		doctorName.setLabel("客户姓名");
		doctorName.setName("doctorName");
		tableHeaders.add(doctorName);
		
		TableHeader mobile = new TableHeader();
		mobile.setLabel("手机号");
		mobile.setName("mobiles");
		tableHeaders.add(mobile);
		
		TableHeader gender = new TableHeader();
		gender.setLabel("性别");
		gender.setName("gender");
		tableHeaders.add(gender);
		
		TableHeader department = new TableHeader();
		department.setLabel("科室");
		department.setName("department");
		tableHeaders.add(department);
		
		TableHeader isHasWeChat = new TableHeader();
		isHasWeChat.setLabel("是否添加微信");
		isHasWeChat.setName("isHasWeChat");
		tableHeaders.add(isHasWeChat);
		
		TableHeader hospitalName = new TableHeader();
		hospitalName.setLabel("医院");
		hospitalName.setName("hospitalName");
		tableHeaders.add(hospitalName);
		
		TableHeader visitTime = new TableHeader();
		visitTime.setLabel("上次拜访时间");
		visitTime.setName("visitTimeStr");
		tableHeaders.add(visitTime);
		
		TableHeader visitResult = new TableHeader();
		visitResult.setLabel("拜访结果");
		visitResult.setName("visitResultList");
		tableHeaders.add(visitResult);
		
		TableHeader nextVisitTime = new TableHeader();
		nextVisitTime.setLabel("下次拜访时间");
		nextVisitTime.setName("nextVisitTimeStr");
		tableHeaders.add(nextVisitTime);


		TableHeader doctorCreateTime = new TableHeader();
		doctorCreateTime.setLabel("医生创建时间");
		doctorCreateTime.setName("doctorCreateTime");
		tableHeaders.add(doctorCreateTime);

//
//		TableHeader isBreakOff = new TableHeader();
//		isBreakOff.setLabel("是否脱落");
//		isBreakOff.setName("isBreakOff");
//		tableHeaders.add(isBreakOff);
		
//		TableHeader isTarget = new TableHeader();
//		isTarget.setLabel("是否是目标客户");
//		isTarget.setName("isTarget");
//		tableHeaders.add(isTarget);
		
		//////产品信息//////

		ProductTableHeader productInfo = new ProductTableHeader();
		productInfo.setLabel("产品信息");
		productInfo.setName("productInfos");
		tableHeaders.add(productInfo);
		List<TableHeader> info = productInfo.getInfo();
		
		TableHeader product = new TableHeader();
		product.setLabel("产品");
		product.setName("product");
		info.add(product);
		
		TableHeader isRecruit = new TableHeader();
		isRecruit.setLabel("是否招募");
		isRecruit.setName("isRecruit");
		info.add(isRecruit);
		
		TableHeader hcpPotential = new TableHeader();
		hcpPotential.setLabel("医生潜力");
		hcpPotential.setName("hcpPotential");
		info.add(hcpPotential);
		
//		TableHeader hcpLevel = new TableHeader();
//		hcpLevel.setLabel("客户等级");
//		hcpLevel.setName("hcpLevel");
//		info.add(hcpLevel);
		
		TableHeader isHasDrug = new TableHeader();
		isHasDrug.setLabel("是否有药");
		isHasDrug.setName("isHasDrug");
		info.add(isHasDrug);
		
	}


	/**
	 * 得到法定节假日和周六日，在此基础上加上设置频次
	 * @param list
	 * @param limitDays
	 * @return
	 */
	private List<VisitDoctorResponseBean> getVisitDoctorList(List<VisitDoctorResponseBean> list, Integer limitDays, Integer searchType){

		if (CollectionsUtil.isEmptyList(list)){
			return new ArrayList<>();
		}

		List<VisitDoctorResponseBean> handleList = new ArrayList<>();
		// 得到法定节假日
		List<String> holidayStrList = holidayMapper.getHolidayStrList();

		list.forEach(v ->{
			Long doctorId = v.getDoctorId();

			String maxVisitTime = v.getMaxVisitTime();
			int days = 0;
			int dayCount = 0;
			if (searchType == SearchTypeEnum.SEARCH_FIVE.getUserType()){
				days = DateUtil.getDaysWeekend(DateUtil.gettDateStrFromSpecialDate(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD), maxVisitTime, holidayStrList);
				dayCount = DateUtil.getDayCount(DateUtil.gettDateStrFromSpecialDate(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD), maxVisitTime);
			}else {
				days = DateUtil.getDaysWeekend(maxVisitTime, DateUtil.gettDateStrFromSpecialDate(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD), holidayStrList);
				dayCount = DateUtil.getDayCount(maxVisitTime, DateUtil.gettDateStrFromSpecialDate(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
			}
//			int days = DateUtil.getDaysWeekend(maxVisitTime, DateUtil.gettDateStrFromSpecialDate(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD), holidayStrList);
//			int dayCount = DateUtil.getDayCount(maxVisitTime, DateUtil.gettDateStrFromSpecialDate(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
			int diff = dayCount - days;

			if (searchType == SearchTypeEnum.SEARCH_TWO.getUserType()){
				if (diff >= v.getFrequency()){
					v.setVisitIntervalDay(dayCount);
					handleList.add(v);
				}
			}else if (searchType == SearchTypeEnum.SEARCH_FIVE.getUserType()){
				if (diff >=0 && diff <= v.getFrequency()){
					v.setVisitIntervalDay(dayCount);
					handleList.add(v);
				}
			}else {
				if (diff >= limitDays){
					v.setVisitIntervalDay(dayCount);
					handleList.add(v);
				}
			}

		});




		return handleList;
	}
}

