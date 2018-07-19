package com.nuoxin.virtual.rep.api.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 文件业务类
 * @author xiekaiyu
 */
@Service
public class FileService {
	
	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
	
	/**
	 * 文件处理业务
	 * @param urlStr 下载源 URL
	 * @param fileName 文件名
	 * @param savePath 路径
	 */
	public void processLocalFile(String urlStr, String fileName, String savePath) {
		byte[] binaryArr = this.downLoadFromUrl(urlStr, fileName, savePath);
		if (binaryArr == null || binaryArr.length == 0) {
			logger.warn("得到的二进制数组为 null 或 长度为 0,无法保存文件!");
			return;
		}
		
		this.saveFile(savePath, fileName, binaryArr);
	}
	
	/**
	 * 下载文件获取二进制(在 catch 语句块中加入重试)
	 * @param urlStr 下载源 URL
	 * @param fileName 文件名
	 * @param savePath 路径
	 * @return 成功返回文件二进制数组,否则返回空数组
	 */
	public byte[] downLoadFromUrl(String urlStr, String fileName, String savePath)  {
		URLConnection con = null;
		URL url = null;
		try {
			url = new URL(urlStr);
			con = url.openConnection();
		} catch (IOException e) {
			logger.error("IOException", e);
			try {
				Thread.sleep(10000);
				con = url.openConnection();
			} catch (InterruptedException e1) {
				logger.error("InterruptedException", e1);
			} catch (IOException e1) {
				logger.error("IOException", e1);
			}
		}
		
		if (con == null) {
			logger.warn("URLConnection is null !");
			return new byte[0];
		}
		
		// 设置超时间为60秒
		con.setConnectTimeout(60000);
		// 防止屏蔽程序抓取而返回403错误
		con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		
		return this.getBinaryArr(con);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 *根据URLConnection获取二进制流 (在 catch 语句块中加入重试)
	 * @param con URLConnection 对象
	 * @return 成功返回byte数组,否则返回空数组
	 */
	private byte[] getBinaryArr(URLConnection con) {
		byte[] binaryArr = null;
		InputStream inputStream = null;
		
		try {
			inputStream = con.getInputStream();
			binaryArr = readInputStream(inputStream);
		} catch (IOException e) {
			logger.error("IOException", e);
			try {
				Thread.sleep(10000);
				inputStream = con.getInputStream();
			} catch (InterruptedException e1) {
				logger.error("InterruptedException", e1);
			} catch (IOException e1) {
				logger.error("IOException", e1);
			}
			
			binaryArr = readInputStream(inputStream);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("IOException", e);
				}
			}
		}
		
		return binaryArr;
	}

	/**
	 * 将流转换为byt数组
	 * @param inputStream
	 * @return 成功返回byte数组
	 */
	private byte[] readInputStream(InputStream inputStream) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(4096);
		try {
			byte[] buffer = new byte[4096];
			int len = 0;
			while ((len = inputStream.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
		} catch (IOException e) {
			logger.error("IOException", e);
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				logger.error("IOException", e);
			}
		}
		
		return bos.toByteArray();
	}
	
	/**
	 * 保存文件
	 * @param savePath 路径
	 * @param fileName 文件名
	 * @param binaryArr 二进制数组
	 */
	private void saveFile(String savePath, String fileName, byte[] binaryArr) {
		File saveDir = new File(savePath);
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}

		File file = new File(saveDir + File.separator + fileName);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(binaryArr);
			fos.flush();
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					logger.error("IOException", e);
				}
			}
		}
	}
}