package com.nuoxin.virtual.rep.api;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ExcelUtilsTest {

    public static void main(String [] args){
        ///List<List<String>> result = testReadExcel();
        File file = new File("C:\\Users\\27168\\Desktop\\hh.xls");
        String name = file.getName();
        System.out.println(name);

        byte b = 'a';

    }









    public static List<List<String>> testReadExcel(){
        List<List<String>> result = new ArrayList<>();
       try {
           InputStream inp = new FileInputStream("C:\\Users\\27168\\Desktop\\hh.xls");
           //InputStream inp = new FileInputStream("workbook.xlsx");

           Workbook wb = WorkbookFactory.create(inp);
           Sheet sheet = wb.getSheetAt(0);
           if (sheet == null){
               System.out.println("文档为空。。。。。。。。。。。。。。。。。");
               return null;
           }
           int lastRowNum = sheet.getLastRowNum();
           int firstRowNum = sheet.getFirstRowNum();
           //总行数
           int rows = lastRowNum + 1 - firstRowNum;
           
           if (rows > 0){
               for (int i = 2; i< rows; i++){
                   Row row = sheet.getRow(i);
                   if (null == row){
                       continue;
                   }


                   //int firstCellNum = row.getFirstCellNum();
                   int lastCellNum = row.getLastCellNum();
                   //总列数
                   //int cells = lastCellNum + 1 - firstCellNum;
                   if (lastCellNum > 0){
                       List<String> cellList = new ArrayList<>();
                       for (int j = 0; j < lastCellNum; j++){
                           Cell cell = row.getCell(j);
                           if (null != cell){
                               String stringCellValue = cell.getStringCellValue();
                               cellList.add(stringCellValue);
                           }
                       }

                       result.add(cellList);

                   }

               }
           }

           System.out.println(result);
           inp.close();
//           Row row = sheet.getRow(2);
//           Cell cell = row.getCell(3);
//           if (cell == null)
//               cell = row.createCell(3);
//           cell.setCellType(CellType.STRING);
//           cell.setCellValue("a test");
//
//           // Write the output to a file
//           FileOutputStream fileOut = new FileOutputStream("workbook.xls");
//           wb.write(fileOut);
//           fileOut.close();
       }catch (Exception e){
           e.printStackTrace();
       }

        return result;
    }


}
