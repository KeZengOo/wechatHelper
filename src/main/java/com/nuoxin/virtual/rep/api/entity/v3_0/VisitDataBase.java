package com.nuoxin.virtual.rep.api.entity.v3_0;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName VisitDataBase
 * @Description 拜访数据基本信息
 * @Author dangjunhui
 * @Date 2019/5/14 18:08
 * @Version 1.0
 */
@Data
public class VisitDataBase implements Serializable {

    private static final long serialVersionUID = 7939953568284356790L;

    /**
     * 产品id
     */
    private Long proId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 拜访方式
     */
    private String visitWay;

    /**
     * 类型
     */
    private String saleType;

}
