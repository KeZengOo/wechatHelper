package com.nuoxin.virtual.rep.api.entity.v3_0.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName VisitDataParam
 * @Description 拜访数据用于mapper中参数传递
 * @Author dangjunhui
 * @Date 2019/5/17 11:30
 * @Version 1.0
 */
@Data
public class VisitDataParam implements Serializable {

    private static final long serialVersionUID = 8869738936423849885L;

    /**
     * 产品id
     */
    private Long proId;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 销售代表id
     */
    private List<Long> list;

}
