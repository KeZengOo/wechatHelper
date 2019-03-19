package com.nuoxin.virtual.rep.api.mybatis;

import org.apache.ibatis.annotations.Param;

/**
 * 白名单类
 * @author tiancun
 * @date 2019-03-19
 */
public interface ShortUrlWhiteMapper {

    /**
     * 得到总数，判断白名单是否存在
     * @param url
     * @return
     */
    Integer getUrlWhiteCount(@Param(value = "url") String url);

}
