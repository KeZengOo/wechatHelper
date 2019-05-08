package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.mybatis.DoctorMapper;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v3_0.MyDoctorService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.utils.ExcelUtils;
import com.nuoxin.virtual.rep.api.utils.RegularUtils;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.MyDoctorRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.vo.DoctorVo;
import com.nuoxin.virtual.rep.api.web.controller.response.message.MessageResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
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

    private static final Logger logger = LoggerFactory.getLogger(MyDoctorServiceImpl.class);


    @Resource
    private DoctorMapper doctorMapper;


    @Resource
    private DrugUserMapper drugUserMapper;

    @Resource
    private CommonService commonService;


    @Override
    public PageResponseBean<MyDoctorResponse> getDoctorPage(DrugUser drugUser, MyDoctorRequest request) {

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

        List<Long> doctorIdList = myDoctorList.stream().map(MyDoctorResponse::getDoctorId).distinct().collect(Collectors.toList());
        List<Long> productIdList = myDoctorList.stream().map(MyDoctorResponse::getProductId).distinct().collect(Collectors.toList());
        if (CollectionsUtil.isNotEmptyList(doctorIdList)){

            // 填充医生的手机号
            List<DoctorTelephoneResponse> doctorTelephoneList = doctorMapper.getDoctorTelephoneList(doctorIdList);
            if (CollectionsUtil.isEmptyList(doctorTelephoneList)){
                doctorTelephoneList = new ArrayList<>();
            }

            Map<Long, List<DoctorTelephoneResponse>> doctorTelephoneMap = doctorTelephoneList.stream().collect(Collectors.groupingBy(DoctorTelephoneResponse::getDoctorId));
            if (CollectionsUtil.isEmptyMap(doctorTelephoneMap)){
                doctorTelephoneMap = new HashMap<>();
            }


            // 填充医生的多个产品
            List<DoctorProductResponse> productList = drugUserMapper.getProductListByDoctorId(drugUser.getLeaderPath(), doctorIdList);
            if (CollectionsUtil.isEmptyList(productList)){
                productList = new ArrayList<>();
            }

            // 填充医生的拜访数据
            List<DoctorVisitResponse> doctorVisitList = doctorMapper.getLastDoctorVisitList(doctorIdList, productIdList);
            if (CollectionsUtil.isEmptyList(doctorVisitList)){
                doctorVisitList = new ArrayList<>();
            }

            Map<Long, List<DoctorProductResponse>> productListMap = productList.stream().collect(Collectors.groupingBy(DoctorProductResponse::getDoctorId));
            if (CollectionsUtil.isEmptyMap(productListMap)){
                productListMap = new HashMap<>();
            }

            for (MyDoctorResponse doctor : myDoctorList) {
                Long doctorId = doctor.getDoctorId();
                List<DoctorTelephoneResponse> doctorTelephones = doctorTelephoneMap.get(doctorId);
                // 去掉无效的手机号
                List<String> telephoneList = commonService.filterInvalidTelephones(doctorTelephones);
                if (CollectionsUtil.isNotEmptyList(telephoneList)){
                    doctor.setTelephoneList(telephoneList);
                }

                List<DoctorProductResponse> doctorProductList = productListMap.get(doctorId);
                if (CollectionsUtil.isNotEmptyList(doctorProductList)){
                    doctor.setDoctorProductList(doctorProductList);
                }


                Optional<DoctorVisitResponse> first = doctorVisitList.stream().filter(dv -> (doctor.getDoctorId().equals(dv.getDoctorId()) && doctor.getProductId().equals(dv.getProductId()))).findFirst();
                if (first.isPresent()) {
                    DoctorVisitResponse doctorVisitResponse = first.get();
                    Date lastVisitDate = doctorVisitResponse.getLastVisitTime();
                    if (lastVisitDate !=null){
                        long visitTimeDelta = System.currentTimeMillis() - lastVisitDate.getTime();
                        String lastVisitTime = commonService.alterLastVisitTimeContent(visitTimeDelta);
                        doctor.setLastVisitTime(lastVisitTime);
                    }else{
                        doctor.setLastVisitTime("无");
                    }
                    doctor.setVisitDrugUserId(doctorVisitResponse.getVisitDrugUserId());
                    doctor.setVisitDrugUserName(doctorVisitResponse.getVisitDrugUserName());
                }
            }

        }

        return new PageResponseBean<>(request, count, myDoctorList);

    }







}
