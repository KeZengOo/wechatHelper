package com.nuoxin.virtual.rep.api.service;

import java.io.File;
import java.util.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import com.aliyuncs.exceptions.ClientException;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualSplitSpeechCallInfoParams;
import com.nuoxin.virtual.rep.api.utils.AudioConvertUtil;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.utils.SpeechRecognitionUtil;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.common.constant.FileConstant;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.dao.DoctorCallInfoRepository;
import com.nuoxin.virtual.rep.api.entity.DoctorCallInfo;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorCallInfoParams;
import com.nuoxin.virtual.rep.api.mybatis.DoctorMapper;
import com.nuoxin.virtual.rep.api.mybatis.VirtualDoctorCallInfoMapper;

@Transactional
@Service
public abstract class BaseCallBackImpl implements CallBackService{
	
	private static final Logger logger = LoggerFactory.getLogger(BaseCallBackImpl.class);

	@Value("${recording.file.path}")
	private String path;

	@Resource
	private OssService ossService;
	@Resource
	private FileService fileService;
	@Resource
	private VirtualDoctorCallInfoMapper callInfoMapper;
	@Resource
	private DoctorMapper doctorMapper;
	@Resource
	private DoctorCallInfoRepository callInfoDao;


	/**
	 * 父类通用回调处理
	 * @param result ConvertResult 对象
	 */
	protected void processCallBack(ConvertResult result) {
		String sinToken = result.getSinToken();
		String audioFileDownloadUrl = result.getMonitorFilenameUrl();
		String callOssUrl = this.processFile(audioFileDownloadUrl, sinToken);
		logger.info("callOssUrl={}, processCallBack方法中获取的地址："+ callOssUrl);

		//异步（分割录音文件并上传阿里云，返回左右声道的阿里云地址 并且 根据左右声道的阿里云地址进行语音识别，进行入库）
		new Thread(new Runnable(){
			@Override
			public void run() {
				logger.info("runCallOssUrl={}, processCallBack方法中获取的地址："+ callOssUrl);
				//分割录音文件并上传阿里云，返回左右声道的阿里云地址
				Map<String,String> pathMap = splitSpeechAliyunUrlUpdate(callOssUrl);
				logger.info("pathMap={}, 分割录音文件并上传阿里云，返回左右声道的阿里云地址", pathMap);
				if(pathMap.size() > 0){
					//根据左右声道的阿里云地址进行语音识别，进行入库
					boolean result_is_save = saveSpeechRecognitionResultCallInfo(pathMap, sinToken);
					logger.info("result_is_save={}, 根据左右声道的阿里云地址进行语音识别，进行入库是否成功！", result_is_save);
				}
			}
		}).start();

//		//分割录音文件并上传阿里云，返回左右声道的阿里云地址
//		Map<String,String> pathMap = splitSpeechAliyunUrlUpdate(callOssUrl);
//		//根据左右声道的阿里云地址进行语音识别，进行入库
//		boolean result_is_save = saveSpeechRecognitionResultCallInfo(pathMap, sinToken,0);

		result.setMonitorFilenameUrl(callOssUrl);

		DoctorCallInfo info = this.getDoctorCallInfoBySinToken(sinToken);
		if (info == null) {
			logger.warn("无法获取 DoctorCallInfo 信息 sinToken:{}, 走插入表路线", sinToken);
			this.saveCallInfo(result);
		} else {
			logger.warn("可以获取 DoctorCallInfo 信息 sinToken:{}, 走修改表路线", sinToken);
			Long callId = info.getId();
			String statusName = info.getStatusName();
			if(this.flag(statusName)) {
				logger.warn("将 naxions私有 statueName:{} 回写至记录", statusName);
				result.setStatusName(statusName);
			}

			this.updateUrl(callOssUrl, result.getStatus(), result.getStatusName(), callId, result.getCallTime());
		}

		// 语音识别
		this.updateCallUrlText(sinToken, callOssUrl);

		// 语音拆分
		logger.warn("回调执行成功! sinToken:{}, status:{}, statusName:{}, downloadUrl:{}", 
				sinToken, result.getStatus(), result.getStatusName(), callOssUrl);
	}

