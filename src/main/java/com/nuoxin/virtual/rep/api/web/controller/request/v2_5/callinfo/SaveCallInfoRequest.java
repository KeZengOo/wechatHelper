package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.questionnaire.VirtualQuestionRequestBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(value = "保存电话拜访接通后的信息")
@EqualsAndHashCode(callSuper=false)
@Data
public class SaveCallInfoRequest  extends BaseCallInfoRequest{
	@ApiModelProperty(value = "医生潜力 3高,2中,1低,-1未知")
	private Integer hcpPotential;
	
	@ApiModelProperty(value = "是否有药 1.有,0.无,-1未知")
	private Integer isHasDrug;
	@ApiModelProperty(value = "是否是目标客户 1.是,0.非,-1未知")
	private Integer isTarget;
	@ApiModelProperty(value = "是否有 AE 1.是,0.非,-1未知")
	private Integer isHasAe;
	@ApiModelProperty(value = "是否招募 1.是,0.非, -1未知")
	private Integer isRecruit;
	
	@NotNull(message="attitude is null")
	@ApiModelProperty(value = "医生态度 0-5")
	private Integer attitude;

	/**
	 * 保存问卷的时候必须选择
 	 */
	@ApiModelProperty(value = "产品ID")
	private Integer productId;
	
//	@NotNull(message="visitResult is null")
//	@ApiModelProperty(value = "拜访结果,以字符串数组形式传入具体文字如[\"成功招募\",\"成功传递\"]")
//	private List<String> visitResult;

	@NotNull(message="拜访结果ID不能为空")
	@ApiModelProperty(value = "")
	private List<Long> visitResultId;

	@NotNull(message="招募状态不能为空")
	@ApiModelProperty(value = "招募状态，1成功招募，2是退出项目")
	private Integer recruitStatus;

	@ApiModelProperty(value = "通话录音地址")
	private String callUrl;
	
	///////////////////////////////////////////////////////
	
	@ApiModelProperty(value = "问卷ID")
	private Integer virtualQuestionaireId;
	@ApiModelProperty(value = "作答结果")
	
	@NotNull(message="questions is null")
	List<VirtualQuestionRequestBean> questions = new ArrayList<>();
}
