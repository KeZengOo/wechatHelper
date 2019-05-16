package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v3_0.VisitDataBase;
import com.nuoxin.virtual.rep.api.mybatis.VisitingDataMapper;
import com.nuoxin.virtual.rep.api.service.v3_0.VisitingDataService;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.VisitDataRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.VisitDataResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName VisitingDataServiceImpl
 * @Description 拜访数据Service实现类
 * @Author dangjunhui
 * @Date 2019/5/14 11:28
 * @Version 1.0
 */
@Service
public class VisitingDataServiceImpl implements VisitingDataService, Serializable {

    @Resource
    private VisitingDataMapper visitingDataMapper;

    @Override
    public PageResponseBean<List<VisitDataResponse>> getVisitDataByPage(String leaderPath, VisitDataRequest request) {

        int total = visitingDataMapper.getVisitDataCount(leaderPath, request);

        PageResponseBean<List<VisitDataResponse>> result = null;

        if(total > 0) {
            List<VisitDataBase> list = visitingDataMapper.getVisitDataBaseInfo(leaderPath, request);
            // 产品对应的销售代表
            Map<Long, List<VisitDataBase>> map = list.stream().collect(Collectors.groupingBy(k -> k.getProId()));

            map.forEach((k, v) -> {


            });

            List<VisitDataResponse> rlist = new ArrayList<>(list.size());
            list.forEach(k -> {
                VisitDataResponse bean = new VisitDataResponse();
                bean.setRepId(k.getUserId());
                bean.setProduct(k.getProductName());
                bean.setRepName(k.getUserName());
                bean.setRepType(k.getSaleType());
                bean.setRepVisitWay(k.getVisitWay());


                rlist.add(bean);
            });
            result = new PageResponseBean(request, total, rlist);
        }

        return result;
    }

}
