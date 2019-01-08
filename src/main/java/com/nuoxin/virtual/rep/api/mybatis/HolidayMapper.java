package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.HolidayRarams;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.HolidayResponseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tiancun
 * @date 2019-01-08
 */
public interface HolidayMapper {

    /**
     * 批量新增
     * @param list
     */
    void batchInsert(List<HolidayRarams> list);

    /**
     * 得到设置的法定节假日
     * @return
     */
    List<HolidayResponseBean> getHolidayList();

    /**
     * 根据名称删除
     * @param batchNo
     */
    void deleteByBatchNo(@Param(value = "batchNo") String batchNo);

}
