package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.mybatis.MonthlyRecruitMapper;
import com.nuoxin.virtual.rep.api.mybatis.ProductTargetMapper;
import com.nuoxin.virtual.rep.api.service.v3_0.MonthlyRecruitService;
import com.nuoxin.virtual.rep.api.utils.*;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.MonthlyRecruitRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.statistics.ProductTargetResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 月报招募相关
 * @author tiancun
 * @date 2019-06-12
 */
@Service
public class MonthlyRecruitServiceImpl implements MonthlyRecruitService {

    @Resource
    private ProductTargetMapper productTargetMapper;

    @Resource
    private MonthlyRecruitMapper monthlyRecruitMapper;

    @Override
    public MonthlyHospitalRecruitResponse getMonthlyHospitalRecruit(MonthlyRecruitRequest request) {
        this.checkDateParam(request);


        Long productId = request.getProductId();
        ProductTargetResponseBean productTarget = productTargetMapper.getProductTarget(productId);
        Integer targetHospital = 0;
        if (productTarget != null){
            targetHospital = productTarget.getTargetHospital();
        }


        Integer contactHospital = monthlyRecruitMapper.getContactHospital(request);
        Integer touchHospital = monthlyRecruitMapper.getTouchHospital(request);
        Integer recruitHospital = monthlyRecruitMapper.getRecruitHospital(request);

        String contactHospitalRate = CalculateUtil.getPercentage(contactHospital, targetHospital, 2);
        String touchHospitalRate = CalculateUtil.getPercentage(touchHospital, contactHospital, 2);
        String recruitHospitalRate = CalculateUtil.getPercentage(recruitHospital, touchHospital, 2);

        MonthlyHospitalRecruitResponse monthlyHospitalRecruit = new MonthlyHospitalRecruitResponse();
        monthlyHospitalRecruit.setTargetHospital(targetHospital);
        monthlyHospitalRecruit.setContactHospital(contactHospital);
        monthlyHospitalRecruit.setTouchHospital(touchHospital);
        monthlyHospitalRecruit.setRecruitHospital(recruitHospital);

        monthlyHospitalRecruit.setContactHospitalRate(contactHospitalRate);
        monthlyHospitalRecruit.setTouchHospitalRate(touchHospitalRate);
        monthlyHospitalRecruit.setRecruitHospitalRate(recruitHospitalRate);

        return monthlyHospitalRecruit;
    }



    @Override
    public MonthlyDoctorRecruitResponse getMonthlyDoctorRecruit(MonthlyRecruitRequest request) {
        this.checkDateParam(request);
        Long productId = request.getProductId();
        ProductTargetResponseBean productTarget = productTargetMapper.getProductTarget(productId);
        Integer targetDoctor = 0;
        if (productTarget != null){
            targetDoctor = productTarget.getTargetDoctor();
        }

        Integer contactDoctor = monthlyRecruitMapper.getContactDoctor(request);
        Integer touchDoctor = monthlyRecruitMapper.getTouchDoctor(request);
        Integer recruitDoctor = monthlyRecruitMapper.getRecruitDoctor(request);

        String contactDoctorRate = CalculateUtil.getPercentage(contactDoctor, targetDoctor, 2);
        String touchDoctorRate = CalculateUtil.getPercentage(touchDoctor, contactDoctor, 2);
        String recruitDoctorRate = CalculateUtil.getPercentage(recruitDoctor, touchDoctor, 2);

        MonthlyDoctorRecruitResponse monthlyDoctorRecruit = new MonthlyDoctorRecruitResponse();
        monthlyDoctorRecruit.setTargetDoctor(targetDoctor);
        monthlyDoctorRecruit.setContactDoctor(contactDoctor);
        monthlyDoctorRecruit.setTouchDoctor(touchDoctor);
        monthlyDoctorRecruit.setRecruitDoctor(recruitDoctor);

        monthlyDoctorRecruit.setContactDoctorRate(contactDoctorRate);
        monthlyDoctorRecruit.setTouchDoctorRate(touchDoctorRate);
        monthlyDoctorRecruit.setRecruitDoctorRate(recruitDoctorRate);

        return monthlyDoctorRecruit;


    }

