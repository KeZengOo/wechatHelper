package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 我的客户请求参数
 * @author tiancun
 * @date 2019-04-28
 */
@ApiModel(value = "我的客户请求参数")
@Data
public class MyDoctorRequest extends DoctorBaseRequest implements Serializable {
    private static final long serialVersionUID = 7386171743701861387L;

    @ApiModelProperty(value = "搜索的标签, 1、需要会议回访，2、新转入医生，3、超过10天未拜访，4、阅读文章但为拜访，5、今日需拜访的，6最近一次未接通")
    private Integer tag;

    @ApiModelProperty(value = "上次拜访时间排序，up 是时间升序，down 是时间降序, 默认是降序")
    private String lastVisitTimeOrder = "down";



}
