package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v2_5.ProductVisitResultResponse;
import com.nuoxin.virtual.rep.api.enums.RoleTypeEnum;
import com.nuoxin.virtual.rep.api.mybatis.DailyReportMapper;
import com.nuoxin.virtual.rep.api.mybatis.ProductTargetMapper;
import com.nuoxin.virtual.rep.api.mybatis.VirtualProductVisitResultMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v3_0.DailyReportService;
import com.nuoxin.virtual.rep.api.utils.CalculateUtil;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DailyReportRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.statistics.ProductTargetResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DailyReportResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.daily.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tiancun
 * @date 2019-05-09
 */
@Service
public class DailyReportServiceImpl implements DailyReportService {

    @Resource
    private DailyReportMapper dailyReportMapper;

    @Resource
    private ProductTargetMapper productTargetMapper;

    @Resource
    private VirtualProductVisitResultMapper virtualProductVisitResultMapper;

    @Resource
    private CommonService commonService;


    @Override
    public void exportDailyReport(HttpServletResponse response, DailyReportRequest request, DrugUser drugUser) {
        List<Long> productIdList = request.getProductIdList();
        if (CollectionsUtil.isEmptyList(productIdList) || productIdList.size() > 1){
            throw new BusinessException(ErrorEnum.ERROR, "只能选择一个产品！");
        }

        if (CollectionsUtil.isEmptyList(request.getDrugUserIdList())){
            throw new BusinessException(ErrorEnum.ERROR, "代表不能为空！");
        }



        // 拜访的明细，按照天



        Long productId = productIdList.get(0);
        Map<String, String> dailyReportTitle = this.getExportDailyReportTitle(productId);
        List<LinkedHashMap<String, Object>> dailyReportExportData = this.getAllDailyReportExportData(request, drugUser);


    }

    /**
     * 得到全部的日报导出数据
     * @param request
     * @param drugUser
     * @return
     */
    private List<LinkedHashMap<String, Object>> getAllDailyReportExportData(DailyReportRequest request, DrugUser drugUser) {

        List<LinkedHashMap<String, Object>> list = new ArrayList<>();
        Long roleId = drugUser.getRoleId();
        if (RoleTypeEnum.MANAGER.getType().equals(roleId) || RoleTypeEnum.PROJECT_MANAGER.getType().equals(roleId)){

            List<Long> drugUserIdList = request.getDrugUserIdList();
            for (Long drugUserId : drugUserIdList) {
                List<Long> selectDrugUserIdList = new ArrayList<>();
                selectDrugUserIdList.add(drugUserId);
                request.setDrugUserIdList(selectDrugUserIdList);
                LinkedHashMap<String, Object> exportDailyReportData = this.getExportDailyReportData(request);
                list.add(exportDailyReportData);
            }

        }else{

            List<Long> drugUserIdList = new ArrayList<>();
            drugUserIdList.add(drugUser.getId());
            request.setDrugUserIdList(drugUserIdList);
            LinkedHashMap<String, Object> exportDailyReportData = this.getExportDailyReportData(request);
            list.add(exportDailyReportData);
        }


        return list;


    }