    @Override
    public void exportMonthlyRecruit(MonthlyRecruitRequest request, HttpServletResponse response) {
        this.checkDateParam(request);
        ExportExcelWrapper<MonthlyRecruitExportResponse> exportExcelWrapper = new ExportExcelWrapper();
        exportExcelWrapper.exportExcel("月报—招募转化漏斗".concat(request.getStartDate()).concat("~").concat(request.getEndDate()), "月报—招募转化漏斗",
                new String[]{"阶段", "医院数量", "分阶段医院转化率", "医生数量", "分阶段医生转化率"},
                this.getMonthlyRecruitList(request), response, ExportExcelUtil.EXCEl_FILE_2007);
    }

    @Override
    public MonthlyRecruitContactResponse getMonthlyRecruitContact(MonthlyRecruitRequest request) {
        this.checkDateParam(request);
        Integer recruitDoctor = monthlyRecruitMapper.getRecruitDoctor(request);
        Integer hasMobileDoctor = monthlyRecruitMapper.getHasMobileDoctor(request);
        Integer hasWechatDoctor = monthlyRecruitMapper.getHasWechatDoctor(request);
        Integer addWechatDoctor = monthlyRecruitMapper.getAddWechatDoctor(request);

        String hasMobileDoctorRate = CalculateUtil.getPercentage(hasMobileDoctor, recruitDoctor, 2);
        String hasWechatDoctorRate = CalculateUtil.getPercentage(hasWechatDoctor, recruitDoctor, 2);
        String addWechatDoctorRate = CalculateUtil.getPercentage(addWechatDoctor, hasWechatDoctor, 2);

        MonthlyRecruitContactResponse monthlyRecruitContact = new MonthlyRecruitContactResponse();
        monthlyRecruitContact.setRecruitDoctor(recruitDoctor);
        monthlyRecruitContact.setHasMobileDoctor(hasMobileDoctor);
        monthlyRecruitContact.setHasMobileDoctorRate(hasMobileDoctorRate);
        monthlyRecruitContact.setHasWechatDoctor(hasWechatDoctor);
        monthlyRecruitContact.setHasWechatDoctorRate(hasWechatDoctorRate);
        monthlyRecruitContact.setAddWechatDoctor(addWechatDoctor);
        monthlyRecruitContact.setAddWechatDoctorRate(addWechatDoctorRate);

        return monthlyRecruitContact;
    }



    @Override
    public void exportMonthlyRecruitContact(MonthlyRecruitRequest request, HttpServletResponse response) {
        List<MonthlyRecruitContactDetailResponse> list = this.getMonthlyRecruitContactDetailList(request);

        ExportExcelWrapper<MonthlyRecruitContactDetailResponse> exportExcelWrapper = new ExportExcelWrapper();
        exportExcelWrapper.exportExcel("月报—联系方式收集统计".concat(request.getStartDate()).concat("~").concat(request.getEndDate()), "月报—联系方式收集统计",
                new String[]{"月份", "成功招募医生数", "有手机号医生数", "有手机号医生数/成功招募医生数百分比", "有微信号医生数",
                        "有微信号医生数/成功招募医生数百分比", "已添加微信医生数", "已添加微信医生数/有微信号医生数百分比"},
                this.getMonthlyRecruitContactDetailList(request), response, ExportExcelUtil.EXCEl_FILE_2007);
    }

