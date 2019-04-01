package com.nuoxin.virtual.rep.api.slave.mybatis;

import com.nuoxin.virtual.rep.api.entity.ProductLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wujiang
 * @date 2019-03-25
 */
public interface ProductSlaveMapper {

    /**
     * 获取从库产品最新数据时间
     * @return String
     */
    String getProductNewCreateTime();

    /**
     * 同步产品数据到从库
     * @param list
     * @return boolean
     */
    boolean syncProductList(List<ProductLine> list);

    /**
     * 同步产品数据到从库 update
     * @param name
     * @param descriptions
     * @param code
     * @return boolean
     */
    boolean syncUpdateProductList(@Param(value = "name") String name,@Param(value = "descriptions") String descriptions,@Param(value = "code") String code);
}
