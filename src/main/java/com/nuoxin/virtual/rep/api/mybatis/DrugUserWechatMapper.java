package com.nuoxin.virtual.rep.api.mybatis;

import org.apache.ibatis.annotations.Param;

/**
 * @author tiancun
 * @date 2018-10-08
 */
public interface DrugUserWechatMapper {

    /**
     * 根据微信号查询总数
     * @param wechatNumber
     * @return
     */
    Integer getCountByWechat(@Param(value = "wechatNumber") String wechatNumber);
}
