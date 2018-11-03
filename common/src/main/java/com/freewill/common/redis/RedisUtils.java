package com.freewill.common.redis;

import com.freewill.common.config.CommonConfig;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
@Slf4j
public class RedisUtils {
    private static Redisson redisson;
    private RedisUtils() {
    }

    public synchronized static Redisson getRedisson() {
        if (null == redisson) {
            Config config = new Config();
            config.useSingleServer().setAddress("redis://" + CommonConfig.getRedisServer() + ":" + CommonConfig.getRedisPort()).setPassword(CommonConfig.getRedisPwd());
            redisson = (Redisson) Redisson.create(config);
        }
        return redisson;
    }
}
