package com.freewill.console.common.config;

import org.springframework.stereotype.Component;

/**
 * 项目的配置类
 *
 * @author dengfuyuan
 */
@Component
public final class ApiConfig {

//    private static final Logger logger = LoggerFactory.getLogger(ApiConfig.class);
//    private static Properties props = new Properties();
//    /**
//     * OSS bucket name
//     */
//    public final static String OSS_PUBLIC_BUCKETNAME = getProperty("oss.public.bucketname");
//    public final static String OSS_PRIVATE_BUCKETNAME = getProperty("oss.private.bucketname");
//    /**
//     * 共有OSS的域名
//     */
//    public final static String OSS_ENDPOINT = getProperty("oss.endpoint");
//    /**
//     * 图片处理OSS CDN加速域名
//     */
//    public final static String OSS_IMG_URL = getProperty("oss.imgurl");
//    public static final boolean IM_OFF = Boolean.parseBoolean(getProperty("imoff"));
//    /****  清库宝  ************/
//    public static final String QKB_DOMAIN = getProperty("qkb.domain");
//    public static final String DOMAIN = getProperty("domain");
//    public static final String APP_API_DOMAIN = getProperty("app.api.domain");
//    public static final String BUSINESSCARD = getProperty("businessCard.url");
//    public static final String POST_TEMPLATE_URL = getProperty("post.template.url");
//
//    static {
//        try {
//            InputStream in = ApiConfig.class.getResourceAsStream("/settings.properties");
//            props.load(in);
//        } catch (Exception e) {
//            logger.error("加载配置文件失败", e);
//        }

//        //初始化公共组件的配置
//        CommonConfig.setDebugMode(Boolean.parseBoolean(getProperty("debug")));
//        CommonConfig.setSmsOff(Boolean.parseBoolean(getProperty("smsoff")));
//        CommonConfig.setRedisServer(getProperty("redis.server"));
//        CommonConfig.setRedisPort(getProperty("redis.port"));
//        CommonConfig.setRedisPwd(getProperty("redis.pwd"));
//
//        CommonConfig.setAliyunRamAccesskey(getProperty("aliyun.ram.accesskey"));
//        CommonConfig.setAliyunRamAccesssecret(getProperty("aliyun.ram.accesssecret"));
//        CommonConfig.setAliyunAccountEndpoint(getProperty("aliyun.account.endpoint"));
//        CommonConfig.setAliyunSmsSign(getProperty("aliyun.sms.sign"));
//    }
//
//    private ApiConfig() {
//    }

//    /**
//     * 获取setting配置文件的配置项
//     *
//     * @param key
//     * @return
//     */
//    public static String getProperty(String key) {
//        try {
//            return props.getProperty(key).trim();
//        } catch (Exception ex) {
//            logger.error(key, ex);
//        }
//        return "";
//    }
}
