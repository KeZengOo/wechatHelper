package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualQuestionnaireService;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.VirtualQuestionnaireRecordResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.VirtualQuestionnaireResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 虚拟代表问卷 Controller 类
 * @author xiekaiyu
 */
@Api(value = "V2.5虚拟代表问卷相关接口")
@RequestMapping(value = "/questionnaire")
@RestController
public class VirtualQuestionnaireController extends BaseController {
	
	@Resource
	private VirtualQuestionnaireService questionnaireService;
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "获取最近一次问卷做题结果", notes = "获取最近一次问卷做题结果")
	@RequestMapping(value = "/last_questionnaire_record/get", method = { RequestMethod.GET })
	public DefaultResponseBean<List<VirtualQuestionnaireRecordResponse>> list(
			                                                        @ApiParam(value="虚拟代表ID")@RequestParam(value="virtual_drug_user_id") Long virtualDrugUserId, 
																	@ApiParam(value="医生ID")@RequestParam(value="doctor_id") Long virtualDoctorId, 
																	@ApiParam(value="问卷ID") @RequestParam(value="virtual_questionnaire_id") Long virtualQuestionnaireId,
			                                                        HttpServletRequest request) {
		DrugUser user = this.getDrugUser(request);
		if (user == null) {
			return this.getLoginErrorResponse();
		}

		DefaultResponseBean<List<VirtualQuestionnaireRecordResponse>> responseBean = new DefaultResponseBean<>();
		List<VirtualQuestionnaireRecordResponse> list = questionnaireService.getLastQuestionnaireRecord(virtualDrugUserId,
				virtualDoctorId, virtualQuestionnaireId);
		responseBean.setData(list);
		
		return responseBean;
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "根据产品线ID获取问卷信息", notes = "根据产品线ID获取问卷信息")
	@RequestMapping(value = "/questionaires/get", method = { RequestMethod.GET })
	public DefaultResponseBean<List<VirtualQuestionnaireResponse>> getQuestionnaireByProductLineId(
																	@ApiParam(value="产品线ID") @RequestParam(value="product_line_id") Long productLineId,
			                                                        HttpServletRequest request) {
		DrugUser user = this.getDrugUser(request);
		if (user == null) {
			return this.getLoginErrorResponse();
		}
		
		if (productLineId == null) {
			return super.getParamsErrorResponse("product_line_id is null");
		}

		DefaultResponseBean<List<VirtualQuestionnaireResponse>> responseBean = new DefaultResponseBean<List<VirtualQuestionnaireResponse>>();
		List<VirtualQuestionnaireResponse> questionnaires = questionnaireService.getQuestionnaireByProductLineId(productLineId);
		responseBean.setData(questionnaires);
		
		return responseBean;
	}
	
}
