package com.nuoxin.virtual.rep.api.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiancun on 17/8/3.
 */
public class CollectionUtil<T> {


    /**
     * List中分页
     * @param dataList
     * @param page
     * @param pageSize
     * @return
     */
    public static<T>  List<T> getListPage(List<T> dataList, Integer page, Integer pageSize){
        List<T> list = new ArrayList<>();
        if (dataList == null && dataList.size() == 0){
            return list;
        }

        int totalCount = dataList.size();
        int pageCount = 0;
        int m = totalCount % pageSize;
        if (m > 0){
            pageCount = totalCount/pageSize+1;
        } else{
            pageCount = totalCount/pageSize;
        }

        if (page > totalCount){
            return list;
        }

        if (page == pageCount){
            List<T> subList = dataList.subList((page-1) * pageSize,totalCount);
            list.addAll(subList);
        }else{
            List<T> subList = dataList.subList((page-1) * pageSize, pageSize * page);
            list.addAll(subList);
        }

//        if (m == 0){
//            List<ConsultDetail> subList = sortconsultDetailList.subList((page-1) * pageSize,pageSize * (page));
//            list.addAll(subList);
//        }else{
//            if (page == pageCount){
//                List<ConsultDetail> subList = sortconsultDetailList.subList((page-1) * pageSize,totalCount);
//                list.addAll(subList);
//            }else{
//                List<ConsultDetail> subList = sortconsultDetailList.subList((page-1) * pageSize,pageSize * (page));
//                list.addAll(subList);
//            }
//
//
//        }

        return list;
    }




}
