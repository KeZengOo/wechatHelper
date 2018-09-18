package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup;

import java.util.List;

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
public class ScreenRequestBean extends PageRequestBean {

	private static final long serialVersionUID = 8545014227620171255L;
	
	/**
	 * 虚拟代表 ids
	 */
	@ApiModelProperty(value="虚拟代表ids")
	private List<Long> virtualDrugUserIds;
	/**
	 * 产品线 id
	 */
	@ApiModelProperty(value="产品线ids")
	private List<Long> productLineIds;
	
}
