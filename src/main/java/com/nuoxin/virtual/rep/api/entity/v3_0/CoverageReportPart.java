package com.nuoxin.virtual.rep.api.entity.v3_0;

import lombok.Data;

/**
 * @ClassName CoverageReportPart
 * @Description 覆盖月报部分
 * @Author dangjunhui
 * @Date 2019/6/17 11:59
 * @Version 1.0
 */
@Data
public class CoverageReportPart {

    private Long hciId;

    private Long hcpId;

    private String timeStr;

}
