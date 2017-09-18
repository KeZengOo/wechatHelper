package com.nuoxin.virtual.rep.api.common.util;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created by fenggang on 9/18/17.
 */
public class FileUtils {
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 创建单个文件
     *
     * @param descFileName
     *            文件名，包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createFile(String descFileName) {
        File file = new File(descFileName);
        if (file.exists()) {
            logger.debug("文件 " + descFileName + " 已存在!");
            return false;
        }
        if (descFileName.endsWith(File.separator)) {
            logger.debug(descFileName + " 为目录，不能创建目录!");
            return false;
        }
        if (!file.getParentFile().exists()) {
            // 如果文件所在的目录不存在，则创建目录
            if (!file.getParentFile().mkdirs()) {
                logger.debug("创建文件所在的目录失败!");
                return false;
            }
        }

        // 创建文件
        try {
            if (file.createNewFile()) {
                logger.debug(descFileName + " 文件创建成功!");
                return true;
            } else {
                logger.debug(descFileName + " 文件创建失败!");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(descFileName + " 文件创建失败!");
            return false;
        }

    }

    /**
     * 创建目录
     *
     * @param descDirName
     *            目录名,包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createDirectory(String descDirName) {
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        if (descDir.exists()) {
            logger.debug("目录 " + descDirNames + " 已存在!");
            return false;
        }
        // 创建目录
        if (descDir.mkdirs()) {
            logger.debug("目录 " + descDirNames + " 创建成功!");
            return true;
        } else {
            logger.debug("目录 " + descDirNames + " 创建失败!");
            return false;
        }

    }

    /**
     * 修正路径，将 \\ 或 / 等替换为 File.separator
     *
     * @param path
     *            待修正的路径
     * @return 修正后的路径
     */
    public static String path(String path) {
        String p = StringUtils.replace(path, "\\", "/");
        p = StringUtils.join(StringUtils.split(p, "/"), "/");
        if (!StringUtils.startsWithAny(p, "/") && StringUtils.startsWithAny(path, "\\", "/")) {
            p += "/";
        }
        if (!StringUtils.endsWithAny(p, "/") && StringUtils.endsWithAny(path, "\\", "/")) {
            p = p + "/";
        }
        if (path != null && path.startsWith("/")) {
            p = "/" + p; // linux下路径
        }
        return p;
    }

    /**
     * 获目录下的文件列表
     *
     * @param dir
     *            搜索目录
     * @param searchDirs
     *            是否是搜索目录
     * @return 文件列表
     */
    public static List<String> findChildrenList(File dir, boolean searchDirs) {
        List<String> files = Lists.newArrayList();
        for (String subFiles : dir.list()) {
            File file = new File(dir + "/" + subFiles);
            if (((searchDirs) && (file.isDirectory())) || ((!searchDirs) && (!file.isDirectory()))) {
                files.add(file.getName());
            }
        }
        return files;
    }

    /**
     * 获取文件扩展名(返回小写)
     *
     * @param fileName
     *            文件名
     * @return 例如：test.jpg 返回： jpg
     */
    public static String getFileExtension(String fileName) {
        if ((fileName == null) || (fileName.lastIndexOf(".") == -1)
                || (fileName.lastIndexOf(".") == fileName.length() - 1)) {
            return null;
        }
        return StringUtils.lowerCase(fileName.substring(fileName.lastIndexOf(".") + 1));
    }

    /**
     * 获取文件名，不包含扩展名
     *
     * @param fileName
     *            文件名
     * @return 例如：d:\files\test.jpg 返回：d:\files\test
     */
    public static String getFileNameWithoutExtension(String fileName) {
        if ((fileName == null) || (fileName.lastIndexOf(".") == -1)) {
            return null;
        }
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * 加载文件属性
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static Map<String, String> loadFileProperties(String filePath) throws IOException {
        if (null == filePath || "".equals(filePath.trim())) {
            System.out.println("The file path is null,return");
            return null;
        }
        filePath = filePath.trim();
        // 获取资源文件
        InputStream is = FileUtils.class.getClassLoader().getResourceAsStream(filePath);
        // 从输入流中读取属性列表
        Properties prop = new Properties();
        prop.load(is);
        // 返回Properties中包含的key-value的Set视图
        Set<Map.Entry<Object, Object>> set = prop.entrySet();
        // 返回在此Set中的元素上进行迭代的迭代器
        Iterator<Map.Entry<Object, Object>> it = set.iterator();
        String key = null, value = null;
        Map<String, String> map = new HashMap<String, String>();
        // 循环取出key-value
        while (it.hasNext()) {
            Map.Entry<Object, Object> entry = it.next();
            key = String.valueOf(entry.getKey());
            value = String.valueOf(entry.getValue());
            // 将key-value放入map中
            map.put(key, value);
        }
        return map;
    }

    public static String getFileSeparator() {
        return File.separator;
    }

    static String imgNames = "bmp,dib,gif,jfif,jpe,jpeg,jpg,png,tif,tiff,ico";
    /**
     * 判定是否为图片
     * bmp,dib,gif,jfif,jpe,jpeg,jpg,png,tif,tiff,ico
     * @param suffixName 后缀名
     * @return true:是，false:不是图片
     */
    public static boolean isImg(String suffixName){
        return imgNames.contains(suffixName);
    }
}
