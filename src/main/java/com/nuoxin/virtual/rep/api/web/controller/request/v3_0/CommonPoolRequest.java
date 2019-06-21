package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

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

    @ApiModelProperty(value = "上次拜访时间排序，up 是时间升序，down 是时间降序, 默认是降序")
    private String lastVisitTimeOrder = "down";

    @ApiModelProperty(value = "是否是可分页的,0和null 可以分页的，其他是可以分页的")
    private Integer paginable;

    @ApiModelProperty(value = "医生ID列表")
    private List<Long> doctorIdList;

    @ApiModelProperty(value = "拜访结果ID")
    private List<Long> resultIdList;

}
