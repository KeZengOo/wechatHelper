package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.dao.DoctorRepository;
import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v2_5.DrugUserDoctorLogParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.DrugUserDoctorParams;
import com.nuoxin.virtual.rep.api.mybatis.*;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v3_0.MyDoctorService;
import com.nuoxin.virtual.rep.api.utils.*;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DrugUserDoctorLogPageRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DrugUserDoctorLogRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.MyDoctorRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.vo.DoctorVo;
import com.nuoxin.virtual.rep.api.web.controller.response.message.MessageResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
    private ProductLineMapper productLineMapper;

    @Resource
    private DrugUserDoctorMapper drugUserDoctorMapper;

    @Resource
    private DrugUserDoctorLogMapper drugUserDoctorLogMapper;

    @Resource
    private CommonService commonService;

    @Resource
    private DrugUserRepository drugUserRepository;

    @Resource
    private DoctorRepository doctorRepository;


    @Override
    public PageResponseBean<MyDoctorResponse> getDoctorPage(DrugUser drugUser, MyDoctorRequest request) {

        String searchKeyword = request.getSearchKeyword();
        if (StringUtil.isNotEmpty(searchKeyword)){
            request.setSearchKeyword(searchKeyword.trim());
        }

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
            List<Long> productId = commonService.getProductIdListByDrugUserId(drugUser.getId());
            List<DoctorProductResponse> productList = drugUserMapper.getProductListByDoctorIdAndProduct(1, productId, doctorIdList);
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

                doctor.setHospitalLevelStr(HospitalLevelUtil.getLevelNameByLevelCode("" +doctor.getHospitalLevel()));
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertDrugUserDoctorLog(DrugUser operateDrugUser, DrugUserDoctorLogRequest request) {
        this.checkLogRequest(request);
        DrugUserDoctorLogParams logParams = this.getDrugUserDoctorLogParam(request);
        this.saveDrugUserDoctor(request);
        logParams.setOperateDrugUserId(operateDrugUser.getId());
        logParams.setOperateDrugUserName(operateDrugUser.getName());
        // 1代表操作单个
        logParams.setOperateWay(1);
        drugUserDoctorLogMapper.insert(logParams);

    }

    private void saveDrugUserDoctor(DrugUserDoctorLogRequest request) {
        Long oldDrugUserId = request.getOldDrugUserId();
        Long doctorId = request.getDoctorId();
        Long newDrugUserId = request.getNewDrugUserId();
        Long productId = request.getProductId();
        DrugUser newDrugUser = drugUserRepository.findFirstById(newDrugUserId);
        Doctor doctor = doctorRepository.findFirstById(doctorId);
        if (doctor == null){
            throw new BusinessException(ErrorEnum.ERROR, "所选择的医生不存在！");
        }

        DrugUserDoctorParams drugUserDoctorParams = new DrugUserDoctorParams();
        drugUserDoctorParams.setDoctorId(doctorId);
        drugUserDoctorParams.setDoctorName(doctor.getName());
        drugUserDoctorParams.setDoctorEmail(doctor.getEmail());
        drugUserDoctorParams.setDrugUserId(newDrugUserId);
        drugUserDoctorParams.setDrugUserName(newDrugUser.getName());
        drugUserDoctorParams.setDrugUserEmail(newDrugUser.getEmail());
        drugUserDoctorMapper.saveDrugUserDoctor(drugUserDoctorParams);
        drugUserDoctorMapper.updateDrugUserDoctorAvailable(oldDrugUserId, doctorId, productId);

    }

    @Override
    public PageResponseBean<DrugUserDoctorLogResponse> getSingleDoctorTransferLogPage(DrugUserDoctorLogPageRequest request) {

        Integer count = drugUserDoctorLogMapper.getSingleDoctorTransferLogListCount(request);
        List<DrugUserDoctorLogResponse> list = null;
        if (count != null && count > 0){
            list = drugUserDoctorLogMapper.getSingleDoctorTransferLogList(request);
        }
        return new PageResponseBean<>(request, count, list);

    }

    private void checkLogRequest(DrugUserDoctorLogRequest request) {


        Long oldDrugUserId = request.getOldDrugUserId();
        Long newDrugUserId = request.getNewDrugUserId();
        Long doctorId = request.getDoctorId();
        Long productId = request.getProductId();

        if (oldDrugUserId.equals(newDrugUserId)){
            throw new BusinessException(ErrorEnum.ERROR, "代表转医生不能自己转自己！");
        }


        Integer oldCount = productLineMapper.getProductUserCountById(oldDrugUserId, productId);
        if (oldCount == null || oldCount == 0){
            throw new BusinessException(ErrorEnum.ERROR, "被转移代表不在选择产品下！");
        }


        Integer newCount = productLineMapper.getProductUserCountById(newDrugUserId, productId);
        if (newCount == null || newCount == 0){
            throw new BusinessException(ErrorEnum.ERROR, "待转移代表不在选择产品下！");
        }


        Integer oldDoctorCount = drugUserDoctorMapper.getCountByDrugUserDoctorProduct(oldDrugUserId, doctorId, productId);
        if (oldDoctorCount == null || oldDoctorCount == 0){
            throw new BusinessException(ErrorEnum.ERROR, "被转移代表在选择产品下没有该医生！");
        }

        Integer newDoctorCount = drugUserDoctorMapper.getCountByDrugUserDoctorProduct(newDrugUserId, doctorId, productId);
        if (newDoctorCount == null && newDoctorCount > 0){
            throw new BusinessException(ErrorEnum.ERROR, "待转移代表在选择产品下已经有该医生！");
        }

    }

    /**
     * 组装新增的参数
     * @param request
     * @return
     */
    private DrugUserDoctorLogParams getDrugUserDoctorLogParam(DrugUserDoctorLogRequest request) {

        Long oldDrugUserId = request.getOldDrugUserId();
        Long newDrugUserId = request.getNewDrugUserId();
        Long doctorId = request.getDoctorId();
        Long productId = request.getProductId();

        DrugUser oldDrugUser = drugUserRepository.findFirstById(oldDrugUserId);
        DrugUser newDrugUser = drugUserRepository.findFirstById(newDrugUserId);


        DrugUserDoctorLogParams params = new DrugUserDoctorLogParams();
        params.setOldDrugUserId(oldDrugUserId);
        params.setNewDrugUserId(newDrugUserId);
        params.setOldDrugUserName(oldDrugUser.getName());
        params.setNewDrugUserName(newDrugUser.getName());
        params.setDoctorId(doctorId);
        params.setProductId(productId);

        return params;

    }


}
