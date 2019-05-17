package com.nuoxin.virtual.rep.api.utils.v3_0;

/**
 * @ClassName TimeUtil
 * @Description 时间处理类
 * @Author dangjunhui
 * @Date 2019/5/16 11:43
 * @Version 1.0
 */
public class TimeUtil {

    /**
     * 转化时间
     * @param seconds 秒
     * @return 天 时 分 秒
     */
    public static String alterCallTimeContent(Long seconds) {
        if (seconds == null || seconds == 0){
            return "";
        }

        StringBuilder timeStr = new StringBuilder("");
        timeStr.append(seconds).append("秒");
        if (seconds > 60) {
            long second = seconds % 60;
            long min = seconds / 60;
            timeStr.delete(0, timeStr.length());
            timeStr.append(min).append("分").append(second).append("秒");
            if (min > 60) {
                min = (seconds / 60) % 60;
                long hour = (seconds / 60) / 60;
                timeStr.delete(0, timeStr.length());
                timeStr.append(hour).append("小时").append(min).append("分").append(second).append("秒");
                if (hour > 24) {
                    hour = ((seconds / 60) / 60) % 24;
                    long day = (((seconds / 60) / 60) / 24);
                    timeStr.delete(0, timeStr.length());
                    timeStr.append(day).append("天").append(hour).append("小时").append(min).append("分").append(second).append("秒");
                }
            }
        }
        return timeStr.toString();
    }

}
