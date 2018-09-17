package com.nuoxin.virtual.rep.api.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

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
	public boolean callBack(ConcurrentMap<String, String> paramsMap) {
		logger.info("执行 7moor 回调方法... params:{}", JSONObject.toJSONString(paramsMap));
		// 线程休眠等待文件生成
		this.threadSleep();
		// 变更通话状态,将 7moor -> 老状态
		AlterResult alterResult = this.alterStatusName(paramsMap);
		// 通用父类方法处理回调
		boolean flag = super.processCallBack(alterResult.getSinToken(), alterResult.getStatusName(),
				alterResult.getMonitorFilenameUrl());
		
		logger.info("执行 7moor 回调方完成法完成! params:{}", JSONObject.toJSONString(paramsMap));
		
		return flag;
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 线程暂停70秒
	 */
	private void threadSleep() {
		try {
			Thread.sleep(70000); // 这个值是在 7moor 技术人员给出的1分钟基础上又加了10秒钟  
		} catch (InterruptedException e) {
			logger.error("InterruptedException", e);
		}
	}
	
	/**
	 * 变更通话状态,将 7moor -> 老状态
	 * @param paramsMap
	 * @return AlterResult
	 */
	private AlterResult alterStatusName(ConcurrentMap<String, String> paramsMap) {
		// 通话记录ID,CallSheetID 是这条通话记录再DB中的唯一id 与数据库对应的字段 sin_token
		String sinToken = paramsMap.get("CallSheetID");
		/*
		 * 接听状态：dealing（已接）,notDeal（振铃未接听）,
		 * leak（ivr放弃）,queueLeak（排队放弃）,blackList（黑名单）,voicemail（留言） 与数据库对应的字段
		 * status_name
		 */
		String statusName = paramsMap.get("State");
		// 电话录音下载地址
		String monitorFilenameUrl = paramsMap.get("MonitorFilename");
		// 通话类型：dialout外呼通话,normal普通来电,transfer转接电话,dialTransfer外呼转接
		String callType = paramsMap.get("CallType");
		
		logger.info("sinToken:{},通话类型:{},录音下载地址:{}", sinToken, callType, monitorFilenameUrl);

		// 7moor 呼出状态-> 转成老的状态
		if ("dialout".equalsIgnoreCase(callType)) {
			if ("dealing".equalsIgnoreCase(statusName)) {
				statusName = "answer";
			} else if ("notDeal".equalsIgnoreCase(statusName)) {
				// notDeal（振铃未接听）
				statusName = "cancelmakecall  ";
			}
		} 
		// 7moor 呼入状态-> 转成老的状态
		else if ("normal".equalsIgnoreCase(callType)) {
			if ("dealing".equalsIgnoreCase(statusName)) {
				statusName = "incall";
			} else if ("notDeal".equalsIgnoreCase(statusName)) {
				// notDeal（振铃未接听）
				statusName = "cancelmakecall  ";
			}
		}
		
		AlterResult alterResult = new AlterResult();
		alterResult.setSinToken(sinToken);
		alterResult.setStatusName(statusName);
		alterResult.setMonitorFilenameUrl(monitorFilenameUrl);
		
		return alterResult;
	}
	
	@Data
	private static class AlterResult{
		private String sinToken;
		private String statusName;
		private String monitorFilenameUrl;
	}
}
