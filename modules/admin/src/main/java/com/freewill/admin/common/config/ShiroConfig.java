package com.freewill.admin.common.config;

import com.freewill.admin.common.security.MyShiroDbRealm;
import com.freewill.admin.common.security.SpringCacheManagerWrapper;
import com.freewill.admin.common.security.session.MySessionManager;
import com.freewill.admin.common.security.session.OnlineSessionFactory;
import com.freewill.admin.common.security.session.RedissonSessionDao;
import com.freewill.admin.common.security.session.ShiroSessionListener;
import com.freewill.common.redis.RedisUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.redisson.client.codec.Codec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 *
 * @author Administrator
 * @date 2017/12/11
 */
@Log4j2
@Configuration
public class ShiroConfig {
    /**
     * 设置会话的全局过期时间（毫秒为单位），默认 30 分钟：
     */
    private static final int GLOBAL_SESSION_TIMEOUT = 1800000;
    /**
     * 设置会话过期验证间隔时间，默认 60 分钟：
     */
    private static final int SESSION_VALID_INTERVAL = 3600000;

    /**
     * Shiro的过滤器链
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        log.debug("ShiroConfiguration.shiroFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/unlogin");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //注意过滤器配置顺序 不能颠倒
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
//        filterChainDefinitionMap.put("/logout", "logout");
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/**", "authc");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public MyShiroDbRealm myShiroRealm() {
        return new MyShiroDbRealm();
    }

    /**
     * cookie对象;
     * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
     *
     * @return SimpleCookie
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间//30天 ,单位秒;-->
        simpleCookie.setMaxAge(30 * 24 * 60 * 60);
        return simpleCookie;
    }

    /**
     * cookie管理对象;
     * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     *
     * @return CookieRememberMeManager
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(org.apache.shiro.codec.Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        //配置记住我 参考博客：
        securityManager.setRememberMeManager(rememberMeManager());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    /**
     * 配置保存sessionId的cookie
     * 注意：这里的cookie 不是上面的记住我 cookie 记住我需要一个cookie session管理 也需要自己的cookie
     *
     * @return SimpleCookie
     */
    @Bean("sessionIdCookie")
    public SimpleCookie sessionIdCookie() {
        //这个参数是cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：

        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //maxAge=-1表示浏览器关闭时失效此Cookie，7天 单位秒;
        simpleCookie.setMaxAge(7 * 24 * 60 * 60);
        return simpleCookie;
    }

    @Bean
    public SessionManager sessionManager() {
        MySessionManager sessionManager = new MySessionManager();
        sessionManager.setSessionDAO(redissonSessionDao());
        Collection<SessionListener> listeners = new ArrayList<>();
        //配置监听
        listeners.add(sessionListener());
        sessionManager.setSessionFactory(sessionFactory());
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionIdCookie(sessionIdCookie());

        //全局会话超时时间（单位毫秒），默认30分钟  暂时设置为10秒钟 用来测试
        sessionManager.setGlobalSessionTimeout(GLOBAL_SESSION_TIMEOUT);
        //是否开启删除无效的session对象  默认为true
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启定时调度器进行检测过期session 默认为true
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
        //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
        //暂时设置为 5秒 用来测试
        sessionManager.setSessionValidationInterval(SESSION_VALID_INTERVAL);

        //取消url 后面的 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);

        return sessionManager;
    }


    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * <p>
     */
    @Bean
    public RedissonSessionDao redissonSessionDao() {
        RedissonSessionDao redissonSessionDao = new RedissonSessionDao();
        //使用自定义的序列化编码器
        LocalDateTimeSerializerConfig  serializerConfig=new  LocalDateTimeSerializerConfig();
        Codec customCodec = new JsonJacksonCodec(serializerConfig.objectMapper());
        redissonSessionDao.setCodec(customCodec);
        redissonSessionDao.setRedisson(RedisUtils.getRedisson());
        return redissonSessionDao;
    }

    @Bean
    public CacheManager cacheManager() {
        Map<String, CacheConfig> config = new HashMap<>(16);

        config.put("shiro-cache", new CacheConfig(30 * 60 * 1000, 15 * 60 * 1000));
        SpringCacheManagerWrapper scw = new SpringCacheManagerWrapper();
        scw.setCacheManager(new RedissonSpringCacheManager(RedisUtils.getRedisson(), config));
        return scw;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        // 设置代理类
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * 配置session监听
     *
     * @return ShiroSessionListener
     */
    @Bean("sessionListener")
    public ShiroSessionListener sessionListener() {
        return new ShiroSessionListener();
    }

    /**
     * 配置会话的工厂
     *
     * @return sessionFactory
     */
    @Bean("sessionFactory")
    public OnlineSessionFactory sessionFactory() {
        return new OnlineSessionFactory();
    }

    /**
     * Shiro生命周期处理器:
     * 用于在实现了Initializable接口的Shiro bean初始化时调用Initializable接口回调(例如:UserRealm)
     * 在实现了Destroyable接口的Shiro bean销毁时调用 Destroyable接口回调(例如:DefaultSecurityManager)
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
