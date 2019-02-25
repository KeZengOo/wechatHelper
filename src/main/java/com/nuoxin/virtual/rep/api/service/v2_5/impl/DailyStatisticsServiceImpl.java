package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.nuoxin.virtual.rep.api.mybatis.ProductTargetMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.DailyStatisticsService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.statistics.DailyStatisticsRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.statistics.DailyStatisticsResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.statistics.ProductTargetResponseBean;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 日报统计接口相关实现
 * @author tiancun
 * @date 2019-02-25
 */
@Service
public class DailyStatisticsServiceImpl implements DailyStatisticsService {


    @Resource
    private ProductTargetMapper productTargetMapper;


    @Override
    public DailyStatisticsResponseBean getDailyStatistics(DailyStatisticsRequestBean bean) {

        Integer targetHospital = 0;
        Integer targetDoctor = 0;

        Long productId = bean.getProductId();
        ProductTargetResponseBean productTarget = productTargetMapper.getProductTarget(productId);
        if (productTarget != null){
            Integer findTargetDoctor = productTarget.getTargetDoctor();
            Integer findTargetHospital = productTarget.getTargetHospital();
            if (findTargetDoctor != null){
                targetDoctor = findTargetDoctor;
            }

            if (findTargetHospital != null){
                targetHospital = findTargetHospital;
            }
        }

        // 招募医生数




        return null;
    }
}
