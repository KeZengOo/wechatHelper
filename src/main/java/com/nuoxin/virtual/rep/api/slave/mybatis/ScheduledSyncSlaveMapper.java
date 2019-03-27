package com.nuoxin.virtual.rep.api.slave.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 从数据库同步mapper
 * @author wujiang
 * @date 2019-03-25
 */
public interface ScheduledSyncSlaveMapper {

    /**
     * 得到从库最新的角色创建时间
     * @return String
     */
    String getRoleNewCreateTime();

    /**
     * 得到从库最新的角色更新时间
     * @return String
     */
    String getRoleNewUpdateTime();

    /**
     * 同步产品数据到从库
     * @param list
     * @return boolean
     */
    boolean syncRoleList(List<RoleParams> list);

    /**
     * 同步角色数据到从库 update
     * @param id
     * @param roleName
     * @param createTime
     * @param updateTime
     * @return boolean
     */
    boolean syncUpdateRoleList(@Param(value = "id") Long id, @Param(value = "roleName") String roleName, @Param(value = "createTime") String createTime,@Param(value = "updateTime") String updateTime);

    /**
     * 得到从库最新的销售与医生关系指标创建时间
     * @return String
     */
    String getDrugUserDoctorQuateNewCreateTime();

    /**
     * 得到从库最新的销售与医生关系指标更新时间
     * @return String
     */
    String getDrugUserDoctorQuateNewUpdateTime();

    /**
     * 同步产品数据到从库
     * @param list
     * @return boolean
     */
    boolean syncDrugUserDoctorQuateList(List<DrugUserDoctorQuateBean> list);

    /**
     * 同步角色数据到从库 update
     * @param drugUserDoctorQuateBean
     * @return boolean
     */
    boolean syncUpdateDrugUserDoctorQuateList(@Param(value = "drugUserDoctorQuateBean") DrugUserDoctorQuateBean drugUserDoctorQuateBean);

    /**
     * 得到从库最新的电话拜访扩展创建时间
     * @return String
     */
    String getVirtualDoctorCallInfoMendNewCreateTime();

    /**
     * 得到从库最新的电话拜访扩展更新时间
     * @return String
     */
    String getVirtualDoctorCallInfoMendNewUpdateTime();

    /**
     * 同步产品数据到从库
     * @param list
     * @return boolean
     */
    boolean syncVirtualDoctorCallInfoMendList(List<VirtualDoctorCallInfoMendBean> list);

    /**
     * 同步角色数据到从库 update
     * @param virtualDoctorCallInfoMendBean
     * @return boolean
     */
    boolean syncUpdateVirtualDoctorCallInfoMendList(@Param(value = "virtualDoctorCallInfoMendBean") VirtualDoctorCallInfoMendBean virtualDoctorCallInfoMendBean);


    /**
     * 得到从库最新的销售代表给医生打电话创建时间
     * @return String
     */
    String getVirtualDoctorCallInfoNewCreateTime();

    /**
     * 得到从库最新的销售代表给医生打电话更新时间
     * @return String
     */
    String getVirtualDoctorCallInfoNewUpdateTime();

    /**
     * 同步产品数据到从库
     * @param list
     * @return boolean
     */
    boolean syncVirtualDoctorCallInfoList(List<VirtualDoctorCallInfoBean> list);

    /**
     * 同步角色数据到从库 update
     * @param virtualDoctorCallInfoBean
     * @return boolean
     */
    boolean syncUpdateVirtualDoctorCallInfoList(@Param(value = "virtualDoctorCallInfo") VirtualDoctorCallInfoBean virtualDoctorCallInfoBean);



    /**
     * 得到从库最新的诺和医院信息创建时间
     * @return String
     */
    String getEnterpriseHciCreateTime();

    /**
     * 得到从库最新的诺和医院信息更新时间
     * @return String
     */
    String getEnterpriseHciUpdateTime();

    /**
     * 同步产品数据到从库
     * @param list
     * @return boolean
     */
    boolean syncEnterpriseHciList(List<HospitalBean> list);

    /**
     * 同步角色数据到从库 update
     * @param hospitalBean
     * @return boolean
     */
    boolean syncUpdateEnterpriseHciList(@Param(value = "hospitalBean") HospitalBean hospitalBean);
}
