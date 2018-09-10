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
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualDoctorService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.SaveVirtualDoctorRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "医生相关接口")
@RequestMapping(value = "/doctors")
@RestController
public class VirtualDoctorController extends BaseController {
	
	@Resource
	private VirtualDoctorService virtualDoctorService;

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "添加医生(客户)信息", notes = "添加医生(客户)信息")
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public DefaultResponseBean<Boolean> save(HttpServletRequest request, @RequestBody @Valid  SaveVirtualDoctorRequest saveRequest, 
			                                                             BindingResult bindingResult) {
		DrugUser user = this.getDrugUser(request);
		if(user == null) {
			return super.getLoginErrorResponse();
		} 
		
		if(bindingResult.hasErrors()){
			return super.getParamsErrorResponse(bindingResult.getFieldError().getDefaultMessage());
        }
		
		boolean flag = virtualDoctorService.saveVirtualDoctor(saveRequest);
		
		DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<Boolean>();
		responseBean.setData(flag);
		return responseBean;
	}
}
