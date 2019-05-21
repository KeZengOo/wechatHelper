package com.nuoxin.virtual.rep.api.web.controller.request.vo.v3_0;

import com.nuoxin.virtual.rep.api.common.annotations.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 会议导入
 * @author tiancun
 * @date 2019-05-21
 */
@Data
public class MeetingVo {

    @Excel(name = "会议名称", width = 100)
    private String meetingName;

    @Excel(name = "主题名称", width = 50)
    private String subjectName;

    @Excel(name = "演讲者")
    private String speaker;

    @Excel(name = "主题开始时间", width = 50)
    private Date subjectStartTime;

    @Excel(name = "主题结束时间", width = 50)
    private Date subjectEndTime;


}
