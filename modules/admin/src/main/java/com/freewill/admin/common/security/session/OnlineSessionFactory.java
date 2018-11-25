package com.freewill.admin.common.security.session;

import com.freewill.common.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SimpleSessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @author GaoJian
 */
@Slf4j
public class OnlineSessionFactory extends SimpleSessionFactory {
    @Override
    public Session createSession(SessionContext initData) {
        log.debug("OnlineSessionFactory.createSession:{}", initData);
        OnlineSession session = new OnlineSession();

        if (initData instanceof WebSessionContext) {
            WebSessionContext sessionContext = (WebSessionContext) initData;
            HttpServletRequest request = (HttpServletRequest) sessionContext.getServletRequest();
            if (request != null) {
                session.setHost(IPUtils.getIpAddr(request));
                session.setUserAgent(request.getHeader("User-Agent"));
                session.setSystemHost(request.getLocalAddr() + ":" + request.getLocalPort());
            }
        }
        return session;
    }
}