package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 公共池请求参数
 * @author tiancun
 * @date 2019-04-28
 */
@Data
@ApiModel(value = "公共池请求参数")
public class CommonPoolRequest extends DoctorBaseRequest implements Serializable {
    private static final long serialVersionUID = -5912106098368472822L;

    @ApiModelProperty(value = "是否关联代表，1是关联，0是未关联，其他展示全部")
    private Integer relationDrugUser;




}
