package com.freewill.admin.common.config;

import com.freewill.admin.common.interceptor.ResponseResultInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author GaoJian
 * @description 全局统一异常
 * @email j.gao@ejauto.cn
 * @created 2018/9/25/0025 19:15
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final ResponseResultInterceptor responseResultInterceptor;

    @Autowired
    public InterceptorConfig(ResponseResultInterceptor responseResultInterceptor) {
        this.responseResultInterceptor = responseResultInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //响应结果控制拦截
        registry.addInterceptor(responseResultInterceptor).addPathPatterns("/**");
    }

}