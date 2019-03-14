package com.nuoxin.virtual.rep.api.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangyang on 2019/3/1.
 */
public class HospitalLevelUtil {
    private static final Map<String, String> levelMap = new HashMap<String, String>();
    static{
        levelMap.put("0", "未知");
        levelMap.put("11", "一级甲等");
        levelMap.put("12", "一级乙等");
        levelMap.put("13", "一级丙等");
        levelMap.put("14", "一级特等");
        levelMap.put("21", "二级甲等");
        levelMap.put("22", "二级乙等");
        levelMap.put("23", "二级丙等");
        levelMap.put("31", "三级甲等");
        levelMap.put("32", "三级乙等");
        levelMap.put("33", "三级丙等");
        levelMap.put("40", "特级医院");
        levelMap.put("50", "民营医院");
    }

    public static String getLevelNameByLevelCode(String levelCode){
        return levelMap.containsKey(levelCode) ? levelMap.get(levelCode) : "未知";
    }
}
