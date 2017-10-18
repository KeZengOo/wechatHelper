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
import java.util.Calendar;
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
            responseBean.setTargerNum(this._getTarget(target.getMonthCovered(),bean.getDateType(),bean.getDate()));
        }
        if(responseBean.getTargerNum()!=null && responseBean.getTargerNum()!=0){
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
            responseBean = new TargetResponseBean();
        }
        if(target!=null){
            responseBean.setTargerNum(this._getTarget(target.getMonthTelPerson(),bean.getDateType(),bean.getDate()));
        }
        if(responseBean.getTargerNum()!=null && responseBean.getTargerNum()!=0){
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
            responseBean = new TargetResponseBean();
        }
        if(target!=null){
            responseBean.setTargerNum(this._getTarget(target.getMonthTelNum(),bean.getDateType(),bean.getDate()));
        }
        if(responseBean.getTargerNum()!=null && responseBean.getTargerNum()!=0){
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
            responseBean = new TargetResponseBean();
        }
        if(target!=null){
            responseBean.setTargerNum(this._getTarget(target.getMonthImNum(),bean.getDateType(),bean.getDate()));
        }
        if(responseBean.getTargerNum()!=null && responseBean.getTargerNum()!=0){
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
            responseBean = new TargetResponseBean();
        }
        if(target!=null){
            responseBean.setTargerNum(this._getTarget(target.getMonthWechatNum(),bean.getDateType(),bean.getDate()));
        }
        if(responseBean.getTargerNum()!=null &&responseBean.getTargerNum()!=0){
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
            responseBean = new TargetResponseBean();
        }
        if(target!=null){
            responseBean.setTargerNum(this._getTarget(target.getMonthTelTime()*60,bean.getDateType(),bean.getDate()));
        }
        if(responseBean.getTargerNum()!=null && responseBean.getTargerNum()!=0){
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
            responseBean = new TargetResponseBean();
        }
        if(target!=null){
            responseBean.setTargerNum(this._getTarget(target.getMonthTelAvgTime()*60,bean.getDateType(),bean.getDate()));
        }
        if(responseBean.getTargerNum()!=null && responseBean.getTargerNum()!=0){
            responseBean.setPercentage(new BigDecimal(responseBean.getCoverNum()/responseBean.getTargerNum().floatValue()).floatValue());
        }
        return responseBean;
    }

    /**
     * 会议
     * @param bean
     * @return
     */
    public MettingTargetResponseBean meeting(TargetAnalysisRequestBean bean) {
        bean.checkDate();
        Target target = targetService.findFirstByProductIdAndLevel(bean.getProductId(), bean.getCustomLevel());
        MettingTargetResponseBean responseBean = targetAnalysisMapper.meeting(bean);
        if (responseBean == null) {
            responseBean = new MettingTargetResponseBean();
        }
        if (target != null) {
            responseBean.setdTargetCount(this._getTarget(target.getMonthMeetingPersonSum(),bean.getDateType(),bean.getDate()));
            responseBean.setmTargetCount(this._getTarget(target.getMonthMeetingNum(),bean.getDateType(),bean.getDate()));
            if (responseBean.getdTargetCount() != 0) {
                responseBean.setdPercentage(new BigDecimal(responseBean.getdCount() / responseBean.getdTargetCount().doubleValue()).floatValue());
            }
            if (responseBean.getmTargetCount() != 0) {
                responseBean.setmPercentage(new BigDecimal(responseBean.getmCount() / responseBean.getmTargetCount().doubleValue()).floatValue());
            }
        }
        return responseBean;
    }

    private Integer _getTarget(Integer targetNum,Integer type,String date){
        if(targetNum==null){
            return 0;
        }

        if(type==1){
            //获取当月多少天
            String[] dates = date.split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.valueOf(dates[0]),Integer.valueOf(dates[1])-1,1,0,0,0);
            calendar.set(Calendar.DATE, 1);//把日期设置为当月第一天
            calendar.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
            int maxDate = calendar.get(Calendar.DATE);
            return targetNum/maxDate;
        }else if(type==2){
            return targetNum/4;
        }else if(type==3){
            return  targetNum;
        }else if(type==4){
            return  targetNum*3;
        }
        return 0;
    }
}
