package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 产品下分型设置
 * @author tiancun
 * @date 2019-01-03
 */
@Data
@ApiModel(value = "产品下分型设置")
public class ProductClassificationRequestBean implements Serializable {
    private static final long serialVersionUID = -7717048082817229047L;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "字段对应的值，多个值用英文逗号分开")
    private String fieldValue;

    /**
     * 字段对应的值,供后端入库
     */
    @JsonIgnore
    private List<String> fieldValueList;

    @ApiModelProperty(value = "字段类型，1是文本,2是下拉框")
    private Integer fieldType;

    @ApiModelProperty(value = "1是必填，0是非必填")
    private Integer required;

    @ApiModelProperty(value = "没有填写是否提示补充，0是不提示，1是提示")
    private Integer supplement;

}
