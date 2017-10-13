package com.nuoxin.virtual.rep.api.mybatis;



import com.nuoxin.virtual.rep.api.common.util.MyMapper;
import com.nuoxin.virtual.rep.api.entity.ProductLine;

import java.util.List;

/**
 * Created by fenggang on 8/4/17.
 */
public interface ProductLineMapper extends MyMapper<ProductLine> {

    List<ProductLine> findByLeaderPath(String leaderPath);

    List<Long> getProductIds(String leaderPath);
}
