package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.slave.mybatis.TestSlaveMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestSlaveService {

    @Resource
    private TestSlaveMapper testSlaveMapper;

    /**
     * 测试根据产品ID获取产品名称
     * @return String
     */
   public String getProductName(Long productId){

        String name = testSlaveMapper.getProductName(productId);

        return name;
    }
}
