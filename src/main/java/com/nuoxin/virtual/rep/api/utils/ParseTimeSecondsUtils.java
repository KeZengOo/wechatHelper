package com.nuoxin.virtual.rep.api.utils;


/**
 * 把秒数转换成天时分秒
 * @author wujiang
 * @date 20190513
 */
public class ParseTimeSecondsUtils {
    /**
     * 返回日时分秒
     * @param second
     * @return
     */
    public static String secondToTime(long second) {
        //转换天数
        long days = second / 86400;
        //剩余秒数
        second = second % 86400;
        //转换小时数
        long hours = second / 3600;
        //剩余秒数
        second = second % 3600;
        //转换分钟
        long minutes = second / 60;
        //剩余秒数
        second = second % 60;
        if (0 < days){
            return days + "天"+hours+"小时"+minutes+"分"+second+"秒";
        }else {
            return hours+"小时"+minutes+"分"+second+"秒";
        }
    }
}
