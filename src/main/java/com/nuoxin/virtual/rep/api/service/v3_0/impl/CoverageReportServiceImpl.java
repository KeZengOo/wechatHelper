package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.entity.v3_0.CoverageReportPart;
import com.nuoxin.virtual.rep.api.mybatis.CoverageReportMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v3_0.CoverageReportService;
import com.nuoxin.virtual.rep.api.utils.*;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.CoverageCallResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.CoverageMeetingResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.CoverageOverviewResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.WeChatResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName CoverageReportServiceImpl
 * @Description 拜访数据汇总 覆盖月报Service实现
 * @Author dangjunhui
 * @Date 2019/6/14 17:04
 * @Version 1.0
 */
@Service
public class CoverageReportServiceImpl implements CoverageReportService {

    private static final String[] MONTH_ARRAY = {"01","02","03","04","05","06","07","08","09","10","11","12"};

    private static final String[] OVERVIEW_DATA_TITLES = {"月份", "已招募医院数", "覆盖医院数", "医院覆盖率", "已招募医生数", "覆盖医生数", "医生覆盖率"};

    private static final String[] PATIENT_VOLUME_DATA_TITLES = {"目标患者量/月", "成功招募医生数", "覆盖医生数", "覆盖次数", "覆盖率"};

    private static final String[] CALL_DATA_TITLES = {"月份", "覆盖人数", "覆盖次数", "总时长"};

    private static final String[] WECHAT_DATA_TITLES = {"月份", "发送条数", "回复条数", "覆盖人数"};

    private static final String[] MEETING_DATA_TITLES = {"月份", "会议数量", "参会人次", "参会人数", "总时长"};

    private static final String[] CONTENT_DATA_TITLES = {"月份", "发送人数", "阅读人数", "阅读率"};

    @Resource
    private CoverageReportMapper coverageReportMapper;

    @Resource
    private CommonService commonService;

