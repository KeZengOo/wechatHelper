package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v3_0.WenJuanAnswer;
import com.nuoxin.virtual.rep.api.entity.v3_0.WenJuanAnswerSheet;
import com.nuoxin.virtual.rep.api.entity.v3_0.WenJuanProject;
import com.nuoxin.virtual.rep.api.entity.v3_0.WenJuanProjectUserAndShortId;
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
}
