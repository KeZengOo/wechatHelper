package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.common.constant.TimeCronConstant;
import com.nuoxin.virtual.rep.api.common.constant.WenJuanApiConstant;
import com.nuoxin.virtual.rep.api.entity.v3_0.ScheduleResult;
import com.nuoxin.virtual.rep.api.entity.v3_0.WenJuanProject;
import com.nuoxin.virtual.rep.api.mybatis.WenJuanQuestionnaireMapper;
import com.nuoxin.virtual.rep.api.service.v3_0.WenJuanQuestionnaireService;
import com.nuoxin.virtual.rep.api.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 问卷网
 * @author wujiang
 */
@Component
@Service
public class WenJuanQuestionnaireServiceImpl implements WenJuanQuestionnaireService {

    @Resource
    private WenJuanQuestionnaireMapper wenJuanQuestionnaireMapper;
    @Resource
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(WenJuanQuestionnaireServiceImpl.class);

    @Override
    public String wenJuanLogin(){
        //当前时间戳
        long timestamp = System.currentTimeMillis()/1000;
        //问卷网登录接口
        //生产MD5_signature签名
        String md5Signature = WenJuanApiConstant.WJ_APPKEY_VALUE
                +timestamp+WenJuanApiConstant.WJ_USER_VALUE
                +WenJuanApiConstant.WJ_APPSECRET_VALUE;
        String md5Result = MD5Util.MD5Encode(md5Signature,"utf8");

        String url =WenJuanApiConstant.URL+WenJuanApiConstant.LOGIN+"?"+WenJuanApiConstant.WJ_APPKEY+"="+WenJuanApiConstant.WJ_APPKEY_VALUE
                +"&"+WenJuanApiConstant.WJ_USER+"="+WenJuanApiConstant.WJ_USER_VALUE
                +"&"+WenJuanApiConstant.WJ_TIMESTAMP+"="+timestamp
                +"&"+WenJuanApiConstant.WJ_SIGNATURE+"="+md5Result;
        String result = restTemplate.getForObject(url,String.class);

        return result;
    }

    @Scheduled(cron = "0 47 12 * * ?")
    @Override
    public ScheduleResult saveWenJuanProject() {
        boolean tag = false;
        String loginResult = wenJuanLogin();
        logger.info(loginResult);

        //当前时间戳
        long timestamp = System.currentTimeMillis()/1000;
        //问卷网项目接口
        String projectMd5Signature = WenJuanApiConstant.WJ_APPKEY_VALUE
                +WenJuanApiConstant.WJ_DATATYPE_JSON
                +timestamp
                +WenJuanApiConstant.WJ_APPSECRET_VALUE;
        String projectMd5SignatureResult = MD5Util.MD5Encode(projectMd5Signature,"utf8");
        String projectUrl = WenJuanApiConstant.URL+WenJuanApiConstant.GET_PROJ_LIST+"?"+WenJuanApiConstant.WJ_APPKEY+"="+WenJuanApiConstant.WJ_APPKEY_VALUE
                +"&"+WenJuanApiConstant.WJ_DATATYPE+"="+WenJuanApiConstant.WJ_DATATYPE_JSON
                +"&"+WenJuanApiConstant.WJ_TIMESTAMP+"="+timestamp+"&"+WenJuanApiConstant.WJ_SIGNATURE+"="+projectMd5SignatureResult;
        String projectResult = restTemplate.getForObject(projectUrl,String.class);

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
        ScheduleResult scheduleResult = new ScheduleResult();
        scheduleResult.setResult(tag);
        return scheduleResult;
    }

    /**
     * 检查数组是否包含某个值
     */
    public static boolean arrayExistValue(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    
}
