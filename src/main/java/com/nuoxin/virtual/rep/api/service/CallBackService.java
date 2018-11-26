package com.nuoxin.virtual.rep.api.service;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.nuoxin.virtual.rep.api.web.controller.request.call.Call7mmorRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.IdentifyCallUrlRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.Call7mmorResponseBean;

/**
 * 回调接口类
 * @author xiekaiyu
 */
public interface CallBackService {
	
	/**
	 * 回调业务方法
	 * @param paramsMap 参数键值对
	 */
	void callBack(ConcurrentMap<String, String> paramsMap) throws Exception;

	/**
	 *
	 * @param bean
	 * @return
	 */
	List<Call7mmorResponseBean> getCallList(Call7mmorRequestBean bean);

	/**
	 * 重新上传失败的录音文件
	 * @param bean
	 */
	void repeatUploadFile(Call7mmorRequestBean bean);

	/**
	 * 重新更新或者添加没有回调的电话拜访记录
	 * @param bean
	 */
	void repeatSaveOrUpdateCall(Call7mmorRequestBean bean);

	/**
	 * 录音识别
	 * @param bean
	 */
	void identifyCallUrl(IdentifyCallUrlRequestBean bean);

	/**
	 * 处理非阿里云录音url，转成阿里云url,再上传
	 */
	void handleNotAliyunCallUrl();

}
