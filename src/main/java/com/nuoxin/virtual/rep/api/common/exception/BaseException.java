package com.nuoxin.virtual.rep.api.common.exception;


import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;

/**
 * Created by fenggang on 7/28/17.
 */
public class BaseException extends RuntimeException {
    static final long serialVersionUID = -7034897190745766939L;
    /**
     * 错误码
     */
    protected int code = ErrorEnum.ERROR.getStatus();
    /**
     * 错误码的说明
     */
    protected String label = ErrorEnum.ERROR.getMessage();

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }


    protected BaseException(String message, Throwable cause,
                            boolean enableSuppression,
                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
