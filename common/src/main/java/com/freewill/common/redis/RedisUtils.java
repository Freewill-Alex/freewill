package com.freewill.common.redis;

import com.freewill.common.config.CommonConfig;
import com.freewill.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

@Slf4j
public class RedisUtils {
    private static Redisson redisson;

    private RedisUtils() {
    }

    public synchronized static Redisson getRedisson() {//创建配置
        if (null == redisson) {
            Config config = new Config();

            //指定编码，默认编码为org.redisson.codec.JsonJacksonCodec
            //之前使用的spring-data-redis，用的客户端jedis，编码为org.springframework.data.redis.serializer.StringRedisSerializer
            //改用redisson后为了之间数据能兼容，这里修改编码为org.redisson.client.codec.StringCodec
            config.setCodec(new org.redisson.client.codec.StringCodec());

            //指定使用单节点部署方式

            SingleServerConfig singleServerConfig = config.useSingleServer();
            singleServerConfig.setAddress("https://" + CommonConfig.getRedisServer() + ":" + CommonConfig.getRedisPort());

            if (!StringUtils.isEmpty(CommonConfig.getRedisPwd())) {
                String pwd = CommonConfig.getRedisPwd();

                singleServerConfig.setPassword(pwd);
            }

            //创建客户端(发现创建RedissonClient非常耗时，基本在2秒-4秒左右)
            redisson = (Redisson) Redisson.create(config);

            //首先获取redis中的key-value对象，key不存在没关系
            RBucket<String> keyObject = redisson.getBucket("key");
            //如果key存在，就设置key的值为新值value
            //如果key不存在，就设置key的值为value
            keyObject.set("value");


        }
        return redisson;
    }
}
