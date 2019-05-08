package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.mybatis.DoctorMapper;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserMapper;
import com.nuoxin.virtual.rep.api.mybatis.DynamicFieldMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v3_0.CommonPoolService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.CommonPoolRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
    private DrugUserMapper drugUserMapper;

    @Resource
    private DynamicFieldMapper dynamicFieldMapper;

    @Resource
    private CommonService commonService;

    @Override
    public PageResponseBean<CommonPoolDoctorResponse> getDoctorPage(DrugUser drugUser, CommonPoolRequest request) {

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


        List<Long> doctorIdList = commonPoolDoctorList.stream().map(CommonPoolDoctorResponse::getDoctorId).distinct().collect(Collectors.toList());
        List<Long> productIdList = commonPoolDoctorList.stream().map(CommonPoolDoctorResponse::getProductId).distinct().collect(Collectors.toList());
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

            for (CommonPoolDoctorResponse commonPoolDoctor : commonPoolDoctorList) {
                Long doctorId = commonPoolDoctor.getDoctorId();
                List<DoctorTelephoneResponse> doctorTelephones = doctorTelephoneMap.get(doctorId);
                // 去掉无效的手机号
                List<String> telephoneList = commonService.filterInvalidTelephones(doctorTelephones);
                if (CollectionsUtil.isNotEmptyList(telephoneList)){
                    commonPoolDoctor.setTelephoneList(telephoneList);
                }

                List<DoctorProductResponse> doctorProductList = productListMap.get(doctorId);
                if (CollectionsUtil.isNotEmptyList(doctorProductList)){
                    commonPoolDoctor.setDoctorProductList(doctorProductList);
                }


                Optional<DoctorVisitResponse> first = doctorVisitList.stream().filter(dv -> (commonPoolDoctor.getDoctorId().equals(dv.getDoctorId()) && commonPoolDoctor.getProductId().equals(dv.getProductId()))).findFirst();
                if (first.isPresent()) {
                    DoctorVisitResponse doctorVisitResponse = first.get();
                    Date lastVisitDate = doctorVisitResponse.getLastVisitTime();
                    if (lastVisitDate !=null){
                        long visitTimeDelta = System.currentTimeMillis() - lastVisitDate.getTime();
                        String lastVisitTime = commonService.alterLastVisitTimeContent(visitTimeDelta);
                        commonPoolDoctor.setLastVisitTime(lastVisitTime);
                    }else{
                        commonPoolDoctor.setLastVisitTime("无");
                    }
                    commonPoolDoctor.setVisitDrugUserId(doctorVisitResponse.getVisitDrugUserId());
                    commonPoolDoctor.setVisitDrugUserName(doctorVisitResponse.getVisitDrugUserName());
                }
            }

        }





        return new PageResponseBean<>(request, count, commonPoolDoctorList);
    }


    @Override
    public void exportDoctorList(HttpServletResponse response, CommonPoolRequest request) {
        List<Long> productIdList = request.getProductIdList();
        if (CollectionsUtil.isEmptyList(productIdList)){
            throw new BusinessException(ErrorEnum.ERROR, "请选择产品！");
        }

        if (productIdList.size() > 1){
            throw new BusinessException(ErrorEnum.ERROR, "只能选择一个产品！");
        }

        /**
         * 得到导出固定的字段
         */
        List<String> titleList = this.getExportDoctorTitle(productIdList);




    }


    /**
     * 得到导出医生的标题
     * @param productIdList
     * @return
     */
    private List<String> getExportDoctorTitle(List<Long> productIdList) {
        List<String> titleList = new ArrayList<>();
        titleList.add("代表ID");
        titleList.add("代表姓名");
        titleList.add("医生ID");
        titleList.add("医生姓名");
        titleList.add("医生性别");
        titleList.add("医生科室");
        titleList.add("医生职称");
        titleList.add("医院ID");
        titleList.add("医院名称");
        titleList.add("医院省份");
        titleList.add("医院城市");
        titleList.add("医院等级");
        titleList.add("产品ID");
        titleList.add("产品名称");
        titleList.add("是否有药");
        titleList.add("是否目标医生");
        titleList.add("是否有AE");
        titleList.add("是否招募");
        titleList.add("是否覆盖");
        titleList.add("医生潜力");
        titleList.add("医生态度");
        titleList.add("拜访结果");

        List<DynamicFieldNameValueResponse> allDynamicFieldList = dynamicFieldMapper.getAllDynamicFieldList(productIdList);
        if (CollectionsUtil.isEmptyList(allDynamicFieldList)){
            List<String> dynamicFieldList = allDynamicFieldList.stream().map(DynamicFieldNameValueResponse::getFieldName).distinct().collect(Collectors.toList());
            titleList.addAll(dynamicFieldList);
        }

        return titleList;
    }
}
