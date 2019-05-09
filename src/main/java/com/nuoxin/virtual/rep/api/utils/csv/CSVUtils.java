package com.nuoxin.virtual.rep.api.utils.csv;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 文件操作
 */
public class CSVUtils {

    /**
     * 功能说明：获取UTF-8编码文本文件开头的BOM签名。
     * BOM(Byte Order Mark)，是UTF编码方案里用于标识编码的标准标记。例：接收者收到以EF BB BF开头的字节流，就知道是UTF-8编码。
     * @return UTF-8编码文本文件开头的BOM签名
     */
    public static String getBOM() {
        byte b[] = {(byte)0xEF, (byte)0xBB, (byte)0xBF};
        return new String(b);
    }

    /**
     * 生成并csv文件
     * @param map
     * @param outPutPath
     * @param fileName
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public static BufferedWriter createCSVFile(LinkedHashMap map, String outPutPath,String fileName) throws IOException{
        //创建一个文件
        File csvFile = CSVUtils.createFile(outPutPath, fileName);
        BufferedWriter csvFileOutputStream=CSVUtils.createTitle(map, csvFile);
        //生成表头
        return  csvFileOutputStream;
    }


    /**
     * 创建一个文件
     * @param outPutPath
     * @param fileName
     * @return
     * @throws IOException
     */
    private static File createFile(String outPutPath, String fileName) throws IOException {
        File csvFile;
        File file = new File(outPutPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        //定义文件名格式并创建
        csvFile =new File(outPutPath+fileName);
        if(csvFile.exists()){
            csvFile.delete();
        }
        csvFile.createNewFile();
        return csvFile;
    }

    /**
     * 创建标题
     * @param map
     * @param csvFile
     * @return
     * @throws IOException
     */
    private static BufferedWriter createTitle(LinkedHashMap map, File csvFile) throws IOException {
        // UTF-8使正确读取分隔符","
        //如果生产文件乱码，windows下用gbk，linux用UTF-8
        BufferedWriter csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"), 1024);
        //写入前段字节流，防止乱码
        csvFileOutputStream.write(getBOM());
        // 写入文件头部
        for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext();) {
            Map.Entry propertyEntry = (Map.Entry) propertyIterator.next();
            csvFileOutputStream.write((String) propertyEntry.getValue() != null ? (String) propertyEntry.getValue() : "" );
            if (propertyIterator.hasNext()) {
                csvFileOutputStream.write(",");
            }
        }
        return csvFileOutputStream;
    }

    /**
     * 生成数据
     * @param exportData
     * @param map
     * @param csvFileOutputStream
     * @throws IOException
     */
    public static void createData(List exportData, LinkedHashMap map, BufferedWriter csvFileOutputStream) throws IOException {
        csvFileOutputStream.newLine();
        // 写入文件内容
        for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
            Object row = iterator.next();
            for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext();) {
                Map.Entry propertyEntry = (Map.Entry) propertyIterator.next();
                String str="";
                if(row!=null){
                    str=((Map)row).get(propertyEntry.getKey())!=null?((Map)row).get(propertyEntry.getKey()).toString():"";
                }
                if(!StringUtils.isEmpty(str)){
                    str=str.replaceAll("\"","“");
                    if(str.indexOf(",")>=0){
                        str=str.replaceAll(",","，");
                    }
                }
                csvFileOutputStream.write(str);
                if (propertyIterator.hasNext()) {
                    csvFileOutputStream.write(",");
                }
            }
            if (iterator.hasNext()) {
                csvFileOutputStream.newLine();
            }
        }
    }

    /**
     * fileFlush
     * @param csvFileOutputStream
     * @throws IOException
     */
    public static void fileFlush(BufferedWriter csvFileOutputStream) throws IOException{
        csvFileOutputStream.flush();
    }

    /**
     * 下载文件
     *
     * @param response
     * @param outPutPath
     *            文件路径
     * @param fileName
     *            文件名称
     * @throws IOException
     */
    public static void exportFile(HttpServletResponse response,
                                  String outPutPath, String fileName) throws IOException {
        InputStream in = null;
        try {
            in = new FileInputStream(outPutPath+fileName);
            int len = 0;
            byte[] buffer = new byte[1024];
            OutputStream out = response.getOutputStream();
            response.reset();
            response.setContentType("application/csv;charset=UTF-8");
            response.setHeader("Content-Disposition","attachment; filename=" + new String((fileName.toString()).getBytes("gb2312"), "ISO-8859-1"));
            response.setCharacterEncoding("UTF-8");
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.close();
        } catch (FileNotFoundException e) {
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 删除单个文件
     * @param filePath
     *     文件目录路径
     * @param fileName
     *     文件名称
     */
    public static void deleteFile(String filePath, String fileName) {
        File file = new File(filePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    System.out.printf(files[i].getName());
                    if (files[i].getName().equals(fileName)) {
                        files[i].delete();
                        return;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        String str="\"12,";
        str=str.replaceAll("\"","“");
        if(str.indexOf(",")>=0){
            str=str.replaceAll(",","，");
        }
        System.out.println(str);
    }

}