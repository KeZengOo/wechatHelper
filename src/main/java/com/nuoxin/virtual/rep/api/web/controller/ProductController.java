package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.service.ProductLineService;
import com.nuoxin.virtual.rep.api.web.controller.request.product.ProductRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.product.ProductResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    @PostMapping("/getList")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<List<ProductResponseBean>>> getList(@RequestParam(value = "drugUserId", required = false) Long drugUserId, HttpServletRequest request){
        if (drugUserId == null || drugUserId == 0){
            drugUserId = getLoginId(request);
        }

        List<ProductResponseBean> list = productLineService.getList(drugUserId);

        DefaultResponseBean<List<ProductResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(list);

        return ResponseEntity.ok(responseBean);

    }


}
