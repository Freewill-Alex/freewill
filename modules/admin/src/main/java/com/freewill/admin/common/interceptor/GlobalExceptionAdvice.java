package com.freewill.admin.common.interceptor;

import com.baomidou.mybatisplus.extension.api.R;
import com.freewill.common.constant.CommonConstant;
import com.freewill.common.exception.BussinessException;
import com.freewill.common.exception.SystemException;
import com.freewill.common.web.wrapper.GlobalResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.io.IOException;

import static com.freewill.common.constant.CommonConstant.BUSINESS_ERR;

/**
 * @author GaoJian
 * @description 全局统一异常
 * @email j.gao@ejauto.cn
 * @created 2018/9/25/0025 19:15
 */
@RestController
@ControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {
    /**
     * 业务开发相关异常
     *
     * @param req
     * @param e
     * @return GlobalResponseResult
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BussinessException.class, RuntimeException.class})
    public R<Object> businessException(HttpServletRequest req, Exception e) {
        log.error("API服务异常：", e);
        return exceptionWarpper(CommonConstant.FAILED_CODE, "Exception ：" + e.getMessage(), req);
    }

    /**
     * 登录相关异常
     *
     * @param req
     * @param e
     * @return GlobalResponseResult
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {ShiroException.class,})
    public R<Object> shiroException(HttpServletRequest req, Exception e) {
        log.error("未登录：", e);
        return exceptionWarpper(CommonConstant.NOLOGIN_CODE, "用户未登录 ：" + e.getMessage(), req);
    }

    /**
     * 500 -  服务器内部异常跳转页面
     *
     * @param e
     * @param request
     * @return ModelAndView
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ServletException.class, IOException.class, SystemException.class})
    public ModelAndView serverException(Exception e, WebRequest request) {
        log.error("系统服务异常：", e);
        log.debug(request.getHeader("Content-Type"));
        ModelAndView mvc = new ModelAndView();
        mvc.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR);
        mvc.addObject("message", e.getClass() + ":" + e.getLocalizedMessage());
        mvc.setViewName("/error/500.html");
        return mvc;
    }

    /**
     * 404 - 服务未找到
     *
     * @param e
     * @param request
     * @return ModelAndView
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ModelAndView noMapping(NoHandlerFoundException e, WebRequest request) {
        log.warn("请求服务路径[{}]未找到可用服务...", request.getDescription(true));
        return new ModelAndView("/error/404.html");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R<Object> handleMissingServletRequestParameterException(HttpServletRequest req, MissingServletRequestParameterException e) {
        log.error("缺少请求参数", e);
        return exceptionWarpper(CommonConstant.FAILED_CODE, "缺少请求参数", req);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<Object> handleHttpMessageNotReadableException(HttpServletRequest req, HttpMessageNotReadableException e) {
        log.error("参数解析错误", e);
        return exceptionWarpper(CommonConstant.FORM_VALID_ERROR_CODE, "参数解析错误", req);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Object> handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error != null ? error.getField() : null;
        String code = error != null ? error.getDefaultMessage() : null;
        String message = String.format("%s:%s", field, code);
        log.error("参数验证错误{}", message);
        return exceptionWarpper(CommonConstant.FORM_VALID_ERROR_CODE, "参数验证错误" + message, req);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public R<Object> handleBindException(HttpServletRequest req, BindException e) {
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error != null ? error.getField() : null;
        String code = error != null ? error.getDefaultMessage() : null;
        String message = String.format("[%s]:%s", field, code);
        log.error("参数绑定异常{}", message);
        return exceptionWarpper(CommonConstant.FORM_VALID_ERROR_CODE, "参数绑定异常" + message, req);
    }


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public R<Object> handleServiceException(HttpServletRequest req, ConstraintViolationException e) {
        log.error("参数验证错误", e);

        return exceptionWarpper(CommonConstant.FORM_VALID_ERROR_CODE, "参数验证错误", req);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public R<Object> handleValidationException(HttpServletRequest req, ValidationException e) {
        log.error("参数验证错误", e);
        return exceptionWarpper(CommonConstant.FORM_VALID_ERROR_CODE, "参数验证错误", req);
    }


    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<Object> handleHttpRequestMethodNotSupportedException(HttpServletRequest req, HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法", e);
        return exceptionWarpper(CommonConstant.FAILED_CODE, "不支持当前请求方法", req);
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public R<Object> handleHttpMediaTypeNotSupportedException(HttpServletRequest req, HttpMediaTypeNotSupportedException e) {
        log.error("不支持当前媒体类型", e);

        return exceptionWarpper(CommonConstant.FAILED_CODE, "不支持当前媒体类型", req);
    }

    private R<Object> exceptionWarpper(int code, String message, HttpServletRequest req) {
        return new GlobalResponseResult().setPath(req.getRequestURI()).setCode(code).setMsg(message).setData(BUSINESS_ERR);
    }


}


