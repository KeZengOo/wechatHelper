package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.questionnaire;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 医生详情产品问卷请求数据
 * @author tiancun
 * @date 2018-11-06
 */
@Data
@ApiModel(value = "医生详情产品问卷请求数据")
public class ProductQuestionnaireRequestBean extends PageRequestBean {
    private static final long serialVersionUID = 4614595982535279607L;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "产品ID")
    private Long productId;





}
