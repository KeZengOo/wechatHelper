package com.nuoxin.virtual.rep.api.service;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BaseException;
import com.nuoxin.virtual.rep.api.common.exception.FileFormatException;
import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.dao.MeetingDetailRepository;
import com.nuoxin.virtual.rep.api.dao.MeetingRepository;
import com.nuoxin.virtual.rep.api.dao.ProductLineRepository;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.Meeting;
import com.nuoxin.virtual.rep.api.entity.ProductLine;
import com.nuoxin.virtual.rep.api.mybatis.MeetingMapper;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.utils.ExcelUtils;
import com.nuoxin.virtual.rep.api.utils.RegularUtils;
import com.nuoxin.virtual.rep.api.web.controller.request.meeting.MeetingRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.vo.MeetingVo;
import com.nuoxin.virtual.rep.api.web.controller.response.meeting.MeetingResponseBean;

/**
 * 会议相关
 * Create by tiancun on 2017/10/11
 */
@Service
public class MeetingService extends BaseService {

    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private MeetingDetailRepository meetingDetailRepository;
    @Autowired
    private DrugUserRepository drugUserRepository;
    @Autowired
    private ProductLineRepository productLineRepository;
    @Autowired
    private MeetingMapper meetingMapper;

    /**
     * 导入会议记录
     * @param file 会议的excel文件
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean importExcel(MultipartFile file, Long productId) {
        boolean flag = false;
        
        String originalFilename = file.getOriginalFilename();
		if (!originalFilename.endsWith(RegularUtils.EXTENSION_XLS) && !originalFilename.endsWith(RegularUtils.EXTENSION_XLSX)) {
            throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR);
        }

        List<MeetingVo> meetingVos = null;
        InputStream inputStream = null;
        ExcelUtils<MeetingVo> excelUtils = new ExcelUtils<>(new MeetingVo());
        
        try {
        	inputStream = file.getInputStream();
            meetingVos = excelUtils.readFromFile(null, inputStream);
        } catch (Exception e) {
            logger.error("读取上传的excel文件失败。。", e);
            throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR);
        } finally {
        	if(inputStream != null) {
        		try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("IOException", e);
				}
        	}
        }

        if (null == meetingVos || meetingVos.size() <= 0) {
            return false;
        }

        List<Meeting> meetings = new ArrayList<>();
        for (MeetingVo meetingVo : meetingVos) {
            Meeting meeting = new Meeting();
            if (meetingVo != null) {
                meeting.setTitle(meetingVo.getTitle());
                meeting.setSpeaker(meetingVo.getSpeaker());
                meeting.setMeetingStartTime(DateUtil.getDateTimeString(meetingVo.getMeetingStartTime()));
                meeting.setMeetingEndTime(DateUtil.getDateTimeString(meetingVo.getMeetingEndTime()));
             
                ProductLine productLine = productLineRepository.getOne(productId);
                if (productLine != null){
                    meeting.setProductId(productId);
                    meeting.setProductName(productLine.getName());
                }
                meeting.setHospital(meetingVo.getHospital());
                meeting.setCreateTime(new Date());
                meeting.setUpdateTime(new Date());
                meetings.add(meeting);
            }
        }

        meetingRepository.save(meetings);

        flag = true;
        return flag;
    }

    public PageResponseBean<MeetingResponseBean> getList(MeetingRequestBean bean) {
        DrugUser drugUser = drugUserRepository.findFirstById(bean.getDrugUserId());
        String leaderPath = drugUser.getLeaderPath();
        if (leaderPath == null) {
            leaderPath = "";
        }
        bean.setLeaderPath(leaderPath + "%");
        
        String title = bean.getTitle();
        if (!StringUtils.isEmpty(title)) {
            bean.setTitle("%" + title + "%");
        }

        String productName = bean.getProductName();
        if (!StringUtils.isEmpty(productName)) {
            bean.setProductName("%" + productName + "%");
        }

        String speaker = bean.getSpeaker();
        if (!StringUtils.isEmpty(speaker)){
            bean.setSpeaker("%" + speaker + "%");
        }

        Integer page = bean.getPage();
        Integer pageSize = bean.getPageSize();
        bean.setCurrentSize(page  * pageSize);

        String meetingStartTime = bean.getMeetingStartTime();
        String meetingEndTime = bean.getMeetingEndTime();
        if (!StringUtils.isEmpty(meetingStartTime) && !StringUtils.isEmpty(meetingEndTime)) {
            int compareTo = meetingStartTime.compareTo(meetingEndTime);
            if (compareTo > 0) {
                throw new BaseException("开始时间不能比结束时间大");
            }
        }

        List<MeetingResponseBean> list = meetingMapper.getList(bean);
        Integer listCount = meetingMapper.getListCount(bean);
        PageResponseBean<MeetingResponseBean> pageResponseBean = new PageResponseBean<>(bean, listCount, list);

        return pageResponseBean;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean delete(Long id) {
        boolean flag = false;
        meetingDetailRepository.deleteAllByMeetingId(id);
        meetingRepository.delete(id);

        flag = true;
        return flag;
    }

}
