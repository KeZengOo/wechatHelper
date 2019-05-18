package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v3_0.VisitDataBase;
import com.nuoxin.virtual.rep.api.entity.v3_0.VisitDataPart;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.VisitDataParam;
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

    /**
     * 根据产品id和销售代表id查询拜访医生数
     * @param param 参数对象
     * @return list
     */
    List<VisitDataPart> getVisitHcpCount(@Param("key") VisitDataParam param);

    /**
     * 根据产品id和销售代表id查询接触&成功医生数
     * @param type 1 接触医生数 2 成功医生数
     * @param param 参数对象
     * @return list
     */
    List<VisitDataPart> getContactAndSuccessHcpCount(@Param("type") Integer type, @Param("key") VisitDataParam param);

    /**
     * 根据产品id和销售代表id查询招募医生数
     * @param param 参数对象
     * @return list
     */
    List<VisitDataPart> getRecruitHcpCount(@Param("key") VisitDataParam param);

    /**
     * 根据产品id和销售代表id查询覆盖医生——需在代码中去重
     * @param param 参数对象
     * @return list
     */
    List<VisitDataPart> getCoverHcpCount(@Param("key") VisitDataParam param);

    /**
     * 根据产品id和销售代表id查询单次通话时长大于75s的医生数量
     * @param param 参数对象
     * @return list
     */
    List<VisitDataPart> getCallTimeMore75Count(@Param("key") VisitDataParam param);

    /**
     * 根据产品id和销售代表id查询微信回复医生数量
     * @param param 参数对象
     * @return list
     */
    List<VisitDataPart> getWeChatReplyCount(@Param("key") VisitDataParam param);

    /**
     * 根据产品id和销售代表id查询参会医生数量
     * @param param 参数对象
     * @return list
     */
    List<VisitDataPart> getAttendMeetingCount(@Param("key") VisitDataParam param);

    /**
     * 根据产品id和销售代表id查询接通数量、呼出数量
     * @param type 类型用于区分查询的是 type!=1 接通数量 type=1 呼出数量
     * @param param 参数对象
     * @return list
     */
    List<VisitDataPart> getConnectCount(@Param("type") Integer type, @Param("key") VisitDataParam param);

    /**
     * 根据产品id和销售代表id查询总时长
     * @param param 参数对象
     * @return list
     */
    List<VisitDataPart> getOutboundTimeCount(@Param("key") VisitDataParam param);

    /**
     * 根据产品id和销售代表id查询微信发送人数
     * @param param 参数对象
     * @return list
     */
    List<VisitDataPart> getWeChatSendCount(@Param("key") VisitDataParam param);

    /**
     * 根据产品id和销售代表id查询微信添加数
     * @param param 参数对象
     * @return list
     */
    List<VisitDataPart> getWeChatAddCount(@Param("key") VisitDataParam param);

    /**
     * 根据产品id和销售代表id查询对应的发送次数
     * @param param 参数对象
     * @return list
     */
    List<VisitDataPart> getContentPartSendCount(@Param("key") VisitDataParam param);

    /**
     * 根据产品id和销售代表id查询对应的阅读时长和阅读人数
     * @param param 参数对象
     * @return list
     */
    List<VisitDataPart> getContentPartTimeAndCount(@Param("key") VisitDataParam param);

    /**
     * 根据产品id和销售代表id查询阅读时长大于50s的医生
     * @param param 参数对象
     * @return list
    */
    List<VisitDataPart> getContentPartMoreThen50s(@Param("key") VisitDataParam param);

}
