package com.nuoxin.virtual.rep.virtualrepapi.common.controller;

import com.nuoxin.virtual.rep.virtualrepapi.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.virtualrepapi.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.virtualrepapi.common.exception.BusinessException;
import com.nuoxin.virtual.rep.virtualrepapi.common.exception.NeedLoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

/**
 * <pre>
 * 异常响应处理。将异常包装为固定的格式并返回。
 * 指定格式为{@link ResponseEntity}。
 * </pre>
 * 
 * @author fenggang
 */
@ControllerAdvice
public class ControllerAssist {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 注册全局数据编辑器，若传递的数据为空字串 转成 null
	 * 
	 * @param binder
	 *          数据绑定
	 * @param request
	 *          web请求
	 */
	@InitBinder
	public void registerCustomEditors(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * 处理业务异常
	 * 
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	public ResponseEntity<DefaultResponseBean<?>> handleBusinessException(BusinessException exception, HttpServletRequest request) {
		logger.info("", exception);
		return ResponseEntity.ok(DefaultResponseBean.clone(exception.getLabel(), exception.getCode(), exception.getMessage()));
	}

	@ExceptionHandler(NeedLoginException.class)
	@ResponseBody
	public ResponseEntity<DefaultResponseBean<?>> handleNeedLoginException(NeedLoginException exception, HttpServletRequest request) {
		logger.info("", exception);
		return ResponseEntity.ok(DefaultResponseBean.clone(ErrorEnum.LOGIN_NO.getMessage(), ErrorEnum.LOGIN_NO.getStatus(), exception.getMessage()));
	}

	/**
	 * 处理请求参数验证异常
	 * 
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<DefaultResponseBean<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                                        HttpServletRequest request) {
		logger.info("", exception);
		ErrorEnum error = ErrorEnum.SYSTEM_REQUEST_PARAM_ERROR;
		String message = error.getMessage();
		BindingResult bindingResult = exception.getBindingResult();
		if (bindingResult != null && bindingResult.hasErrors()) {
			List<ObjectError> objectErrorList = bindingResult.getAllErrors();
			if (!objectErrorList.isEmpty()) {
				message = objectErrorList.get(0).getDefaultMessage();
			}
		}
		return ResponseEntity.ok(DefaultResponseBean.clone(message, error.getStatus(), exception.getMessage()));
	}

	/**
	 * 处理服务器端数据访问错误
	 * 
	 * @param request
	 *          请求对象
	 * @param exception
	 *          异常对象
	 * @param locale
	 *          地理信息
	 * @return
	 */
	@ExceptionHandler({ SQLException.class, DataAccessException.class, DataAccessResourceFailureException.class,
		org.hibernate.exception.DataException.class, DataIntegrityViolationException.class })
	@ResponseBody
	public ResponseEntity<DefaultResponseBean<?>> handleSQLException(HttpServletRequest request, Exception exception, Locale locale) {
		logger.info("", exception);
		ErrorEnum error = ErrorEnum.ERROR;
		return ResponseEntity.ok(DefaultResponseBean.clone(error.getMessage(), error.getStatus(), exception.getMessage()));
	}

	/**
	 * 处理服务器端RuntimeException
	 * 
	 * @param request
	 *          请求对象
	 * @param exception
	 *          异常对象
	 * @param locale
	 *          地理信息
	 * @return
	 */
	@ExceptionHandler({ RuntimeException.class })
	@ResponseBody
	public ResponseEntity<DefaultResponseBean<?>> handleAllException(HttpServletRequest request, Exception exception, Locale locale) {
		logger.info("", exception);
		ErrorEnum error = ErrorEnum.ERROR;
		return ResponseEntity.ok(DefaultResponseBean.clone(error.getMessage(), error.getStatus(), exception.getMessage()));
	}

}
