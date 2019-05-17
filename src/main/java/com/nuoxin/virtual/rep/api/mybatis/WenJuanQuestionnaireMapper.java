package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v3_0.WenJuanAnswer;
import com.nuoxin.virtual.rep.api.entity.v3_0.WenJuanAnswerSheet;
import com.nuoxin.virtual.rep.api.entity.v3_0.WenJuanProject;
import com.nuoxin.virtual.rep.api.entity.v3_0.WenJuanProjectUserAndShortId;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.WenJuanInfoParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.WenJuanInfoRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.WenJuanProjectRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 问卷网API
 * @author wujiang
 * @date 20190426
 */
public interface WenJuanQuestionnaireMapper {

    /**
     * 保存问卷项目
     * @param list
     * @return boolean
     */
    boolean saveWenJuanProject(@Param(value = "list") List<WenJuanProject> list);

    /**
     * 获取库中所有项目的短id
     * @return list
     */
    List<String> shortIdList();

    /**
     * 查询项目列表
     * @return list
     */
    List<WenJuanProjectUserAndShortId> projectList();

    /**
     * 保存问卷网-答卷详情列表
     * @param list
     * @return boolean
     */
    boolean saveAnswerSheet(@Param(value = "list") List<WenJuanAnswerSheet> list);

    /**
     * 保存问卷网-答卷答案表
     * @param list
     * @return boolean
     */
    boolean saveAnswer(@Param(value = "list") List<WenJuanAnswer> list);

    /**
     * 查询问卷网-答卷详情列表
     */
    List<WenJuanAnswerSheet> getAnswerSheetList();

    /**
     * 问卷列表
     * @param wenJuanProjectRequest
     * @return list
     */
    List<WenJuanProject> getWenJuanProjectListPage(@Param(value = "wenJuanProjectRequest") WenJuanProjectRequest wenJuanProjectRequest);

    /**
     * 问卷Count
     * @param wenJuanProjectRequest
     * @return int
     */
    Integer getWenJuanProjectCount(@Param(value = "wenJuanProjectRequest") WenJuanProjectRequest wenJuanProjectRequest);

    /**
     * 编辑问卷所属产品
     * @param projectId
     * @param productId
     * @param productName
     * @return boolean
     */
    boolean wenJuanProductIdAndNameUpdate(@Param(value = "projectId")Integer projectId, @Param(value = "productId")Integer productId, @Param(value = "productName")String productName);

    /**
     * 问卷详情
     * @param wenJuanInfoRequest
     * @return list
     */
    List<WenJuanInfoParams> getWenJuanInfoList(@Param(value = "wenJuanInfoRequest") WenJuanInfoRequest wenJuanInfoRequest);

    /**
     * 问卷详情Count
     * @param wenJuanInfoRequest
     * @return list
     */
    Integer getWenJuanInfoListCount(@Param(value = "wenJuanInfoRequest") WenJuanInfoRequest wenJuanInfoRequest);

    /**
     * 根据项目短ID、答卷序号查询问卷答案集
     * @param telPhone
     * @param shortId
     * @param seq
     * @return list
     */
    List<WenJuanAnswer> getWenJuanAnswerListByShortIdAndSeq(@Param(value = "telPhone") String telPhone, @Param(value = "shortId") String shortId, @Param(value = "seq") Integer seq);
}
