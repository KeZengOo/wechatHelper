package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 公共池医生返回数据
 * @author tiancun
 * @date 2019-05-07
 */
@Data
@ApiModel(value = "公共池医生返回数据")
public class CommonPoolDoctorResponse extends DoctorBaseResponse implements Serializable {
    private static final long serialVersionUID = -9019688284751624575L;

    @JsonIgnore
    private Long maxVisitId;

    @ApiModelProperty(value = "上一次拜访时间")
    private String lastVisitTime;

    @ApiModelProperty(value = "拜访的代表ID")
    private Long visitDrugUserId;

    @ApiModelProperty(value = "拜访的代表姓名")
    private String visitDrugUserName;

    @ApiModelProperty(value = "拜访结果")
    private String visitResult;


}
