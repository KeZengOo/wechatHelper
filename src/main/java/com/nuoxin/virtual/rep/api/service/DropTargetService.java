package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.dao.DropTargetRepository;
import com.nuoxin.virtual.rep.api.entity.DropTarget;
import com.nuoxin.virtual.rep.api.web.controller.request.month_target.DropTargetRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.DropTargetResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by tiancun on 2017/10/20
 */
@Service
public class DropTargetService {

    @Autowired
    private DropTargetRepository dropTargetRepository;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean add(List<DropTargetRequestBean> list){
        if (null == list || list.isEmpty()){
            return true;
        }

        dropTargetRepository.deleteByProductId(list.get(0).getProductId());

        Boolean flag = false;
        List<DropTarget> dropTargets = new ArrayList<>();
        for (DropTargetRequestBean bean:list){
            if (null != bean){
                DropTarget dropTarget = new DropTarget();
                dropTarget.setProductId(bean.getProductId());
                dropTarget.setDropPriod(bean.getDropPeriod());
                dropTarget.setLevel(bean.getLevel());
                dropTarget.setCreateTime(new Date());
                dropTargets.add(dropTarget);
            }

        }

        dropTargetRepository.save(dropTargets);

        flag = true;
        return flag;
    }



    public List<DropTargetResponseBean> getDropTargetList(Long productId){
        List<DropTargetResponseBean> list = new ArrayList<>();
        List<DropTarget> dropTargets = dropTargetRepository.findByProductId(productId);
        if (null != dropTargets && !dropTargets.isEmpty()){
            for (DropTarget dropTarget:dropTargets){
                DropTargetResponseBean dropTargetResponseBean = new DropTargetResponseBean();
                dropTargetResponseBean.setDropPeriod(dropTarget.getDropPriod());
                dropTargetResponseBean.setId(dropTarget.getId());
                dropTargetResponseBean.setLevel(dropTarget.getLevel());
                dropTargetResponseBean.setProductId(dropTarget.getProductId());
                list.add(dropTargetResponseBean);
            }
        }

        return list;
    }




}
