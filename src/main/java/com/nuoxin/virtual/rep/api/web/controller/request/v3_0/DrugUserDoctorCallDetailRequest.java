package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 拜访详情请求参数
 * @author tiancun
 * @date 2019-05-13
 */
@Data
@ApiModel
public class DrugUserDoctorCallDetailRequest extends PageRequestBean implements Serializable {
    private static final long serialVersionUID = -7638207011746085656L;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "代表ID")
    private Long drugUserId;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "拜访渠道")
    private Integer visitChannel;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;



}
