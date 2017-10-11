package com.nuoxin.virtual.rep.api.common.util.mem;

import org.springframework.stereotype.Component;

/**
 * CHECK 缓存
 *
 * Created by fenggang on 7/28/17.
 */
@Component
public class CheckMemUtils extends MemcachedUtils {

	public static final String CHECK_PREFIX = "CHECK_";

	public void setCheck(String key, Object obj) {
		if (obj == null) {
			return;
		}
		this.set(CHECK_PREFIX + key, obj);
	}

	public Object getCheck(String key) {
		Object obj = get(CHECK_PREFIX + key);
		if (obj != null) {
			return obj;
		}
		return null;
	}

	public void delCheck(String key) {
		delete(CHECK_PREFIX + key);
	}

}
