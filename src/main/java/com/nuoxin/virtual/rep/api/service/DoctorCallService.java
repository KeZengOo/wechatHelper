package com.nuoxin.virtual.rep.api.service;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.nuoxin.virtual.rep.api.entity.v2_5.ProductBean;
import com.nuoxin.virtual.rep.api.enums.HospitalLevelEnum;
import com.nuoxin.virtual.rep.api.mybatis.DoctorCallInfoMapper;
import com.nuoxin.virtual.rep.api.mybatis.DoctorMapper;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserDoctorMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallDoctorResponseBean;
import com.nuoxin.virtual.rep.api.service.v2_5.NewDoctorService;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualDoctorCallInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.constant.FileConstant;
import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.common.util.WavToMp3Util;
import com.nuoxin.virtual.rep.api.dao.DoctorCallInfoDetailsRepository;
import com.nuoxin.virtual.rep.api.dao.DoctorCallInfoRepository;
import com.nuoxin.virtual.rep.api.dao.DoctorRepository;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.entity.DoctorCallInfo;
import com.nuoxin.virtual.rep.api.entity.DoctorCallInfoDetails;
import com.nuoxin.virtual.rep.api.entity.DoctorQuestionnaire;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.ProductLine;
import com.nuoxin.virtual.rep.api.enums.CallTypeEnum;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
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

/**
 * Created by fenggang on 9/13/17.
 */
@Service
@Transactional(readOnly = true)
public class DoctorCallService extends BaseService {

	@Value("${recording.file.path}")
    private String path;
	@Deprecated
    @Value("${audio.download.url}")
    private String url;
	
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
    private ProductLineService productLineService;
    @Autowired
    private OssService ossService;
    @Resource
    private CommonService commonService;
    @Resource
    private FileService fileService;
    @Resource
    private DoctorMapper doctorMapper;
    @Resource
    private DoctorCallInfoMapper doctorCallInfoMapper;
    @Resource
    private DrugUserDoctorMapper drugUserDoctorMapper;


    @Resource
    private NewDoctorService newDoctorService;

    @Resource
    private VirtualDoctorCallInfoService virtualDoctorCallInfoService;


    @Deprecated
    public String uploadUrl() throws Exception{
        fileService.downLoadFromUrl("http://106.75.91.226/16?file=/app/clpms/record/20171211/103_nxclcc_8001_13799438628_20171211185149_1512989509171.wav","test.wav",path);
        File file = new File(path+"test.wav");
        String url = ossService.uploadFile(file);
        return url;
    }

