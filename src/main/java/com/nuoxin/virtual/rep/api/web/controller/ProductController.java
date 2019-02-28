package com.nuoxin.virtual.rep.api.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualWechatDateReturn;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualWechatVisitCountAndCycleConfigParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.service.ProductLineService;
import com.nuoxin.virtual.rep.api.web.controller.response.product.ProductResponseBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Create by tiancun on 2017/10/17
 */
@Api(value = "产品相关接口")
@RequestMapping(value = "/product")
@RestController
public class ProductController extends BaseController{

    @Autowired
    private ProductLineService productLineService;

    @ApiOperation(value = "产品列表接口", notes = "产品列表接口")
    @GetMapping("/getList")
    @ResponseBody
	public ResponseEntity<DefaultResponseBean<List<ProductResponseBean>>> getList(
			@RequestParam(value = "drugUserId", required = false) Long drugUserId,@RequestParam(value = "doctorId", required = false) Long doctorId, HttpServletRequest request) {
        if (drugUserId == null || drugUserId == 0){
            drugUserId = getLoginId(request);
        }

        DefaultResponseBean<List<ProductResponseBean>> responseBean = new DefaultResponseBean<>();
        List<ProductResponseBean> list = productLineService.getList(drugUserId, doctorId);
        responseBean.setData(list);

        return ResponseEntity.ok(responseBean);
    }

    /**
     * 在客户聊天记录中，当前代表在过去N天内是否与医生有微信聊天记录
     * @param drugUserId
     * @param doctorId
     * @return 1为在配置天数内有微信聊天记录， 0为没有
     */
    @ApiOperation(value = "当前代表在过去N天内是否与医生有微信聊天记录", notes = "当前代表在过去N天内是否与医生有微信聊天记录")
    @GetMapping("/wechatChatRecordIsExist")
    public ResponseEntity<DefaultResponseBean<Integer>> wechatChatRecordIsExist(@RequestParam(value = "drugUserId", required = false) Long drugUserId,@RequestParam(value = "doctorId", required = false) Long doctorId,HttpServletRequest request){
        if (drugUserId == null || drugUserId == 0){
            drugUserId = getLoginId(request);
        }
        VirtualWechatVisitCountAndCycleConfigParams vwvc = new VirtualWechatVisitCountAndCycleConfigParams();
        //获取数据库中的配置信息
        //微信聊天记录天数和一天内可添加微信拜访记录的配置表
        vwvc = productLineService.virtualWechatVisitCountAndCycleConfig();
        int existCount = 0;
        existCount = productLineService.wechatChatRecordIsExist(drugUserId,doctorId,vwvc.getCycleCount());
        DefaultResponseBean<Integer> responseBean = new DefaultResponseBean<>();
        if(existCount > 0){
            existCount = 1;
        }
        else
        {
            responseBean.setMessage("系统检测到"+vwvc.getCycleCount()+"天内，没有可添加微信拜访的微信聊天记录，请使用微信聊天上传工具上传聊天内容");
        }
        responseBean.setData(existCount);
        return ResponseEntity.ok(responseBean);
    }

    /**
     * 每天添加微信拜访条数是否已上限
     * @param drugUserId
     * @param doctorId
     * @param request
     * @return 1为已上限，0为可以继续添加拜访记录
     */
    @ApiOperation(value = "每天添加微信拜访条数是否已上限", notes = "每天添加微信拜访条数是否已上限")
    @GetMapping("/wechatVisitLogCountOneDay")
    public ResponseEntity<DefaultResponseBean<Integer>> wechatVisitLogCountOneDay(@RequestParam(value = "drugUserId", required = false) Long drugUserId,@RequestParam(value = "doctorId", required = false) Long doctorId, HttpServletRequest request){
        if (drugUserId == null || drugUserId == 0){
            drugUserId = getLoginId(request);
        }

        //一天内微信拜访记录存在的条数
        int existCount = 0;
        existCount = productLineService.wechatVisitLogCountOneDay(drugUserId,doctorId);

        VirtualWechatVisitCountAndCycleConfigParams vwvc = new VirtualWechatVisitCountAndCycleConfigParams();
        //获取数据库中的配置信息
        //微信聊天记录天数和一天内可添加微信拜访记录的配置表
        vwvc = productLineService.virtualWechatVisitCountAndCycleConfig();
        DefaultResponseBean<Integer> responseBean = new DefaultResponseBean<>();
        //如果配置的限制条数大于当前日期的存在条数，返回0，说明可以继续添加微信拜访记录
        if(vwvc.getAddCount() <= existCount)
        {
            existCount = 1;
            responseBean.setMessage("每天添加条数已上限，不能添加更多微信拜访登记!");
        }
        else
        {
            existCount = 0;
        }
        responseBean.setData(existCount);
        return ResponseEntity.ok(responseBean);
    }

    /**
     * 获取N天内的所有日期，有微信聊天记录的日期，有微信拜访的日期
     * @param drugUserId
     * @param doctorId
     * @return String
     */
    @ApiOperation(value = "获取N天内的所有日期，有微信聊天记录的日期，有微信拜访的日期", notes = "获取N天内的所有日期，有微信聊天记录的日期，有微信拜访的日期")
    @GetMapping("/wechatIsExistDateList")
    public ResponseEntity<DefaultResponseBean<VirtualWechatDateReturn>> wechatIsExistDateList(@RequestParam(value = "drugUserId", required = false) Long drugUserId,@RequestParam(value = "doctorId", required = false) Long doctorId, @RequestParam(value = "productId", required = false) Integer productId, HttpServletRequest request){
        if (drugUserId == null || drugUserId == 0){
            drugUserId = getLoginId(request);
        }

        VirtualWechatVisitCountAndCycleConfigParams vwvc = new VirtualWechatVisitCountAndCycleConfigParams();
        //获取数据库中的配置信息
        //微信聊天记录天数和一天内可添加微信拜访记录的配置表
        vwvc = productLineService.virtualWechatVisitCountAndCycleConfig();
        //微信拜访记录或消息存在的时间返回类
        VirtualWechatDateReturn virtualWechatDateReturn = new VirtualWechatDateReturn();
        Map<String, Integer> result = productLineService.wechatIsExistDateList(drugUserId,doctorId,productId,vwvc.getCycleCount());
        virtualWechatDateReturn.setEvents(result);
        virtualWechatDateReturn.setDayCount(vwvc.getCycleCount());
        DefaultResponseBean<VirtualWechatDateReturn> responseBean = new DefaultResponseBean<>();
        responseBean.setData(virtualWechatDateReturn);
        return ResponseEntity.ok(responseBean);
    }
}
