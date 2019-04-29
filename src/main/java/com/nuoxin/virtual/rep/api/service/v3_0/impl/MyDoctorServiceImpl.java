package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.mybatis.DoctorMapper;
import com.nuoxin.virtual.rep.api.service.v3_0.MyDoctorService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.utils.RegularUtils;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.MyDoctorRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.message.MessageResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DoctorTelephoneResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DoctorVisitResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.MyDoctorResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 我的客户相关接口实现
 *
 * @author tiancun
 * @date 2019-04-29
 */
@Service
public class MyDoctorServiceImpl implements MyDoctorService {

    @Resource
    private DoctorMapper doctorMapper;


    @Override
    public PageResponseBean<MyDoctorResponse> getDoctorPage(MyDoctorRequest request) {

        Integer count = doctorMapper.getMyDoctorListCount(request);
        if (count == null){
            count = 0;
        }
        List<MyDoctorResponse> myDoctorList ;
        if (count == 0) {
            myDoctorList = new ArrayList<>();
            return new PageResponseBean<>(request, count, myDoctorList);
        }

        myDoctorList = doctorMapper.getMyDoctorList(request);
        if (CollectionsUtil.isEmptyList(myDoctorList)) {
            myDoctorList = new ArrayList<>();
            return new PageResponseBean<>(request, count, myDoctorList);

        }

        // 填充医生的手机号
        List<Long> doctorIdList = myDoctorList.stream().map(MyDoctorResponse::getDoctorId).distinct().collect(Collectors.toList());
        if (CollectionsUtil.isNotEmptyList(doctorIdList)){
            List<DoctorTelephoneResponse> doctorTelephoneList = doctorMapper.getDoctorTelephoneList(doctorIdList);
            if (CollectionsUtil.isNotEmptyList(doctorTelephoneList)){
                Map<Long, List<DoctorTelephoneResponse>> doctorTelephoneMap = doctorTelephoneList.stream().collect(Collectors.groupingBy(DoctorTelephoneResponse::getDoctorId));
                myDoctorList.forEach(m -> {
                    List<DoctorTelephoneResponse> doctorTelephones = doctorTelephoneMap.get(m.getDoctorId());
                    // 去掉无效的手机号
                    List<String> telephoneList = this.filterInvalidTelephones(doctorTelephones);
                    if (CollectionsUtil.isNotEmptyList(telephoneList)){
                        m.setTelephoneList(telephoneList);
                    }
                });
            }
        }


        // 填充医生的拜访数

        List<Long> visitIdList = myDoctorList.stream().map(MyDoctorResponse::getMaxVisitId).distinct().collect(Collectors.toList());
        if (CollectionsUtil.isEmptyList(visitIdList)) {
            return new PageResponseBean<>(request, count, myDoctorList);
        }

        List<DoctorVisitResponse> doctorVisitList = doctorMapper.getDoctorVisitList(visitIdList);
        if (CollectionsUtil.isEmptyList(doctorVisitList)){
            return new PageResponseBean<>(request, count, myDoctorList);
        }

        myDoctorList.forEach(m -> {
            Optional<DoctorVisitResponse> first = doctorVisitList.stream().filter(dv -> (dv.getMaxVisitId().equals(m.getMaxVisitId()))).findFirst();
            if (first.isPresent()) {
                DoctorVisitResponse doctorVisitResponse = first.get();
                m.setLastVisitTime(doctorVisitResponse.getLastVisitTime());
                m.setVisitDrugUserId(doctorVisitResponse.getVisitDrugUserId());
                m.setVisitDrugUserName(doctorVisitResponse.getVisitDrugUserName());
                m.setVisitResult(doctorVisitResponse.getVisitResult());
            }
        });

        return new PageResponseBean<>(request, count, myDoctorList);

    }


    /**
     * 过滤掉无效的联系方式
     * @param doctorTelephones
     * @return
     */
    private List<String> filterInvalidTelephones(List<DoctorTelephoneResponse> doctorTelephones) {
        if (CollectionsUtil.isEmptyList(doctorTelephones)){
            return null;
        }

        List<String> telephoneList = new ArrayList<>();
        List<String> telephones = doctorTelephones.stream().map(DoctorTelephoneResponse::getTelephone).distinct().collect(Collectors.toList());
        if (CollectionsUtil.isNotEmptyList(telephones)){
            for (String telephone : telephones) {
                if (RegularUtils.isMatcher(RegularUtils.MATCH_ELEVEN_NUM, telephone) || RegularUtils.isMatcher(RegularUtils.MATCH_FIX_PHONE, telephone)){
                    telephoneList.add(telephone);
                }
            }
        }

        return telephoneList;
    }


}
