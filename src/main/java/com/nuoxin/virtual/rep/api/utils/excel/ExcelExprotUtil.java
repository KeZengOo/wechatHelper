package com.nuoxin.virtual.rep.api.utils.excel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * excel导出
 * @author fenggang
 *
 */
public class ExcelExprotUtil {

	private static Logger logger = LoggerFactory.getLogger(ExcelExprotUtil.class);
	
	/**
	 * exprot excel
	 * @param list 数据源	
	 * @param name  文件名
	 * @param keys 对应数据源里面的属性
	 * @param columnNames 每列的标题
	 * @param response
	 * @param request
	 */
	public static void exprot(List<Map<String,Object>> list, String name, String []keys, String columnNames[],
                              HttpServletResponse response, HttpServletRequest request){
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ExcelUtils.createWorkBook(list,keys,columnNames).write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			//response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="+ new String((name + ".xls").getBytes(), "ISO8859-1"));
			ServletOutputStream out = response.getOutputStream();
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			logger.debug("excel导出错误【{}】",e.getMessage());
		} finally {
			if (bis != null)
				try {
					bis.close();
				} catch (IOException e) {
					logger.debug("excel导出input-IO异常：【{}】",e.getMessage());
					e.printStackTrace();
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					logger.debug("excel导出output-IO异常：【{}】",e.getMessage());
					e.printStackTrace();
				}
		}
	}
}
