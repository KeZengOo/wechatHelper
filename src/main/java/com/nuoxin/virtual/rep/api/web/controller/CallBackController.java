package com.nuoxin.virtual.rep.api.web.controller;

import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.ResponseObj;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.service.CallBackService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.call.Call7mmorRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.Call7mmorResponseBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "电话回调 Controller 类")
@RestController
@RequestMapping(value = "/callback")
public class CallBackController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CallBackController.class);

	@Resource(name = "sevenMoor")
	private CallBackService callBackService;

	/**
	 * 七陌回调入口方法 默认超时时间为10s<br> 
	 * 参考链接 https://developer.7moor.com/event/
	 * @param request
	 * @param response
	 * @return ResponseEntity<?>
	 */
	@ApiOperation(value = "回调接口方法", notes = "回调接口方法")
	@RequestMapping("/7moor")
	public ResponseEntity<?> callback(HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		// 参数转换
		ConcurrentMap<String, String> paramsMap = this.getParamsMap(request);
		if (CollectionsUtil.isNotEmptyMap(paramsMap)) {
			this.processCallBack(paramsMap); // 调用异步处理业务方法
		} else {
			logger.error("7moor 传参异常,响应给 7moor 500");
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}

	@ApiOperation(value = "查询通话记录", notes = "查询通话记录")
	@GetMapping("/call/list")
	public ResponseEntity<DefaultResponseBean<List<Call7mmorResponseBean>>> getCallList(Call7mmorRequestBean bean) {
		List<Call7mmorResponseBean> callList = callBackService.getCallList(bean);
		DefaultResponseBean<List<Call7mmorResponseBean>> responseBean = new DefaultResponseBean<>();
		responseBean.setData(callList);
		return ResponseEntity.ok(responseBean);
	}

	@ApiOperation(value = "录音文件重新上传", notes = "录音文件重新上传")
	@GetMapping("/call/url/repeat")
	public ResponseEntity<DefaultResponseBean<Boolean>> repeatUploadFile(Call7mmorRequestBean bean) {
		callBackService.repeatUploadFile(bean);
		DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
		responseBean.setData(true);
		return ResponseEntity.ok(responseBean);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 将回调 GET 请求参数 -> Map 
	 * @param request
	 * @return
	 */
	private ConcurrentMap<String, String> getParamsMap(HttpServletRequest request) {
		ConcurrentMap<String, String> paramsMap = new ConcurrentHashMap<>();
		
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames != null && paramNames.hasMoreElements()) {
			String parameter = paramNames.nextElement();
			String[] parameterValues = request.getParameterValues(parameter);
			if (parameterValues.length == 1) {
				String parameterValue = parameterValues[0];
				paramsMap.put(parameter, parameterValue);
				logger.info("{}={}", parameter, parameterValue);
			}
		}

		return paramsMap;
	}
	
	/**
	 * 调业务层方法
	 * @param paramsMap
	 */
	@Async
	private void processCallBack(ConcurrentMap<String, String> paramsMap) {
		String callSheetId = paramsMap.get("CallSheetID");
		try {
			callBackService.callBack(paramsMap);
		} catch (Exception e) {
			logger.error(" 7moor 回调处理异常, callSheetId:{}", callSheetId, e);
		}
	}

}
