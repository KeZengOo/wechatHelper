package com.nuoxin.virtual.rep.api.utils;

import com.nuoxin.virtual.rep.api.entity.v2_5.DynamicFieldResponse;

import java.util.*;

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

    public static Map<String, String> getDoctorVisitDetaiListTitleMap(List<DynamicFieldResponse> list){
        Map<String, String> map=new LinkedHashMap<>();
        map.put("drugUserName","代表");
        map.put("visitTime","拜访时间");
        map.put("doctorId","医生ID");
        map.put("doctorName","医生姓名");
        map.put("hospitalId","医院ID");
        map.put("hospitalName","医院");
        map.put("visitType","拜访方式");
        map.put("shareContent","分享内容");

        map.put("visitResult","拜访结果");
        map.put("attitude","医生态度");
        map.put("nextVisitTime","下次拜访时间");
//        map.put("clientLevel","客户等级");
        map.put("hcpPotential","医生潜力");
        map.put("isRecruit", "是否招募");
        map.put("isHasDrug","是否有药");
        map.put("isTarget","是否是目标客户");
        map.put("isHasAe","是否有AE");
        list.forEach(x->{
            map.put(x.getProp(),x.getLable());
        });
        return map;
    }

}