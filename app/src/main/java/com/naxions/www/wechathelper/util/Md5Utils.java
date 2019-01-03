package com.naxions.www.wechathelper.util;

import java.security.MessageDigest;
/**
 * zengke 2018 12
 */
public class Md5Utils {
    /***
     * MD5加密 生成32位md5码
     * @param inStr  "mm" + uid
     * @return 返回32位md5码
     */
    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

}
