package com.nuoxin.virtual.rep.api.utils;


import org.springframework.util.StringUtils;

/**
 * 操作网址URL的工具类
 * @author tiancun
 * @date 2018-06-21
 */
public class UrlUtil {

    /**
     * 根据网址得到域名
     * @param urlStr 网址字符串
     * @return 成功返回网址对应域名，否则返回空串
     */
    public static String getDomainNameByUrl(String urlStr){
        String domainName = "";
        if (StringUtils.isEmpty(urlStr)){
            return domainName;
        }

        try {
            java.net.URL url = new java.net.URL(urlStr);
            String host = url.getHost();
            if (!StringUtils.isEmpty(host)){
                domainName = host;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return domainName;
    }

    public static void main(String[] args) {
        String domainNameByUrl = getDomainNameByUrl("https://www.baidu.com/aaa/api/sss");
        System.out.println(domainNameByUrl);
    }
}
