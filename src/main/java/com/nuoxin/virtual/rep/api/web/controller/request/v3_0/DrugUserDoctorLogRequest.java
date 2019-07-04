package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 代表转医生记录表
 * @author tiancun
 * @date 2019-07-04
 */
@Data
@ApiModel(value = "代表转医生记录表请求参数")
public class DrugUserDoctorLogRequest  implements Serializable {
    private static final long serialVersionUID = -3012549935094617707L;

    @NotNull(message = "原来代表ID不能为空")
    @ApiModelProperty(value = "原来代表ID")
    private Long oldDrugUserId;

    @NotNull(message = "新的代表ID不能为空")
    @ApiModelProperty(value = "新的代表ID")
    private Long newDrugUserId;

    @NotNull(message = "医生ID不能为空")
    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @NotNull(message = "产品ID不能为空")
    @ApiModelProperty(value = "产品ID")
    private Long productId;

}
