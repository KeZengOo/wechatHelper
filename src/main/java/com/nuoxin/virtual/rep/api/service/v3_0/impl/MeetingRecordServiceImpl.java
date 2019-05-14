package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v3_0.excel.MeetingParticipantsExcel;
import com.nuoxin.virtual.rep.api.entity.v3_0.excel.MeetingSubjectExcel;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.MeetingAttendDetailsParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.MeetingParticipantsParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.MeetingRecordParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.MeetingSubjectParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.MeetingAttendDetailsRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.MeetingRecordRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.MeetingSubjectRequest;
import com.nuoxin.virtual.rep.api.mybatis.MeetingRecordMapper;
import com.nuoxin.virtual.rep.api.service.v3_0.MeetingRecordService;
import com.nuoxin.virtual.rep.api.utils.ExcelUtils;
import com.nuoxin.virtual.rep.api.utils.excel.RegularUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 会议记录查询
 * @author wujiang
 * @date 20190429
 */
@Slf4j
@Service
public class MeetingRecordServiceImpl implements MeetingRecordService {

    @Resource
    private MeetingRecordMapper meetingRecordMapper;

    private static final Logger logger = LoggerFactory.getLogger(MeetingRecordServiceImpl.class);

    @Override
    public PageResponseBean<List<MeetingRecordParams>> getMeetingRecordList(MeetingRecordRequest meetingRecordRequest) {

        List<MeetingRecordParams> list = meetingRecordMapper.getMeetingRecordList(meetingRecordRequest);
        Integer getMeetingRecordListCount = meetingRecordMapper.getMeetingRecordListCount(meetingRecordRequest);
        List<MeetingRecordParams> newList = new ArrayList<MeetingRecordParams>();
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);

