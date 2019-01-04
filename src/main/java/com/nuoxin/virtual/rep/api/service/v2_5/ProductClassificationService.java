package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.questionnaire.ProductQuestionnaireRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductClassificationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductClassificationResponseBean;

import java.util.List;

/** 产品下医生分型设置
 * @author tiancun
 * @date 2019-01-03
 */
public interface ProductClassificationService {

    /**
     * 新增
     * @param bean
     */
    void add(ProductClassificationRequestBean bean);

    /**
     * 更新
     * @param bean
     */
    void update(ProductClassificationRequestBean bean);

    /**
     * 根据产品删除
     * @param productId
     */
    void deleteByProductId(Long productId);

    /**
     * 查询产品下的医生分型
     * @param productId
     * @return
     */
    List<ProductClassificationResponseBean> getProductClassificationList(Long productId);
}
