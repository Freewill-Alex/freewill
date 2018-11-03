package com.freewill.common.web.aop;


import com.freewill.common.redis.RedisUtils;
import com.freewill.common.utils.RequestUtils;
import com.freewill.common.web.IPLimitException;
import com.freewill.common.web.annotation.IPLimit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 拦截需要限制IP频率的接口
 *
 * @author Admin
 */
@Aspect
@Component
public class IPLimitAOP {
    private static final Logger logger = LoggerFactory.getLogger(IPLimitAOP.class);

    /**
     * 拦截需要限制IP频率的接口
     *
     * @param joinPoint
     * @param limit
     * @throws IPLimitException
     */
    @Before("within(@org.springframework.stereotype.Controller *) && @annotation(limit)")
    public void requestLimit(final JoinPoint joinPoint, IPLimit limit) throws IPLimitException {
        try {
            Object[] args = joinPoint.getArgs();
            HttpServletRequest request = null;
            for (Object arg : args) {
                if (arg instanceof HttpServletRequest) {
                    request = (HttpServletRequest) arg;
                    break;
                }
            }
            if (request == null) {
                throw new IPLimitException("缺失HttpServletRequest参数");
            }
            String ip = RequestUtils.getIpAddr(request);
            Signature signature = joinPoint.getSignature();
            String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
            String key = "request_limit_".concat(methodName).concat(ip);
            long count = RedisUtils.getRedisson().getAtomicLong(key).incrementAndGet();
            if (count == 1) {
                RedisUtils.getRedisson().getAtomicLong(key).expire(limit.time(), TimeUnit.MILLISECONDS);
            }
            if (count > limit.count()) {
                throw new IPLimitException("用户IP[" + ip + "]访问接口[" + methodName + "]超过了限定的次数[" + limit.count() + "]");
            }
        } catch (IPLimitException e) {
            throw e;
        } catch (Exception e) {
            logger.error("发生错误", e);
        }
    }
}
