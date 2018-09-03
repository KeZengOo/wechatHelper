package com.nuoxin.virtual.rep.api.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.mybatis.DoctorCallInfoMapper;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.utils.RestUtils;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.call.Call7mmorRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.Call7mmorResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.Default7MoorResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

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
	public void callBack(Map<String, String> paramsMap) {
		this.pause();
		
		// 与数据库对应的字段 sin_token(callId)
		String sinToken = paramsMap.get("CallSheetID");
		// 与数据库对应的字段 status_name
		String statusName = paramsMap.get("State");
		// 电话录音下载地址
		String monitorFilenameUrl = paramsMap.get("MonitorFilename");
		
		// 7moor 状态-> 转成老的状态
		if ("dealing".equalsIgnoreCase(statusName)) {
			statusName = "answer";
		} else if ("notDeal".equalsIgnoreCase(statusName)) {
			statusName = "incall";
		}

		super.processCallBack(sinToken, statusName, monitorFilenameUrl);
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
	private void pause () {
		try {
			Thread.sleep(70000);
		} catch (InterruptedException e) {
			logger.error("InterruptedException", e);
		}
	}
}