    @Deprecated
    @Transactional(readOnly = false)
    public void callback(CallbackRequestBean bean){
        if(bean!=null){
            if(bean.getCount()>0){
                List<CallbackListRequestBean> list = bean.getRecordList();
                if(list!=null && !list.isEmpty()){
                    for (CallbackListRequestBean bl : list) {
                        DoctorCallInfo info = doctorCallInfoRepository.findBySinToken(bl.getCallid());
                        if(info!=null){
                            if(StringUtils.isNotEmtity(bl.getRecordUrl())){
                                //TODO 保存录音文件
                                try {
                                    fileService.downLoadFromUrl(url+bl.getRecordUrl(),info.getSinToken()+".wav",path);
                                    File file = new File(path+info.getSinToken()+".wav");
                                    //WavToMp3Util.execute(file,path+info.getSinToken()+".mp3");
                                    //file = new File(path+info.getSinToken()+".mp3");
                                    String url = ossService.uploadFile(file);
                                    info.setCallUrl(url);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            info.setJson(JSON.toJSONString(bl));
                            doctorCallInfoRepository.updateUrl(info.getCallUrl(),info.getJson(),info.getId());
                        }
                    }
                }
            }
        }
    }

    /**
     * 根据 sinToken(callId) 获取 DoctorCallInfo 信息
     * @param sinToken(callId)
     * @return 成功返回 DoctorCallInfo 对象,否则返回 null
     */
    public DoctorCallInfo checkoutSinToken(String sinToken){
        DoctorCallInfo info = doctorCallInfoRepository.findBySinToken(sinToken);
        if(info==null){
            return null;
        }
        return info;
    }

    /**
     * 获取已打电话列表
     * @param bean
     * @return
     */
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
                //predicates.add(cb.equal(root.get("delFlag").as(Integer.class),0));
                query.where(cb.and(cb.and(predicates.toArray(new Predicate[0]))));
                return query.getRestriction();
            }
        };
        Page<DoctorCallInfo> page = doctorCallInfoRepository.findAll(spec,pagetable);
        PageResponseBean<CallResponseBean> responseBean = new PageResponseBean<>(page);
        List<DoctorCallInfo> list = page.getContent();
        Map<Long,String> map = new HashMap<>();
        if(list!=null && !list.isEmpty()){
            List<CallResponseBean> responseBeans = new ArrayList<>();
            for (DoctorCallInfo info:list) {

                if(info.getDoctor()==null){
//                    continue;
                }
                CallResponseBean callResponseBean = this._getCallResponseBean(info);
                if(info.getProductId()!=null){
                    String name = map.get(info.getProductId());
                    if(StringUtils.isNotEmtity(name)){
                        callResponseBean.setProductName(name);
                    }else{
                        ProductLine productLine = productLineService.findById(info.getProductId());
                        if(productLine!=null){

                            name = productLine.getName();
                            map.put(info.getProductId(),name);
                            callResponseBean.setProductName(name);
                        }
                    }
                }
                responseBeans.add(callResponseBean);
            }
            responseBean.setContent(responseBeans);
        }
        return responseBean;
    }

    /**
     * 获取电话历史
     * @param bean
     * @return
     */
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

    /**
     * 获取企业用户打电话汇总信息
     * @param drugUserId
     * @return
     */
    public CallStatResponseBean stat(Long drugUserId){
    	CallStatResponseBean responseBean = new CallStatResponseBean();

    	DrugUser drugUser = drugUserService.findById(drugUserId);
    	if(drugUser == null) {
    		return responseBean;
    	}
    	
        String leaderPath = drugUser.getLeaderPath()+"%";
        this.setOutNum(leaderPath, responseBean);
        this.setInNum(leaderPath, responseBean);
		
        return responseBean;
    }

    /**
     * 保存打电话记录
     * @param bean CallRequestBean 对象
     * @return 返回 CallRequestBean 对象
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CallRequestBean save(CallRequestBean bean) throws ParseException {
//        DoctorCallInfo info = new DoctorCallInfo();
//        info.setSinToken(bean.getSinToken());
//        info.setStatus(bean.getStatus());
//        info.setStatusName(bean.getStatusName());
//        info.setMobile(bean.getMobile());
//        info.setType(bean.getType());
//        info.setDrugUserId(bean.getDrugUserId());
//        info.setProductId(bean.getProductId());

        
        //Doctor doctor = doctorRepository.findTopByMobile(bean.getMobile());
//        Doctor doctor = newDoctorService.findFirstByMobile(bean.getMobile());
//        info.setDoctor(doctor);
//
//        DrugUser drugUser = drugUserService.findById(bean.getDrugUserId());
//        info.setDrugUser(drugUser);
        //doctorCallInfoRepository.saveAndFlush(info);
//        if (doctor != null){
//            bean.setDoctorId(doctor.getId());
//        }
        if(bean.getCreateTime() == null){
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = format.format(new Date());
            bean.setCreateTime(createTime);
        }
        virtualDoctorCallInfoService.saveCallInfo(bean);
        
//        Long infoId = info.getId();
//        bean.setId(infoId);
        
        DoctorCallInfoDetails infoDetails = new DoctorCallInfoDetails();
//        infoDetails.setCallId(infoId);
        infoDetails.setCallId(bean.getId());
        infoDetails.setStatus(bean.getStatus());
        infoDetails.setStatusName(bean.getStatusName());
        if(bean.getCreateTime() != null && !bean.getCreateTime().equals("")){
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            date = format.parse(bean.getCreateTime());
            infoDetails.setCreateTime(date);
        }
        else
        {
            infoDetails.setCreateTime(new Date());
        }
        doctorCallInfoDetailsRepository.save(infoDetails);
        
        return bean;
    }

    /**
     * 修改打电话记录
     * @param bean CallRequestBean 对象
     * @return 返回 CallRequestBean 对象
     */
    @Transactional(readOnly = false)
    public CallRequestBean update(CallRequestBean bean){
		DoctorCallInfo info = doctorCallInfoRepository.findOne(bean.getId());
		if (info == null) {
			bean.setId(null);
			return bean;
		}

		info.setSinToken(bean.getSinToken());
		info.setStatus(bean.getStatus());
		info.setStatusName(bean.getStatusName());
		info.setMobile(bean.getMobile());
		info.setType(bean.getType());

		if (info.getCallTime() == null) {
			if (bean.getTimes() != null && bean.getTimes() > 0l) {
				info.setCallTime(bean.getTimes());
			}
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
		if (info == null) {
			return false;
		}
		
		info.setCallTime(bean.getTimes());
		info.setProductId(bean.getProductId());
		info.setFollowUpType(bean.getType());
		info.setRemark(bean.getRemark());
		info.setCallUrl(bean.getUrl());

		// 获取状态详细信息
		List<DoctorCallInfoDetails> detailsList = doctorCallInfoDetailsRepository
				.findByCallIdOrderOrderByCreateTime(info.getId());
		if (CollectionsUtil.isNotEmptyList(detailsList)) {
			DoctorCallInfoDetails details = null;
			for (DoctorCallInfoDetails d : detailsList) {
				if (d.getStatusName() != null && ("answer".equals(d.getStatusName()) || "incall".equals(d.getStatusName()))) {
					details = d;
					break;
				}
			}
			
			if (details == null) {
				details = detailsList.get(0);
			}
			
			info.setStatus(details.getStatus());
			info.setStatusName(details.getStatusName());
		}

		// 保存通话信息
		doctorCallInfoRepository.saveAndFlush(info);

		// 保存问卷信息
		List<DoctorQuestionnaire> saveList = new ArrayList<>();
		List<QuestionnaireRequestBean> list = bean.getQuestions();
		
		if (list != null && !list.isEmpty()) {
			for (QuestionnaireRequestBean arb : list) {
				List<QuestionRequestBean> qs = arb.getQuestions();
				if (qs != null && !qs.isEmpty()) {
					for (QuestionRequestBean qrb : qs) {
						DoctorQuestionnaire dq = new DoctorQuestionnaire();
						dq.setAnswer(qrb.getAnswer());
						dq.setCreateTime(new Date());
						dq.setDoctorId(info.getDoctor() == null ? 0l : info.getDoctor().getId());
						dq.setDrugUserId(bean.getDrugUserId());
						dq.setQuestionnaireId(arb.getId());
						dq.setQuestionId(qrb.getId());
						dq.setCallId(info.getId());
						saveList.add(dq);
					}
				}
			}
		}
		
		if (CollectionsUtil.isNotEmptyList(saveList)) {
			doctorQuestionnaireService.save(saveList);
		}
		
        return true;
     }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * 统计呼出
     * @param leaderPath
     * @param responseBean
     */
	private void setOutNum(String leaderPath, CallStatResponseBean responseBean) {
		Long outNum = doctorCallInfoRepository.statDrugUserIdsCount(leaderPath, CallTypeEnum.CALL_TYPE_CALLOUT.getType());
		if (outNum == null) {
			outNum = 0L;
		}
		responseBean.setCallOutNum(outNum.intValue());

		if (outNum > 0) {
			Map<String, Long> outMap = doctorCallInfoRepository.statDrugUserIds(leaderPath,
					CallTypeEnum.CALL_TYPE_CALLOUT.getType());
			if (outMap != null) {
				Long callTimes = outMap.get("callTimes");
				if (callTimes != null) {
					responseBean.setCallOutAllTimes(callTimes);
				}

				Long outNumTemp = outMap.get("allNum");
				if (outNumTemp != null) {
					responseBean.setCallOutAllNum(outNumTemp.intValue());
				}
			}
		}
	}
	
	/**
	 * 统计呼入
	 * @param leaderPath
	 * @param responseBean
	 */
	private void setInNum(String leaderPath, CallStatResponseBean responseBean) {
		Long inNum = doctorCallInfoRepository.statDrugUserIdsCallCount(leaderPath, CallTypeEnum.CALL_TYPE_INCALL.getType(),
				"incall");
		if (inNum == null) {
			inNum = 0L;
		}
		responseBean.setInCallNum(inNum.intValue());

		if (inNum > 0) {
			Map<String, Long> inMap = doctorCallInfoRepository.statDrugUserIds(leaderPath,
					CallTypeEnum.CALL_TYPE_INCALL.getType());
			if (inMap != null) {
				Long callTimes = inMap.get("callTimes");
				if (callTimes != null) {
					responseBean.setInCallAllTimes(callTimes);
				}

				Long inNumTemp = inMap.get("allNum");
				if (inNumTemp != null) {
					responseBean.setInCallAllNum(inNumTemp.intValue());
				}
			}
		}
	}

    @Deprecated
    private void downLoadFile(DoctorCallInfo info) {
    	try {
			String fileName = info.getSinToken() + FileConstant.MP3_SUFFIX;
			fileService.downLoadFromUrl("", fileName, path);
			File file = new File(path + fileName);
			String url = ossService.uploadFile(file);
			info.setCallUrl(url);
		} catch (Exception e) {
			logger.error("上传文件异常", e);
		}
    }

    private CallHistoryResponseBean _getCallHistoryResponseBean(DoctorCallInfo info,Long timeLong){
        CallHistoryResponseBean callBean = new CallHistoryResponseBean();
        callBean.setDataUrl(info.getCallUrl());
        //callBean.setDoctorId(info.getDoctor().getId());
        //callBean.setQuestions();
        callBean.setStatus(info.getStatus());
        callBean.setStatusName(info.getStatusName());
        callBean.setRemark(info.getRemark());
        callBean.setTimeLong(info.getCreateTime().getTime());
        callBean.setTimes(info.getCallTime());
        callBean.setType(info.getType());
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
            if(info.getDoctor()!=null && info.getDoctor().getDoctorVirtual()!=null) {
                responseBean.setClientLevel(info.getDoctor().getDoctorVirtual().getClientLevel());
                responseBean.setDoctorId(info.getDoctor().getId());
                responseBean.setDoctorMobile(info.getDoctor().getMobile());
                responseBean.setDoctorName(info.getDoctor().getName());
                responseBean.setTimeLong(info.getCreateTime().getTime());
            }else{
                responseBean.setDoctorMobile(info.getMobile());
            }
            responseBean.setType(info.getType());
            if(info.getDrugUser()!=null){
                responseBean.setDrugUserName(info.getDrugUser().getName());
                responseBean.setDrugUserId(info.getDrugUser().getId());
            }
            responseBean.setTimeStr(DateUtil.getDateTimeString(info.getCreateTime()));
            responseBean.setDrugUserId(info.getDrugUserId());
            responseBean.setProductId(info.getProductId());
            responseBean.setStatus(info.getStatus());
            responseBean.setStatusName(info.getStatusName());
        }
        return responseBean;
    }

    /**
     * 医生产品列表及基本信息
     * @param drugUserId
     * @param doctorId
     * @return
     */
    public CallDoctorResponseBean doctorProductInfo(Long drugUserId, Long doctorId) {
        //获取医生基本信息
        CallDoctorResponseBean bean =doctorMapper.doctorProductInfo(doctorId);
        if(bean!=null){
            //医院级别
            if(bean.getHospitalLevel()!=null){
                bean.setHospitalLevelStr(HospitalLevelEnum.getName(bean.getHospitalLevel()));
            }
            //产品列表
            bean.setProducts(drugUserDoctorMapper.getProducts(drugUserId,bean.getDoctorId()));
            //上次拜访时间
            bean.setLastVisitTime(doctorCallInfoMapper.lastVisitTime(drugUserId,bean.getDoctorId()));
            //上次拜访时间转化为几天前
            if (bean.getLastVisitTime() != null) {
                long visitTimeDelta = System.currentTimeMillis() - bean.getLastVisitTime().getTime();
                String lastVisitTime = commonService.alterLastVisitTimeContent(visitTimeDelta);
                bean.setVisitTimeStr(lastVisitTime);
            } else {
                bean.setVisitTimeStr("无");
            }
        }
        return bean;
    }
}
