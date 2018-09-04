package com.nuoxin.virtual.rep.api.service.v2_5;

import java.util.List;

import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.product.ProductResponseBean;

public interface DrugUserService {

	/**
     * 根据 leaderpath, productId 查询下属销售
     * @param leaderPath 
     * @return 返回 List<DrugUserResponseBean>
     */
	List<DrugUserResponseBean> getSubordinates(String leaderPath);
	
	/**
	 * 根据 leaderPath 获取产品线信息
	 * @param drugUserId
	 * @return
	 */
	List<ProductResponseBean>getProductsByDrugUserId(String leaderPath);

}
