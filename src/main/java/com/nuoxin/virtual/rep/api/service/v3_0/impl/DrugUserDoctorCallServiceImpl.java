package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.enums.VisitChannelEnum;
import com.nuoxin.virtual.rep.api.mybatis.VirtualDoctorCallInfoMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v3_0.DrugUserDoctorCallService;
import com.nuoxin.virtual.rep.api.utils.*;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DrugUserDoctorCallDetailRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DrugUserDoctorCallRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DrugUserDoctorCallDetailResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DrugUserDoctorCallResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.ExportDrugUserDoctorCallResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 电话拜访记录查询相关业务接口实现
 * @author tiancun
 * @date 2019-05-10
 */
@Service
public class DrugUserDoctorCallServiceImpl implements DrugUserDoctorCallService {

    @Resource
    private VirtualDoctorCallInfoMapper virtualDoctorCallInfoMapper;

    @Resource
    private CommonService commonService;


    @Override
    public PageResponseBean<DrugUserDoctorCallResponse> getDrugUserDoctorCallPage(DrugUserDoctorCallRequest request) {

        String searchKeyword = request.getSearchKeyword();
        if (StringUtil.isNotEmpty(searchKeyword)){
            request.setSearchKeyword(searchKeyword.trim());
        }

        Integer count = virtualDoctorCallInfoMapper.getDrugUserDoctorCallListCount(request);
        if (count == null){
            count = 0;
        }

        List<DrugUserDoctorCallResponse> drugUserDoctorCallList = null;
        if (count > 0){
            drugUserDoctorCallList = this.getDrugUserDoctorCallList(request);
        }


        return new PageResponseBean<>(request, count, drugUserDoctorCallList);

    }

    @Override
    public PageResponseBean<DrugUserDoctorCallDetailResponse> getDrugUserDoctorCallDetailPage(DrugUserDoctorCallDetailRequest request) {
        Integer count = virtualDoctorCallInfoMapper.getDrugUserDoctorCallDetailListCount(request);
        if (count == null){
            count = 0;
        }

        List<DrugUserDoctorCallDetailResponse> drugUserDoctorCallDetailList = null;
        if (count > 0){
            drugUserDoctorCallDetailList = virtualDoctorCallInfoMapper.getDrugUserDoctorCallDetailList(request);
        }


        // 转换录音时长格式
        if (CollectionsUtil.isNotEmptyList(drugUserDoctorCallDetailList)){
            drugUserDoctorCallDetailList.forEach(c->{
                Long callSecond = c.getCallSecond();
                String callTime = commonService.alterCallTimeContent(callSecond);
                c.setCallTime(callTime);
                Integer visitChannel = c.getVisitChannel();
                c.setVisitChannelStr(VisitChannelEnum.getVisitChannelStr(visitChannel));

            });
        }


        return new PageResponseBean<>(request, count, drugUserDoctorCallDetailList);
    }

    @Override
    public void exportDrugUserDoctorCallList(HttpServletResponse response, DrugUserDoctorCallRequest request) {
        request.setPaginable(1);
        List<DrugUserDoctorCallResponse> drugUserDoctorCallList = this.getDrugUserDoctorCallList(request);
        if (CollectionsUtil.isEmptyList(drugUserDoctorCallList)){
            throw new BusinessException(ErrorEnum.ERROR, "暂无数据！");
        }

        List<ExportDrugUserDoctorCallResponse> exportDrugUserDoctorCallList = new ArrayList<>();
        drugUserDoctorCallList.forEach(d->{
            ExportDrugUserDoctorCallResponse e = new ExportDrugUserDoctorCallResponse();
            BeanUtils.copyProperties(d, e);
            exportDrugUserDoctorCallList.add(e);
        });



        ExportExcelWrapper<ExportDrugUserDoctorCallResponse> exportExcelWrapper = new ExportExcelWrapper();
        exportExcelWrapper.exportExcel("电话拜访记录", "电话拜访记录",
                new String[]{"产品ID", "产品名称", "代表ID", "代表姓名", "线上或者线下", "医生ID",
                        "医生姓名", "医院ID", "医院名称", "医生科室", "覆盖状态", "招募状态",
                        "外呼数", "接通数", "接通率", "接通总时长", "最后一次拜访时间", "最后一次通话时长"},
                exportDrugUserDoctorCallList, response, ExportExcelUtil.EXCEl_FILE_2007);
    }


    /**
     * 查询列表
     * @param request
     * @return
     */
    private List<DrugUserDoctorCallResponse> getDrugUserDoctorCallList(DrugUserDoctorCallRequest request){
        List<DrugUserDoctorCallResponse> drugUserDoctorCallList = virtualDoctorCallInfoMapper.getDrugUserDoctorCallList(request);

        if (CollectionsUtil.isNotEmptyList(drugUserDoctorCallList)){
            drugUserDoctorCallList.forEach(c->{
                Long totalCallSecond = c.getTotalCallSecond();
                Long lastCallSecond = c.getLastCallSecond();
                Date lastVisitDate = c.getLastVisitDate();
                Integer connectCallCount = c.getConnectCallCount();
                Integer callCount = c.getCallCount();
                String lastVisitTime = "";
                String connectRate = "";
                String totalCallTime = commonService.alterCallTimeContent(totalCallSecond);
                String lastCallTime = commonService.alterCallTimeContent(lastCallSecond);
                if (lastVisitDate !=null){
                    long visitTimeDelta = System.currentTimeMillis() - lastVisitDate.getTime();
                    lastVisitTime = commonService.alterLastVisitTimeContent(visitTimeDelta);
                }

                c.setTotalCallTime(totalCallTime);
                c.setLastCallTime(lastCallTime);
                c.setLastVisitTime(lastVisitTime);
                connectRate = CalculateUtil.getPercentage(connectCallCount, callCount, 2);

                c.setConnectRate(connectRate);

            });
        }

        return drugUserDoctorCallList;

    }


}
