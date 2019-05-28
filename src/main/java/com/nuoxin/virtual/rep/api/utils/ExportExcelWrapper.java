package com.nuoxin.virtual.rep.api.utils;

import java.net.URLEncoder;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 包装类
 * @author liuyazhuang
 *
 * @param <T>
 */
@Slf4j
public class ExportExcelWrapper<T> extends ExportExcelUtil<T> {
    /**
     * <p>
     * 导出带有头部标题行的Excel <br>
     * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
     * </p>
     *
     * @param title 表格标题
     * @param headers 头部标题集合
     * @param dataset 数据集合
     * @param version 2003 或者 2007，不传时默认生成2003版本
     */
    public  void exportExcel(String fileName, String title, String[] headers, Collection<T> dataset, HttpServletResponse response,String version) {
        try {
            if(StringUtils.isBlank(version) || EXCEL_FILE_2003.equals(version.trim())){
                response.setContentType("application/vnd.ms-excel");
                response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8") + ".xls");
                exportExcel2003(title, headers, dataset, response.getOutputStream(), "yyyy-MM-dd hh:mm:ss");
            }else{
                response.setContentType("application/vnd.ms-excel");
                response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
                exportExcel2007(title, headers, dataset, response.getOutputStream(), "yyyy-MM-dd hh:mm:ss");
            }
            response.addHeader("Content-Length", String.valueOf(response.getBufferSize()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * demo调用案例
     *
     *@Controller("test")
     * @RequestMapping("/test")
     * public class TestController {
     * 	@RequestMapping("/get/excel")
     * 	public void getExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
     * 		// 准备数据
     * 		List<Student> list = new ArrayList<>();
     * 		for (int i = 0; i < 10; i++) {
     * 			 list.add(new Student(111,"张三asdf","男"));
     * 		     list.add(new Student(111,"李四asd","男"));
     * 		     list.add(new Student(111,"王五","女"));
     * 		}
     * 		String[] columnNames = { "ID", "姓名", " 性别"};
     * 		String fileName = "excel1";
     * 		ExportExcelWrapper<Student> util = new ExportExcelWrapper<Student>();
     * 		util.exportExcel(fileName, fileName, columnNames, list, response, ExportExcelUtil.EXCEL_FILE_2003);
     * 	}
     * }
     *
     *
     *
     *
     */



}