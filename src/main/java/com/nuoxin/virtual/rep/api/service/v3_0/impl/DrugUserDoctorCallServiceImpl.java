package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.mybatis.VirtualDoctorCallInfoMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v3_0.DrugUserDoctorCallService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DrugUserDoctorCallRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DrugUserDoctorCallResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 电话拜访记录查询相关业务接口实现
 * @author tiancun
 * @date 2019-05-10
 */
@Service
public class DrugUserDoctorCallServiceImpl implements DrugUserDoctorCallService {

    @Resource
    private VirtualDoctorCallInfoMapper virtualDoctorCallInfoMapper;

    @Resource
    private CommonService commonService;


    @Override
    public PageResponseBean<DrugUserDoctorCallResponse> getDrugUserDoctorCallPage(DrugUserDoctorCallRequest request) {

        Integer count = virtualDoctorCallInfoMapper.getDrugUserDoctorCallListCount(request);
        if (count == null){
            count = 0;
        }

        List<DrugUserDoctorCallResponse> drugUserDoctorCallList = null;
        if (count > 0){
            drugUserDoctorCallList = virtualDoctorCallInfoMapper.getDrugUserDoctorCallList(request);
        }

        if (CollectionsUtil.isNotEmptyList(drugUserDoctorCallList)){
            drugUserDoctorCallList.forEach(c->{
                Long totalCallSecond = c.getTotalCallSecond();
                Long lastCallSecond = c.getLastCallSecond();
                Date lastVisitDate = c.getLastVisitDate();
                Integer connectCallCount = c.getConnectCallCount();
                Integer callCount = c.getCallCount();
                String lastVisitTime = "";
                String connectRate = "";
                String totalCallTime = commonService.alterCallTimeContent(totalCallSecond);
                String lastCallTime = commonService.alterCallTimeContent(lastCallSecond);
                if (lastVisitDate !=null){
                    long visitTimeDelta = System.currentTimeMillis() - lastVisitDate.getTime();
                    lastVisitTime = commonService.alterLastVisitTimeContent(visitTimeDelta);
                }

                c.setTotalCallTime(totalCallTime);
                c.setLastCallTime(lastCallTime);
                c.setLastVisitTime(lastVisitTime);

                if (callCount !=null && callCount > 0){
                    if (connectCallCount == null || connectCallCount == 0){
                        connectRate = "0%";
                    }else {

                    }
                    double v = (double) (connectCallCount * 100) / callCount;
                    String s = String.format("%.2f", v).toString();
                    connectRate = s.concat("%");
                }

                c.setConnectRate(connectRate);

            });
        }


        return new PageResponseBean<>(request, count, drugUserDoctorCallList);

    }
}
