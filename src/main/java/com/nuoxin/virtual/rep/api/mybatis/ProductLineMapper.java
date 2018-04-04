package com.nuoxin.virtual.rep.api.mybatis;



import com.nuoxin.virtual.rep.api.common.util.MyMapper;
import com.nuoxin.virtual.rep.api.entity.ProductLine;
import com.nuoxin.virtual.rep.api.web.controller.request.product.ProductRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.product.ProductResponseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     * @return
     */
    List<ProductResponseBean> getList(@Param(value = "leaderPath") String leaderPath);
}
