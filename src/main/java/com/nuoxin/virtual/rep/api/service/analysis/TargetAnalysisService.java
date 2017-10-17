package com.nuoxin.virtual.rep.api.service.analysis;

import com.nuoxin.virtual.rep.api.dao.TargetRepository;
import com.nuoxin.virtual.rep.api.entity.Target;
import com.nuoxin.virtual.rep.api.mybatis.TargetAnalysisMapper;
import com.nuoxin.virtual.rep.api.service.TargetService;
import com.nuoxin.virtual.rep.api.web.controller.request.analysis.TargetAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.ta.MettingTargetResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.ta.TargetResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by fenggang on 10/12/17.
 */
@Service
public class TargetAnalysisService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TargetService targetService;
    @Autowired
    private TargetAnalysisMapper targetAnalysisMapper;

    /***
     * 汇总目标及覆盖量
     * @param bean
     * @return
     */
    public TargetResponseBean summation(TargetAnalysisRequestBean bean) {
        bean.checkDate();
        TargetResponseBean responseBean = new TargetResponseBean();
        Target target = targetService.findFirstByProductIdAndLevel(bean.getProductId(), bean.getCustomLevel());
        Integer count = targetAnalysisMapper.summation(bean);
        responseBean.setCoverNum(count);
        if (target != null) {
            responseBean.setTargerNum(target.getMonthCovered());
        }
        if(responseBean.getTargerNum()!=0){
            responseBean.setPercentage(new BigDecimal(responseBean.getCoverNum()/responseBean.getTargerNum().doubleValue()).floatValue());
        }
        return responseBean;
    }

    /**
     * 目标电话人数
     * @param bean
     * @return
     */
    public TargetResponseBean custom_tel_person(TargetAnalysisRequestBean bean) {
        bean.checkDate();
        Target target = targetService.findFirstByProductIdAndLevel(bean.getProductId(), bean.getCustomLevel());
        TargetResponseBean responseBean = targetAnalysisMapper.customTelPerson(bean);
        if(responseBean==null){

        }
        if(target!=null){
            responseBean.setTargerNum(target.getMonthTelPerson());
        }
        if(responseBean.getTargerNum()!=0){
            responseBean.setPercentage(new BigDecimal(responseBean.getCoverNum()/responseBean.getTargerNum().floatValue()).floatValue());
        }
        return responseBean;
    }

    /**
     * 目标电话次数
     * @param bean
     * @return
     */
    public TargetResponseBean custom_tel_count(TargetAnalysisRequestBean bean) {
        bean.checkDate();
        Target target = targetService.findFirstByProductIdAndLevel(bean.getProductId(), bean.getCustomLevel());
        TargetResponseBean responseBean = targetAnalysisMapper.customTelCount(bean);
        if(responseBean==null){

        }
        if(target!=null){
            responseBean.setTargerNum(target.getMonthTelNum());
        }
        if(responseBean.getTargerNum()!=0){
            responseBean.setPercentage(new BigDecimal(responseBean.getCoverNum()/responseBean.getTargerNum().floatValue()).floatValue());
        }
        return responseBean;
    }

    /**
     * 目标短信人数
     * @param bean
     * @return
     */
    public TargetResponseBean custom_sms(TargetAnalysisRequestBean bean) {
        bean.checkDate();
        Target target = targetService.findFirstByProductIdAndLevel(bean.getProductId(), bean.getCustomLevel());
        TargetResponseBean responseBean = targetAnalysisMapper.customSms(bean);
        if(responseBean==null){

        }
        if(target!=null){
            responseBean.setTargerNum(target.getMonthImNum());
        }
        if(responseBean.getTargerNum()!=0){
            responseBean.setPercentage(new BigDecimal(responseBean.getCoverNum()/responseBean.getTargerNum().floatValue()).floatValue());
        }
        return responseBean;
    }

    /**
     * 目标微信人数
     * @param bean
     * @return
     */
    public TargetResponseBean custom_wechat(TargetAnalysisRequestBean bean) {
        bean.checkDate();
        Target target = targetService.findFirstByProductIdAndLevel(bean.getProductId(), bean.getCustomLevel());
        TargetResponseBean responseBean = targetAnalysisMapper.customWechat(bean);
        if(responseBean==null){

        }
        if(target!=null){
            responseBean.setTargerNum(target.getMonthWechatNum());
        }
        if(responseBean.getTargerNum()!=0){
            responseBean.setPercentage(new BigDecimal(responseBean.getCoverNum()/responseBean.getTargerNum().floatValue()).floatValue());
        }
        return responseBean;
    }

    /**
     * 目标电话汇总时长
     * @param bean
     * @return
     */
    public TargetResponseBean custom_tel_sum(TargetAnalysisRequestBean bean) {
        bean.checkDate();
        Target target = targetService.findFirstByProductIdAndLevel(bean.getProductId(), bean.getCustomLevel());
        TargetResponseBean responseBean = targetAnalysisMapper.customTelSum(bean);
        if(responseBean==null){

        }
        if(target!=null){
            responseBean.setTargerNum(target.getMonthTelTime()*60);
        }
        if(responseBean.getTargerNum()!=0){
            responseBean.setPercentage(new BigDecimal(responseBean.getCoverNum()/responseBean.getTargerNum().floatValue()).floatValue());
        }
        return responseBean;
    }

    /**
     * 目标电话平均时长
     * @param bean
     * @return
     */
    public TargetResponseBean custom_tel_avg(TargetAnalysisRequestBean bean) {
        bean.checkDate();
        Target target = targetService.findFirstByProductIdAndLevel(bean.getProductId(), bean.getCustomLevel());
        TargetResponseBean responseBean = targetAnalysisMapper.customTelAvg(bean);
        if(responseBean==null){

        }
        if(target!=null){
            responseBean.setTargerNum(target.getMonthTelAvgTime()*60);
        }
        if(responseBean.getTargerNum()!=0){
            responseBean.setPercentage(new BigDecimal(responseBean.getCoverNum()/responseBean.getTargerNum().floatValue()).floatValue());
        }
        return responseBean;
    }

    public MettingTargetResponseBean meeting(TargetAnalysisRequestBean bean) {
        bean.checkDate();
        Target target = targetService.findFirstByProductIdAndLevel(bean.getProductId(), bean.getCustomLevel());
        MettingTargetResponseBean responseBean = targetAnalysisMapper.meeting(bean);
        if (responseBean == null) {
            responseBean = new MettingTargetResponseBean();
        }
        if (target != null) {
            responseBean.setdTargetCount(target.getMonthMeetingPersonSum());
            responseBean.setmTargetCount(target.getMonthMeetingNum());
            if (responseBean.getdTargetCount() != 0) {
                responseBean.setdPercentage(new BigDecimal(responseBean.getdCount() / responseBean.getdTargetCount().doubleValue()).floatValue());
            }
            if (responseBean.getmTargetCount() != 0) {
                responseBean.setmPercentage(new BigDecimal(responseBean.getmCount() / responseBean.getmTargetCount().doubleValue()).floatValue());
            }
        }
        return responseBean;
    }
}
