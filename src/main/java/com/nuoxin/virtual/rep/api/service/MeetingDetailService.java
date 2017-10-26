package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.FileFormatException;
import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.dao.DoctorRepository;
import com.nuoxin.virtual.rep.api.dao.MeetingDetailRepository;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.entity.MeetingDetail;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.utils.ExcelUtils;
import com.nuoxin.virtual.rep.api.utils.RegularUtils;
import com.nuoxin.virtual.rep.api.utils.StringFormatUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.vo.MeetingDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 会议详情相关
 * Create by tiancun on 2017/10/11
 */
@Service
public class MeetingDetailService extends BaseService{

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private MeetingDetailRepository meetingDetailRepository;

    /**
     * 导入会议详情记录
     *
     * @param file 会议详情的excel文件
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean importExcel(Long meetingId, MultipartFile file){

        boolean flag = false;
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();

        } catch (IOException e) {
            logger.error("得到上传文件的输入流失败。。" + e);
            logger.error("得到上传文件的输入流失败。。" + e.getMessage());
            e.printStackTrace();
            throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR);
        }

        String originalFilename = file.getOriginalFilename();

        if (!originalFilename.endsWith(RegularUtils.EXTENSION_XLS) && !originalFilename.endsWith(RegularUtils.EXTENSION_XLSX)) {
            throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR);
        }

        ExcelUtils<MeetingDetailVo> excelUtils = new ExcelUtils<>(new MeetingDetailVo());
        List<MeetingDetailVo> meetingDetailVos = null;
        try {
            meetingDetailVos = excelUtils.readFromFile(null, inputStream);
        } catch (Exception e) {
            logger.error("读取上传的excel文件失败。。", e.getMessage());
            e.printStackTrace();
            throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR);
        }

        if (null == meetingDetailVos || meetingDetailVos.size() <= 0) {
            return false;
        }

        List<MeetingDetail> meetingDetails = new ArrayList<>();
        for (MeetingDetailVo meetingDetailVo:meetingDetailVos){
            if (meetingDetailVo != null){
                MeetingDetail meetingDetail = new MeetingDetail();
                meetingDetail.setMeetingId(meetingId);
                Date meetingStartTime = meetingDetailVo.getAttendStartTime();
                Date meetingEndTime = meetingDetailVo.getAttendEndTime();
                meetingDetail.setAttendStartTime(DateUtil.getDateTimeString(meetingStartTime));
                meetingDetail.setAttendEndTime(DateUtil.getDateTimeString(meetingEndTime));
                Long meetingEnd = meetingEndTime.getTime();
                Long meetingStart = meetingStartTime.getTime();
                int m = (int)((meetingEnd - meetingStart)/(1000*60));
                meetingDetail.setAttendSumTime(m);
                String telephone = meetingDetailVo.getTelephone();
                telephone = StringFormatUtil.getTelephoneStr(telephone);
                boolean matcher = RegularUtils.isMatcher(RegularUtils.MATCH_TELEPHONE, telephone);
                if (!matcher){
                    throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR, "手机号输入有误，请检查是否是文本格式");
                }
                Doctor doctor = doctorRepository.findTopByMobile(telephone);
                if (doctor != null){

                    List<MeetingDetail> meetingDetailList = meetingDetailRepository.findByMeetingIdAndDoctorId(meetingId, doctor.getId());
                    if (meetingDetailList != null && meetingDetailList.size() > 0){
                        meetingDetailRepository.deleteAllByMeetingIdAndDoctorId(meetingId,doctor.getId());
                    }

                    meetingDetail.setTelephone(telephone);
                    meetingDetail.setDoctorId(doctor.getId());
                    meetingDetail.setDoctorName(doctor.getName());
                }

                meetingDetail.setAttendType(meetingDetailVo.getAttendType());
                meetingDetail.setAttendWay(meetingDetailVo.getAttendWay());
                meetingDetail.setDownload(meetingDetailVo.getDownload());
                meetingDetail.setCreateTime(new Date());

                meetingDetails.add(meetingDetail);
            }

        }

        if (null != meetingDetails && !meetingDetails.isEmpty()){
            meetingDetailRepository.save(meetingDetails);
        }
        flag = true;
        return flag;
    }

}
