package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v2_5.ProductVisitResultResponse;
import com.nuoxin.virtual.rep.api.enums.VisitResultTypeEnum;


import com.nuoxin.virtual.rep.api.mybatis.*;
import com.nuoxin.virtual.rep.api.service.v2_5.DailyStatisticsService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;

import com.nuoxin.virtual.rep.api.utils.StringUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.statistics.DailyStatisticsRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.statistics.DailyStatisticsResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.statistics.ProductTargetResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.statistics.VisitResultDoctorResponseBean;
import org.apache.poi.ss.formula.functions.Rate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 日报统计接口相关实现
 * @author tiancun
 * @date 2019-02-25
 */
@Service
public class DailyStatisticsServiceImpl implements DailyStatisticsService {


    @Resource
    private ProductTargetMapper productTargetMapper;

    @Resource
    private DrugUserDoctorQuateMapper drugUserDoctorQuateMapper;

    @Resource
    private VirtualDoctorCallInfoMapper virtualDoctorCallInfoMapper;

    @Resource
    private DynamicFieldMapper dynamicFieldMapper;

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private DoctorMendMapper doctorMendMapper;

    @Resource
    private DrugUserDoctorMapper drugUserDoctorMapper;


    @Resource
    private DrugUserDoctorQuateResultMapper drugUserDoctorQuateResultMapper;
    
    @Resource
    private VirtualProductVisitResultMapper virtualProductVisitResultMapper;

    @Resource
    private ProductLineMapper productLineMapper;

    @Resource
    private DrugUserMapper drugUserMapper;

    @Resource
    private DrugUserRepository drugUserRepository;





