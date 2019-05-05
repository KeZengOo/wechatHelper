package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 医生导入返回的错误
 * @author tiancun
 * @date 2019-04-30
 */
@Data
@ApiModel(value = "医生导入返回的错误")
public class DoctorImportErrorResponse implements Serializable {
    private static final long serialVersionUID = 6698690987539253161L;

    @ApiModelProperty(value = "sheet名称")
    private String sheetName;

    @ApiModelProperty(value = "总数")
    private Integer totalNum;

    @ApiModelProperty(value = "重复数")
    private Integer repeatNum;

    @ApiModelProperty(value = "成功总数")
    private Integer successNum;

    @ApiModelProperty(value = "失败总数")
    private Integer failNum;

    @ApiModelProperty(value = "错误明细")
    private List<DoctorImportErrorResponse> detailList = new ArrayList<>();

}
