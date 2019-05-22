package com.nuoxin.virtual.rep.api.web.controller.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.v3_0.VisitingDataService;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DrugUserDoctorCallRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.VisitDataRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.VisitDataResponse;
import com.nuoxin.virtual.rep.api.web.controller.v2_5.NewBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
        Date startTime = request.getStartTime();
        Date endTime = request.getEndTime();
        List<Long> proIds = request.getProductIdList();
        if(CollectionUtils.isEmpty(proIds)) {
            throw new Exception("参数不合法!");
        }
        if(startTime == null || endTime == null) {
            throw new Exception("参数不合法!");
        }

        PageResponseBean<VisitDataResponse> list = visitingDataService.getVisitDataByPage(leaderPath, request);
        DefaultResponseBean<PageResponseBean<VisitDataResponse>> result = new DefaultResponseBean<>();
        result.setData(list);
        return result;
    }

    @ApiOperation(value = "拜访数据汇总查询列表导出")
    @GetMapping(value = "/list/export")
    public void getVisitDataSummaryList(HttpServletRequest request) throws Exception {
        DrugUser drugUser = this.getDrugUser();
        String leaderPath = drugUser.getLeaderPath();
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Date startDate;
        Date endDate;
        if(StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            throw new Exception("参数不合法!");
        } else {
            try {
                startDate = DateUtils.parseDate(startTime, "yyyy-MM-dd");
                endDate = DateUtils.parseDate(endTime, "yyyy-MM-dd");
                // TODO 增加日期范围验证

            } catch (Exception e) {
                throw new Exception("参数不合法!");
            }
        }
        String pIds = request.getParameter("productIdList");
        if(StringUtils.isBlank(pIds)) {
            throw new Exception("参数不合法!");
        }
        String[] proIds = pIds.split(",");
        List<Long> productIdList = new ArrayList<>(1);
        for(String s : proIds) {
            productIdList.add(Long.parseLong(s));
        }
        VisitDataRequest req = new VisitDataRequest();
        req.setStartTime(startDate);
        req.setEndTime(endDate);
        req.setProductIdList(productIdList);
        String drugIds = request.getParameter("drugUserIdList");
        if(StringUtils.isNotBlank(drugIds)) {
            List<Long> drugUserIdList = new ArrayList<>(1);
            String[] str = drugIds.split(",");
            for(String s : str) {
                drugUserIdList.add(Long.parseLong(s));
            }
            req.setDrugUserIdList(drugUserIdList);
        }
        visitingDataService.exportVisitDataSummary(getResponse(), leaderPath, req);
    }

}
