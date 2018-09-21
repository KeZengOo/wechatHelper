package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.constant.StatisticalConstant;
import com.nuoxin.virtual.rep.api.common.constant.VisitResultConstant;
import com.nuoxin.virtual.rep.api.entity.DoctorDynamicFieldValue;
import com.nuoxin.virtual.rep.api.entity.v2_5.*;
import com.nuoxin.virtual.rep.api.mybatis.*;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v2_5.StatisticalService;
import com.nuoxin.virtual.rep.api.utils.ArithUtil;
import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserResponseBean;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 统计分析服务接口
 */
@Service
public class StatisticalServiceImpl implements StatisticalService {

	@Autowired
	private DrugUserDoctorMapper drugUserDoctorMapper;
	@Autowired
	private VirtualDoctorCallInfoMapper virtualDoctorCallInfoMapper;
	@Autowired
	private VirtualDoctorCallInfoMendMapper virtualDoctorCallInfoMendMapper;
	@Autowired
	private MessageMapper messageMapper;
	@Autowired
	private ActivityShareMapper activityShareMapper;
	@Autowired
	private DrugUserDoctorQuateMapper drugUserDoctorQuateMapper;
	@Autowired
	private DoctorCallInfoMapper doctorCallInfoMapper;
	@Resource
	private DynamicFieldMapper dynamicFieldMapper;

	/**
	 * 医生拜访明细表·分页
	 * @param statisticsParams
	 * @return
	 */
	@Override
	public PageResponseBean<List<LinkedHashMap<String,Object>>> doctorVisitDetailPage(StatisticsParams statisticsParams) {
		int total=doctorCallInfoMapper.getDoctorVisitDetailListCount(statisticsParams);
		List<LinkedHashMap<String,Object>> list =new ArrayList<>();
		if(total>0){
			list=getDoctorVisitDetailList(statisticsParams);
		}
		return new PageResponseBean(statisticsParams, total, list);
	}

	/**
	 * 医生拜访明细表·列表
	 * @param statisticsParams
	 * @return
	 */
	@Override
	public List<LinkedHashMap<String,Object>> doctorVisitDetailList(StatisticsParams statisticsParams) {
		return getDoctorVisitDetailList(statisticsParams);
	}

	/**
	 * 医生拜访明细表·封装list
	 * @param statisticsParams
	 * @return
	 */
	private List<LinkedHashMap<String,Object>> getDoctorVisitDetailList(StatisticsParams statisticsParams) {
		List<LinkedHashMap<String,Object>> list=doctorCallInfoMapper.getDoctorVisitDetailList(statisticsParams);
		Set<Integer> ids=new HashSet<>();
		list.forEach(x->{
			ids.add(Integer.parseInt(x.get("doctorId").toString()));
			x.put("hcpPotential",getLevel((String)x.get("hcpPotential")));
			x.put("isHasDrug",getValue((String)x.get("isHasDrug")));
			x.put("isTarget",getValue((String)x.get("isTarget")));
			x.put("isHasAe",getValue((String)x.get("isHasAe")));
		});
		List<DynamicFieldValueResponse> fieldValueList=dynamicFieldMapper.getProductDynamicFieldValue(statisticsParams.getProductId(),ids);
		Map<Integer, List<DynamicFieldValueResponse>> map = fieldValueList.stream().collect(Collectors.groupingBy(DynamicFieldValueResponse::getDoctorId));
		list.forEach(x->{
			Integer doctorId=Integer.parseInt(x.get("doctorId").toString());
			List<DynamicFieldValueResponse> valueList=map.get(doctorId);
			valueList.forEach(xx->{
				x.put(xx.getProp(),xx.getValue()==null?"":xx.getValue());
			});
		});
		return list;
	}

	/**
	 * 医生拜访统计表·列表
	 * @param statisticsParams
	 * @return
	 */
	@Override
	public List<StatisticsResponse> visitStatisticsList(StatisticsParams statisticsParams) {
		return getStatisticsList(statisticsParams);
	}

