package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualDoctorCallInfoService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo.SaveCallInfoRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo.SaveCallInfoUnConnectedRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 电话拜访 Controller 类
 * @author xiekaiyu
 */
@Api(value = "V2.5电话拜访相关接口")
@RequestMapping(value = "/call_info")
@RestController
public class VirtualDoctorCallInfoController extends NewBaseController{
	
	@Resource
	private VirtualDoctorCallInfoService callInfoService;
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "保存电话接通拜访信息", notes = "保存电话接通拜访信息")
	@RequestMapping(value = "/connected/save", method = { RequestMethod.POST })
	public DefaultResponseBean<Boolean> connectedSave(HttpServletRequest request, 
			                                                             @RequestBody @Valid  SaveCallInfoRequest saveRequest, 
			                                                             BindingResult bindingResult) {
		DrugUser user = this.getDrugUser(request);
		if(user == null) {
			return super.getLoginErrorResponse();
		} 
		
		if(bindingResult.hasErrors()){
			return super.getParamsErrorResponse(bindingResult.getFieldError().getDefaultMessage());
        }
		
		boolean flag = callInfoService.saveConnectedCallInfo(saveRequest);
		DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<Boolean>();
		responseBean.setData(flag);
		
		return responseBean;
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "保存电话未接通拜访信息", notes = "保存电话未接通拜访信息")
	@RequestMapping(value = "/unconnected/save", method = { RequestMethod.POST })
	public DefaultResponseBean<Boolean> unconnectedSave(HttpServletRequest request, 
			                                                             @RequestBody @Valid  SaveCallInfoUnConnectedRequest saveRequest, 
			                                                             BindingResult bindingResult) {
		DrugUser user = this.getDrugUser(request);
		if(user == null) {
			return super.getLoginErrorResponse();
		} 
		
		if(bindingResult.hasErrors()){
			return super.getParamsErrorResponse(bindingResult.getFieldError().getDefaultMessage());
        }
		
		boolean flag = callInfoService.saveUnconnectedCallInfo(saveRequest);
		DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<Boolean>();
		responseBean.setData(flag);
		
		return responseBean;
	}



	@SuppressWarnings("unchecked")
	@ApiOperation(value = "得到产品下某个医生的招募状态", notes = "得到产品下某个医生的招募状态")
	@GetMapping(value = "/product/Recruit/{productId}/{doctorId}")
	public DefaultResponseBean<Integer> getProductRecruit(HttpServletRequest request, @PathVariable(value = "productId") Long productId, @PathVariable(value = "doctorId") Long doctorId) {
		DrugUser user = this.getDrugUser(request);
		if(user == null) {
			return super.getLoginErrorResponse();
		}

		Integer recruit = callInfoService.getProductRecruit(productId, doctorId);
		DefaultResponseBean<Integer> responseBean = new DefaultResponseBean<Integer>();
		responseBean.setData(recruit);

		return responseBean;
	}

}
