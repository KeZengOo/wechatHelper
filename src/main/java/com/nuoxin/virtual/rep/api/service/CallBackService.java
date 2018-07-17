package com.nuoxin.virtual.rep.api.service;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;

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
	public void callBack(Map<String, String> map) {
		String sinToken = map.get("CallSheetID");
		String statusName = map.get("State");
		String recordUrl = map.get("RecordFile");
		if (StringUtils.isBlank(recordUrl) || StringUtils.isBlank(recordUrl) || StringUtils.isBlank(recordUrl)) {
			logger.error("CallSheetID,State,RecordFile 为空!");
			return ;
		}
		
		DoctorCallInfo info = callInfoDao.findBySinToken(sinToken);
		if(info == null) {
			logger.error("无法获取 DoctorCallInfo 信息 callId:{}", sinToken);
			return;
		}
		
		fileService.processFile(recordUrl, sinToken + FileConstant.AUDIO_SUFFIX, path);
		String callOssUrl = ossService.uploadFile(new File(path + info.getSinToken() + FileConstant.AUDIO_SUFFIX));
		Long id = info.getId();
		
		// 7moor 状态-> 转成老的状态
		if ("dealing".equalsIgnoreCase(statusName)) {
			statusName = "answer";
		} else if ("notDeal".equalsIgnoreCase(statusName)) {
			statusName = "incall";
		}
		
		callInfoDao.updateUrlRefactor(callOssUrl, statusName, id);
		logger.info("callUrl:{},statusName:{},id:{}", callOssUrl, statusName, id);
	}

}
