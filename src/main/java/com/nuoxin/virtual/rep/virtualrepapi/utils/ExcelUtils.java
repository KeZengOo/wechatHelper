package com.nuoxin.virtual.rep.virtualrepapi.utils;

import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作excel的工具类
 */
public class ExcelUtils {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 读取excel，支持xlsx格式和xls格式
     *
     * @param filePath excel文件路径，包含文件名称
     * @return
     */
    public static List<List<String>> readExcel(String filePath) {
        List<List<String>> result = new ArrayList<>();
        InputStream inp = null;
        try {
            inp = new FileInputStream("filePath");
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            if (sheet == null) {
                logger.error("文档为空。。。。。。。。。。。。。。。。。");
                return null;
            }

            int lastRowNum = sheet.getLastRowNum();
            int firstRowNum = sheet.getFirstRowNum();
            //总行数
            int rows = lastRowNum + 1 - firstRowNum;
            if (rows > 0) {
                for (int i = 0; i < rows; i++) {
                    Row row = sheet.getRow(i);
                    if (null == row) {
                        continue;
                    }


                    //int firstCellNum = row.getFirstCellNum();
                    int lastCellNum = row.getLastCellNum();
                    //总列数
                    //int cells = lastCellNum + 1 - firstCellNum;
                    if (lastCellNum > 0) {
                        List<String> cellList = new ArrayList<>();
                        for (int j = 0; j < lastCellNum; j++) {
                            Cell cell = row.getCell(j);
                            if (null != cell) {
                                String stringCellValue = cell.getStringCellValue();
                                cellList.add(stringCellValue);
                            }
                        }

                        result.add(cellList);

                    }

                }
            }

        } catch (Exception e) {
            logger.error("读取excel异常出现错误。。", e.getMessage());

        } finally {
            if (null != inp) {
                try {
                    inp.close();
                } catch (IOException e) {
                    logger.error("关闭流异常。。。", e.getMessage());
                }
            }
        }

        return result;

    }


}