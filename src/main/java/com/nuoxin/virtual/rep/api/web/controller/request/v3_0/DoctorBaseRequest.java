package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 医生请求参数的基类
 * @author tiancun
 * @date 2019-04-28
 */
@ApiModel(value = "医生请求参数基类")
@Data
public class DoctorBaseRequest implements Serializable {
    private static final long serialVersionUID = 7309358505480261940L;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "代表ID列表")
    private List<Long> drugUserIdList;

    @ApiModelProperty(value = "0未招募，1招募， -1 未知")
    private Integer recruit;

    @ApiModelProperty(value = "覆盖状态，0未开始，1覆盖中，2退出项目")
    private Integer cover;

    @ApiModelProperty(value = "是否是目标医生，0否 1是，-1 未知")
    private Integer target;

    @ApiModelProperty(value = "是否有药，0否，1是，2临采")
    private Integer hasDrug;

    @ApiModelProperty(value = "搜索的关键词")
    private String searchKeyword;




}
