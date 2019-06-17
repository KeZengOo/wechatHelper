package com.nuoxin.virtual.rep.api.web.controller.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.CoverageReportRequest;
import com.nuoxin.virtual.rep.api.service.v3_0.CoverageReportService;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.VisitDataRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.VisitDataResponse;
import com.nuoxin.virtual.rep.api.web.controller.v2_5.NewBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName CoverageReportController
 * @Description 拜访数据汇总-覆盖月报相关接口
 * @Author dangjunhui
 * @Date 2019/6/14 15:59
 * @Version 1.0
 */
@Slf4j
@Api(value = "V3.1.0拜访数据汇总-覆盖月报相关接口")
@RestController
@RequestMapping("/coverage")
public class CoverageReportController extends NewBaseController implements Serializable {

    private static final long serialVersionUID = 2065078175471895137L;

    @Resource
    private CoverageReportService coverageReportService;

    @ApiOperation(value = "覆盖情况总览", notes = "覆盖情况总览")
    @PostMapping("/overview")
    public DefaultResponseBean<PageResponseBean<VisitDataResponse>> overview(@RequestBody CoverageReportRequest request) throws Exception {
        String startTime = request.getStartTime();
        String endTime = request.getEndTime();
        Long productId = request.getProductId();
        if(productId == null) {
            throw new Exception("参数不合法!");
        }
        if(StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            throw new Exception("参数不合法!");
        }

        DefaultResponseBean<PageResponseBean<VisitDataResponse>> result = new DefaultResponseBean<>();
        result.setData(null);
        return result;
    }

}
