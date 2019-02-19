package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 搜索要用到的动态字段
 * @author tiancun
 * @date 2019-01-30
 */
@Data
@ApiModel(value = "搜索要用到的动态字段,分出类别")
public class SearchDynamicFieldListResponseBean implements Serializable {
    private static final long serialVersionUID = 3196945805944090046L;

    @ApiModelProperty(value = "基本信息动态字段")
    private List<SearchDynamicFieldResponseBean> basic = new ArrayList<>();

    @ApiModelProperty(value = "医院信息动态字段")
    private List<SearchDynamicFieldResponseBean> hospital = new ArrayList<>();

    @ApiModelProperty(value = "处方信息动态字段")
    private List<SearchDynamicFieldResponseBean> prescription = new ArrayList<>();

    @ApiModelProperty(value = "拜访记录信息动态字段")
    private List<SearchDynamicFieldResponseBean> visit = new ArrayList<>();

}
