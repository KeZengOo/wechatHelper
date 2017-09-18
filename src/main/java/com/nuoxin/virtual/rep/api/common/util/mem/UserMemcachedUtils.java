package com.nuoxin.virtual.rep.api.common.util.mem;

import com.alibaba.fastjson.JSON;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fenggang on 7/28/17.
 */
@Component
public class UserMemcachedUtils extends MemcachedUtils {

  public static final String USER_PREFIX = "USER_INFO_";
  public static final String USER_NAME_PREFIX = "USER_NAME_";
  public static final String USER_FOLLOW_PREFIX = "USER_FOLLOW_";

  // 操作用户
  public void setUser(Long userId, DrugUser user) {
    if (user == null) {
      return;
    }
    set(USER_PREFIX + userId, JSON.toJSONString(user));
    setUserIdByUsername(user.getName(), userId);
  }

  public DrugUser getUser(Long userId) {
    Object obj = get(USER_PREFIX + userId);
    if (obj != null) {
      return JSON.parseObject(obj.toString(), DrugUser.class);
    }
    return null;
  }

  /**
   * 批量获得用户信息
   * 
   * @param postIds
   * @param userId
   * @return
   */
  public Map<String, Object> getBulkUser(List<Long> userIds) {
    List<String> keys = new ArrayList<String>();
    for (Long userId : userIds) {
      keys.add(USER_PREFIX + userId);
    }
    return getBulk(keys);
  }

  /**
   * 保存用户名和用户ID的关联
   * 
   * @param username
   * @param userId
   */
  private void setUserIdByUsername(String username, Long userId) {
    set(USER_NAME_PREFIX + username, userId);
  }

  /**
   * 根据用户名获得用户ID
   * 
   * @param username
   * @return
   */
  public Long getUserIdByUsername(String username) {
    Object obj = get(USER_NAME_PREFIX + username);
    if (obj != null) {
      return (Long) obj;
    }
    return null;
  }

  /**
   * 判断用户名是否已存在
   * 
   * @param username
   * @return
   */
  public boolean isUniqueUsername(String username) {
    Object obj = get(USER_NAME_PREFIX + username);
    if (obj != null) {
      return false;
    }
    return true;
  }

  /**
   * 删除关注数据
   * 
   * @param followId
   * @param fanId
   */
  public void deleteFollow(Long followId, Long fanId) {
    delete(USER_FOLLOW_PREFIX + followId + "_" + fanId);
  }
}
