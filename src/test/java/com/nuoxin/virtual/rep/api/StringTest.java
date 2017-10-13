package com.nuoxin.virtual.rep.api;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fenggang on 10/13/17.
 */
public class StringTest {

    /**
     * 正则表达式匹配两个指定字符串中间的内容
     * @param soap
     * @return
     */
    public static List<String> getSubUtil(String soap, String rgex){
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            int i = 1;
            list.add(m.group(i));
            i++;
        }
        return list;
    }

    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样
     * @param soap
     * @param rgex
     * @return
     */
    public static String getSubUtilSimple(String soap,String rgex){
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while(m.find()){
            return m.group(1);
        }
        return "";
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        String str = "sadffa<u>&nbsp;</u>fasdf<u>&nbsp;&nbsp;&nbs</u>fsg<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>fgs";
        String rgex = "<u>(.*?)</u>";
        System.out.println(getSubUtil(str,rgex));
        System.out.println(getSubUtilSimple(str, rgex));
    }
}
