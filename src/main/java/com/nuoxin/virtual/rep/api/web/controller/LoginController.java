package com.nuoxin.virtual.rep.api.web.controller;

import com.alibaba.fastjson.JSON;
import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.common.util.mem.SessionMemUtils;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.enums.OnOffLineEnum;
import com.nuoxin.virtual.rep.api.enums.SaleUserTypeEnum;
import com.nuoxin.virtual.rep.api.service.*;
import com.nuoxin.virtual.rep.api.web.controller.request.LoginRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.UpdatePwdRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserCallDetaiBean;
import com.nuoxin.virtual.rep.api.web.controller.response.LoginResponseBean;
import com.sun.org.apache.bcel.internal.generic.SALOAD;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 * Created by fenggang on 9/11/17.
 */
@Api(value = "用户登录相关")
@RestController
public class LoginController extends BaseController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private SecurityService sercurityService;
	@Autowired
	private DrugUserService drugUserService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private SessionMemUtils memUtils;


	@SuppressWarnings("unchecked")
	@ApiOperation(value = "虚拟代表登录", notes = "虚拟代表登录")
	@PostMapping("/login")
	@ResponseBody
	public DefaultResponseBean<LoginResponseBean> login(@RequestBody LoginRequestBean bean, 
			                                                                              HttpServletRequest request, HttpServletResponse response) {
		DrugUser drugUser = loginService.login(bean);
		if (drugUser.getRoleId() == null) {
			return super.getLoginErrorResponse();
		}

		sercurityService.saveSession(request, response, drugUser);

		DefaultResponseBean<LoginResponseBean> responseBean = new DefaultResponseBean<>();
		responseBean.setData(this.getLoginResponseBean(drugUser));
		return responseBean;
	}
	
	@ApiOperation(value = "虚拟代表登出", notes = "虚拟代表登出")
	@GetMapping("/logout")
	@ResponseBody
	public DefaultResponseBean<Object> logout(HttpServletRequest request, HttpServletResponse response) {
		sercurityService.cleanSession(request);
		DefaultResponseBean<Object> responseBean = new DefaultResponseBean<>();
		return responseBean;
	}

	@GetMapping("/retrieve/pwd/send")
	@ResponseBody
	public DefaultResponseBean<Object> emailCodeSend(@RequestParam("email") String email, HttpServletRequest request,
			HttpServletResponse response) throws MessagingException {
		DrugUser drugUser = drugUserService.findByEmail(email);
		DefaultResponseBean<Object> responseBean = new DefaultResponseBean<>();
		if (drugUser == null) {
			responseBean.setCode(500);
			responseBean.setMessage("账号不存在");
			return responseBean;
		}

		responseBean.setMessage("验证码发送成功");
		responseBean.setData(emailService.sendEmailCode(drugUser));
		return responseBean;
	}

	@GetMapping("/retrieve/check/{token}/{code}")
	@ResponseBody
	public DefaultResponseBean<Object> emailCodeSend(@PathVariable String token, @PathVariable String code,
			HttpServletRequest request, HttpServletResponse response) throws MessagingException {
		DefaultResponseBean<Object> responseBean = new DefaultResponseBean<>();
		Object obj = memUtils.get(token);
		if (obj == null) {
			responseBean.setCode(500);
			responseBean.setMessage("验证码失效");
			return responseBean;
		}

		Object val = memUtils.get(obj.toString());
		if (val == null) {
			responseBean.setCode(500);
			responseBean.setMessage("验证码失效");
			return responseBean;
		}

		if (!code.equals(val.toString())) {
			responseBean.setCode(500);
			responseBean.setMessage("验证码错误");
			return responseBean;
		}

		responseBean.setMessage("验证成功");
		return responseBean;
	}

	@PostMapping("/retrieve/pwd/save")
	@ResponseBody
	public DefaultResponseBean<Object> updatePawd(@RequestBody UpdatePwdRequestBean bean, HttpServletRequest request,
			HttpServletResponse response) throws MessagingException {
		DefaultResponseBean<Object> responseBean = new DefaultResponseBean<>();
		Object email = memUtils.get(bean.getToken());
		if (email == null || "".equals(email)) {
			responseBean.setCode(500);
			responseBean.setMessage("验证码过期");
			return responseBean;
		}

		if (null == bean.getEmail() || !bean.getEmail().equals(email)) {
			responseBean.setCode(500);
			responseBean.setMessage("邮箱错误");
			return responseBean;
		}

		Object code = memUtils.get(bean.getEmail());
		if (code == null || "".equals(code)) {
			responseBean.setCode(500);
			responseBean.setMessage("验证码过期");
			return responseBean;
		}

		if (!code.toString().equals(bean.getCode())) {
			responseBean.setCode(500);
			responseBean.setMessage("验证码错误");
			return responseBean;
		}

		// 校验密码格式
		if (!Pattern.matches("^[0-9a-zA-Z]{6,20}", bean.getPassword())) {
			responseBean.setCode(500);
			responseBean.setMessage("请输入6-20位英文、数字组合");
			return responseBean;
		}

		drugUserService.updatePawd(bean);
		responseBean.setMessage("修改密码成功");
		memUtils.deleteKey(bean.getEmail());
		memUtils.deleteKey(bean.getToken());
		return responseBean;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private LoginResponseBean getLoginResponseBean(DrugUser drugUser) {
		LoginResponseBean loginBean = new LoginResponseBean();
		loginBean.setName(drugUser.getName());
		loginBean.setEmail(drugUser.getEmail());
		loginBean.setRoleId(drugUser.getRoleId());
		loginBean.setRoleName(drugUser.getRoleName());
		loginBean.setVirtualDrugUserId(drugUser.getId());
		Integer saleType = drugUser.getSaleType();
		if (OnOffLineEnum.ONLINE.getUserType().equals(saleType)){
			loginBean.setSaleTypeName(OnOffLineEnum.ONLINE.getUserTypeName());
		}else if (OnOffLineEnum.OFFLINE.getUserType().equals(saleType)){
			loginBean.setSaleTypeName(OnOffLineEnum.OFFLINE.getUserTypeName());
		}else {
			loginBean.setSaleTypeName(OnOffLineEnum.OTHER.getUserTypeName());
		}

		loginBean.setSaleType(drugUser.getSaleType());
		loginBean.setCallBean(JSON.parseObject(drugUser.getCallInfo(), DrugUserCallDetaiBean.class));

		return loginBean;
	}

}