	/**
	 * 医生拜访统计表·分页
	 * @param statisticsParams
	 * @return
	 */
	@Override
	public PageResponseBean<List<StatisticsResponse>> visitStatisticsPage(StatisticsParams statisticsParams) {
		int total=drugUserDoctorMapper.selectDrugUserDoctorsCount(statisticsParams);
		List<StatisticsResponse> list =new ArrayList<>();
		if(total>0){
			list=getStatisticsList(statisticsParams);
		}
		return new PageResponseBean(statisticsParams, total, list);
	}

	/**
	 * 医生拜访统计表·封装list
	 * @param statisticsParams
	 * @return
	 */
	private List<StatisticsResponse> getStatisticsList(StatisticsParams statisticsParams) {

		List<StatisticsResponse> list=drugUserDoctorMapper.selectDrugUserDoctors(statisticsParams);
		//内容服务人数
		List<StatisticsDrugNumResponse> contentServiceTotal=activityShareMapper.getContentServiceCount(statisticsParams);
		//设置拜访医生数
		setVisitDoctorNum(statisticsParams, list);
		//设置接触医生数
		setContactDoctorNum(statisticsParams, list, contentServiceTotal);
		//设置成功医生数
		setSuccessDoctorNum(statisticsParams, list, contentServiceTotal);
		//设置招募医生数
		setRecruitmentDoctorNum(statisticsParams, list);
		//设置覆盖医生数
		setCoverDoctorNum(statisticsParams, list, contentServiceTotal);
		//设置高潜力医生
		setPotentialDoctor(statisticsParams, list, StatisticalConstant.HIGH);
		//设置中潜力医生
		setPotentialDoctor(statisticsParams, list,StatisticalConstant.MIDDLE);
		//设置低潜力医生
		setPotentialDoctor(statisticsParams, list,StatisticalConstant.LOW);
		//设置微信回复人数
		setWeiXin(statisticsParams, list,StatisticalConstant.REPLY);
		//设置微信发送人数
		setWeiXin(statisticsParams, list,StatisticalConstant.SEND);
		//设置内容发送人数
		setContentSendNum(statisticsParams, list);
		//设置内容阅读人数
		setContentReadNum(statisticsParams, list);
		//设置内容阅读率
		setContentReadRate(list);
		//设置内容阅读时长
		setContentReadTime(statisticsParams, list);
		//增加求和列
		addSum(list);
		return list;
	}

	/**
	 *
	 * @param productId
	 * @param productName
	 * @return
	 */
	@Override
	public List<DynamicFieldResponse> getDynamicFieldByProductId(Integer productId, String productName) {
		Map<String, String> map=new LinkedHashMap<>();
		map.put("drugUserName","代表");
		map.put("visitTime","拜访时间");
		map.put("doctorId","医生ID");
		map.put("doctorName","医生姓名");
		map.put("hospitalName","医院");
		map.put("visitType","拜访方式");
		map.put("shareContent","分享内容");
		map.put("visitResult","拜访结果");
		map.put("attitude","医生态度");
		map.put("nextVisitTime","下次拜访时间");
		map.put("clientLevel","客户等级");
		map.put("hcpPotential","医生潜力");
		map.put("isHasDrug","是否有药");
		map.put("isTarget","是否是目标客户");
		map.put("isHasAe","是否有AE");
		List<DynamicFieldResponse> list=new ArrayList<>();
		map.forEach((k,v)->{
			DynamicFieldResponse t= new DynamicFieldResponse();
			t.setLable(v);
			t.setProp(k);
			list.add(t);
		});
		DynamicFieldResponse t= new DynamicFieldResponse();
		t.setLable(productName);
		t.setProp("product");
		t.setChildren(dynamicFieldMapper.getProductDynamicField(productId));
		list.add(t);
		return list;
	}

	private String getValue(String value){
		if(value.equals("0")){
			return "否";
		}else if(value.equals("1")){
			return "是";
		}else{
			return "未知";
		}
	}

