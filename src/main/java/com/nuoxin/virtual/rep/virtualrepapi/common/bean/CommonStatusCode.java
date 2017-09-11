package com.nuoxin.virtual.rep.virtualrepapi.common.bean;

/**
 * 主数据接口返回的http状态
 * Created by yangyang on 2017/7/4.
 */
public class CommonStatusCode {
    public static final class StatusCode {
        public static final Integer OK = 200;
        public static final Integer SERVER_ERROR = 500;
        public static final Integer DATABASE_ERROR = 100;
        public static final Integer PARAM_ERROR = 101;
        public static final Integer REFER_ERROR = 102;
        public static final Integer NEED_LOGIN = 401;
        public static final Integer AUTHORIZE_ERROR = 403;

        public static final Integer REMOTE_ERROR = 900;
    }

    public static final class StatusMsg {
        public static final String OK_MSG = "success";
        public static final String SERVER_ERROR_MSG = "服务器异常，请稍后重试。";
    }
}
