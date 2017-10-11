package com.nuoxin.virtual.rep.api.common.util;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by fenggang on 7/28/17.
 */
public class MySession implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;
  private Map<String, Object> innerData;;

  public MySession() {
    innerData = new HashMap<>();
  }

  public MySession(String sessionId) {

    if (StringUtils.isEmpty(sessionId)) {
      id = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
    } else {
      id = sessionId;
    }

    innerData = new HashMap<>();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Map<String, Object> getInnerData() {
    return innerData;
  }

  public void setInnerData(Map<String, Object> innerData) {
    this.innerData = innerData;
  }

  public void setAttribute(String key, Object value) {
    innerData.put(key, value);
  }

  public Object getAttribute(String key) {
    return innerData.get(key);
  }

}
