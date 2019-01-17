package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.entity.v2_5.ProductVisitResultParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.ProductVisitResultResponse;

import java.util.List;

public interface VirtualProductVisitResultService {

	/**
	 * 根据产品id查询拜访结果列表
	 * @param productId
	 * @return
	 */
	List<ProductVisitResultResponse> selectVisitResultList(Long productId);

	/**
	 * 批量保存拜访结果列表
	 * @param params
	 * @return
	 */
	int batchInsert(ProductVisitResultParams params);

	/**
	 * 修改拜访结果
	 * @param param
	 * @return
	 */
	int update(ProductVisitResultParams param);

	/**
	 * 根据产品初始化数据
	 * @param productId
	 * @return
	 */
    int visitResultInitialization(Long productId);
}
