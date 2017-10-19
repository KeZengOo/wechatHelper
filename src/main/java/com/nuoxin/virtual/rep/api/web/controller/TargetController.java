package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;

import com.nuoxin.virtual.rep.api.common.controller.BaseController;

import com.nuoxin.virtual.rep.api.service.CoveredTargetService;
import com.nuoxin.virtual.rep.api.service.FollowUpTypeService;
import com.nuoxin.virtual.rep.api.service.TargetService;

import com.nuoxin.virtual.rep.api.web.controller.request.target.FollowUpSetRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.target.MonthCoverTargetSetRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.target.MonthWorkTargetSetRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.target.FollowUpResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.target.MonthCoverTargetResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.target.MonthWorkTargetResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.OneMonthNoFollowCustomerResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Create by tiancun on 2017/10/18
 */
@Api(value = "目标设置相关接口")
public class TargetController extends BaseController{

    @Autowired
    private FollowUpTypeService followUpTypeService;

    @Autowired
    private CoveredTargetService coveredTargetService;

    @Autowired
    private TargetService targetService;


    @ApiOperation(value = "新增或者修改跟进类型接口", notes = "新增或者修改跟进类型接口")
    @PostMapping("/addOrUpdateFollowUp")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<Boolean>>  getOneMonthNoFollowCustomers(@RequestBody FollowUpSetRequestBean bean, HttpServletRequest request){


        Boolean flag = followUpTypeService.addFollowUp(bean);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(flag);
        return ResponseEntity.ok(responseBean);

    }


    @ApiOperation(value = "跟进类型列表接口", notes = "跟进类型列表接口")
    @PostMapping("/getFollowUpList")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<List<FollowUpResponseBean>>>  getFollowUpList(@RequestBody FollowUpSetRequestBean bean, HttpServletRequest request){


        List<FollowUpResponseBean> followUpList = followUpTypeService.getFollowUpList(bean);
        DefaultResponseBean<List<FollowUpResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(followUpList);
        return ResponseEntity.ok(responseBean);

    }



    @ApiOperation(value = "新增或者修改月覆盖目标接口", notes = "新增或者修改月覆盖目标接口")
    @PostMapping("/addOrUpdateMonthCover")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<Boolean>>  addOrUpdateMonthCover(@RequestBody List<MonthCoverTargetSetRequestBean> list, @RequestParam(value = "productId", required = true) Long productId, HttpServletRequest request){


        Boolean flag = coveredTargetService.add(list, productId);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(flag);
        return ResponseEntity.ok(responseBean);

    }


    @ApiOperation(value = "月覆盖目标列表接口", notes = "月覆盖目标列表接口")
    @GetMapping("/monthCoverList/{id}")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<List<MonthCoverTargetResponseBean>>>  getFollowUpList(@PathVariable(value = "id") Long productId, HttpServletRequest request){


        List<MonthCoverTargetResponseBean> monthTargetList = coveredTargetService.getMonthTargetList(productId);
        DefaultResponseBean<List<MonthCoverTargetResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(monthTargetList);
        return ResponseEntity.ok(responseBean);

    }


    @ApiOperation(value = "新增或者修改月工作量目标接口", notes = "新增或者修改月工作量目标接口")
    @PostMapping("/addOrUpdateMonthWorkMonth")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<Boolean>>  addOrUpdateMonthWorkMonth(@RequestBody List<MonthWorkTargetSetRequestBean> list, @RequestParam(value = "productId", required = true) Long productId, HttpServletRequest request){


        Boolean flag = targetService.add(list, productId);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(flag);
        return ResponseEntity.ok(responseBean);

    }


    @ApiOperation(value = "月工作量目标列表接口", notes = "月工作量目标列表接口")
    @GetMapping("/getMonthWorkTargetList/{id}")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<List<MonthWorkTargetResponseBean>>>  getMonthWorkTargetList(@PathVariable(value = "id") Long productId, HttpServletRequest request){


        List<MonthWorkTargetResponseBean> targetList = targetService.getMonthWorkTargetList(productId);
        DefaultResponseBean<List<MonthWorkTargetResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(targetList);
        return ResponseEntity.ok(responseBean);

    }


}
