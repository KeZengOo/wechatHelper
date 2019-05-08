package com.nuoxin.virtual.rep.api.web.controller.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v3_0.CommonPoolService;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.CommonPoolRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.CommonPoolDoctorResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DoctorImportErrorResponse;
import com.nuoxin.virtual.rep.api.web.controller.v2_5.NewBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @ApiOperation(value = "列表", notes = "列表")
    @PostMapping(value = "/doctor/list")
    public DefaultResponseBean<PageResponseBean<CommonPoolDoctorResponse>> getDoctorPage(HttpServletRequest request, @RequestBody CommonPoolRequest bean) {
        DrugUser drugUser = this.getDrugUser(request);
        PageResponseBean<CommonPoolDoctorResponse> doctorPage = commonPoolService.getDoctorPage(drugUser, bean);
        DefaultResponseBean<PageResponseBean<CommonPoolDoctorResponse>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(doctorPage);
        return responseBean;
    }



    @ApiOperation(value = "导入医生", notes = "导入医生")
    @PostMapping(value = "/doctor/import")
    public DefaultResponseBean<Map<String, DoctorImportErrorResponse>> doctorImport(MultipartFile file) {

        Map<String, DoctorImportErrorResponse> map = commonService.doctorImport(file);
        DefaultResponseBean<Map<String, DoctorImportErrorResponse>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(map);
        return responseBean;
    }


    @ApiOperation(value = "导入医生转代表", notes = "导入医生转代表")
    @PostMapping(value = "/drug/user/doctor/transfer")
    public DefaultResponseBean<Map<String, DoctorImportErrorResponse>> drugUserDoctorTransfer(MultipartFile file) {
        Map<String, DoctorImportErrorResponse> map = commonService.drugUserDoctorTransfer(file);
        DefaultResponseBean<Map<String, DoctorImportErrorResponse>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(map);
        return responseBean;
    }



    @ApiOperation(value = "导出医生", notes = "导出医生")
    @PostMapping(value = "/doctor/export")
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

        Integer recruit;
        Integer cover;
        Integer target;
        Integer hasDrug;
        Integer relationDrugUser;

        if (StringUtil.isNotEmpty(recruitStr)){
            recruit = Integer.valueOf(recruitStr);
        }

        if (StringUtil.isNotEmpty(targetStr)){
            target = Integer.valueOf(targetStr);
        }

        if (StringUtil.isNotEmpty(coverStr)){
            cover = Integer.valueOf(coverStr);
        }

        if (StringUtil.isNotEmpty(hasDrugStr)){
            hasDrug = Integer.valueOf(hasDrugStr);
        }

        if (StringUtil.isNotEmpty(relationDrugUserStr)){
            relationDrugUser = Integer.valueOf(relationDrugUserStr);
        }


        return null;

    }

}
