package com.naxions.www.wechathelper.util;

import android.util.Log;

import com.naxions.www.wechathelper.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * @Author: zengke
 * @Date: 2018.12
 *
 */
public class FileUtil {

    private static FileOutputStream fs;
    private static InputStream inStream;

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int byteRead = 0;
            File oldFile = new File(oldPath);
            //文件存在时
            if (oldFile.exists()) {
                //读入原文件
                inStream = new FileInputStream(oldPath);
                fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteRead);
                }
            }

        } catch (Exception e) {
            if (MainActivity.isDebug) {
                Log.e("copyFile", "复制单个文件操作出错");
            }
            e.printStackTrace();
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
                if (fs != null) {
                    fs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
