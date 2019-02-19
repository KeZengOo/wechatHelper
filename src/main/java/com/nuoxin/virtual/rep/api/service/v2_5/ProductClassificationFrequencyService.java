package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.entity.v2_5.ProductClassificationFrequencyParams;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductClassificationFrequencyRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductClassificationFrequencyUpdateRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.TableHeader;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.PotentialClassificationResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductClassificationFrequencyResponseBean;

import java.util.List;

/**
 * 产品的不同分型的医生拜访频次设置
 * @author tiancun
 * @date 2019-01-04
 */
public interface ProductClassificationFrequencyService {

    /**
     * 得到新增医生潜力分型频次需要的列表
     * @param productId
     * @return
     */
    PotentialClassificationResponseBean getPotentialClassification(Long productId);


    /**
     * 新增
     * @param bean
     */
    void add(ProductClassificationFrequencyRequestBean bean);

    /**
     * 根据批次删除
     * @param batchNo
     */
    void deleteByBatchNo(String batchNo);


    /**
     * 查询产品下分型拜访频次
     * @param productId
     * @return
     */
    List<ProductClassificationFrequencyResponseBean> getProductClassificationFrequencyList(Long productId);



    /**
     * 更细
     * @param bean
     */
    void update(ProductClassificationFrequencyUpdateRequestBean bean);


    /**
     * 得到标题
     * @param productId
     * @return
     */
    List<TableHeader> getTableHeaderList(Long productId);
}
