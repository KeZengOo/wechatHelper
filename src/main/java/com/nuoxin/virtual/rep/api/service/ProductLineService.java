package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.entity.ProductLine;
import com.nuoxin.virtual.rep.api.mybatis.ProductLineMapper;
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
}
