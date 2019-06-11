package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.constant.WenJuanApiConstant;
import com.nuoxin.virtual.rep.api.entity.v3_0.*;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.WenJuanInfoParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.WenJuanInfoRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.WenJuanProjectRequest;
import com.nuoxin.virtual.rep.api.mybatis.WenJuanQuestionnaireMapper;
import com.nuoxin.virtual.rep.api.service.v3_0.WenJuanQuestionnaireService;
import com.nuoxin.virtual.rep.api.utils.EmojiUtil;
import com.nuoxin.virtual.rep.api.utils.MD5Util;
import com.nuoxin.virtual.rep.api.utils.csv.PublicGlobalCSVExprot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

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

    /**
     * 问卷网项目接口URL分页拼接方法
     * @param page
     * @param pageSize
     * @return string
     */
    public String projectUrlString(String page, String pageSize){
        if(page.equals("")){
            page = "1";
        }
        if(pageSize.equals("")){
            pageSize = "50";
        }

        //当前时间戳
        long timestamp = System.currentTimeMillis()/1000;
        //问卷网项目接口
        String projectMd5Signature = WenJuanApiConstant.WJ_APPKEY_VALUE
                +WenJuanApiConstant.WJ_DATATYPE_JSON
                +page
                +pageSize
                +timestamp
                +WenJuanApiConstant.WJ_USER_VALUE
                +WenJuanApiConstant.WJ_APPSECRET_VALUE;
        String projectMd5SignatureResult = MD5Util.MD5Encode(projectMd5Signature,"utf8");

        String projectUrl = WenJuanApiConstant.URL+WenJuanApiConstant.GET_PROJ_LIST+"?"+WenJuanApiConstant.WJ_APPKEY+"="+WenJuanApiConstant.WJ_APPKEY_VALUE
                +"&"+WenJuanApiConstant.WJ_DATATYPE+"="+WenJuanApiConstant.WJ_DATATYPE_JSON
                +"&"+WenJuanApiConstant.WJ_PAGE+"="+page
                +"&"+WenJuanApiConstant.WJ_PAGESIZE+"="+pageSize
                +"&"+WenJuanApiConstant.WJ_TIMESTAMP+"="+timestamp
                +"&"+WenJuanApiConstant.WJ_USER+"="+WenJuanApiConstant.WJ_USER_VALUE
                +"&"+WenJuanApiConstant.WJ_SIGNATURE+"="+projectMd5SignatureResult;

        return projectUrl;
    }

    /**
     * 保存项目列表
     * @return ScheduleResult
     */
    @Scheduled(cron = "0 0 0/1 * * ? ")
    @Override
    public ScheduleResult saveWenJuanProject() {
        boolean tag = false;
        //登录问卷网API
        String loginResult = wenJuanLogin();
        logger.info(loginResult);

        String projectUrl = projectUrlString("","");
        String projectResult = restTemplate.getForObject(projectUrl,String.class);
        JSONObject jsonObject = JSONObject.parseObject(projectResult);
        //获取项目总数
        Integer totalCount = Integer.parseInt(jsonObject.get("total_count").toString());
        logger.info("共"+totalCount+"条数据");
        List<WenJuanProject> list = new ArrayList<WenJuanProject>();
        //数据库中不存在的项目存放在该列表中
        List<WenJuanProject> notExistShortIdEntity = new ArrayList<WenJuanProject>();
        //获取库中所有项目的短id
        List<String> shortIdList = wenJuanQuestionnaireMapper.shortIdList();
        //根据总数求总页数
        Integer pages = totalCount/WenJuanApiConstant.WJ_PAGESIZE_VALUE;
        logger.info("共"+pages+"页，每页50条");
        for (int i = 1; i <= pages+1; i++){
            logger.info("当前请求页为："+i+"页");
            String projectPageUrl = projectUrlString(i+"",WenJuanApiConstant.WJ_PAGESIZE_VALUE+"");
            String projectPageResult = restTemplate.getForObject(projectPageUrl,String.class);
            JSONObject jsonPageObject = JSONObject.parseObject(projectPageResult);
            JSONArray jan = (JSONArray) jsonPageObject.get("project_list");

            //接口中获取的项目放入列表对象中
            if(jan!=null||jan.size()!=0){
                for(int j=0;j<jan.size();j++){
                    WenJuanProject wenJuanProject = new WenJuanProject();
                    JSONObject jsonObject1 = (JSONObject) jan.get(j);
                    wenJuanProject.setShortId(jsonObject1.getString("short_id"));
                    wenJuanProject.setProjectId(jsonObject1.getString("project_id"));
                    wenJuanProject.setTitle(jsonObject1.getString("title"));
                    wenJuanProject.setStatus(jsonObject1.getInteger("status"));
                    wenJuanProject.setUser(WenJuanApiConstant.WJ_USER_VALUE);
                    wenJuanProject.setRespondentCount(jsonObject1.getString("respondent_count"));
                    wenJuanProject.setPtype(jsonObject1.getString("ptype"));
                    wenJuanProject.setCreateTime(jsonObject1.getString("create_time"));
                    wenJuanProject.setUpdateTime(jsonObject1.getString("update_time"));
                    list.add(wenJuanProject);
                }
            }

            if(shortIdList.size() > 0) {
                //ListToArray
                String[] shortIdArray = new String[list.size()];
                shortIdList.toArray(shortIdArray);

                //判断接口的项目数据数据库中是否存在
                for (int j = 0; j < list.size(); j++) {
                    boolean result = arrayExistValue(shortIdArray, list.get(j).getShortId());
                    if (!result) {
                        WenJuanProject wenJuanProject = new WenJuanProject();
                        wenJuanProject.setShortId(list.get(j).getShortId());
                        wenJuanProject.setProjectId(list.get(j).getProjectId());
                        wenJuanProject.setTitle(list.get(j).getTitle());
                        wenJuanProject.setStatus(list.get(j).getStatus());
                        wenJuanProject.setUser(list.get(j).getUser());
                        wenJuanProject.setRespondentCount(list.get(j).getRespondentCount());
                        wenJuanProject.setPtype(list.get(j).getPtype());
                        wenJuanProject.setCreateTime(list.get(j).getCreateTime());
                        wenJuanProject.setUpdateTime(list.get(j).getUpdateTime());
                        notExistShortIdEntity.add(wenJuanProject);
                    }
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
     * 问卷网答卷接口URL分页拼接方法
     * @param page
     * @param pageSize
     * @param user
     * @param shortId
     * @return string
     */
    public String answerSheetInfoUrlString(String page, String pageSize, String user, String shortId){
        if(page.equals("")){
            page = "1";
        }
        if(pageSize.equals("")){
            pageSize = "50";
        }

        //当前时间戳
        long timestamp = System.currentTimeMillis()/1000;
        //问卷网项目接口
        String projectMd5Signature = WenJuanApiConstant.WJ_APPKEY_VALUE
                +WenJuanApiConstant.WJ_DATATYPE_JSON
                +page
                +pageSize
                +shortId
                +timestamp
                +user
                +WenJuanApiConstant.WJ_APPSECRET_VALUE;
        String projectMd5SignatureResult = MD5Util.MD5Encode(projectMd5Signature,"utf8");

        String projectUrl = WenJuanApiConstant.URL+WenJuanApiConstant.GET_RSPD_DETAIL_LIST+"?"+WenJuanApiConstant.WJ_APPKEY+"="+WenJuanApiConstant.WJ_APPKEY_VALUE
                +"&"+WenJuanApiConstant.WJ_DATATYPE+"="+WenJuanApiConstant.WJ_DATATYPE_JSON
                +"&"+WenJuanApiConstant.WJ_PAGE+"="+page
                +"&"+WenJuanApiConstant.WJ_PAGESIZE+"="+pageSize
                +"&"+WenJuanApiConstant.WJ_SHORT_ID+"="+shortId
                +"&"+WenJuanApiConstant.WJ_TIMESTAMP+"="+timestamp
                +"&"+WenJuanApiConstant.WJ_USER+"="+user
                +"&"+WenJuanApiConstant.WJ_SIGNATURE+"="+projectMd5SignatureResult;

        return projectUrl;
    }

    /**
     * 获取答卷详情列表
     * @return ScheduleResult
     */
    @Scheduled(cron = "0 2 0/1 * * ? ")
    @Override
    public ScheduleResult saveWenJuanAnswerSheetInfo() {
        //登录问卷网API
        String loginResult = wenJuanLogin();
        logger.info(loginResult);

        List<WenJuanAnswerSheet> list = new ArrayList<WenJuanAnswerSheet>();
        List<WenJuanAnswer> wenJuanAnswerList = new ArrayList<WenJuanAnswer>();
        //查询项目列表
        List<WenJuanProjectUserAndShortId> projectList = wenJuanQuestionnaireMapper.projectList();
        //查询问卷网-答卷详情列表
        List<WenJuanAnswerSheet> answerSheetList = new ArrayList<WenJuanAnswerSheet>();
        answerSheetList = wenJuanQuestionnaireMapper.getAnswerSheetList();

        String answerUrl = "";
        for (int i = 0; i < projectList.size(); i++)
        {
            answerUrl = answerSheetInfoUrlString("","",projectList.get(i).getUser(),projectList.get(i).getShortId());
            logger.info("answerUrl："+answerUrl);
            String answerSheetInfoPageResult = restTemplate.getForObject(answerUrl,String.class);
            logger.info("answerSheetInfoPageResult："+answerSheetInfoPageResult);

            if(!answerSheetInfoPageResult.equals("[]")){
                JSONObject jsonPageObject = JSONObject.parseObject(answerSheetInfoPageResult);
                JSONArray jan = (JSONArray) jsonPageObject.get("rspd_list");

                //接口中获取的项目放入列表对象中
                if(jan!=null||jan.size()!=0){
                    for(int j=0;j<jan.size();j++){
                        WenJuanAnswerSheet wenJuanAnswerSheet = new WenJuanAnswerSheet();
                        JSONObject jsonObject1 = (JSONObject) jan.get(j);

                        wenJuanAnswerSheet.setSeq(jsonObject1.getInteger("seq"));
                        wenJuanAnswerSheet.setUser(projectList.get(i).getUser());
                        wenJuanAnswerSheet.setShortId(projectList.get(i).getShortId());
                        wenJuanAnswerSheet.setIp(jsonObject1.getString("ip"));
                        wenJuanAnswerSheet.setScore(jsonObject1.getString("score"));
                        wenJuanAnswerSheet.setSource(jsonObject1.getString("source"));
                        wenJuanAnswerSheet.setTimeUsed(jsonObject1.getString("time_used"));
                        wenJuanAnswerSheet.setFinish(jsonObject1.getString("finish"));
                        wenJuanAnswerSheet.setRspdStatus(jsonObject1.getString("rspd_status"));
                        wenJuanAnswerSheet.setStart(jsonObject1.getString("start"));
                        wenJuanAnswerSheet.setWeixinAddr(jsonObject1.getString("weixin_addr"));
                        wenJuanAnswerSheet.setWeixinNickname(jsonObject1.getString("weixin_nickname"));
                        wenJuanAnswerSheet.setWeixinSex(jsonObject1.getString("weixin_sex"));
                        list.add(wenJuanAnswerSheet);

                        //数据库中的问卷详情列表与问卷网接口中获取的问卷详情数据相比较，如果数据库中已存在接口采集的数据，
                        //则删除list中的该对象，最终不把数据库中已存在的数据再次保存到数据库中
                        if(answerSheetList.contains(wenJuanAnswerSheet)){
                            list.remove(wenJuanAnswerSheet);
                        }
                        else
                        {
                            //获取第n题答案
                            for(int k = 1; k < WenJuanApiConstant.Q_VALUE; k++){
                                WenJuanAnswer wjq = new WenJuanAnswer();
                                String q = jsonObject1.getString("Q"+k);
                                if(null != q){
                                    JSONObject jsonObject2 = JSONObject.parseObject(q);
                                    String emoji = "";
                                    try {
                                        emoji = EmojiUtil.emojiConvert(jsonObject2.getString("answer"));
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    wjq.setAnswer(emoji);
                                    wjq.setTypeDesc(jsonObject2.getString("type_desc"));
                                    wjq.setTitle(jsonObject2.getString("title"));
                                    wjq.setSeq(jsonObject1.getInteger("seq"));
                                    wjq.setShortId(projectList.get(i).getShortId());
                                    wjq.setUser(projectList.get(i).getUser());
                                    wjq.setQTop(k);
                                    wenJuanAnswerList.add(wjq);
                                }
                                else
                                {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        ScheduleResult scheduleResult = new ScheduleResult();

        if(list.size()>0)
        {
            //保存问卷网-答卷详情列表
            boolean asResult = wenJuanQuestionnaireMapper.saveAnswerSheet(list);
            scheduleResult.setAnswerSheetResult(asResult);
        }

        if(wenJuanAnswerList.size()>0){
            //保存问卷网-答卷答案表
            boolean aResult = wenJuanQuestionnaireMapper.saveAnswer(wenJuanAnswerList);
            scheduleResult.setAnswerResult(aResult);
        }

        return scheduleResult;
    }

    /**
     * 问卷网查看答题者最新一条答卷详情（无用）
     * @return ScheduleResult
     */
    @Override
    public ScheduleResult saveWenJuanNewAnswerSheetInfo() {
        //登录问卷网API
        String loginResult = wenJuanLogin();
        logger.info(loginResult);

        String answerUrl = newAnswerSheetInfoUrlString("48984","R7Rf2q","90.137.0.1_f67a451c-ef5e-46dd-8e60-5d9387d76d43");
        logger.info("answerUrl："+answerUrl);
        String answerSheetInfoPageResult = restTemplate.getForObject(answerUrl,String.class);
        logger.info("answerSheetInfoPageResult："+answerSheetInfoPageResult);

        return null;
    }

    @Override
    public PageResponseBean<List<WenJuanProject>> getWenJuanProjectListPage(WenJuanProjectRequest wenJuanProjectRequest) {
        //代表数组转list
        List<Long> productIds = new ArrayList<Long>();
        if(wenJuanProjectRequest.getProductId()!= null){
            productIds = Arrays.asList(wenJuanProjectRequest.getProductId());
        }

        //问卷列表
        List<WenJuanProject> wenJuanProjectList = wenJuanQuestionnaireMapper.getWenJuanProjectListPage(wenJuanProjectRequest,productIds);
        List<WenJuanProject> wenJuanProjectListNew = new ArrayList<WenJuanProject>();

        wenJuanProjectList.forEach(n->{
            WenJuanProject w = new WenJuanProject();
            w = n;
            w.setCreateTime(n.getCreateTime().substring(0,n.getCreateTime().indexOf(".")));
            w.setUpdateTime(n.getUpdateTime().substring(0,n.getUpdateTime().indexOf(".")));
            wenJuanProjectListNew.add(w);
        });

        //问卷count
        Integer count = wenJuanQuestionnaireMapper.getWenJuanProjectCount(wenJuanProjectRequest,productIds);

        return new PageResponseBean(wenJuanProjectRequest, count, wenJuanProjectListNew);
    }

    @Override
    public boolean wenJuanProductIdAndNameUpdate(Integer projectId, Integer productId, String productName) {
        return wenJuanQuestionnaireMapper.wenJuanProductIdAndNameUpdate(projectId,productId,productName);
    }

    @Override
    public PageResponseBean<List<WenJuanInfoParams>> getWenJuanInfoListPage(WenJuanInfoRequest wenJuanInfoRequest) {
        //问卷详情列表
        List<WenJuanInfoParams> wenJuanProjectList = wenJuanQuestionnaireMapper.getWenJuanInfoList(wenJuanInfoRequest);
        //问卷详情count
        Integer count = wenJuanQuestionnaireMapper.getWenJuanInfoListCount(wenJuanInfoRequest);

        return new PageResponseBean(wenJuanInfoRequest, count, wenJuanProjectList);
    }

    @Override
    public void wenJuanInfoExportFile(String telPhone, String shortId, Integer seq, HttpServletResponse response) {
        List<WenJuanAnswer> wenJuanAnswerList = wenJuanQuestionnaireMapper.getWenJuanAnswerListByShortIdAndSeq(telPhone,shortId,seq);
        HashMap map = new LinkedHashMap();
        map.put("1", "手机号");
        map.put("2", "题目");
        map.put("3", "答案");
        String fileds[] = new String[] { "telPhone", "title", "answer"};

        //调用导出CSV文件公共方法
        PublicGlobalCSVExprot.exportCSVFile(response,map,wenJuanAnswerList,fileds,"wenJuanInfo.csv");
    }

    /**
     * 问卷网查看答题者最新一条答卷详情URL
     * @param user
     * @param shortId
     * @return string
     */
    public String newAnswerSheetInfoUrlString(String user, String shortId, String source){

        //当前时间戳
        long timestamp = System.currentTimeMillis()/1000;
        //问卷网项目接口
        String projectMd5Signature = WenJuanApiConstant.WJ_APPKEY_VALUE
                +WenJuanApiConstant.WJ_DATATYPE_JSON
                +source
                +shortId
                +timestamp
                +user
                +WenJuanApiConstant.WJ_APPSECRET_VALUE;
        String projectMd5SignatureResult = MD5Util.MD5Encode(projectMd5Signature,"utf8");

        String projectUrl = WenJuanApiConstant.URL+WenJuanApiConstant.GET_RSPD_DETAIL
                +"?"+WenJuanApiConstant.WJ_APPKEY+"="+WenJuanApiConstant.WJ_APPKEY_VALUE
                +"&"+WenJuanApiConstant.WJ_DATATYPE+"="+WenJuanApiConstant.WJ_DATATYPE_JSON
                +"&"+WenJuanApiConstant.WJ_RESPONDENT+"="+source
                +"&"+WenJuanApiConstant.WJ_SHORT_ID+"="+shortId
                +"&"+WenJuanApiConstant.WJ_TIMESTAMP+"="+timestamp
                +"&"+WenJuanApiConstant.WJ_USER+"="+user
                +"&"+WenJuanApiConstant.WJ_SIGNATURE+"="+projectMd5SignatureResult;
        return projectUrl;
    }

    /**
     * 检查数组是否包含某个值
     */
    public static boolean arrayExistValue(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }


}
