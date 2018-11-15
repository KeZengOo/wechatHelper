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

	@ApiModelProperty(value = "排序规则，默认是下次拜访时间由近及远排序，1下次拜访时间由远及近排序，" +
			"2上次拜访时间由近及远，3是上次拜访时间由远及近，4是创建时间由近及远，5是创建时间由远及近")
	private Integer order;

}
