package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.entity.v2_5.ProductClassificationFrequencyParams;
import com.nuoxin.virtual.rep.api.mybatis.ProductClassificationFrequencyMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.ProductClassificationFrequencyService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductClassificationFrequencyRequestBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author tiancun
 * @date 2019-01-04
 */
@Service
public class ProductClassificationFrequencyServiceImpl implements ProductClassificationFrequencyService {

    @Resource
    private ProductClassificationFrequencyMapper productClassificationFrequencyMapper;

    @Override
    public void add(ProductClassificationFrequencyRequestBean bean) {

        List<ProductClassificationFrequencyParams> list = this.getProductClassificationFrequencyParamsList(bean);

        productClassificationFrequencyMapper.batchInsert(list);

    }

    /**
     * 参数处理，用作新增
     * @param bean
     * @return
     */
    private List<ProductClassificationFrequencyParams> getProductClassificationFrequencyParamsList(ProductClassificationFrequencyRequestBean bean) {
        Long productId = bean.getProductId();
        Integer visitFrequency = bean.getVisitFrequency();
        List<Long> classificationIdList = bean.getClassificationIdList();
        List<Integer> potentialList = bean.getPotentialList();


        if (CollectionsUtil.isEmptyList(classificationIdList)){
            throw new BusinessException(ErrorEnum.ERROR, "医生分型不能为空");
        }

        if (CollectionsUtil.isEmptyList(potentialList)){
            throw new BusinessException(ErrorEnum.ERROR, "医生潜力不能为空");
        }

        List<ProductClassificationFrequencyParams> list = new ArrayList<>();
        List<Long> collectClassificationIdList = classificationIdList.stream().distinct().collect(Collectors.toList());
        List<Integer> collectPotentialList = potentialList.stream().distinct().collect(Collectors.toList());
        collectClassificationIdList.forEach(c ->{
            collectPotentialList.forEach(p ->{
                ProductClassificationFrequencyParams params = new ProductClassificationFrequencyParams();
                params.setProductId(productId);
                params.setVisitFrequency(visitFrequency);
                params.setClassificationId(c);
                params.setPotential(p);
                list.add(params);
            });
        });


        return list;
    }
}
