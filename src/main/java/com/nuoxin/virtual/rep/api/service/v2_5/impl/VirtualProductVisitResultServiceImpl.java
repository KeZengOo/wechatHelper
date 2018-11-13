package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.nuoxin.virtual.rep.api.common.constant.VisitResultConstant;
import com.nuoxin.virtual.rep.api.entity.v2_5.ProductVisitResultParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.ProductVisitResultResponse;
import com.nuoxin.virtual.rep.api.mybatis.VirtualProductVisitResultMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualProductVisitResultService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 拜访结果服务
 */
@Service
public class VirtualProductVisitResultServiceImpl implements VirtualProductVisitResultService {

	@Resource
	private VirtualProductVisitResultMapper virtualProductVisitResultMapper;

	/**
	 * 根据产品id查询拜访结果列表
	 * @param productId
	 * @return
	 */
	@Override
	public List<ProductVisitResultResponse> selectVisitResultList(Long productId){
		List<ProductVisitResultResponse> beans=virtualProductVisitResultMapper.selectVisitResultList(productId);
		if(!CollectionsUtil.isEmptyList(beans)){
			beans.forEach(x->{
				if(StringUtils.isNotBlank(x.getVisitType())){
					List<String> stringList = Arrays.asList(x.getVisitType().split(","));
					if(!CollectionsUtil.isEmptyList(stringList)){
						x.setVisitTypeList(stringList.stream().map(Integer::parseInt).collect(Collectors.toList()));
					}
				}
			});
		}
		return beans;
	}

	/**
	 * 根据产品id查询拜访结果列表
	 * @param params
	 * @return
	 */
	@Override
	public int batchInsert(ProductVisitResultParams params){
		if(null!=params
				&&!CollectionsUtil.isEmptyList(params.getVisitTypeList())
				&&StringUtils.isNotBlank(params.getVisitResult())
				&&null!=params.getProductId()){
			//校验是否产品下是否已经存在过
			Integer flag=virtualProductVisitResultMapper.checkVisitResult(params.getProductId(),params.getVisitResult());
			if(flag==null){
				List<ProductVisitResultParams> list=new ArrayList<>();
				params.setVisitType(StringUtils.join(params.getVisitTypeList(), ","));
				list.add(params);
				return virtualProductVisitResultMapper.batchInsert(list);
			}else{
				return -1;
			}
		}
		return 0;
	}

	/**
	 * 修改拜访结果
	 * @param params
	 * @return
	 */
	@Override
	public int update(ProductVisitResultParams params) {
		if(null!=params
				&&!CollectionsUtil.isEmptyList(params.getVisitTypeList())
				&&StringUtils.isNotBlank(params.getVisitResult())
				&&null!=params.getId()
				&&null!=params.getProductId()){
			boolean tag=false;
			//将要修改的词是否在该产品中存在
			Integer flag=virtualProductVisitResultMapper.checkVisitResult(params.getProductId(),params.getVisitResult());
			if(flag!=null){
				//查询修改前的拜访内容
				ProductVisitResultResponse visitResult=virtualProductVisitResultMapper.selectVisitResultById(params.getId());
				//要修改的跟修改前一样允许修改
				if(null!=visitResult&&StringUtils.isNotBlank(visitResult.getVisitResult())
						&&params.getVisitResult().equals(visitResult.getVisitResult())){
					tag=true;
				}
			}else{
				tag=true; //不存在允许修改
			}
			if(tag){
				params.setVisitType(StringUtils.join(params.getVisitTypeList(), ","));
				//返回修改条数
				return virtualProductVisitResultMapper.update(params);
			}
		}
		return 0;
	}

	/**
	 * 根据产品初始化
	 * @param productId
	 * @return
	 */
	@Override
	public int visitResultInitialization(Long productId) {
		Integer flag=virtualProductVisitResultMapper.checkVisitResult(productId,null);
		if(flag==null) {
			List<ProductVisitResultParams> list=new ArrayList<>();
			ProductVisitResultParams t=getVisitResult(VisitResultConstant.CALLLATER,"1",productId);
			ProductVisitResultParams t1=getVisitResult(VisitResultConstant.TRANSFERSUCCESS,"1,2",productId);
			ProductVisitResultParams t2=getVisitResult(VisitResultConstant.TYPINGSUCCESS,"1,2,3",productId);
			ProductVisitResultParams t3=getVisitResult(VisitResultConstant.SERVICEPHONE,"1,2,3",productId);
			ProductVisitResultParams t4=getVisitResult(VisitResultConstant.DOCTORREFUSED,"1",productId);
			ProductVisitResultParams t5=getVisitResult(VisitResultConstant.NONCONTACT,"",productId);
			list.add(t);
			list.add(t1);
			list.add(t2);
			list.add(t3);
			list.add(t4);
			list.add(t5);
			return virtualProductVisitResultMapper.batchInsert(list);
		}
		return 0;
	}

	private ProductVisitResultParams getVisitResult(String visitResult,String visitType,Long productId) {
		ProductVisitResultParams t=new ProductVisitResultParams();
		t.setVisitType(visitType);
		t.setProductId(productId);
		t.setVisitResult(visitResult);
		return t;
	}
}
