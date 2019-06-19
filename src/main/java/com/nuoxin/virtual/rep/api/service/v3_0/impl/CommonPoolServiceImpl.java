package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.mybatis.DoctorMapper;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserMapper;
import com.nuoxin.virtual.rep.api.mybatis.DynamicFieldMapper;
import com.nuoxin.virtual.rep.api.mybatis.ProductLineMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v3_0.CommonPoolService;
import com.nuoxin.virtual.rep.api.utils.*;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.CommonPoolRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.product.ProductResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.*;
import org.apache.commons.collections4.map.LinkedMap;
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
    private ProductLineMapper productLineMapper;


    @Resource
    private CommonService commonService;

    @Override
    public PageResponseBean<CommonPoolDoctorResponse> getDoctorPage(DrugUser drugUser, CommonPoolRequest request) {
        String searchKeyword = request.getSearchKeyword();
        if (StringUtil.isNotEmpty(searchKeyword)){
            request.setSearchKeyword(searchKeyword.trim());
        }

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
            List<Long> productId = commonService.getProductIdListByDrugUserId(drugUser.getId());
            List<DoctorProductResponse> productList = drugUserMapper.getProductListByDoctorIdAndProduct(null, productId, doctorIdList);
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

                commonPoolDoctor.setHospitalLevelStr(HospitalLevelUtil.getLevelNameByLevelCode("" + commonPoolDoctor.getHospitalLevel()));

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
        // 设置成不可分页的
        request.setPaginable(1);

        List<CommonPoolDoctorResponse> commonPoolDoctorList = doctorMapper.getCommonPoolDoctorList(request);
        List<LinkedHashMap<String, Object>> dataMap = this.exportData(productIdList, commonPoolDoctorList);
        /**
         * 得到导出的字段
         */
        Map<String, String> titleMap = this.getExportDoctorTitle(productIdList);
        HSSFWorkbook workbook = ExportExcel.excelLinkedHashMapExport(dataMap, titleMap, "医生列表");
        OutputStream ouputStream = null;
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("医生列表.xls","UTF-8"));
            response.setHeader("Pragma", "No-cache");
            ouputStream = response.getOutputStream();
            if(ouputStream!=null){
                workbook.write(ouputStream);
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

    @Override
    public List<ProductResponseBean> getProductList() {

        List<ProductResponseBean> commonPoolProductList = productLineMapper.getCommonPoolProductList();

        return commonPoolProductList;
    }

    /**
     * 得到要下载的数据
     * @param productIdList
     * @param commonPoolDoctorList
     * @return
     */
    private List<LinkedHashMap<String, Object>> exportData(List<Long> productIdList, List<CommonPoolDoctorResponse> commonPoolDoctorList) {
        if (CollectionsUtil.isEmptyList(commonPoolDoctorList)){
            return null;
        }

        List<DynamicFieldNameValueResponse> doctorDynamicFieldValue = null;
        List<Long> doctorIdList = commonPoolDoctorList.stream().map(CommonPoolDoctorResponse::getDoctorId).distinct().collect(Collectors.toList());
        if (CollectionsUtil.isNotEmptyList(doctorIdList)){
            doctorDynamicFieldValue = dynamicFieldMapper.getDoctorDynamicFieldNameValue(doctorIdList, productIdList.get(0));
            if (CollectionsUtil.isEmptyList(doctorDynamicFieldValue)){
                doctorDynamicFieldValue = new ArrayList<>();
            }
        }

        Map<Long, List<DynamicFieldNameValueResponse>> doctorDynamicFieldMap = doctorDynamicFieldValue.stream().collect(Collectors.groupingBy(DynamicFieldNameValueResponse::getDoctorId));
        if (CollectionsUtil.isEmptyMap(doctorDynamicFieldMap)){
            doctorDynamicFieldMap = new HashMap<>();
        }

        List<LinkedHashMap<String, Object>> list = new ArrayList<>();
        for (CommonPoolDoctorResponse m : commonPoolDoctorList) {
            LinkedHashMap<String, Object> doctorDetailMap = new LinkedHashMap<>();

            Long drugUserId = m.getDrugUserId();
            String drugUserName = m.getDrugUserName();
            Long doctorId = m.getDoctorId();
            String doctorName = m.getDoctorName();
            String sex = m.getSex();
            String depart = m.getDepart();
            String positions = m.getPositions();
            Long hospitalId = m.getHospitalId();
            String hospitalName = m.getHospitalName();
            String province = m.getProvince();
            String city = m.getCity();
            String hospitalLevel = HospitalLevelUtil.getLevelNameByLevelCode("" + m.getHospitalLevel());
            Long productId = m.getProductId();
            String productName = m.getProductName();
            String hasDrug = m.getHasDrug();
            String target = m.getTarget();
            String hasAe = m.getHasAe();
            String recruit = m.getRecruit();
            String cover = m.getCover();
            String potential = m.getPotential();
            String attitude = m.getAttitude();
            String visitResult = m.getVisitResult();
            doctorDetailMap.put("drugUserId", drugUserId);
            doctorDetailMap.put("drugUserName", drugUserName);
            doctorDetailMap.put("doctorId", doctorId);
            doctorDetailMap.put("doctorName", doctorName);
            doctorDetailMap.put("sex", sex);
            doctorDetailMap.put("depart", depart);
            doctorDetailMap.put("positions", positions);
            doctorDetailMap.put("hospitalId", hospitalId);
            doctorDetailMap.put("hospitalName", hospitalName);
            doctorDetailMap.put("province", province);
            doctorDetailMap.put("city", city);
            doctorDetailMap.put("level", hospitalLevel);
            doctorDetailMap.put("productId", productId);
            doctorDetailMap.put("productName", productName);
            doctorDetailMap.put("isHasDrug", hasDrug);
            doctorDetailMap.put("isTarget", target);
            doctorDetailMap.put("isHasAe", hasAe);
            doctorDetailMap.put("isRecruit", recruit);
            doctorDetailMap.put("isCover", cover);
            doctorDetailMap.put("potential", potential);
            doctorDetailMap.put("attitude", attitude);
            doctorDetailMap.put("visitResult", visitResult);

            List<DynamicFieldNameValueResponse> dynamicFieldNameValueList = doctorDynamicFieldMap.get(doctorId);
            if (CollectionsUtil.isNotEmptyList(dynamicFieldNameValueList)){
                dynamicFieldNameValueList.forEach(dy->{
                    doctorDetailMap.put(String.valueOf(dy.getFieldId()), dy.getFieldValue());
                });
            }

            list.add(doctorDetailMap);

        }

        return list;
    }


    /**
     * 得到导出医生的标题
     * @param productIdList
     * @return
     */
    private Map<String, String> getExportDoctorTitle(List<Long> productIdList) {
        Map<String, String> titleMap = new LinkedHashMap<>();
        titleMap.put("drugUserId", "代表ID");
        titleMap.put("drugUserName", "代表姓名");
        titleMap.put("doctorId", "医生ID");
        titleMap.put("doctorName", "医生姓名");
        titleMap.put("sex", "性别");
        titleMap.put("depart", "科室");
        titleMap.put("positions", "职称");
        titleMap.put("hospitalId", "医院ID");
        titleMap.put("hospitalName", "医院名称");
        titleMap.put("province", "省份");
        titleMap.put("city", "城市");
        titleMap.put("level", "医院等级");
        titleMap.put("productId", "产品ID");
        titleMap.put("productName", "产品名称");
        titleMap.put("isHasDrug", "是否有药");
        titleMap.put("isTarget", "是否目标");
        titleMap.put("isHasAe", "是否有AE");
        titleMap.put("isRecruit", "是否招募");
        titleMap.put("isCover", "是否覆盖");
        titleMap.put("potential", "医生潜力");
        titleMap.put("attitude", "医生态度");
        titleMap.put("visitResult", "拜访结果");

        List<DynamicFieldNameValueResponse> allDynamicFieldList = dynamicFieldMapper.getAllDynamicFieldList(productIdList);
        if (CollectionsUtil.isNotEmptyList(allDynamicFieldList)){
            allDynamicFieldList.forEach(dy->{
                titleMap.put(String.valueOf(dy.getFieldId()), dy.getFieldName());
            });
        }

        return titleMap;
    }
}
