package com.nuoxin.virtual.rep.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.mybatis.DoctorCallInfoMapper;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.utils.RestUtils;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.call.Call7mmorRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.Call7mmorResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.Default7MoorResponseBean;

import lombok.Data;

/**
 * 回调Service类
 * @author xiekaiyu
 */
@Service("sevenMoor")
public class SevenMoorCallBackImpl extends BaseCallBackImpl implements CallBackService{

	private static final Logger logger = LoggerFactory.getLogger(CallBackService.class);

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
		logger.info("执行 7moor 回调方法... params:{}", JSONObject.toJSONString(paramsMap));
	
		this.threadSleep();
		ConvertResult alterResult = this.buildConvertResult(paramsMap);
		super.processCallBack(alterResult);
		
		logger.info("执行 7moor 回调方完成法完成! params:{}", JSONObject.toJSONString(paramsMap));
	}

	// TODO 代码临时写，有的参数写死，待优化
	@Override
	public List<Call7mmorResponseBean> getCallList(Call7mmorRequestBean bean) {
        List<Call7mmorResponseBean> call7mmorResponseBeans = null;
		// 账户编号： 	N00000028346
		//帐号APISecret： 	e7ac57d0-80ce-11e8-ab50-3f649a8c117a
		String url = "http://apis.7moor.com/v20170704/cdr/getCCCdr/ACCOUNTID?sig=sigKey";
		url = url.replace("ACCOUNTID", "N00000028346");
		String timestamp = DateUtil.getDateMillisecondString(new Date());

		String sigKey = "N00000028346e7ac57d0-80ce-11e8-ab50-3f649a8c117a" + timestamp;
		String md5SigKey = StringUtil.md5Password(sigKey);
		url = url.replace("sigKey", md5SigKey.toUpperCase());
		String auth = "N00000028346:"+ timestamp;
		String base64Auth = StringUtil.base64(auth);
		Map<String, String> header = new HashMap<>();
		header.put("Authorization", base64Auth);
		header.put("charset", "utf-8");
        String jsonStr = RestUtils.post(url, JSONObject.toJSONString(bean), header);
        Default7MoorResponseBean default7MoorResponseBean = JSONObject.parseObject(jsonStr, Default7MoorResponseBean.class);
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
                            doctorCallInfoMapper.updateCallUrlBySigToken(ossUrl, id);
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

		saveOrUpdateDealingCallList(dealingList);

	}


	private void saveOrUpdateDealingCallList(List<Call7mmorResponseBean> dealingList) {


	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 线程暂停65秒
	 */
	private void threadSleep() {
		try {
			Thread.sleep(65000); // 这个值是在 7moor 技术人员给出的1分钟基础上又加了5秒钟  
		} catch (InterruptedException e) {
			logger.error("InterruptedException", e);
		}
	}
	
	/**
	 * 变更通话状态,将 7moor -> 老状态
	 * @param paramsMap
	 * @return AlterResult
	 */
	private ConvertResult buildConvertResult(ConcurrentMap<String, String> paramsMap) {
		String callNo = paramsMap.get("CallNo"); // 被叫号码
		// 通话唯一标识
		String sinToken = paramsMap.get("CallSheetID");
		// 通话类型：dialout外呼通话,normal普通来电,transfer转接电话,dialTransfer外呼转接
		String callType = paramsMap.get("CallType");
		/*
		 * 接听状态：dealing（已接）,notDeal（振铃未接听）,
		 * leak（ivr放弃）,queueLeak（排队放弃）,blackList（黑名单）,voicemail（留言） 与数据库对应的字段
		 * status_name
		 */
		String statusName = paramsMap.get("State");
		statusName = this.convertStatusName(callType, statusName);
		// 电话录音下载地址
		String monitorFilenameUrl = paramsMap.get("MonitorFilename");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String begin = paramsMap.get("Begin");
		String end = paramsMap.get("End");
		
		logger.info("sinToken:{},通话类型:{},录音下载地址:{},begin:{},end{}", sinToken, callType, monitorFilenameUrl, begin, end);
		
		ConvertResult converResult = new ConvertResult();
		converResult.setSinToken(sinToken);
		converResult.setCallNo(callNo);
		converResult.setStatusName(statusName);
		converResult.setMonitorFilenameUrl(monitorFilenameUrl);
		converResult.setVisitTime(begin);
		
		if ("dialout".equalsIgnoreCase(callType)) {
			converResult.setType(1); // 呼出
		} else  if ("normal".equalsIgnoreCase(callType)) {
			converResult.setType(2); // 呼入
		}
		
		try {
			Date startDate = sdf.parse(begin);
			Date endDate = sdf.parse(end);
			long delta = (endDate.getTime() - startDate.getTime()) / 1000; 
			
			converResult.setCallTime(delta);
		} catch (ParseException e) {
			logger.error("时间转换异常:", e);
		}
		
		return converResult;
	}
	
	private String convertStatusName(String callType, String statusName) {
		if ("dialout".equalsIgnoreCase(callType)) { // 7moor 呼出状态-> 转成老的状态
			if ("dealing".equalsIgnoreCase(statusName)) {
				statusName = "answer";
			} else if ("notDeal".equalsIgnoreCase(statusName)) {
				// notDeal（振铃未接听）
				statusName = "cancelmakecall  ";
			}
		} else if ("normal".equalsIgnoreCase(callType)) { // 7moor 呼入状态-> 转成老的状态
			if ("dealing".equalsIgnoreCase(statusName)) {
				statusName = "incall";
			} else if ("notDeal".equalsIgnoreCase(statusName)) {
				// notDeal（振铃未接听）
				statusName = "cancelmakecall  ";
			}
		}
		
		return statusName;
	}
	
}

@Data
class ConvertResult{
	private String sinToken; // 通话唯一标识
	private String statusName; // 状态名
	private String monitorFilenameUrl; // 录音文件地址
	private long callTime; // 通话时长
	private int type; // 呼叫方式 
	private String callNo; // 被叫号码
	private String visitTime; // 拜访时间
}
