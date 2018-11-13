package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.ProductVisitResultParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.ProductVisitResultResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
  * 产品与拜访结果表 Mapper 接口
 * </p>
 *
 * @author lichengxin
 * @since 2018-10-30
 */
@Component
public interface VirtualProductVisitResultMapper {

    /**
     * 批量新增产品与拜访结果
     * @param list
     * @return
     */
    int batchInsert(List<ProductVisitResultParams> list);

    /**
     * 根据产品查询拜访结果列表
     * @param productId
     * @return
     */
    List<ProductVisitResultResponse> selectVisitResultList(Long productId);

    /**
     * 根据产品查询拜访结果列表
     * @param id
     * @return
     */
    ProductVisitResultResponse selectVisitResultById(Long id);

    /**
     * 根据产品查询拜访结果列表
     * @param productId
     * @return
     */
    Integer checkVisitResult(@Param("productId") Long productId,@Param("visitResult") String visitResult);

    /**
     * 根据产品查询拜访结果列表
     * @param productIds
     * @return
     */
    List<Long> selectProductVisitResult(@Param("productIds")List<Long> productIds);

    /**
     * 返回修改条数
     * @param params
     * @return
     */
    int update(ProductVisitResultParams params);
}