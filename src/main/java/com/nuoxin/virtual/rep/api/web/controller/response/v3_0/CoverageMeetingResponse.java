package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName CoverageMeetingResponse
 * @Description 会议覆盖
 * @Author dangjunhui
 * @Date 2019/6/19 15:55
 * @Version 1.0
 */
@Data
public class CoverageMeetingResponse implements Serializable {

    // 月份
    private String timeStr;

    // 会议数量
    private Integer meetingNum;

    // 参会人次
    private Integer hcpCount;

    // 参会人数
    private Integer hcpNum;

    // 总时长
    private String totalTime;

}
