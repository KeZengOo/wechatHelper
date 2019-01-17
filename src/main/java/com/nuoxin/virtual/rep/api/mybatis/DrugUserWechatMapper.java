package com.nuoxin.virtual.rep.api.mybatis;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tiancun
 * @date 2018-10-08
 */
@Repository
public interface DrugUserWechatMapper {

    /**
     * 根据微信号查询总数
     * @param wechatNumber
     * @return
     */
    Integer getCountByWechat(@Param(value = "wechatNumber") String wechatNumber);

    /**
     * 根据微信号查询代表ID
     * @param wechatNumber
     * @return
     */
    Long getDrugUserIdByWechat(@Param(value = "wechatNumber") String wechatNumber);


    /**
     * 查询代表的微信
     * @param drugUserId
     * @return
     */
    List<String> getDrugUserWechat(@Param(value = "drugUserId") Long drugUserId);
}
