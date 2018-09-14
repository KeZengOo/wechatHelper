package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.common.util.MyMapper;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
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
    
}
