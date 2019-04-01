package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据同步mapper
 * @author wujiang
 * @date 2019-03-25
 */
public interface ScheduledSyncMapper {

    /**
     * 根据创建时间获取大于该时间的角色list
     * @param createTime
     * @return list
     */
    List<RoleParams> getRoleListByCreateTime(@Param(value = "createTime") String createTime);

    /**
     * 根据更新时间获取大于该时间的角色list
     * @param updateTime
     * @return list
     */
    List<RoleParams> getRoleListByUpdateTime(@Param(value = "updateTime") String updateTime);

    /**
     * 根据创建时间获取大于该时间的销售与医生关系指标list
     * @param createTime
     * @return list
     */
    List<DrugUserDoctorQuateBean> getDrugUserDoctorQuateListByCreateTime(@Param(value = "createTime") String createTime);

    /**
     * 根据更新时间获取大于该时间的销售与医生关系指标list
     * @param updateTime
     * @return list
     */
    List<DrugUserDoctorQuateBean> getDrugUserDoctorQuateListByUpdateTime(@Param(value = "updateTime") String updateTime);

    /**
     * 根据创建时间获取大于该时间的销售与医生关系指标list
     * @param createTime
     * @return list
     */
    List<VirtualDoctorCallInfoMendBean> getVirtualDoctorCallInfoMendListByCreateTime(@Param(value = "createTime") String createTime);

    /**
     * 根据更新时间获取大于该时间的销售与医生关系指标list
     * @param updateTime
     * @return list
     */
    List<VirtualDoctorCallInfoMendBean> getVirtualDoctorCallInfoMendListByUpdateTime(@Param(value = "updateTime") String updateTime);

    /**
     * 根据创建时间获取大于该时间的销售代表给医生打电话list
     * @param createTime
     * @return list
     */
    List<VirtualDoctorCallInfoBean> getVirtualDoctorCallInfoListByCreateTime(@Param(value = "createTime") String createTime);

    /**
     * 根据更新时间获取大于该时间的销售代表给医生打电话list
     * @param updateTime
     * @return list
     */
    List<VirtualDoctorCallInfoBean> getVirtualDoctorCallInfoListByUpdateTime(@Param(value = "updateTime") String updateTime);

    /**
     * 根据创建时间获取大于该时间的医院list
     * @param prodId
     * @param createTime
     * @return list
     */
    List<HospitalBean> getHospitalListByCreateTime(@Param(value = "prodId") Integer prodId, @Param(value = "createTime") String createTime);

    /**
     * 根据更新时间获取大于该时间的医院list
     * @param prodId
     * @param updateTime
     * @return list
     */
    List<HospitalBean> getHospitalListByUpdateTime(@Param(value = "prodId") Integer prodId, @Param(value = "updateTime") String updateTime);


    /**
     * 根据创建时间获取大于该时间的诺和内部会议记录list
     * @param prodId
     * @param createTime
     * @return list
     */
    List<EnterpriseInternalMeetingBean> getEnterpriseInternalMeetingListByCreateTime(@Param(value = "prodId") Integer prodId,@Param(value = "createTime") String createTime);

    /**
     * 根据更新时间获取大于该时间的诺和内部会议记录list
     * @param prodId
     * @param updateTime
     * @return list
     */
    List<EnterpriseInternalMeetingBean> getEnterpriseInternalMeetingListByUpdateTime(@Param(value = "prodId") Integer prodId,@Param(value = "updateTime") String updateTime);

    /**
     * 根据创建时间获取大于该时间的医生参加诺和内部会议详情表list
     * @param prodId
     * @param createTime
     * @return list
     */
    List<EnterpriseMeetingAttendDetailsBean> getEnterpriseMeetingAttendDetailsListByCreateTime(@Param(value = "prodId") Integer prodId,@Param(value = "createTime") String createTime);

    /**
     * 根据更新时间获取大于该时间的医生参加诺和内部会议详情表list
     * @param prodId
     * @param updateTime
     * @return list
     */
    List<EnterpriseMeetingAttendDetailsBean> getEnterpriseMeetingAttendDetailsListByUpdateTime(@Param(value = "prodId") Integer prodId,@Param(value = "updateTime") String updateTime);

    /**
     * 根据创建时间获取大于该时间的医生详情表list
     * @param prodId
     * @param createTime
     * @return list
     */
    List<EnterpriseHcpBean> getEnterpriseHcpBeanListByCreateTime(@Param(value = "prodId") Integer prodId,@Param(value = "createTime") String createTime);

    /**
     * 根据更新时间获取大于该时间的医生详情表list
     * @param prodId
     * @param updateTime
     * @return list
     */
    List<EnterpriseHcpBean> getEnterpriseHcpBeanListByUpdateTime(@Param(value = "prodId") Integer prodId,@Param(value = "updateTime") String updateTime);

    /**
     * 根据创建时间获取大于该时间的角色用户映射表list
     * @param prodId
     * @param createTime
     * @return list
     */
    List<EnterpriseSaleRepBean> getEnterpriseSaleRepListByCreateTime(@Param(value = "prodId") Integer prodId,@Param(value = "createTime") String createTime);

    /**
     * 根据更新时间获取大于该时间的角色用户映射表list
     * @param prodId
     * @param updateTime
     * @return list
     */
    List<EnterpriseSaleRepBean> getEnterpriseSaleRepListByUpdateTime(@Param(value = "prodId") Integer prodId,@Param(value = "updateTime") String updateTime);

    /**
     * 根据创建时间获取大于该时间的代表-医生-产品关联表list
     * @param prodId
     * @param createTime
     * @return list
     */
    List<EnterpriseSaleRepProductHcpBean> getEnterpriseSaleRepProductHcpListByCreateTime(@Param(value = "prodId") Integer prodId,@Param(value = "createTime") String createTime);

    /**
     * 根据更新时间获取大于该时间的代表-医生-产品关联表list
     * @param prodId
     * @param updateTime
     * @return list
     */
    List<EnterpriseSaleRepProductHcpBean> getEnterpriseSaleRepProductHcpListByUpdateTime(@Param(value = "prodId") Integer prodId,@Param(value = "updateTime") String updateTime);

}
