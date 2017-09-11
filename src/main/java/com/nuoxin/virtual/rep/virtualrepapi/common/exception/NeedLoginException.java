package com.nuoxin.virtual.rep.virtualrepapi.common.exception;

import com.nuoxin.virtual.rep.virtualrepapi.common.enums.ErrorEnum;

/**
 * 当登录无效时，使用该异常
 *
 * Created by fenggang on 7/28/17.
 */
public class NeedLoginException extends BaseException {

  private static final long serialVersionUID = 1L;



  public NeedLoginException() {
    super();
  }

  /**
   * 构造函数
   *
   * @param code：错误码
   * @param codeLabel：错误码说明
   */
  public NeedLoginException(int code, String codeLabel) {
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
  public NeedLoginException(int code, String codeLabel, String message) {
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
  public NeedLoginException(int code, String codeLabel, String message, Throwable cause) {
    super(message, cause);
    setCode(code);
    setLabel(codeLabel);
  }

  /**
   * 构造函数
   *
   * @param error：错误码
   */
  public NeedLoginException(ErrorEnum error) {
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
  public NeedLoginException(ErrorEnum error, String message) {
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
  public NeedLoginException(ErrorEnum error, String message, Throwable cause) {
    super(message, cause);
    setCode(error.getStatus());
    setLabel(error.getMessage());
  }


}
