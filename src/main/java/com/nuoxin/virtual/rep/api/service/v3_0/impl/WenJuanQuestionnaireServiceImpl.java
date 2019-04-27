package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.entity.v3_0.WenJuanProject;
import com.nuoxin.virtual.rep.api.mybatis.WenJuanQuestionnaireMapper;
import com.nuoxin.virtual.rep.api.service.v3_0.WenJuanQuestionnaireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 问卷网
 * @author wujiang
 */
@Service
public class WenJuanQuestionnaireServiceImpl implements WenJuanQuestionnaireService {

    @Resource
    private WenJuanQuestionnaireMapper wenJuanQuestionnaireMapper;

    private static final Logger logger = LoggerFactory.getLogger(WenJuanQuestionnaireServiceImpl.class);

    @Override
    public boolean saveWenJuanProject(String projectResult) {
        boolean tag = false;
        JSONObject jsonObject = JSONObject.parseObject(projectResult);
        JSONArray jan = (JSONArray) jsonObject.get("project_list");
        List<WenJuanProject> list = new ArrayList<WenJuanProject>();
        //接口中获取的项目放入列表对象中
        if(jan!=null||jan.size()!=0){
            for(int i=0;i<jan.size();i++){
                WenJuanProject wenJuanProject = new WenJuanProject();
                JSONObject jsonObject1 = (JSONObject) jan.get(i);
                wenJuanProject.setShortId(jsonObject1.getString("short_id"));
                wenJuanProject.setProjectId(jsonObject1.getString("project_id"));
                wenJuanProject.setTitle(jsonObject1.getString("title"));
                wenJuanProject.setStatus(jsonObject1.getInteger("status"));
                wenJuanProject.setUser(jsonObject1.getString("user"));
                wenJuanProject.setRespondentCount(jsonObject1.getString("respondent_count"));
                wenJuanProject.setPtype(jsonObject1.getString("ptype"));
                wenJuanProject.setCreateTime(jsonObject1.getString("create_time"));
                wenJuanProject.setUpdateTime(jsonObject1.getString("update_time"));
                list.add(wenJuanProject);
            }
        }

        //数据库中不存在的项目存放在该列表中
        List<WenJuanProject> notExistShortIdEntity = new ArrayList<WenJuanProject>();

        //获取库中所有项目的短id
        List<String> shortIdList = wenJuanQuestionnaireMapper.shortIdList();
        if(shortIdList.size() > 0) {
            //ListToArray
            String[] shortIdArray = new String[list.size()];
            shortIdList.toArray(shortIdArray);

            //判断接口的项目数据数据库中是否存在
            for (int i = 0; i < list.size(); i++) {
                boolean result = arrayExistValue(shortIdArray, list.get(i).getShortId());
                if (!result) {
                    WenJuanProject wenJuanProject = new WenJuanProject();
                    wenJuanProject.setShortId(list.get(i).getShortId());
                    wenJuanProject.setProjectId(list.get(i).getProjectId());
                    wenJuanProject.setTitle(list.get(i).getTitle());
                    wenJuanProject.setStatus(list.get(i).getStatus());
                    wenJuanProject.setUser(list.get(i).getUser());
                    wenJuanProject.setRespondentCount(list.get(i).getRespondentCount());
                    wenJuanProject.setPtype(list.get(i).getPtype());
                    wenJuanProject.setCreateTime(list.get(i).getCreateTime());
                    wenJuanProject.setUpdateTime(list.get(i).getUpdateTime());
                    notExistShortIdEntity.add(wenJuanProject);
                }
            }
        }

        if (notExistShortIdEntity.size() > 0)
        {
            tag = wenJuanQuestionnaireMapper.saveWenJuanProject(notExistShortIdEntity);
            logger.info("数据库中存在数据，保存最新数据入库");
        }
        else if(shortIdList.size() == 0)
        {
            tag = wenJuanQuestionnaireMapper.saveWenJuanProject(list);
            logger.info("数据库中没有数据，保存新数据入库");
        }
        else
        {
            logger.info("没有数据可以更新");
        }

        return tag;
    }

    /**
     * 检查数组是否包含某个值
     */
    public static boolean arrayExistValue(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }
}
