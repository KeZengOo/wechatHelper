package com.nuoxin.virtual.rep.api.web.controller.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v3_0.ScheduleResult;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.MeetingAttendDetailsParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.MeetingRecordParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.MeetingSubjectParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.MeetingAttendDetailsRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.MeetingRecordRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.MeetingSubjectRequest;
import com.nuoxin.virtual.rep.api.service.v3_0.MeetingRecordService;
import com.nuoxin.virtual.rep.api.service.v3_0.WenJuanQuestionnaireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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

    @ApiOperation(value = "参会查询列表")
    @RequestMapping(value = "/getMeetingAttendDetailsListByMeetingId", method = { RequestMethod.POST})
    public DefaultResponseBean<PageResponseBean<List<MeetingAttendDetailsParams>>> getMeetingAttendDetailsListByMeetingId(@RequestBody @Valid MeetingAttendDetailsRequest meetingAttendDetailsRequest){
        PageResponseBean<List<MeetingAttendDetailsParams>> list = meetingRecordService.getMeetingAttendDetailsListByMeetingId(meetingAttendDetailsRequest);
        DefaultResponseBean<PageResponseBean<List<MeetingAttendDetailsParams>>> responseBean = new DefaultResponseBean<PageResponseBean<List<MeetingAttendDetailsParams>>>();
        responseBean.setData(list);
        return responseBean;
    }

    @ApiOperation(value = "导入会议列表")
    @RequestMapping(value = "/meetingImport", method = {RequestMethod.POST})
    public DefaultResponseBean<Map<String, Object>> meetingImport(@RequestParam("file") @ApiParam("会议列表文件") MultipartFile file) {
        Map<String, Object> result = meetingRecordService.meetingImport(file);
        DefaultResponseBean<Map<String, Object>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(result);
        responseBean.setMessage("");
        return responseBean;
    }

    @ApiOperation(value = "导入参会列表")
    @RequestMapping(value = "/meetingParticipantsImport", method = {RequestMethod.POST})
    public DefaultResponseBean<Map<String, Object>> meetingParticipantsImport(@RequestParam("file") @ApiParam("参会列表文件") MultipartFile file, @RequestParam("meetingId") String meetingId) {
        Map<String, Object> result = meetingRecordService.meetingParticipantsImport(file,meetingId);
        DefaultResponseBean<Map<String, Object>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(result);
        responseBean.setMessage("");
        return responseBean;
    }

    @ApiOperation(value = "会议编辑，根据会议名称追加会议主题的产品ID")
    @RequestMapping(value = "/updateMeetingSubjectProductIdByMeetingName", method = {RequestMethod.GET})
    public DefaultResponseBean<Boolean> updateMeetingSubjectProductIdByMeetingName(@RequestParam("meetingName") String meetingName, @RequestParam("productId") Integer productId) {
        boolean result = meetingRecordService.updateMeetingSubjectProductIdByMeetingName(meetingName,productId);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<Boolean>();
        responseBean.setData(result);
        return responseBean;
    }
}
