package com.nuoxin.virtual.rep.api.common.util.mem;

import com.alibaba.fastjson.JSON;
import com.nuoxin.virtual.rep.api.common.util.MySession;
import org.springframework.stereotype.Component;

/**
 * session 缓存
 *
 * Created by fenggang on 7/28/17.
 */
@Component
public class SessionMemUtils extends MemcachedUtils {

    public static final String SESSION_PREFIX = "SESSION_";

    // 保存session
    public void setSession(String sessionId, MySession session) {
        if (session == null) {
            return;
        }
        set(SESSION_PREFIX + sessionId, JSON.toJSONString(session));
    }

    // 获得session
    public MySession getSession(String sessionId) {
        Object obj = get(SESSION_PREFIX + sessionId);
        if (obj != null) {
            return JSON.parseObject(obj.toString(), MySession.class);
        }
        return null;
    }

    // 删除session
    public void delSession(String sessionId) {
        delete(SESSION_PREFIX + sessionId);
    }

    public void deleteKey(String key){
        delete(key);
    }

}
