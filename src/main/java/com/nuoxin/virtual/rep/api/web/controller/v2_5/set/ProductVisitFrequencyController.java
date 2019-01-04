package com.nuoxin.virtual.rep.api.web.controller.v2_5.set;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.v2_5.ProductClassificationFrequencyService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductClassificationFrequencyRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share.ShareRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.ContentShareResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author tiancun
 * @date 2019-01-04
 */
@RestController
@Api(value = "V2.5医生拜访频次设置")
@RequestMapping(value = "/product/frequency")
public class ProductVisitFrequencyController {

    @Resource
    private ProductClassificationFrequencyService productClassificationFrequencyService;


    @ApiOperation(value = "医生分型拜访频次设置", notes = "医生分型拜访频次设置")
    @PostMapping(value = "/classification")
    public DefaultResponseBean<Boolean> add(@RequestBody ProductClassificationFrequencyRequestBean bean) {

        productClassificationFrequencyService.add(bean);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);
        return responseBean;
    }

}