    @Override
    public DailyReportResponse getDailyReport(DailyReportRequest request) {


        /*
         * 我的业绩相关
         */
        // 招募医生数
        Integer recruitDoctorNum = dailyReportMapper.recruitDoctorNum(request);

        // 有收益的覆盖医生数
        Integer activeCoverDoctorNum = dailyReportMapper.activeCoverDoctorNum(request);

        // 多渠道覆盖医生数
        Integer mulChannelDoctorNum = dailyReportMapper.mulChannelDoctorNum(request);



        // 覆盖医生数
        Integer coverDoctorNum = dailyReportMapper.coverDoctorNum(request);

        // 覆盖医院数
        Integer coverHospitalNum = dailyReportMapper.coverHospitalNum(request);


        // 未招募医生数
        Integer noRecruitDoctorNum = 0;





        // 产品设置的不同拜访结果医生数统计
        List<VisitResultDoctorNumStatisticsResponse> visitResultDoctorNumList = dailyReportMapper.getVisitResultDoctorNumList(request);

        // 目标招募医院医生数量
        Integer targetHospital = 0;
        Integer targetDoctor = 0;
        ProductTargetResponseBean productTarget = productTargetMapper.getProductTarget(request.getProductIdList().get(0));
        if (productTarget != null){
            if(productTarget.getTargetDoctor() != null){
                targetDoctor = productTarget.getTargetDoctor();
            }

            if (productTarget.getTargetHospital() != null){
                targetHospital = productTarget.getTargetHospital();
            }

        }

        noRecruitDoctorNum = targetDoctor - recruitDoctorNum;
        if (noRecruitDoctorNum < 0){
            noRecruitDoctorNum = 0;
        }

        // 医生招募率
        String recruitDoctorRate = CalculateUtil.getPercentage(recruitDoctorNum, targetDoctor, 2);

        // 微信回复人数
        Integer wechatReplyDoctorNum = dailyReportMapper.getWechatReplyDoctorNum(request);


        // 微信回复次数
        Integer wechatReplyDoctorCount = dailyReportMapper.getWechatReplyDoctorCount(request);

        // 有微信的人数
        Integer hasWechatDoctorNum = dailyReportMapper.hasWechatDoctorNum(request);

        // 添加微信的人数
        Integer addWechatDoctorNum = dailyReportMapper.addWechatDoctorNum(request);

        // 有需求的医生人数
        Integer hasDemandDoctorNum = dailyReportMapper.hasDemandDoctorNum(request);

        // 有AE的医生人数
        Integer hasAeDoctorNum = dailyReportMapper.hasAeDoctorNum(request);

        // 退出项目的医生数
        Integer quitDoctorNum = dailyReportMapper.quitDoctorNum(request);

        // 招募的医院数
        Integer recruitHospitalNum = dailyReportMapper.recruitHospitalNum(request);
        Integer noRecruitHospitalNum = 0;
        noRecruitHospitalNum = targetHospital - recruitHospitalNum;
        if (noRecruitDoctorNum < 0){
            noRecruitHospitalNum = 0;
        }

        // 医院招募率
        String recruitHospitalRate = CalculateUtil.getPercentage(recruitHospitalNum, targetHospital, 2);

        List<VisitResultHospitalNumStatisticsResponse> visitResultHospitalNumList = dailyReportMapper.getVisitResultHospitalNumList(request);

        //CalculateUtil.getPercentage();




        DailyReportResponse dailyReportResponse = new DailyReportResponse();
        dailyReportResponse.setRecruitDoctorNum(recruitDoctorNum);
        dailyReportResponse.setNoRecruitDoctorNum(noRecruitDoctorNum);
        dailyReportResponse.setActiveCoverDoctorNum(activeCoverDoctorNum);
        dailyReportResponse.setMulChannelDoctorNum(mulChannelDoctorNum);
        dailyReportResponse.setVisitResultDoctorNumList(visitResultDoctorNumList);
        dailyReportResponse.setRecruitDoctorRate(recruitDoctorRate);
        dailyReportResponse.setWechatReplyDoctorNum(wechatReplyDoctorNum);
        dailyReportResponse.setWechatReplyDoctorCount(wechatReplyDoctorCount);
        dailyReportResponse.setHasWechatDoctorNum(hasWechatDoctorNum);
        dailyReportResponse.setAddWechatDoctorNum(addWechatDoctorNum);
        dailyReportResponse.setHasDemandDoctorNum(hasDemandDoctorNum);
        dailyReportResponse.setHasAeDoctorNum(hasAeDoctorNum);
        dailyReportResponse.setQuitDoctorNum(quitDoctorNum);
        dailyReportResponse.setRecruitHospitalNum(recruitHospitalNum);
        dailyReportResponse.setNoRecruitHospitalNum(noRecruitHospitalNum);
        dailyReportResponse.setRecruitHospitalRate(recruitHospitalRate);
        dailyReportResponse.setVisitResultHospitalNumList(visitResultHospitalNumList);
        dailyReportResponse.setTargetDoctor(targetDoctor);
        dailyReportResponse.setTargetHospital(targetHospital);
        return dailyReportResponse;

    }

