package com.nuoxin.virtual.rep.api.service.v3_0;

import com.nuoxin.virtual.rep.api.entity.v3_0.ScheduleResult;

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
}
