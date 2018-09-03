package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.DropTarget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Create by tiancun on 2017/10/20
 */
public interface DropTargetRepository extends JpaRepository<DropTarget,Long>,JpaSpecificationExecutor<DropTarget> {

    void deleteByProductId(Long productId);

    DropTarget findFirstByProductIdAndLevel(Long productId,String level);

    List<DropTarget> findByProductId(Long productId);

}
