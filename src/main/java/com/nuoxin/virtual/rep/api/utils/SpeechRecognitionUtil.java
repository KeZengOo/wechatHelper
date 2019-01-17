package com.nuoxin.virtual.rep.api.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import shaded.org.apache.commons.lang3.StringUtils;

/**
 * Created by yangyang on 2018/10/25.
 * 参考链接：https://help.aliyun.com/document_detail/32290.html?spm=a2c4g.11186623.6.613.4c754d7a9IDrC9
 */
public class SpeechRecognitionUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SpeechRecognitionUtil.class);
	
    private static final String REGIONID = "cn-shanghai";
    private static final String ENDPOINTNAME = "cn-shanghai";
    private static final String PRODUCT = "nls-filetrans";
    private static final String DOMAIN = "filetrans.cn-shanghai.aliyuncs.com";
    private static final String API_VERSION = "2018-08-17";
    private static final String POST_REQUEST_ACTION = "SubmitTask";
    private static final String GET_REQUEST_ACTION = "GetTaskResult";
    
    private static final String KEY_APP_KEY = "app_key";
    private static final String KEY_FILE_LINK = "file_link";
    private static final String KEY_TASK = "Task";
    private static final String KEY_TASK_ID = "TaskId";
    private static final String KEY_STATUS_TEXT = "StatusText";
    
    //naxions accessKeyId
    private static final String accessKeyId = "LTAI3UKhV3R68YYQ";
    //naxions accessKeySecret
    private static final String accessKeySecret = "mu0v9rh1kUM9ndSX5ODIZh8wz6Ak3N";
    //naxions appKey
    private static final String appKey = "X8P4PfbkTkU32OpO";
    
    /**
     * 根据语音文件 URL 识别对应文字
     * @param fileUrl 语音文件 URL
     * @return 返回识别后的文字
     * @throws ClientException
     */
    public static String getSpeechRecognitionResult(String fileUrl) throws ClientException {
        CommonRequest commonRequest = SpeechRecognitionUtil.buildCommonRequestByFileUrl(fileUrl);
        IAcsClient client = SpeechRecognitionUtil.getClient();
		CommonResponse postResponse = client.getCommonResponse(commonRequest);
		
		String speechRecognitionResult = "";
		String taskId = SpeechRecognitionUtil.getTaskId(postResponse);
		
		if(StringUtils.isNotBlank(taskId)) {
			CommonRequest getRequest = SpeechRecognitionUtil.buildCommonRequestByTaskId(taskId);
			speechRecognitionResult = SpeechRecognitionUtil.doGetSpeechRecognitionResult(client, getRequest);
		}
		
		return speechRecognitionResult;
	}
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * 获取阿里云鉴权client
     * @return
     * @throws ClientException
     */
	private static IAcsClient getClient() throws ClientException {
		DefaultProfile.addEndpoint(ENDPOINTNAME, REGIONID, PRODUCT, DOMAIN); // 设置endpoint
		DefaultProfile profile = DefaultProfile.getProfile(REGIONID, accessKeyId, accessKeySecret); // 创建DefaultAcsClient实例并初始化
		return new DefaultAcsClient(profile);
	}
    
    /**
     * 创建CommonRequest 设置请求参数
     */
    private static CommonRequest buildCommonRequestByFileUrl (String fileUrl) {
        CommonRequest postRequest = new CommonRequest();
        // 设置域名
        postRequest.setDomain(DOMAIN);
        // 设置API的版本号，格式为YYYY-MM-DD
        postRequest.setVersion(API_VERSION);
        // 设置action
        postRequest.setAction(POST_REQUEST_ACTION);
        // 设置产品名称
        postRequest.setProduct(PRODUCT);
        
        /*
         * 设置录音文件识别请求参数，以JSON字符串的格式设置到请求的Body中
         */
        JSONObject taskObject = new JSONObject();
        // 设置app_key
        taskObject.put(KEY_APP_KEY, appKey);
        // 设置音频文件访问链接
        taskObject.put(KEY_FILE_LINK, fileUrl);
        String task = taskObject.toJSONString();
        // 设置以上JSON字符串为Body参数
        postRequest.putBodyParameter(KEY_TASK, task);
        // 设置为POST方式的请求
        postRequest.setMethod(MethodType.POST);
        
        return postRequest;
    }
    
    /**
     * 创建CommonRequest 设置任务ID 
     * @param taskId
     * @return
     */
    private static CommonRequest buildCommonRequestByTaskId (String taskId) {
        CommonRequest getRequest = new CommonRequest();
        // 设置域名
        getRequest.setDomain(DOMAIN);
        // 设置API版本
        getRequest.setVersion(API_VERSION);
        // 设置action
        getRequest.setAction(GET_REQUEST_ACTION);
        // 设置产品名称
        getRequest.setProduct(PRODUCT);
        // 设置任务ID为查询参数
        getRequest.putQueryParameter(KEY_TASK_ID, taskId);
        // 设置为GET方式的请求
        getRequest.setMethod(MethodType.GET);
        
        return getRequest;
    }
    
    /**
     * 获取 taskId
     * @param postResponse
     * @return
     */
	private static String getTaskId(CommonResponse postResponse) {
		String taskId = ""; // 获取录音文件识别请求任务的ID，以供识别结果查询使用
		if (postResponse != null && postResponse.getHttpStatus() == 200) {
			JSONObject result = JSONObject.parseObject(postResponse.getData());
			String statusText = result.getString(KEY_STATUS_TEXT);
			if ("SUCCESS".equals(statusText)) {
				logger.info("录音文件识别请求成功响应： " + result.toJSONString());
				taskId = result.getString(KEY_TASK_ID);
			} else {
				logger.error("录音文件识别请求失败： " + postResponse.getData());
				return "";
			}
		} else {
			logger.error("录音文件识别请求失败，Http错误码：" + postResponse.getHttpStatus());
			logger.error("录音文件识别请求失败响应：" + postResponse.getData());
			return "";
		}

		return taskId;
	}
    
    /**
     * 获取识别结果
     * @param client
     * @param getRequest
     * @return
     * @throws ClientException
     */
	private static String doGetSpeechRecognitionResult(IAcsClient client, CommonRequest request) throws ClientException {
		String speechRecognitionResult = "";
		String statusText = "";
		
        /*
         * 提交录音文件识别结果查询请求，以轮询的方式进行识别结果的查询。
         * 直到服务端返回的状态描述为“SUCCESS”/“SUCCESS_WITH_NO_VALID_FRAGMENT”，或者为错误描述，则结束轮询。
         */
		while (true) {
			CommonResponse getResponse = client.getCommonResponse(request);
			if (getResponse.getHttpStatus() != 200) {
				logger.error("识别结果查询请求失败，Http错误码：" + getResponse.getHttpStatus());
				logger.error("识别结果查询请求失败：" + getResponse.getData());
				break;
			}

			JSONObject result = JSONObject.parseObject(getResponse.getData());
			logger.debug("识别查询结果：" + result.toJSONString());

			statusText = result.getString(KEY_STATUS_TEXT);
			if ("RUNNING".equals(statusText) || "QUEUEING".equals(statusText)) {
				try {
					Thread.sleep(3000); // 继续轮询
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				JSONArray arr = result.getJSONObject("Result").getJSONArray("Sentences");
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < arr.size(); i++) {
					JSONObject o = arr.getJSONObject(i);
					sb.append(o.getString("Text"));
				}

				speechRecognitionResult = sb.toString();
				logger.info("识别结果:{}", speechRecognitionResult);
				break;
			}
		}

		if ("SUCCESS".equals(statusText) || "SUCCESS_WITH_NO_VALID_FRAGMENT".equals(statusText)) {
			logger.warn("录音文件识别成功！");
		} else {
			logger.warn("录音文件识别失败！");
			speechRecognitionResult = "";
		}

		return speechRecognitionResult;
	}
	
    public static void main(String args[]) throws Exception {
        // naxions 音频地址
        String fileUrl = "https://nuoxin-virtual-rep-storage.oss-cn-beijing.aliyuncs.com/virtual/2018112317/42b7f644-4199-4d8d-9a55-41eeb1d97585.mp3";
		System.out.println("识别结果：" + SpeechRecognitionUtil.getSpeechRecognitionResult(fileUrl));
    }

}
