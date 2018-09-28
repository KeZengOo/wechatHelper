package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 首页查询请求类 
 * @author xiekaiyu
 */
@ApiModel
@EqualsAndHashCode(callSuper=false)
@Data
public class ListRequestBean extends ScreenRequestBean {

	private static final long serialVersionUID = 8545014227620171255L;
	
}
