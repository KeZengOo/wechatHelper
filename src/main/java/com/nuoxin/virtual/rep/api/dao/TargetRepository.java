package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Create by tiancun on 2017/10/16
 */
public interface TargetRepository extends JpaRepository<Target,Long>,JpaSpecificationExecutor<Target> {

    List<Target> findByProductId(Long productId);

    Target findFirstByProductIdAndLevel(Long productId, String level);

    void deleteByProductId(Long productId);
}
