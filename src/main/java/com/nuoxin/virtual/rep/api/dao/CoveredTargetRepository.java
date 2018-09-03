package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.CoveredTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Create by tiancun on 2017/10/19
 */
public interface CoveredTargetRepository  extends JpaRepository<CoveredTarget, Long>,JpaSpecificationExecutor<CoveredTarget> {

    CoveredTarget findFirstByProductIdAndLevel(Long productId, String level);

    void deleteByProductId(Long productId);

    List<CoveredTarget> findByProductId(Long productId);

}
