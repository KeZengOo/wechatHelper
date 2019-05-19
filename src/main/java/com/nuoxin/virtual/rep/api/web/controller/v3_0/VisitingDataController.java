package com.nuoxin.virtual.rep.api.web.controller.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.v3_0.VisitingDataService;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
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
import java.util.List;

/**
 * @ClassName VisitingDataController
 * @Description 拜访数据汇总Controller
 * @Author dangjunhui
 * @Date 2019/5/13 16:59
 * @Version 1.0
 */
@Slf4j
@Api(value = "V3.0.1拜访数据汇总相关接口")
@RestController
@RequestMapping("/visit")
public class VisitingDataController extends NewBaseController implements Serializable {

    private static final long serialVersionUID = 6360295635377672540L;

    @Resource
    private VisitingDataService visitingDataService;

    @ApiOperation(value = "列表", notes = "列表")
    @PostMapping("/summary/list")
    public DefaultResponseBean<PageResponseBean<VisitDataResponse>> visitDataSummaryList(@RequestBody VisitDataRequest request) throws Exception {
        DrugUser drugUser = this.getDrugUser();
        String leaderPath = drugUser.getLeaderPath();
        String startTime = request.getStartTime();
        String endTime = request.getEndTime();
        List<Long> proIds = request.getProductIdList();
        if(CollectionUtils.isEmpty(proIds)) {
            throw new Exception("参数不合法!");
        }
        if(StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            throw new Exception("参数不合法!");
        }

        PageResponseBean<VisitDataResponse> list = visitingDataService.getVisitDataByPage(leaderPath, request);
        DefaultResponseBean<PageResponseBean<VisitDataResponse>> result = new DefaultResponseBean<>();
        result.setData(list);
        return result;
    }

}
