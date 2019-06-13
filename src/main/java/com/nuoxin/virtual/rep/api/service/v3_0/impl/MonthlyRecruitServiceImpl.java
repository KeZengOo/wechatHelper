package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.mybatis.MonthlyRecruitMapper;
import com.nuoxin.virtual.rep.api.mybatis.ProductTargetMapper;
import com.nuoxin.virtual.rep.api.service.v3_0.MonthlyRecruitService;
import com.nuoxin.virtual.rep.api.utils.CalculateUtil;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.utils.ExportExcelUtil;
import com.nuoxin.virtual.rep.api.utils.ExportExcelWrapper;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.MonthlyRecruitRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.statistics.ProductTargetResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly.MonthlyDoctorRecruitResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly.MonthlyHospitalRecruitResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly.MonthlyRecruitExportResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
