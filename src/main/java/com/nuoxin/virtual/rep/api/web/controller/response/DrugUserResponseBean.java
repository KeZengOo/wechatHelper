package com.nuoxin.virtual.rep.api.web.controller.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by fenggang on 10/18/17.
 */
@ApiModel
@Data
public class DrugUserResponseBean implements Serializable {

	private static final long serialVersionUID = -8927315455674282592L;

	@ApiModelProperty(value = "代表ID")
    private Long id;
    @ApiModelProperty(value = "代表名称")
    private String name;

}
