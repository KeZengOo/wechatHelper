package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.FollowUpType;
import com.nuoxin.virtual.rep.api.entity.SmsTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;

/**
 * Created by fenggang on 9/18/17.
 */
public interface FollowUpTypeRepository extends JpaRepository<FollowUpType,Long>,JpaSpecificationExecutor<FollowUpType> {

    List<FollowUpType> findByProductIdIn(Collection<Long> productIds);

    void deleteByProductId(Long productId);

    List<FollowUpType> findByProductId(Long productId);

}