	/**
	 * 求和对象
	 * @param list
	 */
	private void addSum(List<StatisticsResponse> list) {
		StatisticsResponse sum=new StatisticsResponse();
		sum.setDrugUserName("SUM");
		sum.setVisitDoctorNum(list.stream().mapToInt(StatisticsResponse::getVisitDoctorNum).sum());
		sum.setContactDoctorNum(list.stream().mapToInt(StatisticsResponse::getContactDoctorNum).sum());
		sum.setSuccessDoctorNum(list.stream().mapToInt(StatisticsResponse::getSuccessDoctorNum).sum());
		sum.setRecruitDoctorNum(list.stream().mapToInt(StatisticsResponse::getRecruitDoctorNum).sum());
		sum.setCoverDoctorNum(list.stream().mapToInt(StatisticsResponse::getCoverDoctorNum).sum());
		sum.setPotentialDoctorHighNum(list.stream().mapToInt(StatisticsResponse::getPotentialDoctorHighNum).sum());
		sum.setPotentialDoctorMiddleNum(list.stream().mapToInt(StatisticsResponse::getPotentialDoctorMiddleNum).sum());
		sum.setPotentialDoctorLowNum(list.stream().mapToInt(StatisticsResponse::getPotentialDoctorLowNum).sum());
		sum.setWxSendNum(list.stream().mapToInt(StatisticsResponse::getWxSendNum).sum());
		sum.setWxReplyNum(list.stream().mapToInt(StatisticsResponse::getWxReplyNum).sum());
		sum.setContentSendNum(list.stream().mapToInt(StatisticsResponse::getContentSendNum).sum());
		sum.setContentReadNum(list.stream().mapToInt(StatisticsResponse::getContentReadNum).sum());
		sum.setContentReadRate(getRate(sum));
		sum.setContentReadTime(list.stream().mapToInt(StatisticsResponse::getContentReadTime).sum());
		list.add(sum);
	}

	/**
	 * 设置内容阅读率
	 * @param list
	 */
	private void setContentReadRate( List<StatisticsResponse> list) {
		list.forEach(x->{
			x.setContentReadRate(getRate(x));
		});
	}

	private String getRate(StatisticsResponse x) {
		if(x.getContentSendNum()==0){
			return "0.00%";
		}
		return ArithUtil.divPercentage((double) x.getContentReadNum(),(double) x.getContentSendNum())+"%";
	}

	/**
	 * 设置内容阅读时长
	 * @param statisticsParams
	 * @param list
	 */
	private void setContentReadTime(StatisticsParams statisticsParams, List<StatisticsResponse> list) {
		List<StatisticsDrugNumResponse> contentReadTimeTotal=activityShareMapper.getContentReadTimeCount(statisticsParams);
		//内容阅读时长赋值
		this.setFieldValue(list, StatisticalConstant.CONTENTREADTIME,contentReadTimeTotal);
	}
	/**
	 * 设置内容阅读人数
	 * @param statisticsParams
	 * @param list
	 */
	private void setContentReadNum(StatisticsParams statisticsParams, List<StatisticsResponse> list) {
		List<StatisticsDrugNumResponse> contentReadTotal=activityShareMapper.getContentReadCount(statisticsParams);
		//内容阅读人数赋值
		this.setFieldValue(list, StatisticalConstant.CONTENTREADNUM,contentReadTotal);
	}

	/**
	 * 设置内容发送人数
	 * @param statisticsParams
	 * @param list
	 */
	private void setContentSendNum(StatisticsParams statisticsParams, List<StatisticsResponse> list) {
		List<StatisticsDrugNumResponse> contentSendTotal=activityShareMapper.getContentSendCount(statisticsParams);
		//内容发送人数赋值
		this.setFieldValue(list, StatisticalConstant.CONTENTSENDNUM,contentSendTotal);
	}

