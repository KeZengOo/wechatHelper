package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v3_0.WenJuanProject;
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
     * @return
     */
    boolean saveWenJuanProject(@Param(value = "list") List<WenJuanProject> list);

    /**
     * 获取库中所有项目的短id
     * @return list
     */
    List<String> shortIdList();
}
