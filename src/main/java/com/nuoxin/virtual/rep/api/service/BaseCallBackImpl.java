package com.nuoxin.virtual.rep.api.service;

import java.io.File;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.common.constant.FileConstant;
import com.nuoxin.virtual.rep.api.dao.DoctorCallInfoRepository;
import com.nuoxin.virtual.rep.api.entity.DoctorCallInfo;

@Transactional
@Service
public abstract class BaseCallBackImpl implements CallBackService {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseCallBackImpl.class);

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
	 * 根据 sinToken 获取 DoctorCallInfo 信息
	 * @param sinToken 通讯唯一标识
	 * @return 成功返回 DoctorCallInfo,否则返回 null
	 */
	DoctorCallInfo getDoctorCallInfoBySinToken(String sinToken) {
		return callInfoDao.findBySinToken(sinToken);
	}

	/**
	 * 文件处理
	 * @param url 文件下载 URL
	 * @param sinToken 通讯唯一标识
	 * @return 成功返回 OSS URL,否则返回 null
	 */
	String fileProcess(String url, String sinToken) {
		fileService.processLocalFile(url, sinToken + FileConstant.AUDIO_SUFFIX, path);
		String fullFileName = path + sinToken + FileConstant.AUDIO_SUFFIX;
		String callOssUrl = ossService.uploadFile(new File(fullFileName));
		return callOssUrl;
	}
	
	/**
	 * 根据 id 更新 statusName, callOssUrl
	 * @param callOssUrl OSS URL
	 * @param statusName 状态名
	 * @param id 打电话记录主键
	 */
	@Transactional
	void updateUrl(String callOssUrl, String statusName, Long id) {
		logger.info("callUrl:{},statusName:{},id:{}", callOssUrl, statusName, id);
		callInfoDao.updateUrlRefactor(callOssUrl, statusName, id);
	}

}
