package com.freewill.admin.common.config;

import com.freewill.common.config.CommonConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目的配置类
 *
 * @author dengfuyuan
 */

@Configuration
@Slf4j
@Getter
@Setter
@ToString
public class CommonProperty {

    private static Map properties = new HashMap<>();

    static {
        Yaml yaml = new Yaml();
        try {
            InputStream in = CommonProperty.class.getResourceAsStream("/freewill.yml");
            if (in != null) {
                //也可以将值转换为Map
                properties = yaml.load(in);
            }
            log.debug("自定义配置：{}", properties);
        } catch (Exception e) {
            log.error("加载配置文件失败", e);
        }

        //初始化公共组件的配置
        CommonConfig.setRedisServer(getProperty("redis.server"));
        CommonConfig.setRedisPort(getProperty("redis.port"));
        CommonConfig.setRedisPwd(getProperty("redis.pwd"));
    }

//
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
//
//    public static final String DOMAIN = getProperty("domain");


    /**
     * 通过 [xxx.xxx.x.x] 获取配置文件的配置项
     *
     * @param key
     * @return
     */
    private static String getProperty(String key) {
        if (properties != null) {
            try {
                Map objMap = properties;
                if (key.contains(".")) {
                    String[] keys = key.split("\\.");
                    Object obj;

                    for (String keyElement : keys) {
                        obj = objMap.get(keyElement);
                        if ((obj instanceof Map)) {
                            objMap = (Map) obj;
                        } else {
                            return obj.toString();
                        }
                    }
                } else {
                    log.debug("Key:" + key);
                    return objMap.get(key).toString();
                }
            } catch (Exception ex) {
                log.error(key, ex);
            }
        }
        return "";
    }

    public static void main(String[] args) {
        String key = "redis.server";
        String[] keys = key.split("\\.");
        System.out.println(keys.length);
    }
}
