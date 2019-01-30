package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.enums.DynamicFieldTypeEnum;
import com.nuoxin.virtual.rep.api.enums.ExtendDynamicFieldTypeEnum;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserMapper;
import com.nuoxin.virtual.rep.api.mybatis.DynamicFieldMapper;
import com.nuoxin.virtual.rep.api.mybatis.VirtualProductVisitResultMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CustomerSetService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.DoctorDynamicFieldRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.DynamicFieldProductRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.customer.DoctorDynamicFieldResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DynamicFieldProductResponseBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户设置相关业务方法
 * @author tiancun
 * @date 2018-09-12
 */
@Service
public class CustomerSetServiceImpl implements CustomerSetService {

    @Resource
    private DynamicFieldMapper dynamicFieldMapper;

    @Resource
    private DrugUserMapper drugUserMapper;

    @Resource
    private VirtualProductVisitResultMapper virtualProductVisitResultMapper;

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

        Integer extendType = bean.getExtendType();
        if (extendType !=null && (extendType == ExtendDynamicFieldTypeEnum.POTENTIAL.getType() || extendType == ExtendDynamicFieldTypeEnum.CLASSIFICATION.getType())){
            if (extendType == ExtendDynamicFieldTypeEnum.POTENTIAL.getType()){
                Integer type = bean.getType();
                if (type != DynamicFieldTypeEnum.RADIO.getType() && type != DynamicFieldTypeEnum.SINGLE.getType()){
                    throw new BusinessException(ErrorEnum.ERROR, "潜力字段必须为单选！");
                }
            }

            Long extendId = dynamicFieldMapper.getProductExtendTypeField(bean);
            if (extendId != null){
                if (id != extendId){
                    throw new BusinessException(ErrorEnum.ERROR.getStatus(), ExtendDynamicFieldTypeEnum.getNameByType(bean.getExtendType()) +  " 字段已经存在！");
                }
            }
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

        Integer extendType = bean.getExtendType();
        if (extendType != null && extendType !=0){
            if (extendType == ExtendDynamicFieldTypeEnum.POTENTIAL.getType()){
                Integer type = bean.getType();
                if (type != DynamicFieldTypeEnum.RADIO.getType() && type != DynamicFieldTypeEnum.SINGLE.getType()){
                    throw new BusinessException(ErrorEnum.ERROR, "潜力字段必须为单选！");
                }
            }

            Long id = dynamicFieldMapper.getProductExtendTypeField(bean);
            if (id !=null && id > 0){
                throw new BusinessException(ErrorEnum.ERROR.getStatus(), ExtendDynamicFieldTypeEnum.getNameByType(bean.getExtendType()) +  " 字段已经存在！");
            }
        }

        dynamicFieldMapper.insertDoctorDynamicField(bean);
        return bean.getId();
    }

    @Override
    public PageResponseBean<DynamicFieldProductResponseBean> getDynamicFieldProductPage(DrugUser user, DynamicFieldProductRequestBean bean) {
        bean.setLeaderPath(drugUserMapper.getLeaderPathById(user.getId()));
        Integer page = bean.getPage();
        Integer pageSize = bean.getPageSize();
        bean.setCurrentSize(page  * pageSize);
        PageResponseBean<DynamicFieldProductResponseBean> pageEmpty = new PageResponseBean<>();
        Integer dynamicFieldProductListCount = dynamicFieldMapper.getDynamicFieldProductListCount(bean);
        if (dynamicFieldProductListCount != null && dynamicFieldProductListCount > 0){
            List<DynamicFieldProductResponseBean> dynamicFieldProductList = dynamicFieldMapper.getDynamicFieldProductList(bean);
            if(!CollectionsUtil.isEmptyList(dynamicFieldProductList)) {
                //提取产品id列表
                List<Long> productIds = dynamicFieldProductList.stream().map(DynamicFieldProductResponseBean::getProductId).collect(Collectors.toList());
                //那些产品初始化过数据
                List<Long> ps = virtualProductVisitResultMapper.selectProductVisitResult(productIds);
                for (DynamicFieldProductResponseBean x : dynamicFieldProductList) {
                    //默认没初始化
                    x.setVisitStatus(2);
                    if (!CollectionsUtil.isEmptyList(ps)) {
                        for (Long x1 : ps) {
                            //找到数据则认为是已经初始化的数据
                            if (x.getProductId().equals(x1)) {
                                x.setVisitStatus(1);
                                break;
                            }
                        }
                    }
                }
            }
            pageEmpty = new PageResponseBean<>(bean, dynamicFieldProductListCount, dynamicFieldProductList);
        }
        return pageEmpty;
        }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void copyDynamicFieldByProductId(DrugUser user, Long oldProductId, Long newProductId) {
        if (oldProductId == null || newProductId == null){
            throw new BusinessException(ErrorEnum.ERROR.getStatus(), "复制的产品ID或者被复制的产品ID不能为空");
        }

        if (oldProductId.equals(newProductId)){
            throw new BusinessException(ErrorEnum.ERROR.getStatus(), "复制的产品ID和被复制的产品ID不能相同");
        }

        dynamicFieldMapper.deleteByProductId(newProductId);
        dynamicFieldMapper.copyByProductId(user.getId(), user.getName(), oldProductId, newProductId);
    }

    @Override
    public Integer getProductDynamicFieldCount(Long productId) {
        Integer fieldCount = dynamicFieldMapper.getProductDynamicFieldCount(productId);
        if (fieldCount == null){
            fieldCount = 0;
        }

        return fieldCount;
    }
}