	/**
	 * 设置医生潜力
	 * @param statisticsParams
	 * @param list
	 */
	private void setPotentialDoctor(StatisticsParams statisticsParams, List<StatisticsResponse> list,Integer type) {
		//
		List<StatisticsDrugNumResponse> potentialDoctorTotal=drugUserDoctorQuateMapper.getPotentialDoctorCount(statisticsParams,type);
		int typeParm;
		if(type==StatisticalConstant.HIGH){
			typeParm=StatisticalConstant.POTENTIALDOCTORHIGHNUM;
		}else if(type==StatisticalConstant.MIDDLE){
			typeParm=StatisticalConstant.POTENTIALDOCTORMIDDLENUM;
		}else{
			typeParm=StatisticalConstant.POTENTIALDOCTORLOWNUM;
		}
		//接触医生数赋值
		this.setFieldValue(list, typeParm,potentialDoctorTotal);
	}

	/**
	 * 设置接触医生数
	 * @param statisticsParams
	 * @param list
	 * @param contentServiceTotal
	 */
	private void setContactDoctorNum(StatisticsParams statisticsParams, List<StatisticsResponse> list, List<StatisticsDrugNumResponse> contentServiceTotal) {
		//visit_result中包含【招募成功、传递成功、分型成功、服务电话、医生拒绝、稍后致电】的医生人数
		List<StatisticsDrugNumResponse> contactDoctorTotal=virtualDoctorCallInfoMendMapper.getDoctorNum(statisticsParams, VisitResultConstant.getContactDoctorParm());
		//接触医生数赋值
		this.setFieldValue(list, StatisticalConstant.CONTACTDOCTORNUM,contactDoctorTotal,contentServiceTotal);
	}

	/**
	 * 设置微信消息
	 * @param statisticsParams
	 * @param list
	 * @param wechatMessageStatus
	 */
	private void setWeiXin(StatisticsParams statisticsParams, List<StatisticsResponse> list,String wechatMessageStatus) {
		//微信消息数
		List<StatisticsDrugNumResponse> WeiXinTotal=messageMapper.getWeiXinMessageCount(statisticsParams,wechatMessageStatus);
		int typeParm;
		if(wechatMessageStatus.equals(StatisticalConstant.SEND)){
			typeParm=StatisticalConstant.WXSENDNUM;
		}else{
			typeParm=StatisticalConstant.WXREPLYNUM;
		}
		this.setFieldValue(list, typeParm,WeiXinTotal);
	}

	/**
	 * 设置拜访医生数
	 * @param statisticsParams
	 * @param list
	 */
	private void setVisitDoctorNum(StatisticsParams statisticsParams, List<StatisticsResponse> list) {
		//医生电话拜访记录
		List<StatisticsDrugNumResponse> telephoneTotal=virtualDoctorCallInfoMapper.geTelephoneDoctorVisitCount(statisticsParams);
		//医生微信拜访记录
		List<StatisticsDrugNumResponse> weiXinTotal=messageMapper.getWeiXinDoctorVisitCount(statisticsParams);
		//医生短信拜访记录
		List<StatisticsDrugNumResponse> messageTotal=activityShareMapper.getMessageDoctorVisitCount(statisticsParams);
		//医生拜访数赋值
		this.setFieldValue(list, StatisticalConstant.VISITDOCTORNUM,telephoneTotal, weiXinTotal,messageTotal);
	}

	/**
	 * 设置成功医生数
	 * @param statisticsParams
	 * @param list
	 * @param contentServiceTotal
	 */
	private void setSuccessDoctorNum(StatisticsParams statisticsParams, List<StatisticsResponse> list, List<StatisticsDrugNumResponse> contentServiceTotal) {
		//visit_result中包含【招募成功+传递成功+医生分型+服务电话】的医生人数
		List<StatisticsDrugNumResponse> successDoctorTotal=virtualDoctorCallInfoMendMapper.getDoctorNum(statisticsParams, VisitResultConstant.getSuccessDoctorParm());
		//接触成功医生数
		this.setFieldValue(list, StatisticalConstant.SUCCESSDOCTORNUM,successDoctorTotal,contentServiceTotal);
	}

