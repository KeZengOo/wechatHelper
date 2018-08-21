package com.nuoxin.virtual.rep.api.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Map.Entry;


public class RestUtils {

  private static Logger logger = LoggerFactory.getLogger(RestUtils.class);
  public static final String UTF_8 = "UTF-8";

  public static String get(String url) {
    String output = null;
    CloseableHttpClient httpClient = HttpClients.createDefault();
    try {
      HttpGet request = new HttpGet(url);
      request.setHeader("Accept", "application/json");
      request.setHeader("Content-Type", "application/json");
      logger.debug("request url={}", url);
      HttpResponse response = httpClient.execute(request);
      if (response.getStatusLine().getStatusCode() != 200) {
        logger.warn("GET调用异常，返回code : {}", response.getStatusLine().getStatusCode());
        logger.warn("url={}", url);
      }
      output = EntityUtils.toString(response.getEntity(), UTF_8);
      logger.debug("response {}", output);
    } catch (Exception e) {
      logger.error("RestUtils get(String url) 请求发送失败 {}", e.getMessage());
      logger.error("RestUtils get(String url) 请求发送失败 {}", e);
    } finally {

    }
    return output;
  }

  public static String post(String url, String jsonParams, Map<String, String> headers) {
    String output = null;
    HttpResponse response = null;
    CloseableHttpClient httpClient = null;
    try {
      httpClient = HttpClients.createDefault();
      HttpPost request = new HttpPost(url);
      request.setHeader("Accept", "*/*");
      request.setHeader("Content-Type", "application/json");
      // add request header
      if (headers != null && !headers.isEmpty()) {
        for (Entry<String, String> entry : headers.entrySet()) {
          request.setHeader(entry.getKey(), entry.getValue());
        }
      }
      // add request body
      if (StringUtils.isNotBlank(jsonParams)) {
        StringEntity input = new StringEntity(jsonParams, UTF_8);
        request.setEntity(input);
      }
      logger.debug("request url={}, params={}", url, jsonParams);
      response = httpClient.execute(request);
      output = EntityUtils.toString(response.getEntity(), UTF_8);
    } catch (Exception e) {
      logger.error("RestUtils post(String url, String jsonParams, Map<String, String> headers) 请求发送失败 {}", e.getMessage());
      logger.error("RestUtils post(String url, String jsonParams, Map<String, String> headers) 请求发送失败 {}", e);
      throw new RuntimeException("POST调用异常");
    }
    if (response.getStatusLine().getStatusCode() != 200) {
      logger.error("发送短信出现异常，params={}", JSON.toJSONString(response));
      logger.warn("POST调用异常，返回code : {}", response.getStatusLine().getStatusCode());
      logger.warn("url={}, params={}", url, jsonParams);
      throw new RuntimeException("非200状态码,调用方异常."); //当前针对短信发送,只处理此状态码
    }
    
    logger.debug("response {}", output);

    return output;
  }

  public static String put(String url) {
    String output = null;
    CloseableHttpClient httpClient = HttpClients.createDefault();
    try {
      HttpPut request = new HttpPut(url);
      request.setHeader("Accept", "application/json");
      request.setHeader("Content-Type", "application/json");
      logger.debug("request url={}", url);
      HttpResponse response = httpClient.execute(request);
      if (response.getStatusLine().getStatusCode() != 200) {
        logger.warn("PUT调用异常，返回code : {}", response.getStatusLine().getStatusCode());
        logger.warn("url={}", url);
      }
      output = EntityUtils.toString(response.getEntity(), UTF_8);
      logger.debug("response {}", output);
    } catch (Exception e) {
      logger.error("RestUtils put(String url) 请求发送失败 {}", e.getMessage());
      logger.error("RestUtils put(String url) 请求发送失败 {}", e);

    }
    return output;
  }

  public static String delete(String url, Map<String, String> headers) {
    String output = null;
    CloseableHttpClient httpClient = HttpClients.createDefault();
    try {
      HttpDelete request = new HttpDelete(url);
      request.setHeader("Accept", "application/json");
      request.setHeader("Content-Type", "application/json");
      // add request header
      if (headers != null && !headers.isEmpty()) {
        for (Entry<String, String> entry : headers.entrySet()) {
          request.setHeader(entry.getKey(), entry.getValue());
        }
      }
      HttpResponse response = httpClient.execute(request);
      if (response.getStatusLine().getStatusCode() != 200) {
        logger.warn("DELETE调用异常，返回code : {}", response.getStatusLine().getStatusCode());
        logger.warn("url={}", url);
      }
      output = EntityUtils.toString(response.getEntity(), UTF_8);
      logger.debug("response {}", output);
    } catch (Exception e) {
      logger.error("RestUtils delete(String url, Map<String, String> headers) 请求发送失败 {}", e.getMessage());
      logger.error("RestUtils delete(String url, Map<String, String> headers) 请求发送失败 {}", e);
    }
    return output;
  }

}
