package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName VisitDataRequest
 * @Description 拜访数据请求对象
 * @Author dangjunhui
 * @Date 2019/5/14 14:30
 * @Version 1.0
 */
@Data
public class VisitDataRequest extends CommonRequest implements Serializable {

    private static final long serialVersionUID = -7788364251117380864L;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

//    private boolean userFlag;

//    private boolean proFlag;


//    public void hasFilter() {
//        if(super.drugUserIdList != null && super.drugUserIdList.size() > 0) {
//            userFlag = true;
//        }
//        if(super.productIdList != null && super.productIdList.size() > 0) {
//            proFlag = true;
//        }
//    }

}
