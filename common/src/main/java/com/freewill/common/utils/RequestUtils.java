package com.freewill.common.utils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class RequestUtils {

    Logger log = Logger.getLogger(getClass());

    private static String getParameter(HttpServletRequest request, String parameterName) {
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("获取参数出错", e);
        }
        String para = request.getParameter(parameterName);
        if (para != null && para.equals("undefined")) {
            return null;
        } else {
            return para;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getParameter(HttpServletRequest request, String parameterName, T defaultValue) {

        String value = getParameter(request, parameterName);

        if (value == null) {
            return defaultValue;
        }

        if (defaultValue instanceof String) {
            return (T) value;
        } else if (defaultValue instanceof Integer) {
            return (T) Integer.valueOf(value);
        } else if (defaultValue instanceof Float) {
            return (T) Float.valueOf(value);
        } else if (defaultValue instanceof Long) {
            return (T) Long.valueOf(value);
        } else if (defaultValue instanceof Double) {
            return (T) Double.valueOf(value);
        } else if (defaultValue instanceof Boolean) {
            return (T) Boolean.valueOf(value);
        } else if (defaultValue instanceof byte[]) {
            return (T) value.getBytes();
        } else {
            return (T) value;
        }

    }

    public void print(HttpServletRequest request, boolean isFull) {
        String url = getRequestURL(request);
        String method = getMethod(request);
        String params = getParametersToStr(request);
        String ip = getIpAddr(request);
        log.info("Url:" + url);
        log.info("Params:" + params);

        if (isFull) {
            log.info("Method:" + method);
            log.info("Ip:" + ip);
        }
    }

    /**
     * 获取所有请求参数
     *
     * @param request
     * @return Map<String   ,       Object>
     */
    public static Map<String, Object> getParameters(HttpServletRequest request) {

        Map<String, Object> params = new HashMap<String, Object>();

        Enumeration<String> paramEnumeration = request.getParameterNames();

        while (paramEnumeration.hasMoreElements()) {

            String key = paramEnumeration.nextElement();
            String value = request.getParameter(key);

            params.put(key, value);
        }
        return params;
    }

    public static String getParametersToStr(HttpServletRequest request) {
        Map<String, Object> param = getParameters(request);
        return MapUtils.transMap2Str(param);
    }

    public static String getRequestURL(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

    public static String getRequestURI(HttpServletRequest request) {
        return request.getRequestURI();
    }

    public static String getMethod(HttpServletRequest request) {
        return request.getMethod();
    }

    /**
     * 获取访问者IP
     * <p>
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * <p>
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    /**
     * 获取浏览器名称
     *
     * @param request
     * @return
     */
    public static String getBrowserName(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));

        Browser b = userAgent.getBrowser();

        return b.getName();
    }

    /**
     * 获取浏览器标识
     * <名称+版本号>
     *
     * @param request
     * @return
     */
    public static String getBrowser(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));

        Browser b = userAgent.getBrowser();

        StringBuilder sb = new StringBuilder(b.getName());

        Version v = userAgent.getBrowserVersion();
        if (null != v) {
            sb.append(" V").append(v.getVersion());
        }

        return sb.toString();
    }
}
