package com.nuoxin.virtual.rep.api.web.controller.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v3_0.CommonPoolService;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.CommonPoolRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.product.ProductResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.CommonPoolDoctorResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DoctorImportErrorResponse;
import com.nuoxin.virtual.rep.api.web.controller.v2_5.NewBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 我的客户相关接口
 * @author tiancun
 * @date 2019-05-06
 */
@RestController
@Api(value = "V3.0.1客户公共池")
@RequestMapping(value = "/common/pool")
public class CommonPoolController extends NewBaseController {

    @Resource
    private CommonPoolService commonPoolService;

    @Resource
    private CommonService commonService;


    @ApiOperation(value = "产品列表", notes = "产品列表")
    @GetMapping(value = "/product/list")
    public DefaultResponseBean<List<ProductResponseBean>> getProductList() {
        List<ProductResponseBean> productList = commonPoolService.getProductList();
        DefaultResponseBean<List<ProductResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(productList);
        return responseBean;
    }



    @ApiOperation(value = "列表", notes = "列表")
    @PostMapping(value = "/doctor/list")
    public DefaultResponseBean<PageResponseBean<CommonPoolDoctorResponse>> getDoctorPage(HttpServletRequest request, @RequestBody CommonPoolRequest bean) {
        DrugUser drugUser = this.getDrugUser(request);
        PageResponseBean<CommonPoolDoctorResponse> doctorPage = commonPoolService.getDoctorPage(drugUser, bean);
        DefaultResponseBean<PageResponseBean<CommonPoolDoctorResponse>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(doctorPage);
        return responseBean;
    }



    @ApiOperation(value = "导入医生，不同的sheet错误一块展示", notes = "导入医生，不同的sheet错误一块展示")
    @PostMapping(value = "/doctor/import")
    public DefaultResponseBean<DoctorImportErrorResponse> doctorImport(MultipartFile file) {

        DoctorImportErrorResponse error = commonService.doctorImport(file);
        DefaultResponseBean<DoctorImportErrorResponse> responseBean = new DefaultResponseBean<>();
        responseBean.setData(error);
        return responseBean;
    }


    @ApiOperation(value = "导入医生转代表，不同的sheet错误一块展示", notes = "导入医生转代表，不同的sheet错误一块展示")
    @PostMapping(value = "/drug/user/doctor/transfer")
    public DefaultResponseBean<DoctorImportErrorResponse> drugUserDoctorTransfer(MultipartFile file) {
        DoctorImportErrorResponse error = commonService.drugUserDoctorTransfer(file);
        DefaultResponseBean<DoctorImportErrorResponse> responseBean = new DefaultResponseBean<>();
        responseBean.setData(error);
        return responseBean;
    }


    @ApiOperation(value = "导入医生，不同的sheet错误分开展示", notes = "导入医生，不同的sheet错误分开展示")
    @PostMapping(value = "/doctor/import/map")
    public DefaultResponseBean<Map<String, DoctorImportErrorResponse>> doctorImportMap(MultipartFile file) {

        Map<String, DoctorImportErrorResponse> map = commonService.doctorImportMap(file);
        DefaultResponseBean<Map<String, DoctorImportErrorResponse>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(map);
        return responseBean;
    }


    @ApiOperation(value = "导入医生转代表，不同的sheet错误分开展示", notes = "导入医生转代表，不同的sheet错误分开展示")
    @PostMapping(value = "/drug/user/doctor/transfer/map")
    public DefaultResponseBean<Map<String, DoctorImportErrorResponse>> drugUserDoctorTransferMap(MultipartFile file) {
        Map<String, DoctorImportErrorResponse> map = commonService.drugUserDoctorTransferMap(file);
        DefaultResponseBean<Map<String, DoctorImportErrorResponse>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(map);
        return responseBean;
    }



    @ApiOperation(value = "导出医生", notes = "导出医生")
    @GetMapping(value = "/doctor/export")
    public void exportDoctorList(HttpServletResponse response, HttpServletRequest request) {
        CommonPoolRequest bean = this.getExportParams(request);
        commonPoolService.exportDoctorList(response, bean);
    }

    /**
     * 得到导出传入的参数
     * @param request
     * @return
     */
    private CommonPoolRequest getExportParams(HttpServletRequest request) {
        String recruitStr = request.getParameter("recruit");
        String coverStr = request.getParameter("cover");
        String targetStr = request.getParameter("target");
        String hasDrugStr = request.getParameter("hasDrug");
        String searchKeyword = request.getParameter("searchKeyword");
        String productIdStr = request.getParameter("productIdStr");
        String drugUserIdStr = request.getParameter("drugUserIdStr");
        String relationDrugUserStr = request.getParameter("relationDrugUser");
        String resultIdStr = request.getParameter("resultIdStr");


        Integer recruit = null;
        Integer cover = null;
        Integer target = null;
        Integer hasDrug = null;
        Integer relationDrugUser = null;
        List<Long> productIdList;
        List<Long> drugUserIdList;
        List<Long> resultIdList;

        if (StringUtil.isNotEmpty(recruitStr)){
            try {
                recruit = Integer.valueOf(recruitStr);
            }catch (Exception e){
                throw new BusinessException(ErrorEnum.ERROR, "是否招募输入不合法");
            }

        }

        if (StringUtil.isNotEmpty(targetStr)){
            try {
                target = Integer.valueOf(targetStr);
            }catch (Exception e){
                throw new BusinessException(ErrorEnum.ERROR, "是否目标输入不合法");
            }
        }

        if (StringUtil.isNotEmpty(coverStr)){
            try {
                cover = Integer.valueOf(coverStr);
            }catch (Exception e){
                throw new BusinessException(ErrorEnum.ERROR, "是否覆盖输入不合法");
            }
        }

        if (StringUtil.isNotEmpty(hasDrugStr)){

            try {
                hasDrug = Integer.valueOf(hasDrugStr);
            }catch (Exception e){
                throw new BusinessException(ErrorEnum.ERROR, "是否有药输入不合法");
            }
        }

        if (StringUtil.isNotEmpty(relationDrugUserStr)){

            try {
                relationDrugUser = Integer.valueOf(relationDrugUserStr);
            }catch (Exception e){
                throw new BusinessException(ErrorEnum.ERROR, "是否关联代表输入不合法");
            }
        }


        try {
            productIdList = StringUtil.getIdList(productIdStr);
        }catch (Exception e){
            throw new BusinessException(ErrorEnum.ERROR, "产品ID输入不合法，多个以逗号分开");
        }

        try {
            resultIdList = StringUtil.getIdList(resultIdStr);
        }catch (Exception e){
            throw new BusinessException(ErrorEnum.ERROR, "代表ID输入不合法，多个以逗号分开");
        }


        try {
            drugUserIdList = StringUtil.getIdList(drugUserIdStr);
        }catch (Exception e){
            throw new BusinessException(ErrorEnum.ERROR, "代表ID输入不合法，多个以逗号分开");
        }


        CommonPoolRequest commonPoolRequest = new CommonPoolRequest();
        commonPoolRequest.setRelationDrugUser(relationDrugUser);
        commonPoolRequest.setCover(cover);
        commonPoolRequest.setDrugUserIdList(drugUserIdList);
        commonPoolRequest.setHasDrug(hasDrug);
        commonPoolRequest.setProductIdList(productIdList);
        commonPoolRequest.setRecruit(recruit);
        commonPoolRequest.setSearchKeyword(searchKeyword);
        commonPoolRequest.setTarget(target);
        commonPoolRequest.setResultIdList(resultIdList);

        return commonPoolRequest;

    }




}
