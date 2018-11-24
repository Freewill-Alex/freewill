package com.freewill.admin.common.interceptor;

import com.freewill.common.web.annotation.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;


/**
 * @author zhumaer
 * @desc 接口响应体控制拦截器
 * @since 4/1/2018 3:00 PM
 */
@Component
@Slf4j
public class ResponseResultInterceptor implements HandlerInterceptor {

    public static final String RESPONSE_RESULT = "RESPONSE-RESULT";
    private List<Integer> errorCodeList = Collections.singletonList(500);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        log.debug("ResponseResultInterceptor.preHandle------------->>>");
        if (errorCodeList.contains(response.getStatus())) {

            return false;
        }
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Class<?> clazz = handlerMethod.getBeanType();
            final Method method = handlerMethod.getMethod();
            if (clazz.isAnnotationPresent(ResponseResult.class)) {
                request.setAttribute(RESPONSE_RESULT, clazz.getAnnotation(ResponseResult.class));
            } else if (method.isAnnotationPresent(ResponseResult.class)) {
                request.setAttribute(RESPONSE_RESULT, method.getAnnotation(ResponseResult.class));
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)   {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)   {
        // nothing to do
    }

}

