package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 医生潜力，医生分型新增返回的列表数据
 * @author tiancun
 * @date 2019-01-22
 */
@ApiModel(value = "医生潜力，医生分型新增返回的列表数据")
@Data
public class PotentialClassificationResponseBean implements Serializable {
    private static final long serialVersionUID = 7720461826809612574L;

    @ApiModelProperty(value = "医生潜力字段")
    private String potential = "";

    @ApiModelProperty(value = "潜力字段类型，文本、单选、下拉")
    private Integer potentialType;

    @ApiModelProperty(value = "医生潜力")
    private List<String> potentialList = new ArrayList<>();

    @ApiModelProperty(value = "医生分型字段")
    private String classification = "";

    @ApiModelProperty(value = "分型字段类型，文本、单选、下拉")
    private Integer classificationType;

    @ApiModelProperty(value = "医生分型")
    private List<String> classificationList = new ArrayList<>();

    @ApiModelProperty(value = "拜访频次字段名称")
    private String visitFrequency = "";
}
