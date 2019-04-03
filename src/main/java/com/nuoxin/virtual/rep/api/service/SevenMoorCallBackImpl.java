package com.nuoxin.virtual.rep.api.service;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.nuoxin.virtual.rep.api.web.controller.request.call.IdentifyCallUrlRequestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.mybatis.DoctorCallInfoMapper;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.utils.ConvertStatusUtil;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.utils.RestUtils;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.call.Call7mmorRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo.RetryCallInfoRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.Call7mmorResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallInfoResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.Default7MoorResponseBean;

import lombok.Data;

/**
 * 回调Service类
 * @author xiekaiyu
 */
@Service("sevenMoor")
public class SevenMoorCallBackImpl extends BaseCallBackImpl implements CallBackService{

	private static final Logger logger = LoggerFactory.getLogger(SevenMoorCallBackImpl.class);

	@Resource
	private RestTemplate restTemplate;
	@Resource
	private DoctorCallInfoMapper doctorCallInfoMapper;
	
	/**
	 * 参考链接 https://developer.7moor.com/event/
	 * @param paramsMap
	 */
	@Override
	public void callBack(ConcurrentMap<String, String> paramsMap) {
		logger.warn("执行 7moor 回调方法... params:{}", JSONObject.toJSONString(paramsMap));
		this.threadSleep();
		super.processCallBack(this.buildConvertResult(paramsMap));
		logger.warn("执行 7moor 回调方完成法完成! params:{}", JSONObject.toJSONString(paramsMap));
	}

	// TODO 代码临时写，有的参数写死，待优化
	@Override
	public List<Call7mmorResponseBean> getCallList(Call7mmorRequestBean bean) {
        List<Call7mmorResponseBean> call7mmorResponseBeans = null;
		// 账户编号： 	N00000028346
		//帐号APISecret： 	e7ac57d0-80ce-11e8-ab50-3f649a8c117a
		String url = "http://apis.7moor.com/v20170704/cdr/getCCCdr/ACCOUNTID?sig=sigKey";
		url = url.replace("ACCOUNTID", "N00000028346");
		String timestamp = DateUtil.getDateSecondString(new Date());

		String sigKey = "N00000028346e7ac57d0-80ce-11e8-ab50-3f649a8c117a" + timestamp;
		String md5SigKey = StringUtil.md5Password(sigKey);
		url = url.replace("sigKey", md5SigKey.toUpperCase());
		String auth = "N00000028346:"+ timestamp;
		String base64Auth = StringUtil.base64(auth);
		Map<String, String> header = new HashMap<>();
		header.put("Authorization", base64Auth);
		header.put("charset", "utf-8");
        String jsonStr = RestUtils.post(url, JSONObject.toJSONString(bean), header);
        Default7MoorResponseBean<?> default7MoorResponseBean = JSONObject.parseObject(jsonStr, Default7MoorResponseBean.class);
        boolean success = default7MoorResponseBean.isSuccess();
        if (success){
            call7mmorResponseBeans = JSONObject.parseArray(default7MoorResponseBean.getData().toString(), Call7mmorResponseBean.class);
        }else{
            logger.error("获取通话记录失败：{}", JSONObject.toJSONString(default7MoorResponseBean));
            throw new BusinessException(ErrorEnum.ERROR, "获取通话记录失败!");
        }

        return call7mmorResponseBeans;
	}

