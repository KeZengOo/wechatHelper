package com.nuoxin.virtual.rep.api.web.controller.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v3_0.ScheduleResult;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.MeetingRecordParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.MeetingSubjectParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.MeetingRecordRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.MeetingSubjectRequest;
import com.nuoxin.virtual.rep.api.service.v3_0.MeetingRecordService;
import com.nuoxin.virtual.rep.api.service.v3_0.WenJuanQuestionnaireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 会议记录查询
 * @author wujiang
 * @date 20190429
 */
@Api(value = "V3_0 会议记录查询相关接口")
@RequestMapping(value = "/meetingRecordApi")
@RestController
public class MeetingRecordController {

    @Resource
    private MeetingRecordService meetingRecordService;

    @ApiOperation(value = "会议记录查询列表")
    @RequestMapping(value = "/getMeetingRecordList", method = { RequestMethod.POST})
    public DefaultResponseBean<PageResponseBean<List<MeetingRecordParams>>> getMeetingRecordList(@RequestBody @Valid MeetingRecordRequest meetingRecordRequest){
        PageResponseBean<List<MeetingRecordParams>> list = meetingRecordService.getMeetingRecordList(meetingRecordRequest);
        DefaultResponseBean<PageResponseBean<List<MeetingRecordParams>>> responseBean = new DefaultResponseBean<PageResponseBean<List<MeetingRecordParams>>>();
        responseBean.setData(list);
        return responseBean;
    }

    @ApiOperation(value = "查询每个会议的主题列表")
    @RequestMapping(value = "/getMeetingSubjectListByProductIdAndMeetingName", method = { RequestMethod.POST})
    public DefaultResponseBean<List<MeetingSubjectParams>> getMeetingSubjectListByProductIdAndMeetingName(@RequestBody @Valid MeetingSubjectRequest meetingSubjectRequest){
        List<MeetingSubjectParams> list = meetingRecordService.getMeetingSubjectListByProductIdAndMeetingName(meetingSubjectRequest);
        DefaultResponseBean<List<MeetingSubjectParams>> responseBean = new DefaultResponseBean<List<MeetingSubjectParams>>();
        responseBean.setData(list);
        return responseBean;
    }
}
