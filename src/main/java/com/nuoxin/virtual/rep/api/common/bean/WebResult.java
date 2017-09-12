package com.nuoxin.virtual.rep.api.common.bean;

import java.io.Serializable;

/**
 * Created by yangyang on 2017/7/4.
 */
public class WebResult implements Serializable {

    private static final long serialVersionUID = -14972284664427720L;
    private int resultcode;
    private String msg;
    private Object data;

    //set get
    public int getResultcode() {
        return resultcode;
    }

    public void setResultcode(int resultcode) {
        this.resultcode = resultcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public static class ResultFactory {

        public static WebResult makeErrorResult(int errorcode, String msg) {
            WebResult result = new WebResult();
            result.setResultcode(errorcode);
            result.setMsg(msg);
            result.setData("");
            return result;
        }

        public static WebResult makeOKResult() {
            WebResult result = new WebResult();
            result.setResultcode(CommonStatusCode.StatusCode.OK);
            return result;
        }

        public static WebResult makeOKResult(Object object) {
            WebResult result = new WebResult();
            result.setResultcode(CommonStatusCode.StatusCode.OK);
            result.setData(object);
            return result;
        }

        public static WebResult makeNoPrivilegeResult(int errorcode, String msg) {
            WebResult result = new WebResult();
            result.setResultcode(errorcode);
            result.setMsg(msg);
            result.setData(msg);
            return result;
        }
    }
}