    @Override
    public MyAchievementResponse getMyAchievement(DailyReportRequest request) {

        // 招募医生数
        Integer recruitDoctorNum = dailyReportMapper.recruitDoctorNum(request);

        // 有收益的覆盖医生数
        Integer activeCoverDoctorNum = dailyReportMapper.activeCoverDoctorNum(request);

        // 多渠道覆盖医生数
        Integer mulChannelDoctorNum = dailyReportMapper.mulChannelDoctorNum(request);

        MyAchievementResponse myAchievement = new MyAchievementResponse();
        myAchievement.setRecruitDoctorNum(recruitDoctorNum);
        myAchievement.setActiveCoverDoctorNum(activeCoverDoctorNum);
        myAchievement.setMulChannelDoctorNum(mulChannelDoctorNum);

        return myAchievement;

    }

    @Override
    public CallVisitStatisticsResponse getCallVisitStatistics(DailyReportRequest request) {
        CallVisitStatisticsResponse callVisitStatistics = dailyReportMapper.getCallVisitStatistics(request);
        if (callVisitStatistics == null){
            callVisitStatistics = new CallVisitStatisticsResponse();
        }

        Integer callConnectCount = dailyReportMapper.callConnectCount(request);
        Integer callUnConnectCount = dailyReportMapper.callUnConnectCount(request);

        callVisitStatistics.setConnectCallCount(callConnectCount);
        callVisitStatistics.setUnConnectCallCount(callUnConnectCount);
        callVisitStatistics.setTotalCallTime(commonService.alterCallTimeContent(callVisitStatistics.getTotalCallSecond()));


        return callVisitStatistics;

    }

    @Override
    public List<VisitChannelDoctorNumResponse> getVisitChannelDoctorNumList(DailyReportRequest request) {

        List<VisitChannelDoctorNumResponse> visitChannelDoctorNumList = dailyReportMapper.getVisitChannelDoctorNumList(request);

        return visitChannelDoctorNumList;
    }

    @Override
    public List<VisitResultDoctorNumStatisticsResponse> getVisitResultDoctorNum(DailyReportRequest request) {
        List<VisitResultDoctorNumStatisticsResponse> visitResultDoctorNumList = dailyReportMapper.getVisitResultDoctorNumList(request);
        return visitResultDoctorNumList;
    }

    @Override
    public List<VisitResultHospitalNumStatisticsResponse> getVisitResultHospitalNum(DailyReportRequest request) {

        List<VisitResultHospitalNumStatisticsResponse> visitResultHospitalNumList = dailyReportMapper.getVisitResultHospitalNumList(request);

        return visitResultHospitalNumList;

    }

    @Override
    public DoctorRecruitResponse getDoctorRecruit(DailyReportRequest request) {
        ProductTargetResponseBean productTarget = productTargetMapper.getProductTarget(request.getProductIdList().get(0));
        Integer targetDoctor = 0;
        if (productTarget != null){
            targetDoctor = productTarget.getTargetDoctor();
            if (targetDoctor == null || targetDoctor < 0){
                targetDoctor = 0;
            }
        }


        Integer recruitDoctorNum = dailyReportMapper.recruitDoctorNum(request);
        Integer noRecruitDoctorNum = targetDoctor - recruitDoctorNum;
        if (noRecruitDoctorNum == null || noRecruitDoctorNum < 0){
            noRecruitDoctorNum = 0;
        }


        String recruitRate = CalculateUtil.getPercentage(recruitDoctorNum, targetDoctor, 2);


        DoctorRecruitResponse doctorRecruit = new DoctorRecruitResponse();

        doctorRecruit.setRecruitDoctorNum(recruitDoctorNum);
        doctorRecruit.setNoRecruitDoctorNum(noRecruitDoctorNum);
        doctorRecruit.setTargetDoctor(targetDoctor);
        doctorRecruit.setRecruitRate(recruitRate);


        return doctorRecruit;

    }

