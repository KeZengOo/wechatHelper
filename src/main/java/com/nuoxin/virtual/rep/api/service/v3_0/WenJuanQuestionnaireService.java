package com.nuoxin.virtual.rep.api.service.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v3_0.ScheduleResult;
import com.nuoxin.virtual.rep.api.entity.v3_0.WenJuanProject;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.WenJuanInfoParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.WenJuanInfoRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.WenJuanProjectRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 问卷网
 * @author wujiang
 * @date 20190425
 */
public interface WenJuanQuestionnaireService {

    /**
     * 问卷网登录接口
     * @return String
     */
    String wenJuanLogin();

    /**
     * 保存项目列表
     * @return ScheduleResult
     */
    ScheduleResult saveWenJuanProject();

    /**
     * 获取答卷详情列表
     * @return ScheduleResult
     */
    ScheduleResult saveWenJuanAnswerSheetInfo();

    /**
     * 获取答题者最新一条答卷详情
     * @return ScheduleResult
     */
    ScheduleResult saveWenJuanNewAnswerSheetInfo();

    /**
     * 问卷列表
     * @param wenJuanProjectRequest
     * @return list
     */
    PageResponseBean<List<WenJuanProject>> getWenJuanProjectListPage(WenJuanProjectRequest wenJuanProjectRequest);

    /**
     * 编辑问卷所属产品
     * @param projectId
     * @param productId
     * @param productName
     * @return boolean
     */
    boolean wenJuanProductIdAndNameUpdate(Integer projectId, Integer productId, String productName);

    /**
     * 问卷详情
     * @param wenJuanInfoRequest
     * @return list
     */
    PageResponseBean<List<WenJuanInfoParams>> getWenJuanInfoListPage(WenJuanInfoRequest wenJuanInfoRequest);

    /**
     * 医生的问卷详情下载
     * @param telPhone
     * @param shortId
     * @param seq
     * @param response
     */
    void wenJuanInfoExportFile(String telPhone, String shortId, Integer seq, HttpServletResponse response);


    /**
     * 跟新问卷答案手机号
     * @param startDate
     * @param endDate
     */
    void updateWjAnswerTelephone(Date startDate, Date endDate);


}
