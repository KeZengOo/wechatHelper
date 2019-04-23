package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.common.util.MyMapper;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v2_5.ProductDO;
import com.nuoxin.virtual.rep.api.web.controller.request.QueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorResponseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 药企用户mapper
 * Created by fenggang on 10/18/17.
 */
public interface DrugUserMapper extends MyMapper<DrugUser> {

    /**
     * 根据条件查询医生
     * @param bean
     * @return
     */
    List<DoctorResponseBean> doctorPage(QueryRequestBean bean);

    /**
     * 得到指定产品的线上代表
     * @param productId
     * @return
     */
    List<DrugUserResponseBean> getOnlineDrugUserList(@Param(value = "productId") Long productId);

    Integer doctorPageCount(QueryRequestBean bean);

    /**
     * 根据leaderpath, productId查询下属销售
     * @param leaderPath
     * @param productId
     * @return
     */
    List<DrugUserResponseBean> relationDrugUser(@Param("leaderPath") String leaderPath,@Param("productId") Long productId);
    
    ////////////////////////////////////////以下是V2.5用到的//////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 根据 leaderPath 查询下属销售
     * @param leaderPath
     * @return 有结果返回 List<DrugUserResponseBean>,否则返回[] 
     */
	List<DrugUserResponseBean> getSubordinatesByLeaderPath(@Param("leaderPath") String leaderPath);
	
	/**
     * 根据 leaderPath 获取所有下属(直接&间接) virtualDrugUserIds
     * @param leaderPath
     * @return 有结果返回 List<Long>,否则返回[] 
     */
	List<Long> getSubordinateIdsByLeaderPath(@Param("leaderPath") String leaderPath);


    /**
     * 查询名下所有的产品
     * @param leaderPath
     * @return
     */
	List<ProductDO> getProductList(@Param(value = "leaderPath") String leaderPath);

    /**
     * 查询销售名下所有设置动态字段的产品
     * @param leaderPath
     * @return
     */
    List<ProductDO> getSetDynamicFieldProductList(@Param(value = "leaderPath") String leaderPath);

    /**
     * 查询销售名下某个医生所有设置动态字段的产品
     * @param leaderPath
     * @param doctorId
     * @return
     */
    List<ProductDO> getSetDynamicFieldProductListByDoctorId(@Param(value = "leaderPath") String leaderPath,@Param(value = "doctorId") Long doctorId);


    /**
     * 根据ID获得已经拼上%的leaderPath
     * @param id
     * @return
     */
	String getLeaderPathById(@Param(value = "id") Long id);


    /**
     * 根据邮箱查询代表的产品ID列表
     * @param email
     * @return
     */
	List<Long> getProductIdListByEmail(@Param(value = "email") String email);


    /**
     * 根据销售代表ID查询角色ID
     * @param drugUserId
     * @return
     */
	String getRoleIdByDrugUserId(@Param(value = "drugUserId") Long drugUserId);

    /**
     * 得到多个代表名字
     * @param drugUserIdList
     * @return
     */
	String getDrugUserNameList(@Param(value = "drugUserIdList") List<Long> drugUserIdList);

    /**
     * 查询是包含管理员
     * @param drugUserIdList
     * @return
     */
	Integer getMangerCount(@Param(value = "drugUserIdList") List<Long> drugUserIdList);
}
