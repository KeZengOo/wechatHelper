package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName CoverageOverviewResponse
 * @Description 覆盖月报概览
 * @Author dangjunhui
 * @Date 2019/6/17 18:01
 * @Version 1.0
 */
@Data
public class CoverageOverviewResponse implements Serializable {

    private String timeStr;

    private Integer recruitHciNum;

    private Integer coverageHciNum;

    private String hciPercent;

    private Integer recruitHcpNum;

    private Integer coverageHcpNum;

    private String hcpPercent;

}