    @Override
    public DailyStatisticsResponseBean getDailyStatistics(DailyStatisticsRequestBean bean) {

        Integer targetHospital = 0;
        Integer targetDoctor = 0;
        String startTime = bean.getStartTime();

        Long productId = bean.getProductId();
        ProductTargetResponseBean productTarget = productTargetMapper.getProductTarget(productId);
        if (productTarget != null){
            Integer findTargetDoctor = productTarget.getTargetDoctor();
            Integer findTargetHospital = productTarget.getTargetHospital();
            if (findTargetDoctor != null){
                targetDoctor = findTargetDoctor;
            }

            if (findTargetHospital != null){
                targetHospital = findTargetHospital;
            }
        }

        // 招募医生数
        Integer recruitDoctor = drugUserDoctorQuateMapper.getRecruitDoctor(bean);

        // 招募医院数
        Integer recruitHospital = drugUserDoctorQuateMapper.getRecruitHospital(bean);


        // 目标医生招募达成率
        String targetRecruitDoctorRate = "0%";

        if (targetDoctor > 0 && recruitDoctor > 0){
            DecimalFormat df = new DecimalFormat("#.0");
            targetRecruitDoctorRate = df.format((recruitDoctor * 100)/targetDoctor);
            targetRecruitDoctorRate = targetRecruitDoctorRate + "%";
        }

        // 目标医院招募达成率
        String targetRecruitHospitalRate = "0%";
        if (targetHospital > 0 && recruitHospital > 0){
            DecimalFormat df = new DecimalFormat("#.0");
            targetRecruitHospitalRate = df.format((recruitHospital * 100)/targetHospital);
            targetRecruitHospitalRate = targetRecruitHospitalRate + "%";
        }


        // 拜访医生数
        Integer visitDoctorCount = virtualDoctorCallInfoMapper.getVisitDoctorCount(bean);

        // 拜访医院数
        Integer visitHospitalCount = virtualDoctorCallInfoMapper.getVisitHospitalCount(bean);

        // 接触医院数量
        bean.setVisitType(VisitResultTypeEnum.CONTACT.getType());
        Integer contactHospital = virtualDoctorCallInfoMapper.getVisitTypeHospitalCount(bean);

        // 接触医生数量
        Integer contactDoctor = virtualDoctorCallInfoMapper.getVisitTypeDoctorCount(bean);

        // 覆盖医院数量
        bean.setVisitType(VisitResultTypeEnum.COVER.getType());
        Integer coverHospital = virtualDoctorCallInfoMapper.getVisitTypeHospitalCount(bean);

        // 覆盖医生数量
        Integer coverDoctor = virtualDoctorCallInfoMapper.getVisitTypeDoctorCount(bean);


        // 成功医院数量
        bean.setVisitType(VisitResultTypeEnum.SUCCESS.getType());
        Integer successHospital = virtualDoctorCallInfoMapper.getVisitTypeHospitalCount(bean);

        // 成功医生数量
        Integer successDoctor = virtualDoctorCallInfoMapper.getVisitTypeDoctorCount(bean);

        // 有医生需求的医生数量
        Integer demandDoctor = dynamicFieldMapper.getDemandDoctor(bean);


        // 医生有AE的数量
        Integer hasAeDoctor = drugUserDoctorQuateMapper.hasAeDoctor(bean);

        // 电话外呼量
        Integer callCount = virtualDoctorCallInfoMapper.getCallCount(bean);

        // 电话接通量
        Integer connectCallCount = virtualDoctorCallInfoMapper.getConnectCallCount(bean);

        // 通话时长(单位是分钟，保留一位小数)
        String callTime = virtualDoctorCallInfoMapper.getCallTime(bean);

        // 面谈拜访次数
        Integer interviewVisit = virtualDoctorCallInfoMapper.interviewVisit(bean);

        // 微信、短信、邮件的拜访
        Integer otherVisit = virtualDoctorCallInfoMapper.otherVisit(bean);


        // 未招募医院数
        Integer noRecruitHospital = 0;
        if (targetHospital != 0){
            if ((targetHospital-recruitHospital) >=0 ){
                noRecruitHospital = targetHospital - recruitHospital;
            }
        }

        // 未招募医生数
        Integer noRecruitDoctor = 0;
        if (targetDoctor != 0){
            if ((targetDoctor-recruitDoctor) >=0 ){
                noRecruitDoctor = targetDoctor - recruitDoctor;
            }
        }

        // 微信医生回复次数
        Integer doctorWechatCount = messageMapper.doctorWechatCount(bean);

        // 微信医生回复人数
        Integer doctorWechatNum = messageMapper.doctorWechatNum(bean);


        // 有微信医生人数
        Integer hasWechatDoctor = doctorMendMapper.hasWechatDoctor(bean);

        // 添加微信的人数
        Integer addWechatDoctor = drugUserDoctorMapper.addWechatDoctor(bean);

        // 不同拜访结果的医生数量
        List<VisitResultDoctorResponseBean> visitResultDoctorList = drugUserDoctorQuateResultMapper.getVisitResultDoctorList(bean);


        // 退出项目的医院数量
        Integer breakOffHospital = drugUserDoctorQuateMapper.breakOffHospital(bean);

        // 退出项目的医生数量
        Integer breakOffDoctor = drugUserDoctorQuateMapper.breakOffDoctor(bean);


        // 放到最后
        // 新增招募医院数
        Integer addRecruitHospital = 0;
        // 新增招募医生数量
        Integer addRecruitDoctor = 0;

        // 所有招募医生数
        bean.setStartTime(null);
        bean.setEndTime(null);
        Integer allRecruitDoctor = drugUserDoctorQuateMapper.getRecruitDoctor(bean);

        // 所有招募医院数
        Integer allRecruitHospital = drugUserDoctorQuateMapper.getRecruitHospital(bean);
        if ((allRecruitHospital - recruitHospital) > 0){
            addRecruitHospital = allRecruitHospital - recruitHospital;
        }


        if ((allRecruitDoctor - recruitDoctor) > 0){
            addRecruitDoctor = allRecruitDoctor - recruitDoctor;
        }


        DailyStatisticsResponseBean dailyStatistics = new DailyStatisticsResponseBean();
        dailyStatistics.setTargetHospital(targetHospital);
        dailyStatistics.setTargetDoctor(targetDoctor);

        dailyStatistics.setTargetRecruitDoctorRate(targetRecruitDoctorRate);
        dailyStatistics.setTargetRecruitHospitalRate(targetRecruitHospitalRate);

        dailyStatistics.setRecruitHospital(recruitHospital);
        dailyStatistics.setRecruitDoctor(recruitDoctor);
        dailyStatistics.setAddRecruitHospital(addRecruitHospital);
        dailyStatistics.setAddRecruitDoctor(addRecruitDoctor);
        dailyStatistics.setVisitHospital(visitHospitalCount);
        dailyStatistics.setContactHospital(contactHospital);
        dailyStatistics.setCoverHospital(coverHospital);
        dailyStatistics.setSuccessHospital(successHospital);
        dailyStatistics.setVisitDoctor(visitDoctorCount);
        dailyStatistics.setContactDoctor(contactDoctor);
        dailyStatistics.setCoverDoctor(coverDoctor);
        dailyStatistics.setSuccessDoctor(successDoctor);
        dailyStatistics.setDemandDoctor(demandDoctor);
        dailyStatistics.setHasAeDoctor(hasAeDoctor);
        dailyStatistics.setDoctorCall(callCount);
        dailyStatistics.setDoctorConnectCall(connectCallCount);
        dailyStatistics.setCallTime(callTime);
        dailyStatistics.setInterviewVisit(interviewVisit);
        dailyStatistics.setOtherVisit(otherVisit);
        dailyStatistics.setNoRecruitDoctor(noRecruitDoctor);
        dailyStatistics.setNoRecruitHospital(noRecruitHospital);
        dailyStatistics.setDoctorWechatReplyCount(doctorWechatCount);
        dailyStatistics.setDoctorWechatReplyNum(doctorWechatNum);
        dailyStatistics.setHasWechatDoctor(hasWechatDoctor);
        dailyStatistics.setAddWechatDoctor(addWechatDoctor);
        if (CollectionsUtil.isNotEmptyList(visitResultDoctorList)){
            dailyStatistics.setVisitResultDoctor(visitResultDoctorList);
        }

        dailyStatistics.setBreakOffHospital(breakOffHospital);
        dailyStatistics.setBreakOffDoctor(breakOffDoctor);


        return dailyStatistics;
    }




