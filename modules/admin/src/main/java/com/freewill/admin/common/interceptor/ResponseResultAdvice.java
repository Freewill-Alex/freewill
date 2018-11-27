package com.freewill.admin.common.interceptor;

import com.baomidou.mybatisplus.extension.api.R;
import com.freewill.common.constant.CommonConstant;
import com.freewill.common.utils.RequestContextHolderUtil;
import com.freewill.common.web.annotation.ResponseResult;
import com.freewill.common.web.wrapper.GlobalResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author zhumaer
 * @desc 接口响应体处理器
 * @since 4/1/2018 3:00 PM
 */
@Slf4j
@ControllerAdvice
public class
ResponseResultAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //根据业务和上面各个参数判断，是否执行下面的beforeBodyWrite方法，返回true则执行，否则不执行
        ResponseResult responseResultAnn = (ResponseResult) RequestContextHolderUtil.getRequest().getAttribute(ResponseResultInterceptor.RESPONSE_RESULT);
        return responseResultAnn != null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 此处的body即为返回前的返回结果，可对此进行处理，如加密，替换等等，如数据列权限的控制，可在此处用反射进行替换等
        if (body instanceof GlobalResponseResult) {
            return body;
        }

        ResponseResult responseResultAnn = (ResponseResult) RequestContextHolderUtil.getRequest().getAttribute(ResponseResultInterceptor.RESPONSE_RESULT);
        log.debug("ResponseResult[MediaType:{}," +
                "HttpMessageConverter:{}," +
                "MethodParameter:{}]", selectedContentType, selectedConverterType, returnType);

        log.debug("返回值:{}", body.toString());

        Class<? extends R> resultClazz = responseResultAnn.value();
        if (resultClazz.isAssignableFrom(GlobalResponseResult.class)) {
            R result = new GlobalResponseResult().setPath(RequestContextHolderUtil.getRequest().getRequestURI()).setCode(CommonConstant.SUCCESS_CODE).setData(body);
            if (body instanceof String) {
                return result.toString();
            }
            return result;
        }
        return body;
    }

}
