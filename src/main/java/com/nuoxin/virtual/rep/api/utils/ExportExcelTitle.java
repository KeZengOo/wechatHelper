package com.nuoxin.virtual.rep.api.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @className ExportExcel
 * List集合导出成Excel表格工具类
 */
public class ExportExcelTitle {

    public static Map<String, String> getStatisticsListTitleMap(){
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("drugUserId","销售代表id");
        map.put("drugUserName","销售代表名称");
        map.put("visitDoctorNum","拜访医生数");
        map.put("contactDoctorNum","接触医生数");
        map.put("successDoctorNum","成功医生数");
        map.put("recruitDoctorNum","招募医生数");
        map.put("coverDoctorNum","覆盖医生数");
        map.put("potentialDoctorHighNum","高潜力医生数");
        map.put("potentialDoctorMiddleNum","中潜力医生数");
        map.put("potentialDoctorLowNum","低潜力医生数");
        map.put("wxSendNum","微信发送消息人数");
        map.put("wxReplyNum","微信回复消息人数");
        map.put("contentSendNum","内容发送人数");
        map.put("contentReadNum","内容阅读人数");
        map.put("contentReadRate","内容阅读率");
        map.put("contentReadTime","内容阅读时长");
        return map;
    }

}