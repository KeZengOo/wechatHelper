package com.nuoxin.virtual.rep.api.entity.v2_5;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import lombok.Data;

import java.util.List;

/**
 * 拜访统计入参
 */
@Data
public class StatisticsParams extends PageRequestBean {
	/**
	 * 产品id
	 */

	private Integer productId;
	/**
	 * 销售代表列表id
	 */
	private List<Integer> drugUserIds;
	/**
	 * 销售代表id
	 */
	private Integer drugUserId;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 电话回访结果
	 */
	private List<String> contents;
	/**
	 * 1 列表 2 导出
	 */
	private Integer  type;
}
