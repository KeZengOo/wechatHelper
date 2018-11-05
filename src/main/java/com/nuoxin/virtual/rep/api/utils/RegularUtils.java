package com.nuoxin.virtual.rep.api.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 *
 * @author tiancun
 */
public class RegularUtils {

    //excel文件扩展名校验
    public static final String EXTENSION_XLS = "xls";

    public static final String EXTENSION_XLSX = "xlsx";

    //手机号验证
    public static final String MATCH_TELEPHONE = "^1\\d{10}$";

    // 校验座机号
    public static final String MATCH_FIX_PHONE = "^(0\\d{2,3}-?)?\\d{7,8}$";

    // 匹配1开头的11位数字
    public static final String MATCH_ELEVEN_NUM = "(?<!\\d)(?:(?:1\\d{10}))";

    /**
     * @param regEx 正则表达式
     * @param text  要匹配的文本
     * @return
     */
    public static Matcher getMatcher(String regEx, String text) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(text);
        return matcher;
    }


    /**
     * @param regEx 正则表达式
     * @param text  要匹配的文本
     * @return
     */
    public static boolean isMatcher(String regEx, String text) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(text);
        boolean matches = matcher.matches();
        return matches;
    }



    public static void main(String[] args) {

        String s = "0101234567890";
        boolean matcher = RegularUtils.isMatcher(RegularUtils.MATCH_FIX_PHONE, s);
        System.out.println(matcher);


    }


}
