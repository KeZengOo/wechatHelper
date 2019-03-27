package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.constant.TimeCronConstant;
import com.nuoxin.virtual.rep.api.entity.ProductLine;
import com.nuoxin.virtual.rep.api.entity.v2_5.*;
import com.nuoxin.virtual.rep.api.mybatis.ProductLineMapper;
import com.nuoxin.virtual.rep.api.mybatis.ScheduledSyncMapper;
import com.nuoxin.virtual.rep.api.slave.mybatis.ProductSlaveMapper;
import com.nuoxin.virtual.rep.api.slave.mybatis.ScheduledSyncSlaveMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * 两个库同步的定时任务类（营销中心库和虚拟代表版HCP360库）
 * 创建任务类
 */
@Slf4j
@Component
@Service
public class ScheduledServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledServiceImpl.class);

    @Resource
    private ProductSlaveMapper productSlaveMapper;
    @Resource
    private ProductLineMapper productLineMapper;
    @Resource
    private ScheduledSyncMapper scheduledSyncMapper;
    @Resource
    private ScheduledSyncSlaveMapper scheduledSyncSlaveMapper;


    /**
     * 产品同步
     */
//    @Scheduled(cron = TimeCronConstant.PRODUCT_CRON)
    public void productSync(){
        //------------------------------------------------同步插入--------------------------------------------------
        //获取从库产品表的最新数据时间
        String createTime = productSlaveMapper.getProductNewCreateTime();
        if(createTime == null)
        {
            //如果从库为空的话便默认一个时间
            createTime = "2000-01-01 00:00:00";
        }
        logger.info("获取从库的产品最新数据时间："+ createTime);
        //根据从库产品表最新数据时间获取主库产品表最新数据list
        List<ProductLine> productLineList = productLineMapper.getProductListByCreateTime(createTime);
        logger.info("根据从库产品表最新数据时间获取主库产品表最新数据list："+ productLineList);

        if(productLineList.size() >  0){
            //把主库最新的产品数据插入从库的产品表中
            boolean result = productSlaveMapper.syncProductList(productLineList);
            logger.info("把主库最新的产品数据插入从库的产品表中："+ result);
        }

        //------------------------------------------------同步更新--------------------------------------------------
        //获取从库产品表的最新数据时间
        String updateTime = productSlaveMapper.getProductNewCreateTime();
        //根据更新时间获取大于该时间的产品list
        List<ProductLine> productListByUpdateTimeList = productLineMapper.getProductListByUpdateTime(updateTime);
        if(productListByUpdateTimeList.size() > 0){
            productListByUpdateTimeList.forEach(updateProduct -> {
                boolean updateResult = productSlaveMapper.syncUpdateProductList(updateProduct.getName(),updateProduct.getDesc(),updateProduct.getId().toString());
                logger.info("把主库更新后的产品数据更新到从库的产品表中："+ updateResult);
            });
        }
    }


    /**
     * 角色同步
     */
    @Scheduled(cron = TimeCronConstant.ROLE_CRON)
    public void roleSync(){
        //------------------------------------------------同步插入--------------------------------------------------
        //获取从库角色表的最新数据时间
        String createTime = scheduledSyncSlaveMapper.getRoleNewCreateTime();
        if(createTime == null)
        {
            //如果从库为空的话便默认一个时间
            createTime = "2000-01-01 00:00:00";
        }
        logger.info("获取从库的角色最新数据时间："+ createTime);
        //根据从库产品表最新数据时间获取主库产品表最新数据list
        List<RoleParams> roleParamsList = scheduledSyncMapper.getRoleListByCreateTime(createTime);
        logger.info("根据从库角色表最新数据时间获取主库角色表最新数据list："+ roleParamsList);

        if(roleParamsList.size() >  0){
            //把主库最新的产品数据插入从库的产品表中
            boolean result = scheduledSyncSlaveMapper.syncRoleList(roleParamsList);
            logger.info("把主库最新的角色数据插入从库的角色表中："+ result);
        }

        //------------------------------------------------同步更新--------------------------------------------------
        //获取从库角色表的最新更新数据时间
        String updateTime = scheduledSyncSlaveMapper.getRoleNewUpdateTime();
        //根据更新时间获取大于该时间的产品list
        List<RoleParams> roleListByUpdateTimeList = scheduledSyncMapper.getRoleListByUpdateTime(updateTime);
        if(roleListByUpdateTimeList.size() > 0){
            roleListByUpdateTimeList.forEach(updateRole -> {
                boolean updateResult = scheduledSyncSlaveMapper.syncUpdateRoleList(updateRole.getId(),updateRole.getRoleName(),updateRole.getCreateDate(),updateRole.getUpdateDate());
                logger.info("把主库更新后的角色数据更新到从库的角色表中："+ updateResult);
            });
        }
    }

    /**
     * 销售与医生关系指标表同步 drug_user_doctor_quate
     */
    @Scheduled(cron = TimeCronConstant.DRUG_USER_DOCTOR_QUATE_CRON)
    public void DrugUserDoctorQuateSync(){
        //------------------------------------------------同步插入--------------------------------------------------
        //获取从库角色表的最新数据时间
        String createTime = scheduledSyncSlaveMapper.getDrugUserDoctorQuateNewCreateTime();
        if(createTime == null)
        {
            //如果从库为空的话便默认一个时间
            createTime = "2000-01-01 00:00:00";
        }
        logger.info("获取从库的销售与医生关系指标最新数据时间："+ createTime);
        //根据从库产品表最新数据时间获取主库产品表最新数据list
        List<DrugUserDoctorQuateBean> drugUserDoctorQuateParamsList = scheduledSyncMapper.getDrugUserDoctorQuateListByCreateTime(createTime);
        logger.info("根据从库销售与医生关系指标最新数据时间获取主库销售与医生关系指标表最新数据list："+ drugUserDoctorQuateParamsList);

        if(drugUserDoctorQuateParamsList.size() >  0){
            //把主库最新的产品数据插入从库的产品表中
            boolean result = scheduledSyncSlaveMapper.syncDrugUserDoctorQuateList(drugUserDoctorQuateParamsList);
            logger.info("把主库最新的销售与医生关系指标数据插入从库的销售与医生关系指标表中："+ result);
        }

        //------------------------------------------------同步更新--------------------------------------------------
        //获取从库角色表的最新更新数据时间
        String updateTime = scheduledSyncSlaveMapper.getDrugUserDoctorQuateNewUpdateTime();
        //根据更新时间获取大于该时间的产品list
        List<DrugUserDoctorQuateBean> drugUserDoctorQuateListByUpdateTimeList = scheduledSyncMapper.getDrugUserDoctorQuateListByUpdateTime(updateTime);
        if(drugUserDoctorQuateListByUpdateTimeList.size() > 0){
            drugUserDoctorQuateListByUpdateTimeList.forEach(updateList -> {
                boolean updateResult = scheduledSyncSlaveMapper.syncUpdateDrugUserDoctorQuateList(updateList);
                logger.info("把主库更新后的销售与医生关系指标数据更新到从库的销售与医生关系指标表中："+ updateResult);
            });
        }
    }

    /**
     * 电话拜访扩展表同步 virtual_doctor_call_info_mend
     */
    @Scheduled(cron = TimeCronConstant.VIRTUAL_DOCTOR_CALL_INFO_MEND_CRON)
    public void VirtualDoctorCallInfoMendSync(){
        //------------------------------------------------同步插入--------------------------------------------------
        //获取从库角色表的最新数据时间
        String createTime = scheduledSyncSlaveMapper.getVirtualDoctorCallInfoMendNewCreateTime();
        if(createTime == null)
        {
            //如果从库为空的话便默认一个时间
            createTime = "2000-01-01 00:00:00";
        }
        logger.info("获取从库的电话拜访扩展最新数据时间："+ createTime);
        //根据从库产品表最新数据时间获取主库产品表最新数据list
        List<VirtualDoctorCallInfoMendBean> virtualDoctorCallInfoMendList = scheduledSyncMapper.getVirtualDoctorCallInfoMendListByCreateTime(createTime);
        logger.info("根据从库电话拜访扩展最新数据时间获取主库电话拜访扩展表最新数据list："+ virtualDoctorCallInfoMendList);

        if(virtualDoctorCallInfoMendList.size() >  0){
            //把主库最新的产品数据插入从库的产品表中
            boolean result = scheduledSyncSlaveMapper.syncVirtualDoctorCallInfoMendList(virtualDoctorCallInfoMendList);
            logger.info("把主库最新的电话拜访扩展数据插入从库的电话拜访扩展表中："+ result);
        }

        //------------------------------------------------同步更新--------------------------------------------------
        //获取从库角色表的最新更新数据时间
        String updateTime = scheduledSyncSlaveMapper.getVirtualDoctorCallInfoMendNewUpdateTime();
        //根据更新时间获取大于该时间的产品list
        List<VirtualDoctorCallInfoMendBean> virtualDoctorCallInfoMendListByUpdateTimeList = scheduledSyncMapper.getVirtualDoctorCallInfoMendListByUpdateTime(updateTime);
        if(virtualDoctorCallInfoMendListByUpdateTimeList.size() > 0){
            virtualDoctorCallInfoMendListByUpdateTimeList.forEach(updateList -> {
                boolean updateResult = scheduledSyncSlaveMapper.syncUpdateVirtualDoctorCallInfoMendList(updateList);
                logger.info("把主库更新后的电话拜访扩展数据更新到从库的电话拜访扩展表中："+ updateResult);
            });
        }
    }

    /**
     * 销售代表给医生打电话表同步 virtual_doctor_call_info
     */
    @Scheduled(cron = TimeCronConstant.VIRTUAL_DOCTOR_CALL_INFO_CRON)
    public void VirtualDoctorCallInfoSync(){
        //------------------------------------------------同步插入--------------------------------------------------
        //获取从库角色表的最新数据时间
        String createTime = scheduledSyncSlaveMapper.getVirtualDoctorCallInfoNewCreateTime();
        if(createTime == null)
        {
            //如果从库为空的话便默认一个时间
            createTime = "2000-01-01 00:00:00";
        }
        logger.info("获取从库的销售代表给医生打电话最新数据时间："+ createTime);
        //根据从库产品表最新数据时间获取主库产品表最新数据list
        List<VirtualDoctorCallInfoBean> virtualDoctorCallInfoList = scheduledSyncMapper.getVirtualDoctorCallInfoListByCreateTime(createTime);
        logger.info("根据从库销售代表给医生打电话最新数据时间获取主库销售代表给医生打电话最新数据list："+ virtualDoctorCallInfoList);

        if(virtualDoctorCallInfoList.size() >  0){
            //把主库最新的产品数据插入从库的产品表中
            boolean result = scheduledSyncSlaveMapper.syncVirtualDoctorCallInfoList(virtualDoctorCallInfoList);
            logger.info("把主库最新的销售代表给医生打电话数据插入从库的销售代表给医生打电话表中："+ result);
        }

        //------------------------------------------------同步更新--------------------------------------------------
        //获取从库角色表的最新更新数据时间
        String updateTime = scheduledSyncSlaveMapper.getVirtualDoctorCallInfoNewUpdateTime();
        //根据更新时间获取大于该时间的产品list
        List<VirtualDoctorCallInfoBean> virtualDoctorCallInfoListByUpdateTimeList = scheduledSyncMapper.getVirtualDoctorCallInfoListByUpdateTime(updateTime);
        if(virtualDoctorCallInfoListByUpdateTimeList.size() > 0){
            virtualDoctorCallInfoListByUpdateTimeList.forEach(updateList -> {
                boolean updateResult = scheduledSyncSlaveMapper.syncUpdateVirtualDoctorCallInfoList(updateList);
                logger.info("把主库更新后的销售代表给医生打电话数据更新到从库的销售代表给医生打电话表中："+ updateResult);
            });
        }
    }

    /**
     * 医院表同步 hospital---->enterprise_hci
     */
    @Scheduled(cron = TimeCronConstant.ENTERPRISE_HCI_CRON)
    public void enterpriseHciSync(){
        Integer productId = 150;
        //------------------------------------------------同步插入--------------------------------------------------
        //获取从库角色表的最新数据时间
        String createTime = scheduledSyncSlaveMapper.getEnterpriseHciCreateTime();
        if(createTime == null)
        {
            //如果从库为空的话便默认一个时间
            createTime = "2000-01-01 00:00:00";
        }
        logger.info("获取从库的医院最新数据时间："+ createTime);
        //根据从库产品表最新数据时间获取主库产品表最新数据list
        List<HospitalBean> hospitalList = scheduledSyncMapper.getHospitalListByCreateTime(productId,createTime);
        logger.info("根据从库医院最新数据时间获取主库医院最新数据list："+ hospitalList);

        if(hospitalList.size() >  0){
            //把主库最新的产品数据插入从库的产品表中
            boolean result = scheduledSyncSlaveMapper.syncEnterpriseHciList(hospitalList);
            logger.info("把主库最新的医院数据插入从库的医院表中："+ result);
        }

        //------------------------------------------------同步更新--------------------------------------------------
        //获取从库角色表的最新更新数据时间
        String updateTime = scheduledSyncSlaveMapper.getEnterpriseHciUpdateTime();
        //根据更新时间获取大于该时间的产品list
        List<HospitalBean> hospitalListByUpdateTimeList = scheduledSyncMapper.getHospitalListByUpdateTime(productId,updateTime);
        if(hospitalListByUpdateTimeList.size() > 0){
            hospitalListByUpdateTimeList.forEach(updateList -> {
                boolean updateResult = scheduledSyncSlaveMapper.syncUpdateEnterpriseHciList(updateList);
                logger.info("把主库更新后的医院数据更新到从库的医院表中："+ updateResult);
            });
        }
    }

}
