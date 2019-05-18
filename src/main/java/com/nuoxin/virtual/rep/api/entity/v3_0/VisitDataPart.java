package com.nuoxin.virtual.rep.api.entity.v3_0;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName VisitDataPart
 * @Description 拜访数据部分
 * @Author dangjunhui
 * @Date 2019/5/17 11:04
 * @Version 1.0
 */
@Data
public class VisitDataPart implements Serializable {

    private Long userId;

    private Long doctorId;

    private Integer total;

}
