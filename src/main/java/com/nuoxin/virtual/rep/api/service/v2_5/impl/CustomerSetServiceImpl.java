package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.mybatis.DynamicFieldMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CustomerSetService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.DoctorDynamicFieldRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.customer.DoctorDynamicFieldResponseBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户设置相关业务方法
 * @author tiancun
 * @date 2018-09-12
 */
@Service
public class CustomerSetServiceImpl implements CustomerSetService {

    @Resource
    private DynamicFieldMapper dynamicFieldMapper;

    @Override
    public List<DoctorDynamicFieldResponseBean> getBasicAndHospitalFieldList() {
        List<DoctorDynamicFieldResponseBean> dynamicFieldList = dynamicFieldMapper.getDoctorDynamicFieldList(null);
        return dynamicFieldList;
    }

    @Override
    public List<DoctorDynamicFieldResponseBean> getProductFieldList(Long productId) {
        if (productId == null || productId <=0){
            throw new BusinessException(ErrorEnum.ERROR.getStatus(),"产品id 不能为空");
        }
        DoctorDynamicFieldRequestBean bean = new DoctorDynamicFieldRequestBean();
        bean.setProductId(productId);
        List<DoctorDynamicFieldResponseBean> dynamicFieldList = dynamicFieldMapper.getDoctorDynamicFieldList(bean);
        return dynamicFieldList;
    }

    @Override
    public void updateDoctorDynamicField(DoctorDynamicFieldRequestBean bean) {
        Long id = bean.getId();
        if (id == null || id <=0){
            throw new BusinessException(ErrorEnum.ERROR.getStatus(),"修改id 不能为空");
        }

        dynamicFieldMapper.updateDoctorDynamicField(bean);

    }

    @Override
    public void deleteDoctorDynamicField(Long id) {
        if (id == null || id <=0){
            throw new BusinessException(ErrorEnum.ERROR.getStatus(),"删除id无效");
        }

        dynamicFieldMapper.deleteDoctorDynamicField(id);
    }

    @Override
    public Long insertDoctorDynamicField(DoctorDynamicFieldRequestBean bean) {

        dynamicFieldMapper.insertDoctorDynamicField(bean);
        return bean.getId();
    }

    @Override
    public Long insertProductDoctorDynamicField(DoctorDynamicFieldRequestBean bean) {
        Long productId = bean.getProductId();
        if (productId == null || productId <= 0){
            throw new BusinessException(ErrorEnum.ERROR.getStatus(),"产品id 不能为空");
        }

        dynamicFieldMapper.insertDoctorDynamicField(bean);
        return bean.getId();
    }
}
