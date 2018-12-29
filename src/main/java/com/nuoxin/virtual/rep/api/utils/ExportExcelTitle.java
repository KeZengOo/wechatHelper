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
        map.put("remark", "备注");
        map.put("callText", "录音识别文本");
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


    /**
     * 内容分享表头
     * @return
     */
    public static Map<String, String> getDoctorShareListTitleMap(){
        Map<String, String> map=new LinkedHashMap<>();
        map.put("contentId","内容ID");
        map.put("title", "内容标题");
        map.put("doctorId","医生ID");
        map.put("doctorName", "医生姓名");
        map.put("hospitalName", "医院");
        map.put("depart", "科室");
        map.put("hospitalLevel", "医院级别");
        map.put("shareTime", "分享时间");
        return map;
    }

    /**
     * 内容阅读表头
     * @return
     */
    public static Map<String, String> getDoctorReadListTitleMap(){
        Map<String, String> map=new LinkedHashMap<>();
        map.put("contentId","内容ID");
        map.put("title", "内容标题");
        map.put("doctorId","医生ID");
        map.put("doctorName", "医生姓名");
        map.put("hospitalName", "医院");
        map.put("depart", "科室");
        map.put("hospitalLevel", "医院级别");
        map.put("comment", "内容评论");
        map.put("duration", "阅读时长");
        map.put("readTime", "阅读时间");
        map.put("useful", "是否有用");
        map.put("channel", "阅读渠道");
        return map;
    }


    /**
     * 内容分享表头
     * @return
     */
    public static Map<String, String> getDoctorAnswerListTitleMap(){
        Map<String, String> map=new LinkedHashMap<>();
        map.put("contentId","内容ID");
        map.put("title", "内容标题");
        map.put("doctorId","医生ID");
        map.put("doctorName", "医生姓名");
        map.put("hospitalName", "医院");
        map.put("depart", "科室");
        map.put("hospitalLevel", "医院级别");
        map.put("questionId", "问题ID");
        map.put("questionTitle", "问题标题");
        map.put("answer", "问题答案");
        map.put("createTime", "答题时间");

        return map;
    }

}