    @Override
    public HospitalRecruitResponse getHospitalRecruit(DailyReportRequest request) {
        ProductTargetResponseBean productTarget = productTargetMapper.getProductTarget(request.getProductIdList().get(0));
        Integer targetHospital = 0;
        if (targetHospital != null){
            targetHospital = productTarget.getTargetDoctor();
            if (targetHospital == null || targetHospital < 0){
                targetHospital = 0;
            }
        }


        Integer recruitHospitalNum = dailyReportMapper.recruitHospitalNum(request);
        Integer noRecruitHospitallNum = targetHospital - recruitHospitalNum;
        if (noRecruitHospitallNum == null || noRecruitHospitallNum < 0){
            noRecruitHospitallNum = 0;
        }

        String recruitRate = CalculateUtil.getPercentage(recruitHospitalNum, targetHospital, 2);

        HospitalRecruitResponse hospitalRecruit = new HospitalRecruitResponse();

        hospitalRecruit.setRecruitHospitalNum(recruitHospitalNum);
        hospitalRecruit.setNoRecruitHospitalNum(noRecruitHospitallNum);
        hospitalRecruit.setTargetHospital(targetHospital);
        hospitalRecruit.setRecruitRate(recruitRate);


        return hospitalRecruit;
    }


    /**
     * 导出的Excel数据
     * @param request
     * @return
     */
    private LinkedHashMap<String, Object> getExportDailyReportData(DailyReportRequest request){

        DailyReportResponse dailyReport = this.getDailyReport(request);
        Integer recruitDoctorNum = dailyReport.getRecruitDoctorNum();
        Integer activeCoverDoctorNum = dailyReport.getActiveCoverDoctorNum();
        Integer mulChannelDoctorNum = dailyReport.getMulChannelDoctorNum();
        List<VisitResultDoctorNumStatisticsResponse> visitResultDoctorNumList = dailyReport.getVisitResultDoctorNumList();
        Integer targetDoctor = dailyReport.getTargetDoctor();
        Integer noRecruitDoctorNum = dailyReport.getNoRecruitDoctorNum();
        String recruitDoctorRate = dailyReport.getRecruitDoctorRate();
        Integer wechatReplyDoctorNum = dailyReport.getWechatReplyDoctorNum();
        Integer wechatReplyDoctorCount = dailyReport.getWechatReplyDoctorCount();
        Integer hasWechatDoctorNum = dailyReport.getHasWechatDoctorNum();
        Integer addWechatDoctorNum = dailyReport.getAddWechatDoctorNum();
        Integer hasDemandDoctorNum = dailyReport.getHasDemandDoctorNum();
        Integer hasAeDoctorNum = dailyReport.getHasAeDoctorNum();
        Integer quitDoctorNum = dailyReport.getQuitDoctorNum();
        Integer targetHospital = dailyReport.getTargetHospital();
        Integer recruitHospitalNum = dailyReport.getRecruitHospitalNum();
        String recruitHospitalRate = dailyReport.getRecruitHospitalRate();
        List<VisitResultHospitalNumStatisticsResponse> visitResultHospitalNumList = dailyReport.getVisitResultHospitalNumList();

        LinkedHashMap<String, Object> dataMap = new LinkedHashMap<>();

        dataMap.put("recruitDoctorNum", recruitDoctorNum);
        dataMap.put("activeCoverDoctorNum", activeCoverDoctorNum);
        dataMap.put("mulChannelDoctorNum", mulChannelDoctorNum);

        if (CollectionsUtil.isNotEmptyList(visitResultDoctorNumList)){
            visitResultDoctorNumList.forEach(d->{
                Long id = d.getId();
                Integer doctorNum = d.getDoctorNum();
                dataMap.put("d_" + id, doctorNum);
            });
        }


        dataMap.put("targetDoctor", targetDoctor);
        dataMap.put("noRecruitDoctorNum", noRecruitDoctorNum);
        dataMap.put("recruitDoctorRate", recruitDoctorRate);
        dataMap.put("wechatReplyDoctorNum", wechatReplyDoctorNum);
        dataMap.put("wechatReplyDoctorCount", wechatReplyDoctorCount);
        dataMap.put("hasWechatDoctorNum", hasWechatDoctorNum);
        dataMap.put("addWechatDoctorNum", addWechatDoctorNum);
        dataMap.put("hasDemandDoctorNum", hasDemandDoctorNum);
        dataMap.put("hasAeDoctorNum", hasAeDoctorNum);
        dataMap.put("quitDoctorNum", quitDoctorNum);
        dataMap.put("targetHospital", targetHospital);
        dataMap.put("recruitHospital", recruitHospitalNum);
        dataMap.put("recruitHospitalRate", recruitHospitalRate);

        if (CollectionsUtil.isNotEmptyList(visitResultHospitalNumList)){
            visitResultHospitalNumList.forEach(h->{
                Long id = h.getId();
                Integer hospitalNum = h.getHospitalNum();
                dataMap.put("h_" + id, hospitalNum);
            });
        }

        return dataMap;

    }



