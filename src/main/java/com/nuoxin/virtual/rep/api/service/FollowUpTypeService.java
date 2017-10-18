package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.dao.FollowUpTypeRepository;
import com.nuoxin.virtual.rep.api.entity.FollowUpType;
import com.nuoxin.virtual.rep.api.entity.ProductLine;
import com.nuoxin.virtual.rep.api.entity.Target;
import com.nuoxin.virtual.rep.api.web.controller.request.target.FollowUpSetRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.FollowUpTypResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.target.FollowUpResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
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

    /**
     * 跟进类型新增,修改
     * @param bean
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean addFollowUp(FollowUpSetRequestBean bean){
        Boolean flag = false;
        Long productId = bean.getProductId();
        followUpTypeRepository.deleteByProductId(productId);
        String types = bean.getTypes();
        if (!StringUtils.isEmpty(types)){
            List<FollowUpType> list = new ArrayList<>();
            String[] strings = types.split(",");
            if (null != strings && strings.length > 0){
                for (String type:strings){
                    FollowUpType followUpType = new FollowUpType();
                    followUpType.setProductId(productId);
                    followUpType.setType(type);
                    followUpType.setCreateTime(new Date());
                    followUpType.setUpdateTime(new Date());
                    list.add(followUpType);
                }
            }

            followUpTypeRepository.save(list);
        }

        flag = true;
        return flag;
    }

    /**
     * 跟进类型列表
     * @param bean
     * @return
     */
    public List<FollowUpResponseBean> getFollowUpList(FollowUpSetRequestBean bean){
        List<FollowUpType> followUpTypes = followUpTypeRepository.findByProductId(bean.getProductId());

        List<FollowUpResponseBean> list = new ArrayList<>();

        if (null != followUpTypes && !followUpTypes.isEmpty()){
            for (FollowUpType followUpType:followUpTypes){
                if (null !=followUpType){
                    FollowUpResponseBean followUpResponseBean = new FollowUpResponseBean();
                    followUpResponseBean.setType(followUpType.getType());
                    followUpResponseBean.setProductId(followUpType.getProductId());
                    list.add(followUpResponseBean);
                }
            }
        }

        return list;

    }

}
