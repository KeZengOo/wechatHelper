package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import java.util.List;

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
import com.nuoxin.virtual.rep.api.entity.v2_5.HospitalProvinceBean;
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
public class VirtualDoctorController extends NewBaseController {
	
	@Resource
	private VirtualDoctorService virtualDoctorService;
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "添加单个客户医生信息")
	@RequestMapping(value = "/single/save", method = { RequestMethod.POST })
	public DefaultResponseBean<Long> singleSave(HttpServletRequest request,
			@RequestBody @Valid SaveVirtualDoctorRequest saveRequest, BindingResult bindingResult) {
		DrugUser user = this.getDrugUser(request);
		if (user == null) {
			return super.getLoginErrorResponse();
		}

		// 参数校验
		if (bindingResult.hasErrors()) {
			return super.getParamsErrorResponse(bindingResult.getFieldError().getDefaultMessage());
		}

		Long id = virtualDoctorService.saveVirtualDoctor(saveRequest, user);

		DefaultResponseBean<Long> responseBean = new DefaultResponseBean<Long>();
		responseBean.setData(id);
		return responseBean;
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "根据医院名模糊匹配")
	@RequestMapping(value = "/hospitals/get", method = { RequestMethod.GET })
	public DefaultResponseBean<List<HospitalProvinceBean>> getHospitals(HttpServletRequest request,
			@ApiParam(value = "控件中输入的医院名") @RequestParam(value = "hospital_name") String hospitalName) {
		DrugUser user = this.getDrugUser(request);
		if (user == null) {
			return super.getLoginErrorResponse();
		}

		List<HospitalProvinceBean> list = virtualDoctorService.getHospitals(hospitalName);
		DefaultResponseBean<List<HospitalProvinceBean>> responseBean = new DefaultResponseBean<List<HospitalProvinceBean>>();
		responseBean.setData(list);
		
		return responseBean;
	}
}
