package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.web.controller.request.call.Call7mmorRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.Call7mmorResponseBean;

import java.util.List;
import java.util.Map;

/**
 * 回调接口类
 * @author xiekaiyu
 */
public interface CallBackService {
	
	/**
	 * 回调业务方法
	 * @param paramsMap 参数键值对
	 */
	void callBack(Map<String, String> paramsMap);


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
}
