package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.nuoxin.virtual.rep.api.enums.VisitResultTypeEnum;


import com.nuoxin.virtual.rep.api.mybatis.DrugUserDoctorQuateMapper;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.statistics.DailyStatisticsRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.statistics.DailyStatisticsResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.statistics.VisitResultDoctorResponseBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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





    @Override
    public DailyStatisticsResponseBean getDailyStatistics(DailyStatisticsRequestBean bean) {

        List<Long> drugUserIdList = new ArrayList<>();
        drugUserIdList.add(bean.getDrugUserId());
        bean.setDrugUserIdList(drugUserIdList);

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

        // 通话时长(单位是秒)
        Integer callTime = virtualDoctorCallInfoMapper.getCallTime(bean);

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
}
