package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 产品分型拜访频次请求参数
 * @author tiancun
 * @date 2019-01-04
 */
@Data
@ApiModel(value = "产品分型拜访频次请求参数, 用于更新")
public class ProductClassificationFrequencyUpdateRequestBean extends ProductClassificationFrequencyRequestBean implements Serializable {

    @ApiModelProperty(value = "批次")
    @NotNull(message = "batchNo不能为空")
    private String batchNo;

}
