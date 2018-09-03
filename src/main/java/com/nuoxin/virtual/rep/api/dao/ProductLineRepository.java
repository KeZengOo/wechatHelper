package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Create by tiancun on 2017/10/16
 */
public interface ProductLineRepository extends JpaRepository<ProductLine, Long>,JpaSpecificationExecutor<ProductLine> {
}
