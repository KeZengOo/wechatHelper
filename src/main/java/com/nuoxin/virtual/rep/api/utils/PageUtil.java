package com.nuoxin.virtual.rep.api.utils;

/**
 * 分页中的公用部分
 * @author tiancun
 * @date 2018-05-03
 */
public class PageUtil {

    /**
     * 得到要分的页数
     * @param count 要分页的总数
     * @param pageSize 每页要分的数量
     * @return 成功返回要分的页数，否则返回 0
     */
    public static int getTotalPage(int count,int pageSize){
        if (pageSize <= 0){
            pageSize = 10;
        }

        int totalPage = count/pageSize;
        int mod = count % pageSize;
        if(mod > 0){
            totalPage = totalPage + 1;
        }

        return totalPage;
    }
}
