package com.nuoxin.virtual.rep.api.config;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.oss.OSSClient;
import com.nuoxin.virtual.rep.api.web.intercept.CrossDomainFilter;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import javax.servlet.MultipartConfigElement;
import java.io.IOException;

/**
 * Created by fenggang on 7/28/17.
 */
@Configuration
@PropertySource(value = {"classpath:/application.properties"}, ignoreResourceNotFound = true, encoding = "utf-8")
public class AppBeanFactary {

    @Resource
    private MemcachedConfig memcachedConfig;
    @Resource
    private AliyunConfig aliyunConfig;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    Logger logger = LoggerFactory.getLogger(RedisCacheConfiguration.class);

    @Value("${spring.redis.database}")
    private int database;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.password}")
    private String password;

    /**
     * spring.redis.pool.max-idle=8
     * spring.redis.pool.min-idle=0
     * spring.redis.pool.max-active=8
     * spring.redis.pool.max-wait=-1
     *
     * @return
     */

    @Bean
    public JedisPool redisPoolFactory() {
        logger.info("JedisPool注入成功！！");
        logger.info("redis地址：" + host + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        if(password==null ||"".equals(password)){
            return new JedisPool(jedisPoolConfig, host, port, timeout);
        }
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout,password,database);
        return jedisPool;
    }

    @Bean
    MemcachedClient memcachedClient() throws IOException {
        MemcachedClient memcachedClient = null;
        if (memcachedConfig.isNeedAuth()) {
            AuthDescriptor ad =
                    new AuthDescriptor(new String[] {"PLAIN"}, new PlainCallbackHandler(memcachedConfig.getUsername(), memcachedConfig.getPassword()));
            memcachedClient =
                    new MemcachedClient(new ConnectionFactoryBuilder().setProtocol(ConnectionFactoryBuilder.Protocol.BINARY).setAuthDescriptor(ad).build(),
                            AddrUtil.getAddresses(memcachedConfig.getServers()));
        } else {
            memcachedClient =
                    new MemcachedClient(new ConnectionFactoryBuilder().setProtocol(ConnectionFactoryBuilder.Protocol.BINARY).build(), AddrUtil.getAddresses(memcachedConfig
                            .getServers()));
        }

        return memcachedClient;
    }

    @Bean
    CloudAccount  cloudAccount(){
        CloudAccount account = new CloudAccount(aliyunConfig.getAccessKeyId(), aliyunConfig.getAccessKeySecret(), aliyunConfig.getMnsEndpoint());
        return account;
    }

    @Bean(name = "uploadOSSClient")
    public OSSClient uploadOSSClient() {
        return new OSSClient(aliyunConfig.getUploadEndpoint(), aliyunConfig.getOssAccessKeyId(), aliyunConfig.getOssAccessKeySecret());
    }

    /**
     * 上传文件大小限制
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory configFactory = new MultipartConfigFactory();
        configFactory.setMaxFileSize("100MB");
        configFactory.setMaxRequestSize("100MB");
        return configFactory.createMultipartConfig();
    }

    /**
     * 注册跨域支持过滤器
     */
    @Bean
    public FilterRegistrationBean registerCrossDomainFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CrossDomainFilter crossDomainFilter = new CrossDomainFilter();
        // 设置是否允许跨域访问
        crossDomainFilter.setAllowCrossDomain(true);
        registrationBean.setFilter(crossDomainFilter);
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
