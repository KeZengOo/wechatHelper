package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.dao.FollowUpTypeRepository;
import com.nuoxin.virtual.rep.api.entity.FollowUpType;
import com.nuoxin.virtual.rep.api.entity.ProductLine;
import com.nuoxin.virtual.rep.api.web.controller.response.FollowUpTypResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fenggang on 10/17/17.
 */
@Service
@Transactional(readOnly = true)
public class FollowUpTypeService extends BaseService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FollowUpTypeRepository followUpTypeRepository;
    @Autowired
    private ProductLineService productLineService;

    public List<FollowUpTypResponseBean> list(String leaderPath){
        List<Long> ids = productLineService.getProductIds(leaderPath);
        if(ids!=null && !ids.isEmpty()){
            return this.list(ids);
        }
        return null;
    }

    public List<FollowUpTypResponseBean> list(List<Long> ids){
        List<FollowUpTypResponseBean> responseBeans = new ArrayList<>();
        List<FollowUpType> list = followUpTypeRepository.findByProductIdIn(ids);
        if(list!=null && !list.isEmpty()){
            for (FollowUpType entity:list) {
                FollowUpTypResponseBean responseBean = new FollowUpTypResponseBean();
                ProductLine product = productLineService.findById(entity.getProductId());
                if(product!=null){
                    responseBean.setProductName(product.getName());
                }
                responseBean.setId(entity.getId());
                responseBean.setProductId(entity.getProductId());
                responseBean.setType(entity.getType());
                responseBeans.add(responseBean);
            }
        }
        return responseBeans;
    }
}
