package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 法定节假日请求参数
 * @author tiancun
 * @date 2019-01-08
 */
@Data
@ApiModel(value = "法定节假日请求参数")
public class HolidayRequestBean implements Serializable {
    private static final long serialVersionUID = -5239755974912009463L;
}
