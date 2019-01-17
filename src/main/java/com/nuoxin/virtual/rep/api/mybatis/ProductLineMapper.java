package com.nuoxin.virtual.rep.api.mybatis;



import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoxin.virtual.rep.api.common.util.MyMapper;
import com.nuoxin.virtual.rep.api.entity.ProductLine;
import com.nuoxin.virtual.rep.api.web.controller.response.product.ProductResponseBean;

/**
 * 获取产品mapper
 * Created by fenggang on 8/4/17.
 */
public interface ProductLineMapper extends MyMapper<ProductLine> {

    /**
     * 根据leaderpath获取产品列表
     * @param leaderPath
     * @return
     */
    List<ProductLine> findByLeaderPath(String leaderPath);

    /**
     * 根据leaderpath获取产品id集合
     * @param leaderPath
     * @return
     */
    List<Long> getProductIds(String leaderPath);


    /**
     * 根据leaderpath获取产品列表
     * @param leaderPath
     * @param doctorId
     * @return
     */
    List<ProductResponseBean> getList(@Param(value = "leaderPath") String leaderPath,@Param(value = "doctorId") Long doctorId);
    
	List<ProductResponseBean> getListByDrugUserId(@Param(value = "virtualDrugUserIds") List<Long> virtualDrugUserIds);


}
