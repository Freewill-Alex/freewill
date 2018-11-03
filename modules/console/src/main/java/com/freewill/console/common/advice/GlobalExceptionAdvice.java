package com.freewill.console.common.advice;

import com.freewill.common.web.wrapper.GlobalResponseResult;
import com.freewill.console.common.exception.BussinessException;
import lombok.extern.slf4j.Slf4j;
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
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.util.Set;

import static com.freewill.common.web.wrapper.CommonConstant.BUSINESS_ERR;

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
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BussinessException.class,})
    public GlobalResponseResult businessException(HttpServletRequest req, BussinessException e) {
        log.debug("API服务异常：", e);
        return new GlobalResponseResult().setErrorMsg(BUSINESS_ERR).setPath(req.getRequestURI()).setData("Exception ：" + e.getMessage());

    }

    /**
     * 500 -  服务器内部异常跳转页面
     *
     * @param e
     * @param request
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ServletException.class, IOException.class, RuntimeException.class})
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
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ModelAndView noMapping(NoHandlerFoundException e, WebRequest request) {
        log.error("请求服务路径[{}]未找到可用服务...", request.getDescription(true));
        return new ModelAndView("/error/404.html");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("缺少请求参数", e);
        return "缺少请求参数";
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("参数解析失败", e);
        return "参数解析失败";
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GlobalResponseResult handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        log.error("参数验证失败{}", message);
        return new GlobalResponseResult().setErrorMsg(BUSINESS_ERR).setPath(req.getRequestURI()).setData("参数验证失败" + message);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public GlobalResponseResult handleBindException(HttpServletRequest req, BindException e) {
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("[%s]:%s", field, code);
        log.error("参数绑定失败{}", message);
        return new GlobalResponseResult().setErrorMsg(BUSINESS_ERR).setPath(req.getRequestURI()).setData("参数绑定失败" + message);
    }


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleServiceException(ConstraintViolationException e) {
        log.error("参数验证失败", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return "参数验证失败" + message;
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public String handleValidationException(ValidationException e) {
        log.error("参数验证失败", e);
        return "参数验证失败";
    }


    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法", e);
        return "request_method_not_supported";
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public String handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error("不支持当前媒体类型", e);
        return "content_type_not_supported";
    }
}


