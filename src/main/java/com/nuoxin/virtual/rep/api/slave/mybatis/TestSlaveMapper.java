package com.nuoxin.virtual.rep.api.slave.mybatis;

import org.apache.ibatis.annotations.Param;

/**
 * @author wujiang
 * @date 2019-03-20
 */
public interface TestSlaveMapper {

    /**
     * 测试根据产品ID获取产品名称
     * @return String
     */
    String getProductName(@Param(value = "productId") Long productId);

}
