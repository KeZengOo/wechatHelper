package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
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
@ApiModel(value = "代表转医生记录表请求分页")
public class DrugUserDoctorLogPageRequest extends PageRequestBean implements Serializable {

    private static final long serialVersionUID = 8899289226336363398L;

    @NotNull(message = "医生ID不能为空")
    @ApiModelProperty(value = "医生ID")
    private Long doctorId;



}
