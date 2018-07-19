package com.nuoxin.virtual.rep.api.service;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.common.constant.FileConstant;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.dao.DoctorCallInfoRepository;
import com.nuoxin.virtual.rep.api.entity.DoctorCallInfo;

/**
 * 回调Service类
 * @author xiekaiyu
 */
@Transactional
@Service
public class CallBackService {

	private static final Logger logger = LoggerFactory.getLogger(CallBackService.class);

	@Value("${recording.file.path}")
	private String path;
	@Value("${audio.download.url}")
	private String url;

	@Resource
	private DoctorCallInfoRepository callInfoDao;
	@Resource
	private OssService ossService;
	@Resource
	private FileService fileService;

	/**
	 * 参考链接 https://developer.7moor.com/event/
	 * @param map
	 */
	public void callBack(Map<String, String> paramsMap) {
		this.pause();
		// 与数据库对应的字段 sin_token(callId)
		String sinToken = paramsMap.get("CallSheetID");
		// 与数据库对应的字段 status_name
		String statusName = paramsMap.get("State");
		// 电话录音下载地址
		String monitorFilenameUrl = paramsMap.get("MonitorFilename");
		
		DoctorCallInfo info = callInfoDao.findBySinToken(sinToken);
		if(info == null) {
			logger.error("无法获取 DoctorCallInfo 信息 callId:{},paramsMap:{}", sinToken, paramsMap);
			return;
		}

		fileService.processFile(monitorFilenameUrl, sinToken + FileConstant.AUDIO_SUFFIX, path);
		String callOssUrl = ossService.uploadFile(new File(path + info.getSinToken() + FileConstant.AUDIO_SUFFIX));
		Long id = info.getId();
		
		// 7moor 状态-> 转成老的状态
		if ("dealing".equalsIgnoreCase(statusName)) {
			statusName = "answer";
		} else if ("notDeal".equalsIgnoreCase(statusName)) {
			statusName = "incall";
		}
		
		// 这里走了个补偿.即:当上传至阿里失败时写入 7moor 链接
		if(StringUtils.isBlank(callOssUrl)) {
			callOssUrl = monitorFilenameUrl;
		}
		
		this.updateUrl(callOssUrl, statusName, id);
		logger.info("callUrl:{},statusName:{},id:{}", callOssUrl, statusName, id);
	}

	@Transactional
	private void updateUrl(String callOssUrl, String statusName, Long id) {
		callInfoDao.updateUrlRefactor(callOssUrl, statusName, id);
	}
	
	/**
	 * 线程暂停70秒
	 */
	private void pause () {
		try {
			Thread.sleep(70000);
		} catch (InterruptedException e) {
			logger.error("InterruptedException", e);
		}
	}
}
