package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 首页查询请求类 
 * @author xiekaiyu
 */
@ApiModel
@EqualsAndHashCode(callSuper=false)
@Data
public class SearchRequestBean extends ScreenRequestBean {

	private static final long serialVersionUID = 8545014227620171255L;
	
	/**
	 * 搜索值
	 */
	@ApiModelProperty(value="搜索值")
	private String search;
	
}
