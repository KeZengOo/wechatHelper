package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import java.io.Serializable;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.ListRequestBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户跟进列表类
 * @author xiekaiyu
 */
@ApiModel
@EqualsAndHashCode(callSuper=false)
@Data
public class CustomerFollowScreenMoreListBean extends PageRequestBean implements Serializable {

	private static final long serialVersionUID = 4838937055702231658L;
	
	/**
	 * 最后一次电话拜访时间
	 */
	@ApiModelProperty(value="最后一次电话拜访时间")
	private String lastVisitTime;
	/**
	 * 最后一次电话拜访状态 
	 */
	@ApiModelProperty(value="最后一次电话拜访状态 ")
	private String statusName;
	/**
	 * 距当前时间N天内拜访过的
	 */
	@ApiModelProperty(value="距当前时间N天内拜访过的")
	private Integer lastNDaysVisited;
	/**
	 * 距当前时间N天内未拜访过的
	 */
	@ApiModelProperty(value="距当前时间N天内未拜访过的 ")
	private Integer lastNDaysNotVisited;
	/**
	 * 是否添加微信:1已添加,0未添加,-1全部
	 */
	@ApiModelProperty(value="是否添加微信:1已添加,0未添加,-1全部")
	private Integer isHashWeChat;
	
	/**
	 * 是否添加招募:1已招募,0未招募,-1全部
	 */
	@ApiModelProperty(value="是否添加招募:1已招募,0未招募,-1全部")
	private Integer isRecruited;
	/**
	 * 医生潜力:1高,2中,3低,-1全部
	 */
	@ApiModelProperty(value="医生潜力:1高,2中,3低,-1全部")
	private Integer docPotential;
	/**
	 * 医生等级
	 */
	@ApiModelProperty(value="医生等级")
	private String hcpLevel;
	/**
	 * 是否有药:1.有,0.无,-1未知
	 */
	@ApiModelProperty(value="是否有药:1.有,0.无,-1未知")
	private Integer isHasDrug;
	@ApiModelProperty(value="医院名")
	private String hci;
	@ApiModelProperty(value="医院所在省")
	private String hciProvince;
	@ApiModelProperty(value="医院所在市")
	private String hciCity;
	@ApiModelProperty(value="医院等级")
	private String hciLevel;
	@ApiModelProperty(value="医院类型")
	private String hciType;
}
