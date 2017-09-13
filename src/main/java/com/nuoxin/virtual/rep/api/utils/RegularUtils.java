package com.nuoxin.virtual.rep.api.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * @author tiancun
 */
public class RegularUtils {

    /**
     *
     * @param regEx 正则表达式
     * @param text 要匹配的文本
     * @return
     */
    public static Matcher getMatcher(String regEx, String text) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(text);
        return matcher;
    }



    /**
     *
     * @param regEx 正则表达式
     * @param text 要匹配的文本
     * @return
     */
    public static boolean isMatcher(String regEx, String text) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(text);
        boolean matches = matcher.matches();
        return matches;
    }
}