package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v2_5.ProductVisitResultResponse;
import com.nuoxin.virtual.rep.api.enums.RoleTypeEnum;
import com.nuoxin.virtual.rep.api.enums.VisitChannelEnum;
import com.nuoxin.virtual.rep.api.enums.VisitResultTypeEnum;
import com.nuoxin.virtual.rep.api.mybatis.DailyReportMapper;
import com.nuoxin.virtual.rep.api.mybatis.ProductTargetMapper;
import com.nuoxin.virtual.rep.api.mybatis.VirtualProductVisitResultMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v3_0.DailyReportService;
import com.nuoxin.virtual.rep.api.utils.*;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.excel.SheetRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DailyReportRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.statistics.ProductTargetResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DailyReportResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.VisitCountStatisticsResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.VisitDoctorStatisticsResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.VisitHospitalStatisticsResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.daily.*;
import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    private DrugUserRepository drugUserRepository;

    @Override
    public void exportDailyReport(HttpServletResponse response, DailyReportRequest request, DrugUser drugUser) {
        this.checkoutExportDailyReportRequest(request);
        Long productId = request.getProductIdList().get(0);

        // 明细
        Map<String, String> dailyReportDetailTitle = this.getDailyReportDetailTitle();
        List<LinkedHashMap<String, Object>> dailyReportDetailData = this.getDailyReportDetailData(request);

        // 招募汇总
        Map<String, String> dailyReportRecruitTitle = this.getDailyReportRecruitTitle();
        List<LinkedHashMap<String, Object>> dailyReportRecruitData = this.getDailyReportRecruitData(request, productId);

        // 覆盖汇总

        Map<String, String> dailyReportCoverTitle = this.getDailyReportCoverTitle();
        List<LinkedHashMap<String, Object>> dailyReportCoverData = this.getDailyReportCoverData(request);

        // 日报
        Map<String, String> dailyReportTitle = this.getExportDailyReportTitle(productId);
        List<LinkedHashMap<String, Object>> dailyReportExportData = this.getAllDailyReportExportData(request, drugUser);


        List<SheetRequestBean> sheetList = new ArrayList<>();
        SheetRequestBean detailSheet = new SheetRequestBean();
        detailSheet.setDataList(dailyReportDetailData);
        detailSheet.setTitleMap(dailyReportDetailTitle);
        detailSheet.setSheetName("明细");
        sheetList.add(detailSheet);

        SheetRequestBean recruitSheet = new SheetRequestBean();
        recruitSheet.setDataList(dailyReportRecruitData);
        recruitSheet.setTitleMap(dailyReportRecruitTitle);
        recruitSheet.setSheetName("招募汇总");
        sheetList.add(recruitSheet);

        SheetRequestBean coverSheet = new SheetRequestBean();
        coverSheet.setDataList(dailyReportCoverData);
        coverSheet.setTitleMap(dailyReportCoverTitle);
        coverSheet.setSheetName("覆盖汇总");
        sheetList.add(coverSheet);


        SheetRequestBean dailyReportSheet = new SheetRequestBean();
        dailyReportSheet.setDataList(dailyReportExportData);
        dailyReportSheet.setTitleMap(dailyReportTitle);
        dailyReportSheet.setSheetName("日报");
        sheetList.add(dailyReportSheet);
        HSSFWorkbook wb= ExportExcel.excelLinkedHashMapExport(sheetList);


        OutputStream ouputStream = null;
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("日报.xls","UTF-8"));
            response.setHeader("Pragma", "No-cache");
            ouputStream = response.getOutputStream();
            if(ouputStream!=null){
                wb.write(ouputStream);
            }
            ouputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                ouputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }




    /**
     * 检查请求参数
     * @param request
     */
    private void checkoutDailyReportRequest(DailyReportRequest request){

        if (CollectionsUtil.isEmptyList(request.getDrugUserIdList())){
            throw new BusinessException(ErrorEnum.ERROR, "代表不能为空！");
        }
    }


    /**
     * 检查请求参数
     * @param request
     */
    private void checkoutExportDailyReportRequest(DailyReportRequest request){

        List<Long> productIdList = request.getProductIdList();
        if (CollectionsUtil.isEmptyList(productIdList) || productIdList.size() > 1){
            throw new BusinessException(ErrorEnum.ERROR, "只能选择一个产品！");
        }

        if (CollectionsUtil.isEmptyList(request.getDrugUserIdList())){
            throw new BusinessException(ErrorEnum.ERROR, "代表不能为空！");
        }
    }

    /**
     * 得到全部的日报导出数据
     * @param request
     * @param drugUser
     * @return
     */
    private List<LinkedHashMap<String, Object>> getAllDailyReportExportData(DailyReportRequest request, DrugUser drugUser) {

        List<LinkedHashMap<String, Object>> list = new ArrayList<>();

        // drugUser暂时废弃，不适用，看看需求更倾向那个

//        Long roleId = drugUser.getRoleId();
//        if (RoleTypeEnum.MANAGER.getType().equals(roleId) || RoleTypeEnum.PROJECT_MANAGER.getType().equals(roleId)){
//
//            List<Long> drugUserIdList = request.getDrugUserIdList();
//            for (Long drugUserId : drugUserIdList) {
//                List<Long> selectDrugUserIdList = new ArrayList<>();
//                selectDrugUserIdList.add(drugUserId);
//                request.setDrugUserIdList(selectDrugUserIdList);
//                LinkedHashMap<String, Object> exportDailyReportData = this.getExportDailyReportData(request);
//
//                DrugUser findDrugUser = drugUserRepository.findFirstById(drugUserId);
//                exportDailyReportData.put("drugUserId", findDrugUser.getId());
//                exportDailyReportData.put("drugUserName", findDrugUser.getName());
//
//                list.add(exportDailyReportData);
//            }
//
//        }else{
//
//            List<Long> drugUserIdList = new ArrayList<>();
//            drugUserIdList.add(drugUser.getId());
//            request.setDrugUserIdList(drugUserIdList);
//            LinkedHashMap<String, Object> exportDailyReportData = this.getExportDailyReportData(request);
//            exportDailyReportData.put("drugUserId", drugUser.getId());
//            exportDailyReportData.put("drugUserName", drugUser.getName());
//            list.add(exportDailyReportData);
//        }




        List<Long> drugUserIdList = request.getDrugUserIdList();
        for (Long drugUserId : drugUserIdList) {
            List<Long> selectDrugUserIdList = new ArrayList<>();
            selectDrugUserIdList.add(drugUserId);
            request.setDrugUserIdList(selectDrugUserIdList);
            LinkedHashMap<String, Object> exportDailyReportData = this.getExportDailyReportData(request);

            DrugUser findDrugUser = drugUserRepository.findFirstById(drugUserId);
            exportDailyReportData.put("drugUserId", findDrugUser.getId());
            exportDailyReportData.put("drugUserName", findDrugUser.getName());

            list.add(exportDailyReportData);
        }


        return list;


    }

    @Override
    public DailyReportResponse getDailyReport(DailyReportRequest request) {



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

        this.checkoutDailyReportRequest(request);

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

        this.checkoutDailyReportRequest(request);

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
        this.checkoutDailyReportRequest(request);

        List<VisitChannelDoctorNumResponse> list = new ArrayList<>();
        // 不能合起来用一个SQL查询
        Integer wechatDoctorNum = dailyReportMapper.callDoctorNum(request, VisitChannelEnum.WECHAT.getVisitChannel());
        VisitChannelDoctorNumResponse wechatVisit = new VisitChannelDoctorNumResponse();
        wechatVisit.setVisitChannel(VisitChannelEnum.WECHAT.getVisitChannel());
        wechatVisit.setVisitChannelStr(VisitChannelEnum.WECHAT.getVisitChannelStr());
        wechatVisit.setDoctorNum(wechatDoctorNum);
        list.add(wechatVisit);


        Integer interviewDoctorNum = dailyReportMapper.callDoctorNum(request, VisitChannelEnum.INTERVIEW.getVisitChannel());
        VisitChannelDoctorNumResponse interviewVisit = new VisitChannelDoctorNumResponse();
        interviewVisit.setVisitChannel(VisitChannelEnum.INTERVIEW.getVisitChannel());
        interviewVisit.setVisitChannelStr(VisitChannelEnum.INTERVIEW.getVisitChannelStr());
        interviewVisit.setDoctorNum(interviewDoctorNum);
        list.add(interviewVisit);


        Integer smsDoctorNum = dailyReportMapper.callDoctorNum(request, VisitChannelEnum.MESSAGE.getVisitChannel());
        VisitChannelDoctorNumResponse smsVisit = new VisitChannelDoctorNumResponse();
        smsVisit.setVisitChannel(VisitChannelEnum.MESSAGE.getVisitChannel());
        smsVisit.setVisitChannelStr(VisitChannelEnum.MESSAGE.getVisitChannelStr());
        smsVisit.setDoctorNum(smsDoctorNum);
        list.add(smsVisit);


        Integer emailDoctorNum = dailyReportMapper.callDoctorNum(request, VisitChannelEnum.EMAIL.getVisitChannel());
        VisitChannelDoctorNumResponse emailVisit = new VisitChannelDoctorNumResponse();
        emailVisit.setVisitChannel(VisitChannelEnum.EMAIL.getVisitChannel());
        emailVisit.setVisitChannelStr(VisitChannelEnum.EMAIL.getVisitChannelStr());
        emailVisit.setDoctorNum(emailDoctorNum);
        list.add(emailVisit);

        return list;
    }

    @Override
    public List<VisitResultDoctorNumStatisticsResponse> getVisitResultDoctorNum(DailyReportRequest request) {
        this.checkoutDailyReportRequest(request);
        List<VisitResultDoctorNumStatisticsResponse> visitResultDoctorNumList = dailyReportMapper.getVisitResultDoctorNumList(request);
        return visitResultDoctorNumList;
    }

    @Override
    public List<VisitResultHospitalNumStatisticsResponse> getVisitResultHospitalNum(DailyReportRequest request) {

        this.checkoutDailyReportRequest(request);

        List<VisitResultHospitalNumStatisticsResponse> visitResultHospitalNumList = dailyReportMapper.getVisitResultHospitalNumList(request);
        return visitResultHospitalNumList;

    }

    @Override
    public List<VisitTypeDoctorNumStatisticsResponse> getVisitTypeDoctorNum(DailyReportRequest request) {

        this.checkoutDailyReportRequest(request);

        Integer contactDoctor = dailyReportMapper.visitResultTypeDoctorNum(request, VisitResultTypeEnum.CONTACT.getType());

        VisitTypeDoctorNumStatisticsResponse contact = new VisitTypeDoctorNumStatisticsResponse();
        contact.setVisitType(VisitResultTypeEnum.CONTACT.getType());
        contact.setVisitTypeStr(VisitResultTypeEnum.CONTACT.getName());
        contact.setDoctorNum(contactDoctor);

        Integer successDoctor = dailyReportMapper.visitResultTypeDoctorNum(request, VisitResultTypeEnum.SUCCESS.getType());
        VisitTypeDoctorNumStatisticsResponse success = new VisitTypeDoctorNumStatisticsResponse();
        success.setVisitType(VisitResultTypeEnum.SUCCESS.getType());
        success.setVisitTypeStr(VisitResultTypeEnum.SUCCESS.getName());
        success.setDoctorNum(successDoctor);

        Integer coverDoctor = dailyReportMapper.visitResultTypeDoctorNum(request, VisitResultTypeEnum.COVER.getType());
        VisitTypeDoctorNumStatisticsResponse cover = new VisitTypeDoctorNumStatisticsResponse();
        cover.setVisitType(VisitResultTypeEnum.CONTACT.getType());
        cover.setVisitTypeStr(VisitResultTypeEnum.CONTACT.getName());
        cover.setDoctorNum(coverDoctor);



        List<VisitTypeDoctorNumStatisticsResponse> list = new ArrayList<>();
        list.add(contact);
        list.add(success);
        list.add(cover);

        return list;
    }

    @Override
    public List<VisitTypeHospitalNumStatisticsResponse> getVisitTypeHospitalNum(DailyReportRequest request) {
        this.checkoutDailyReportRequest(request);

        Integer contactHospital = dailyReportMapper.visitResultTypeHospitalNum(request, VisitResultTypeEnum.CONTACT.getType());

        VisitTypeHospitalNumStatisticsResponse contact = new VisitTypeHospitalNumStatisticsResponse();
        contact.setVisitType(VisitResultTypeEnum.CONTACT.getType());
        contact.setVisitTypeStr(VisitResultTypeEnum.CONTACT.getName());
        contact.setHospitalNum(contactHospital);

        Integer successHospital = dailyReportMapper.visitResultTypeHospitalNum(request, VisitResultTypeEnum.SUCCESS.getType());
        VisitTypeHospitalNumStatisticsResponse success = new VisitTypeHospitalNumStatisticsResponse();
        success.setVisitType(VisitResultTypeEnum.SUCCESS.getType());
        success.setVisitTypeStr(VisitResultTypeEnum.SUCCESS.getName());
        success.setHospitalNum(successHospital);

        Integer coverHospital = dailyReportMapper.visitResultTypeHospitalNum(request, VisitResultTypeEnum.COVER.getType());
        VisitTypeHospitalNumStatisticsResponse cover = new VisitTypeHospitalNumStatisticsResponse();
        cover.setVisitType(VisitResultTypeEnum.CONTACT.getType());
        cover.setVisitTypeStr(VisitResultTypeEnum.CONTACT.getName());
        cover.setHospitalNum(coverHospital);



        List<VisitTypeHospitalNumStatisticsResponse> list = new ArrayList<>();
        list.add(contact);
        list.add(success);
        list.add(cover);

        return list;
    }

    @Override
    public DoctorRecruitResponse getDoctorRecruit(DailyReportRequest request) {

        this.checkoutDailyReportRequest(request);

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

        this.checkoutDailyReportRequest(request);

        ProductTargetResponseBean productTarget = productTargetMapper.getProductTarget(request.getProductIdList().get(0));
        Integer targetHospital = 0;
        if (productTarget != null){
            targetHospital = productTarget.getTargetHospital();
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

    @Override
    public DoctorVisitResponse getDoctorVisit(DailyReportRequest request) {
        this.checkoutDailyReportRequest(request);

        Integer wechatReplyDoctorNum = dailyReportMapper.getWechatReplyDoctorNum(request);
        Integer wechatReplyDoctorCount = dailyReportMapper.getWechatReplyDoctorCount(request);
        Integer hasWechatDoctorNum = dailyReportMapper.hasWechatDoctorNum(request);
        Integer addWechatDoctorNum = dailyReportMapper.addWechatDoctorNum(request);
        Integer hasAeDoctorNum = dailyReportMapper.hasAeDoctorNum(request);
        Integer hasDemandDoctorNum = dailyReportMapper.hasDemandDoctorNum(request);
        Integer quitDoctorNum = dailyReportMapper.quitDoctorNum(request);


        DoctorVisitResponse doctorVisit = new DoctorVisitResponse();
        doctorVisit.setDoctorReplyDoctorNum(wechatReplyDoctorNum);
        doctorVisit.setDoctorReplyDoctorCount(wechatReplyDoctorCount);
        doctorVisit.setHasWechatDoctorNum(hasWechatDoctorNum);
        doctorVisit.setAddWechatDoctorNum(addWechatDoctorNum);
        doctorVisit.setHasAeDoctorNum(hasAeDoctorNum);
        doctorVisit.setHasDemandDoctorNum(hasDemandDoctorNum);
        doctorVisit.setQuitDoctorNum(quitDoctorNum);

        return doctorVisit;

    }


    /**
     * 导出的Excel数据
     * @param request
     * @return
     */
    private LinkedHashMap<String, Object> getExportDailyReportData(DailyReportRequest request){


        // 招募医生数
        Integer recruitDoctorNum = dailyReportMapper.recruitDoctorNum(request);

        // 有收益的覆盖医生数
        Integer activeCoverDoctorNum = dailyReportMapper.activeCoverDoctorNum(request);

        // 多渠道覆盖医生数
        Integer mulChannelDoctorNum = dailyReportMapper.mulChannelDoctorNum(request);


        Integer callCount = 0;
        Integer connectCallCount = 0;
        Integer unConnectCallCount = 0;
        String totalCallTime = "";
        CallVisitStatisticsResponse callVisitStatistics = this.getCallVisitStatistics(request);
        if (callVisitStatistics != null){
            callCount = callVisitStatistics.getCallCount();
            connectCallCount = callVisitStatistics.getConnectCallCount();
            unConnectCallCount = callVisitStatistics.getUnConnectCallCount();
            Long totalCallSecond = callVisitStatistics.getTotalCallSecond();
            totalCallTime = commonService.alterCallTimeContent(totalCallSecond);
        }

        Integer wechatVisit = 0;
        Integer smsVisit = 0;
        Integer emailVisit = 0;
        Integer interviewVisit = 0;
        List<VisitChannelDoctorNumResponse> visitChannelDoctorNumList = dailyReportMapper.getVisitChannelDoctorNumList(request);
        if (CollectionsUtil.isNotEmptyList(visitChannelDoctorNumList)){
            Optional<VisitChannelDoctorNumResponse> wechatFirst = visitChannelDoctorNumList.stream().filter(v -> (VisitChannelEnum.WECHAT.getVisitChannel().equals(v.getVisitChannel()))).findFirst();
            if (wechatFirst.isPresent()){
                VisitChannelDoctorNumResponse visitChannelDoctorNumResponse = wechatFirst.get();
                wechatVisit = visitChannelDoctorNumResponse.getDoctorNum();
            }


            Optional<VisitChannelDoctorNumResponse> smsFirst = visitChannelDoctorNumList.stream().filter(v -> (VisitChannelEnum.MESSAGE.getVisitChannel().equals(v.getVisitChannel()))).findFirst();
            if (smsFirst.isPresent()){
                VisitChannelDoctorNumResponse visitChannelDoctorNumResponse = smsFirst.get();
                smsVisit = visitChannelDoctorNumResponse.getDoctorNum();
            }


            Optional<VisitChannelDoctorNumResponse> emailFirst = visitChannelDoctorNumList.stream().filter(v -> (VisitChannelEnum.EMAIL.getVisitChannel().equals(v.getVisitChannel()))).findFirst();
            if (emailFirst.isPresent()){
                VisitChannelDoctorNumResponse visitChannelDoctorNumResponse = emailFirst.get();
                emailVisit = visitChannelDoctorNumResponse.getDoctorNum();
            }


            Optional<VisitChannelDoctorNumResponse> interviewFirst = visitChannelDoctorNumList.stream().filter(v -> (VisitChannelEnum.INTERVIEW.getVisitChannel().equals(v.getVisitChannel()))).findFirst();
            if (interviewFirst.isPresent()){
                VisitChannelDoctorNumResponse visitChannelDoctorNumResponse = interviewFirst.get();
                interviewVisit = visitChannelDoctorNumResponse.getDoctorNum();
            }


        }

        Integer contactHospital = 0;
        Integer successHospital = 0;
        Integer coverHospital = 0;

        Integer contactDoctor = 0;
        Integer successDoctor = 0;
        Integer coverDoctor = 0;
        List<VisitTypeHospitalNumStatisticsResponse> visitTypeHospitalNum = this.getVisitTypeHospitalNum(request);
        if (CollectionsUtil.isNotEmptyList(visitTypeHospitalNum)){
            Optional<VisitTypeHospitalNumStatisticsResponse> contactFirst = visitTypeHospitalNum.stream().filter(v -> (VisitResultTypeEnum.CONTACT.getType().equals(v.getVisitType()))).findFirst();
            if (contactFirst.isPresent()){
                VisitTypeHospitalNumStatisticsResponse visitTypeHospitalNumStatisticsResponse = contactFirst.get();
                contactHospital = visitTypeHospitalNumStatisticsResponse.getHospitalNum();
            }


            Optional<VisitTypeHospitalNumStatisticsResponse> successFirst = visitTypeHospitalNum.stream().filter(v -> (VisitResultTypeEnum.SUCCESS.getType().equals(v.getVisitType()))).findFirst();
            if (successFirst.isPresent()){
                VisitTypeHospitalNumStatisticsResponse visitTypeHospitalNumStatisticsResponse = successFirst.get();
                successHospital = visitTypeHospitalNumStatisticsResponse.getHospitalNum();
            }

            Optional<VisitTypeHospitalNumStatisticsResponse> coverFirst = visitTypeHospitalNum.stream().filter(v -> (VisitResultTypeEnum.COVER.getType().equals(v.getVisitType()))).findFirst();
            if (coverFirst.isPresent()){
                VisitTypeHospitalNumStatisticsResponse visitTypeHospitalNumStatisticsResponse = coverFirst.get();
                coverHospital = visitTypeHospitalNumStatisticsResponse.getHospitalNum();
            }

        }


        List<VisitTypeDoctorNumStatisticsResponse> visitTypeDoctorNum = this.getVisitTypeDoctorNum(request);
        if (CollectionsUtil.isNotEmptyList(visitTypeHospitalNum)){
            Optional<VisitTypeDoctorNumStatisticsResponse> contactFirst = visitTypeDoctorNum.stream().filter(v -> (VisitResultTypeEnum.CONTACT.getType().equals(v.getVisitType()))).findFirst();
            if (contactFirst.isPresent()){
                VisitTypeDoctorNumStatisticsResponse visitTypeHospitalNumStatisticsResponse = contactFirst.get();
                contactDoctor = visitTypeHospitalNumStatisticsResponse.getDoctorNum();
            }


            Optional<VisitTypeDoctorNumStatisticsResponse> successFirst = visitTypeDoctorNum.stream().filter(v -> (VisitResultTypeEnum.SUCCESS.getType().equals(v.getVisitType()))).findFirst();
            if (successFirst.isPresent()){
                VisitTypeDoctorNumStatisticsResponse visitTypeHospitalNumStatisticsResponse = successFirst.get();
                contactDoctor = visitTypeHospitalNumStatisticsResponse.getDoctorNum();
            }

            Optional<VisitTypeDoctorNumStatisticsResponse> coverFirst = visitTypeDoctorNum.stream().filter(v -> (VisitResultTypeEnum.COVER.getType().equals(v.getVisitType()))).findFirst();
            if (coverFirst.isPresent()){
                VisitTypeDoctorNumStatisticsResponse visitTypeHospitalNumStatisticsResponse = coverFirst.get();
                contactDoctor = visitTypeHospitalNumStatisticsResponse.getDoctorNum();
            }

        }



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
        if (noRecruitHospitalNum < 0){
            noRecruitHospitalNum = 0;
        }

        // 医院招募率
        String recruitHospitalRate = CalculateUtil.getPercentage(recruitHospitalNum, targetHospital, 2);

        List<VisitResultHospitalNumStatisticsResponse> visitResultHospitalNumList = dailyReportMapper.getVisitResultHospitalNumList(request);




        LinkedHashMap<String, Object> dataMap = new LinkedHashMap<>();

        dataMap.put("recruitDoctorNum", recruitDoctorNum);
        dataMap.put("activeCoverDoctorNum", activeCoverDoctorNum);
        dataMap.put("mulChannelDoctorNum", mulChannelDoctorNum);

        dataMap.put("callCount", callCount);
        dataMap.put("connectCallCount", connectCallCount);
        dataMap.put("unConnectCallCount", unConnectCallCount);
        dataMap.put("callTime", totalCallTime);
        dataMap.put("wechatVisit", wechatVisit);
        dataMap.put("smsVisit", smsVisit);
        dataMap.put("emailVisit", emailVisit);
        dataMap.put("interviewVisit", interviewVisit);


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

        dataMap.put("contactDoctor", contactDoctor);
        dataMap.put("successDoctor", successDoctor);
        dataMap.put("coverDoctor", coverDoctor);

        dataMap.put("wechatReplyDoctorNum", wechatReplyDoctorNum);
        dataMap.put("wechatReplyDoctorCount", wechatReplyDoctorCount);
        dataMap.put("hasWechatDoctorNum", hasWechatDoctorNum);
        dataMap.put("addWechatDoctorNum", addWechatDoctorNum);
        dataMap.put("hasDemandDoctorNum", hasDemandDoctorNum);
        dataMap.put("hasAeDoctorNum", hasAeDoctorNum);
        dataMap.put("quitDoctorNum", quitDoctorNum);

        dataMap.put("targetHospital", targetHospital);
        dataMap.put("recruitHospital", recruitHospitalNum);
        dataMap.put("noRecruitHospital", noRecruitHospitalNum);
        dataMap.put("recruitHospitalRate", recruitHospitalRate);

        if (CollectionsUtil.isNotEmptyList(visitResultHospitalNumList)){
            visitResultHospitalNumList.forEach(h->{
                Long id = h.getId();
                Integer hospitalNum = h.getHospitalNum();
                dataMap.put("h_" + id, hospitalNum);
            });
        }

        dataMap.put("contactHospital", contactHospital);
        dataMap.put("successHospital", successHospital);
        dataMap.put("coverHospital", coverHospital);


        return dataMap;

    }



    /**
     * 导出的Excel 表头
     * @param productId
     * @return
     */
    private Map<String, String> getExportDailyReportTitle(Long productId) {

        Map<String, String> titleMap = new LinkedHashMap<>();
        titleMap.put("drugUserId", "代表ID");
        titleMap.put("drugUserName", "代表姓名");

        titleMap.put("recruitDoctorNum", "招募医生数");
        titleMap.put("activeCoverDoctorNum", "活跃覆盖医生数");
        titleMap.put("mulChannelDoctorNum", "多渠道覆盖医生数");

        titleMap.put("callCount", "外呼次数");
        titleMap.put("connectCallCount", "外呼接通次数");
        titleMap.put("unConnectCallCount", "外呼未接通次数");
        titleMap.put("callTime", "通话时长");
        titleMap.put("wechatVisit", "微信拜访");
        titleMap.put("smsVisit", "短信拜访");
        titleMap.put("emailVisit", "邮件拜访");
        titleMap.put("interviewVisit", "面谈拜访");


        List<ProductVisitResultResponse> recruitDoctorVisitResultList = virtualProductVisitResultMapper.selectVisitResultList(productId);
        if (CollectionsUtil.isNotEmptyList(recruitDoctorVisitResultList)){
            recruitDoctorVisitResultList.forEach(r->{
                String visitResult = r.getVisitResult();
                Long id = r.getId();
                titleMap.put("d_" + id, "拜访结果_".concat(visitResult).concat("_招募医生数"));
            });
        }


        titleMap.put("targetDoctor", "目标招募医生数");
        titleMap.put("noRecruitDoctorNum", "未招募医生数");
        titleMap.put("recruitDoctorRate", "医生招募率");

        titleMap.put("contactDoctor", "拜访结果类型_接触医生");
        titleMap.put("successDoctor", "拜访结果类型_成功医生");
        titleMap.put("coverDoctor", "拜访结果类型_覆盖医生");

        titleMap.put("wechatReplyDoctorNum", "微信回复人数");
        titleMap.put("wechatReplyDoctorCount", "微信回复人次");
        titleMap.put("hasWechatDoctorNum", "有微信医生人数");
        titleMap.put("addWechatDoctorNum", "添加微信医生人数");
        titleMap.put("hasDemandDoctorNum", "有需求医生数");
        titleMap.put("hasAeDoctorNum", "有AE医生数");
        titleMap.put("quitDoctorNum", "退出项目医生数");

        titleMap.put("targetHospital", "目标招募医院数");
        titleMap.put("recruitHospital", "招募医院数");
        titleMap.put("noRecruitHospital", "未招募医院数");
        titleMap.put("recruitHospitalRate", "医院招募率");

        if (CollectionsUtil.isNotEmptyList(recruitDoctorVisitResultList)){
            recruitDoctorVisitResultList.forEach(r->{
                String visitResult = r.getVisitResult();
                Long id = r.getId();
                titleMap.put("h_" + id, "拜访结果_".concat(visitResult).concat("_招募医院数"));
            });
        }

        titleMap.put("contactHospital", "拜访结果类型_接触医院");
        titleMap.put("successHospital", "拜访结果类型_成功医院");
        titleMap.put("coverHospital", "拜访结果类型_覆盖医院");

        return titleMap;

    }


    /**
     * 日报明细下载数据
     * @param request
     * @return
     */
    private List<LinkedHashMap<String, Object>> getDailyReportDetailData(DailyReportRequest request) {

        List<ExportDetailResponse> list = this.getExportDetailList(request);
        List<LinkedHashMap<String, Object>> data = this.getDailyReportDetailData(list);

        return data;
    }

    /**
     * 将对应实体转换成能下载的Map格式
     * @param list
     * @return
     */
    private List<LinkedHashMap<String, Object>> getDailyReportDetailData(List<ExportDetailResponse> list) {
        if (CollectionsUtil.isEmptyList(list)){
            return null;
        }

        List<LinkedHashMap<String, Object>> detailList = new ArrayList<>();
        list.forEach(e->{
            String visitDate = e.getVisitDate();
            Integer visitHospital = e.getVisitHospital();
            Integer contactHospital = e.getContactHospital();
            Integer successHospital = e.getSuccessHospital();
            Integer coverHospital = e.getCoverHospital();
            Integer contactDoctor = e.getContactDoctor();
            Integer successDoctor = e.getSuccessDoctor();
            Integer coverDoctor = e.getCoverDoctor();
            Integer visitDoctorNum = e.getVisitDoctorNum();
            Integer callConnectDoctorNum = e.getCallConnectDoctorNum();
            Integer wechatReplyDoctorNum = e.getWechatReplyDoctorNum();
            Integer interviewDoctorNum = e.getInterviewDoctorNum();
            Integer attendMeetingDoctorNum = e.getAttendMeetingDoctorNum();
            Integer readDoctorNum = e.getReadDoctorNum();

            LinkedHashMap<String, Object> dataMap = new LinkedHashMap<>();

            dataMap.put("visitDate", visitDate);
            dataMap.put("visitHospital", visitHospital);
            dataMap.put("contactHospital", contactHospital);
            dataMap.put("successHospital", successHospital);
            dataMap.put("coverHospital", coverHospital);
            dataMap.put("contactDoctor", contactDoctor);
            dataMap.put("successDoctor", successDoctor);
            dataMap.put("coverDoctor", coverDoctor);
            dataMap.put("visitDoctorNum", visitDoctorNum);
            dataMap.put("callConnectDoctorNum", callConnectDoctorNum);
            dataMap.put("wechatReplyDoctorNum", wechatReplyDoctorNum);
            dataMap.put("interviewDoctorNum", interviewDoctorNum);
            dataMap.put("attendMeetingDoctorNum", attendMeetingDoctorNum);
            dataMap.put("readDoctorNum", readDoctorNum);
            detailList.add(dataMap);

        });

        return detailList;

    }

    /**
     * 得到日报明细导出的数据，实体对象的格式
     * @param request
     * @return
     */
    private List<ExportDetailResponse> getExportDetailList(DailyReportRequest request) {
        Date startTime = request.getStartTime();
        Date endTime = request.getEndTime();
        List<String> days = DateUtil.getDays(DateUtil.getDateString(startTime), DateUtil.getDateString(endTime));
        if (CollectionsUtil.isEmptyList(days)){
            throw new BusinessException(ErrorEnum.ERROR, "请选择日期！");
        }

        List<ExportDetailResponse> list = new ArrayList<>();
        days.forEach(d->{
            ExportDetailResponse detail = new ExportDetailResponse();
            detail.setVisitDate(d);
            list.add(detail);
        });


        List<VisitHospitalStatisticsResponse> visitHospitalList = dailyReportMapper.visitDateHospitalNum(request);
        List<VisitHospitalStatisticsResponse> contactHospitalList = dailyReportMapper.visitDateVisitResultHospitalNum(request, VisitResultTypeEnum.CONTACT.getType());
        List<VisitHospitalStatisticsResponse> successHospitalList = dailyReportMapper.visitDateVisitResultHospitalNum(request, VisitResultTypeEnum.SUCCESS.getType());
        List<VisitHospitalStatisticsResponse> coverHospitalList = dailyReportMapper.visitDateVisitResultHospitalNum(request, VisitResultTypeEnum.COVER.getType());
        List<VisitDoctorStatisticsResponse> visitDoctorList = dailyReportMapper.visitDateDoctorNum(request);
        List<VisitDoctorStatisticsResponse> contactDoctorList = dailyReportMapper.visitDateVisitResultDoctorNum(request, VisitResultTypeEnum.CONTACT.getType());
        List<VisitDoctorStatisticsResponse> successDoctorList = dailyReportMapper.visitDateVisitResultDoctorNum(request, VisitResultTypeEnum.SUCCESS.getType());
        List<VisitDoctorStatisticsResponse> coverDoctorList = dailyReportMapper.visitDateVisitResultDoctorNum(request, VisitResultTypeEnum.COVER.getType());

        List<VisitDoctorStatisticsResponse> connectDoctorList = dailyReportMapper.visitDateCallConnectDoctorNum(request);
        List<VisitDoctorStatisticsResponse> wechatReplyDoctorList = dailyReportMapper.getVisitDateWechatReplyDoctorNum(request);

        List<VisitDoctorStatisticsResponse> interviewDoctorList = dailyReportMapper.visitDateCallDoctorNum(request, VisitChannelEnum.INTERVIEW.getVisitChannel());

        List<VisitDoctorStatisticsResponse> attendMeetingDoctorList = dailyReportMapper.visitDateAttendMeetingDoctorNum(request);

        List<VisitDoctorStatisticsResponse> readDoctorList = dailyReportMapper.visitDateReadDoctorNum(request);

        list.forEach(d->{
            String visitDate = d.getVisitDate();

            if (CollectionsUtil.isNotEmptyList(visitHospitalList)){
                Optional<VisitHospitalStatisticsResponse> first = visitHospitalList.stream().filter(v -> (visitDate.equals(v.getVisitDate()))).findFirst();
                if (first.isPresent()){
                    VisitHospitalStatisticsResponse visitHospitalStatisticsResponse = first.get();
                    d.setVisitHospital(visitHospitalStatisticsResponse.getHospitalNum());
                }
            }


            if (CollectionsUtil.isNotEmptyList(contactHospitalList)){
                Optional<VisitHospitalStatisticsResponse> first = contactHospitalList.stream().filter(v -> (visitDate.equals(v.getVisitDate()))).findFirst();
                if (first.isPresent()){
                    VisitHospitalStatisticsResponse visitHospitalStatisticsResponse = first.get();
                    d.setContactHospital(visitHospitalStatisticsResponse.getHospitalNum());
                }
            }



            if (CollectionsUtil.isNotEmptyList(successHospitalList)){
                Optional<VisitHospitalStatisticsResponse> first = successHospitalList.stream().filter(v -> (visitDate.equals(v.getVisitDate()))).findFirst();
                if (first.isPresent()){
                    VisitHospitalStatisticsResponse visitHospitalStatisticsResponse = first.get();
                    d.setSuccessHospital(visitHospitalStatisticsResponse.getHospitalNum());
                }
            }


            if (CollectionsUtil.isNotEmptyList(coverHospitalList)){
                Optional<VisitHospitalStatisticsResponse> first = coverHospitalList.stream().filter(v -> (visitDate.equals(v.getVisitDate()))).findFirst();
                if (first.isPresent()){
                    VisitHospitalStatisticsResponse visitHospitalStatisticsResponse = first.get();
                    d.setCoverHospital(visitHospitalStatisticsResponse.getHospitalNum());
                }
            }


            if (CollectionsUtil.isNotEmptyList(visitDoctorList)){
                Optional<VisitDoctorStatisticsResponse> first = visitDoctorList.stream().filter(v -> (visitDate.equals(v.getVisitDate()))).findFirst();
                if (first.isPresent()){
                    VisitDoctorStatisticsResponse visitHospitalStatisticsResponse = first.get();
                    d.setVisitDoctorNum(visitHospitalStatisticsResponse.getDoctorNum());
                }
            }


            if (CollectionsUtil.isNotEmptyList(contactDoctorList)){
                Optional<VisitDoctorStatisticsResponse> first = contactDoctorList.stream().filter(v -> (visitDate.equals(v.getVisitDate()))).findFirst();
                if (first.isPresent()){
                    VisitDoctorStatisticsResponse visitHospitalStatisticsResponse = first.get();
                    d.setContactDoctor(visitHospitalStatisticsResponse.getDoctorNum());
                }
            }


            if (CollectionsUtil.isNotEmptyList(successDoctorList)){
                Optional<VisitDoctorStatisticsResponse> first = successDoctorList.stream().filter(v -> (visitDate.equals(v.getVisitDate()))).findFirst();
                if (first.isPresent()){
                    VisitDoctorStatisticsResponse visitHospitalStatisticsResponse = first.get();
                    d.setSuccessDoctor(visitHospitalStatisticsResponse.getDoctorNum());
                }
            }


            if (CollectionsUtil.isNotEmptyList(coverDoctorList)){
                Optional<VisitDoctorStatisticsResponse> first = coverDoctorList.stream().filter(v -> (visitDate.equals(v.getVisitDate()))).findFirst();
                if (first.isPresent()){
                    VisitDoctorStatisticsResponse visitHospitalStatisticsResponse = first.get();
                    d.setCoverDoctor(visitHospitalStatisticsResponse.getDoctorNum());
                }
            }


            if (CollectionsUtil.isNotEmptyList(connectDoctorList)){
                Optional<VisitDoctorStatisticsResponse> first = connectDoctorList.stream().filter(v -> (visitDate.equals(v.getVisitDate()))).findFirst();
                if (first.isPresent()){
                    VisitDoctorStatisticsResponse visitHospitalStatisticsResponse = first.get();
                    d.setCallConnectDoctorNum(visitHospitalStatisticsResponse.getDoctorNum());
                }
            }


            if (CollectionsUtil.isNotEmptyList(wechatReplyDoctorList)){
                Optional<VisitDoctorStatisticsResponse> first = wechatReplyDoctorList.stream().filter(v -> (visitDate.equals(v.getVisitDate()))).findFirst();
                if (first.isPresent()){
                    VisitDoctorStatisticsResponse visitHospitalStatisticsResponse = first.get();
                    d.setWechatReplyDoctorNum(visitHospitalStatisticsResponse.getDoctorNum());
                }
            }


            if (CollectionsUtil.isNotEmptyList(interviewDoctorList)){
                Optional<VisitDoctorStatisticsResponse> first = interviewDoctorList.stream().filter(v -> (visitDate.equals(v.getVisitDate()))).findFirst();
                if (first.isPresent()){
                    VisitDoctorStatisticsResponse visitHospitalStatisticsResponse = first.get();
                    d.setInterviewDoctorNum(visitHospitalStatisticsResponse.getDoctorNum());
                }
            }


            if (CollectionsUtil.isNotEmptyList(attendMeetingDoctorList)){
                Optional<VisitDoctorStatisticsResponse> first = attendMeetingDoctorList.stream().filter(v -> (visitDate.equals(v.getVisitDate()))).findFirst();
                if (first.isPresent()){
                    VisitDoctorStatisticsResponse visitHospitalStatisticsResponse = first.get();
                    d.setAttendMeetingDoctorNum(visitHospitalStatisticsResponse.getDoctorNum());
                }
            }



            if (CollectionsUtil.isNotEmptyList(readDoctorList)){
                Optional<VisitDoctorStatisticsResponse> first = readDoctorList.stream().filter(v -> (visitDate.equals(v.getVisitDate()))).findFirst();
                if (first.isPresent()){
                    VisitDoctorStatisticsResponse visitHospitalStatisticsResponse = first.get();
                    d.setReadDoctorNum(visitHospitalStatisticsResponse.getDoctorNum());
                }
            }

        });


        return list;

    }

    /**
     * 日报明细下载表头
     * @return
     */
    private Map<String, String> getDailyReportDetailTitle() {
        Map<String, String> titleMap = new LinkedHashMap<>();
        titleMap.put("visitDate", "日期");
        titleMap.put("visitHospital", "拜访医院数");
        titleMap.put("contactHospital", "接触医院数");
        titleMap.put("successHospital", "成功医院数");
        titleMap.put("coverHospital", "覆盖医院数");
        titleMap.put("contactDoctor", "接触医生数");
        titleMap.put("successDoctor", "成功医生数");
        titleMap.put("coverDoctor", "覆盖医生数");
        titleMap.put("visitDoctorNum", "拜访医生数");
        titleMap.put("callConnectDoctorNum", "电话接通医生数");
        titleMap.put("wechatReplyDoctorNum", "微信回复医生数");
        titleMap.put("interviewDoctorNum", "面谈医生数");
        titleMap.put("attendMeetingDoctorNum", "参会医生数");
        titleMap.put("readDoctorNum", "阅读医生数");

        return titleMap;
    }



    /**
     * 日报招募汇总下载数据
     * @param reportRequest
     * @param productId
     * @return
     */
    private List<LinkedHashMap<String, Object>> getDailyReportRecruitData(DailyReportRequest reportRequest, Long productId) {


        Integer targetHospital = 0;
        Integer targetDoctor = 0;
        ProductTargetResponseBean productTarget = productTargetMapper.getProductTarget(productId);
        if (productTarget != null){
            targetHospital = productTarget.getTargetHospital();
            if (targetHospital == null){
                targetHospital = 0;
            }

            targetDoctor = productTarget.getTargetDoctor();
            if (targetDoctor == null){
                targetDoctor = 0;
            }

        }

        Integer recruitHospitalNum = dailyReportMapper.recruitHospitalNum(reportRequest);
        Integer recruitDoctorNum = dailyReportMapper.recruitDoctorNum(reportRequest);

        String recruitHospitalRate = CalculateUtil.getPercentage(recruitHospitalNum, targetHospital, 2);
        String recruitDoctorRate = CalculateUtil.getPercentage(recruitDoctorNum, targetDoctor, 2);

        LinkedHashMap<String, Object> dataMap = new LinkedHashMap<>();
        dataMap.put("targetHospital", targetHospital);
        dataMap.put("recruitHospital", recruitHospitalNum);
        dataMap.put("recruitHospitalRate", recruitHospitalRate);

        dataMap.put("targetDoctor", targetDoctor);
        dataMap.put("recruitDoctor", recruitDoctorNum);
        dataMap.put("recruitDoctorRate", recruitDoctorRate);


        List<LinkedHashMap<String, Object>> list = new ArrayList<>();
        list.add(dataMap);


        return list;

    }


    /**
     * 日报招募汇总下载表头
     * @return
     */
    private Map<String, String> getDailyReportRecruitTitle() {
        Map<String, String> titleMap = new LinkedHashMap<>();
        titleMap.put("targetHospital", "目标医院数");
        titleMap.put("recruitHospital", "招募医院");
        titleMap.put("recruitHospitalRate", "医院招募率");

        titleMap.put("targetDoctor", "目标医生数");
        titleMap.put("recruitDoctor", "招募医生数");
        titleMap.put("recruitDoctorRate", "医生招募率");

        return titleMap;
    }

    /**
     * 日报覆盖汇总下载数据
     * @return
     */
    private List<LinkedHashMap<String, Object>> getDailyReportCoverData(DailyReportRequest reportRequest) {

        Integer recruitHospitalNum = dailyReportMapper.recruitHospitalNum(reportRequest);
        Integer recruitDoctorNum = dailyReportMapper.recruitDoctorNum(reportRequest);

        Integer coverHospitalNum = dailyReportMapper.coverHospitalNum(reportRequest);
        Integer coverDoctorNum = dailyReportMapper.coverDoctorNum(reportRequest);

        String coverHospitalRate = CalculateUtil.getPercentage(coverHospitalNum, recruitHospitalNum, 2);
        String coverDoctorRate = CalculateUtil.getPercentage(coverDoctorNum, recruitDoctorNum, 2);


        LinkedHashMap<String, Object> dataMap = new LinkedHashMap<>();
        dataMap.put("recruitHospital", recruitHospitalNum);
        dataMap.put("coverHospital", coverHospitalNum);
        dataMap.put("coverHospitalRate", coverHospitalRate);

        dataMap.put("recruitDoctor", recruitDoctorNum);
        dataMap.put("coverDoctor", coverDoctorNum);
        dataMap.put("coverDoctorRate", coverDoctorRate);

        List<LinkedHashMap<String, Object>> list = new ArrayList<>();
        list.add(dataMap);

        return list;
    }

    /**
     * 日报覆盖汇总下载表头
     * @return
     */
    private Map<String, String> getDailyReportCoverTitle() {
        Map<String, String> titleMap = new LinkedHashMap<>();
        titleMap.put("recruitHospital", "招募医院数");
        titleMap.put("coverHospital", "覆盖医院数");
        titleMap.put("coverHospitalRate", "医院覆盖率");

        titleMap.put("recruitDoctor", "招募医生数");
        titleMap.put("coverDoctor", "覆盖医生数");
        titleMap.put("coverDoctorRate", "医生覆盖率");

        return titleMap;
    }

}