    /**
     * 导出的Excel 表头
     * @param productId
     * @return
     */
    private Map<String, String> getExportDailyReportTitle(Long productId) {

        Map<String, String> titleMap = new LinkedHashMap<>();
        titleMap.put("recruitDoctorNum", "招募医生数");
        titleMap.put("activeCoverDoctorNum", "活跃覆盖医生数");
        titleMap.put("mulChannelDoctorNum", "多渠道覆盖医生数");

        List<ProductVisitResultResponse> recruitDoctorVisitResultList = virtualProductVisitResultMapper.selectVisitResultList(productId);
        if (CollectionsUtil.isNotEmptyList(recruitDoctorVisitResultList)){
            recruitDoctorVisitResultList.forEach(r->{
                String visitResult = r.getVisitResult();
                Long id = r.getId();
                titleMap.put("d_" + id, visitResult.concat("招募医生数"));
            });
        }


        titleMap.put("targetDoctor", "目标招募医生数");
        titleMap.put("noRecruitDoctorNum", "未招募医生数");
        titleMap.put("recruitDoctorRate", "医生招募率");
        titleMap.put("wechatReplyDoctorNum", "微信回复人数");
        titleMap.put("wechatReplyDoctorCount", "微信回复人次");
        titleMap.put("hasWechatDoctorNum", "有微信医生人数");
        titleMap.put("addWechatDoctorNum", "添加微信医生人数");
        titleMap.put("hasDemandDoctorNum", "有需求医生数");
        titleMap.put("hasAeDoctorNum", "有AE医生数");
        titleMap.put("quitDoctorNum", "退出项目医生数");
        titleMap.put("targetHospital", "目标医院数");
        titleMap.put("recruitHospital", "招募医院数");
        titleMap.put("recruitHospitalRate", "医院招募率");

        if (CollectionsUtil.isNotEmptyList(recruitDoctorVisitResultList)){
            recruitDoctorVisitResultList.forEach(r->{
                String visitResult = r.getVisitResult();
                Long id = r.getId();
                titleMap.put("h_" + id, visitResult.concat("招募医院数"));
            });
        }

        return titleMap;

    }
}
