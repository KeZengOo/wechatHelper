package com.nuoxin.virtual.rep.api.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @描述：测试excel读取
 * */
public class ExcelUtils{

	/** 总行数 */

	private static int totalRows = 0;

	/** 总列数 */

	private static int totalCells = 0;

	/** 错误信息 */

	private static String errorInfo;

	/** 构造方法 */

	public ExcelUtils(){}

	/**
	 * 
	 * @描述：得到总行数
	 * 
	 * @参数：@return
	 * 
	 * @返回值：int
	 */

	public static int getTotalRows(){

		return totalRows;

	}

	/**
	 * 
	 * @描述：得到总列数
	 * 
	 * @参数：@return
	 * 
	 * @返回值：int
	 */

	public static int getTotalCells(){

		return totalCells;

	}

	/**
	 * 
	 * @描述：得到错误信息
	 * 
	 * @参数：@return
	 * 
	 * @返回值：String
	 */

	public static String getErrorInfo(){

		return errorInfo;

	}

	/**
	 * 
	 * @描述：验证excel文件
	 * 
	 * @参数：@param filePath　文件完整路径
	 * 
	 * @参数：@return
	 * 
	 * @返回值：boolean
	 */

	public static boolean validateExcel(String filePath){

		/** 检查文件名是否为空或者是否是Excel格式的文件 */

		if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))){

			errorInfo = "文件名不是excel格式";

			return false;

		}

		/** 检查文件是否存在 */

		File file = new File(filePath);

		if (file == null || !file.exists()){

			errorInfo = "文件不存在";

			return false;

		}

		return true;

	}

	/**
	 * 
	 * @描述：根据文件名读取excel文件
	 * 
	 * @参数：@param filePath 文件完整路径
	 * 
	 * @参数：@return
	 * 
	 * @返回值：List
	 */

	public static List<List<Object>> read(String filePath){

		List<List<Object>> dataLst = new ArrayList<List<Object>>();

		InputStream is = null;

		try{

			/** 验证文件是否合法 */

			if (!validateExcel(filePath)){

				System.out.println(errorInfo);

				return null;

			}

			/** 判断文件的类型，是2003还是2007 */

			boolean isExcel2003 = true;

			if (WDWUtil.isExcel2007(filePath)){

				isExcel2003 = false;

			}

			/** 调用本类提供的根据流读取的方法 */

			File file = new File(filePath);

			is = new FileInputStream(file);

			dataLst = read(is, isExcel2003);

			is.close();

		}catch (Exception ex){

			ex.printStackTrace();

		}finally{

			if (is != null){

				try{

					is.close();

				}catch (IOException e){

					is = null;

					e.printStackTrace();

				}

			}

		}

		/** 返回最后读取的结果 */

		return dataLst;

	}

	/**
	 * 
	 * @描述：根据流读取Excel文件
	 * 
	 * @参数：@param inputStream
	 * 
	 * @参数：@param isExcel2003
	 * 
	 * @参数：@return
	 * 
	 * @返回值：List
	 */

	public static List<List<Object>> read(InputStream inputStream, boolean isExcel2003){

		List<List<Object>> dataLst = null;

		try{

			/** 根据版本选择创建Workbook的方式 */

			Workbook wb = null;

			if (isExcel2003){
				wb = new HSSFWorkbook(inputStream);
			}else{
				wb = new XSSFWorkbook(inputStream);
			}
			dataLst = read(wb);

		}catch (IOException e){

			e.printStackTrace();

		}

		return dataLst;

	}

	/**
	 * 
	 * @描述：读取数据
	 * 
	 * @参数：@param Workbook
	 * 
	 * @参数：@return
	 * 
	 * @返回值：List<List<String>>
	 */
	private static List<List<Object>> read(Workbook wb){

		List<List<Object>> dataLst = new ArrayList<List<Object>>();

		/** 得到第一个shell */

		Sheet sheet = wb.getSheetAt(0);

		/** 得到Excel的行数 */

		totalRows = sheet.getPhysicalNumberOfRows();

		/** 得到Excel的列数 */

		if (totalRows >= 1 && sheet.getRow(0) != null){

			totalCells = sheet.getRow(0).getPhysicalNumberOfCells();

		}

		/** 循环Excel的行 */

		for (int r = 0; r < totalRows; r++){

			Row row = sheet.getRow(r);

			if (row == null){

				continue;

			}

			List<Object> rowLst = new ArrayList<Object>();

			/** 循环Excel的列 */

			for (int c = 0; c < getTotalCells(); c++){

				Cell cell = row.getCell(c);
				Object cellValue = "";
				if (null != cell){
					// 以下是判断数据的类型
					switch (cell.getCellType()){
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字
						if(HSSFDateUtil.isCellDateFormatted(cell)){
							//用于转化为日期格式
							Date d = cell.getDateCellValue();
							DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
							cellValue = formater.format(d);
						}else{
							// 用于格式化数字，只保留数字的整数部分
							DecimalFormat df = new DecimalFormat("########");
							cellValue = df.format(cell.getNumericCellValue());
						}
						//cellValue = cell.getNumericCellValue();
						break;

					case HSSFCell.CELL_TYPE_STRING: // 字符串
						cellValue = cell.getStringCellValue();
						break;

					case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
						cellValue = cell.getBooleanCellValue() ;
						break;

					case HSSFCell.CELL_TYPE_FORMULA: // 公式
						cellValue = cell.getCellFormula() ;
						break;

					case HSSFCell.CELL_TYPE_BLANK: // 空值
						cellValue = "";
						break;

					case HSSFCell.CELL_TYPE_ERROR: // 故障
						cellValue = "非法字符";
						break;

					default:
						cellValue = "未知类型";
						break;
					}
				}

				rowLst.add(cellValue);

			}

			/** 保存第r行的第c列 */

			dataLst.add(rowLst);

		}

		return dataLst;

	}

	/**
	 * 创建excel文档，
	 * @param list 数据
	 * @param keys list中map的key数组集合
	 * @param columnNames excel的列名
	 * */
	public static Workbook createWorkBook(List<Map<String, Object>> list, String []keys, String columnNames[]) {
		// 创建excel工作簿
		Workbook wb = new HSSFWorkbook();
		// 创建第一个sheet（页），并命名
		Sheet sheet = wb.createSheet("sheet");
		// 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
		for(int i=0;i<keys.length;i++){
			sheet.setColumnWidth((short) i, (short) (35.7 * 150));
		}

		// 创建第一行
		Row row = sheet.createRow((short) 0);

		// 创建两种单元格格式
		CellStyle cs = wb.createCellStyle();
		CellStyle cs2 = wb.createCellStyle();

		// 创建两种字体
		Font f = wb.createFont();
		Font f2 = wb.createFont();

		// 创建第一种字体样式（用于列名）
		f.setFontHeightInPoints((short) 10);
		f.setColor(IndexedColors.BLACK.getIndex());
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// 创建第二种字体样式（用于值）
		f2.setFontHeightInPoints((short) 10);
		f2.setColor(IndexedColors.BLACK.getIndex());

		//      Font f3=wb.createFont();
		//      f3.setFontHeightInPoints((short) 10);
		//      f3.setColor(IndexedColors.RED.getIndex());

		// 设置第一种单元格的样式（用于列名）
		cs.setFont(f);
		cs.setBorderLeft(CellStyle.BORDER_THIN);
		cs.setBorderRight(CellStyle.BORDER_THIN);
		cs.setBorderTop(CellStyle.BORDER_THIN);
		cs.setBorderBottom(CellStyle.BORDER_THIN);
		cs.setAlignment(CellStyle.ALIGN_CENTER);

		// 设置第二种单元格的样式（用于值）
		cs2.setFont(f2);
		cs2.setBorderLeft(CellStyle.BORDER_THIN);
		cs2.setBorderRight(CellStyle.BORDER_THIN);
		cs2.setBorderTop(CellStyle.BORDER_THIN);
		cs2.setBorderBottom(CellStyle.BORDER_THIN);
		cs2.setAlignment(CellStyle.ALIGN_CENTER);
		//设置列名
		for(int i=0;i<columnNames.length;i++){
			Cell cell = row.createCell(i);
			cell.setCellValue(columnNames[i]);
			cell.setCellStyle(cs);
		}
		//设置每行每列的值
		for (short i = 0; i < list.size(); i++) {
			// Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
			// 创建一行，在页sheet上
			Row row1 = sheet.createRow((short) i+1);
			// 在row行上创建一个方格
			for(short j=0;j<keys.length;j++){
				Cell cell = row1.createCell(j);
				cell.setCellValue(list.get(i).get(keys[j]) == null?" ": list.get(i).get(keys[j]).toString());
				cell.setCellStyle(cs2);
			}
		}
		return wb;
	}

	public static List<Map<String, Object>> listToMap(List<List<Object>> list){
		List<Object> lists = list.get(0);
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		for (int i=1;i<list.size();i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Object> listl = list.get(i);
			for (int j = 0; j < lists.size(); j++) {
				map.put(lists.get(j).toString(), listl.get(j));
			}
			listmap.add(map);
		}
		return listmap;
	}


}

/**
 * 
 * @描述：工具类
 * 
 */

class WDWUtil{

	/**
	 * 
	 * @描述：是否是2003的excel，返回true是2003
	 * 
	 * @参数：@param filePath　文件完整路径
	 * 
	 * @参数：@return
	 * 
	 * @返回值：boolean
	 */

	public static boolean isExcel2003(String filePath){

		return filePath.matches("^.+\\.(?i)(xls)$");

	}

	/**
	 * 
	 * @描述：是否是2007的excel，返回true是2007
	 * 
	 * @参数：@param filePath　文件完整路径
	 * 
	 * @参数：@return
	 * 
	 * @返回值：boolean
	 */

	public static boolean isExcel2007(String filePath){

		return filePath.matches("^.+\\.(?i)(xlsx)$");

	}

}
