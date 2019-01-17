package com.nuoxin.virtual.rep.api.web.controller.response.call;

import com.nuoxin.virtual.rep.api.entity.v2_5.ProductBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fenggang on 9/12/17.
 */
@ApiModel("医生产品及基本信息")
@Data
public class CallDoctorResponseBean implements Serializable {

    private static final long serialVersionUID = 199411772918914469L;

    @ApiModelProperty(value = "医生id")
    private Long doctorId;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "电话")
    private String mobile;
    @ApiModelProperty(value = "职称")
    private String title;
    @ApiModelProperty(value = "产品列表")
    private List<ProductBean> products;
    @ApiModelProperty(value = "科室")
    private String department;
    @ApiModelProperty(value = "医院")
    private String hospital;
    @ApiModelProperty(value = "医院级别 前端不展示",hidden = true)
    private Integer hospitalLevel;
    @ApiModelProperty(value = "医院级别")
    private String hospitalLevelStr;
    @ApiModelProperty(value = "上次拜访时间")
    private Date lastVisitTime;
    @ApiModelProperty(value = "几天前")
    private String visitTimeStr;

}
