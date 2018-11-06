package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.entity.Doctor;

/**
 * 新本的医生业务相关接口
 * @author tiancun
 * @date 2018-11-06
 */
public interface NewDoctorService {

    /**
     * 根据手机号查询医生
     * @param mobile
     * @return
     */
    Doctor findFirstByMobile(String mobile);


}
