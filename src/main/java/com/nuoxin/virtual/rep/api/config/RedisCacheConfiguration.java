package com.nuoxin.virtual.rep.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

import java.util.Collection;

/**
 * 类名：RedisCacheConfiguration<br>
 * 描述：<br>
 * 创建人：<br>
 * 创建时间：2016/9/6 17:33<br>
 *
 * @version v1.0
 */

@Configuration
public class RedisCacheConfiguration extends CachingConfigurerSupport {

    // PropertySourcesPlaceholderConfigurer这个bean，
    // 这个bean主要用于解决@value中使用的${…}占位符。
    // 假如你不使用${…}占位符的话，可以不使用这个bean。

    @Autowired
    JedisPool jedisPool;

    @Bean
    public CacheManager cacheManager(Cache cache) {
        CacheManager cacheManager = new CacheManager() {
            @Override
            public Cache getCache(String s) {
                return new RedisCacheConfig(s,jedisPool);
            }

            @Override
            public Collection<String> getCacheNames() {
                return null;
            }
        };
        return cacheManager;
    }

}