    @Override
    public Map<String, Object> findOverviewListByProductIdAndTime(Long proId, String startTime, String endTime) {
        // 招募医生部分
        Map<String, Object> map = new HashMap<>(2);
        List<String> yearAndMonth = this.buildYearAndMonth(startTime, endTime);
        map.put("xAxisData", yearAndMonth);
        List<Object> resultList = new ArrayList<>(6);
        List<CoverageReportPart> recruitList = coverageReportMapper.findRecruitList(proId, startTime, endTime);
        List<Integer> recruitHciList = new ArrayList<>(13);
        List<Integer> coverageHciList = new ArrayList<>(13);
        List<Double> lineHciList = new ArrayList<>(13);
        List<Integer> recruitHcpList = new ArrayList<>(13);
        List<Integer> coverageHcpList = new ArrayList<>(13);
        List<Double> lineHcpList = new ArrayList<>(13);
        if(!CollectionUtils.isEmpty(recruitList)) {
            // 每个时间段的招募医院数量
            Map<String, Set<Long>> recruitListHci = recruitList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                    Collectors.mapping(k -> k.getHciId(), Collectors.toSet())));
            Map<String, Set<Long>> newRecruitHci = this.buildMap(recruitListHci, yearAndMonth, startTime);
            // 每个时间段的招募医生数量
            Map<String, Set<Long>> recruitListHcp = recruitList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                    Collectors.mapping(k -> k.getHcpId(), Collectors.toSet())));
            Map<String, Set<Long>> newRecruitHcp = this.buildMap(recruitListHcp, yearAndMonth, startTime);
            // 覆盖医生部分
            List<CoverageReportPart> coverageList = coverageReportMapper.findCoverageList(proId, startTime, endTime);
            if(!CollectionUtils.isEmpty(coverageList)) {
                // 每个时间段的覆盖医院数量
                Map<String, Set<Long>> coverageListHci = coverageList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHciId(), Collectors.toSet())));
                // 每个时间段的招募医生数量
                Map<String, Set<Long>> coverageListHcp = coverageList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHcpId(), Collectors.toSet())));
                Set<Long> hciSet = new HashSet<>();
                Set<Long> hcpSet = new HashSet<>();
                yearAndMonth.forEach(k -> {
                    Set<Long> rhciSet = newRecruitHci.get(k);
                    int rhciNum = rhciSet != null ? rhciSet.size() : 0;
                    recruitHciList.add(rhciNum);
                    Set<Long> chciSet = coverageListHci.get(k);
                    int hciNum = 0;
                    Double hciPercent = 0.0d;
                    if(chciSet != null) {
                        hciSet.addAll(chciSet);
                        hciSet.retainAll(rhciSet);
                        hciNum = hciSet.size();
                        if(rhciNum > 0) {
                            hciPercent = ArithUtil.mul(ArithUtil.div(hciNum, rhciNum, 4), 100);
                        }
                    }
                    coverageHciList.add(hciNum);
                    lineHciList.add(hciPercent);
                    Set<Long> rhcpSet = newRecruitHcp.get(k);
                    int rhcpNum = rhcpSet != null ? rhcpSet.size() : 0;
                    recruitHcpList.add(rhcpNum);
                    Set<Long> chcpSet = coverageListHcp.get(k);
                    int lineNum = 0;
                    Double hcpPercent = 0.0d;
                    if(chcpSet != null) {
                        hcpSet.addAll(chcpSet);
                        hcpSet.retainAll(rhcpSet);
                        lineNum = hcpSet.size();
                        if(rhcpNum > 0) {
                            hcpPercent = ArithUtil.mul(ArithUtil.div(lineNum, rhcpNum, 4), 100);
                        }
                    }
                    coverageHcpList.add(lineNum);
                    lineHcpList.add(hcpPercent);
                    hciSet.clear();
                    hcpSet.clear();
                });
            } else {
                yearAndMonth.forEach(k -> {
                    recruitHciList.add(0);
                    coverageHciList.add(0);
                    lineHciList.add(0.0d);
                    recruitHcpList.add(0);
                    coverageHcpList.add(0);
                    lineHcpList.add(0.0);
                });
            }
        } else {
            yearAndMonth.forEach(k -> {
                recruitHciList.add(0);
                coverageHciList.add(0);
                lineHciList.add(0.0d);
                recruitHcpList.add(0);
                coverageHcpList.add(0);
                lineHcpList.add(0.0);
            });
        }
        resultList.add(recruitHciList);
        resultList.add(coverageHciList);
        resultList.add(lineHciList);
        resultList.add(recruitHcpList);
        resultList.add(coverageHcpList);
        resultList.add(lineHcpList);
        map.put("seriesData", resultList);
        return map;
    }

    @Override
    public void exportOverview(HttpServletResponse response, Long proId, String startTime, String endTime) {
        List<CoverageReportPart> recruitList = coverageReportMapper.findRecruitList(proId, startTime, endTime);
        List<String> yearAndMonth = this.buildYearAndMonth(startTime, endTime);
        List<CoverageOverviewResponse> rlist = new ArrayList<>();
        if(!CollectionUtils.isEmpty(recruitList)) {
            // 每个时间段的招募医院数量
            Map<String, Set<Long>> recruitListHci = recruitList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                    Collectors.mapping(k -> k.getHciId(), Collectors.toSet())));
            Map<String, Set<Long>> newRecruitHci = this.buildMap(recruitListHci, yearAndMonth, startTime);
            // 每个时间段的招募医生数量
            Map<String, Set<Long>> recruitListHcp = recruitList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                    Collectors.mapping(k -> k.getHcpId(), Collectors.toSet())));
            Map<String, Set<Long>> newRecruitHcp = this.buildMap(recruitListHcp, yearAndMonth, startTime);
            // 覆盖医生部分
            List<CoverageReportPart> coverageList = coverageReportMapper.findCoverageList(proId, startTime, endTime);
            if(!CollectionUtils.isEmpty(coverageList)) {
                // 每个时间段的覆盖医院数量
                Map<String, Set<Long>> coverageListHci = coverageList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHciId(), Collectors.toSet())));
                // 每个时间段的招募医生数量
                Map<String, Set<Long>> coverageListHcp = coverageList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHcpId(), Collectors.toSet())));

                Set<Long> hciSet = new HashSet<>();
                Set<Long> hcpSet = new HashSet<>();
                yearAndMonth.forEach(k -> {
                    CoverageOverviewResponse res = new CoverageOverviewResponse();
                    res.setTimeStr(k);
                    Set<Long> rhciSet = newRecruitHci.get(k);
                    int rhciNum = rhciSet != null ? rhciSet.size() : 0;
                    res.setRecruitHciNum(rhciNum);
                    Set<Long> chciSet = coverageListHci.get(k);
                    int hciNum = 0;
                    Double hciPercent = 0.0d;
                    if(chciSet != null) {
                        hciSet.addAll(chciSet);
                        hciSet.retainAll(rhciSet);
                        hciNum = hciSet.size();
                        if(rhciNum > 0) {
                            hciPercent = ArithUtil.mul(ArithUtil.div(hciNum, rhciNum, 4), 100);
                        }
                    }
                    res.setCoverageHciNum(hciNum);
                    res.setHciPercent(hciPercent + "%");
                    Set<Long> rhcpSet = recruitListHcp.get(k);
                    int rhcpNum = rhcpSet != null ? rhcpSet.size() : 0;
                    res.setRecruitHcpNum(rhcpNum);
                    Set<Long> chcpSet = coverageListHcp.get(k);
                    int lineNum = 0;
                    Double hcpPercent = 0.0d;
                    if(chcpSet != null) {
                        hcpSet.addAll(chcpSet);
                        hcpSet.retainAll(rhcpSet);
                        lineNum = hcpSet.size();
                        if(rhcpNum > 0) {
                            hcpPercent = ArithUtil.mul(ArithUtil.div(lineNum, rhcpNum, 4), 100);
                        }
                    }
                    res.setCoverageHcpNum(lineNum);
                    res.setHcpPercent(hcpPercent + "%");
                    rlist.add(res);
                    hciSet.clear();
                    hcpSet.clear();
                });
                CoverageOverviewResponse res = new CoverageOverviewResponse();
                res.setTimeStr("总计");
                // 总招募医院数
                int hciTotalNum = newRecruitHci.get(yearAndMonth.get(yearAndMonth.size()-1)).size();
                res.setRecruitHciNum(hciTotalNum);
                // 总招募医生数
                int hcpTotalNum = newRecruitHcp.get(yearAndMonth.get(yearAndMonth.size()-1)).size();
                res.setRecruitHcpNum(hcpTotalNum);
                // 总覆盖医院数
                Long coverageHciTotal = coverageList.stream().map(k -> k.getHciId()).distinct().count();
                res.setCoverageHciNum(coverageHciTotal.intValue());
                // 总覆盖医生数
                Long coverageHcpTotal = coverageList.stream().map(k -> k.getHcpId()).distinct().count();
                res.setCoverageHcpNum(coverageHcpTotal.intValue());
                Double hciPercent = ArithUtil.mul(ArithUtil.div(res.getCoverageHciNum(), res.getRecruitHciNum(), 4), 100);
                res.setHciPercent(hciPercent + "%");
                Double hcpPercent = ArithUtil.mul(ArithUtil.div(res.getCoverageHcpNum(), res.getRecruitHcpNum(), 4), 100);
                res.setHcpPercent(hcpPercent + "%");
                rlist.add(res);
            }
        }
        // 导出逻辑
        ExportExcelWrapper<CoverageOverviewResponse> exportExcelWrapper = new ExportExcelWrapper();
        exportExcelWrapper.exportExcel("月报—覆盖情况总览—".concat(startTime).concat("-").concat(endTime), "覆盖情况总览表", OVERVIEW_DATA_TITLES,
                rlist, response, ExportExcelUtil.EXCEl_FILE_2007);
    }

    @Override
    public Map<String, Object> findCallListByProductIdAndTime(Long productId, String startTime, String endTime) {
        // 招募医生部分
        Map<String, Object> map = new HashMap<>(2);
        List<String> yearAndMonth = this.buildYearAndMonth(startTime, endTime);
        map.put("xAxisData", yearAndMonth);
        List<Integer> hcpNumList = new ArrayList<>(yearAndMonth.size());
        List<Integer> coverNumList = new ArrayList<>(yearAndMonth.size());
        List<Object> resultList = new ArrayList<>(6);
        List<CoverageReportPart> recruitList = coverageReportMapper.findCoverageRecruitList(productId, startTime, endTime);
        if(!CollectionUtils.isEmpty(recruitList)) {
            // 每个时间段的招募医生数量
            Map<String, Set<Long>> recruitListHcp = recruitList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                    Collectors.mapping(k -> k.getHcpId(), Collectors.toSet())));
            Map<String, Set<Long>> newRecruitHcp = this.buildMap(recruitListHcp, yearAndMonth, startTime);
            List<CoverageReportPart> recruitCallList = coverageReportMapper.findCoverageCallList(productId, startTime, endTime);
            if(!CollectionUtils.isEmpty(recruitCallList)) {
                // 每个时间段的覆盖医生数量
                Map<String, Set<Long>> recruitCallListHcp = recruitCallList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHcpId(), Collectors.toSet())));
                // 每个时间段的覆盖次数
                Map<String, List<Long>> coverageMap = recruitCallList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHcpId(), Collectors.toList())));

                Set<Long> hcpSet = new HashSet<>();
                List<Long> coverageList = new ArrayList<>();
                yearAndMonth.forEach(k -> {
                    Set<Long> recruitHcpSet = newRecruitHcp.get(k);
                    if(CollectionUtils.isEmpty(recruitHcpSet)) {
                        recruitHcpSet = Collections.emptySet();
                    }
                    Set<Long> callHcpSet = recruitCallListHcp.get(k);
                    if(CollectionUtils.isEmpty(callHcpSet)) {
                        callHcpSet = Collections.emptySet();
                    }
                    hcpSet.addAll(callHcpSet);
                    hcpSet.retainAll(recruitHcpSet);
                    int hcpNum = hcpSet.size();
                    hcpNumList.add(hcpNum);

                    List<Long> coverage = coverageMap.get(k);
                    if(CollectionUtils.isEmpty(coverage)) {
                        coverage = Collections.emptyList();
                    }
                    coverageList.addAll(coverage);
                    coverageList.retainAll(recruitHcpSet);
                    coverNumList.add(coverageList.size());
                    hcpSet.clear();
                    coverageList.clear();
                });
            } else {
                yearAndMonth.forEach(k -> {
                    hcpNumList.add(0);
                    coverNumList.add(0);
                });
            }
        } else {
            yearAndMonth.forEach(k -> {
                hcpNumList.add(0);
                coverNumList.add(0);
            });
        }
        resultList.add(hcpNumList);
        resultList.add(coverNumList);
        map.put("seriesData", resultList);
        return map;
    }

    @Override
    public void exportCall(HttpServletResponse response, Long proId, String startTime, String endTime) {
        // 招募医生部分
        List<String> yearAndMonth = this.buildYearAndMonth(startTime, endTime);
        List<CoverageCallResponse> rlist = new ArrayList<>();
        List<CoverageReportPart> recruitList = coverageReportMapper.findCoverageRecruitList(proId, startTime, endTime);
        if(!CollectionUtils.isEmpty(recruitList)) {
            // 每个时间段的招募医生数量
            Map<String, Set<Long>> recruitListHcp = recruitList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                    Collectors.mapping(k -> k.getHcpId(), Collectors.toSet())));
            Map<String, Set<Long>> newRecruitHcp = this.buildMap(recruitListHcp, yearAndMonth, startTime);
            List<CoverageReportPart> recruitCallList = coverageReportMapper.findCoverageCallList(proId, startTime, endTime);
            if(!CollectionUtils.isEmpty(recruitCallList)) {
                // 每个时间段的覆盖医生数量
                Map<String, Set<Long>> recruitCallListHcp = recruitCallList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHcpId(), Collectors.toSet())));
                // 每个时间段的覆盖次数
                Map<String, List<Long>> coverageMap = recruitCallList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHcpId(), Collectors.toList())));
                // 每个时间段的覆盖时长
                Map<String, List<Long>> coverageTimeMap = recruitCallList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHciId(), Collectors.toList())));
                Set<Long> hcpSet = new HashSet<>();
                List<Long> coverageList = new ArrayList<>();
                yearAndMonth.forEach(k -> {
                    CoverageCallResponse res = new CoverageCallResponse();
                    res.setTimeStr(k);
                    Set<Long> recruitHcpSet = newRecruitHcp.get(k);
                    if(CollectionUtils.isEmpty(recruitHcpSet)) {
                        recruitHcpSet = Collections.emptySet();
                    }
                    Set<Long> callHcpSet = recruitCallListHcp.get(k);
                    if(CollectionUtils.isEmpty(callHcpSet)) {
                        callHcpSet = Collections.emptySet();
                    }
                    hcpSet.addAll(callHcpSet);
                    hcpSet.retainAll(recruitHcpSet);
                    int hcpNum = hcpSet.size();
                    res.setCoverageNum(hcpNum);
                    List<Long> coverage = coverageMap.get(k);
                    if(CollectionUtils.isEmpty(coverage)) {
                        coverage = Collections.emptyList();
                    }
                    coverageList.addAll(coverage);
                    coverageList.retainAll(recruitHcpSet);
                    res.setCoverageCount(coverageList.size());
                    List<Long> timeList = coverageTimeMap.get(k);
                    if(!CollectionUtils.isEmpty(timeList)) {
                        Long time = timeList.stream().mapToLong(Long::longValue).sum();
                        String totalTime = commonService.alterCallTimeContent(time);
                        res.setTotalTime(totalTime);
                    }
                    hcpSet.clear();
                    coverageList.clear();
                    rlist.add(res);
                });

                // 总计部分
                CoverageCallResponse res = new CoverageCallResponse();
                Set<Long> totalHcp = new HashSet<>();
                Set<Long> recruitHcp = recruitList.stream().map(k -> k.getHcpId()).collect(Collectors.toSet());
                Set<Long> coverageHcp = recruitCallList.stream().map(k -> k.getHcpId()).collect(Collectors.toSet());
                totalHcp.addAll(coverageHcp);
                totalHcp.retainAll(recruitHcp);
                res.setTimeStr("总计");
                res.setCoverageNum(totalHcp.size());
                res.setCoverageCount(recruitCallList.size());
                Long time = recruitCallList.stream().mapToLong(k -> k.getHciId()).sum();
                String totalTime = commonService.alterCallTimeContent(time);
                res.setTotalTime(totalTime);
                rlist.add(res);
            }
        }
        // 导出逻辑
        ExportExcelWrapper<CoverageCallResponse> exportExcelWrapper = new ExportExcelWrapper();
        exportExcelWrapper.exportExcel("月报—电话覆盖分析—".concat(startTime).concat("-").concat(endTime), "电话覆盖分析表", CALL_DATA_TITLES,
                rlist, response, ExportExcelUtil.EXCEl_FILE_2007);
    }

    @Override
    public Map<String, Object> findWeChatListByProductIdAndTime(Long productId, String startTime, String endTime) {
        // 招募医生部分
        Map<String, Object> map = new HashMap<>(2);
        List<String> yearAndMonth = this.buildYearAndMonth(startTime, endTime);
        map.put("xAxisData", yearAndMonth);
        List<Object> resultList = new ArrayList<>(6);
        List<Integer> sendCountList = new ArrayList<>(yearAndMonth.size());
        List<Integer> replyCountList = new ArrayList<>(yearAndMonth.size());
        List<Integer> coverNumList = new ArrayList<>(yearAndMonth.size());
        List<CoverageReportPart> recruitList = coverageReportMapper.findCoverageRecruitList(productId, startTime, endTime);
        if(!CollectionUtils.isEmpty(recruitList)) {
            // 每个时间段的招募医生数量
            Map<String, Set<Long>> recruitListHcp = recruitList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                    Collectors.mapping(k -> k.getHcpId(), Collectors.toSet())));
            Map<String, Set<Long>> newRecruitHcp = this.buildMap(recruitListHcp, yearAndMonth, startTime);
            List<CoverageReportPart> coverageWeChatList = coverageReportMapper.findCoverageWeChatList(productId, startTime, endTime);
            List<CoverageReportPart> sendList = coverageReportMapper.findCoverageWeChatSend(productId, startTime, endTime);
            if(!CollectionUtils.isEmpty(coverageWeChatList)) {
                // 每个时间段的覆盖次数
                Map<String, List<Long>> coverageMap = coverageWeChatList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHcpId(), Collectors.toList())));
                // 每个时间段的发送次数
                Map<String, List<Long>> sendMap = sendList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHcpId(), Collectors.toList())));
                List<Long> sendCount = new ArrayList<>();
                List<Long> replyCount = new ArrayList<>();
                Set<Long> coverageSet = new HashSet<>();
                yearAndMonth.forEach(k -> {
                    Set<Long> recruitHcpSet = newRecruitHcp.get(k);
                    if(CollectionUtils.isEmpty(recruitHcpSet)) {
                        recruitHcpSet = Collections.emptySet();
                    }
                    List<Long> sendWeChat = sendMap.get(k);
                    if(CollectionUtils.isEmpty(sendWeChat)) {
                        sendWeChat = Collections.emptyList();
                    }
                    sendCount.addAll(sendWeChat);
                    sendCount.retainAll(recruitHcpSet);
                    int sendNum = sendCount.size();
                    sendCountList.add(sendNum);

                    List<Long> weChatHcpSet = coverageMap.get(k);
                    if(CollectionUtils.isEmpty(weChatHcpSet)) {
                        weChatHcpSet = Collections.emptyList();
                    }
                    replyCount.addAll(weChatHcpSet);
                    replyCount.retainAll(recruitHcpSet);
                    int replyNum = replyCount.size();
                    replyCountList.add(replyNum);

                    coverageSet.addAll(weChatHcpSet);
                    coverageSet.retainAll(recruitHcpSet);
                    coverNumList.add(coverageSet.size());

                    sendCount.clear();
                    replyCount.clear();
                    coverageSet.clear();
                });
            } else {
                yearAndMonth.forEach(k -> {
                    sendCountList.add(0);
                    replyCountList.add(0);
                    coverNumList.add(0);
                });
            }
        } else {
            yearAndMonth.forEach(k -> {
                sendCountList.add(0);
                replyCountList.add(0);
                coverNumList.add(0);
            });
        }
        resultList.add(sendCountList);
        resultList.add(replyCountList);
        resultList.add(coverNumList);
        map.put("seriesData", resultList);
        return map;
    }

    @Override
    public void exportWeChat(HttpServletResponse response, long productId, String startTime, String endTime) {
        List<String> yearAndMonth = this.buildYearAndMonth(startTime, endTime);
        List<CoverageReportPart> recruitList = coverageReportMapper.findCoverageRecruitList(productId, startTime, endTime);
        List<WeChatResponse> rlist = new ArrayList<>();
        List<Long> sendCount = new ArrayList<>();
        List<Long> replyCount = new ArrayList<>();
        Set<Long> coverageSet = new HashSet<>();
        if(!CollectionUtils.isEmpty(recruitList)) {
            // 每个时间段的招募医生数量
            Map<String, Set<Long>> recruitListHcp = recruitList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                    Collectors.mapping(k -> k.getHcpId(), Collectors.toSet())));
            Map<String, Set<Long>> newRecruitHcp = this.buildMap(recruitListHcp, yearAndMonth, startTime);
            List<CoverageReportPart> coverageWeChatList = coverageReportMapper.findCoverageWeChatList(productId, startTime, endTime);
            List<CoverageReportPart> sendList = coverageReportMapper.findCoverageWeChatSend(productId, startTime, endTime);
            if(!CollectionUtils.isEmpty(coverageWeChatList)) {
                // 每个时间段的覆盖次数
                Map<String, List<Long>> coverageMap = coverageWeChatList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHcpId(), Collectors.toList())));
                // 每个时间段的发送次数
                Map<String, List<Long>> sendMap = sendList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHcpId(), Collectors.toList())));
                yearAndMonth.forEach(k -> {
                    WeChatResponse res = new WeChatResponse();
                    res.setTimeStr(k);
                    Set<Long> recruitHcpSet = newRecruitHcp.get(k);
                    if(CollectionUtils.isEmpty(recruitHcpSet)) {
                        recruitHcpSet = Collections.emptySet();
                    }
                    List<Long> sendWeChat = sendMap.get(k);
                    if(CollectionUtils.isEmpty(sendWeChat)) {
                        sendWeChat = Collections.emptyList();
                    }
                    sendCount.addAll(sendWeChat);
                    sendCount.retainAll(recruitHcpSet);
                    int sendNum = sendCount.size();
                    res.setSendNum(sendNum);

                    List<Long> weChatHcpSet = coverageMap.get(k);
                    if(CollectionUtils.isEmpty(weChatHcpSet)) {
                        weChatHcpSet = Collections.emptyList();
                    }
                    replyCount.addAll(weChatHcpSet);
                    replyCount.retainAll(recruitHcpSet);
                    int replyNum = replyCount.size();
                    res.setReplyNum(replyNum);

                    coverageSet.addAll(weChatHcpSet);
                    coverageSet.retainAll(recruitHcpSet);
                    res.setHcpNum(coverageSet.size());

                    sendCount.clear();
                    replyCount.clear();
                    coverageSet.clear();
                    rlist.add(res);
                });
                // 总计部分
                WeChatResponse res = new WeChatResponse();
                List<Long> total = new ArrayList<>();
                Set<Long> recruitHcp = recruitList.stream().map(k -> k.getHcpId()).collect(Collectors.toSet());
                List<Long> sendTotal = sendList.stream().map(k -> k.getHcpId()).collect(Collectors.toList());
                total.addAll(sendTotal);
                total.retainAll(recruitHcp);
                res.setTimeStr("总计");
                res.setSendNum(total.size());
                total.clear();
                List<Long> replyTotal = coverageWeChatList.stream().map(k -> k.getHcpId()).collect(Collectors.toList());
                total.addAll(replyTotal);
                total.retainAll(recruitHcp);
                res.setReplyNum(total.size());
                Long totalHcp = total.stream().distinct().count();
                res.setHcpNum(totalHcp.intValue());
                rlist.add(res);
            }
        }
        // 导出逻辑
        ExportExcelWrapper<WeChatResponse> exportExcelWrapper = new ExportExcelWrapper();
        exportExcelWrapper.exportExcel("月报—微信覆盖分析—".concat(startTime).concat("-").concat(endTime), "微信覆盖分析表", WECHAT_DATA_TITLES,
                rlist, response, ExportExcelUtil.EXCEl_FILE_2007);
    }

    @Override
    public Map<String, Object> findMeetingListByProductIdAndTime(Long productId, String startTime, String endTime) {
        // 招募医生部分
        Map<String, Object> map = new HashMap<>(2);
        List<String> yearAndMonth = this.buildYearAndMonth(startTime, endTime);
        map.put("xAxisData", yearAndMonth);
        List<Object> resultList = new ArrayList<>(6);
        // 会议数量
        List<Integer> meetingCountList = new ArrayList<>(yearAndMonth.size());
        // 参会人次
        List<Integer> participantsCount = new ArrayList<>(yearAndMonth.size());
        // 参会人数
        List<Integer> participants = new ArrayList<>(yearAndMonth.size());
        List<CoverageReportPart> recruitList = coverageReportMapper.findCoverageRecruitList(productId, startTime, endTime);
        if(!CollectionUtils.isEmpty(recruitList)) {
            // 每个时间段的招募医生数量
            Map<String, Set<Long>> recruitListHcp = recruitList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                    Collectors.mapping(k -> k.getHcpId(), Collectors.toSet())));
            Map<String, Set<Long>> newRecruitHcp = this.buildMap(recruitListHcp, yearAndMonth, startTime);

            List<CoverageReportPart> meeting = coverageReportMapper.findCoverageMeeting(productId, startTime, endTime);
            if(!CollectionUtils.isEmpty(meeting)) {
                List<CoverageReportPart> meetingList = coverageReportMapper.findCoverageMeetingList(productId, startTime, endTime);
                // 每个月份的会议数量
                Map<String, Long> meetingMap = meeting.stream().collect(Collectors.toMap(k -> k.getTimeStr(), k -> k.getHciId(), (k1, k2) -> k2));
                // 每个月份的参会医生
                Map<String, List<Long>> hcpMeeting = meetingList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHcpId(), Collectors.toList())));
                List<Long> personTime = new ArrayList<>();
                yearAndMonth.forEach(k -> {
                    Set<Long> recruitHcpSet = newRecruitHcp.get(k);
                    if(CollectionUtils.isEmpty(recruitHcpSet)) {
                        recruitHcpSet = Collections.emptySet();
                    }
                    int meetingCount = meetingMap.get(k) != null ? meetingMap.get(k).intValue() : 0;
                    meetingCountList.add(meetingCount);
                    List<Long> person = hcpMeeting.get(k);
                    if(!CollectionUtils.isEmpty(person)) {
                        personTime.addAll(person);
                        personTime.retainAll(recruitHcpSet);
                        participantsCount.add(personTime.size());
                        Long hcpNum = personTime.stream().distinct().count();
                        participants.add(hcpNum.intValue());
                    } else {
                        participantsCount.add(0);
                        participants.add(0);
                    }
                    personTime.clear();
                });
            } else {
                yearAndMonth.forEach(k -> {
                    meetingCountList.add(0);
                    participantsCount.add(0);
                    participants.add(0);
                });
            }
        } else {
            yearAndMonth.forEach(k -> {
                meetingCountList.add(0);
                participantsCount.add(0);
                participants.add(0);
            });
        }
        resultList.add(meetingCountList);
        resultList.add(participantsCount);
        resultList.add(participants);
        map.put("seriesData", resultList);
        return map;
    }

    @Override
    public void exportMeeting(HttpServletResponse response, long productId, String startTime, String endTime) {
        List<String> yearAndMonth = this.buildYearAndMonth(startTime, endTime);
        List<CoverageMeetingResponse> rlist = new ArrayList<>();
        List<CoverageReportPart> recruitList = coverageReportMapper.findCoverageRecruitList(productId, startTime, endTime);
        if(!CollectionUtils.isEmpty(recruitList)) {
            // 每个时间段的招募医生数量
            Map<String, Set<Long>> recruitListHcp = recruitList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                    Collectors.mapping(k -> k.getHcpId(), Collectors.toSet())));
            Map<String, Set<Long>> newRecruitHcp = this.buildMap(recruitListHcp, yearAndMonth, startTime);

            List<CoverageReportPart> meeting = coverageReportMapper.findCoverageMeeting(productId, startTime, endTime);
            if(!CollectionUtils.isEmpty(meeting)) {
                List<CoverageReportPart> meetingList = coverageReportMapper.findCoverageMeetingList(productId, startTime, endTime);
                // 每个月份的会议数量
                Map<String, Long> meetingMap = meeting.stream().collect(Collectors.toMap(k -> k.getTimeStr(), k -> k.getHciId(), (k1, k2) -> k2));
                // 每个月份的参会医生
                Map<String, List<Long>> hcpMeeting = meetingList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHcpId(), Collectors.toList())));
                // 每个月份的参会时长
                Map<String, List<Long>> meetingTime = meetingList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHciId(), Collectors.toList())));
                List<Long> personTime = new ArrayList<>();
                yearAndMonth.forEach(k -> {
                    CoverageMeetingResponse res = new CoverageMeetingResponse();
                    res.setTimeStr(k);
                    int meetingCount = meetingMap.get(k) != null ? meetingMap.get(k).intValue() : 0;
                    res.setMeetingNum(meetingCount);
                    Set<Long> recruitHcpSet = newRecruitHcp.get(k);
                    if(CollectionUtils.isEmpty(recruitHcpSet)) {
                        recruitHcpSet = Collections.emptySet();
                    }
                    List<Long> person = hcpMeeting.get(k);
                    if(!CollectionUtils.isEmpty(person)) {
                        personTime.addAll(person);
                        personTime.retainAll(recruitHcpSet);
                        res.setHcpCount(personTime.size());
                        Long hcpNum = personTime.stream().distinct().count();
                        res.setHcpNum(hcpNum.intValue());
                    } else {
                        res.setHcpCount(0);
                        res.setHcpNum(0);
                    }
                    List<Long> timeList = meetingTime.get(k);
                    if(!CollectionUtils.isEmpty(timeList)) {
                        Long time = timeList.stream().mapToLong(Long::longValue).sum();
                        String totalTime = commonService.alterCallTimeContent(time);
                        res.setTotalTime(totalTime);
                    }
                    personTime.clear();
                    rlist.add(res);
                });

                // 总计部分
                CoverageMeetingResponse res = new CoverageMeetingResponse();
                List<Long> totalHcp = new ArrayList<>();
                Set<Long> recruitHcp = recruitList.stream().map(k -> k.getHcpId()).collect(Collectors.toSet());
                List<Long> coverageHcp = meetingList.stream().map(k -> k.getHcpId()).collect(Collectors.toList());
                totalHcp.addAll(coverageHcp);
                totalHcp.retainAll(recruitHcp);
                res.setTimeStr("总计");
                Long totalMeeting = meeting.stream().mapToLong(k -> k.getHciId()).sum();
                res.setMeetingNum(totalMeeting.intValue());
                res.setHcpCount(totalHcp.size());
                Long hcpNum = totalHcp.stream().distinct().count();
                res.setHcpNum(hcpNum.intValue());
                Long time = meetingList.stream().mapToLong(k -> k.getHciId()).sum();
                String totalTime = commonService.alterCallTimeContent(time);
                res.setTotalTime(totalTime);
                rlist.add(res);
            }
        }
        // 导出逻辑
        ExportExcelWrapper<CoverageMeetingResponse> exportExcelWrapper = new ExportExcelWrapper();
        exportExcelWrapper.exportExcel("月报—会议覆盖分析—".concat(startTime).concat("-").concat(endTime), "会议覆盖分析表", MEETING_DATA_TITLES,
                rlist, response, ExportExcelUtil.EXCEl_FILE_2007);
    }

    @Override
    public Map<String, Object> findContentListByProductIdAndTime(Long productId, String startTime, String endTime) {
        Map<String, Object> map = new HashMap<>(2);
        List<String> yearAndMonth = this.buildYearAndMonth(startTime, endTime);
        map.put("xAxisData", yearAndMonth);
        List<Object> resultList = new ArrayList<>(6);
        // 发送人数
        List<Integer> sendCountList = new ArrayList<>(yearAndMonth.size());
        // 阅读人数
        List<Integer> readCount = new ArrayList<>(yearAndMonth.size());
        // 阅读率
        List<Double> readingRate = new ArrayList<>(yearAndMonth.size());
        List<CoverageReportPart> recruitList = coverageReportMapper.findCoverageRecruitList(productId, startTime, endTime);
        if(!CollectionUtils.isEmpty(recruitList)) {
            // 每个时间段的招募医生数量
            Map<String, Set<Long>> recruitListHcp = recruitList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                    Collectors.mapping(k -> k.getHcpId(), Collectors.toSet())));
            Map<String, Set<Long>> newRecruitHcp = this.buildMap(recruitListHcp, yearAndMonth, startTime);

            List<CoverageReportPart> sendList = coverageReportMapper.findCoverageSendList(productId, startTime, endTime);
            if(!CollectionUtils.isEmpty(sendList)) {
                List<CoverageReportPart> readList = coverageReportMapper.findCoverageReadList(productId, startTime, endTime);
                // 发送医生时间map
                Map<String, Set<Long>> sendMap = sendList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHcpId(), Collectors.toSet())));
                // 阅读医生时间map
                Map<String, Set<Long>> readMap = readList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHcpId(), Collectors.toSet())));
                Set<Long> sSet = new HashSet<>();
                Set<Long> rSet = new HashSet<>();
                yearAndMonth.forEach(k -> {
                    Set<Long> recruitHcpSet = newRecruitHcp.get(k);
                    if(CollectionUtils.isEmpty(recruitHcpSet)) {
                        recruitHcpSet = Collections.emptySet();
                    }
                    int sendNum = 0;
                    Set<Long> kSend = sendMap.get(k);
                    if(!CollectionUtils.isEmpty(kSend)) {
                        sSet.addAll(kSend);
                        sSet.retainAll(recruitHcpSet);
                        sendNum = sSet.size();
                    }
                    sendCountList.add(sendNum);
                    Set<Long> rSend = readMap.get(k);
                    int readNum = 0;
                    if(!CollectionUtils.isEmpty(rSend)) {
                        rSet.addAll(rSend);
                        rSet.retainAll(recruitHcpSet);
                        readNum = rSet.size();
                    }
                    readCount.add(readNum);
                    double d = 0.0d;
                    if(sendNum > 0) {
                        d = ArithUtil.mul(ArithUtil.div(readNum, sendNum, 4), 100);
                    }
                    readingRate.add(d);
                    sSet.clear();
                    rSet.clear();
                });
            } else {
                yearAndMonth.forEach(k -> {
                    sendCountList.add(0);
                    readCount.add(0);
                    readingRate.add(0.0d);
                });
            }
        } else {
            yearAndMonth.forEach(k -> {
                sendCountList.add(0);
                readCount.add(0);
                readingRate.add(0.0d);
            });
        }
        resultList.add(sendCountList);
        resultList.add(readCount);
        resultList.add(readingRate);
        map.put("seriesData", resultList);
        return map;
    }

    @Override
    public void exportContent(HttpServletResponse response, long productId, String startTime, String endTime) {
        List<String> yearAndMonth = this.buildYearAndMonth(startTime, endTime);
        List<CoverageCallResponse> rlist = new ArrayList<>();
        List<CoverageReportPart> recruitList = coverageReportMapper.findCoverageRecruitList(productId, startTime, endTime);
        if(!CollectionUtils.isEmpty(recruitList)) {
            // 每个时间段的招募医生数量
            Map<String, Set<Long>> recruitListHcp = recruitList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                    Collectors.mapping(k -> k.getHcpId(), Collectors.toSet())));
            Map<String, Set<Long>> newRecruitHcp = this.buildMap(recruitListHcp, yearAndMonth, startTime);

            List<CoverageReportPart> sendList = coverageReportMapper.findCoverageSendList(productId, startTime, endTime);
            if (!CollectionUtils.isEmpty(sendList)) {
                List<CoverageReportPart> readList = coverageReportMapper.findCoverageReadList(productId, startTime, endTime);
                // 发送医生时间map
                Map<String, Set<Long>> sendMap = sendList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHcpId(), Collectors.toSet())));
                // 阅读医生时间map
                Map<String, Set<Long>> readMap = readList.stream().collect(Collectors.groupingBy(k -> k.getTimeStr(),
                        Collectors.mapping(k -> k.getHcpId(), Collectors.toSet())));
                Set<Long> sSet = new HashSet<>();
                Set<Long> rSet = new HashSet<>();
                yearAndMonth.forEach(k -> {
                    CoverageCallResponse res = new CoverageCallResponse();
                    res.setTimeStr(k);
                    Set<Long> recruitHcpSet = newRecruitHcp.get(k);
                    if (CollectionUtils.isEmpty(recruitHcpSet)) {
                        recruitHcpSet = Collections.emptySet();
                    }
                    int sendNum = 0;
                    Set<Long> kSend = sendMap.get(k);
                    if (!CollectionUtils.isEmpty(kSend)) {
                        sSet.addAll(kSend);
                        sSet.retainAll(recruitHcpSet);
                        sendNum = sSet.size();
                    }
                    res.setCoverageNum(sendNum);
                    Set<Long> rSend = readMap.get(k);
                    int readNum = 0;
                    if (!CollectionUtils.isEmpty(rSend)) {
                        rSet.addAll(rSend);
                        rSet.retainAll(recruitHcpSet);
                        readNum = rSet.size();
                    }
                    res.setCoverageCount(readNum);
                    double d = 0.0d;
                    if (sendNum > 0) {
                        d = ArithUtil.mul(ArithUtil.div(readNum, sendNum, 4), 100);
                    }
                    res.setTotalTime(d + "%");
                    rlist.add(res);
                    sSet.clear();
                    rSet.clear();
                });

                // 总计部分
                CoverageCallResponse res = new CoverageCallResponse();
                Set<Long> totalHcp = new HashSet<>();
                Set<Long> recruitHcp = recruitList.stream().map(k -> k.getHcpId()).collect(Collectors.toSet());
                Set<Long> coverageHcp = sendList.stream().map(k -> k.getHcpId()).collect(Collectors.toSet());
                totalHcp.addAll(coverageHcp);
                totalHcp.retainAll(recruitHcp);
                res.setTimeStr("总计");
                int sendNum = totalHcp.size();
                res.setCoverageNum(sendNum);
                totalHcp.clear();
                Set<Long> readHcp = readList.stream().map(k -> k.getHcpId()).collect(Collectors.toSet());
                totalHcp.addAll(readHcp);
                totalHcp.retainAll(recruitHcp);
                int readNum = totalHcp.size();
                res.setCoverageCount(readNum);
                double d = 0.0d;
                if(sendNum > 0) {
                    d = ArithUtil.mul(ArithUtil.div(readNum, sendNum, 4), 100);
                }
                res.setTotalTime(d + "%");
                rlist.add(res);
            }
        }
        // 导出逻辑
        ExportExcelWrapper<CoverageCallResponse> exportExcelWrapper = new ExportExcelWrapper();
        exportExcelWrapper.exportExcel("月报—内容覆盖分析—".concat(startTime).concat("-").concat(endTime), "内容覆盖分析表", CONTENT_DATA_TITLES,
                rlist, response, ExportExcelUtil.EXCEl_FILE_2007);
    }

    @Override
    public Map<String, Object> findPatientVolumeListByProductIdAndTime(Long productId, String startTime, String endTime) {
        Map<String, Object> map = new HashMap<>(2);
        List<Object> resultList = new ArrayList<>(6);
        // 招募人数
        List<Integer> recruitCount = new ArrayList<>();
        // 覆盖人数
        List<Integer> coverageCount = new ArrayList<>();
        // 覆盖次数
        List<Integer> coverageNum = new ArrayList<>();
        CoverageReportPart bean = coverageReportMapper.getFieldValueByProductId(productId);
        List<CoverageReportPart> recruitList = coverageReportMapper.findCoveragePatientRecruitList(productId, startTime, endTime);
        if(bean != null && StringUtils.isNotBlank(bean.getTimeStr())) {
            List<String> yearAndMonth = Arrays.asList(bean.getTimeStr().split(","));
            map.put("xAxisData", yearAndMonth);
            if(!CollectionUtils.isEmpty(recruitList)) {
                Map<String, Integer> recruitMap = recruitList.stream().collect(Collectors.toMap(k -> k.getTimeStr(), k -> k.getHcpId().intValue(), (k1, k2) -> k2));
                List<CoverageReportPart> coverageList = coverageReportMapper.findCoveragePatientList(productId, startTime, endTime);
                Map<String, Integer> coverageHcpMap = coverageList.stream().collect(Collectors.toMap(k -> k.getTimeStr(), k -> k.getHcpId().intValue(), (k1, k2) -> k2));
                Map<String, Integer> coverageMap = coverageList.stream().collect(Collectors.toMap(k -> k.getTimeStr(), k -> k.getHciId().intValue(), (k1, k2) -> k2));
                yearAndMonth.forEach(k -> {
                    Integer recruitNum = recruitMap.get(k);
                    recruitCount.add(recruitNum == null ? 0 : recruitNum);
                    Integer hcpNum = coverageHcpMap.get(k);
                    coverageCount.add(hcpNum == null ? 0 : hcpNum);
                    Integer num = coverageMap.get(k);
                    coverageNum.add(num == null ? 0 : num);
                });
            } else {
                yearAndMonth.forEach(k -> {
                    recruitCount.add(0);
                    coverageCount.add(0);
                    coverageNum.add(0);
                });
            }
            resultList.add(recruitCount);
            resultList.add(coverageCount);
            resultList.add(coverageNum);
            map.put("seriesData", resultList);
        } else {
            map.put("xAxisData", null);
            map.put("seriesData", null);
        }
        return map;
    }

    @Override
    public void exportPatientVolume(HttpServletResponse response, long productId, String startTime, String endTime) {
        List<CoverageMeetingResponse> rlist = new ArrayList<>();
        CoverageReportPart bean = coverageReportMapper.getFieldValueByProductId(productId);
        if(bean != null && StringUtils.isNotBlank(bean.getTimeStr())) {
            List<CoverageReportPart> recruitList = coverageReportMapper.findCoveragePatientRecruitList(productId, startTime, endTime);
            if(!CollectionUtils.isEmpty(recruitList)) {
                List<String> yearAndMonth = Arrays.asList(bean.getTimeStr().split(","));
                Map<String, Integer> recruitMap = recruitList.stream().collect(Collectors.toMap(k -> k.getTimeStr(), k -> k.getHcpId().intValue(), (k1, k2) -> k2));
                List<CoverageReportPart> coverageList = coverageReportMapper.findCoveragePatientList(productId, startTime, endTime);
                Map<String, Integer> coverageHcpMap = coverageList.stream().collect(Collectors.toMap(k -> k.getTimeStr(), k -> k.getHcpId().intValue(), (k1, k2) -> k2));
                Map<String, Integer> coverageMap = coverageList.stream().collect(Collectors.toMap(k -> k.getTimeStr(), k -> k.getHciId().intValue(), (k1, k2) -> k2));
                yearAndMonth.forEach(k -> {
                    CoverageMeetingResponse res = new CoverageMeetingResponse();
                    res.setTimeStr(k);
                    Integer recruitNum = recruitMap.get(k);
                    res.setMeetingNum(recruitNum);
                    Integer hcpNum = coverageHcpMap.get(k);
                    res.setHcpCount(hcpNum);
                    Integer num = coverageMap.get(k);
                    res.setHcpNum(num);
                    Double d = 0.0d;
                    if(recruitNum > 0) {
                        d = ArithUtil.mul(ArithUtil.div(hcpNum, recruitNum, 4), 100);
                    }
                    res.setTotalTime(d + "%");
                    rlist.add(res);
                });
            }
        }
        // 导出逻辑
        ExportExcelWrapper<CoverageMeetingResponse> exportExcelWrapper = new ExportExcelWrapper();
        exportExcelWrapper.exportExcel("月报—不同患者量的医生覆盖分析覆盖分析—".concat(startTime).concat("-").concat(endTime), "不同患者量的医生覆盖分析覆盖分析表", PATIENT_VOLUME_DATA_TITLES,
                rlist, response, ExportExcelUtil.EXCEl_FILE_2007);
    }

    /**
     * 根据前端传递的参数构建所有x轴坐标
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return list
     */
    private List<String> buildYearAndMonth(String startTime, String endTime) {
        List<String> list = new ArrayList<>(13);
        if(startTime.equals(endTime)) {
            list.add(startTime);
            return list;
        } else {
            int startY = Integer.parseInt(startTime.substring(0, 4));
            int endY = Integer.parseInt(endTime.substring(0, 4));
            for(int i = startY; i<=endY; i++) {
                for(String s : MONTH_ARRAY) {
                    list.add(i + "-".concat(s));
                }
            }
            return list.subList(list.indexOf(startTime), list.indexOf(endTime) + 1);
        }
    }

    /**
     * 封装累计每个时间段对应的数据
     * @param sourceMap 源数据
     * @param yearAndMonth 月份
     * @param startTime 开始时间
     * @return map
     */
    private Map<String, Set<Long>> buildMap(Map<String, Set<Long>> sourceMap, List<String> yearAndMonth, String startTime) {
        Map<String, Set<Long>> recruitMap = new HashMap<>(yearAndMonth.size());
        List<String> recruitKey = sourceMap.keySet().stream().sorted().collect(Collectors.toList());
        Set<Long> start = new HashSet<>();
        int index = recruitKey.indexOf(startTime);
        for(int i=0; i<= index; i++) {
            start.addAll(sourceMap.get(recruitKey.get(i)));
        }
        recruitMap.put(startTime, start);
        if(index == -1) {
            index = 0;
        }
        for(int i=index; i<recruitKey.size(); i++) {
            recruitMap.put(recruitKey.get(i), sourceMap.get(recruitKey.get(i)));
        }
        for(int i=0; i<yearAndMonth.size()-1; i++) {
            Set<Long> setI = recruitMap.get(yearAndMonth.get(i));
            if(setI == null) {
                setI = Collections.emptySet();
            }
            String j = yearAndMonth.get(i+1);
            Set<Long> setJ = recruitMap.get(j);
            if(setJ == null) {
                setJ = Collections.emptySet();
            }
            setJ.addAll(setI);
            recruitMap.put(j, setJ);
        }
        return recruitMap;
    }

}
