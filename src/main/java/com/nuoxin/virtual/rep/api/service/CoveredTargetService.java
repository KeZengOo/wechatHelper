package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.dao.CoveredTargetRepository;
import com.nuoxin.virtual.rep.api.entity.CoveredTarget;
import com.nuoxin.virtual.rep.api.web.controller.request.month_target.MonthCoverTargetSetRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.month_target.MonthCoverTargetResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 月目标设置相关
 * Create by tiancun on 2017/10/19
 */
@Service
public class CoveredTargetService {

    @Autowired
    private CoveredTargetRepository coveredTargetRepository;


    public CoveredTarget findFirstByProductIdAndLevel(Long productId, String level){
        return coveredTargetRepository.findFirstByProductIdAndLevel(productId,level);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean add(List<MonthCoverTargetSetRequestBean> list, Long productId){
        Boolean flag = false;

        if (null == list || list.isEmpty()){
            return true;
        }

        coveredTargetRepository.deleteByProductId(productId);
        List<CoveredTarget> coveredTargetList = new ArrayList<>();
        for (MonthCoverTargetSetRequestBean bean:list){
            if (null != bean){
                CoveredTarget coveredTarget = new CoveredTarget();
                coveredTarget.setProductId(productId);
                coveredTarget.setLevel(bean.getLevel());
                coveredTarget.setMonthCovered(bean.getMonthCovered());
                coveredTarget.setCreateTime(new Date());
                coveredTargetList.add(coveredTarget);
            }
        }

        coveredTargetRepository.save(coveredTargetList);

        flag = true;

        return flag;

    }

    public List<MonthCoverTargetResponseBean> getMonthTargetList(Long productId){
        List<MonthCoverTargetResponseBean> list = new ArrayList<>();
        List<CoveredTarget> coveredTargetList = coveredTargetRepository.findByProductId(productId);

        if (null != coveredTargetList && !coveredTargetList.isEmpty()){
            for (CoveredTarget coveredTarget:coveredTargetList){
                if (null != coveredTarget){

                    MonthCoverTargetResponseBean monthCoverTarget = new MonthCoverTargetResponseBean();
                    monthCoverTarget.setLevel(coveredTarget.getLevel());
                    monthCoverTarget.setMonthCovered(coveredTarget.getMonthCovered());
                    monthCoverTarget.setProductId(coveredTarget.getProductId());
                    monthCoverTarget.setId(coveredTarget.getId());
                    list.add(monthCoverTarget);
                }
            }
        }

        return list;
    }



}