	/**
	 * 设置招募医生数
	 * @param statisticsParams
	 * @param list
	 */
	private void setRecruitmentDoctorNum(StatisticsParams statisticsParams, List<StatisticsResponse> list) {
		//visit_result中包含【招募成功+传递成功+医生分型+服务电话】的医生人数
		List<StatisticsDrugNumResponse> recruitmentDoctorTotal=virtualDoctorCallInfoMendMapper.getDoctorNum(statisticsParams, VisitResultConstant.getRecruitmentParm());
		//招募医生数
		this.setFieldValue(list, StatisticalConstant.RECRUITDOCTORNUM,recruitmentDoctorTotal);
	}

	/**
	 * 设置覆盖医生人数
	 * @param statisticsParams
	 * @param list
	 * @param contentServiceTotal
	 */
	private void setCoverDoctorNum(StatisticsParams statisticsParams, List<StatisticsResponse> list, List<StatisticsDrugNumResponse> contentServiceTotal) {
		//visit_result中包含【医生分型+服务电话】的医生人数
		List<StatisticsDrugNumResponse> coverDoctorTotal=virtualDoctorCallInfoMendMapper.getDoctorNum(statisticsParams, VisitResultConstant.getCoverDoctorParm());
		//接触成功医生数
		this.setFieldValue(list, StatisticalConstant.COVERDOCTORNUM,coverDoctorTotal,contentServiceTotal);
	}

	/**
	 * 医生拜访数赋值
	 * @param list
	 * @param total
	 */
	private void setFieldValue(List<StatisticsResponse> list,Integer type, List<StatisticsDrugNumResponse>... total) {
		for(StatisticsResponse l:list){
			Integer sumTotal=0;
			for(List<StatisticsDrugNumResponse> t:total){
				sumTotal += getTotal(t, l.getDrugUserId());
			}
			switch(type){
				case StatisticalConstant.VISITDOCTORNUM :
					l.setVisitDoctorNum(sumTotal);break;
				case StatisticalConstant.CONTACTDOCTORNUM :
					l.setContactDoctorNum(sumTotal);break;
				case StatisticalConstant.RECRUITDOCTORNUM :
					l.setRecruitDoctorNum(sumTotal);break;
				case StatisticalConstant.SUCCESSDOCTORNUM :
					l.setSuccessDoctorNum(sumTotal);break;
				case StatisticalConstant.COVERDOCTORNUM :
					l.setCoverDoctorNum(sumTotal);break;
				case StatisticalConstant.POTENTIALDOCTORHIGHNUM :
					l.setPotentialDoctorHighNum(sumTotal);break;
				case StatisticalConstant.POTENTIALDOCTORMIDDLENUM :
					l.setPotentialDoctorMiddleNum(sumTotal);break;
				case StatisticalConstant.POTENTIALDOCTORLOWNUM :
					l.setPotentialDoctorLowNum(sumTotal);break;
				case StatisticalConstant.WXSENDNUM :
					l.setWxSendNum(sumTotal);break;
				case StatisticalConstant.WXREPLYNUM :
					l.setWxReplyNum(sumTotal);break;
				case StatisticalConstant.CONTENTSENDNUM :
					l.setContentSendNum(sumTotal);break;
				case StatisticalConstant.CONTENTREADNUM :
					l.setContentReadNum(sumTotal);break;
				case StatisticalConstant.CONTENTREADTIME :
					l.setContentReadTime(sumTotal);break;
			}
		}
	}

	/**
	 * 赛选值
	 * @param total
	 * @param drugUserId
	 * @return
	 */
	private Integer getTotal(List<StatisticsDrugNumResponse> total, Integer drugUserId) {
		for(StatisticsDrugNumResponse t:total){
			if(drugUserId.equals(t.getDrugUserId())){
				return t.getTotal();
			}
		}
		return 0;
	}

	private String getLevel(String value){
		if(value.equals("3")){
			return "高";
		}else if(value.equals("2")){
			return "中";
		}else if(value.equals("1")){
			return "低";
		}else{
			return "未知";
		}
	}

}
