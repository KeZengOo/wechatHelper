package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.UpdateVirtualDoctorRequest;
import org.apache.ibatis.annotations.Param;

/**
 * 每次拜访修改的医生的固定字段
 * @author tiancun
 * @date 2018-12-27
 */
public interface VirtualCallDoctorFiexFieldMapper {

    /**
     * 如果存在删除掉
     * @param callId
     */
    void deleteByCallId(@Param(value = "callId") Long callId);

    /**
     * 新增
     * @param bean
     */
    void add(UpdateVirtualDoctorRequest bean);

}
