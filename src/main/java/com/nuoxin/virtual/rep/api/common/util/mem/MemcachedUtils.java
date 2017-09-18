package com.nuoxin.virtual.rep.api.common.util.mem;

import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by fenggang on 7/28/17.
 */
@Component
public class MemcachedUtils {

  // 默认过期时间30天
  public static final int DEFAULT_EXPRIE_TIME = 60 * 60 * 24 * 2; // 单位为秒
  public static final int DEFAULT_UPLOAD_TOKEN_EXPRIE_TIME = 60 * 60 * 24 * 15; // 单位为秒

  public static final String TEXT_MSG_PREFIX = "TEXT_MSG_";

  @Autowired
  MemcachedClient memcachedClient;

  protected void set(String key, Object value) {
    memcachedClient.set(key, DEFAULT_EXPRIE_TIME, value);
  }

  public void set(String key, int exprieTime, Object value) {
    memcachedClient.set(key, exprieTime, value);
  }

  protected void delete(String key) {
    memcachedClient.delete(key);
  }

  public Object get(String key) {
    return memcachedClient.get(key);
  }

  protected void touch(String key) {
    memcachedClient.touch(key, DEFAULT_EXPRIE_TIME);
  }

  protected long incr(String key) {
    return memcachedClient.incr(key, 1);
  }

  protected long decr(String key) {
    return memcachedClient.decr(key, 1, 1l);
  }

  protected Map<String, Object> getBulk(List<String> keys) {
    return memcachedClient.getBulk(keys);
  }

}
