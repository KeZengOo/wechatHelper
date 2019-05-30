package com.nuoxin.virtual.rep.api.web.controller.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentReadLogsParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentSharingParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.FilePathParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.ContentReadLogsRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.ContentSharingRequest;
import com.nuoxin.virtual.rep.api.service.v3_0.ContentSharingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 内容分享记录查询查询
 * @author wujiang
 * @date 20190505
 */
@Api(value = "V3_0 内容分享记录查询查询相关接口")
@RequestMapping(value = "/contentSharingApi")
@RestController
public class ContentSharingController extends BaseController {

    @Resource
    private ContentSharingService contentSharingService;

    @ApiOperation(value = "内容分享记录查询查询列表")
    @RequestMapping(value = "/getContentSharingListPage", method = {RequestMethod.POST})
    public DefaultResponseBean<PageResponseBean<List<ContentSharingParams>>> getContentSharingListPage(@RequestBody @Valid ContentSharingRequest contentSharingRequest, HttpServletRequest request) {
        Long[] drugUserIdArray = new Long[1];
        DrugUser user = this.getLoginUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }
        // 101 虚拟代表 104 招募 105 覆盖
        if(user.getRoleId() == 101 || user.getRoleId() == 104 || user.getRoleId() == 105)
        {
            drugUserIdArray[0] = user.getId();
            contentSharingRequest.setDrugUserId(drugUserIdArray);
        }

        PageResponseBean<List<ContentSharingParams>> list = contentSharingService.getContentSharingListPage(contentSharingRequest);
        DefaultResponseBean<PageResponseBean<List<ContentSharingParams>>> responseBean = new DefaultResponseBean<PageResponseBean<List<ContentSharingParams>>>();
        responseBean.setData(list);
        return responseBean;
    }

    @ApiOperation(value = "内容阅读记录查询查询列表")
    @RequestMapping(value = "/getContentReadLogsListPage", method = {RequestMethod.POST})
    public DefaultResponseBean<PageResponseBean<List<ContentReadLogsParams>>> getContentReadLogsListPage(@RequestBody @Valid ContentReadLogsRequest contentReadLogsRequest) {
        PageResponseBean<List<ContentReadLogsParams>> list = contentSharingService.getContentReadLogsListPage(contentReadLogsRequest);
        DefaultResponseBean<PageResponseBean<List<ContentReadLogsParams>>> responseBean = new DefaultResponseBean<PageResponseBean<List<ContentReadLogsParams>>>();
        responseBean.setData(list);
        return responseBean;
    }

    @ApiOperation(value = "内容分享记录导出")
    @RequestMapping(value = "/exportFile", method = {RequestMethod.POST})
    public DefaultResponseBean<FilePathParams>  exportFile(@RequestBody @Valid ContentSharingRequest contentSharingRequest, HttpServletResponse response) {
        FilePathParams pathParams = contentSharingService.exportFile(contentSharingRequest,response);
        return successData(pathParams);
    }

    @ApiOperation(value = "内容分享记录导出文件下載")
    @RequestMapping(value = "/download", method = {RequestMethod.GET})
    public void download(@RequestParam("outPutPath") String outPutPath, @RequestParam("fileName")String fileName, HttpServletResponse response) {
        contentSharingService.download(outPutPath,fileName,response);
    }

    @ApiOperation(value = "内容分享记录导出文件下載一体")
    @RequestMapping(value = "/exportCSVFile", method = {RequestMethod.GET})
    public void exportCSVFile(@RequestParam(value = "productId", required = false) Long[] productId, @RequestParam(value = "drugUserId", required = false)Long[] drugUserId, @RequestParam(value = "startTimeAfter", required = false)String startTimeAfter, @RequestParam(value = "startTimeBefore", required = false)String startTimeBefore, @RequestParam(value = "shareType", required = false) Integer shareType, @RequestParam(value = "title", required = false) String title, HttpServletResponse response, HttpServletRequest request) {

        DrugUser user = this.getLoginUser(request);
        if(user != null) {
            // 101 虚拟代表 104 招募 105 覆盖
            if(user.getRoleId() == 101 || user.getRoleId() == 104 || user.getRoleId() == 105)
            {
                Long[] drugUserIdArray = new Long[1];
                drugUserIdArray[0] = user.getId();
                drugUserId = drugUserIdArray;
            }
        }
        contentSharingService.contentSharingExportFile(productId, drugUserId, startTimeAfter, startTimeBefore, shareType, title, response);
    }

}
