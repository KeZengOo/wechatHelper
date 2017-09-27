package com.nuoxin.virtual.rep.api.service;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import com.nuoxin.virtual.rep.api.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.nuoxin.virtual.rep.api.common.util.FileUtils;
import com.nuoxin.virtual.rep.api.common.util.OSSContentTypeUtil;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.config.AliyunConfig;

import javax.annotation.Resource;

@Service
public class OssService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private AliyunConfig aliyunConfig;
	@Resource
	private OSSClient uploadOSSClient;

	/**
	 * oss上传文件，返回文件访问路径
	 *
	 * @param file：文件
	 * @return
	 */
	public String upload(MultipartFile file) {
		String originFileName = file.getOriginalFilename();
		String suffixName = originFileName.substring(originFileName.indexOf(".") + 1);
		String fileType = OSSContentTypeUtil.getContentType(suffixName);
		// 设置文件名
		String filePathName = generateRelativeStoragePath(suffixName);
		byte[] fileContent = null;
		try {
			fileContent = file.getBytes();
		} catch (Exception e) {
			logger.error("Cannot get file content from {}.", originFileName);
		}
		ObjectMetadata meta = new ObjectMetadata();
		// 设置上传文件长度
		meta.setContentLength(file.getSize());
		// 设置上传MD5校验
		String md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(fileContent));
		meta.setHeader("Content-MD5",md5);
		meta.setContentType(fileType);

		// 存储
		try {
			uploadOSSClient.putObject(aliyunConfig.getBucketName(), filePathName, file.getInputStream(), meta);
		} catch (OSSException | ClientException | IOException e) {
			logger.error("OSS storage error", e);
		}
		String path = aliyunConfig.getDownloadEndpoint() + "/" + filePathName;
		if (FileUtils.isImg(suffixName)) {
			// 图片访问处理样式，可在oss自定义,缩放、裁剪、压缩、旋转、格式、锐化、水印等
			path += StringUtils.isNotBlank(aliyunConfig.getStyleName()) ? "?x-oss-process=style/" + aliyunConfig.getStyleName() : "";
		}
		return path;
	}

	/**
	 * base64code方式上传
	 *
	 * @param imageBytes 文件流
	 * @return
	 */
	public String uploadImageBase64(byte[] imageBytes) {
		String fileType = "image/jpeg";
		// 设置文件名
		String filePathName = generateRelativeStoragePath("jpeg");
		ObjectMetadata meta = new ObjectMetadata();
		// 设置上传文件长度
		meta.setContentLength(imageBytes.length);
		// 设置上传MD5校验
		String md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(imageBytes));
		meta.setHeader("Content-MD5",md5);
		meta.setContentType(fileType);

		// 存储
		try {
			uploadOSSClient.putObject(aliyunConfig.getBucketName(), filePathName, new ByteArrayInputStream(imageBytes), meta);
		} catch (Exception e) {
			logger.error("OSS storage error", e);
		}
		String path = aliyunConfig.getDownloadEndpoint() + FileUtils.getFileSeparator() + filePathName;
		// 图片访问处理样式，可在oss自定义,缩放、裁剪、压缩、旋转、格式、锐化、水印等
		return path + (StringUtils.isNotBlank(aliyunConfig.getStyleName()) ? "?x-oss-process=style/" + aliyunConfig.getStyleName() : "");
	}

	/**
	 * File方式上传
	 *
	 * @param file 文件
	 * @return
	 */
	public String uploadFile(File file) {
		// 存储
		InputStream is = null;
		try {
			String originFileName = file.getName();
			String suffixName = originFileName.substring(originFileName.indexOf(".") + 1);
			String fileType = OSSContentTypeUtil.getContentType(suffixName);
			// String fileType = "application/octet-stream";
			// 设置文件名
			String filePathName = generatePath(originFileName);
			is = new FileInputStream(file);
			byte[] fileContent = new byte[is.available()];
			is.read(fileContent);
			ObjectMetadata meta = new ObjectMetadata();
			// 设置上传文件长度
			meta.setContentLength(file.length());
			// 设置上传MD5校验
			String md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(fileContent));
			meta.setHeader("Content-MD5",md5);
			meta.setContentType(fileType);
			if("wav".equals(suffixName)){
				meta.setContentType("audio/x-ms-wax");

			}
			uploadOSSClient.putObject(aliyunConfig.getBucketName(), filePathName, new ByteArrayInputStream(fileContent), meta);
			String path = aliyunConfig.getDownloadEndpoint() + FileUtils.getFileSeparator() + filePathName;
			return path;
		} catch (Exception e) {
			logger.error("OSS storage error", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 下载文件
	 *
	 * @param url
	 * @return
	 */
	public byte[] download(String url) {
		InputStream is = null;
		try {
			String key = url.split(aliyunConfig.getDownloadEndpoint() + "/")[1];
			OSSObject ossObject = uploadOSSClient.getObject(aliyunConfig.getBucketName(), key);
			is = ossObject.getObjectContent();
			byte[] data = IOUtils.readStreamAsByteArray(is);
			return data;
		} catch (Exception e) {
			logger.error("下载文件异常,url={}", url, e);
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 获取存储的相对路径 规则path + / + yyyyMMddHH + uuid
	 *
	 * @param suffixName 后缀名
	 * @return
	 */
	private String generateRelativeStoragePath(String suffixName) {
		String time = DateUtil.gettDateStrFromSpecialDate(new Date(),"yyyyMMddHH");
		String uuid = StringUtils.uuid();
		StringBuilder sb = new StringBuilder();
		String storagePath = aliyunConfig.getStoragePath();
		if (StringUtils.isNotBlank(storagePath)) {
			sb.append(storagePath).append("/");
		}
		sb.append(time).append("/").append(uuid);
		if (StringUtils.isNotBlank(suffixName)) {
			sb.append(".").append(suffixName);
		}
		return sb.toString();
	}

	/**
	 * 获取存储的相对路径 规则path + / + yyyyMMddHH + uuid
	 *
	 * @param suffixName 后缀名
	 * @return
	 */
	private String generatePath(String suffixName) {
		String time = DateUtil.gettDateStrFromSpecialDate(new Date(),"yyyyMMddHH");
		String uuid = StringUtils.uuid();
		StringBuilder sb = new StringBuilder();
		String storagePath = aliyunConfig.getStoragePath();
		if (StringUtils.isNotBlank(storagePath)) {
			sb.append(storagePath).append("/");
		}
		sb.append(time);
		if (StringUtils.isNotBlank(suffixName)) {
			sb.append("/").append(suffixName);
		}
		return sb.toString();
	}
}
