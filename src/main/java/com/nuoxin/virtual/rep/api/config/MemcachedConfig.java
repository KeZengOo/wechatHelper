package com.nuoxin.virtual.rep.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 与memcached相关的配置信息
 *
 * @author caoxudong
 * @since 0.1.0
 */
@ConfigurationProperties(prefix = "memcached")
@Component
public class MemcachedConfig {

  private String servers;
  private String username;
  private String password;
  private boolean needAuth;

  public String getServers() {
    return servers;
  }

  public void setServers(String servers) {
    this.servers = servers;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isNeedAuth() {
    return needAuth;
  }

  public void setNeedAuth(boolean needAuth) {
    this.needAuth = needAuth;
  }
  
}
