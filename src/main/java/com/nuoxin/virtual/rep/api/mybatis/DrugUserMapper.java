package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.common.util.MyMapper;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.web.controller.request.QueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorResponseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by fenggang on 10/18/17.
 */
public interface DrugUserMapper extends MyMapper<DrugUser> {

    List<DoctorResponseBean> doctorPage(QueryRequestBean bean);
    Integer doctorPageCount(QueryRequestBean bean);

    List<DrugUserResponseBean> relationDrugUser(@Param("leaderPath") String leaderPath,@Param("productId") Long productId);
}
