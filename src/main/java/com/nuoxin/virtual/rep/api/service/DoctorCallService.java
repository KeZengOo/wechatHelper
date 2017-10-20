package com.nuoxin.virtual.rep.api.service;

import com.alibaba.fastjson.JSON;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.common.util.WavToMp3Util;
import com.nuoxin.virtual.rep.api.dao.DoctorCallInfoDetailsRepository;
import com.nuoxin.virtual.rep.api.dao.DoctorCallInfoRepository;
import com.nuoxin.virtual.rep.api.dao.DoctorRepository;
import com.nuoxin.virtual.rep.api.entity.DoctorCallInfo;
import com.nuoxin.virtual.rep.api.entity.DoctorCallInfoDetails;
import com.nuoxin.virtual.rep.api.entity.DoctorQuestionnaire;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.enums.CallTypeEnum;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.CallbackListRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.CallbackRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.QueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallHistoryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallInfoRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionnaireRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallHistoryResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallStatResponseBean;
import mp3.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.spring.web.json.Json;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by fenggang on 9/13/17.
 */
@Service
@Transactional(readOnly = true)
public class DoctorCallService extends BaseService {

    @Autowired
    private DoctorQuestionnaireService doctorQuestionnaireService;
    @Autowired
    private DoctorCallInfoDetailsRepository doctorCallInfoDetailsRepository;
    @Autowired
    private DoctorCallInfoRepository doctorCallInfoRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DrugUserService drugUserService;
    @Autowired
    private OssService ossService;
    @Value("${recording.file.path}")
    private String path;

