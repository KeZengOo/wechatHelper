package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.DrugUserDoctorQuateBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.RoleParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorCallInfoMendBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据同步mapper
 * @author wujiang
 * @date 2019-03-25
 */
public interface ScheduledSyncMapper {

    /**
     * 根据创建时间获取大于该时间的角色list
     * @param createTime
     * @return list
     */
    List<RoleParams> getRoleListByCreateTime(@Param(value = "createTime") String createTime);

    /**
     * 根据更新时间获取大于该时间的角色list
     * @param updateTime
     * @return list
     */
    List<RoleParams> getRoleListByUpdateTime(@Param(value = "updateTime") String updateTime);

    /**
     * 根据创建时间获取大于该时间的销售与医生关系指标list
     * @param createTime
     * @return list
     */
    List<DrugUserDoctorQuateBean> getDrugUserDoctorQuateListByCreateTime(@Param(value = "createTime") String createTime);

    /**
     * 根据更新时间获取大于该时间的销售与医生关系指标list
     * @param updateTime
     * @return list
     */
    List<DrugUserDoctorQuateBean> getDrugUserDoctorQuateListByUpdateTime(@Param(value = "updateTime") String updateTime);

    /**
     * 根据创建时间获取大于该时间的销售与医生关系指标list
     * @param createTime
     * @return list
     */
    List<VirtualDoctorCallInfoMendBean> getVirtualDoctorCallInfoMendListByCreateTime(@Param(value = "createTime") String createTime);

    /**
     * 根据更新时间获取大于该时间的销售与医生关系指标list
     * @param updateTime
     * @return list
     */
    List<VirtualDoctorCallInfoMendBean> getVirtualDoctorCallInfoMendListByUpdateTime(@Param(value = "updateTime") String updateTime);

}
