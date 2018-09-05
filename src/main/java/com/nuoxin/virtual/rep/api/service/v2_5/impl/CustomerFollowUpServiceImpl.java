package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.CustomerFollowListBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.TableHeader;

@Service
public class CustomerFollowUpServiceImpl implements CustomerFollowUpService{
	private static List<TableHeader> tableHeaders = new ArrayList<>(15);
	
	@Resource
	private DrugUserMapper drugUserMapper;
	@Resource
	private ProductLineMapper productLineMapper;
	@Resource
	private DrugUserDoctorMapper drugUserDoctorMapper;
	@Resource
	private DoctorMapper doctorMapper;
	
	static {
		TableHeader header = new TableHeader();
		header.setLabel("客户姓名");
		header.setName("doctorName");

		TableHeader header2 = new TableHeader();
		header2.setLabel("手机号");
		header2.setName("doctorMobile");

		tableHeaders.add(header);
		tableHeaders.add(header2);
		
		// TODO 补全
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageResponseBean<List<CustomerFollowListBean>> list(CustomerFollowListRequestBean request) {
		PageResponseBean pageResponseBean = null;
		int count = 0;
		
		// 获取所有下属(直接&间接) virtualDrugUserIds
		List<Long> virtualDrugUserIds = this.getSubordinateIds(request.getLeaderPath());
		if (CollectionsUtil.isNotEmptyList(virtualDrugUserIds)) {
			count = this.getDoctorsCount(virtualDrugUserIds);
			pageResponseBean = this.getDoctorsList(count, null, virtualDrugUserIds, request);
		} 
		
		// 补偿
		if (pageResponseBean == null) {
			count = 0;
			pageResponseBean = new PageResponseBean(request, count, Collections.emptyList());
		}
		pageResponseBean.setTableHeaders(tableHeaders);
		
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
				pageResponseBean = this.getDoctorsList(doctorIds.size(), doctorIds, virtualDrugUserIds, request);
			}
		} 
		
		if (pageResponseBean == null) {
			int count = 0;
			pageResponseBean = new PageResponseBean(request, count, Collections.emptyList());
		}
		pageResponseBean.setTableHeaders(tableHeaders);
		
		return pageResponseBean;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageResponseBean<List<CustomerFollowListBean>> screen(CustomerFollowListRequestBean request) {
		PageResponseBean pageResponseBean = null;
		
		List<Long> doctorIds = drugUserDoctorMapper.screen(request.getVirtualDrugUserIds(), request.getProductLineIds());
		if (CollectionsUtil.isNotEmptyList(doctorIds)) {
			pageResponseBean = this.getDoctorsList(doctorIds.size(), doctorIds, request.getVirtualDrugUserIds(), request);
		} 
		
		if (pageResponseBean == null) {
			int count = 0;
			pageResponseBean = new PageResponseBean(request, count, Collections.emptyList());
		}
		pageResponseBean.setTableHeaders(tableHeaders);
		
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
	
	private int getDoctorsCount(List<Long> virtualDrugUserIds) {
		return doctorMapper.getDoctorsCount(virtualDrugUserIds);
	}
	/**
	 * 根据 doctorIds,virtualDrugUserIds及分页参数获取列表信息
	 * @param doctorIds
	 * @param virtualDrugUserIds
	 * @param pageRequestBean
	 * @return PageResponseBean<List<CustomerFollowListBean>>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private PageResponseBean<List<CustomerFollowListBean>> getDoctorsList(int count, List<Long> doctorIds, List<Long> virtualDrugUserIds,
			PageRequestBean pageRequestBean) {

		List<CustomerFollowListBean> list = null;
		if (count > 0) {
			list = doctorMapper.getDoctors(doctorIds, virtualDrugUserIds, pageRequestBean.getCurrentSize(),
					pageRequestBean.getPageSize());
			if (CollectionsUtil.isNotEmptyList(list)) {
				list.forEach(doctor -> {
					Date visitTime = doctor.getVisitTime();
					if (visitTime != null) {
						long lastVisitTimeInterval = System.currentTimeMillis() - visitTime.getTime();
						lastVisitTimeInterval = lastVisitTimeInterval / 60000;
						doctor.setLastVisitTimeInterval(lastVisitTimeInterval);
					} else {
						doctor.setLastVisitTimeInterval(-1);
					}
				});
				
				// TODO 补全其它信息,如:产品相关信息,自定义信息
			}
		}
		
		if(CollectionsUtil.isEmptyList(list)) {
			list = Collections.emptyList();
		}

		return new PageResponseBean(pageRequestBean, count, list);
	}

}
