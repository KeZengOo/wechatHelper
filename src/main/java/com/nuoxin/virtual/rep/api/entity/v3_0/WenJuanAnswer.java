package com.nuoxin.virtual.rep.api.entity.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 问卷网-答卷答案表
 * @author wujiang
 * @date 20190427
 */
@ApiModel("问卷网-答卷答案表")
@Data
public class WenJuanAnswer {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "答题内容")
    private String answer;
    @ApiModelProperty(value = "题目类型")
    private String typeDesc;
    @ApiModelProperty(value = "题目标题")
    private String title;
    @ApiModelProperty(value = "答卷序号")
    private Integer seq;
    @ApiModelProperty(value = "项目短id")
    private String shortId;
    @ApiModelProperty(value = "用户编号")
    private String user;
    @ApiModelProperty(value = "第n题答案")
    private Integer qTop;
    @ApiModelProperty(value = "答题医生电话")
    private String telPhone;
}
