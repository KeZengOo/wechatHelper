package com.nuoxin.virtual.rep.api.entity.v3_0;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 问卷网-项目表
 * @author wujiang
 * @date 20190426
 */
@Data
public class WenJuanProject {

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "用户编号")
    private String user;
    @ApiModelProperty(value = "项目短ID")
    private String shortId;
    @ApiModelProperty(value = "项目ID")
    private String projectId;
    @ApiModelProperty(value = "项目标题")
    private String title;
    @ApiModelProperty(value = "项目状态；0:未发布, 1:收集中, 2:已结束, 3:暂停中, -2:已删除,-1:永久删除")
    private Integer status;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "答题数量")
    private String respondentCount;
    @ApiModelProperty(value = "项目类型")
    private String ptype;
}
