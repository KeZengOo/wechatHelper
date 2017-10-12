package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BaseException;
import com.nuoxin.virtual.rep.api.common.exception.FileFormatException;
import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.dao.MeetingRepository;
import com.nuoxin.virtual.rep.api.entity.Meeting;
import com.nuoxin.virtual.rep.api.entity.Message;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.utils.ExcelUtils;
import com.nuoxin.virtual.rep.api.utils.RegularUtils;
import com.nuoxin.virtual.rep.api.web.controller.request.meeting.MeetingRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.vo.MeetingVo;
import com.nuoxin.virtual.rep.api.web.controller.request.vo.WechatMessageVo;
import com.nuoxin.virtual.rep.api.web.controller.response.meeting.MeetingResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.message.MessageResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 会议相关
 * Create by tiancun on 2017/10/11
 */
@Service
public class MeetingService extends BaseService {

    @Autowired
    private MeetingRepository meetingRepository;



    /**
     * 导入会议记录
     *
     * @param file 会议的excel文件
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean importExcel(MultipartFile file, Long productId, String productName) {

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

        ExcelUtils<MeetingVo> excelUtils = new ExcelUtils<>(new MeetingVo());

        List<MeetingVo> meetingVos = null;

        try {
            meetingVos = excelUtils.readFromFile(null, inputStream);
        } catch (Exception e) {
            logger.error("读取上传的excel文件失败。。", e.getMessage());
            e.printStackTrace();
            throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR);
        }

        if (null == meetingVos || meetingVos.size() <= 0) {
            return false;
        }

        List<Meeting> meetings = new ArrayList<>();
        for (MeetingVo meetingVo:meetingVos){
            Meeting meeting = new Meeting();
            if (meetingVo !=null){
                meeting.setTitle(meetingVo.getTitle());
                meeting.setSpeaker(meetingVo.getSpeaker());
                meeting.setMeetingStartTime(DateUtil.getDateTimeString(meetingVo.getMeetingStartTime()));
                meeting.setMeetingEndTime(DateUtil.getDateTimeString(meetingVo.getMeetingEndTime()));
                meeting.setProductId(productId);
                meeting.setProductName(productName);
                meeting.setCreateTime(new Date());
                meeting.setUpdateTime(new Date());
                meetings.add(meeting);
            }
        }

        meetingRepository.save(meetings);

        flag = true;
        return flag;

    }


    public PageResponseBean<MeetingResponseBean> getList(MeetingRequestBean bean){


        Specification<Meeting> specification = new Specification<Meeting>() {
            @Override
            public Predicate toPredicate(Root<Meeting> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(bean.getTitle())){
                    predicates.add(criteriaBuilder.like(root.get("title").as(String.class), "%" + bean.getTitle() + "%"));
                }

                if (!StringUtils.isEmpty(bean.getSpeaker())){
                    predicates.add(criteriaBuilder.like(root.get("speaker").as(String.class), "%" + bean.getSpeaker() + "%"));
                }

                String meetingStartTime = bean.getMeetingStartTime();
                String meetingEndTime = bean.getMeetingEndTime();
                if (!StringUtils.isEmpty(meetingStartTime)){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("meetingStartTime").as(String.class),meetingStartTime));
                }

                if (!StringUtils.isEmpty(meetingEndTime)){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("meetingEndTime").as(String.class),meetingEndTime));

                }

                if (!StringUtils.isEmpty(bean.getProductName())){
                    predicates.add(criteriaBuilder.like(root.get("productName").as(String.class), "%" + bean.getProductName() + "%"));

                }

                if (!StringUtils.isEmpty(meetingStartTime) && !StringUtils.isEmpty(meetingEndTime)){
                    int compareTo = meetingStartTime.compareTo(meetingEndTime);
                    if (compareTo > 0){
                        throw new BaseException("开始时间不能比结束时间大");
                    }
                }


                criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.and(predicates.toArray(new Predicate[0]))));
                return criteriaQuery.getRestriction();

            }
        };

        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "updateTime");
        Sort sort = new Sort(order);
        Pageable pageable = new PageRequest(bean.getPage(), bean.getPageSize(), sort);

        Page<Meeting> page = meetingRepository.findAll(specification, pageable);
        PageResponseBean<MeetingResponseBean> meetingPage = new PageResponseBean<>(page);
        List<MeetingResponseBean> meetingList = new ArrayList<>();
        List<Meeting> content = page.getContent();
        if (null != content && !content.isEmpty()){
            for (Meeting meeting:content){
                if (null != meeting){
                    MeetingResponseBean meetingResponseBean = new MeetingResponseBean();
                    meetingResponseBean.setId(meeting.getId());
                    meetingResponseBean.setTitle(meeting.getTitle());
                    meetingResponseBean.setSpeaker(meeting.getSpeaker());
                    meetingResponseBean.setMeetingStartTime(meeting.getMeetingStartTime());
                    meetingResponseBean.setMeetingEndTime(meeting.getMeetingEndTime());
                    meetingResponseBean.setProductId(meeting.getProductId());
                    meetingResponseBean.setProductName(meeting.getProductName());
                    meetingList.add(meetingResponseBean);
                }
            }
        }

        meetingPage.setContent(meetingList);

        return meetingPage;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean delete(Long id){

        return true;
    }

}
