package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.common.util.MyMapper;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v2_5.ProductDO;
import com.nuoxin.virtual.rep.api.web.controller.request.QueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DoctorProductResponse;
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

    /**
     * 得到产品下所有的代表，不包括经理,管理员
     * @param productId
     * @return
     */
    List<DrugUserResponseBean> getAllDrugUserList(@Param(value = "productId") Long productId);


    Integer doctorPageCount(QueryRequestBean bean);

    /**
     * 根据leaderpath, productId查询下属销售
     * @param leaderPath
     * @param productId
     * @return
     */
    List<DrugUserResponseBean> relationDrugUser(@Param("leaderPath") String leaderPath,@Param("productId") Long productId);


    /**
     * virtual_doctor_call_info_mend 中代表ID已经和产品取消关联的代表ID
     * @param productId
     * @return
     */
    List<Long> mendOtherDrugUserId(@Param("productId") Long productId);
    
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
     * 查询销售名下某个医生所有设置动态字段的产品
     * @param productIdList
     * @param doctorId
     * @return
     */
    List<ProductDO> getSetDynamicFieldProductListByProduct(@Param(value = "productIdList") List<Long> productIdList,@Param(value = "doctorId") Long doctorId);


    /**
     * 查询销售名下某个医生所有设置动态字段的产品
     * @param leaderPath
     * @param doctorIdList
     * @return
     */
    List<DoctorProductResponse> getProductListByDoctorId(@Param(value = "leaderPath") String leaderPath, @Param(value = "doctorIdList") List<Long> doctorIdList);


    /**
     * 查询销售名下某个医生所有设置动态字段的产品
     * @param type 1 是代表我的医生的，
     *             其他代表是公共池的
     * @param productIdList
     * @param doctorIdList
     * @return
     */
    List<DoctorProductResponse> getProductListByDoctorIdAndProduct(@Param(value = "type") Integer type, @Param(value = "productIdList") List<Long> productIdList, @Param(value = "doctorIdList") List<Long> doctorIdList);



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
	List<Long> getRoleIdByDrugUserId(@Param(value = "drugUserId") Long drugUserId);

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


	/****************************  V3.0.1相关   **************************************/

    /**
     * 根据代表邮箱查询代表角色种类，去重
     * @param drugUserEmailList
     * @return
     */
	List<Long> getRoleIdList(@Param(value = "drugUserEmailList") List<String> drugUserEmailList);

    /**
     * 根据代表邮箱查询代表角色种类，去重
     * @param drugUserId
     * @param doctorId
     * @param productId
     * @return
     */
    List<DrugUser> getRoleIdListByDoctor(@Param(value = "drugUserId") Long drugUserId, @Param(value = "doctorId") Long doctorId,@Param(value = "productId") Long productId);

    /**
     * 得到代表角色种类，去重
     * @param telephone
     * @param productId
     * @param roleIdList
     * @return
     */
    List<DrugUser> getDrugUserList(@Param(value = "telephone") String telephone,
                                                    @Param(value = "productId") Long productId,
                                                    @Param(value = "roleIdList") List<Long> roleIdList);

    /**
     * 根据角色ID查询名称
     * @param roleId
     * @return
     */
    String getRoleNameById(@Param(value = "roleId") Long roleId);

    /**
     * 根据代表ID查询角色名称
     * @param drugUserId
     * @return
     */
    String getRoleNameByUserId(@Param(value = "drugUserId") Long drugUserId);

}
