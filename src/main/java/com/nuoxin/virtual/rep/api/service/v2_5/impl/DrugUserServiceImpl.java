package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.mybatis.DoctorMapper;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserMapper;
import com.nuoxin.virtual.rep.api.mybatis.ProductLineMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.DrugUserService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.product.ProductResponseBean;

/**
 * 虚拟代表业务实现类
 * @author xiekaiyu
 */
@Service
public class DrugUserServiceImpl implements DrugUserService{
	
	@Resource
	private DrugUserMapper drugUserMapper;
	@Resource
	private ProductLineMapper productLineMapper;
	@Resource
	private DoctorMapper doctorMapper;

	@Override
	public List<DrugUserResponseBean> getSubordinates(String leaderPath) {
		List<DrugUserResponseBean> list = drugUserMapper.getSubordinatesByLeaderPath(leaderPath);
		if(CollectionsUtil.isEmptyList(list)) {
			list = new ArrayList<>(1);
		}
		
		DrugUserResponseBean headElement = new DrugUserResponseBean();
		headElement.setId(-1L);
		headElement.setName("全部");
		list.add(0, headElement);
		
		return list;
	}

	@Override
	public List<ProductResponseBean> getProductsByDrugUserId(String leaderPath) {
		List<ProductResponseBean> list;
		List<Long> virtualDrugUserIds = drugUserMapper.getSubordinateIdsByLeaderPath(leaderPath);
		if(CollectionsUtil.isNotEmptyList(virtualDrugUserIds)) {
			list = productLineMapper.getListByDrugUserId(virtualDrugUserIds);
		} else {
			list = new ArrayList<>(1);
		}
		
		ProductResponseBean headElement = new ProductResponseBean();
		headElement.setProductId(-1L);
		headElement.setProductName("全部");
		list.add(0, headElement);
		
		return list;
	}
	
}
