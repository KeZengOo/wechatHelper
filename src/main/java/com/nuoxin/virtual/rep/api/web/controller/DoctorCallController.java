package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.service.DoctorCallService;
import com.nuoxin.virtual.rep.api.web.controller.request.QueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallHistoryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallInfoRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallHistoryResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallStatResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fenggang on 9/11/17.
 */
@Api(value = "",description = "电话页面接口")
@RestController
@RequestMapping(value = "/call")
public class DoctorCallController extends BaseController {

    @Autowired
    private DoctorCallService doctorCallService;

    @ApiOperation(value = "客户电话搜索列表", notes = "客户电话搜索列表")
    @PostMapping("/doctor/page")
    public DefaultResponseBean<PageResponseBean<CallResponseBean>> doctorPage(@RequestBody QueryRequestBean bean,
                                                                              HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        bean.setDrugUserId(super.getLoginId(request));
        responseBean.setData(doctorCallService.doctorPage(bean));
        return responseBean;
    }

    @ApiOperation(value = "客户电话历史记录", notes = "客户电话历史记录")
    @PostMapping("/doctor/history/page")
    public DefaultResponseBean<PageResponseBean<CallHistoryResponseBean>> doctorHistoryPage(@RequestBody CallHistoryRequestBean bean,
                                                                                            HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        bean.setDrugUserId(super.getLoginId(request));
        responseBean.setData(doctorCallService.doctorHistoryPage(bean));
        return responseBean;
    }

    @ApiOperation(value = "电话顶部统计", notes = "电话顶部统计")
    @PostMapping("/stat")
    public DefaultResponseBean<CallStatResponseBean> stat(HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        responseBean.setData(doctorCallService.stat(super.getLoginId(request)));
        return responseBean;
    }

    @ApiOperation(value = "拨号保存电话记录", notes = "拨号保存电话记录")
    @PostMapping("/save")
    public DefaultResponseBean<CallRequestBean> save(@RequestBody CallRequestBean bean,
                                    HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        bean.setDrugUserId(super.getLoginId(request));
        responseBean.setData(doctorCallService.save(bean));
        return responseBean;
    }

    @ApiOperation(value = "拨号电话记录修改", notes = "拨号电话记录修改")
    @PostMapping("/update")
    public DefaultResponseBean<CallRequestBean> update(@RequestBody CallRequestBean bean,
                                                     HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        Long id = bean.getId();
        if(id==null){
            responseBean.setCode(300);
            responseBean.setMessage("请求参数id不能为空");
            return responseBean;
        }
        bean.setDrugUserId(super.getLoginId(request));
        bean = doctorCallService.save(bean);
        if(bean.getId()==null){
            responseBean.setCode(300);
            responseBean.setMessage("状态更新失败");
        }
        responseBean.setData(bean);

        return responseBean;
    }

    @ApiOperation(value = "挂断保存电话记录", notes = "挂断保存电话记录")
    @PostMapping("/stop/update")
    public DefaultResponseBean<Boolean> stopSave(@RequestBody CallInfoRequestBean bean,
                                        HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        if(bean.getId()==null){
            responseBean.setCode(300);
            responseBean.setMessage("请求参数id不能为空");
            return responseBean;
        }
        bean.setDrugUserId(super.getLoginId(request));
        responseBean.setData(doctorCallService.stopUpdate(bean));
        return responseBean;
    }

}
