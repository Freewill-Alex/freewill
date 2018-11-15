package com.freewill.cms.common.config;

import com.freewill.common.config.CommonConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 项目的配置类
 *
 * @author dengfuyuan
 */
@Component
@Slf4j
public final class ApiConfig {

    private ApiConfig() {
    }
    private static Properties props = new Properties();


    static {
//        try {
//            InputStream in = ApiConfig.class.getResourceAsStream("/settings.properties");
//            props.load(in);
//        } catch (Exception e) {
//            logger.error("加载配置文件失败", e);
//        }

        //初始化公共组件的配置
        CommonConfig.setDebugMode(Boolean.parseBoolean(getProperty("debug")));
        CommonConfig.setSmsOff(Boolean.parseBoolean(getProperty("smsoff")));
        CommonConfig.setRedisServer(getProperty("redis.server"));
        CommonConfig.setRedisPort(getProperty("redis.port"));
        CommonConfig.setRedisPwd(getProperty("redis.pwd"));

        CommonConfig.setAliyunRamAccesskey(getProperty("aliyun.ram.accesskey"));
        CommonConfig.setAliyunRamAccesssecret(getProperty("aliyun.ram.accesssecret"));
        CommonConfig.setAliyunAccountEndpoint(getProperty("aliyun.account.endpoint"));
        CommonConfig.setAliyunSmsSign(getProperty("aliyun.sms.sign"));
    }


    /**
     * OSS bucket name
     */
    public final static String OSS_PUBLIC_BUCKETNAME = getProperty("oss.public.bucketname");
    public final static String OSS_PRIVATE_BUCKETNAME = getProperty("oss.private.bucketname");
    /**
     * 共有OSS的域名
     */
    public final static String OSS_ENDPOINT = getProperty("oss.endpoint");
    /**
     * 图片处理OSS CDN加速域名
     */
    public final static String OSS_IMG_URL = getProperty("oss.imgurl");

    public static final String DOMAIN = getProperty("domain");
    

    /**
     * 获取setting配置文件的配置项
     *
     * @param key
     * @return
     */
    public static String getProperty(String key) {
//        try {
//            return props.getProperty(key).trim();
//        } catch (Exception ex) {
//            logger.error(key, ex);
//        }
        return "";
    }
}
