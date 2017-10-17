package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.ProductLine;
import com.nuoxin.virtual.rep.api.mybatis.ProductLineMapper;
import com.nuoxin.virtual.rep.api.web.controller.request.product.ProductRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.product.ProductResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fenggang on 8/4/17.
 */
@Service
public class ProductLineService {

    @Autowired
    private ProductLineMapper productLineMapper;

    @Autowired
    private DrugUserRepository drugUserRepository;

    @Cacheable(value = "virtual_app_web_product", key = "'_'+#productId")
    public ProductLine findById(Long productId){
        ProductLine productLine = new ProductLine();
        productLine.setId(productId);
        return productLineMapper.selectOne(productLine);
    }

    @Cacheable(value = "virtual_app_web_product", key = "'_ids_'+#leaderPath")
    public List<Long> getProductIds(String leaderPath){
        return productLineMapper.getProductIds(leaderPath);
    }


    public List<ProductResponseBean> getList(Long drugUserId){
        DrugUser drugUser = drugUserRepository.findFirstById(drugUserId);
        String leaderPath = drugUser.getLeaderPath();
        if (leaderPath == null){
            leaderPath = "";
        }
        leaderPath = leaderPath + "%";

        List<ProductResponseBean> list = productLineMapper.getList(leaderPath);

        return list;

    }


}
