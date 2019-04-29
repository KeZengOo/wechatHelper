package com.nuoxin.virtual.rep.api.service.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.MeetingRecordParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.MeetingRecordRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

}
