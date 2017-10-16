package com.nuoxin.virtual.rep.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Create by tiancun on 2017/10/16
 */
public class StringFormatUtil {

    private static Logger logger = LoggerFactory.getLogger(StringFormatUtil.class);
    /**
     * excel 导入手机号转换，例如将1.5712905821E10转为 15712905821
     * @param tel
     * @return
     */
    public static String getTelephoneStr(String tel){
        String telephone = "";
        try {
            BigDecimal bd = new BigDecimal("1.5712905821E10");
            String str = bd.toPlainString();
            Long i = Long.parseLong(str);
            telephone = i + "";
        }catch (Exception e){
            logger.error("excel导入手机号转换失败。。");
        }
        return telephone;
    }
}