    public PageResponseBean<CallResponseBean> doctorPage(QueryRequestBean bean){
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pagetable = super.getPage(bean,sort);
        Specification<DoctorCallInfo> spec = new Specification<DoctorCallInfo>() {
            @Override
            public Predicate toPredicate(Root<DoctorCallInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(StringUtils.isNotEmtity(bean.getName())){
                    predicates.add(cb.like(root.get("doctor").get("name").as(String.class),"%"+bean.getName()+"%"));
                }
                if(StringUtils.isNotEmtity(bean.getMobile())){
                    predicates.add(cb.like(root.get("doctor").get("mobile").as(String.class),"%"+bean.getMobile()+"%"));
                }
                if(StringUtils.isNotEmtity(bean.getDepartment())){
                    predicates.add(cb.like(root.get("doctor").get("department").as(String.class),"%"+bean.getDepartment()+"%"));
                }
                if(StringUtils.isNotEmtity(bean.getDoctorLevel())){
                    predicates.add(cb.like(root.get("doctor").get("doctorLevel").as(String.class),"%"+bean.getDoctorLevel()+"%"));
                }
                if(StringUtils.isNotEmtity(bean.getHospital())){
                    predicates.add(cb.like(root.get("doctor").get("hospitalName").as(String.class),"%"+bean.getHospital()+"%"));
                }
                if(bean.getYear()!=0 && bean.getMonth()!=0 && bean.getDay()!=0){
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(bean.getYear(), bean.getMonth()-1, bean.getDay(), 0, 0, 0);
                    Date start = calendar.getTime();
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class),start));
                    calendar.add(Calendar.DAY_OF_YEAR,+1);
                    Date end =calendar.getTime();
                    predicates.add(cb.lessThanOrEqualTo(root.get("createTime").as(Date.class),end));
                }
                if(bean.getYear()!=0 && bean.getMonth()!=0 && bean.getDay()==0){
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(bean.getYear(), bean.getMonth()-1, bean.getDay(), 0, 0, 0);
                    Date start = calendar.getTime();
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class),start));
                    calendar.add(Calendar.DAY_OF_MONTH,+1);
                    Date end =calendar.getTime();
                    predicates.add(cb.lessThanOrEqualTo(root.get("createTime").as(Date.class),end));

                }
                if(bean.getYear()!=0 && bean.getMonth()==0 && bean.getDay()==0){
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(bean.getYear(), bean.getMonth(), bean.getDay(), 0, 0, 0);
                    Date start =calendar.getTime();
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class),start));
                    calendar.add(Calendar.YEAR,+1);
                    Date end =calendar.getTime();
                    predicates.add(cb.lessThanOrEqualTo(root.get("createTime").as(Date.class),end));

                }
                predicates.add(cb.like(root.get("drugUser").get("leaderPath").as(String.class),bean.getLeaderPath()+"%"));
                predicates.add(cb.equal(root.get("delFlag").as(Integer.class),0));
                query.where(cb.and(cb.and(predicates.toArray(new Predicate[0]))));
                return query.getRestriction();
            }
        };
        Page<DoctorCallInfo> page = doctorCallInfoRepository.findAll(spec,pagetable);
        PageResponseBean<CallResponseBean> responseBean = new PageResponseBean<>(page);
        List<DoctorCallInfo> list = page.getContent();
        if(list!=null && !list.isEmpty()){
            List<CallResponseBean> responseBeans = new ArrayList<>();
            for (DoctorCallInfo info:list) {

                if(info.getDoctor()==null){
                    continue;
                }
                responseBeans.add(this._getCallResponseBean(info));
            }
            responseBean.setContent(responseBeans);
        }
        return responseBean;
    }

    public PageResponseBean<CallHistoryResponseBean> doctorHistoryPage(CallHistoryRequestBean bean){
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        if(bean.getTimeLong()!=null && bean.getTimeLong()!=0){
            if(bean.getDoctorId()!=null && bean.getDoctorId()>0){
                Integer count = doctorCallInfoRepository.findByCreateTimeCount(new Date(bean.getTimeLong()),bean.getDoctorId());
                if(count!=0){
                    bean.setPage((int)Math.ceil(count.doubleValue()/bean.getPageSize()));
                }
            }else{
                Integer count = doctorCallInfoRepository.findByCreateTimeCount(new Date(bean.getTimeLong()));
                if(count!=0){
                    bean.setPage((int)Math.ceil(count.doubleValue()/bean.getPageSize()));
                }
            }
        }
        PageRequest pagetable = super.getPage(bean,sort);
        Specification<DoctorCallInfo> spec = new Specification<DoctorCallInfo>() {
            @Override
            public Predicate toPredicate(Root<DoctorCallInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(bean.getDoctorId()!=null && bean.getDoctorId()>0){
                    predicates.add(cb.equal(root.get("doctor").get("id").as(Long.class),bean.getDoctorId()));
                }
                predicates.add(cb.like(root.get("drugUser").get("leaderPath").as(String.class),bean.getLeaderPath()+"%"));
                predicates.add(cb.equal(root.get("delFlag").as(Integer.class),0));
                query.where(cb.and(cb.and(predicates.toArray(new Predicate[0]))));
                return query.getRestriction();
            }
        };
        Page<DoctorCallInfo> page = doctorCallInfoRepository.findAll(spec,pagetable);
        PageResponseBean<CallHistoryResponseBean> responseBean = new PageResponseBean<>(page);
        List<DoctorCallInfo> list = page.getContent();
        if(list!=null && !list.isEmpty()){
            List<CallHistoryResponseBean> responseBeans = new ArrayList<>();
            for (DoctorCallInfo info:list) {
                responseBeans.add(this._getCallHistoryResponseBean(info,bean.getTimeLong()));
            }
            responseBean.setContent(responseBeans);
        }
        return responseBean;
    }

    public CallStatResponseBean stat(Long drugUserId){
        DrugUser drugUser = drugUserService.findById(drugUserId);
        CallStatResponseBean responseBean = new CallStatResponseBean();
        Map<String,Long> map = doctorCallInfoRepository.statDrugUserIds(drugUser.getLeaderPath()+"%", CallTypeEnum.CALL_TYPE_CALLOUT.getType());
        Long callTimes = null;
        Long num = null;
        if(map!=null){
            responseBean.setCallOutAllNum(map.get("allNum").intValue());
            callTimes = map.get("callTimes");
            num = doctorCallInfoRepository.statDrugUserIdsCount(drugUser.getLeaderPath()+"%", CallTypeEnum.CALL_TYPE_CALLOUT.getType());
            if(callTimes!=null){
                responseBean.setCallOutAllTimes(callTimes);
            }
            if(num!=null){
                responseBean.setCallOutNum(num.intValue());
            }


        }
        map = doctorCallInfoRepository.statDrugUserIds(drugUser.getLeaderPath()+"%", CallTypeEnum.CALL_TYPE_INCALL.getType());
        if (map != null) {
            responseBean.setInCallAllNum(map.get("allNum").intValue());
            callTimes = map.get("callTimes");
            num = doctorCallInfoRepository.statDrugUserIdsCount(drugUser.getLeaderPath()+"%", CallTypeEnum.CALL_TYPE_INCALL.getType());
            if(callTimes!=null){
                responseBean.setInCallAllTimes(callTimes);
            }
            if(num!=null){
                responseBean.setInCallNum(num.intValue());
            }


        }
        return responseBean;
    }

    @Transactional(readOnly = false)
    public CallRequestBean save(CallRequestBean bean){
        DoctorCallInfo info = new DoctorCallInfo();
        info.setDoctor(doctorRepository.findTopByMobile(bean.getMobile()));
        info.setSinToken(bean.getSinToken());
        info.setStatus(bean.getStatus());
        info.setStatusName(bean.getStatusName());
        info.setMobile(bean.getMobile());
        info.setType(bean.getType());
        info.setDrugUserId(bean.getDrugUserId());
        doctorCallInfoRepository.saveAndFlush(info);
        bean.setId(info.getId());
        DoctorCallInfoDetails infoDetails = new DoctorCallInfoDetails();
        infoDetails.setCallId(info.getId());
        infoDetails.setStatus(info.getStatus());
        infoDetails.setStatusName(info.getStatusName());
        infoDetails.setCreateTime(new Date());
        doctorCallInfoDetailsRepository.save(infoDetails);
        return bean;
    }

    @Transactional(readOnly = false)
    public CallRequestBean update(CallRequestBean bean){
        DoctorCallInfo info = doctorCallInfoRepository.findOne(bean.getId());
        if(info==null){
            bean.setId(null);
            return bean;
        }
        info.setSinToken(bean.getSinToken());
        info.setStatus(bean.getStatus());
        info.setStatusName(bean.getStatusName());
        info.setMobile(bean.getMobile());
        info.setType(bean.getType());
        if(info.getCallTime()==null){
            if(bean.getTimes()!=null && bean.getTimes()>0l){
                info.setCallTime(bean.getTimes());
            }
        }
        if(StringUtils.isNotEmtity(bean.getUrl())){
            //TODO 保存录音文件
            try {
                this.downLoadFromUrl("",info.getSinToken()+".wav",path);
                File file = new File(path+info.getSinToken()+".wav");
                //WavToMp3Util.execute(file,path+info.getSinToken()+".mp3");
                //file = new File(path+info.getSinToken()+".mp3");
                String url = ossService.uploadFile(file);
                info.setCallUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            info.setCallUrl(bean.getUrl());
        }
        doctorCallInfoRepository.saveAndFlush(info);
        DoctorCallInfoDetails infoDetails = new DoctorCallInfoDetails();
        infoDetails.setCallId(info.getId());
        infoDetails.setStatus(info.getStatus());
        infoDetails.setStatusName(info.getStatusName());
        infoDetails.setCreateTime(new Date());
        doctorCallInfoDetailsRepository.save(infoDetails);
        return bean;
    }

    @Transactional(readOnly = false)
    public Boolean stopUpdate(CallInfoRequestBean bean){
        DoctorCallInfo info = doctorCallInfoRepository.findOne(bean.getId());
        if(info==null){
            return false;
        }
        info.setCallTime(bean.getTimes());
        if(StringUtils.isNotEmtity(bean.getUrl())){
            //TODO 保存录音文件
            try {
                this.downLoadFromUrl("",info.getSinToken()+".wav",path);
                File file = new File(path+info.getSinToken()+".wav");
                //WavToMp3Util.execute(file,path+info.getSinToken()+".mp3");
                //file = new File(path+info.getSinToken()+".mp3");
                String url = ossService.uploadFile(file);
                info.setCallUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            info.setCallUrl(bean.getUrl());
        }
        info.setFollowUpType(bean.getType());
        info.setRemark(bean.getRemark());
        //保存通话信息
        doctorCallInfoRepository.saveAndFlush(info);

        //保存问卷信息
        List<DoctorQuestionnaire> saveList = new ArrayList<>();
        List<QuestionnaireRequestBean> list = bean.getQuestions();
        if(list!=null && !list.isEmpty()){
            for (QuestionnaireRequestBean arb:list) {
                List<QuestionRequestBean> qs = arb.getQuestions();
                if(qs!=null && !qs.isEmpty()){
                    for (QuestionRequestBean qrb:qs) {
                        DoctorQuestionnaire dq = new DoctorQuestionnaire();
                        dq.setAnswer(qrb.getAnswer());
                        dq.setCreateTime(new Date());
                        dq.setDoctorId(info.getDoctor()==null?0l:info.getDoctor().getId());
                        dq.setDrugUserId(bean.getDrugUserId());
                        dq.setQuestionnaireId(arb.getId());
                        dq.setQuestionId(qrb.getId());
                        dq.setCallId(info.getId());
                        saveList.add(dq);
                    }
                }
            }
        }
        if(saveList!=null && !saveList.isEmpty()){
            doctorQuestionnaireService.save(saveList);
        }
        return true;
    }

    private CallHistoryResponseBean _getCallHistoryResponseBean(DoctorCallInfo info,Long timeLong){
        CallHistoryResponseBean callBean = new CallHistoryResponseBean();
        callBean.setDataUrl(info.getCallUrl());
        //callBean.setDoctorId(info.getDoctor().getId());
        //callBean.setQuestions();
        callBean.setRemark(info.getRemark());
        callBean.setTimeLong(info.getCreateTime().getTime());
        callBean.setTimes(info.getCallTime());
        callBean.setFollowUpType(info.getFollowUpType());
        callBean.setTimeStr(DateUtil.getDateTimeString(info.getCreateTime()));
        if(timeLong!=null && timeLong.equals(callBean.getTimeLong())){
            callBean.setCurrent(true);
        }

        //添加试题
        callBean.setQuestions(doctorQuestionnaireService.findByCallId(info.getId()));
        return callBean;
    }

    private CallResponseBean _getCallResponseBean(DoctorCallInfo info){
        CallResponseBean responseBean = new CallResponseBean();
        if(info!=null){
            //responseBean.setClientLevel(info.getDoctor().getClientLevel());
            responseBean.setDoctorId(info.getDoctor().getId());
            responseBean.setDoctorMobile(info.getDoctor().getMobile());
            responseBean.setDoctorName(info.getDoctor().getName());
            responseBean.setTimeLong(info.getCreateTime().getTime());
            responseBean.setTimeStr(DateUtil.getDateTimeString(info.getCreateTime()));
            responseBean.setDrugUserId(info.getDrugUserId());
            responseBean.setProductId(info.getProductId());
        }
        return responseBean;
    }

    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    private void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }

        System.out.println("info:"+url+" download success");

    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    private byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public void file(){
        try{
            downLoadFromUrl("http://106.75.91.226/16?file=/app/clpms/record/20170925/aaafc53bb1c14851b656bbb116b6674c.wav",
                    "aaaaa.wav","C:\\Users\\27168\\Desktop\\");
            File file = new File("C:\\Users\\27168\\Desktop\\aaaaa.wav");
            File file1 = new File("C:\\Users\\27168\\Desktop\\aaaaa.mp3");
            if(file1==null){
                file1.createNewFile();
            }

            WavToMp3Util.execute(file,"C:\\Users\\27168\\Desktop\\aaaaa.mp3");

            Main main = new mp3.Main();
            main.convertWAVToMP3("/Users/fenggang/Downloads/aaaaa");
            File mp3File = new File( "/Users/fenggang/Downloads/aaaaa.mp3");
            if (mp3File.length() == 0) {
                int retryTimes = 0;
                while (true) {
                    Thread.sleep(2000);
                    mp3File = new File("/Users/fenggang/Downloads/aaaaa.mp3");
                    if (mp3File.length() > 0 || retryTimes == 50) break;
                    retryTimes++;
                    System.out.println("=============tts retry " + retryTimes + " times.");
                }
                if (mp3File.length() == 0) {
                    try {
                        System.out.println("/Users/fenggang/Downloads/aaaaa.mp3 .mp3 file create failed..");
                        throw new Exception("/Users/fenggang/Downloads/aaaaa.mp3  .mp3 file create failed..");
                    } catch (Exception e) {
                        // do nothing
                        System.out.println(e.getMessage());
                    }
                }
            }
            //WavToMp3Util.execute(file,"/Users/fenggang/Downloads/aaaaa.mp3");


            //String url = ossService.uploadFile(file);
            //System.out.println(url);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = false)
    public void callback(CallbackRequestBean bean){
        if(bean!=null){
            if(bean.getCount()>0){
                List<CallbackListRequestBean> list = bean.getRecordList();
                if(list!=null && !list.isEmpty()){
                    for (CallbackListRequestBean bl : list) {
                        DoctorCallInfo info = doctorCallInfoRepository.findBySinToken(bl.getCallid());
                        if(info!=null){
                            info.setJson(JSON.toJSONString(bl));
                            doctorCallInfoRepository.saveAndFlush(info);
                        }
                    }
                }
            }
        }
    }
}
