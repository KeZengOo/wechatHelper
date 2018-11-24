package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.nuoxin.virtual.rep.api.mybatis.DrugUserDoctorQuateResultMapper;
import com.nuoxin.virtual.rep.api.mybatis.VirtualDoctorCallInfoMapper;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.CustomerFollowUpPageResponseBean;
import com.nuoxin.virtual.rep.api.mybatis.DoctorMapper;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserDoctorQuateMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v2_5.CustomerFollowUpService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.ListRequestBean;
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

	@Resource
	private DrugUserDoctorQuateMapper drugUserDoctorQuateMapper;

	@Resource
	private VirtualDoctorCallInfoMapper virtualDoctorCallInfoMapper;

	@Resource
	private DrugUserDoctorQuateResultMapper drugUserDoctorQuateResultMapper;

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
	public CustomerFollowUpPageResponseBean<List<CustomerFollowListBean>> list(ListRequestBean pageRequest, String leaderPath) {
		int count = 0;
		CustomerFollowUpPageResponseBean pageResponse = null;
		// 获取所有下属(直接&间接) virtualDrugUserIds
		List<Long> virtualDrugUserIds = commonService.getSubordinateIds(leaderPath);
		if (CollectionsUtil.isNotEmptyList(virtualDrugUserIds)) {
			count = doctorMapper.getListCount(virtualDrugUserIds, null, null);
			if(count > 0) {
				int currentSize = pageRequest.getCurrentSize();
				int pageSize = pageRequest.getPageSize();
				long startTime = System.currentTimeMillis();
				Integer minValue = Integer.MIN_VALUE;
				List<CustomerFollowListBean> list = doctorMapper.getList(virtualDrugUserIds, currentSize, pageSize, null, null, pageRequest.getOrder(), minValue);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public CustomerFollowUpPageResponseBean<List<CustomerFollowListBean>> search(SearchRequestBean request, String leaderPath) {
		CustomerFollowUpPageResponseBean pageResponseBean = null;
		int count = 0;
		 // 获取所有下属(直接&间接) virtualDrugUserIds
		List<Long> productLineIds = request.getProductLineIds();
		List<Long> virtualDrugUserIds = commonService.getSubordinateIds(leaderPath);
		if (CollectionsUtil.isNotEmptyList(virtualDrugUserIds)) {
			String search = request.getSearch();
			count = doctorMapper.getListCount(virtualDrugUserIds, search, null);
			if(count > 0) {
				int currentSize = request.getCurrentSize();
				int pageSize = request.getPageSize();
				Integer minValue = Integer.MIN_VALUE;
				List<CustomerFollowListBean> list = doctorMapper.getList(virtualDrugUserIds, currentSize, pageSize, search, null, request.getOrder(), minValue);
				// TODO 填充上产品
				pageResponseBean = this.getDoctorsList(count, list, request);
				this.fillProductInfos(list, productLineIds);
			}
		} 
		
		this.compensate(request, pageResponseBean);
		return pageResponseBean;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public CustomerFollowUpPageResponseBean<List<CustomerFollowListBean>> screen(ScreenRequestBean request) {
		CustomerFollowUpPageResponseBean pageResponseBean = null;
		List<Long> virtualDrugUserIds = request.getVirtualDrugUserIds();
		List<Long> productLineIds = request.getProductLineIds();
		
		int count = doctorMapper.getListCount(virtualDrugUserIds, null, productLineIds);
		if(count > 0 ) {
			int currentSize = request.getCurrentSize();
			int pageSize = request.getPageSize();
			Integer minValue = Integer.MIN_VALUE;
			List<CustomerFollowListBean> list = doctorMapper.getList(virtualDrugUserIds, currentSize, pageSize, null,
					productLineIds, request.getOrder(), minValue);
			pageResponseBean = this.getDoctorsList(count, list, request);
		}
		
		this.compensate(request, pageResponseBean);
		return pageResponseBean;
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
			list.forEach(doctor -> {
				this.alterCustomerFollowListBean(doctor, productLineIds);
				// TODO 补充对应的产品信息 @田存
				if (CollectionsUtil.isNotEmptyList(productLineIds)){
					this.fillProductInfos(customerFollowListBeanList, productLineIds);
				}

			});
		}

		if (CollectionsUtil.isEmptyList(list)) {
			list = Collections.emptyList();
		}

		return new CustomerFollowUpPageResponseBean(pageRequestBean, count, list);
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


		List<CallTelephoneReponseBean> telephoneCallCount = virtualDoctorCallInfoMapper.getTelephoneCallCount(doctor.getDoctorId());
		if (CollectionsUtil.isNotEmptyList(telephoneCallCount)){
			doctor.setMobiles(telephoneCallCount);
		}


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

		List<String> visitResult = drugUserDoctorQuateResultMapper.getVisitResult(doctor.getDoctorId(), productLineIds);
		if (CollectionsUtil.isNotEmptyList(visitResult)){
			doctor.setVisitResultList(visitResult);
		}


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
}
