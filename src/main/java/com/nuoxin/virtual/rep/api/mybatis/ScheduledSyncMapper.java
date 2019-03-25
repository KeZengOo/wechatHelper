package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.RoleParams;
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

}
