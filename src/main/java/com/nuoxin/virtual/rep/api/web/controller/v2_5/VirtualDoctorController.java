package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorBasicResponse;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualDoctorService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.SaveVirtualDoctorRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 医生 Controller 类
 * @author xiekaiyu
 */
@Api(value = "V2.5客户医生相关接口")
@RequestMapping(value = "/doctors")
@RestController
public class VirtualDoctorController extends BaseController {
	
	@Resource
	private VirtualDoctorService virtualDoctorService;
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "获取单个客户医生信息", notes = "获取单个客户医生信息")
	@RequestMapping(value = "/single/get", method = { RequestMethod.POST })
	public DefaultResponseBean<VirtualDoctorBasicResponse> singleGet(
			@ApiParam("医生ID") @RequestParam(value = "doctor_id") Long virtualDoctorId, HttpServletRequest request) {
		DrugUser user = this.getDrugUser(request);
		if (user == null) {
			return super.getLoginErrorResponse();
		}

		if (virtualDoctorId == null) {
			return super.getParamsErrorResponse("doctor_id is null");
		}

		VirtualDoctorBasicResponse virtualDoctorBasic = virtualDoctorService.getVirtualDoctorBasic(virtualDoctorId);

		DefaultResponseBean<VirtualDoctorBasicResponse> responseBean = new DefaultResponseBean<VirtualDoctorBasicResponse>();
		responseBean.setData(virtualDoctorBasic);
		return responseBean;
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "添加单个客户医生信息", notes = "添加单个客户医生信息")
	@RequestMapping(value = "/single/save", method = { RequestMethod.POST })
	public DefaultResponseBean<Boolean> singleSave(HttpServletRequest request,
			@RequestBody @Valid SaveVirtualDoctorRequest saveRequest, BindingResult bindingResult) {
		DrugUser user = this.getDrugUser(request);
		if (user == null) {
			return super.getLoginErrorResponse();
		}

		// 参数校验
		if (bindingResult.hasErrors()) {
			return super.getParamsErrorResponse(bindingResult.getFieldError().getDefaultMessage());
		}

		boolean flag = virtualDoctorService.saveVirtualDoctor(saveRequest, user);

		DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<Boolean>();
		responseBean.setData(flag);
		return responseBean;
	}
}
