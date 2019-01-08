package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.entity.v2_5.HolidayRarams;
import com.nuoxin.virtual.rep.api.mybatis.HolidayMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.HolidayService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.HolidayRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.HolidayResponseBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author tiancun
 * @date 2019-01-08
 */
@Service
public class HolidayServiceImpl implements HolidayService {

    @Resource
    private HolidayMapper holidayMapper;

    @Override
    public void add(HolidayRequestBean bean) {
        List<HolidayRarams> params = this.getHolidayParams(bean);
        if (CollectionsUtil.isNotEmptyList(params)){
            holidayMapper.batchInsert(params);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(HolidayRequestBean bean) {
        String batchNo = bean.getBatchNo();
        this.deleteByBatchNo(batchNo);
        this.add(bean);
    }


    private List<HolidayRarams> getHolidayParams(HolidayRequestBean bean) {

        String name = bean.getName();
        Date startDate = bean.getStartDate();
        Integer days = bean.getDays();
        String batchNo = UUID.randomUUID().toString();
        if (days == null || days <=0){
            throw new BusinessException(ErrorEnum.ERROR, "不合法的天数");

        }

        List<HolidayRarams> list = new ArrayList<>();
        for (int i = 0; i < days; i++){
            if (i == 0){
                HolidayRarams holidayRarams = new HolidayRarams();
                holidayRarams.setBatchNo(batchNo);
                holidayRarams.setHolidayDate(startDate);
                holidayRarams.setName(name);
                list.add(holidayRarams);
            }else {
                HolidayRarams holidayRarams = new HolidayRarams();
                holidayRarams.setBatchNo(batchNo);
                holidayRarams.setHolidayDate(DateUtil.addDay(startDate, i));
                holidayRarams.setName(name);
                list.add(holidayRarams);
            }
        }


        return list;

    }

    @Override
    public List<HolidayResponseBean> getHolidayList() {
        List<HolidayResponseBean> holidayList = holidayMapper.getHolidayList();
        if (CollectionsUtil.isNotEmptyList(holidayList)){
            return holidayList;
        }

        return new ArrayList<>();
    }

    @Override
    public void deleteByBatchNo(String batchNo) {

        holidayMapper.deleteByBatchNo(batchNo);

    }
}
