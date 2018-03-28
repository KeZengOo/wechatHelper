package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.dao.FollowUpTypeRepository;
import com.nuoxin.virtual.rep.api.entity.FollowUpType;
import com.nuoxin.virtual.rep.api.entity.ProductLine;
import com.nuoxin.virtual.rep.api.web.controller.request.month_target.FollowUpSetRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.FollowUpTypResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.month_target.FollowUpResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

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

    /**
     * 根据leaderpath获取跟进类型
     * @param leaderPath
     * @return
     */
    public List<FollowUpTypResponseBean> list(String leaderPath){
        List<Long> ids = productLineService.getProductIds(leaderPath);
        if(ids!=null && !ids.isEmpty()){
            return this.list(ids);
        }
        return null;
    }

    /**
     * 根据id获取根据类型
     * @param ids
     * @return
     */
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
     * @param list
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean addFollowUp(List<FollowUpSetRequestBean> list){
        if (null == list || list.isEmpty()){
            return true;
        }
        Boolean flag = false;

        followUpTypeRepository.deleteByProductId(list.get(0).getProductId());
//        String types = bean.getTypes();
//        if (!StringUtils.isEmpty(types)){
//            List<FollowUpType> list = new ArrayList<>();
//            String[] strings = types.split(",");
//            if (null != strings && strings.length > 0){
//                for (String type:strings){
//                    FollowUpType followUpType = new FollowUpType();
//                    followUpType.setProductId(productId);
//                    followUpType.setType(type);
//                    followUpType.setCreateTime(new Date());
//                    followUpType.setUpdateTime(new Date());
//                    list.add(followUpType);
//                }
//            }
//
//
//            followUpTypeRepository.save(list);
//        }

        List<FollowUpType> followUpTypeList = new ArrayList<>();
        for (FollowUpSetRequestBean followUpSetRequestBean:list){
            if (null != followUpSetRequestBean){
                FollowUpType followUpType = new FollowUpType();
                followUpType.setProductId(followUpSetRequestBean.getProductId());
                followUpType.setType(followUpSetRequestBean.getType());
                followUpType.setCreateTime(new Date());
                followUpType.setUpdateTime(new Date());
                followUpTypeList.add(followUpType);
            }
        }

        followUpTypeRepository.save(followUpTypeList);

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

    public Map<Long,FollowUpType> findByAllMap(){
        Map<Long,FollowUpType> map = new HashMap<>();
        List<FollowUpType> list = followUpTypeRepository.findAll();
        if(list!=null && !list.isEmpty()){
            for (FollowUpType followUpType : list) {
                map.put(followUpType.getId(),followUpType);
            }
        }
        return map;
    }

}
