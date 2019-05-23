package com.nuoxin.virtual.rep.api.utils;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.ObjectMetadata;
import com.nuoxin.virtual.rep.api.common.constant.FileConstant;
import com.nuoxin.virtual.rep.api.common.util.FileUtils;
import com.nuoxin.virtual.rep.api.common.util.OSSContentTypeUtil;
import com.nuoxin.virtual.rep.api.config.AliyunConfig;
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

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

	private static final String ENABLE_CALLBACK = "enable_callback";
	private static final String CALLBACK_URL = "callback_url";


    //naxions accessKeyId
    private static final String accessKeyId = "LTAI3UKhV3R68YYQ";
    //naxions accessKeySecret
    private static final String accessKeySecret = "mu0v9rh1kUM9ndSX5ODIZh8wz6Ak3N";
    //naxions appKey
    private static final String appKey = "X8P4PfbkTkU32OpO";


	/**
	 *  1.下载阿里云上的电话录音到本地
	 * 	2.之后进行MP3转WAV
	 * 	3.进行左右声道分割
	 * 	4.分别进行语音识别转成文本
	 * 	5.入库
	 */
//	private static final String OSS_HTTP_URL = "https://nuoxin-virtual-rep-storage.oss-cn-beijing.aliyuncs.com/virtual/2018112317/42b7f644-4199-4d8d-9a55-41eeb1d97585.mp3";

	public static Integer ossDownLoad(String ossHttpUrl){
		BufferedInputStream bis=null;
		BufferedOutputStream bos=null;
		try {
			URL url = new URL(ossHttpUrl);
			HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream is = connection.getInputStream();

			bis = new BufferedInputStream(is);

			//名字截取 可以省略
			File file = new File(FileConstant.LOCAL_PATH+ossHttpUrl.substring((ossHttpUrl.lastIndexOf("/"))));
			FileOutputStream fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			int b = 0;
			byte[] byArr = new byte[1024*4];
			while((b=bis.read(byArr))!=-1){
				bos.write(byArr, 0, b);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}finally{
			try {
				if(bis!=null){
					bis.close();
				}
				if(bos!=null){
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * File方式上传
	 *
	 * @param filePath 本地文件路径
	 * @return path
	 */
	public String getFileOSSPathByLocalFilePath(String filePath){
		File file = new File(filePath);
		String path = "";
//		path = uploadFile(file);
		return path;
	}

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
        //设置回调方式
		taskObject.put(ENABLE_CALLBACK, true);
		taskObject.put(CALLBACK_URL, fileUrl);

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
     * @param request
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

	/**
	 * 根据回调语音文件 URL 识别对应文字
	 * @param fileUrl 语音文件 URL
	 * @return 返回识别后的文字
	 * @throws ClientException
	 */
	public static Map<Integer,String> getSpeechRecognitionResultCallBack(String fileUrl) throws ClientException {
		Map<Integer,String> textMaps = new HashMap<Integer,String>(16);

		CommonRequest commonRequest = SpeechRecognitionUtil.buildCommonRequestByFileUrl(fileUrl);
		IAcsClient client = SpeechRecognitionUtil.getClient();
		CommonResponse postResponse = client.getCommonResponse(commonRequest);

		String speechRecognitionResult = "";
		String taskId = SpeechRecognitionUtil.getTaskId(postResponse);

		if(StringUtils.isNotBlank(taskId)) {
			CommonRequest getRequest = SpeechRecognitionUtil.buildCommonRequestByTaskId(taskId);
			textMaps = SpeechRecognitionUtil.doGetSpeechRecognitionResultCallBack(client, getRequest);
		}

		return textMaps;
	}

	/**
	 * 获取回调识别结果
	 * @param client
	 * @return String
	 * @throws ClientException
	 */
	private static Map<Integer,String> doGetSpeechRecognitionResultCallBack(IAcsClient client, CommonRequest request) throws ClientException {
		String speechRecognitionResult = "";
		String statusText = "";

		Map<Integer,String> textMaps = new HashMap<Integer,String>(16);

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
				for (int i = 0; i < arr.size(); i++) {
					JSONObject o = arr.getJSONObject(i);
					Integer beginTime = Integer.parseInt(o.getString("BeginTime"));
					String text = o.getString("Text");
					textMaps.put(beginTime,text);
				}
				logger.info("识别结果:{}", textMaps);
				break;
			}
		}

		if ("SUCCESS".equals(statusText) || "SUCCESS_WITH_NO_VALID_FRAGMENT".equals(statusText)) {
			logger.warn("录音文件识别成功！");
		} else {
			logger.warn("录音文件识别失败！");
			textMaps = null;
		}

		return textMaps;
	}

    public static void main(String args[]) throws Exception {
        // naxions 音频地址
//        String fileUrl = "https://nuoxin-virtual-rep-storage.oss-cn-beijing.aliyuncs.com/virtual/2018112317/42b7f644-4199-4d8d-9a55-41eeb1d97585.mp3";
//		String fileUrl = "https://nuoxin-virtual-rep-storage.oss-cn-beijing.aliyuncs.com/virtual/2019030811/c90dfefd-8d31-4b8e-a93a-3ccd9f19d3f1_left.wav";
//		System.out.println("识别结果：" + SpeechRecognitionUtil.getSpeechRecognitionResult(fileUrl));
//		System.out.println("识别结果：" + SpeechRecognitionUtil.getSpeechRecognitionResultCallBack(fileUrl));

		//下载阿里云中的录音文件
//		Integer result = ossDownLoad("https://nuoxin-virtual-rep-storage.oss-cn-beijing.aliyuncs.com/virtual/2018100315/2630b4fe-9737-4cc9-8d64-2f094c7cc070.mp3");
//		System.out.println(result);
		//		String sourceFileName = LOCAL_PATH+OSS_HTTP_URL.substring((OSS_HTTP_URL.lastIndexOf("/")));
//		String targeFileName = LOCAL_PATH+OSS_HTTP_URL.substring((OSS_HTTP_URL.lastIndexOf("/")));
//		targeFileName = targeFileName.substring(0,targeFileName.length()-3)+"wav";
//		AudioConvertUtil.mp3ToWav(sourceFileName,targeFileName);

		//区分wav左右声道，并保存到本地
//		String wavSourceFileName = LOCAL_PATH+OSS_HTTP_URL.substring((OSS_HTTP_URL.lastIndexOf("/")));
//		wavSourceFileName = wavSourceFileName.substring(0,wavSourceFileName.length()-3)+"wav";
//		String leftTargeFileName = LOCAL_PATH+OSS_HTTP_URL.substring((OSS_HTTP_URL.lastIndexOf("/")));
//		leftTargeFileName = leftTargeFileName.substring(0,leftTargeFileName.length()-4)+"_left.wav";
//		String rightTargeFileName = LOCAL_PATH+OSS_HTTP_URL.substring((OSS_HTTP_URL.lastIndexOf("/")));
//		rightTargeFileName = rightTargeFileName.substring(0,rightTargeFileName.length()-4)+"_right.wav";
//		AudioConvertUtil.steroToMono(wavSourceFileName,leftTargeFileName,rightTargeFileName);

		//把左右声道上传到阿里云
//		String leftOSSPath = getFileOSSPathByLocalFilePath(leftTargeFileName);
//		String rightOSSPath = getFileOSSPathByLocalFilePath(rightTargeFileName);

//		System.out.println("leftOSSPath:"+leftOSSPath);
//		System.out.println("rightOSSPath:"+rightOSSPath);


		String speechRecognitionResult = SpeechRecognitionUtil.getSpeechRecognitionResult("http://47.92.140.240/inOutTempOfWav/bj.ali.14.4/20190523/20190523-102233_N00000028346__917071060328_cc-ali-0-1558578144.171287-in.wav");
		System.out.println(speechRecognitionResult);

	}

}
