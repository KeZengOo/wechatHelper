package com.nuoxin.virtual.rep.api.web.controller.response.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Create by tiancun on 2017/10/17
 */
@ApiModel(value = "坐席分析返回数据")
public class DrugUserAnalysisListResponseBean implements Serializable{
    private static final long serialVersionUID = -645082110497219239L;


    private List<DrugUserAnalysisResponseBean> minCallTotalList;

}
