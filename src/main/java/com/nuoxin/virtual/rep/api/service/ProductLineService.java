package com.nuoxin.virtual.rep.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.ProductLine;
import com.nuoxin.virtual.rep.api.mybatis.ProductLineMapper;
import com.nuoxin.virtual.rep.api.web.controller.response.product.ProductResponseBean;

/**
 * Created by fenggang on 8/4/17.
 */
@Service
public class ProductLineService {

    @Autowired
    private ProductLineMapper productLineMapper;
    @Autowired
    private DrugUserRepository drugUserRepository;

    /**
     * 获取产品
     * @param productId
     * @return
     */
    @Cacheable(value = "virtual_app_web_product", key = "'_'+#productId")
    public ProductLine findById(Long productId){
        ProductLine productLine = new ProductLine();
        productLine.setId(productId);
        return productLineMapper.selectOne(productLine);
    }

    /**
     * 获取产品id集合
     * @param leaderPath
     * @return
     */
    @Cacheable(value = "virtual_app_web_product", key = "'_ids_'+#leaderPath")
    public List<Long> getProductIds(String leaderPath){
        return productLineMapper.getProductIds(leaderPath);
    }

    /**
     * 获取产品集合
     * @param drugUserId
     * @return
     */
    public List<ProductResponseBean> getList(Long drugUserId){
        DrugUser drugUser = drugUserRepository.findFirstById(drugUserId);
        String leaderPath = drugUser.getLeaderPath();
        if (leaderPath == null){
            leaderPath = "";
        }
        leaderPath = leaderPath + "%";

        return productLineMapper.getList(leaderPath);
    }

}
