package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 医生列表返回数据
 * @author tiancun
 * @date 2019-04-28
 */
@ApiModel(value = "医生列表返回数据")
@Data
public class MyDoctorResponse extends DoctorBaseResponse implements Serializable {
    private static final long serialVersionUID = 4482881991099415089L;

    @JsonIgnore
    private Long maxVisitId;

    @JsonIgnore
    private Date lastVisitDate;

    @ApiModelProperty(value = "上一次拜访时间")
    private String lastVisitTime;

    @ApiModelProperty(value = "拜访的代表ID")
    private Long visitDrugUserId;

    @ApiModelProperty(value = "拜访的代表姓名")
    private String visitDrugUserName;

    @ApiModelProperty(value = "拜访结果")
    private String visitResult;

}
