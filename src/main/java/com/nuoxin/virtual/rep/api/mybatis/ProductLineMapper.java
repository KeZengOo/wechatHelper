package com.nuoxin.backend.dao;

import com.nuoxin.backend.common.util.MyMapper;
import com.nuoxin.backend.entity.ProductLine;

import java.util.List;

/**
 * Created by fenggang on 8/4/17.
 */
public interface ProductLineMapper extends MyMapper<ProductLine> {

    List<ProductLine> findByLeaderPath(String leaderPath);

    List<Long> getProductIds(String leaderPath);
}
