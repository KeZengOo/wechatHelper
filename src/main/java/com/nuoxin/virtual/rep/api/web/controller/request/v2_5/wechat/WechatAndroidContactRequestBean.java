package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 安卓手机微信联系人请求参数
 * @author tiancun
 * @date 2018-12-03
 */
@Data
@ApiModel(value = "安卓手机微信联系人请求参数")
public class WechatAndroidContactRequestBean {

    private String userName;

    private String nickName;

    private String alias;

    /**
     * 备注
     */
    private String conRemark;


    private String type;


    private Long doctorId;

    private String telephone;

}
