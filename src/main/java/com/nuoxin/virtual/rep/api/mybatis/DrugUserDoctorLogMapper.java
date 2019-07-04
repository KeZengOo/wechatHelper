package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.DrugUserDoctorLogParams;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DrugUserDoctorLogPageRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DrugUserDoctorLogResponse;

import java.util.List;

/**
 * 代表转移医生日志表操作相关
 * @author tiancun
 * @date 2019-07-04
 */
public interface DrugUserDoctorLogMapper {


    /**
     * 单个新增
     * @param params
     */
    void insert(DrugUserDoctorLogParams params);


    /**
     * 批量插入
     * @param list
     */
    void batchInsert(List<DrugUserDoctorLogParams>  list);


    /**
     * 得到单个医生转移记录
     * @param request
     * @return
     */
    List<DrugUserDoctorLogResponse> getSingleDoctorTransferLogList(DrugUserDoctorLogPageRequest request);


    /**
     * 得到单个医生转移记录总数
     * @param request
     * @return
     */
    Integer getSingleDoctorTransferLogListCount(DrugUserDoctorLogPageRequest request);


}
