package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.HcpService;
import com.nuoxin.virtual.rep.api.web.controller.request.hcp.HcpRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorBasicInfoResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.hcp.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.TreeMap;

/**
 * Create by tiancun on 2017/9/15
 */
@RestController
@RequestMapping(value = "/hcp")
@Api(value = "主数据医生接口")
public class HcpController extends BaseController {


    @Autowired
    private HcpService hcpService;


    @ApiOperation(value = "主数据医生基本信息")
    @GetMapping("/getHcpBaseInfo/{id}")
    public ResponseEntity<DefaultResponseBean<List<DoctorBasicInfoResponseBean>>> getHcpBaseInfo(@PathVariable(value = "id") Long hcpId) {

        //HcpBaseInfoResponseBean hcpBaseInfo = hcpService.getHcpBaseInfo(hcpId);
        List<DoctorBasicInfoResponseBean> hcpBaseInfo = hcpService.getHcpBaseInfo(hcpId);
        DefaultResponseBean<List<DoctorBasicInfoResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(hcpBaseInfo);
        return ResponseEntity.ok(responseBean);
    }



    @ApiOperation(value = "主数据医生社会影响力")
    @GetMapping("/getHcpSociety/{id}")
    public ResponseEntity<DefaultResponseBean<HcpSocietyResponseBean>> getHcpSociety(@PathVariable(value = "id") Long hcpId) {

        HcpSocietyResponseBean hcpSociety = hcpService.getHcpSociety(hcpId);
        DefaultResponseBean<HcpSocietyResponseBean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(hcpSociety);
        return ResponseEntity.ok(responseBean);
    }


    @ApiOperation(value = "主数据医生对话列表")
    @PostMapping("/getDialogList")
    public ResponseEntity<DefaultResponseBean<PageResponseBean<HcpDialogResponseBean>>> getDialogList(@RequestBody HcpRequestBean bean, HttpServletRequest request) {
        Long loginId = getLoginId(request);
        bean.setDrugUserId(loginId);

        PageResponseBean<HcpDialogResponseBean> dialogList = hcpService.getDialogList(bean);
        DefaultResponseBean<PageResponseBean<HcpDialogResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(dialogList);
        return ResponseEntity.ok(responseBean);
    }

    @ApiOperation(value = "主数据医生对话列表中关键词统计")
    @PostMapping("/getKeywordList")
    public ResponseEntity<DefaultResponseBean<KeywordListResponseBean>> getKeywordList(@RequestBody HcpRequestBean bean, HttpServletRequest request) {
        Long loginId = getLoginId(request);
        bean.setDrugUserId(loginId);

        KeywordListResponseBean keywordList = hcpService.getKeywordList(bean);
        DefaultResponseBean<KeywordListResponseBean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(keywordList);
        return ResponseEntity.ok(responseBean);
    }


    @ApiOperation(value = "主数据医生学术影响力雷达图")
    @GetMapping("/getHcpPentagon/{id}")
    public ResponseEntity<DefaultResponseBean<HcpPentagonResponseBean>> getHcpPentagon(@PathVariable(value = "id") Long hcpId) {

        HcpPentagonResponseBean hcpPentagon = hcpService.getHcpPentagon(hcpId);
        DefaultResponseBean<HcpPentagonResponseBean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(hcpPentagon);
        return ResponseEntity.ok(responseBean);
    }


    @ApiOperation(value = "主数据医生学术信息论文观点，合作专刊，合作作者")
    @GetMapping("/getHcpResearchInfo/{id}")
    public ResponseEntity<DefaultResponseBean<HcpResearchInfoResponseBean>> getHcpResearchInfo(@PathVariable(value = "id") Long hcpId) {

        HcpResearchInfoResponseBean hcpResearchInfo = hcpService.getHcpResearchInfo(hcpId);
        DefaultResponseBean<HcpResearchInfoResponseBean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(hcpResearchInfo);
        return ResponseEntity.ok(responseBean);
    }


    @ApiOperation(value = "主数据医生发表论文列表")
    @PostMapping("/getDocList")
    public ResponseEntity<DefaultResponseBean<PageResponseBean<HcpDocResponseBean>>> getDocList(@RequestBody HcpRequestBean bean, HttpServletRequest request) {
        Long loginId = getLoginId(request);
        bean.setDrugUserId(loginId);
        PageResponseBean<HcpDocResponseBean> docList = hcpService.getDocList(bean);
        DefaultResponseBean<PageResponseBean<HcpDocResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(docList);
        return ResponseEntity.ok(responseBean);
    }


    @ApiOperation(value = "主数据医生发表论文列表")
    @GetMapping("/getHcpDocTrend/{id}")
    public ResponseEntity<DefaultResponseBean<TreeMap<Integer, Integer>>> getHcpDocTrend(@PathVariable(value = "id") Long hcpId) {

        TreeMap<Integer, Integer> hcpDocTrend = hcpService.getHcpDocTrend(hcpId);
        DefaultResponseBean<TreeMap<Integer, Integer>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(hcpDocTrend);
        return ResponseEntity.ok(responseBean);
    }



}