    @Override
    public List<List<String>> getDownloadDailyStatisticsData(DailyStatisticsRequestBean bean) {

        List<Long> drugUserIdList = bean.getDrugUserIdList();
        if (CollectionsUtil.isEmptyList(drugUserIdList)){
            throw new BusinessException(ErrorEnum.ERROR, "代表ID不能为空！");
        }

        Long productId = bean.getProductId();
        List<DailyStatisticsResponseBean> dailyList = new ArrayList<>();
        // 下载合计的
        DailyStatisticsResponseBean dailyStatistics = this.getDailyStatistics(bean);
        dailyStatistics.setDrugUserName("合计");
        dailyList.add(dailyStatistics);

        // 因为代表数量不多才这样用循环
        for (Long drugUserId : drugUserIdList) {
            List<Long> subDrugUserIdList = new ArrayList<>();
            subDrugUserIdList.add(drugUserId);
            bean.setDrugUserIdList(subDrugUserIdList);
            DrugUser drugUser = drugUserRepository.findFirstById(drugUserId);
            if (drugUser == null){
                throw new BusinessException(ErrorEnum.ERROR, "不合法的代表ID:" + drugUserId);
            }
            DailyStatisticsResponseBean subDailyStatistics = this.getDailyStatistics(bean);
            subDailyStatistics.setDrugUserName(drugUser.getName());
            dailyList.add(subDailyStatistics);
        }



        // 组装成下载模板需要的格式
        List<List<String>> dataList = this.assemblyExcelData(dailyList, productId);


        return dataList;
    }

    @Override
    public String getDownloadDesc(DailyStatisticsRequestBean bean) {
        String productName = productLineMapper.getProductName(bean.getProductId());
        String drugUserNameList = drugUserMapper.getDrugUserNameList(bean.getDrugUserIdList());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("产品名称：");
        stringBuilder.append(productName);
        stringBuilder.append("  ");
        stringBuilder.append("时间：");
        String startTime = bean.getStartTime();
        if (StringUtil.isEmpty(startTime)){
            startTime = "";
        }

        String endTime = bean.getEndTime();
        if (StringUtil.isEmpty(endTime)){
            endTime = "";
        }

        if (StringUtil.isNotEmpty(startTime) && StringUtil.isNotEmpty(endTime)){
            stringBuilder.append(startTime);
            stringBuilder.append(" ~ ");
            stringBuilder.append(endTime);
        }else {
            stringBuilder.append("无");
        }

        stringBuilder.append("  ");
        stringBuilder.append("代表：");
        stringBuilder.append(drugUserNameList);

        return stringBuilder.toString();
    }


    @Override
    public void updateTargetHospital(Long productId, Integer targetHospital) {

        ProductTargetResponseBean productTarget = productTargetMapper.getProductTarget(productId);
        if (productTarget == null){
            productTargetMapper.addTargetHospital(productId, targetHospital);
        }else {
            productTargetMapper.updateTargetHospital(productId, targetHospital);
        }


    }