	/**
	 * 更新电话录音地址
	 * @param sinToken
	 * @param callOssUrl
	 */
	@Async
	protected void updateCallUrlText(String sinToken, String callOssUrl) {
		if (StringUtil.isNotEmpty(sinToken) && StringUtil.isNotEmpty(callOssUrl)){
			try {
				String callText = SpeechRecognitionUtil.getSpeechRecognitionResult(callOssUrl);
				callInfoMapper.updateCallUrlText(sinToken, callText);
			}catch (Exception e){
				logger.error("BaseCallBackImpl updateCallUrlText(String sinToken, String callOssUrl) error !!! sinToken={}, callOssUrl={}", sinToken, callOssUrl, e);
			}

		}

	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 根据 sinToken 获取 DoctorCallInfo 信息
	 * @param sinToken 通讯唯一标识
	 * @return 成功返回 DoctorCallInfo,否则返回 null
	 */
	private DoctorCallInfo getDoctorCallInfoBySinToken(String sinToken) {
		return callInfoDao.findBySinToken(sinToken);
	}

	/**
	 * 文件处理(保存至本地及上传至阿里OSS)
	 * @param audioFileUrl 供应商提供的录音文件下载 URL
	 * @param sinToken 通讯唯一标识
	 * @return 返回 OSS URL
	 */
	protected String processFile(String audioFileUrl, String sinToken) {
		String fileName = sinToken.concat(FileConstant.MP3_SUFFIX);
		fileService.processLocalFile(audioFileUrl, fileName, path);

		String fullFileName = path.concat(fileName);
		logger.info("文件处理方法的文件名：",fullFileName);
		String ossUrl = ossService.uploadFile(new File(fullFileName));
		logger.info("文件处理处理方法中的ossUrl！ossUrl={}", ossUrl);
		
		// 这里走了补偿机制.即:当上传至阿里失败时写入回调时供应商传递过来的文件下载链接
		if (StringUtils.isBlank(ossUrl)) {
			ossUrl = audioFileUrl;
		}

		if (StringUtil.isNotEmpty(ossUrl)){
			if (ossUrl.contains("\\")){
				ossUrl = ossUrl.replaceAll("\\\\","/");
			}
		}

		
		return ossUrl;
	}
	
	/**
	 * 当 statusName 关机,拒接,空号,忙音,停机,无人接听时,不使用回调状态值 TODO 和前端确认 statusName 的值 @谢开宇
	 * @param statusName
	 * @return
	 */
	private boolean flag(String statusName) {
		// 关机,拒接,空号,忙音,停机,无人接听
		return "poweroff".equalsIgnoreCase(statusName) || "reject".equalsIgnoreCase(statusName)
				|| "emptynumber".equalsIgnoreCase(statusName) || "busy".equalsIgnoreCase(statusName)
				|| "stop".equalsIgnoreCase(statusName) || "noanswer".equals(statusName);
	}
	
	/**
	 * 插入回调信息
	 * @param result ConvertResult 对象
	 */
	private void saveCallInfo(ConvertResult result) {
		Long virtualDoctorId = doctorMapper.getDoctorIdByMobile(result.getCalledNo());
		if (virtualDoctorId == null) {
			virtualDoctorId = 0L;
		}
		
		VirtualDoctorCallInfoParams params = new VirtualDoctorCallInfoParams();
		params.setSinToken(result.getSinToken());
		params.setType(result.getType());
		params.setMobile(result.getCalledNo());
		params.setCallUrl(result.getMonitorFilenameUrl());
		params.setStatus(result.getStatus());
		params.setStatusName(result.getStatusName());
		if (StringUtils.isBlank(result.getVisitTime())) {
			params.setVisitTime(null);
		} else {
			params.setVisitTime(result.getVisitTime());
		}
		params.setCallTime(result.getCallTime());
		params.setVirtualDoctorId(virtualDoctorId);
		
		callInfoMapper.saveVirtualDoctorCallInfo(params);
	}
	
	/**
	 * 根据 callId 更新 statusName, callOssUrl
	 * @param callOssUrl OSS URL
	 * @param statusName 状态名
	 * @param callId 打电话记录主键
	 * @param callTime 通话时长
	 */
	private void updateUrl(String callOssUrl, Integer status, String statusName, Long callId, Long callTime) {
		logger.info("callUrl:{},status:{}, statusName:{},id:{}", callOssUrl, status, statusName, callId);
		callInfoDao.updateUrlRefactor(callOssUrl, status, statusName, callId, callTime);
	}

	/**
	 * 分割录音文件并上传阿里云
	 * @param ossFilePath OSS URL
	 * @return
	 */
	@Override
	public Map<String,String> splitSpeechAliyunUrlUpdate(String ossFilePath){
		logger.info("ossFilePath:{}", "语音文件上传到阿里云后的地址："+ossFilePath);
		Map<String,String> pathMaps = new HashMap<String,String>(16);
		String local = FileConstant.LOCAL_PATH;
		int num = ossFilePath.indexOf("\\");
		if(num> -1)
		{
			ossFilePath = ossFilePath.replace("\\","/");
		}
		//下载阿里云中的录音文件
		Integer result =SpeechRecognitionUtil.ossDownLoad(ossFilePath);
		//如果在阿里云上存在下载的文件
		if(result == 1){
			String fileType = ossFilePath.substring(ossFilePath.length()-4,ossFilePath.length());
			String sourceFileName = "";
			String targeFileName = "";
			if(fileType.equals(FileConstant.MP3_SUFFIX)){
				sourceFileName = local+ossFilePath.substring((ossFilePath.lastIndexOf("/")));
				targeFileName = local+ossFilePath.substring((ossFilePath.lastIndexOf("/")));
				targeFileName = targeFileName.substring(0,targeFileName.length()-3)+"wav";
				AudioConvertUtil.mp3ToWav(sourceFileName,targeFileName);
			}

			//区分wav左右声道，并保存到本地
			String wavSourceFileName = local+ossFilePath.substring((ossFilePath.lastIndexOf("/")));
			wavSourceFileName = wavSourceFileName.substring(0,wavSourceFileName.length()-3)+"wav";
			String leftTargeFileName = local+ossFilePath.substring((ossFilePath.lastIndexOf("/")));
			leftTargeFileName = leftTargeFileName.substring(0,leftTargeFileName.length()-4)+"_left.wav";
			String rightTargeFileName = local+ossFilePath.substring((ossFilePath.lastIndexOf("/")));
			rightTargeFileName = rightTargeFileName.substring(0,rightTargeFileName.length()-4)+"_right.wav";

			try {
				AudioConvertUtil.steroToMono(wavSourceFileName,leftTargeFileName,rightTargeFileName);
				//把左右声道上传到阿里云
				String leftOSSPath = getFileOSSPathByLocalFilePath(leftTargeFileName);
				String rightOSSPath = getFileOSSPathByLocalFilePath(rightTargeFileName);

				//把阿里云地址存入新表中（未设计）
				callInfoMapper.saveSplitSpeechAliyunPath(ossFilePath,leftOSSPath,1);
				callInfoMapper.saveSplitSpeechAliyunPath(ossFilePath,rightOSSPath,2);
				//根据阿里云URL进行语音识别

				pathMaps.put("leftOSSPath",leftOSSPath);
				pathMaps.put("rightOSSPath",rightOSSPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pathMaps;
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
		path = ossService.uploadFile(file);
		return path;
	}

	/**
	 * 根据左右声道的阿里云地址进行语音识别，然后根据语句的开始时间进行排序
	 * @param pathMaps
	 * @param sinToken
	 * @return
	 */
	@Override
	public boolean saveSpeechRecognitionResultCallInfo(Map<String,String> pathMaps, String sinToken){
		Map<Integer,String> leftMapText = new HashMap<Integer,String>(16);
		Map<Integer,String> rightMapText = new HashMap<Integer,String>(16);
		Integer virtualDrugUserId = 0;
		VirtualSplitSpeechCallInfoParams vss = new VirtualSplitSpeechCallInfoParams();
		//判断sinToken或callId是否存在，如果存在通过该字段查询sinToken和id值
		if(sinToken != null && sinToken != ""){
			//通过sinToken查询virtual_doctor_call_info表
			vss = callInfoMapper.getCallInfoBySinToken(sinToken);
			if(null != vss){
				if(vss.getVirtualDrugUserId() == null){
					virtualDrugUserId = 0;
				}
				else
				{
					virtualDrugUserId = vss.getVirtualDrugUserId();
				}
			}
		}

		String leftPath = pathMaps.get("leftOSSPath");
		String rightPath = pathMaps.get("rightOSSPath");
		try {
			int leftNum = leftPath.indexOf("\\");
			if(leftNum> -1)
			{
				leftPath = leftPath.replace("\\","/");
			}

			int rightNum = rightPath.indexOf("\\");
			if(rightNum> -1)
			{
				rightPath = rightPath.replace("\\","/");
			}

			leftMapText = SpeechRecognitionUtil.getSpeechRecognitionResultCallBack(leftPath);
			rightMapText = SpeechRecognitionUtil.getSpeechRecognitionResultCallBack(rightPath);
			List<VirtualSplitSpeechCallInfoParams> leftList = new ArrayList<VirtualSplitSpeechCallInfoParams>();
			List<VirtualSplitSpeechCallInfoParams> rightList = new ArrayList<VirtualSplitSpeechCallInfoParams>();
			//保存数据入库
			//循环遍历左声道
			for (Map.Entry<Integer, String> entry : leftMapText.entrySet()) {
				VirtualSplitSpeechCallInfoParams v = new VirtualSplitSpeechCallInfoParams();
				v.setSinToken(sinToken);
				v.setType(1);
				v.setBeginTime(entry.getKey());
				v.setText(entry.getValue());
				v.setVirtualDrugUserId(virtualDrugUserId);
				leftList.add(v);
			}
			//循环遍历右声道
			for (Map.Entry<Integer, String> entry : rightMapText.entrySet()) {
				VirtualSplitSpeechCallInfoParams v = new VirtualSplitSpeechCallInfoParams();
				v.setSinToken(sinToken);
				v.setType(2);
				v.setBeginTime(entry.getKey());
				v.setText(entry.getValue());
				v.setVirtualDrugUserId(virtualDrugUserId);
				rightList.add(v);
			}

			if (CollectionsUtil.isNotEmptyList(leftList)){
				callInfoMapper.saveSpeechRecognitionResultCallInfo(leftList);
			}

			if (CollectionsUtil.isNotEmptyList(rightList)){
				callInfoMapper.saveSpeechRecognitionResultCallInfo(rightList);
			}

			return true;
		} catch (ClientException e) {
			e.printStackTrace();
			return false;
		}
	}
}
