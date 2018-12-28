package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.enums.OnOffLineEnum;
import com.nuoxin.virtual.rep.api.enums.RoleTypeEnum;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.PrescriptionRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.UpdateVirtualDoctorRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DoctorDetailsResponseBean;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
	@ApiOperation(value = "得到线下代表对应的线上代表")
	@RequestMapping(value = "/online/user/list/{productId}", method = { RequestMethod.GET })
	public DefaultResponseBean<List<DrugUserResponseBean>> getOnlineDrugUserList(HttpServletRequest request,@PathVariable(value = "productId") Long productId) {
		DrugUser user = this.getDrugUser(request);
		if (user == null) {
			return super.getLoginErrorResponse();
		}

		Integer saleType = user.getSaleType();
		if (saleType == null || (!saleType.equals(OnOffLineEnum.OFFLINE.getUserType()))){
			throw new BusinessException(ErrorEnum.ERROR, "非线下代表不能访问");
		}

		List<DrugUserResponseBean> onlineDrugUserList = virtualDoctorService.getOnlineDrugUserList(productId);

		DefaultResponseBean<List<DrugUserResponseBean>> responseBean = new DefaultResponseBean<>();
		responseBean.setData(onlineDrugUserList);
		return responseBean;
	}


	@ApiOperation(value = "修改单个医生基本信息固定字段")
	@RequestMapping(value = "/basic/fix/field/update", method = { RequestMethod.POST })
	public DefaultResponseBean<Boolean> singleUpdate(HttpServletRequest request,
												@RequestBody @Valid UpdateVirtualDoctorRequest saveRequest) {
		DrugUser user = this.getDrugUser(request);
		if (user == null) {
			return super.getLoginErrorResponse();
		}


		virtualDoctorService.updateVirtualDoctor(saveRequest, user);

		DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<Boolean>();
		responseBean.setData(true);
		return responseBean;
	}


	@ApiOperation(value = "修改医生产品信息固定字段")
	@RequestMapping(value = "/product/fix/field/update", method = { RequestMethod.POST })
	public DefaultResponseBean<Boolean> updateDoctorProductFixField(HttpServletRequest request,
															@RequestBody @Valid PrescriptionRequestBean bean) {
		DrugUser user = this.getDrugUser(request);
		if (user == null) {
			return super.getLoginErrorResponse();
		}


		virtualDoctorService.updateDoctorProductFixField(bean);

		DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<Boolean>();
		responseBean.setData(true);
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



	@SuppressWarnings("unchecked")
	@ApiOperation(value = "新版根据联系方式获取医生接口")
	@RequestMapping(value = "/details/{telephone}", method = { RequestMethod.GET })
	public DefaultResponseBean<List<DoctorDetailsResponseBean>> getDoctorListByTelephone(HttpServletRequest request,@PathVariable(value = "telephone") String telephone
																		) {
		DrugUser user = this.getDrugUser(request);
		if (user == null) {
			return super.getLoginErrorResponse();
		}

		List<DoctorDetailsResponseBean> doctorList = virtualDoctorService.getDoctorListByTelephone(telephone);
		DefaultResponseBean<List<DoctorDetailsResponseBean>> responseBean = new DefaultResponseBean<>();
		responseBean.setData(doctorList);

		return responseBean;
	}


}
