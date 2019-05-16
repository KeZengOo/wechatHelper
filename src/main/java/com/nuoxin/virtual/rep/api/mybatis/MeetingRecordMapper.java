package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v3_0.excel.MeetingParticipantsExcel;
import com.nuoxin.virtual.rep.api.entity.v3_0.excel.MeetingSubjectExcel;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.MeetingAttendDetailsParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.MeetingParticipantsParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.MeetingRecordParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.MeetingSubjectParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.MeetingAttendDetailsRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.MeetingRecordRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会议记录查询Mapper
 * @author wujiang
 * @date 20190429
 */
public interface MeetingRecordMapper {

    /**
     * 会议记录查询列表
     * @param meetingRecordRequest
     * @return list
     */
    List<MeetingRecordParams> getMeetingRecordList(@Param("meetingRecordRequest") MeetingRecordRequest meetingRecordRequest);

    /**
     * 会议记录查询列表总数
     * @param meetingRecordRequest
     * @return int
     */
    Integer getMeetingRecordListCount(@Param("meetingRecordRequest") MeetingRecordRequest meetingRecordRequest);

    /**
     * 获取产品下的所有招募医生
     * @param productId
     * @return int
     */
    Integer getRecruitHcpAllCountByProduct(@Param("productId") Integer productId);

    /**
     * 查询每个会议的主题列表
     * @param productId
     * @param meetingName
     * @return list
     */
    List<MeetingSubjectParams> getMeetingSubjectListByProductIdAndMeetingName(@Param("productId") Integer productId,@Param("meetingName") String meetingName);

    /**
     * 根据会议ID查询会议详情
     * @param meetingId
     * @return MeetingRecordParams
     */
    MeetingRecordParams getMeetingInfoByMeetingId(@Param("meetingId") Long meetingId);

    /**
     * 参会列表
     * @param meetingAttendDetailsRequest
     * @return list
     */
    List<MeetingAttendDetailsParams> getMeetingAttendDetailsListByMeetingId(@Param("meetingAttendDetailsRequest") MeetingAttendDetailsRequest meetingAttendDetailsRequest);

    /**
     * 参会总数
     * @param meetingId
     * @return int
     */
    Integer getMeetingAttendDetailsCountByMeetingId(@Param("meetingId") Long meetingId);

    /**
     * 根据医生ID和会议ID查询该医生的所有参会时间
     * @param meetingId
     * @param doctorId
     * @return list
     */
    List<MeetingAttendDetailsParams> getMeetingTimeByDoctorIdAndMeetingID(@Param("doctorId") Long doctorId, @Param("meetingId") Long meetingId);


    /**
     * 会议导入Excel
     * @param list
     * @return boolean
     */
    boolean saveExcel(@Param("list")List<MeetingSubjectExcel> list);

    /**
     * 参会导入Excel
     * @param list
     * @return boolean
     */
    boolean saveMeetingParticipantsExcel(@Param("list")List<MeetingParticipantsExcel> list);

    /**
     * 根据meetingId获取会议项目ID
     * @param meetingId
     * @return MeetingParticipantsParams
     */
    MeetingParticipantsParams getMeetingItemIdByMeetingId(@Param("meetingId") String meetingId);

    /**
     * 根据医生手机号获取医生信息
     * @param doctorTel
     * @return MeetingParticipantsParams
     */
    MeetingParticipantsParams getDoctorInfoByDoctorTel(@Param("doctorTel") String doctorTel);

    /**
     * 根据会议标题查询主题数
     * @param title
     * @return int
     */
    Integer getSubjectCountByMeetingTitle(@Param("title") String title);

    /**
     * 会议编辑，根据会议名称追加会议主题的产品ID
     * @param meetingName
     * @param productId
     * @param productName
     * @param id
     * @return int
     */
    boolean updateMeetingSubjectProductIdByMeetingName(@Param("meetingName")String meetingName, @Param("productId")Integer productId, @Param("productName")String productName, @Param("id")Integer id);

    /**
     * 根据会议id查询参会人员总数及人员idList
     * @param meetingId
     */
    List<MeetingAttendDetailsParams> getMeetingAttendDetailsParamsListByMeetingId(@Param("meetingId")Integer meetingId);

    /**
     * 根据产品Id筛选医生IDlist
     * @param productId
     * @param list
     * @return int
     */
    Integer getDoctorIdsCountByProductId(@Param("productId")Integer productId, @Param("list") List<MeetingAttendDetailsParams> list);
}