        for (int i = 0; i < list.size(); i++){
            MeetingRecordParams m = new MeetingRecordParams();
            //获取当前产品的招募医生数
            Integer recordNum = meetingRecordMapper.getRecruitHcpAllCountByProduct(list.get(i).getProductId());
            //根据会议标题查询主题数
            Integer subjectCount = meetingRecordMapper.getSubjectCountByMeetingTitle(list.get(i).getTitle());
            m = list.get(i);
            if(recordNum > 0){
                String result = numberFormat.format((float) list.get(i).getDoctorCount() / (float) recordNum * 100);
                System.out.println("num1和num2的百分比为:" + result + "%");
                m.setAttendanceRate(result+"%");
            }
            else
            {
                m.setAttendanceRate("0%");
            }
            m.setStartTime(m.getStartTime().substring(0,m.getStartTime().indexOf(".")));
            m.setEndTime(m.getEndTime().substring(0,m.getEndTime().indexOf(".")));
            m.setSubjectNum(subjectCount);
            newList.add(m);
        }
        return new PageResponseBean(meetingRecordRequest, getMeetingRecordListCount, newList);
    }

    @Override
    public List<MeetingSubjectParams> getMeetingSubjectListByProductIdAndMeetingName(MeetingSubjectRequest meetingSubjectRequest) {

        List<MeetingSubjectParams> newList = new ArrayList<MeetingSubjectParams>();
        List<MeetingSubjectParams> list = meetingRecordMapper.getMeetingSubjectListByProductIdAndMeetingName(meetingSubjectRequest.getProductId(),meetingSubjectRequest.getMeetingName());

        //获取会议明细
        MeetingRecordParams meetingRecordParams = meetingRecordMapper.getMeetingInfoByMeetingId(meetingSubjectRequest.getMeetingId());
        //计算两个时间差
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = simpleDateFormat.parse(meetingRecordParams.getStartTime());
            endDate = simpleDateFormat.parse(meetingRecordParams.getEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = endDate.getTime() - startDate.getTime();
        int hours = (int) (diff/(1000 * 60 * 60));

        list.forEach(n -> {
            MeetingSubjectParams m = new MeetingSubjectParams();
            m=n;
            m.setDuration(hours+"小时");
            newList.add(m);
        });

        return newList;
    }

    @Override
    public PageResponseBean<List<MeetingAttendDetailsParams>> getMeetingAttendDetailsListByMeetingId(MeetingAttendDetailsRequest meetingAttendDetailsRequest) {

        //参会列表
        List<MeetingAttendDetailsParams> meetingAttendDetailsParamsList = meetingRecordMapper.getMeetingAttendDetailsListByMeetingId(meetingAttendDetailsRequest);

        //参会总数
        Integer meetingAttendDetailsCount = meetingRecordMapper.getMeetingAttendDetailsCountByMeetingId(meetingAttendDetailsRequest.getMeetingId());

        List<MeetingAttendDetailsParams> newList = new ArrayList<MeetingAttendDetailsParams>();

        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");

        //把每小段时长放在一个数组里
        meetingAttendDetailsParamsList.forEach(n ->{
            MeetingAttendDetailsParams m = new MeetingAttendDetailsParams();
            List<MeetingAttendDetailsParams> list = meetingRecordMapper.getMeetingTimeByDoctorIdAndMeetingID(n.getDoctorId(),n.getMeetingId());
            //开始时间和结束时间数组
            String[] times = new String[list.size()];
            //每个会议时段的时长数组
            String[] timeSum = new String[list.size()];
            //参会总时长
            Integer timeSumCount = 0;

            for (int i = 0; i < list.size(); i++){
                times[i] = dateFormat.format(list.get(i).getAttendStartTime());
                timeSum[i] = list.get(i).getAttendSumTime()+"分";
                timeSumCount += list.get(i).getAttendSumTime();
            }
            m.setId(n.getId());
            m.setDoctorId(n.getDoctorId());
            m.setDoctorName(n.getDoctorName());
            m.setHospitalId(n.getHospitalId());
            m.setHospitalName(n.getHospitalName());
            m.setDepart(n.getDepart());
            m.setAttendSumCountTime(timeSumCount);
            m.setAttendTimeArray(times);
            m.setAttendSumTimeArray(timeSum);
            newList.add(m);
        });

        return new PageResponseBean(meetingAttendDetailsRequest, meetingAttendDetailsCount, newList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> meetingImport(MultipartFile file) {
        Map<String, Object> map =new HashMap<String, Object>(4);

        boolean flag = false;
        String originalFilename = file.getOriginalFilename();
        if (!originalFilename.endsWith(RegularUtils.EXTENSION_XLS) && !originalFilename.endsWith(RegularUtils.EXTENSION_XLSX)) {
            log.error("文件格式错误");
            map.put("flag",flag);
            map.put("message","文件格式错误");
            return map;
        }

        List<MeetingSubjectExcel> meetingSubjectExcels = new ArrayList<MeetingSubjectExcel>();
        InputStream inputStream = null;
        ExcelUtils<MeetingSubjectExcel> excelUtils = new ExcelUtils<>(new MeetingSubjectExcel());

        try {
            inputStream = file.getInputStream();
            meetingSubjectExcels = excelUtils.readFromFile(null, inputStream);
        } catch (Exception e) {
            log.error("读取上传的excel文件失败。。", e);
            map.put("flag",flag);
            map.put("message","读取上传的excel文件失败。。");
            return map;
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("IOException", e);
                }
            }
        }

        List<MeetingSubjectExcel> meetingTemps = new ArrayList<MeetingSubjectExcel>();

        for (MeetingSubjectExcel h : meetingSubjectExcels) {
            MeetingSubjectExcel meetingSubjectExcel = new MeetingSubjectExcel();

            if (h != null) {
                try {
                    if(null != h.getMeetingName() && null != h.getSubjectName() && null != h.getSpeaker() && null != h.getStartTime() && null != h.getEndTime())
                    {
                        meetingSubjectExcel.setMeetingName(h.getMeetingName());
                        meetingSubjectExcel.setSubjectName(h.getSubjectName());
                        meetingSubjectExcel.setSpeaker(h.getSpeaker());
                        meetingSubjectExcel.setStartTime(h.getStartTime());
                        meetingSubjectExcel.setEndTime(h.getEndTime());
                        meetingTemps.add(meetingSubjectExcel);
                    }
                    else
                    {
                        map.put("flag",false);
                        map.put("message","上传数据存在空值");
                        return map;
                    }
                } catch (Exception e) {
                    log.error("IOException", e);
                }
            }
        }

        //把list存到mysql
        boolean result = false;
        try {
            result = meetingRecordMapper.saveExcel(meetingTemps);
            flag = result;
            map.put("flag",flag);
            map.put("message","上传会议成功");
        } catch (Exception e) {
            log.error("IOException", e);
            map.put("flag",flag);
            map.put("message","上传会议失败");
        }

        //预计导入条数
        map.put("estimatedNumber",meetingSubjectExcels.size());
        //预计导入条数
        map.put("actualNumber",meetingTemps.size());
        return map;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Map<String, Object> meetingParticipantsImport(MultipartFile file, String meetingId) {

        Map<String, Object> map =new HashMap<String, Object>(4);

        boolean flag = false;
        String originalFilename = file.getOriginalFilename();
        if (!originalFilename.endsWith(RegularUtils.EXTENSION_XLS) && !originalFilename.endsWith(RegularUtils.EXTENSION_XLSX)) {
            log.error("文件格式错误");
            map.put("flag",flag);
            map.put("message","文件格式错误");
            return map;
        }

        List<MeetingParticipantsExcel> meetingParticipantsExcels = new ArrayList<MeetingParticipantsExcel>();
        InputStream inputStream = null;
        ExcelUtils<MeetingParticipantsExcel> excelUtils = new ExcelUtils<>(new MeetingParticipantsExcel());

        try {
            inputStream = file.getInputStream();
            meetingParticipantsExcels = excelUtils.readFromFile(null, inputStream);
        } catch (Exception e) {
            log.error("读取上传的excel文件失败。。", e);
            map.put("flag",flag);
            map.put("message","读取上传的excel文件失败。。");
            return map;
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("IOException", e);
                }
            }
        }

        List<MeetingParticipantsExcel> meetingParticipantsTemps = new ArrayList<MeetingParticipantsExcel>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (MeetingParticipantsExcel h : meetingParticipantsExcels) {
            MeetingParticipantsExcel meetingParticipantsExcel = new MeetingParticipantsExcel();
            if (h != null) {
                try {
                    if(null != h.getDoctorTel() && null != h.getAttendStartTime() && null != h.getAttendEndTime())
                    {
                        //根据会议ID获取会议项目ID
                        MeetingParticipantsParams meetingParticipants = meetingRecordMapper.getMeetingItemIdByMeetingId(meetingId);
                        //根据医生电话号获取医生信息
                        MeetingParticipantsParams meetingParticipantsDoctorInfo = meetingRecordMapper.getDoctorInfoByDoctorTel(h.getDoctorTel());

                        if(null != meetingParticipants.getItemId() && null != meetingParticipantsDoctorInfo.getDoctorId() && null != meetingParticipantsDoctorInfo.getName()){
                            meetingParticipantsExcel.setDoctorId(meetingParticipantsDoctorInfo.getDoctorId().toString());
                            meetingParticipantsExcel.setDoctorName(meetingParticipantsDoctorInfo.getName());
                            meetingParticipantsExcel.setMeetingId(meetingId);
                            meetingParticipantsExcel.setAttendStartTime(h.getAttendStartTime());
                            meetingParticipantsExcel.setAttendEndTime(h.getAttendEndTime());

                            //计算参会分钟差
                            Date date1 = df.parse(h.getAttendEndTime());
                            Date date2 = df.parse(h.getAttendStartTime());
                            long diff = date1.getTime() - date2.getTime();
                            //计算两个时间之间差了多少分钟
                            long minutes = diff / (1000 * 60);

                            meetingParticipantsExcel.setAttendSumTime(minutes+"");
                            meetingParticipantsExcel.setType("1");
                            meetingParticipantsExcel.setItemId(meetingParticipants.getItemId().toString());
                            meetingParticipantsExcel.setDoctorTel(h.getDoctorTel());
                            meetingParticipantsTemps.add(meetingParticipantsExcel);
                        }
                        else
                        {
                            map.put("flag",false);
                            map.put("message","上传数据存在空值");
                            return map;
                        }

                    }
                    else
                    {
                        map.put("flag",false);
                        map.put("message","上传数据存在空值");
                        return map;
                    }
                } catch (Exception e) {
                    log.error("IOException", e);
                }
            }
        }

        //把list存到mysql
        boolean result = false;
        try {
            result = meetingRecordMapper.saveMeetingParticipantsExcel(meetingParticipantsTemps);
            flag = result;
            map.put("flag",flag);
            map.put("message","上传会议成功");
        } catch (Exception e) {
            log.error("IOException", e);
            map.put("flag",flag);
            map.put("message","上传会议失败");
        }

        //预计导入条数
        map.put("estimatedNumber",meetingParticipantsExcels.size());
        //预计导入条数
        map.put("actualNumber",meetingParticipantsTemps.size());

        return map;
    }

    @Override
    public boolean updateMeetingSubjectProductIdByMeetingName(String meetingName, Integer productId) {
        boolean result = meetingRecordMapper.updateMeetingSubjectProductIdByMeetingName(meetingName,productId);
        return result;
    }

}
