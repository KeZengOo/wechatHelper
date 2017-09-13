package com.nuoxin.virtual.rep.api.common.exception;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;

/**
 * 文件格式异常
 */
public class FileFormatException extends BaseException{
    private static final long serialVersionUID = -2678418705402289426L;

    public FileFormatException() {
        super();
    }

    /**
     *
     * @param code 错误码
     * @param codeLabel 错误码说明
     */
    public FileFormatException(int code, String codeLabel) {
        super(codeLabel);
        setCode(code);
        setLabel(codeLabel);
    }


    public FileFormatException(int code, String codeLable, String message) {
        super(message);
        setCode(code);
        setLabel(codeLable);
    }

    public FileFormatException(int code, String codeLable, String message, Throwable cause) {
        super(message ,cause);
        setCode(code);
        setLabel(codeLable);
    }

    public FileFormatException(ErrorEnum error) {
        setCode(error.getStatus());
        setLabel(error.getMessage());
    }

    public FileFormatException(ErrorEnum error, String message) {
        super(message);
        setCode(error.getStatus());
        setLabel(error.getMessage());
    }


    public FileFormatException(ErrorEnum error, String message, Throwable cause) {
        super(message, cause);
        setCode(error.getStatus());
        setLabel(error.getMessage());
    }


}
