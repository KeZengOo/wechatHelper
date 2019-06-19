package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v3_0.CoverageReportPart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName CoverageReportMapper
 * @Description 拜访数据汇总-覆盖月报Mapper
 * @Author dangjunhui
 * @Date 2019/6/14 17:54
 * @Version 1.0
 */
@Repository
public interface CoverageReportMapper {

    /**
     * 根据产品id查询招募医生数和医院数
     * @param productId 产品id
     * @return list
     */
    CoverageReportPart getRecruitCount(@Param("productId") Long productId);

    /**
     * 根据产品id和时间段查询招募医生和医院数据
     * @param productId 产品id
     * @param startTime 开始时间 eg: 2019-01
     * @param endTime 结束时间 eg: 2019-05
     * @return list
     */
    List<CoverageReportPart> findRecruitList(@Param("productId") Long productId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 根据产品id和时间段查询覆盖医生和医院数据
     * @param productId 产品id
     * @param startTime 开始时间 eg: 2019-01
     * @param endTime 结束时间 eg: 2019-05
     * @return list
     */
    List<CoverageReportPart> findCoverageList(@Param("productId") Long productId, @Param("startTime") String startTime, @Param("endTime") String endTime);



}