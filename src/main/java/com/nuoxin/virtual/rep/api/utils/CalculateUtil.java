package com.nuoxin.virtual.rep.api.utils;

/**
 * 计算工具类
 * @author tiancun
 * @date 2019-05-16
 */
public class CalculateUtil {


    /**
     * 得到两个数的百分比 xx%
     * @param num1 分子
     * @param num2 分母
     * @param decimalPlace 保留的小数位数
     * @return
     */
    public static String getPercentage(Integer num1, Integer num2, Integer decimalPlace){

        if (decimalPlace == null || decimalPlace < 0){
            decimalPlace = 0;
        }

        if (decimalPlace > 0){
            if (num1 !=null && num1 > 0 && num2 !=null && num2 > 0){
                double v = (double) (num1 * 100) / num2;
                String s = String.format("%."+ decimalPlace +"f", v).toString();
                String rate = s.concat("%");
                return rate;
            }
        }else {
            String s = ((num1 * 100) / num2) + "";
            String rate = s.concat("").concat("%");
            return rate;
        }

        return "0%";
    }

    public static void main(String[] args) {
        String percentage = CalculateUtil.getPercentage(1, 3, 2);
        System.out.println(percentage);
    }

}
