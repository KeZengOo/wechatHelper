package com.nuoxin.virtual.rep.api.utils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nuoxin.virtual.rep.api.common.annotations.Excel;

/**
 * 利用发射动态导入excel
 * 
 * @author tiancun
 * @param <E> 对应的java对象
 */
public class ExcelUtils<E> {

	private static final  Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private E e;
	private int etimes = 0;

	public ExcelUtils(E e) {
		this.e = e;
	}

	/**
	 * 重载方法从文件输入流中读取数据，<br>
	 * 最好是所有的单元格都是文本格式，<br>
	 * 日期格式要求yyyy-MM-dd HH:mm:ss,布尔类型0：真，1：假
	 * @param edf 数据格式化，如果没有要格式化的，传null
	 * @param inputStream  Excel文件输入流，支持xlsx 和 xls 后缀的文件
	 * @return List<E>
	 * @throws Exception
	 */
	public List<E> readFromFile(ExcelDataFormatter edf, InputStream inputStream) throws Exception {
		List<E> list = Collections.emptyList();
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(inputStream);
			list = this.convertExcelToList(edf, wb, this.getTextToMap());
		} catch (InvalidFormatException e){
			throw new InvalidFormatException("非Excel格式文件,文件需要另存为Excel格式");
		} catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			if (wb != null) {
				wb.close();
			}
		}

		return list;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private Map<String, String> getTextToMap() throws Exception {
		Field[] fields = ReflectUtils.getClassFieldsAndSuperClassFields(e.getClass());
		Map<String, String> textToKey = new HashMap<String, String>(fields.length);

		Excel excel = null;
		for (Field field : fields) {
			excel = field.getAnnotation(Excel.class);
			if (excel == null || excel.skip() == true) {
				continue;
			}

			textToKey.put(excel.name(), field.getName());
		}

		return textToKey;
	}

	private List<E> convertExcelToList(ExcelDataFormatter edf, Workbook wb, Map<String, String> textToKey) throws Exception {
		Field[] fields = ReflectUtils.getClassFieldsAndSuperClassFields(e.getClass());
		Sheet sheet = wb.getSheetAt(0);
		Row title = sheet.getRow(0);

		// 标题数组，后面用到，根据索引去标题名称，通过标题名称去字段名称用到 textToKey
		String[] titles = new String[title.getPhysicalNumberOfCells()];
		for (int i = 0; i < title.getPhysicalNumberOfCells(); i++) {
			titles[i] = title.getCell(i).getStringCellValue();
		}

		List<E> list = new ArrayList<E>(sheet.getLastRowNum());

		int rowIndex = 0;
		int columnCount = titles.length;
		Cell cell = null;
		Row row = null;
		E e = null;

		for (Iterator<Row> it = sheet.rowIterator(); it.hasNext();) {
			row = it.next();
			if (row == null) {
				break;
			}
			
			if (rowIndex++ == 0) {
				continue;
			}

			if (row.getCell(0) == null) {
				continue;
			}

			e = get();

			for (int i = 0; i < columnCount; i++) {
				cell = row.getCell(i);
				if (cell == null) {
					continue;
				}
				
				etimes = 0;
				readCellContent(textToKey.get(titles[i]), fields, cell, e, edf);
			}

			list.add(e);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	private E get() throws InstantiationException, IllegalAccessException {
		return (E) e.getClass().newInstance();
	}

	/**
	 * 从单元格读取数据，根据不同的数据类型，使用不同的方式读取<br>
	 * 有时候POI自作聪明，经常和我们期待的数据格式不一样，会报异常，<br>
	 * 我们这里采取强硬的方式<br>
	 * 使用各种方法，知道尝试到读到数据为止，然后根据Bean的数据类型，进行相应的转换<br>
	 * 如果尝试完了（总共7次），还是不能得到数据，那么抛个异常出来，没办法了
	 *
	 * @param key    当前单元格对应的Bean字段
	 * @param fields Bean所有的字段数组
	 * @param cell   单元格对象
	 * @param e
	 * @throws Exception
	 */
	private void readCellContent(String key, Field[] fields, Cell cell, E e, ExcelDataFormatter edf) throws Exception {
		Object o = null;
		try {
			switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_BOOLEAN:
				o = cell.getBooleanCellValue();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				o = cell.getNumericCellValue();
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					o = DateUtil.getJavaDate(cell.getNumericCellValue());
				}
				break;
			case XSSFCell.CELL_TYPE_STRING:
				o = cell.getStringCellValue();
				break;
			case XSSFCell.CELL_TYPE_ERROR:
				o = cell.getErrorCellValue();
				break;
			case XSSFCell.CELL_TYPE_BLANK:
				o = null;
				break;
			case XSSFCell.CELL_TYPE_FORMULA:
				o = cell.getCellFormula();
				break;
			default:
				o = null;
				break;
			}

			if (o == null)
				return;

			for (Field field : fields) {
				field.setAccessible(true);
				if (field.getName().equals(key)) {
					Boolean bool = true;
					Map<String, String> map = null;
					if (edf == null) {
						bool = false;
					} else {
						map = edf.get(field.getName());
						if (map == null) {
							bool = false;
						}
					}

					if (field.getType().equals(Date.class)) {
						if (o.getClass().equals(Date.class)) {
							field.set(e, o);
						} else {
							field.set(e, sdf.parse(o.toString()));
						}
					} else if (field.getType().equals(String.class)) {
						if (o.getClass().equals(String.class)) {
							field.set(e, o);
						} else {
							field.set(e, o.toString());
						}
					} else if (field.getType().equals(Long.class)) {
						if (o.getClass().equals(Long.class)) {
							field.set(e, o);
						} else {
							field.set(e, Long.parseLong(o.toString()));
						}
					} else if (field.getType().equals(Integer.class)) {
						if (o.getClass().equals(Integer.class)) {
							field.set(e, o);
						} else {
							// 检查是否需要转换
							if (bool) {
								field.set(e, map.get(o.toString()) != null ? Integer.parseInt(map.get(o.toString()))
										: Integer.parseInt(o.toString()));
							} else {
								field.set(e, Integer.parseInt(o.toString()));
							}

						}
					} else if (field.getType().equals(BigDecimal.class)) {
						if (o.getClass().equals(BigDecimal.class)) {
							field.set(e, o);
						} else {
							field.set(e, BigDecimal.valueOf(Double.parseDouble(o.toString())));
						}
					} else if (field.getType().equals(Boolean.class)) {
						if (o.getClass().equals(Boolean.class)) {
							field.set(e, o);
						} else {
							// 检查是否需要转换
							if (bool) {
								field.set(e, map.get(o.toString()) != null ? Boolean.parseBoolean(map.get(o.toString()))
										: Boolean.parseBoolean(o.toString()));
							} else {
								field.set(e, Boolean.parseBoolean(o.toString()));
							}
						}
					} else if (field.getType().equals(Float.class)) {
						if (o.getClass().equals(Float.class)) {
							field.set(e, o);
						} else {
							field.set(e, Float.parseFloat(o.toString()));
						}
					} else if (field.getType().equals(Double.class)) {
						if (o.getClass().equals(Double.class)) {
							field.set(e, o);
						} else {
							field.set(e, Double.parseDouble(o.toString()));
						}

					}

				}
			}
		} catch (Exception ex) {
			logger.error("Exception", e);
			// 如果还是读到的数据格式还是不对，只能放弃了
			if (etimes > 7) {
				throw ex;
			}
			etimes++;
			if (o == null) {
				readCellContent(key, fields, cell, e, edf);
			}
		}
	}

}
