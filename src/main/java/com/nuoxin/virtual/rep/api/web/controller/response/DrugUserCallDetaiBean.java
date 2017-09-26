package com.nuoxin.virtual.rep.api.web.controller.response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 9/26/17.
 */
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public Integer getLogintype() {
        return logintype;
    }

    public void setLogintype(Integer logintype) {
        this.logintype = logintype;
    }

    public Integer getAuto() {
        return auto;
    }

    public void setAuto(Integer auto) {
        this.auto = auto;
    }

    public String getLogingroups() {
        return logingroups;
    }

    public void setLogingroups(String logingroups) {
        this.logingroups = logingroups;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
