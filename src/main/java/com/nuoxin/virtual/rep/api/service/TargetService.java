package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.dao.TargetRepository;
import com.nuoxin.virtual.rep.api.entity.Target;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fenggang on 10/16/17.
 */
@Service
@Transactional(readOnly = true)
public class TargetService extends BaseService {

    @Autowired
    private TargetRepository targetRepository;

    public Target findFirstByProductIdAndLevel(Long productId, String level){
        return targetRepository.findFirstByProductIdAndLevel(productId,level);
    }





}
