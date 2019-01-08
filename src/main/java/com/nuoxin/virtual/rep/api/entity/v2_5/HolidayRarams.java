package com.nuoxin.virtual.rep.api.entity.v2_5;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 新增节假日入参
 * @author tiancun
 * @date 2019-01-08
 */
@Data
public class HolidayRarams implements Serializable {
    private static final long serialVersionUID = -5617656204396443422L;


    /**
     * 批次ID
     */
    private String batchNo;

    /**
     * 节假日名称
     */
    private String name;

    /**
     * 节假日日期
     */
    private Date holidayDate;

}
