package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.ProductClassificationFrequencyParams;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductClassificationFrequencyResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductClassificationResponseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产品下医生分型拜访频次设置
 * @author tiancun
 * @date 2019-01-04
 */
public interface ProductClassificationFrequencyMapper {



    /**
     * 批量新增
     * @param list
     */
    void batchInsert(@Param(value = "list") List<ProductClassificationFrequencyParams> list);

    /**
     * 查询总数，用来校验某个潜力的分型是否已经录入
     * @param classificationIdList
     * @param potential
     */
    Integer getClassificationCountByPotential(@Param(value = "classificationIdList") List<Long> classificationIdList,
                                              @Param(value = "potential") Integer potential);


    /**
     * 得到产品的分型拜访频次
     * @param productId
     * @return
     */
    List<ProductClassificationFrequencyResponseBean> getProductClassificationFrequencyList(@Param(value = "productId") Long productId);

    /**
     * 根据批次ID删除
     * @param batchNo
     */
    void deleteByBatchNo(@Param(value = "batchNo") String batchNo);


    /**
     * 根据批次查询选中的分型
     * @param batchNoList
     * @return
     */
    List<ProductClassificationResponseBean> getProductClassificationListByBatchNo(@Param(value = "batchNoList") List<String> batchNoList);

}
