package com.nuoxin.virtual.rep.api.entity.v3_0.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 问卷详情params
 * @author wujiang
 * @date 20190517
 */
@Data
@ApiModel("问卷详情params")
public class WenJuanInfoParams {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "项目短ID")
    private String shortId;
    @ApiModelProperty(value = "医生ID")
    private Long readPersonId;
    @ApiModelProperty(value = "医生名称")
    private String readPersonName;
    @ApiModelProperty(value = "医生手机号")
    private String wechatOpenid;
    @ApiModelProperty(value = "文章ID")
    private Integer dataId;
    @ApiModelProperty(value = "问卷开始时间")
    private String start;
    @ApiModelProperty(value = "问卷结束时间")
    private String finish;
    @ApiModelProperty(value = "问卷标题")
    private String title;
    @ApiModelProperty(value = "问卷答案")
    private String answer;
    @ApiModelProperty(value = "答卷序号")
    private Integer seq;
    @ApiModelProperty(value = "第n题答案")
    private Integer qTop;
}
