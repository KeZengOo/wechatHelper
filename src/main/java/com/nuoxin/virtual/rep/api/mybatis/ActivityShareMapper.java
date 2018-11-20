package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsDrugNumResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsParams;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share.ShareRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.ContentCommentResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.ContentShareResponseBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 分享记录
 */
@Component
public interface ActivityShareMapper {

	/**
	 * 医生短信拜访数
	 * @param statisticsParams
	 * @return 返回记录总条数
	 */
	List<StatisticsDrugNumResponse> getMessageDoctorVisitCount(StatisticsParams statisticsParams);

	/**
	 * 内容服务人数(内容分享的医生人数)
	 * @param statisticsParams
	 * @return 返回记录总条数
	 */
	List<StatisticsDrugNumResponse> getContentServiceCount(StatisticsParams statisticsParams);


	/**
	 * 内容服务人数(内容分享的医生人数)：医生ID列表
	 * @param statisticsParams
	 * @return
	 */
	List<Long> getContentServiceDoctorIdList(StatisticsParams statisticsParams);

	/**
	 * 内容服务人数(内容分享的医生人数，内容分享状态为服务的人数)
	 * @param statisticsParams
	 * @return 返回记录总条数
	 */
	List<StatisticsDrugNumResponse> getContentStatusServiceCount(StatisticsParams statisticsParams);

	/**
	 * 内容服务人数(内容分享的医生人数，内容分享状态为服务的人数):内容服务人数(内容分享的医生人数)：医生ID列表
	 * @param statisticsParams
	 * @return 返回记录总条数
	 */
	List<Long> getContentStatusServiceDoctorIdList(StatisticsParams statisticsParams);


	/**
	 * 内容发送人数
	 * @param statisticsParams
	 * @return 返回记录总条数
	 */
	List<StatisticsDrugNumResponse> getContentSendCount(StatisticsParams statisticsParams);

	/**
	 * 内容阅读人数
	 * @param statisticsParams
	 * @return 返回记录总条数
	 */
	List<StatisticsDrugNumResponse> getContentReadCount(StatisticsParams statisticsParams);

	/**
	 * 内容阅读时长
	 * @param statisticsParams
	 * @return 返回记录总条数
	 */
	List<StatisticsDrugNumResponse> getContentReadTimeCount(StatisticsParams statisticsParams);

	/**
	 * 内容推送记录列表
	 * @param bean
	 * @return
	 */
	List<ContentShareResponseBean> getContentShareList(ShareRequestBean bean);

	/**
	 * 内容推送记录列表总数
	 * @param bean
	 * @return
	 */
	Integer getContentShareListCount(ShareRequestBean bean);

	/**
	 * 得到内容的评论
	 * @param doctorId
	 * @param contentId
	 * @return
	 */
	List<ContentCommentResponseBean> getContentCommentList(@Param(value = "doctorId") Long doctorId,@Param(value = "contentId") Long contentId);

	/**
	 * 新增分享类型
	 * @param shareId
	 * @param shareStatus
	 */
	void addShareStatus(@Param(value = "shareId") Long shareId,@Param(value = "shareStatus") Integer shareStatus);

	/**
	 * 更新分享类型
	 * @param shareId
	 * @param shareStatus
	 */
	void updateShareStatus(@Param(value = "shareId") Long shareId,@Param(value = "shareStatus") Integer shareStatus);

	/**
	 * 查询分享类型，判断是否已经存在
	 * @param shareId
	 * @return
	 */
	Integer getShareStatusCount(@Param(value = "shareId") Long shareId);
}

