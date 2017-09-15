package com.nuoxin.virtual.rep.api.config;

import com.nuoxin.virtual.rep.api.web.intercept.CrossDomainFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by fenggang on 7/28/17.
 */
@Configuration
@PropertySource(value = {"classpath:/application.properties"}, ignoreResourceNotFound = true, encoding = "utf-8")
public class AppBeanFactary {

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
