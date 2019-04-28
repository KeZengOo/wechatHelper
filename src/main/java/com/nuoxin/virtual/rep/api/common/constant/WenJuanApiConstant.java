package com.nuoxin.virtual.rep.api.common.constant;

/**
 * 问卷网API
 * @author wujiang
 * @date 20190425
 */
public interface WenJuanApiConstant {

//    /**
//     * 问卷网API url 正式
//     */
//    String URL = "https://www.wenjuan.com/openapi/v3/";

    /**
     * 问卷网API url 测试
     */
    String URL = "http://apitest.wenjuan.com/openapi/v3/";

    /**
     * 登录接口
     */
    String LOGIN = "login/";

    /**
     * 获取项目列表接口
     */
    String GET_PROJ_LIST = "get_proj_list/";

    /**
     * 获取答卷列表接口
     */
    String GET_RSPD_DETAIL_LIST = "get_rspd_detail_list/";

    /**
     * 查看答题者最新一条答卷详情接口
     */
    String GET_RSPD_DETAIL = "get_rspd_detail/";

    /**
     * 查看第几页
     */
    String WJ_PAGE = "wj_page";

    /**
     *每页包含多少条目
     */
    String WJ_PAGESIZE = "wj_pagesize";

    /**
     *每页包含多少条目
     */
    int WJ_PAGESIZE_VALUE = 50;

    /**
     *  第N题循环数
     */
    int Q_VALUE = 1000;

    /**
     * 数据类型
     */
    String WJ_DATATYPE = "wj_datatype";

    /**
     * 数据类型 json
     */
    String WJ_DATATYPE_JSON = "json";

    /**
     * 答题者标识，由第三方开发者提供，查看数据详情时，对应source(来源)字段
     */
    String WJ_RESPONDENT = "wj_respondent";

    /**
     * wj_appkey
     */
    String WJ_APPKEY = "wj_appkey";

    /**
     * wj_appkey_value
     */
    String WJ_APPKEY_VALUE = "wjrk1gycdidtoip31u";

    /**
     * wj_user 用户code
     */
    String WJ_USER = "wj_user";

    /**
     * wj_short_id 项目短id
     */
    String WJ_SHORT_ID = "wj_short_id";

    /**
     * wj_user_value
     */
    String WJ_USER_VALUE = "15317291270";

    /**
     * wj_timestamp 时间戳
     */
    String WJ_TIMESTAMP = "wj_timestamp";

    /**
     * wj_appsecret
     */
    String WJ_APPSECRET = "wj_appsecret";

    /**
     * wj_appsecret_value
     */
    String WJ_APPSECRET_VALUE = "06caaf5b6d9f8688081399e89b740274";

    /**
     * wj_signature 签名
     */
    String WJ_SIGNATURE = "wj_signature";

}
