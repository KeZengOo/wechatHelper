package com.nuoxin.virtual.rep.api.utils.csv;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PublicGlobalCSVExprot {
    /**
     * 导出CSV文件公共方法
     * @param response
     * @param map csv中的标题
     * @param exportData 导出的数据list
     * @param fileds 标题对应的实体字段
     * @param fileName 下载的文件名称
     */
    public static void exportCSVFile(HttpServletResponse response, HashMap map, List exportData, String[] fileds, String fileName) {
        try {
            File tempFile = File.createTempFile("vehicle", ".csv");
            BufferedWriter csvFileOutputStream = null;
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "GBK"), 1024);
            for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext(); ) {
                java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
                csvFileOutputStream.write((String) propertyEntry.getValue() != null ? new String(((String) propertyEntry.getValue()).getBytes("GBK"), "GBK") : "");
                if (propertyIterator.hasNext()) {
                    csvFileOutputStream.write(",");
                }
            }
            csvFileOutputStream.write("\r\n");

            for (int j = 0; exportData != null && !exportData.isEmpty() && j < exportData.size(); j++) {
                Class clazz = exportData.get(j).getClass();
                String[] contents = new String[fileds.length];
                for (int i = 0; fileds != null && i < fileds.length; i++) {
                    String filedName = toUpperCaseFirstOne(fileds[i]);
                    Object obj = null;
                    try {
                        Method method = clazz.getMethod(filedName);
                        method.setAccessible(true);
                        obj = method.invoke(exportData.get(j));
                    } catch (Exception e) {
                    }
                    String str = String.valueOf(obj);
                    if (str == null || str.equals("null")) {
                        str = "";
                    }
                    contents[i] = str;
                }
                for (int n = 0; n < contents.length; n++) {

                    csvFileOutputStream.write(contents[n]);
                    csvFileOutputStream.write(",");
                }
                csvFileOutputStream.write("\r\n");
            }
            csvFileOutputStream.flush();

            java.io.OutputStream out = response.getOutputStream();
            byte[] b = new byte[10240];
            java.io.File fileLoad = new java.io.File(tempFile.getCanonicalPath());
            response.reset();
            response.setContentType("application/csv");
            String trueCSVName = fileName;
            response.setHeader("Content-Disposition", "attachment; filename = "+ new String(trueCSVName.getBytes("GBK"), "ISO8859-1"));
            long fileLength = fileLoad.length();
            String length1 = String.valueOf(fileLength);
            response.setHeader("Content_Length", length1);
            java.io.FileInputStream in = new java.io.FileInputStream(fileLoad);
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将第一个字母转换为大写字母并和get拼合成方法
     * @param origin
     * @return string
     */
    private static String toUpperCaseFirstOne(String origin) {
        StringBuffer sb = new StringBuffer(origin);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        sb.insert(0, "get");
        return sb.toString();
    }
}
