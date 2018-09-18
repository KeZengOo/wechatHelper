package com.nuoxin.virtual.rep.api.service;

import java.io.File;

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
	 * 父类通用回调处理
	 * @param sinToken 通话记录ID
	 * @param statusName 转换后的状态名
	 * @param audioFileDownloadUrl 语音文件下载地址
	 * @param callTime 通话时长
	 */
	protected boolean processCallBack(String sinToken, String statusName, String audioFileDownloadUrl, long callTime) {
		DoctorCallInfo info = this.getDoctorCallInfoBySinToken(sinToken);
		if(info == null) {
			logger.error("无法获取 DoctorCallInfo 信息 sinToken:{}", sinToken);
			return false;
		}
		
		String callOssUrl = this.processFile(audioFileDownloadUrl, sinToken);
		// 这里走了个补偿.即:当上传至阿里失败时写入回调时供应商传递过来的文件下载链接
		if(StringUtils.isBlank(callOssUrl)) {
			callOssUrl = audioFileDownloadUrl;
		}
		
		Long callId = info.getId();
		this.updateUrl(callOssUrl, statusName, callId, callTime);
		logger.warn("回调执行成功, 返回 true! callId:{},sinToken:{},statusName{},downloadUrl:{},callTime:{}", 
													callId, sinToken, statusName, callOssUrl, callTime);
		
		return true;
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
	 * 文件处理
	 * @param url 文件下载 URL
	 * @param sinToken 通讯唯一标识
	 * @return 成功返回 OSS URL,否则返回 null
	 */
	public String processFile(String url, String sinToken) {
		String fileName = sinToken.concat(FileConstant.MP3_SUFFIX);
		fileService.processLocalFile(url, fileName, path);
		
		String fullFileName = path.concat(fileName);
		return ossService.uploadFile(new File(fullFileName));
	}
	
	/**
	 * 根据 id 更新 statusName, callOssUrl
	 * @param callOssUrl OSS URL
	 * @param statusName 状态名
	 * @param id 打电话记录主键
	 * @param callTime 通话时长
	 */
	private void updateUrl(String callOssUrl, String statusName, Long id, Long callTime) {
		logger.info("callUrl:{},statusName:{},id:{}", callOssUrl, statusName, id);
		callInfoDao.updateUrlRefactor(callOssUrl, statusName, id, callTime);
	}

}
