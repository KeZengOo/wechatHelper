package com.nuoxin.virtual.rep.api.web.intercept;

import com.nuoxin.virtual.rep.api.config.SessionConfig;
import com.nuoxin.virtual.rep.api.config.UrlPatternConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * spring mvc的辅助配置
 */
@Configuration
public class DefaultWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Autowired
   private ApplicationContext applicationContext;
    @Autowired
   private SessionConfig sessionConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        UrlPatternConfig validationUtils = sessionConfig.getValidationUrls();
        LoginValidationInterceptor loginValidationInterceptor = applicationContext.getBean(LoginValidationInterceptor.class);
        List<String> includePatterns = validationUtils.getIncludePatterns();
        List<String> excludePatterns = validationUtils.getExcludePatterns();
        InterceptorRegistration registration = null;
        if ((null != includePatterns) && (includePatterns.size() > 0)) {
            registration = registry.addInterceptor(loginValidationInterceptor);
            registration.addPathPatterns(includePatterns.toArray(new String[]{}));
            if ((null != excludePatterns) && (excludePatterns.size() > 0)) {
                registration.excludePathPatterns(excludePatterns.toArray(new String[]{}));
            }
        }
    }

}
