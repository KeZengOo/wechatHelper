package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v3_0.VisitDataBase;
import com.nuoxin.virtual.rep.api.entity.v3_0.VisitDataPart;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.VisitDataParam;
import com.nuoxin.virtual.rep.api.mybatis.VisitingDataMapper;
import com.nuoxin.virtual.rep.api.service.v3_0.VisitingDataService;
import com.nuoxin.virtual.rep.api.utils.ExportExcelUtil;
import com.nuoxin.virtual.rep.api.utils.ExportExcelWrapper;
import com.nuoxin.virtual.rep.api.utils.v3_0.TimeUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.VisitDataRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.VisitDataResponse;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.*;
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

    private static final long serialVersionUID = 5332874403931753847L;

    private static final String[] VISIT_DATA_TITLES = {"销售代表id", "产品名称", "姓名", "类型", " 拜访方式", "拜访医生数",
            "接触医生数", "成功医生数", "招募医生数", "覆盖医生数", "电话>=75s", "微信回复数",
            "会议覆盖", "问卷覆盖", "外呼次数", "接通次数", "总时长", "发送人数", "微信回复人数",
            "微信添加数", "发送次数", "阅读人数", "阅读总时长", ">=50s 医生数"};

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
            param.setStartTime(DateFormatUtils.format(request.getStartTime(), "yyyy-MM-dd").concat(" 00:00:00"));
            param.setEndTime(DateFormatUtils.format(request.getEndTime(), "yyyy-MM-dd").concat(" 23:59:59"));
            param.setList(userIds);
            result = new PageResponseBean(request, total, this.buildResultList(list, param));
        }

        return result;
    }

    @Override
    public void exportVisitDataSummary(HttpServletResponse response, String leaderPath, VisitDataRequest request) {
        List<VisitDataBase> list = visitingDataMapper.getVisitDataInfos(leaderPath, request);
        List<VisitDataResponse> rlist = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)) {
            // 产品对应的销售代表
            List<Long> userIds = list.stream().map(b -> b.getUserId()).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(request.getDrugUserIdList())) {
                userIds = request.getDrugUserIdList();
            }
            VisitDataParam param = new VisitDataParam();
            param.setProId(list.get(0).getProId());
            param.setStartTime(DateFormatUtils.format(request.getStartTime(), "yyyy-MM-dd").concat(" 00:00:00"));
            param.setEndTime(DateFormatUtils.format(request.getEndTime(), "yyyy-MM-dd").concat(" 23:59:59"));
            param.setList(userIds);
            rlist = this.buildResultList(list, param);
        }

        ExportExcelWrapper<VisitDataResponse> exportExcelWrapper = new ExportExcelWrapper();
        exportExcelWrapper.exportExcel("拜访数据汇总记录", "拜访数据汇总记录", VISIT_DATA_TITLES,
                rlist, response, ExportExcelUtil.EXCEl_FILE_2007);
    }

    /**
     * 封装查询结果
     * @param param
     * @param i
     * @return
     */
    private Map<Long, Integer> buildResultMap(VisitDataParam param, int i) {
        Map<Long, Integer> map = new HashMap<>(1);

        switch (i) {
            case 1 :
                // 拜访医生数
                List<VisitDataPart> visitData = visitingDataMapper.getVisitHcpCount(param);
                if(!CollectionUtils.isEmpty(visitData)) {
                    map = visitData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
                }
                break;
            case 2 :
                // 接触医生数
                List<VisitDataPart> contactData = visitingDataMapper.getContactAndSuccessHcpCount(1, param);
                if(!CollectionUtils.isEmpty(contactData)) {
                    map = contactData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
                }
                break;
            case 3 :
                // 成功医生数
                List<VisitDataPart> successData = visitingDataMapper.getContactAndSuccessHcpCount(2, param);
                if(!CollectionUtils.isEmpty(successData)) {
                    map = successData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
                }
                break;
            case 4 :
                // 招募医生数
                List<VisitDataPart> recruitData = visitingDataMapper.getRecruitHcpCount(param);
                if(!CollectionUtils.isEmpty(recruitData)) {
                    map = recruitData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
                }
                break;
            case 5 :
                // 覆盖医生数
                List<VisitDataPart> coverData = visitingDataMapper.getCoverHcpCount(param);
                if(!CollectionUtils.isEmpty(coverData)) {
                    map = coverData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
                }
                break;
            case 6 :
                // 电话>=75s
                List<VisitDataPart> callTimeData = visitingDataMapper.getCallTimeMore75Count(param);
                if(!CollectionUtils.isEmpty(callTimeData)) {
                    map = callTimeData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
                }
                break;
            case 7 :
                // 微信回复数
                List<VisitDataPart> replyData = visitingDataMapper.getWeChatReplyCount(param);
                if(!CollectionUtils.isEmpty(replyData)) {
                    map = replyData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
                }
                break;
            case 8 :
                // 会议覆盖
                List<VisitDataPart> meetingData = visitingDataMapper.getAttendMeetingCount(param);
                if(!CollectionUtils.isEmpty(meetingData)) {
                    map = meetingData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
                }
                break;
            case 9 :
                // 外呼次数
                List<VisitDataPart> outData = visitingDataMapper.getConnectCount(1, param);
                if(!CollectionUtils.isEmpty(outData)) {
                    map = outData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
                }
                break;
            case 10 :
                // 接通次数
                List<VisitDataPart> contactedData = visitingDataMapper.getConnectCount(2, param);
                if(!CollectionUtils.isEmpty(contactedData)) {
                    map = contactedData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
                }
                break;
            case 11 :
                // 微信聊天统计-发送人数
                List<VisitDataPart> sendData = visitingDataMapper.getWeChatSendCount(param);
                if(!CollectionUtils.isEmpty(sendData)) {
                    map = sendData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
                }
                break;
            case 12 :
                // 微信添加数
                List<VisitDataPart> weChatData = visitingDataMapper.getWeChatAddCount(param);
                if(!CollectionUtils.isEmpty(weChatData)) {
                    map = weChatData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
                }
                break;
            case 13 :
                // 发送次数
                List<VisitDataPart> contentData = visitingDataMapper.getContentPartSendCount(param);
                if(!CollectionUtils.isEmpty(contentData)) {
                    map = contentData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
                }
                break;
            case 14 :
                // >=50s 医生数
                List<VisitDataPart> moreThen50sData = visitingDataMapper.getContentPartMoreThen50s(param);
                if(!CollectionUtils.isEmpty(moreThen50sData)) {
                    map = moreThen50sData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
                }
                break;
            default:break;
        }
        return map;
    }

    /**
     * 封装结果集
     * @param list 结果集list
     * @param param 查询参数
     * @return 封装后的list
     */
    private List<VisitDataResponse> buildResultList(List<VisitDataBase> list, VisitDataParam param) {
        List<VisitDataResponse> rlist = new ArrayList<>();
        // 拜访医生数
        Map<Long, Integer> visitDataMap = this.buildResultMap(param, 1);
        // 接触医生数
        Map<Long, Integer> contactDataMap = this.buildResultMap(param, 2);
        // 成功医生数
        Map<Long, Integer> successDataMap = this.buildResultMap(param, 3);
        // 招募医生数
        Map<Long, Integer> recruitDataMap = this.buildResultMap(param, 4);
        // 覆盖医生数
        Map<Long, Integer> coverDataMap = this.buildResultMap(param, 5);
        // 电话>=75s
        Map<Long, Integer> callTimeDataMap = this.buildResultMap(param, 6);
        // 微信回复数
        Map<Long, Integer> replyDataMap = this.buildResultMap(param, 7);
        // 会议覆盖
        Map<Long, Integer> meetingDataMap = this.buildResultMap(param, 8);
        // 外呼次数
        Map<Long, Integer> outDataMap = this.buildResultMap(param, 9);
        // 接通次数
        Map<Long, Integer> contactedDataMap = this.buildResultMap(param, 10);
        // 总时长
        List<VisitDataPart> totalTimeData = visitingDataMapper.getOutboundTimeCount(param);
        Map<Long, String> totalTimeDataMap = new HashMap<>(1);
        if(!CollectionUtils.isEmpty(totalTimeData)) {
            Map<Long, Integer> map = totalTimeData.stream().collect(Collectors.toMap(k -> k.getUserId(), k -> k.getTotal(), (k1, k2) -> k2));
            map.forEach((k, v) -> {
                totalTimeDataMap.put(k, TimeUtil.alterCallTimeContent(v.longValue()));
            });
        }
        // 微信聊天统计-发送人数
        Map<Long, Integer> sendDataMap = this.buildResultMap(param, 11);
        // 微信添加数
        Map<Long, Integer> weChatDataMap = this.buildResultMap(param, 12);
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
        Map<Long, Integer> contentDataMap = this.buildResultMap(param, 13);

        // >=50s 医生数
        Map<Long, Integer> moreThen50sDataMap = this.buildResultMap(param, 14);

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
            bean.setConnectionNum(contactedDataMap.get(userId) == null ? 0 : contactedDataMap.get(userId));
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
        return rlist;
    }

}
