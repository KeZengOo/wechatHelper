package com.nuoxin.virtual.rep.api.web.controller.v2_5.set;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.service.v2_5.HolidayService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.HolidayRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductClassificationFrequencyRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.HolidayResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tiancun
 * @date 2019-01-08
 */
@RestController
@Api(value = "V2.5节假日设置")
@RequestMapping(value = "/holiday")
public class HolidayController {

    @Resource
    private HolidayService holidayService;

    @ApiOperation(value = "节假日列表", notes = "节假日列表")
    @GetMapping(value = "/list")
    public DefaultResponseBean<List<HolidayResponseBean>> list() {
        List<HolidayResponseBean> holidayList = holidayService.getHolidayList();
        DefaultResponseBean<List<HolidayResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(holidayList);
        return responseBean;
    }


    @ApiOperation(value = "节假日新增", notes = "节假日新增")
    @PostMapping(value = "/add")
    public DefaultResponseBean<Boolean> add(@RequestBody HolidayRequestBean bean) {
        holidayService.add(bean);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);
        return responseBean;
    }


    @ApiOperation(value = "节假日删除", notes = "节假日删除")
    @GetMapping(value = "/delete/{batchNo}")
    public DefaultResponseBean<Boolean> delete(@PathVariable(value = "batchNo") String batchNo) {
        holidayService.deleteByBatchNo(batchNo);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);
        return responseBean;
    }

    @ApiOperation(value = "节假日修改", notes = "节假日修改")
    @PostMapping(value = "/update")
    public DefaultResponseBean<Boolean> update(@RequestBody HolidayRequestBean bean) {
        holidayService.update(bean);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);
        return responseBean;
    }
}
