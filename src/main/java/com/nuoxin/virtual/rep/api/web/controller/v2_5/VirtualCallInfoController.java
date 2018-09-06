package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualDoctorlCallInfoService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo.SaveCallInfoRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 电话拜访 Controller 类
 * @author xiekaiyu
 */
@Api(value = "电话拜访相关接口")
@RequestMapping(value = "/call/info")
@RestController
public class VirtualCallInfoController extends BaseController{
	
	@Resource
	private VirtualDoctorlCallInfoService callInfoService;
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "保存电话拜访信息", notes = "保存电话拜访信息")
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public DefaultResponseBean<Boolean> save(HttpServletRequest request, @RequestBody @Valid  SaveCallInfoRequest saveRequest, 
			                                                             BindingResult bindingResult) {
		DrugUser user = this.getDrugUser(request);
		if(user == null) {
			return super.getLoginErrorResponse();
		} 
		
		if(bindingResult.hasErrors()){
			return super.getParamsErrorResponse(bindingResult.getFieldError().getDefaultMessage());
        }
		
		boolean flag = callInfoService.saveCallInfo(saveRequest);
		DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<Boolean>();
		responseBean.setData(flag);
		
		return responseBean;
	}
}
