package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName CoverageCallResponse
 * @Description 电话覆盖
 * @Author dangjunhui
 * @Date 2019/6/18 18:39
 * @Version 1.0
 */
@Data
public class CoverageCallResponse implements Serializable {

    private String timeStr;

//    private Integer recruitNum;
    private Integer coverageNum;

    private Integer coverageCount;

    private String totalTime;

}
