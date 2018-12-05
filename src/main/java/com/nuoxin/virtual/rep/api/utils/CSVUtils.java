package com.nuoxin.virtual.rep.api.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 配合common-csv-1.6.jar使用工具类
 * @author tiancun
 * @date 2018-12-05
 */
public class CSVUtils {


    /**读取csv文件
     * @param filePath 文件路径
     * @param headers csv列头
     * @return CSVRecord 列表
     * @throws IOException **/
    public static List<CSVRecord> readCSV(String filePath, String[] headers){

        CSVParser parser = null;
        FileReader fileReader = null;
        try {
            //创建CSVFormat
            CSVFormat formator = CSVFormat.DEFAULT.withHeader(headers);

            fileReader=new FileReader(filePath);

            //创建CSVParser对象
            parser=new CSVParser(fileReader,formator);

            List<CSVRecord> records=parser.getRecords();
            return records;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (parser != null){
                try {
                    parser.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fileReader != null){
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return null;
    }



    /**读取csv文件
     * @param inputStream 文件输入流
     * @param headers csv列头
     * @return CSVRecord 列表
     * @throws IOException **/
    public static List<CSVRecord> readCSV(InputStream inputStream, String[] headers){

        CSVParser parser = null;
        try {
            //创建CSVFormat
            CSVFormat formator = CSVFormat.DEFAULT.withHeader(headers);
            parser = CSVParser.parse(inputStream, Charset.defaultCharset(), formator);
            List<CSVRecord> records=parser.getRecords();
            return records;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (parser != null){
                try {
                    parser.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }



        return null;
    }


}
