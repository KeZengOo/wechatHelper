package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.entity.v2_5.HolidayRarams;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.HolidayRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.HolidayResponseBean;


import java.util.List;

/**
 * 节假日设置
 * @author tiancun
 * @date 2019-01-08
 */
public interface HolidayService {


    /**
     * 新增
     * @param bean
     */
    void add(HolidayRequestBean bean);

    /**
     * 修改
     * @param bean
     */
    void update(HolidayRequestBean bean);

    /**
     * 得到设置的法定节假日
     * @return
     */
    List<HolidayResponseBean> getHolidayList();

    /**
     * 根据名称删除
     * @param batchNo
     */
    void deleteByBatchNo(String batchNo);
}