    /**
     * 得到月报
     * @param request
     * @return
     */
    private List<MonthlyRecruitContactDetailResponse> getMonthlyRecruitContactDetailList(MonthlyRecruitRequest request) {
        this.checkDateParam(request);
        List<MonthlyRecruitDetailResponse> monthRecruitDoctorList = monthlyRecruitMapper.getMonthRecruitDoctorList(request);
        List<MonthlyRecruitMobileDetailResponse> monthHasMobileDoctorList = monthlyRecruitMapper.getMonthHasMobileDoctorList(request);
        List<MonthlyRecruitWechatDetailResponse> monthHasWechatDoctorList = monthlyRecruitMapper.getMonthHasWechatDoctorList(request);
        List<MonthlyRecruitAddWechatDetailResponse> monthAddWechatDoctorList = monthlyRecruitMapper.getMonthAddWechatDoctorList(request);


        List<String> monthBetween = DateUtil.getMonthBetween(request.getStartDate(), request.getEndDate());
        if (CollectionsUtil.isEmptyList(monthBetween)){
            throw new BusinessException(ErrorEnum.ERROR, "日期输入不合法");
        }

        List<MonthlyRecruitContactDetailResponse> list = new ArrayList<>();
        for (String month : monthBetween) {
            MonthlyRecruitContactDetailResponse monthlyRecruitContactDetail = new MonthlyRecruitContactDetailResponse();
            Integer monthRecruitDoctor = 0;
            Integer hasMobileDoctor = 0;
            Integer hasWechatDoctor = 0;
            Integer addWechatDoctor = 0;

            if (CollectionsUtil.isNotEmptyList(monthRecruitDoctorList)){
                Optional<MonthlyRecruitDetailResponse> first = monthRecruitDoctorList.stream().filter(m -> (month.equals(m.getMonth()))).findFirst();
                if (first.isPresent()){
                    MonthlyRecruitDetailResponse monthlyRecruitDetailResponse = first.get();
                    monthRecruitDoctor = monthlyRecruitDetailResponse.getRecruitDoctor();
                }
            }

            if (CollectionsUtil.isNotEmptyList(monthHasMobileDoctorList)){
                Optional<MonthlyRecruitMobileDetailResponse> first = monthHasMobileDoctorList.stream().filter(m -> (month.equals(m.getMonth()))).findFirst();
                if (first.isPresent()){
                    MonthlyRecruitMobileDetailResponse monthlyRecruitMobileDetailResponse = first.get();
                    hasMobileDoctor = monthlyRecruitMobileDetailResponse.getHasMobileDoctor();
                }
            }

            if (CollectionsUtil.isNotEmptyList(monthHasWechatDoctorList)){
                Optional<MonthlyRecruitWechatDetailResponse> first = monthHasWechatDoctorList.stream().filter(m -> (month.equals(m.getMonth()))).findFirst();
                if (first.isPresent()){
                    MonthlyRecruitWechatDetailResponse monthlyRecruitWechatDetailResponse = first.get();
                    hasWechatDoctor = monthlyRecruitWechatDetailResponse.getHasWechatDoctor();
                }
            }

            if (CollectionsUtil.isNotEmptyList(monthAddWechatDoctorList)){
                Optional<MonthlyRecruitAddWechatDetailResponse> first = monthAddWechatDoctorList.stream().filter(m -> (month.equals(m.getMonth()))).findFirst();
                if (first.isPresent()){
                    MonthlyRecruitAddWechatDetailResponse monthlyRecruitAddWechatDetailResponse = first.get();
                    addWechatDoctor = monthlyRecruitAddWechatDetailResponse.getAddWechatDoctor();
                }
            }


            monthlyRecruitContactDetail.setMonth(month);
            monthlyRecruitContactDetail.setMonthRecruitDoctor(monthRecruitDoctor);
            monthlyRecruitContactDetail.setHasMobileDoctor(hasMobileDoctor);

            String hasMobileDoctorRate = CalculateUtil.getPercentage(hasMobileDoctor, monthRecruitDoctor, 2);
            monthlyRecruitContactDetail.setHasMobileDoctorRate(hasMobileDoctorRate);

            monthlyRecruitContactDetail.setHasWechatDoctor(hasWechatDoctor);
            String hasWechatDoctorRate = CalculateUtil.getPercentage(hasWechatDoctor, monthRecruitDoctor, 2);
            monthlyRecruitContactDetail.setHasWechatDoctorRate(hasWechatDoctorRate);

            monthlyRecruitContactDetail.setAddWechatDoctor(addWechatDoctor);
            String addWechatDoctorRate = CalculateUtil.getPercentage(addWechatDoctor, hasWechatDoctor, 2);
            monthlyRecruitContactDetail.setAddWechatDoctorRate(addWechatDoctorRate);
            list.add(monthlyRecruitContactDetail);
        }

        MonthlyRecruitContactResponse monthlyRecruitContact = this.getMonthlyRecruitContact(request);
        MonthlyRecruitContactDetailResponse monthlyRecruitContactDetailResponse = new MonthlyRecruitContactDetailResponse();
        monthlyRecruitContactDetailResponse.setMonth("总计");
        monthlyRecruitContactDetailResponse.setMonthRecruitDoctor(monthlyRecruitContact.getRecruitDoctor());
        monthlyRecruitContactDetailResponse.setHasMobileDoctor(monthlyRecruitContact.getHasMobileDoctor());
        monthlyRecruitContactDetailResponse.setHasMobileDoctorRate(monthlyRecruitContact.getHasMobileDoctorRate());
        monthlyRecruitContactDetailResponse.setHasWechatDoctor(monthlyRecruitContact.getHasWechatDoctor());
        monthlyRecruitContactDetailResponse.setHasWechatDoctorRate(monthlyRecruitContact.getHasWechatDoctorRate());
        monthlyRecruitContactDetailResponse.setAddWechatDoctor(monthlyRecruitContact.getAddWechatDoctor());
        monthlyRecruitContactDetailResponse.setAddWechatDoctorRate(monthlyRecruitContact.getAddWechatDoctorRate());

        list.add(list.size(), monthlyRecruitContactDetailResponse);
        return list;
    }

