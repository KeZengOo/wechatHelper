package com.nuoxin.virtual.rep.api.mybatis;



import java.util.List;

import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualWechatDate;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualWechatVisitCountAndCycleConfigParams;
import org.apache.ibatis.annotations.Param;

import com.nuoxin.virtual.rep.api.common.util.MyMapper;
import com.nuoxin.virtual.rep.api.entity.ProductLine;
import com.nuoxin.virtual.rep.api.web.controller.response.product.ProductResponseBean;

/**
 * 获取产品mapper
 * Created by fenggang on 8/4/17.
 */
public interface ProductLineMapper extends MyMapper<ProductLine> {

    /**
     * 根据leaderpath获取产品列表
     * @param leaderPath
     * @return
     */
    List<ProductLine> findByLeaderPath(String leaderPath);


    /**
     * 根据代表ID获取产品列表
     * @param drugUserId
     * @return
     */
    List<ProductLine> findByDrugUserId(Long drugUserId);

    /**
     * 根据leaderpath获取产品id集合
     * @param leaderPath
     * @return
     */
    List<Long> getProductIds(String leaderPath);


    /**
     * 根据leaderpath获取产品列表
     * @param leaderPath
     * @param doctorId
     * @return
     */
    List<ProductResponseBean> getList(@Param(value = "leaderPath") String leaderPath,@Param(value = "doctorId") Long doctorId);
    
	List<ProductResponseBean> getListByDrugUserId(@Param(value = "virtualDrugUserIds") List<Long> virtualDrugUserIds);

    /**
     * 公共池的产品列表
     * @return
     */
    List<ProductResponseBean> getCommonPoolProductList();



    /**
     * 在客户聊天记录中，当前代表在过去N天内是否与医生有微信聊天记录
     * @param drugUserId
     * @param doctorId
     * @param dayNum
     * @return count
     */
    Integer wechatChatRecordIsExist(@Param(value = "drugUserId") Long drugUserId,@Param(value = "doctorId") Long doctorId, @Param(value = "dayNum") Integer dayNum);

    /**
     * 一个自然天内，存在多少条微信的拜访记录
     * @param drugUserId
     * @param doctorId
     * @return count
     */
    Integer wechatVisitLogCountOneDay(@Param(value = "drugUserId") Long drugUserId,@Param(value = "doctorId") Long doctorId);

    /**
     * 微信聊天记录天数和一天内可添加微信拜访记录的配置表
     * @return VirtualWechatVisitCountAndCycleConfigParams
     */
    VirtualWechatVisitCountAndCycleConfigParams virtualWechatVisitCountAndCycleConfig ();

    /**
     * 在客户聊天记录中，当前代表在过去N天内是否与医生有微信聊天记录的日期
     * @param drugUserId
     * @param doctorId
     * @param dayNum
     * @return list
     */
    List<VirtualWechatDate> wechatChatRecordDate(@Param(value = "drugUserId") Long drugUserId,@Param(value = "doctorId") Long doctorId, @Param(value = "dayNum") Integer dayNum);

    /**
     * 当前代表在过去N天内是否存在微信拜访记录日期
     * @param drugUserId
     * @param doctorId
     * @param dayNum
     * @param productId
     * @return list
     */
    List<VirtualWechatDate> wechatVisitLogsDate(@Param(value = "drugUserId") Long drugUserId,@Param(value = "doctorId") Long doctorId,@Param(value = "productId") Integer productId, @Param(value = "dayNum") Integer dayNum);


    /**
     * 得到产品名称
     * @param productId
     * @return
     */
    String getProductName(Long productId);

    /**
     * 根据时间获取大于该时间的产品list
     */
    List<ProductLine> getProductListByCreateTime(@Param(value = "createTime") String createTime);

    /**
     * 根据更新时间获取大于该时间的产品list
     */
    List<ProductLine> getProductListByUpdateTime(@Param(value = "updateTime") String createTime);

    /**
     * 得到全部的产品
     * @return
     */
    List<ProductLine> getAllProductLineList();


    /**
     * 查询总数，用来判断代表是否在某个产品名下
     * @param email
     * @param productId
     * @return
     */
    Integer getProductUserCount(@Param(value = "email") String email,@Param(value = "productId") Long productId);




}
