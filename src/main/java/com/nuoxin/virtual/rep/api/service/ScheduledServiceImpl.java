package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.constant.TimeCronConstant;
import com.nuoxin.virtual.rep.api.entity.ProductLine;
import com.nuoxin.virtual.rep.api.entity.v2_5.EnterpriseProductParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.RoleParams;
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
    @Scheduled(cron = TimeCronConstant.PRODUCT_CRON)
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

}
