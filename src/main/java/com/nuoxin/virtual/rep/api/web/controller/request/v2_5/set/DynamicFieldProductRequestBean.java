package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tiancun
 * @date 2018-09-18
 */
@ApiModel(value = "产品设置产品信息请求参数")
@Data
public class DynamicFieldProductRequestBean extends PageRequestBean {
    private static final long serialVersionUID = 2333781603018193011L;

    @ApiModelProperty(value = "前端不用传", hidden = true)
    private String leaderPath;


}
