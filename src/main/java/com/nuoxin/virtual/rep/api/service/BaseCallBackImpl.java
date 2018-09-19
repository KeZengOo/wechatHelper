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
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorCallInfoParams;
import com.nuoxin.virtual.rep.api.mybatis.DoctorMapper;
import com.nuoxin.virtual.rep.api.mybatis.VirtualDoctorCallInfoMapper;

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
	@Resource
	private VirtualDoctorCallInfoMapper callInfoMapper;
	@Resource
	private DoctorMapper doctorMapper;
	
	/**
	 * 父类通用回调处理
	 * @param sinToken 通话记录ID
	 * @param statusName 转换后的状态名
	 * @param audioFileDownloadUrl 语音文件下载地址
	 * @param callTime 通话时长
	 */
	protected void processCallBack(ConvertResult result) {
		String sinToken = result.getSinToken();
		String audioFileDownloadUrl = result.getMonitorFilenameUrl();
		 // 文件处理(保存至本地及上传至阿里OSS)
		String callOssUrl = this.processFile(audioFileDownloadUrl, sinToken);
		
		// 这里走了补偿机制.即:当上传至阿里失败时写入回调时供应商传递过来的文件下载链接
		if (StringUtils.isBlank(callOssUrl)) {
			callOssUrl = audioFileDownloadUrl;
		}
		result.setMonitorFilenameUrl(callOssUrl);

		DoctorCallInfo info = this.getDoctorCallInfoBySinToken(sinToken);
		if (info == null) {
			logger.warn("无法获取 DoctorCallInfo 信息 sinToken:{}, 走插入表路线", sinToken);
			this.saveCallInfo(result);
		} else {
			logger.warn("可以获取 DoctorCallInfo 信息 sinToken:{}, 走修改表路线", sinToken);
			Long callId = info.getId();
			this.updateUrl(callOssUrl, result.getStatusName(), callId, result.getCallTime());
		}

		logger.warn("回调执行成功! ,sinToken:{},downloadUrl:{}", sinToken, callOssUrl);
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
	
	private void saveCallInfo(ConvertResult result) {
		VirtualDoctorCallInfoParams params = new VirtualDoctorCallInfoParams();
		params.setSinToken(result.getSinToken());
		params.setType(result.getType());
		params.setMobile(result.getCallNo());
		params.setCallUrl(result.getMonitorFilenameUrl());
		params.setStatus(1);
		params.setStatusName(result.getStatusName());
		params.setVisitTime(result.getVisitTime());
		params.setCallTime(result.getCallTime());
		
		Long virtualDoctorId = doctorMapper.getDoctorIdByMobile(result.getCallNo());
		if (virtualDoctorId == null) {
			virtualDoctorId = 0L;
		}
		params.setVirtualDoctorId(virtualDoctorId);
		
		callInfoMapper.saveVirtualDoctorCallInfo(params);
	}

}
