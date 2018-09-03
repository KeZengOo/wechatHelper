package com.nuoxin.virtual.rep.api.web.controller.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by fenggang on 9/26/17.
 */
@Data
@ApiModel
public class DrugUserCallDetaiBean implements Serializable {

    private static final long serialVersionUID = 5076736339458733895L;

    @ApiModelProperty(value = "工号")
    private String operator;
    @ApiModelProperty(value = "密码")
    private String pwd;
    @ApiModelProperty(value = "企业编号")
    private Integer companyid;
    @ApiModelProperty(value = "登录方式，0手机1硬话机2软话机")
    private Integer logintype;
    @ApiModelProperty(value = "是否智能外呼，0否，1是")
    private Integer auto;
    @ApiModelProperty(value = "智能外呼情况下，可以传入技能组编号，多个请用英文逗号分隔，非智能外呼下可以不传此参数")
    private String logingroups;
    @ApiModelProperty(value = "登录使用的url")
    private String url;
    @ApiModelProperty(value = "登录模式，Local")
    private String loginmode;
    
}
