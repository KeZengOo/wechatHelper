package com.nuoxin.virtual.rep.api.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

}
