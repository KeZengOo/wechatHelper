package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.v2_5.ShareService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.DoctorDynamicFieldValueListRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.DoctorQuestionnaireDetailRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share.ShareRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share.ShareStatusRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.ContentShareResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DoctorBasicDynamicFieldValueListResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DynamicFieldQuestionDetailResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.ProductDynamicFieldQuestionnaireResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author tiancun
 * @date 2018-09-17
 */
@RestController
@Api(value = "V2.5医生详情内容推送记录")
@RequestMapping(value = "/share")
public class ActivityShareController extends NewBaseController{

    @Resource
    private ShareService shareService;

    @SuppressWarnings("unchecked")
	@ApiOperation(value = "列表", notes = "列表")
    @PostMapping(value = "/list")
    public DefaultResponseBean<PageResponseBean<ContentShareResponseBean>> getContentShareList(HttpServletRequest request, @RequestBody ShareRequestBean bean) {
        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }

        PageResponseBean<ContentShareResponseBean> contentShareList = shareService.getContentShareList(user, bean);
        DefaultResponseBean<PageResponseBean<ContentShareResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(contentShareList);

        return responseBean;
    }


    @SuppressWarnings("unchecked")
    @ApiOperation(value = "更新分享状态", notes = "更新分享状态")
    @PostMapping(value = "/status/update")
    public DefaultResponseBean<Boolean> saveOrUpdateShareStatus(HttpServletRequest request, @RequestBody ShareStatusRequestBean bean) {
        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }

        shareService.saveOrUpdateShareStatus(bean);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);

        return responseBean;
    }

}
