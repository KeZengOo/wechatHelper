package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.mybatis.DoctorMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v3_0.CommonPoolService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.CommonPoolRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.CommonPoolDoctorResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DoctorTelephoneResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DoctorVisitResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tiancun
 * @date 2019-05-07
 */
@Service
public class CommonPoolServiceImpl implements CommonPoolService {

    @Resource
    private DoctorMapper doctorMapper;

    @Resource
    private CommonService commonService;

    @Override
    public PageResponseBean<CommonPoolDoctorResponse> getDoctorPage(CommonPoolRequest request) {

        Integer count = doctorMapper.getCommonPoolDoctorListCount(request);
        if (count == null){
            count = 0;
        }
        List<CommonPoolDoctorResponse> commonPoolDoctorList ;
        if (count == 0) {
            commonPoolDoctorList = new ArrayList<>();
            return new PageResponseBean<>(request, count, commonPoolDoctorList);
        }

        commonPoolDoctorList = doctorMapper.getCommonPoolDoctorList(request);
        if (CollectionsUtil.isEmptyList(commonPoolDoctorList)) {
            commonPoolDoctorList = new ArrayList<>();
            return new PageResponseBean<>(request, count, commonPoolDoctorList);

        }

        // 填充医生的手机号
        List<Long> doctorIdList = commonPoolDoctorList.stream().map(CommonPoolDoctorResponse::getDoctorId).distinct().collect(Collectors.toList());
        if (CollectionsUtil.isNotEmptyList(doctorIdList)){
            List<DoctorTelephoneResponse> doctorTelephoneList = doctorMapper.getDoctorTelephoneList(doctorIdList);
            if (CollectionsUtil.isNotEmptyList(doctorTelephoneList)){
                Map<Long, List<DoctorTelephoneResponse>> doctorTelephoneMap = doctorTelephoneList.stream().collect(Collectors.groupingBy(DoctorTelephoneResponse::getDoctorId));
                commonPoolDoctorList.forEach(m -> {
                    List<DoctorTelephoneResponse> doctorTelephones = doctorTelephoneMap.get(m.getDoctorId());
                    // 去掉无效的手机号
                    List<String> telephoneList = commonService.filterInvalidTelephones(doctorTelephones);
                    if (CollectionsUtil.isNotEmptyList(telephoneList)){
                        m.setTelephoneList(telephoneList);
                    }
                });
            }
        }


        // 填充医生的拜访数

        List<Long> visitIdList = commonPoolDoctorList.stream().map(CommonPoolDoctorResponse::getMaxVisitId).distinct().collect(Collectors.toList());
        if (CollectionsUtil.isEmptyList(visitIdList)) {
            return new PageResponseBean<>(request, count, commonPoolDoctorList);
        }

        List<DoctorVisitResponse> doctorVisitList = doctorMapper.getDoctorVisitList(visitIdList);
        if (CollectionsUtil.isEmptyList(doctorVisitList)){
            return new PageResponseBean<>(request, count, commonPoolDoctorList);
        }

        commonPoolDoctorList.forEach(m -> {
            Optional<DoctorVisitResponse> first = doctorVisitList.stream().filter(dv -> (dv.getMaxVisitId().equals(m.getMaxVisitId()))).findFirst();
            if (first.isPresent()) {
                DoctorVisitResponse doctorVisitResponse = first.get();
                Date lastVisitDate = doctorVisitResponse.getLastVisitTime();
                if (lastVisitDate !=null){
                    long visitTimeDelta = System.currentTimeMillis() - lastVisitDate.getTime();
                    String lastVisitTime = commonService.alterLastVisitTimeContent(visitTimeDelta);
                    m.setLastVisitTime(lastVisitTime);
                }else{
                    m.setLastVisitTime("无");
                }

                m.setVisitDrugUserId(doctorVisitResponse.getVisitDrugUserId());
                m.setVisitDrugUserName(doctorVisitResponse.getVisitDrugUserName());
                m.setVisitResult(doctorVisitResponse.getVisitResult());
            }
        });

        return new PageResponseBean<>(request, count, commonPoolDoctorList);
    }
}
