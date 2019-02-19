package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 高级搜索输入的值
 * @author tiancun
 * @date 2019-01-31
 */
@Data
public class SearchDynamicFieldRequestBean implements Serializable {
    private static final long serialVersionUID = 6197334354410694197L;

    @ApiModelProperty(value = "动态字段ID")
    private Long dynamicFieldId;


    @ApiModelProperty(value = "动态字段输入的值")
    private List<String> dynamicFieldValueList;

}
