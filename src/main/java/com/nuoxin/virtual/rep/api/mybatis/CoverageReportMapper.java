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
     * @param startTime 开始时间 eg: 2019-01 未使用
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

    /**
     * 根据产品id和时间段查询招募医生数据
     * @param productId 产品id
     * @param startTime 开始时间 eg: 2019-01 未使用
     * @param endTime 结束时间 eg: 2019-05
     * @return list
     */
    List<CoverageReportPart> findCoverageRecruitList(@Param("productId") Long productId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 根据产品id和时间段查询电话覆盖医生数据
     * @param productId 产品id
     * @param startTime 开始时间 eg: 2019-01
     * @param endTime 结束时间 eg: 2019-05
     * @return list
     */
    List<CoverageReportPart> findCoverageCallList(@Param("productId") Long productId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 根据产品id和时间段查询微信覆盖 回复数、覆盖医生人数
     * @param productId 产品id
     * @param startTime 开始时间 eg: 2019-01
     * @param endTime 结束时间 eg: 2019-05
     * @return list
     */
    List<CoverageReportPart> findCoverageWeChatList(@Param("productId") Long productId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 根据产品id和时间段查询微信发送次数
     * @param productId 产品id
     * @param startTime 开始时间 eg: 2019-01
     * @param endTime 结束时间 eg: 2019-05
     * @return list
     */
    List<CoverageReportPart> findCoverageWeChatSend(@Param("productId") Long productId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 根据产品id和时间段查询会议数量
     * @param productId 产品id
     * @param startTime 开始时间 eg: 2019-01
     * @param endTime 结束时间 eg: 2019-05
     * @return list
     */
    List<CoverageReportPart> findCoverageMeeting(@Param("productId") Long productId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 根据产品id和时间段查询会议人数、人次、总时长
     * @param productId 产品id
     * @param startTime 开始时间 eg: 2019-01
     * @param endTime 结束时间 eg: 2019-05
     * @return list
     */
    List<CoverageReportPart> findCoverageMeetingList(@Param("productId") Long productId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 根据产品id和时间段查询阅读数据
     * @param productId 产品id
     * @param startTime 开始时间 eg: 2019-01
     * @param endTime 结束时间 eg: 2019-05
     * @return list
     */
    List<CoverageReportPart> findCoverageReadList(@Param("productId") Long productId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 根据产品id和时间段查询阅读数据
     * @param productId 产品id
     * @param startTime 开始时间 eg: 2019-01
     * @param endTime 结束时间 eg: 2019-05
     * @return list
     */
    List<CoverageReportPart> findCoverageSendList(@Param("productId") Long productId, @Param("startTime") String startTime, @Param("endTime") String endTime);

}
