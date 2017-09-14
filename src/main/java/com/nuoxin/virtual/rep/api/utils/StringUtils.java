package com.nuoxin.virtual.rep.api.utils;

/**
 * Created by fenggang on 9/14/17.
 */
public class StringUtils {

    public static boolean isNotEmtity(String obj){
        if(obj==null||"".equals(obj)||"".equals(obj.trim())){
            return false;
        }
        return true;
    }
}
