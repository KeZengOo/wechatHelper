package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 拜访数据汇总请求参数
 * @author tiancun
 * @date 2019-04-28
 */
@Data
@ApiModel(value = "拜访数据汇总请求参数")
public class VisitSummaryRequest extends CommonRequest implements Serializable {
    private static final long serialVersionUID = -2644359249400599116L;

}
