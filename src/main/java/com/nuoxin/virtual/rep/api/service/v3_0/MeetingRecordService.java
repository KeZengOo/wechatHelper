package com.nuoxin.virtual.rep.api.service.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.MeetingAttendDetailsParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.MeetingRecordParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.MeetingSubjectParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.MeetingAttendDetailsRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.MeetingRecordRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.MeetingSubjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 会议记录查询
 * @author wujiang
 * @date 20190429
 */
public interface MeetingRecordService {

    /**
     * 会议记录查询列表
     * @return list
     */
    PageResponseBean<List<MeetingRecordParams>> getMeetingRecordList(MeetingRecordRequest meetingRecordRequest);

    /**
     * 查询每个会议的主题列表
     * @param meetingSubjectRequest
     * @return list
     */
    List<MeetingSubjectParams> getMeetingSubjectListByProductIdAndMeetingName(MeetingSubjectRequest meetingSubjectRequest);

    /**
     * 参会列表
     * @param meetingAttendDetailsRequest
     * @return list
     */
    PageResponseBean<List<MeetingAttendDetailsParams>> getMeetingAttendDetailsListByMeetingId(MeetingAttendDetailsRequest meetingAttendDetailsRequest);

    /**
     * 导入会议列表
     * @param file
     * @return map
     */
    Map<String, Object> meetingImport(MultipartFile file);

    /**
     * 导入参会列表
     * @param file
     * @param meetId
     * @return map
     */
    Map<String, Object> meetingParticipantsImport(MultipartFile file, String meetId);
}
