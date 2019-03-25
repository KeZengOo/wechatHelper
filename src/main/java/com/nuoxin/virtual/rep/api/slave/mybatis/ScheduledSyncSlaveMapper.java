package com.nuoxin.virtual.rep.api.slave.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.RoleParams;
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
}