    @Override
    public void repeatUploadFile(Call7mmorRequestBean bean) {
        List<Call7mmorResponseBean> list = getCallList(bean);
        if (CollectionsUtil.isEmptyList(list)){
            return;
        }

        for (Call7mmorResponseBean call7mmorResponseBean:list){
            String id = call7mmorResponseBean.getCALL_SHEET_ID();
            String file_server = call7mmorResponseBean.getFILE_SERVER();
            String status = call7mmorResponseBean.getSTATUS();
            if ("dealing".equals(status)){
                String record_file_name = call7mmorResponseBean.getRECORD_FILE_NAME();
                String url = file_server + "/" + record_file_name;
                String callUrl = doctorCallInfoMapper.getCallUrlBySigToken(id);
                if (StringUtils.isEmpty(callUrl)){
                    try {
                        String ossUrl = super.processFile(url, id);
                        if (!StringUtils.isEmpty(ossUrl)){
                            String beginTime = call7mmorResponseBean.getBEGIN_TIME();
                            String endTime = call7mmorResponseBean.getEND_TIME();
                            int callTime = DateUtil.calLastedTime(beginTime, endTime);
                            doctorCallInfoMapper.updateCallUrlBySigToken(ossUrl, id, callTime);
                            logger.info("重新上传录音文件更新完毕！callUrl={}, sinToken={}", callUrl, id);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

	@Override
	public void repeatSaveOrUpdateCall(Call7mmorRequestBean bean) {

		String beginTime = bean.getBeginTime();
		// 如果没有开始时间，取当前时间前天的时间
		if (StringUtils.isEmpty(beginTime)){
			Date date=new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, -3);
			date = calendar.getTime();
			bean.setBeginTime(DateUtil.getDateTimeString(date));
		}

		String endTime = bean.getEndTime();
		// 如果没有结束时间，取当前时间
		if (StringUtils.isEmpty(endTime)){
			bean.setEndTime(DateUtil.getDateTimeString(new Date()));
		}

		List<Call7mmorResponseBean> callList = getCallList(bean);
		if (CollectionsUtil.isEmptyList(callList)){
			logger.warn("params= {} 没有要重新新增或者更新的录音文件", JSONObject.toJSONString(bean));
			return;
		}


		// 获得接通的电话记录(status=dealing)
		List<Call7mmorResponseBean> dealingList = callList.stream().filter(call -> call.getSTATUS().equals("dealing")).collect(Collectors.toList());
		if (CollectionsUtil.isEmptyList(dealingList)){
			logger.warn("params= {} 没有要重新新增或者更新 status=dealing 录音文件", JSONObject.toJSONString(bean));
			return;
		}

		this.saveOrUpdateDealingCallList(dealingList);

	}

	@Override
	public void identifyCallUrl(IdentifyCallUrlRequestBean bean) {

		List<CallInfoResponseBean> identifyCallUrlList = doctorCallInfoMapper.getIdentifyCallUrl(bean);
		if (CollectionsUtil.isEmptyList(identifyCallUrlList)){
			return;
		}

		for (CallInfoResponseBean callInfo : identifyCallUrlList) {
			super.updateCallUrlText(callInfo.getSinToken(), callInfo.getCallUrl());
			//异步（分割录音文件并上传阿里云，返回左右声道的阿里云地址 并且 根据左右声道的阿里云地址进行语音识别，进行入库）
			if(!callInfo.getCallUrl().isEmpty()){
				new Thread(new Runnable(){
					@Override
					public void run() {
						logger.info("callUrl={}, callInfo中获取的地址：", callInfo.getCallUrl());
						//分割录音文件并上传阿里云，返回左右声道的阿里云地址
						Map<String,String> pathMap = splitSpeechAliyunUrlUpdate(callInfo.getCallUrl());
						logger.info("pathMap={}, 分割录音文件并上传阿里云，返回左右声道的阿里云地址", pathMap);
						if(pathMap.size() > 0){
							//根据左右声道的阿里云地址进行语音识别，进行入库
							boolean result_is_save = saveSpeechRecognitionResultCallInfo(pathMap, callInfo.getSinToken());
							logger.info("result_is_save={}, 根据左右声道的阿里云地址进行语音识别，进行入库是否成功！", result_is_save);
						}
					}
				}).start();
//			//分割录音文件并上传阿里云，返回左右声道的阿里云地址
//			Map<String,String> pathMap = splitSpeechAliyunUrlUpdate(callInfo.getCallUrl());
//			//根据左右声道的阿里云地址进行语音识别，进行入库
//			boolean result_is_save = saveSpeechRecognitionResultCallInfo(pathMap, callInfo.getSinToken());
			}
		}
	}

	@Override
	public void handleNotAliyunCallUrl() {

		List<CallInfoResponseBean> notAliyunCallUrlList = doctorCallInfoMapper.getNotAliyunCallUrl();
		if (CollectionsUtil.isEmptyList(notAliyunCallUrlList)){
			return;
		}


		notAliyunCallUrlList.forEach(callUrl->{
			try {
				String url = callUrl.getCallUrl();
				String sinToken = callUrl.getSinToken();
				String ossUrl = super.processFile(url, sinToken);
				if (StringUtil.isNotEmpty(ossUrl)) {
					doctorCallInfoMapper.updateNotAliyunCallUrl(sinToken, url);
					logger.info("sinToken={}, 更新非阿里云录音url成功！", sinToken);
				}
			} catch (Exception e){
				if (e instanceof FileNotFoundException){
					logger.warn("文件没有找到：url=" + callUrl.getCallUrl());
				}else {
					e.printStackTrace();
				}

			}
		});

	}

	/**
	 * 新增或者更新电话记录
	 * @param dealingList
	 */
	private void saveOrUpdateDealingCallList(List<Call7mmorResponseBean> dealingList) {
		logger.info("电话记录开始走补偿机制进行校验。。。。");
		for (Call7mmorResponseBean call7mmorResponseBean:dealingList){
			String call_sheet_id = call7mmorResponseBean.getCALL_SHEET_ID();
			logger.info("开始校验sinToken={} 的电话记录！！", call_sheet_id);
			CallInfoResponseBean callInfo = doctorCallInfoMapper.getCallInfoBySinToken(call_sheet_id);
			if (callInfo != null){
				String callUrl = callInfo.getCallUrl();
				if (StringUtils.isEmpty(callUrl) || callInfo.getCallTime()==null || callInfo.getCallTime() == 0 || (!"answer".equals(callInfo.getStatusName()))){
					String ossFilePath = null;
					if (StringUtil.isNotEmpty(callUrl)){
						ossFilePath = callUrl;
					}else{
						String record_file_name = call7mmorResponseBean.getRECORD_FILE_NAME();
						String file_server = call7mmorResponseBean.getFILE_SERVER();
						String originFilePath = file_server +"/" +  record_file_name;
						try {
							ossFilePath = super.processFile(originFilePath, call_sheet_id);
						}catch (Exception e){
							logger.error("sinToken={} 文件上传失败", call_sheet_id, e);
						}
					}


					if (!StringUtils.isEmpty(ossFilePath)){

						String beginTime = call7mmorResponseBean.getBEGIN_TIME();
						String endTime = call7mmorResponseBean.getEND_TIME();
						int callTime = DateUtil.calLastedTime(beginTime, endTime);
						doctorCallInfoMapper.updateCallUrlBySigToken(ossFilePath, call_sheet_id, callTime);

						super.updateCallUrlText(call_sheet_id, ossFilePath);
						logger.info("sinToken={} 的电话记录，没有call_url，更新成功！", call_sheet_id);
					}
				}else {
					logger.info("sinToken={} 的电话记录，库里存在且有call_url，不需要补偿！", call_sheet_id);
				}
			}else {

				String record_file_name = call7mmorResponseBean.getRECORD_FILE_NAME();
				String file_server = call7mmorResponseBean.getFILE_SERVER();
				String originFilePath = file_server +"/" +  record_file_name;
				String ossFilePath = null;
				try {
					ossFilePath = super.processFile(originFilePath, call_sheet_id);
				}catch (Exception e){
					logger.error("sinToken={} 文件上传失败", call_sheet_id, e);
				}

				String connect_type = call7mmorResponseBean.getCONNECT_TYPE();
				String beginTime = call7mmorResponseBean.getBEGIN_TIME();
				String endTime = call7mmorResponseBean.getEND_TIME();
				Integer type = -1;
				// 1是呼出，2是呼入
				if (connect_type != null && "dialout".equals(connect_type)){
					type = 1;
				}else {
					type = 2;
				}
				
				String call_no = call7mmorResponseBean.getCALLED_NO();

				RetryCallInfoRequestBean retryCallInfoRequestBean = new RetryCallInfoRequestBean();
				retryCallInfoRequestBean.setSinToken(call_sheet_id);
				retryCallInfoRequestBean.setMobile(call_no);
				// 走补偿的都是接通的
				retryCallInfoRequestBean.setStatus(1);
				retryCallInfoRequestBean.setStatusName("answer");
				retryCallInfoRequestBean.setCallTime(DateUtil.calLastedTime(beginTime, endTime));
				retryCallInfoRequestBean.setCallUrl(ossFilePath);
				retryCallInfoRequestBean.setCreateTime(beginTime);
				retryCallInfoRequestBean.setType(type);
				doctorCallInfoMapper.addRetryCallInfo(retryCallInfoRequestBean);
				super.updateCallUrlText(call_sheet_id, ossFilePath);
				logger.info("sinToken={} 的电话记录，库里不存在，新增成功！", call_sheet_id);
			}
		}

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 线程暂停65秒,等待录音文件生成
	 */
	private void threadSleep() {
		try {
			Thread.sleep(65000); // 这个值是在 7moor 技术人员给出的1分钟基础上又加了5秒钟  
		} catch (InterruptedException e) {
			logger.error("InterruptedException", e);
		}
	}
	
	/**
	 * 变更通话状态,将 7moor -> 老状态<br>
	 * 参考链接 https://developer.7moor.com/event/
	 * @param paramsMap
	 * @return AlterResult
	 */
	private ConvertResult buildConvertResult(ConcurrentMap<String, String> paramsMap) {
		String calledNo = paramsMap.get("CalledNo"); //被叫号码
		String sinToken = paramsMap.get("CallSheetID"); // 呼叫唯一标识 
		String statusName = paramsMap.get("State");
		String monitorFilenameUrl = paramsMap.get("MonitorFilename"); // 录音文件
		String begin = paramsMap.get("Begin"); // 通话开始时间
		String end = paramsMap.get("End"); // 通话结束时间
		/*
		 * 通话类型：
		 * dialout外呼通话,normal普通来电,
		 * transfer转接电话,dialTransfer外呼转接
		 */
		String callType = paramsMap.get("CallType"); // dialout 呼出
		/*
		 * 接听状态：
		 * dealing（已接）,notDeal（振铃未接听）,
		 * leak（ivr放弃）,queueLeak（排队放弃）,
		 * blackList（黑名单）,voicemail（留言）
		 */
		logger.warn("sinToken:{},通话类型:{},录音下载地址:{},begin:{},end{}, statusName:{}", 
				sinToken, callType, monitorFilenameUrl, begin, end, statusName);
		
		return this.doBuildConvertResult(sinToken, calledNo, monitorFilenameUrl, begin, end, callType, statusName);
	}
	
	/**
	 * 建造 ConvertResult 对象 
	 * @param sinToken
	 * @param calledNo
	 * @param monitorFilenameUrl
	 * @param begin
	 * @param end
	 * @param callType
	 * @param statusName
	 * @return
	 */
	private ConvertResult doBuildConvertResult(String sinToken, String calledNo, String monitorFilenameUrl,
			String begin, String end, String callType, String statusName) {
		ConvertResult convertResult = new ConvertResult();

		// 开始时间或者结束时间为空的一般都是电话未接通的，所以回调时间返回的是空
		// 为了保证电话时间正确性，开始时间和结束时间都一样
		if (StringUtil.isEmpty(begin)){
			begin = DateUtil.getDateTimeString(new Date());
			end = DateUtil.getDateTimeString(new Date());
		}


		convertResult.setSinToken(sinToken);
		convertResult.setCalledNo(calledNo);
		convertResult.setMonitorFilenameUrl(monitorFilenameUrl);
		convertResult.setVisitTime(begin); // 打电话开始时间即为拜访时间
		
		statusName = ConvertStatusUtil.convertStatusName(callType, statusName);
		convertResult.setStatusName(statusName);

		Integer status = ConvertStatusUtil.getStatus(statusName);
		convertResult.setStatus(status);
		
		if ("dialout".equalsIgnoreCase(callType)) {
			convertResult.setType(1); // 呼出
		} else  if ("normal".equalsIgnoreCase(callType)) {
			convertResult.setType(2); // 呼入
		}
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate = sdf.parse(begin);
			Date endDate = sdf.parse(end);
			long delta = (endDate.getTime() - startDate.getTime()) / 1000; 
			convertResult.setCallTime(delta);
		} catch (ParseException e) {
			logger.error("时间转换异常:startDate{}, endDate:{}", begin, end, e);
		}
		
		return convertResult;
	}
}

@Data
class ConvertResult{
	private String sinToken; // 通话唯一标识
	private String statusName; // 状态名
	private String monitorFilenameUrl; // 录音文件地址
	private long callTime; // 通话时长
	private Integer type; // 呼叫方式 
	private String calledNo; // 被叫号码
	private String visitTime; // 拜访时间
	private Integer status;
}
