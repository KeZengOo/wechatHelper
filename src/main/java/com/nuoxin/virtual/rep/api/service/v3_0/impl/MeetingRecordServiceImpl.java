package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.MeetingRecordParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.MeetingSubjectParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.MeetingRecordRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.MeetingSubjectRequest;
import com.nuoxin.virtual.rep.api.mybatis.MeetingRecordMapper;
import com.nuoxin.virtual.rep.api.service.v3_0.MeetingRecordService;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 会议记录查询
 * @author wujiang
 * @date 20190429
 */
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
        long diff = meetingRecordParams.getEndTime().getTime() - meetingRecordParams.getStartTime().getTime();
        int hours = (int) (diff/(1000 * 60 * 60));

        list.forEach(n -> {
            MeetingSubjectParams m = new MeetingSubjectParams();
            m=n;
            m.setDuration(hours+"");
            newList.add(m);
        });

        return newList;
    }

}
