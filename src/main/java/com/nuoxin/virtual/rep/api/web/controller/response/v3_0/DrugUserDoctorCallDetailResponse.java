package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 代表和医生电话拜访详情
 * @author tiancun
 * @date 2019-05-09
 */
@Data
@ApiModel(value = "代表和医生电话拜访详情")
public class DrugUserDoctorCallDetailResponse implements Serializable{

    private static final long serialVersionUID = -6410281198863746859L;

    @ApiModelProperty(value = "本条数据ID")
    private Long id;

    @ApiModelProperty(value = "外呼时间")
    private String createTime;

    @JsonIgnore
    @ApiModelProperty(value = "外呼秒数")
    private Long callSecond;

    @ApiModelProperty(value = "外呼时长")
    private String callTime;

    @ApiModelProperty(value = "拜访目的")
    private String purpose;

    @ApiModelProperty(value = "拜访结果")
    private String visitResult;

    @ApiModelProperty(value = "录音url")
    private String callUrl;

    @ApiModelProperty(value = "录音识别文本")
    private String callText;


    @ApiModelProperty(value = "拜访渠道对应值")
    private Integer visitChannel;

    @ApiModelProperty(value = "拜访渠道")
    private String visitChannelStr;


}
