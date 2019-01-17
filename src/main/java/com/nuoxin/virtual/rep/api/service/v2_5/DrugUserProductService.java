package com.nuoxin.virtual.rep.api.service.v2_5;

import java.util.List;

import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.product.ProductResponseBean;

/**
 * 虚拟代表业产品务接口
 * @author xiekaiyu
 */
public interface DrugUserProductService {

	/**
     * 根据 leaderPath 获取所有下属医药代表信息
     * @param leaderPath 
     * @return 返回 List<DrugUserResponseBean>
     */
	List<DrugUserResponseBean> getSubordinates(String leaderPath);
	
	/**
	 * 根据 leaderPath 获取获取所有下属代表对应的产品线信息
	 * @param drugUserId
	 * @return 返回 List<ProductResponseBean>
	 */
	List<ProductResponseBean>getProductsByDrugUserId(String leaderPath);

}
