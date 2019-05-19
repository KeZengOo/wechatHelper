package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v3_0.VisitDataBase;
import com.nuoxin.virtual.rep.api.entity.v3_0.VisitDataPart;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.VisitDataParam;
import com.nuoxin.virtual.rep.api.mybatis.VisitingDataMapper;
import com.nuoxin.virtual.rep.api.service.v3_0.VisitingDataService;
import com.nuoxin.virtual.rep.api.utils.v3_0.TimeUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.VisitDataRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.VisitDataResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName VisitingDataServiceImpl
 * @Description 拜访数据Service实现类
 * @Author dangjunhui
 * @Date 2019/5/14 11:28
 * @Version 1.0
 */
@Service
public class VisitingDataServiceImpl implements VisitingDataService, Serializable {

    @Resource
    private VisitingDataMapper visitingDataMapper;

    @Override
    public PageResponseBean<VisitDataResponse> getVisitDataByPage(String leaderPath, VisitDataRequest request) {

        int total = visitingDataMapper.getVisitDataCount(leaderPath, request);

        PageResponseBean<VisitDataResponse> result = null;

        if(total > 0) {
            List<VisitDataBase> list = visitingDataMapper.getVisitDataBaseInfo(leaderPath, request);
            // 产品对应的销售代表
            List<Long> userIds = list.stream().map(b -> b.getUserId()).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(request.getDrugUserIdList())) {
                userIds = request.getDrugUserIdList();
            }
            VisitDataParam param = new VisitDataParam();
            param.setProId(list.get(0).getProId());
            param.setStartTime(request.getStartTime());
            param.setEndTime(request.getEndTime());
            param.setList(userIds);
            // 拜访医生数
            List<VisitDataPart> visitData = visitingDataMapper.getVisitHcpCount(param);
            Map<Long, Integer> visitDataMap = new HashMap<>(1);
            if(!CollectionUtils.isEmpty(visitData)) {
                visitDataMap = visitData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
            }
            // 接触医生数
            List<VisitDataPart> contactData = visitingDataMapper.getContactAndSuccessHcpCount(1, param);
            Map<Long, Integer> contactDataMap = new HashMap<>(1);
            if(!CollectionUtils.isEmpty(contactData)) {
                contactDataMap = contactData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
            }
            // 成功医生数
            List<VisitDataPart> successData = visitingDataMapper.getContactAndSuccessHcpCount(2, param);
            Map<Long, Integer> successDataMap = new HashMap<>(1);
            if(!CollectionUtils.isEmpty(successData)) {
                successDataMap = successData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
            }
            // 招募医生数
            List<VisitDataPart> recruitData = visitingDataMapper.getRecruitHcpCount(param);
            Map<Long, Integer> recruitDataMap = new HashMap<>(1);
            if(!CollectionUtils.isEmpty(recruitData)) {
                recruitDataMap = recruitData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
            }
            // 覆盖医生数
            List<VisitDataPart> coverData = visitingDataMapper.getCoverHcpCount(param);
            Map<Long, Integer> coverDataMap = new HashMap<>(1);
            if(!CollectionUtils.isEmpty(coverData)) {
                coverDataMap = coverData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
            }
            // 电话>75s
            List<VisitDataPart> callTimeData = visitingDataMapper.getCallTimeMore75Count(param);
            Map<Long, Integer> callTimeDataMap = new HashMap<>(1);
            if(!CollectionUtils.isEmpty(callTimeData)) {
                callTimeDataMap = callTimeData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
            }
            // 微信回复数
            List<VisitDataPart> replyData = visitingDataMapper.getWeChatReplyCount(param);
            Map<Long, Integer> replyDataMap = new HashMap<>(1);
            if(!CollectionUtils.isEmpty(replyData)) {
                replyDataMap = replyData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
            }
            // 会议覆盖
            List<VisitDataPart> meetingData = visitingDataMapper.getAttendMeetingCount(param);
            Map<Long, Integer> meetingDataMap = new HashMap<>(1);
            if(!CollectionUtils.isEmpty(meetingData)) {
                meetingDataMap = meetingData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
            }
            // 外呼次数
            List<VisitDataPart> outData = visitingDataMapper.getConnectCount(1, param);
            Map<Long, Integer> outDataMap = new HashMap<>(1);
            if(!CollectionUtils.isEmpty(outData)) {
                outDataMap = outData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
            }
            // 接通次数
            List<VisitDataPart> contactedData = visitingDataMapper.getConnectCount(2, param);
            Map<Long, Integer> contactedDataMap = new HashMap<>(1);
            if(!CollectionUtils.isEmpty(contactedData)) {
                contactedDataMap = contactedData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
            }
            // 总时长
            List<VisitDataPart> totalTimeData = visitingDataMapper.getOutboundTimeCount(param);
            Map<Long, String> totalTimeDataMap = new HashMap<>(1);
            if(!CollectionUtils.isEmpty(totalTimeData)) {
                Map<Long, Integer> map = totalTimeData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
                map.forEach((k, v) -> {
                    totalTimeDataMap.put(k, TimeUtil.alterCallTimeContent(v.longValue()));
                });
            }
            // 发送人数
            List<VisitDataPart> sendData = visitingDataMapper.getWeChatSendCount(param);
            Map<Long, Integer> sendDataMap = new HashMap<>(1);
            if(!CollectionUtils.isEmpty(sendData)) {
                sendDataMap = sendData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
            }
            // 微信添加数
            List<VisitDataPart> weChatData = visitingDataMapper.getWeChatAddCount(param);
            Map<Long, Integer> weChatDataMap = new HashMap<>(1);
            if(!CollectionUtils.isEmpty(weChatData)) {
                weChatDataMap = weChatData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
            }
            // 阅读人数&总时长
            List<VisitDataPart> partTimeData = visitingDataMapper.getContentPartTimeAndCount(param);
            Map<Long, String> partTimeDataMap = new HashMap<>(1);
            Map<Long, Integer> partCountDataMap = new HashMap<>(1);
            if(!CollectionUtils.isEmpty(partTimeData)) {
                Map<Long, Integer> pDataMap = partTimeData.stream().collect(Collectors.groupingBy(k -> k.getUserId(),
                        Collectors.summingInt(k -> k.getTotal())));
                for(Map.Entry<Long, Integer> entry : pDataMap.entrySet()) {
                    partTimeDataMap.put(entry.getKey(), TimeUtil.alterCallTimeContent(entry.getValue().longValue()));
                }
                Map<Long, List<VisitDataPart>> bDataMap = partTimeData.stream().collect(Collectors.groupingBy(k -> k.getUserId()));
                for(Map.Entry<Long, List<VisitDataPart>> entry : bDataMap.entrySet()) {
                    Long num = entry.getValue().stream().mapToInt(k -> k.getDoctorId().intValue()).distinct().count();
                    partCountDataMap.put(entry.getKey(), num.intValue());
                }
            }

            // 发送次数
            List<VisitDataPart> contentData = visitingDataMapper.getContentPartSendCount(param);
            Map<Long, Integer> contentDataMap = new HashMap<>(1);
            if(!CollectionUtils.isEmpty(contentData)) {
                contentDataMap = contentData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
            }

            // >50s 医生数
            List<VisitDataPart> moreThen50sData = visitingDataMapper.getContentPartMoreThen50s(param);
            Map<Long, Integer> moreThen50sDataMap = new HashMap<>(1);
            if(!CollectionUtils.isEmpty(moreThen50sData)) {
                moreThen50sDataMap = moreThen50sData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
            }
            List<VisitDataResponse> rlist = new ArrayList<>(list.size());
            for (VisitDataBase k : list) {
                VisitDataResponse bean = new VisitDataResponse();
                Long userId = k.getUserId();
                bean.setRepId(userId);
                bean.setProduct(k.getProductName());
                bean.setRepName(k.getUserName());
                bean.setRepType(k.getSaleType());
                bean.setRepVisitWay(k.getVisitWay());
                bean.setVisitHcpNum(visitDataMap.get(userId) == null ? 0 : visitDataMap.get(userId));
                bean.setContactedHcpNum(contactDataMap.get(userId) == null ? 0 : contactDataMap.get(userId));
                bean.setSuccessHcpNum(successDataMap.get(userId) == null ? 0 : successDataMap.get(userId));
                bean.setRecruitedHcpNum(recruitDataMap.get(userId) == null ? 0 : recruitDataMap.get(userId));
                bean.setCoveringHcpNum(coverDataMap.get(userId) == null ? 0 : coverDataMap.get(userId));
                bean.setMore75s(callTimeDataMap.get(userId) == null ? 0 : callTimeDataMap.get(userId));
                bean.setResponseNum(replyDataMap.get(userId) == null ? 0 : replyDataMap.get(userId));
                bean.setMeetingCoverage(meetingDataMap.get(userId) == null ? 0 : meetingDataMap.get(userId));
                bean.setQuestionCoverage(0);
                bean.setOutGoingTimes(outDataMap.get(userId) == null ? 0 : outDataMap.get(userId));
                bean.setContactedHcpNum(contactedDataMap.get(userId) == null ? 0 : contactedDataMap.get(userId));
                bean.setTotalDuration(totalTimeDataMap.get(userId) == null ? "" : totalTimeDataMap.get(userId));
                bean.setSendingNum(sendDataMap.get(userId) == null ? 0 : sendDataMap.get(userId));
                bean.setHcpReplyNum(replyDataMap.get(userId) == null ? 0 : replyDataMap.get(userId));
                bean.setAdditionNum(weChatDataMap.get(userId) == null ? 0 : weChatDataMap.get(userId));
                bean.setSendingTimes(contentDataMap.get(userId) == null ? 0 : contentDataMap.get(userId));
                bean.setReaderNum(partCountDataMap.get(userId) == null ? 0 : partCountDataMap.get(userId));
                bean.setReadTotalLength(partTimeDataMap.get(userId) == null ? "" : partTimeDataMap.get(userId));
                bean.setMore50sHcpNum(moreThen50sDataMap.get(userId) == null ? 0 : moreThen50sDataMap.get(userId));
                rlist.add(bean);
            }
            result = new PageResponseBean(request, total, rlist);
        }

        return result;
    }

}
