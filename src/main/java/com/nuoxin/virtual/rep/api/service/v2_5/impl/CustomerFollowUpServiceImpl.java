package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.mybatis.DoctorMapper;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserDoctorMapper;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserMapper;
import com.nuoxin.virtual.rep.api.mybatis.ProductLineMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CustomerFollowUpService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.CustomerFollowListRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.CustomerFollowListBean;

import shaded.org.apache.commons.lang3.StringUtils;

@Service
public class CustomerFollowUpServiceImpl implements CustomerFollowUpService{
	
	@Resource
	private DrugUserMapper drugUserMapper;
	@Resource
	private ProductLineMapper productLineMapper;
	@Resource
	private DrugUserDoctorMapper drugUserDoctorMapper;
	@Resource
	private DoctorMapper doctorMapper;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageResponseBean<List<CustomerFollowListBean>> list(CustomerFollowListRequestBean request) {
		PageResponseBean pageResponseBean = null;
		
		// 获取所有下属(直接&间接) virtualDrugUserIds
		List<Long> virtualDrugUserIds = this.getSubordinateIds(request.getLeaderPath());
		if (CollectionsUtil.isNotEmptyList(virtualDrugUserIds)) {
			// 根据 virtualDrugUserIds 从关联表中获取对应的 doctorIds ,已去重
			List<Long> doctorIds = drugUserDoctorMapper.getDoctorIdsByVirtualDrugUserIds(virtualDrugUserIds);
			// 根据 virtualDrugUserIds, doctorIds 及分页参数获取列表信息
			pageResponseBean = this.getCustomerFollows(doctorIds, virtualDrugUserIds, request);
		} 
		
		// 补偿
		if (pageResponseBean == null) {
			int count = 0;
			pageResponseBean = new PageResponseBean(request, count, Collections.emptyList());
		}

		return pageResponseBean;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageResponseBean<List<CustomerFollowListBean>> search(CustomerFollowListRequestBean request) {
		PageResponseBean pageResponseBean = null;
		
		// 获取所有下属(直接&间接) virtualDrugUserIds
		List<Long> virtualDrugUserIds = this.getSubordinateIds(request.getLeaderPath());
		if (CollectionsUtil.isNotEmptyList(virtualDrugUserIds)) {
			// 根据 搜索内容,virtualDrugUserIds 获取对应的 doctorIds
			List<Long> doctorIds = drugUserDoctorMapper.search(request.getSearch(), virtualDrugUserIds);
			if (CollectionsUtil.isNotEmptyList(doctorIds)) {
				pageResponseBean = this.getCustomerFollows(doctorIds, virtualDrugUserIds, request);
			}
		} 
		
		if (pageResponseBean == null) {
			int count = 0;
			pageResponseBean = new PageResponseBean(request, count, Collections.emptyList());
		}

		return pageResponseBean;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageResponseBean<List<CustomerFollowListBean>> screen(CustomerFollowListRequestBean request) {
		PageResponseBean pageResponseBean = null;
		
		List<Long> doctorIds = drugUserDoctorMapper.screen(request.getVirtualDrugUserIds(), request.getProductLineIds());
		if (CollectionsUtil.isNotEmptyList(doctorIds)) {
			pageResponseBean = this.getCustomerFollows(doctorIds, request.getVirtualDrugUserIds(), request);
		} 
		
		if (pageResponseBean == null) {
			int count = 0;
			pageResponseBean = new PageResponseBean(request, count, Collections.emptyList());
		}
		
		return pageResponseBean;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 获取所有下属(直接&间接) virtualDrugUserIds
	 * @param leaderPath 领导路径
	 * @return List<Long>
	 */
	private List<Long> getSubordinateIds(String leaderPath) {
		return drugUserMapper.getSubordinateIdsByLeaderPath(leaderPath);
	}
	
	/**
	 * 根据 doctorIds,virtualDrugUserIds及分页参数获取列表信息
	 * @param doctorIds
	 * @param virtualDrugUserIds
	 * @param pageRequestBean
	 * @return PageResponseBean<List<CustomerFollowListBean>>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private PageResponseBean<List<CustomerFollowListBean>> getCustomerFollows (List<Long> doctorIds ,List<Long> virtualDrugUserIds, PageRequestBean pageRequestBean) {
		int count = 0;
		List<CustomerFollowListBean> list = Collections.emptyList();
		
		if (CollectionsUtil.isNotEmptyList(doctorIds)) {
			count = doctorMapper.getDoctorsCount(virtualDrugUserIds, doctorIds);
			if(count > 0) {
				list = doctorMapper.getDoctors(virtualDrugUserIds, doctorIds, pageRequestBean.getCurrentSize(), pageRequestBean.getPageSize());
				if (CollectionsUtil.isNotEmptyList(list)) {
					list.forEach(doctor -> {
						String visitTime = doctor.getVisitTime();
						if (StringUtils.isNotBlank(visitTime)) {
							visitTime = visitTime.replace(".0", "");
							doctor.setVisitTime(visitTime);
							doctor.setDoctorId("H".concat(doctor.getDoctorId()));
						}
					});
				}
			}
		}
		
		return new PageResponseBean(pageRequestBean, count, list);
	}

}
