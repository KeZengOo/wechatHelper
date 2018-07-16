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
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;

/**
 * 回调类
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
		if(CollectionsUtil.isEmptyMap(map)) {
			return;
		}
		
		String callId = map.get("CallSheetID");
		String statusName = map.get("State");
		String recordUrl = map.get("RecordFile");
		
		DoctorCallInfo info = callInfoDao.findBySinToken(callId);
		if(info == null) {
			return;
		}
		
		if (StringUtils.isNotEmtity(recordUrl)) {
			fileService.processFile(recordUrl, callId + FileConstant.AUDIO_SUFFIX, path);
			String url = ossService.uploadFile(new File(path + info.getSinToken() + FileConstant.AUDIO_SUFFIX));
			info.setCallUrl(url);
		}

		// 7moor 状态-> 转成老的状态
		if ("dealing".equalsIgnoreCase(statusName)) {
			statusName = "answer";
		} else if ("notDeal".equalsIgnoreCase(statusName)) {
			statusName = "incall";
		}

		String callUrl = info.getCallUrl();
		Long id = info.getId();
		callInfoDao.updateUrlRefactor(callUrl, statusName, id);
		
		logger.info("callUrl:{},statusName:{},id:{}", callUrl, statusName, id);
	}

}
