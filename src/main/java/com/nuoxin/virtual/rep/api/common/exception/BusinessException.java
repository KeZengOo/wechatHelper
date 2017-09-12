package com.nuoxin.virtual.rep.api.common.exception;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
/**
 * <pre>
 * 业务异常，处理业务抛出的异常
 * 错误码和错误码说明，需要指定
 * </pre>
 * 
 * @author fenggang
 */
public class BusinessException extends BaseException {
  private static final long serialVersionUID = -4310721413425427596L;

  public BusinessException() {
    super();
  }

  /**
   * 构造函数
   * 
   * @param code：错误码
   * @param codeLabel：错误码说明
   */
  public BusinessException(int code, String codeLabel) {
    super(codeLabel);
    setCode(code);
    setLabel(codeLabel);
  }

  /**
   * 构造函数
   * 
   * @param code：错误码
   * @param codeLabel：错误码说明
   * @param message：异常说明
   */
  public BusinessException(int code, String codeLabel, String message) {
    super(message);
    setCode(code);
    setLabel(codeLabel);
  }

  /**
   * 构造函数
   * 
   * @param code：错误码
   * @param codeLabel：错误码说明
   * @param message：异常说明
   * @param cause
   */
  public BusinessException(int code, String codeLabel, String message, Throwable cause) {
    super(message, cause);
    setCode(code);
    setLabel(codeLabel);
  }

  /**
   * 构造函数
   * 
   * @param error：错误码
   */
  public BusinessException(ErrorEnum error) {
    super(error.getMessage());
    setCode(error.getStatus());
    setLabel(error.getMessage());
  }

  /**
   * 构造函数
   * 
   * @param error：错误码
   * @param message：异常说明
   */
  public BusinessException(ErrorEnum error, String message) {
    super(message);
    setCode(error.getStatus());
    setLabel(error.getMessage());
  }

  /**
   * 构造函数
   * 
   * @param error：错误码
   * @param message：异常说明
   * @param cause
   */
  public BusinessException(ErrorEnum error, String message, Throwable cause) {
    super(message, cause);
    setCode(error.getStatus());
    setLabel(error.getMessage());
  }

}