    /**
     * 得到月报招募导出的数据
     * @param request
     * @return
     */
    private List<MonthlyRecruitExportResponse> getMonthlyRecruitList(MonthlyRecruitRequest request) {

        MonthlyHospitalRecruitResponse monthlyHospitalRecruit = this.getMonthlyHospitalRecruit(request);
        MonthlyDoctorRecruitResponse monthlyDoctorRecruit = this.getMonthlyDoctorRecruit(request);

        MonthlyRecruitExportResponse monthlyTargetRecruitExport = new MonthlyRecruitExportResponse();
        monthlyTargetRecruitExport.setStage("目标");
        monthlyTargetRecruitExport.setHospitalNum(monthlyHospitalRecruit.getTargetHospital());
        monthlyTargetRecruitExport.setHospitalRate("无");
        monthlyTargetRecruitExport.setDoctorNum(monthlyDoctorRecruit.getTargetDoctor());
        monthlyTargetRecruitExport.setDoctorRate("无");


        MonthlyRecruitExportResponse monthlyContactRecruitExport = new MonthlyRecruitExportResponse();
        monthlyContactRecruitExport.setStage("联系");
        monthlyContactRecruitExport.setHospitalNum(monthlyHospitalRecruit.getContactHospital());
        monthlyContactRecruitExport.setHospitalRate(monthlyHospitalRecruit.getContactHospitalRate());
        monthlyContactRecruitExport.setDoctorNum(monthlyDoctorRecruit.getContactDoctor());
        monthlyContactRecruitExport.setDoctorRate(monthlyDoctorRecruit.getContactDoctorRate());


        MonthlyRecruitExportResponse monthlyTouchExportResponse = new MonthlyRecruitExportResponse();
        monthlyTouchExportResponse.setStage("接触");
        monthlyTouchExportResponse.setHospitalNum(monthlyHospitalRecruit.getTouchHospital());
        monthlyTouchExportResponse.setHospitalRate(monthlyHospitalRecruit.getTouchHospitalRate());
        monthlyTouchExportResponse.setDoctorNum(monthlyDoctorRecruit.getTouchDoctor());
        monthlyTouchExportResponse.setDoctorRate(monthlyDoctorRecruit.getTouchDoctorRate());

        MonthlyRecruitExportResponse monthlyRecruitExportResponse = new MonthlyRecruitExportResponse();
        monthlyRecruitExportResponse.setStage("成功招募");
        monthlyRecruitExportResponse.setHospitalNum(monthlyHospitalRecruit.getRecruitHospital());
        monthlyRecruitExportResponse.setHospitalRate(monthlyHospitalRecruit.getRecruitHospitalRate());
        monthlyRecruitExportResponse.setDoctorNum(monthlyDoctorRecruit.getRecruitDoctor());
        monthlyRecruitExportResponse.setDoctorRate(monthlyDoctorRecruit.getRecruitDoctorRate());

        List<MonthlyRecruitExportResponse> list = new ArrayList<>();
        list.add(monthlyTargetRecruitExport);
        list.add(monthlyContactRecruitExport);
        list.add(monthlyTouchExportResponse);
        list.add(monthlyRecruitExportResponse);

        return list;
    }


    /**
     * 检查日期
     * @param request
     */
    private void checkDateParam(MonthlyRecruitRequest request) {
        String startDate = request.getStartDate();
        String endDate = request.getEndDate();

        DateUtil.stringToDate(startDate, DateUtil.DATE_FORMAT_YYYY_MM);
        DateUtil.stringToDate(endDate, DateUtil.DATE_FORMAT_YYYY_MM);

    }


}
