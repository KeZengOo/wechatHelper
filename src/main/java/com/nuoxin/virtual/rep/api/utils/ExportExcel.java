package com.nuoxin.virtual.rep.api.utils;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.excel.SheetRequestBean;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @className ExportExcel
 * List集合导出成Excel表格工具类
 */
public final class ExportExcel {
    /***
     * 构造方法
     */
    private ExportExcel() {

    }

    /***
     * 工作簿
     */
    private static HSSFWorkbook workbook;

    /***
     * sheet
     */
    private static HSSFSheet sheet;
    /***
     * 表头行开始位置
     */
    private static final int HEAD_START_POSITION = 0;

    /***
     * 文本行开始位置
     */
    private static final int CONTENT_START_POSITION = 1;


    /**
     *
     * @param dataList
     *        对象集合
     * @param titleMap
     *        表头信息（对象属性名称->要显示的标题值)[按顺序添加]
     * @param sheetName
     *        sheet名称和表头值
     */
    public static HSSFWorkbook excelExport(List<?> dataList, Map<String, String> titleMap, String sheetName) {
        // 初始化workbook
        initHSSFWorkbook(sheetName);
        // 表头行
        createHeadRow(titleMap);
        createContentRow(dataList, titleMap);
        return workbook;
    }

    /**
     * 单个sheet
     * @param dataList
     *        对象集合
     * @param titleMap
     *        表头信息（对象属性名称->要显示的标题值)[按顺序添加]
     * @param sheetName
     *        sheet名称和表头值
     */
    public static HSSFWorkbook excelLinkedHashMapExport(List<LinkedHashMap<String,Object>> dataList, Map<String, String> titleMap, String sheetName) {
        // 初始化workbook
        initHSSFWorkbook(sheetName);
        // 表头行
        createHeadRow(titleMap);
        createContentRowMap(dataList, titleMap);
        return workbook;
    }


    /**
     * 多个sheet
     * @param list
     *        对象集合
     */
    public static HSSFWorkbook excelLinkedHashMapExport(List<SheetRequestBean> list) {

        if (CollectionsUtil.isEmptyList(list)){
            return new HSSFWorkbook();
        }

        workbook = new HSSFWorkbook();
        for (SheetRequestBean sheetRequestBean : list) {
            String sheetName = sheetRequestBean.getSheetName();
            Map<String, String> titleMap = sheetRequestBean.getTitleMap();
            List<LinkedHashMap<String, Object>> dataList = sheetRequestBean.getDataList();
            HSSFSheet sheet = workbook.createSheet(sheetName);
            // 第1行创建
            HSSFRow headRow = sheet.createRow(HEAD_START_POSITION);
            int i = 0;
            for (String entry : titleMap.keySet()) {
                HSSFCell headCell = headRow.createCell(i);
                headCell.setCellValue(titleMap.get(entry));
                i++;
            }

            try {
                int k=0;
                for (LinkedHashMap obj : dataList) {
                    HSSFRow textRow = sheet.createRow(CONTENT_START_POSITION + i);
                    int j = 0;
                    for (String entry : titleMap.keySet()) {
                        String value =obj.get(entry)!=null?obj.get(entry).toString():"";
                        HSSFCell textcell = textRow.createCell(j);
                        textcell.setCellValue(value);
                        j++;
                    }
                    k++;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

        return workbook;
    }


    /***
     *
     * @param sheetName
     *        sheetName
     */
    private static void initHSSFWorkbook(String sheetName) {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
    }

    /**
     * 创建表头行（第二行创建）
     * @param titleMap 对象属性名称->表头显示名称
     */
    private static void createHeadRow(Map<String, String> titleMap) {
        // 第1行创建
        HSSFRow headRow = sheet.createRow(HEAD_START_POSITION);
        int i = 0;
        for (String entry : titleMap.keySet()) {
            HSSFCell headCell = headRow.createCell(i);
            headCell.setCellValue(titleMap.get(entry));
            i++;
        }
    }

    /**
     * List<对象类型的通用导出></>
     * @param dataList 对象数据集合
     * @param titleMap 表头信息
     */
    private static void createContentRow(List<?> dataList, Map<String, String> titleMap) {
        try {
            int i=0;
            for (Object obj : dataList) {
                HSSFRow textRow = sheet.createRow(CONTENT_START_POSITION + i);
                int j = 0;
                for (String entry : titleMap.keySet()) {
                    String method = "get" + entry.substring(0, 1).toUpperCase() + entry.substring(1);
                    Method m = obj.getClass().getMethod(method, null);
                    String value =m.invoke(obj, null)!=null?m.invoke(obj, null).toString():"";
                    HSSFCell textcell = textRow.createCell(j);
                    textcell.setCellValue(value);
                    j++;
                }
                i++;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * List<LinkedHashMap> 导出字段是动态的</>
     * @param dataList 对象数据集合
     * @param titleMap 表头信息
     */
    private static void createContentRowMap(List<LinkedHashMap<String,Object>> dataList, Map<String, String> titleMap) {
        try {
            int i=0;
            for (LinkedHashMap obj : dataList) {
                HSSFRow textRow = sheet.createRow(CONTENT_START_POSITION + i);
                int j = 0;
                for (String entry : titleMap.keySet()) {
                    String value =obj.get(entry)!=null?obj.get(entry).toString():"";
                    HSSFCell textcell = textRow.createCell(j);
                    textcell.setCellValue(value);
                    j++;
                }
                i++;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}