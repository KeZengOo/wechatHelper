package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v3_0.VisitDataBase;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.VisitDataRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName VisitingDataMapper
 * @Description 拜访数据Mapper
 * @Author dangjunhui
 * @Date 2019/5/14 11:30
 * @Version 1.0
 */
@Repository
public interface VisitingDataMapper {

    /**
     * 查询符合条件的总记录数
     * @Author dangjunhui
     * @param leaderPath 当前登录用户leaderPath
     * @param request 请求参数
     * @return int
     */
    int getVisitDataCount(@Param("leaderPath") String leaderPath, @Param("key") VisitDataRequest request);

    /**
     * 查询符合条件的拜访数据信息
     * @Author dangjunhui
     * @param leaderPath 当前登录用户leaderPath
     * @param request 请求参数
     * @return list
     */
    List<VisitDataBase> getVisitDataBaseInfo(@Param("leaderPath") String leaderPath, @Param("key") VisitDataRequest request);


}
