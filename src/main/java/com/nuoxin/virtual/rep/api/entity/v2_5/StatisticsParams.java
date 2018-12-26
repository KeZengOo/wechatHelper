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

	private Long productId;
	/**
	 * 产品
	 */
	private String productName;
	/**
	 * 销售代表列表id
	 */
	private List<Long> drugUserIds;
	/**
	 * 销售代表id
	 */
	private Long drugUserId;

	/**
	 * 医生ID
	 */
	private Long doctorId;

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

	/**
	 * 拜访结果类型
	 */
	private Integer visitResultType;

	/**
	 * 拜访渠道
	 */
	private Integer visitChannel;

	/**
	 * 拜访结果ID
	 */
	private List<Long> visitResultIdList;


	/**
	 * 是否是本人互动的，1是本人，其他是全部(自己和转移过来的)
	 */
	private Integer self;

}