    @Override
    public void updateTargetDoctor(Long productId, Integer targetDoctor) {

        ProductTargetResponseBean productTarget = productTargetMapper.getProductTarget(productId);
        if (productTarget == null){
            productTargetMapper.addTargetDoctor(productId, targetDoctor);
        }else {
            productTargetMapper.updateTargetDoctor(productId, targetDoctor);
        }
        
    }


    /**
     * 组装Excel下载的数据
     * @param dailyList
     * @param productId
     * @return
     */
    private List<List<String>> assemblyExcelData(List<DailyStatisticsResponseBean> dailyList, Long productId) {
        if (CollectionsUtil.isEmptyList(dailyList)){
            return null;
        }

        List<String> itemList = this.getItemList(productId);
        List<List<String>> dataList = new ArrayList<>();
        dataList.add(itemList);
        for (DailyStatisticsResponseBean dailyStatisticsResponseBean : dailyList) {
            List<String> drugUserStatics = new ArrayList<>();
            drugUserStatics.add(dailyStatisticsResponseBean.getDrugUserName());
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getTargetHospital()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getTargetRecruitHospitalRate()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getVisitHospital()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getAddRecruitHospital()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getContactHospital()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getCoverHospital()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getSuccessHospital()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getNoRecruitHospital()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getBreakOffHospital()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getTargetDoctor()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getTargetRecruitDoctorRate()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getVisitDoctor()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getRecruitDoctor()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getAddRecruitDoctor()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getContactDoctor()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getCoverDoctor()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getSuccessDoctor()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getVisitHospital()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getDemandDoctor()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getHasAeDoctor()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getNoRecruitDoctor()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getDoctorWechatReplyCount()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getDoctorWechatReplyNum()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getHasWechatDoctor()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getAddWechatDoctor()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getBreakOffDoctor()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getDoctorCall()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getDoctorConnectCall()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getCallTime()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getInterviewVisit()));
            drugUserStatics.add(String.valueOf(dailyStatisticsResponseBean.getOtherVisit()));


            List<VisitResultDoctorResponseBean> visitResultDoctor = dailyStatisticsResponseBean.getVisitResultDoctor();
            if (CollectionsUtil.isNotEmptyList(visitResultDoctor)){
                for (VisitResultDoctorResponseBean visitResultDoctorResponseBean : visitResultDoctor) {
                    Integer doctorCount = visitResultDoctorResponseBean.getDoctorCount();
                    drugUserStatics.add(String.valueOf(doctorCount));
                }
            }

            dataList.add(drugUserStatics);
        }

        return dataList;

    }

    /**
     * 下载的指标选项
     * @param productId
     * @return
     */
    private List<String> getItemList(Long productId) {

        List<String> itemList = new ArrayList<>();
        itemList.add("统计指标");
        itemList.add("目标医院数量");
        itemList.add("目标医院招募达成率");
        itemList.add("拜访医院数量");
        itemList.add("招募医院数量");
        itemList.add("新增招募医院数量");
        itemList.add("接触医院数量");
        itemList.add("覆盖医院数量");
        itemList.add("成功医院数量");
        itemList.add("未招募医院数量");
        itemList.add("退出项目的医院数量");

        itemList.add("目标医生数量");
        itemList.add("目标医生招募达成率");
        itemList.add("拜访医生数量");
        itemList.add("招募医生数量");
        itemList.add("新增招募医生数量");
        itemList.add("接触医生数量");
        itemList.add("覆盖医生数量");
        itemList.add("成功医生数量");
        itemList.add("有需求医生数量");
        itemList.add("有AE的医生数量");
        itemList.add("未招募医生数量");
        itemList.add("医生微信回复次数");
        itemList.add("医生微信回复的数量");
        itemList.add("有微信医生");
        itemList.add("添加微信医生数量");
        itemList.add("退出项目的医生数量");


        itemList.add("电话外呼量");
        itemList.add("电话接通数量");
        itemList.add("通话时长(分钟)");
        itemList.add("面谈拜访次数");
        itemList.add("拜访方式为微信/短信/邮件的次数");

        List<ProductVisitResultResponse> productVisitResultList = virtualProductVisitResultMapper.selectVisitResultList(productId);
        if (CollectionsUtil.isNotEmptyList(productVisitResultList)){
            for (ProductVisitResultResponse productVisitResult : productVisitResultList) {
                itemList.add("拜访结果" + productVisitResult.getVisitResult() + "的医生人数");
            }
        }

        return itemList;
        
    }


}
