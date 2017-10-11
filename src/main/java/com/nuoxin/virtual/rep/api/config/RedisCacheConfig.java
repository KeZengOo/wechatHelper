package com.nuoxin.virtual.rep.api.config;

import com.alibaba.fastjson.JSON;
import com.nuoxin.virtual.rep.api.common.util.ObjectAndByte;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Created by fenggang on 7/28/17.
 */
@Configuration
public class RedisCacheConfig implements Cache, InitializingBean {

    private final static Logger logger = LoggerFactory.getLogger(RedisCacheConfig.class);

    private String name;

    private JedisPool jedisPool;

    public RedisCacheConfig(String name, JedisPool jedisPool) {
        super();
        this.name = name;
        this.jedisPool = jedisPool;
    }

    public RedisCacheConfig() {
        super();
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return null;
    }

    @Override
    public ValueWrapper get(Object o) {
        String cacheKey = getName() + o.toString();
        ValueWrapper result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            StopWatch sw = new StopWatch();
            sw.start();
            try {
                byte[] value = jedis.get(cacheKey.getBytes());
                if (value == null) {
                    sw.stop();
                    logger.info("读取redis用时{}, key={}", sw.getTime(), cacheKey);
                    return null;
                }
                Object keyValue = ObjectAndByte.toObject(value);
                if (keyValue == null || "null".equals(keyValue)) {
                    sw.stop();
                    logger.info("读取redis用时{}, key={}", sw.getTime(), cacheKey);
                    return null;
                }
                sw.stop();
                logger.info("读取redis用时{}, key={}", sw.getTime(), cacheKey);
                result = (value != null ? new SimpleValueWrapper(keyValue) : null);
            } catch (Exception e) {
                List<byte[]> list = jedis.lrange(cacheKey.getBytes(),0,100);
                if(list!=null){
                    sw.stop();
                    logger.info("读取redis用时{}, key={}", sw.getTime(), cacheKey);
                    List<Object> rlist = new ArrayList<>();
                    for (byte[] b: list) {
                        rlist.add(ObjectAndByte.toObject(b));
                    }
                    result = (rlist != null ? new SimpleValueWrapper(rlist) : null);
                }
            }

//            try{
//                Object obj = JSON.parseObject(keyValue,Object.class);
//                sw.stop();
//                logger.info("读取redis用时{}, key={}", sw.getTime(), cacheKey);
//                return (value != null ? new SimpleValueWrapper(obj) : null);
//            }catch (Exception e){
//                sw.stop();
//                Object obj = JSON.parseArray(keyValue);
//                logger.info("读取redis用时{}, key={}", sw.getTime(), cacheKey);
//                return (value != null ? new SimpleValueWrapper(obj) : null);
//
//            }
        } catch (Exception e) {
            logger.error("读取redis缓存发生异常, key={}, server={}", cacheKey,
                    "", e.getCause());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public <T> T get(Object o, Class<T> aClass) {
        ValueWrapper element = get(o);
        Object value = (element != null ? element.get() : null);
        if (value == null)
            return null;
        if (aClass != null && !aClass.isInstance(value)) {
            throw new IllegalStateException("缓存的值类型指定错误 [" + aClass.getName() + "]: " + value);
        }
        return JSON.parseObject(value.toString(), aClass);
    }

    @Override
    public <T> T get(Object o, Callable<T> callable) {
        ValueWrapper element = get(o);
        Object value = (element != null ? element.get() : null);
        if (value == null)
            return null;
        if (callable != null) {
            throw new IllegalStateException("缓存的值类型指定错误 [" + callable.toString() + "]: " + value);
        }
        return null;//JSON.parseObject(value.toString(),callable);
    }

    @Override
    public void put(Object key, Object value) {
        String cacheKey = getName() + key.toString();
        logger.info("放入缓存的Key:{}, Value:{}, StoreValue:{}", cacheKey, value);
        Jedis jedis = null;
        try {
            if (value != null) {
                jedis = jedisPool.getResource();
                jedis.del(cacheKey.getBytes());
                if (value instanceof List) {
                    Set<byte[]> set = new HashSet<>();
                    List list = (List) value;
                    for (Object obj : list) {
                        set.add(ObjectAndByte.toByteArray(obj));
                        jedis.rpush(cacheKey.getBytes(), ObjectAndByte.toByteArray(obj));
                    }
                } else {
                    jedis.set(cacheKey.getBytes(), ObjectAndByte.toByteArray(value));
                }
            }
        } catch (Exception e) {
            logger.error("redis写入缓存发生异常, key={}, server={}", cacheKey,
                    "", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public ValueWrapper putIfAbsent(Object o, Object o1) {
        return null;
    }

    @Override
    public void evict(Object o) {
        String cacheKey = getName() + o.toString();
        Jedis jedis = null;
        logger.debug("删除缓存的Key:{}", cacheKey);
        try {
            jedis = jedisPool.getResource();
            Set<byte[]> set = jedis.keys(cacheKey.getBytes());
            if (set != null && !set.isEmpty()) {
                for (byte[] b : set) {
                    jedis.del(b);
                }
            }
        } catch (Exception e) {
            logger.error("redis清除缓存出现异常, key={}, server={}", cacheKey,
                    "", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public void clear() {

    }

}