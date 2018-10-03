package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.FileFormatException;
import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.dao.DoctorRepository;
import com.nuoxin.virtual.rep.api.dao.MeetingDetailRepository;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.entity.MeetingDetail;
import com.nuoxin.virtual.rep.api.enums.AttendMeetingDownloadEnum;
import com.nuoxin.virtual.rep.api.enums.AttendMeetingTypeEnum;
import com.nuoxin.virtual.rep.api.enums.AttendMeetingWayEnum;
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
        String originalFilename = file.getOriginalFilename();

        if (!originalFilename.endsWith(RegularUtils.EXTENSION_XLS) && !originalFilename.endsWith(RegularUtils.EXTENSION_XLSX)) {
            throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR);
        }

		List<MeetingDetailVo> meetingDetailVos = null;
		InputStream inputStream = null;
		ExcelUtils<MeetingDetailVo> excelUtils = new ExcelUtils<>(new MeetingDetailVo());
		try {
			inputStream = file.getInputStream();
			meetingDetailVos = excelUtils.readFromFile(null, inputStream);
		} catch (Exception e) {
			logger.error("读取上传的excel文件失败。。", e);
			throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR, "读取上传的excel文件失败");
		} finally {
			if(inputStream != null) {
        		try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("IOException", e);
				}
        	}
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
                String attendWayStr = meetingDetailVo.getAttendWay();

                String telephone = meetingDetailVo.getTelephone();
                telephone = StringFormatUtil.getTelephoneStr(telephone);
                boolean matcher = RegularUtils.isMatcher(RegularUtils.MATCH_TELEPHONE, telephone);
                if (!matcher){
                    throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR, "手机号输入有误，请检查是否是文本格式");
                }

                Integer attendWay = null;
                try {
                    attendWay = (int)(Double.parseDouble(attendWayStr));
                }catch (Exception e){
                    logger.error("attendWayStr-->attendWay Error", e);
                    throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR, "手机号:" + telephone + "参会方式输入不合法");
                }

                String attendTypeStr = meetingDetailVo.getAttendType();
                Integer attendType = null;
                try {
                   attendType = (int)(Double.parseDouble(attendTypeStr));
                }catch (Exception e){
                    logger.error("attendTypeStr-->attendType Error", e);
                    throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR, "手机号:" + telephone + "参会类型输入不合法");
                }

                String downloadStr = meetingDetailVo.getDownload();
                Integer download = null;
                try {
                    download = (int)(Double.parseDouble(downloadStr));
                }catch (Exception e){
                    logger.error("downloadStr-->download Error", e);
                    throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR, "手机号:" + telephone + "是否下载输入不合法");
                }

                if (attendWay == null || (!(attendWay.equals(AttendMeetingWayEnum.WEBSITE.getType())
                        || attendWay.equals(AttendMeetingWayEnum.TELEPHONE.getType())
                        || attendWay.equals(AttendMeetingWayEnum.WECHAT.getType())))){
                    throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR, "手机号:" + telephone + "参会方式输入不合法");
                }

                if (attendType == null || (!(attendType.equals(AttendMeetingTypeEnum.ATTEND.getType())
                        || attendType.equals(AttendMeetingTypeEnum.VIEW.getType())))){
                    throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR, "手机号:" + telephone + "参会类型输入不合法");
                }


                if (download == null || (!(download.equals(AttendMeetingDownloadEnum.DOWNLOAD.getType())
                        || download.equals(AttendMeetingDownloadEnum.NO_DOWNLOAD.getType())))){
                    throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR, "手机号:" + telephone + "是否下载输入不合法");
                }

                Doctor doctor = doctorRepository.findTopByMobile(telephone);


                meetingDetail.setAttendStartTime(DateUtil.getDateTimeString(meetingStartTime));
                meetingDetail.setAttendEndTime(DateUtil.getDateTimeString(meetingEndTime));
                Long meetingEnd = meetingEndTime.getTime();
                Long meetingStart = meetingStartTime.getTime();
                int m = (int)((meetingEnd - meetingStart)/(1000*60));
                meetingDetail.setAttendSumTime(m);
                if (doctor != null){
                    meetingDetail.setDoctorId(doctor.getId());
                    meetingDetail.setDoctorName(doctor.getName());
                }

                meetingDetail.setAttendType(attendType);
                meetingDetail.setAttendWay(attendWay);
                meetingDetail.setDownload(download);
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
