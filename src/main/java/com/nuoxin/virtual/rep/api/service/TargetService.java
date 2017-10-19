package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.dao.CoveredTargetRepository;
import com.nuoxin.virtual.rep.api.dao.TargetRepository;
import com.nuoxin.virtual.rep.api.entity.CoveredTarget;
import com.nuoxin.virtual.rep.api.entity.Target;
import com.nuoxin.virtual.rep.api.web.controller.request.target.MonthWorkTargetSetRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.ta.TargetResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.target.MonthWorkTargetResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fenggang on 10/16/17.
 */
@Service
@Transactional(readOnly = true)
public class TargetService extends BaseService {

    @Autowired
    private TargetRepository targetRepository;

    @Autowired
    private CoveredTargetRepository coveredTargetRepository;

    public Target findFirstByProductIdAndLevel(Long productId, String level){
        return targetRepository.findFirstByProductIdAndLevel(productId,level);
    }




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean add(List<MonthWorkTargetSetRequestBean> list, Long productId){
        Boolean flag = false;
        if (null == list||list.isEmpty()){
            return true;
        }

        targetRepository.deleteByProductId(productId);

        List<Target> targets = new ArrayList<>();
        for (MonthWorkTargetSetRequestBean bean:list){
            if (null != bean){
                Target target = new Target();
                target.setProductId(productId);
                target.setLevel(bean.getLevel());
                target.setMonthTelNum(bean.getMonthTelNum());
                target.setMonthTelTime(bean.getMonthTelTime());
                target.setMonthTelPerson(bean.getMonthTelPerson());
                target.setMonthTelAvgTime(bean.getMonthTelAvgTime());
                target.setMonthImNum(bean.getMonthImNum());
                target.setMonthImCount(bean.getMonthImCount());
                target.setMonthWechatNum(bean.getMonthWechatNum());
                target.setMonthWechatCount(bean.getMonthWechatCount());
                target.setMonthEmailNum(bean.getMonthEmailNum());
                target.setMonthEmailCount(bean.getMonthEmailCount());
                target.setMonthMeetingNum(bean.getMonthMeetingNum());
                target.setMonthMeetingTime(bean.getMonthMeetingTime());
                target.setMonthMeetingPersonSum(bean.getMonthMeetingPersonSum());
                target.setMonthMeetingPersonCount(bean.getMonthMeetingPersonCount());
                target.setCreateTime(new Date());
                target.setUpdateTime(new Date());
            }
        }

        targetRepository.save(targets);

        flag = true;
        return flag;
    }


    public List<MonthWorkTargetResponseBean> getMonthWorkTargetList(Long productId){
        List<MonthWorkTargetResponseBean> list = new ArrayList<>();
        List<Target> targetList = targetRepository.findByProductId(productId);
        if (null != targetList && !targetList.isEmpty()){
            for (Target target:targetList){
                if (null != target){
                    MonthWorkTargetResponseBean monthWorkTarget= new MonthWorkTargetResponseBean();
                    monthWorkTarget.setId(target.getId());
                    monthWorkTarget.setLevel(target.getLevel());
                    monthWorkTarget.setMonthTelNum(target.getMonthTelNum());
                    monthWorkTarget.setMonthTelTime(target.getMonthTelTime());
                    monthWorkTarget.setMonthTelPerson(target.getMonthTelPerson());
                    monthWorkTarget.setMonthTelAvgTime(target.getMonthTelAvgTime());
                    monthWorkTarget.setMonthImNum(target.getMonthImNum());
                    monthWorkTarget.setMonthImCount(target.getMonthImCount());
                    monthWorkTarget.setMonthWechatNum(target.getMonthWechatNum());
                    monthWorkTarget.setMonthWechatCount(target.getMonthWechatCount());
                    monthWorkTarget.setMonthEmailNum(target.getMonthEmailNum());
                    monthWorkTarget.setMonthEmailCount(target.getMonthEmailCount());
                    monthWorkTarget.setMonthMeetingNum(target.getMonthMeetingNum());
                    monthWorkTarget.setMonthMeetingTime(target.getMonthMeetingTime());
                    monthWorkTarget.setMonthMeetingPersonSum(target.getMonthMeetingPersonSum());
                    monthWorkTarget.setMonthMeetingPersonCount(target.getMonthMeetingPersonCount());
                    list.add(monthWorkTarget);
                }
            }
        }

        return list;
    }


}
