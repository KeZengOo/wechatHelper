package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.service.FollowUpTypeService;
import com.nuoxin.virtual.rep.api.service.TargetService;
import com.nuoxin.virtual.rep.api.web.controller.request.WorkStationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.target.FollowUpSetRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.target.FollowUpResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.OneMonthNoFollowCustomerResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Create by tiancun on 2017/10/18
 */
@Api(value = "目标设置相关接口")
public class TargetController extends BaseController{

    @Autowired
    private FollowUpTypeService followUpTypeService;


    @ApiOperation(value = "新增或者修改跟进类型接口", notes = "新增或者修改跟进类型接口")
    @PostMapping("/addFollowUp")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<Boolean>>  getOneMonthNoFollowCustomers(@RequestBody FollowUpSetRequestBean bean, HttpServletRequest request){


        Boolean flag = followUpTypeService.addFollowUp(bean);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(flag);
        return ResponseEntity.ok(responseBean);

    }


    @ApiOperation(value = "跟进类型列表接口", notes = "跟进类型列表接口")
    @PostMapping("/addFollowUp")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<List<FollowUpResponseBean>>>  getFollowUpList(@RequestBody FollowUpSetRequestBean bean, HttpServletRequest request){


        List<FollowUpResponseBean> followUpList = followUpTypeService.getFollowUpList(bean);
        DefaultResponseBean<List<FollowUpResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(followUpList);
        return ResponseEntity.ok(responseBean);

    }


}
