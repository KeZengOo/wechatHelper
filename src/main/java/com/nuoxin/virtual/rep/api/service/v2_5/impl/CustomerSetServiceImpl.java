package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.nuoxin.virtual.rep.api.mybatis.DynamicFieldMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CustomerSetService;
import com.nuoxin.virtual.rep.api.web.controller.response.customer.DoctorDynamicFieldResponseBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tiancun
 * @date 2018-09-12
 */
@Service
public class CustomerSetServiceImpl implements CustomerSetService {

    @Resource
    private DynamicFieldMapper dynamicFieldMapper;

    @Override
    public List<DoctorDynamicFieldResponseBean> getBasicAndHospitalFieldList() {
        List<DoctorDynamicFieldResponseBean> basicAndHospitalFieldList = dynamicFieldMapper.getBasicAndHospitalFieldList();
        return basicAndHospitalFieldList;
    }
}
